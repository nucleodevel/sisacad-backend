package org.nucleodevel.sisacad.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.nucleodevel.sisacad.domain.AbstractEntity;
import org.nucleodevel.sisacad.dto.AbstractDto;
import org.nucleodevel.sisacad.repositories.AbstractRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.NotGivenIdException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.nucleodevel.sisacad.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

public abstract class AbstractService<E extends AbstractEntity<ID>, ID, DTO extends AbstractDto<E, ID>, R extends AbstractRepository<E, ID>> {

	/*
	 * 
	 * Constants and attributes
	 * 
	 */

	private static final String DEFAULT_GET_ID_METHOD_NAME = "getId";

	private static final String DEFAULT_SUB_LIST_GET_METHOD_PREFIX = "getList";
	private static final String DEFAULT_SUB_LIST_INSERT_METHOD_PREFIX = "insert";
	private static final String DEFAULT_SUB_LIST_DELETE_METHOD_PREFIX = "delete";

	@Autowired
	protected R repository;

	/*
	 * 
	 * Abstract methods
	 * 
	 */

	public abstract E mergeDtoIntoEntity(DTO dto, E entity);

	public abstract void validadeForInsertUpdate(DTO dto);

	/*
	 * 
	 * Getters and setters
	 * 
	 */

	public String getDefaultGetIdMethodName() {
		return DEFAULT_GET_ID_METHOD_NAME;
	}

	public String getDefaultSubListGetMethodPrefix() {
		return DEFAULT_SUB_LIST_GET_METHOD_PREFIX;
	}

	public String getDefaultSubListInsertMethodPrefix() {
		return DEFAULT_SUB_LIST_INSERT_METHOD_PREFIX;
	}

	public String getDefaultSubListDeleteMethodPrefix() {
		return DEFAULT_SUB_LIST_DELETE_METHOD_PREFIX;
	}

	public void validadeForInsertUpdate(E entity) {
		DTO dto = getDtoFromEntity(entity);
		validadeForInsertUpdate(dto);
	}

	/*
	 * 
	 * Generic type methods
	 * 
	 */

	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		Class<E> entityClass = (Class<E>) ReflectUtil.getParameterClassFromParameterizedClass(getClass(), 0);
		return entityClass;
	}

	public Constructor<E> getEntityContructor() {
		try {
			return getEntityClass().getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Class<DTO> getDtoClass() {
		Class<DTO> entityClass = (Class<DTO>) ReflectUtil.getParameterClassFromParameterizedClass(getClass(), 2);
		return entityClass;
	}

	public Constructor<DTO> getDtoContructor() {
		try {
			return getDtoClass().getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 
	 * Copy methods
	 * 
	 */

	public E getEntityFromDto(DTO dto) {
		try {
			E entity = dto.getId() == null ? getEntityContructor().newInstance() : find(dto.getId());
			return mergeDtoIntoEntity(dto, entity);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DTO getDtoFromEntity(E entity) {
		try {
			DTO dto = getDtoContructor().newInstance();
			dto.copyFromEntity(entity);
			return dto;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 
	 * Entity operations
	 * 
	 */

	protected E insert(E entity) {
		validadeForInsertUpdate(entity);
		entity.setId(null);
		return repository.save(entity);
	}

	protected E update(E entity) {
		validadeForInsertUpdate(entity);
		find(entity.getId());
		return repository.save(entity);
	}

	protected void delete(ID id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover");
		}
	}

	protected List<E> findAll() {
		return repository.findAll();
	}

	protected E find(ID id) {
		if (id == null) {
			throw new NotGivenIdException(getEntityClass());
		}
		Optional<E> entity = repository.findById(id);
		return entity.orElseThrow(() -> new ObjectNotFoundException((Integer) id, getEntityClass()));
	}

	@SuppressWarnings("unchecked")
	public <IE extends AbstractEntity<IID>, IID> List<IE> findAllSubEntityList(Class<IE> subEntityClass, ID id)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		List<Object> subObjectList = findAllSubObjectList(subEntityClass, id);

		List<IE> subEntityList = new ArrayList<>();
		subObjectList.forEach((x) -> subEntityList.add((IE) x));

		return subEntityList;
	}

	public <IS extends AbstractService<IE, IID, ?, ?>, IE extends AbstractEntity<IID>, IID> ResponseEntity<Void> insertSubEntityList(
			ID id, IID subEntityId, IS subService) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		return insertSubObjectList(id, subEntityId, subService);
	}

	public <IS extends AbstractService<IE, IID, ?, ?>, IE extends AbstractEntity<IID>, IID> ResponseEntity<Void> deleteSubEntityList(
			ID id, IID subEntityId, IS subService) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		return deleteSubObjectList(id, subEntityId, subService);
	}

	/*
	 * 
	 * DTO operations
	 * 
	 */

	public DTO insertDto(DTO dto) {
		E entity = getEntityFromDto(dto);
		entity = insert(entity);

		return getDtoFromEntity(entity);
	}

	public DTO updateDto(DTO dto) {
		E entity = getEntityFromDto(dto);
		entity = update(entity);

		return getDtoFromEntity(entity);
	}

	public void deleteDto(ID id) {
		delete(id);
	}

	public List<DTO> findAllDto() {
		List<E> listAll = findAll();

		List<DTO> listAllDto = listAll.stream().map(entity -> getDtoFromEntity(entity)).collect(Collectors.toList());
		return listAllDto;
	}

	public DTO findDto(ID id) {
		E entity = find(id);
		return getDtoFromEntity(entity);
	}

	public <IS extends AbstractService<?, ?, IDTO, ?>, IDTO extends AbstractDto<?, ?>> List<IDTO> findAllSubDtoList(
			Class<IS> subServiceClass, Class<IDTO> subDtoClass, ID id)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		Constructor<IS> subServiceConstructor = subServiceClass.getDeclaredConstructor();
		IS subService = subServiceConstructor.newInstance();
		Class<?> subEntityClass = subService.getEntityClass();

		List<Object> subEntityList = findAllSubObjectList(subEntityClass, id);

		List<IDTO> subDtoList = new ArrayList<>();
		Constructor<IDTO> idtoConstructor = subDtoClass.getDeclaredConstructor();

		for (Object subEntity : subEntityList) {
			IDTO subDto = idtoConstructor.newInstance();
			subDto.copyFromObject(subEntityClass.cast(subEntity));
			subDtoList.add(subDto);
		}

		return subDtoList;
	}

	public <IS extends AbstractService<?, IID, ?, ?>, IID> ResponseEntity<Void> insertSubDtoList(ID id, IID subEntityId,
			IS subService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		return insertSubObjectList(id, subEntityId, subService);
	}

	public <IS extends AbstractService<?, IID, ?, ?>, IID> ResponseEntity<Void> deleteSubDtoList(ID id, IID subEntityId,
			IS subService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		return deleteSubObjectList(id, subEntityId, subService);
	}

	/*
	 * 
	 * Auxiliar operations
	 * 
	 */

	@SuppressWarnings("unchecked")
	public List<Object> findAllSubObjectList(Class<?> subEntityClass, ID id)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		String subListMethodName = getDefaultSubListGetMethodPrefix() + subEntityClass.getSimpleName();

		E entity = find(id);
		Method subListMethod = entity.getClass().getMethod(subListMethodName);

		return (List<Object>) subListMethod.invoke(entity);
	}

	@SuppressWarnings("unchecked")
	public <IS extends AbstractService<?, IID, ?, ?>, IID> ResponseEntity<Void> insertSubObjectList(ID id,
			IID subEntityId, IS subService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> subEntityClass = subService.getEntityClass();
		List<Object> subEntityList = findAllSubObjectList(subEntityClass, id);

		boolean exists = false;
		for (Object subObject : subEntityList) {
			Object castSubObject = subEntityClass.cast(subObject);
			Method subGetId = castSubObject.getClass().getMethod(getDefaultGetIdMethodName());

			IID subObjectId = (IID) subGetId.invoke(castSubObject);

			if (subObjectId == subEntityId) {
				exists = true;
			}
		}

		if (!exists) {
			Object subObject = subService.find(subEntityId);

			E entity = find(id);

			String subListMethodName = getDefaultSubListInsertMethodPrefix() + subEntityClass.getSimpleName();
			Method subListMethod = entity.getClass().getMethod(subListMethodName, subEntityClass);
			subListMethod.invoke(entity, subObject);

			update(entity);
		}

		return ResponseEntity.noContent().build();
	}

	@SuppressWarnings("unchecked")
	public <IS extends AbstractService<?, IID, ?, ?>, IID> ResponseEntity<Void> deleteSubObjectList(ID id,
			IID subEntityId, IS subService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> subEntityClass = subService.getEntityClass();
		List<Object> subEntityList = findAllSubObjectList(subEntityClass, id);

		Integer subPosition = null;
		for (int i = 0; i < subEntityList.size(); i++) {

			Object castSubObject = subEntityClass.cast(subEntityList.get(i));
			Method subGetId = castSubObject.getClass().getMethod(getDefaultGetIdMethodName());

			IID subObjectId = (IID) subGetId.invoke(castSubObject);

			if (subObjectId == subEntityId) {
				subPosition = i;
			}
		}

		if (subPosition != null) {
			Object subObject = subService.find(subEntityId);

			E entity = find(id);

			String subListMethodName = getDefaultSubListDeleteMethodPrefix() + subEntityClass.getSimpleName();
			Method subListMethod = entity.getClass().getMethod(subListMethodName, subEntityClass);
			subListMethod.invoke(entity, subObject);

			update(entity);
		}

		return ResponseEntity.noContent().build();
	}

}
