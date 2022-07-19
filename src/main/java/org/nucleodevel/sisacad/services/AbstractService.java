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
	private static final String DEFAULT_ITEM_ENTITY_METHOD_PREFIX = "getList";

	@Autowired
	protected R repo;

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

	public String getDefaultItemEntityMethodPrefix() {
		return DEFAULT_ITEM_ENTITY_METHOD_PREFIX;
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
			E entity = getEntityContructor().newInstance();
			return mergeDtoIntoEntity(dto, entity);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
		return repo.save(entity);
	}

	protected E update(E entity) {
		validadeForInsertUpdate(entity);
		find(entity.getId());
		return repo.save(entity);
	}

	protected void delete(ID id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover");
		}
	}

	protected List<E> findAll() {
		return repo.findAll();
	}

	protected E find(ID id) {
		if (id == null) {
			throw new NotGivenIdException(getEntityClass());
		}
		Optional<E> entity = repo.findById(id);
		return entity.orElseThrow(() -> new ObjectNotFoundException((Integer) id, getEntityClass()));
	}

	@SuppressWarnings("unchecked")
	public <IE extends AbstractEntity<?>> List<IE> findAllItem(Class<IE> itemEntityClass, ID id)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		String itemListMethodName = getDefaultItemEntityMethodPrefix() + itemEntityClass.getSimpleName();

		E entity = find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);

		return (List<IE>) itemListMethod.invoke(entity);
	}

	@SuppressWarnings("unchecked")
	public <IS extends AbstractService<IE, IID, ?, ?>, IE extends AbstractEntity<IID>, IID> ResponseEntity<Void> insertItem(
			Class<IS> itemServiceClass, Class<IE> itemEntityClass, ID id, IID itemEntityId, IS itemService)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		String itemListMethodName = getDefaultItemEntityMethodPrefix() + itemEntityClass.getSimpleName();

		E entity = find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);
		List<IE> itemList = (List<IE>) itemListMethod.invoke(entity);

		boolean exists = false;
		for (IE item : itemList) {
			if (item.getId() == itemEntityId) {
				exists = true;
			}
		}

		if (!exists) {
			IE itemEntity = itemService.find(itemEntityId);
			itemList.add(itemEntity);

			update(entity);
		}

		return ResponseEntity.noContent().build();
	}

	@SuppressWarnings("unchecked")
	public <IS extends AbstractService<IE, IID, ?, ?>, IE extends AbstractEntity<IID>, IID> ResponseEntity<Void> deleteItem(
			Class<IS> itemServiceClass, Class<IE> itemEntityClass, ID id, IID itemEntityId, IS itemService)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		String itemListMethodName = getDefaultItemEntityMethodPrefix() + itemEntityClass.getSimpleName();

		E entity = find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);
		List<Object> itemList = (List<Object>) itemListMethod.invoke(entity);

		Integer itemPosition = null;
		for (int i = 0; i < itemList.size(); i++) {

			IE item = itemEntityClass.cast(itemList.get(i));
			if (item.getId() == itemEntityId) {
				itemPosition = i;
			}
		}

		if (itemPosition != null) {
			itemList.remove(itemPosition.intValue());
			update(entity);
		}

		return ResponseEntity.noContent().build();
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

	@SuppressWarnings("unchecked")
	public <IS extends AbstractService<?, ?, IDTO, ?>, IDTO extends AbstractDto<?, ?>> List<IDTO> findAllDtoItem(
			Class<IS> itemServiceClass, Class<IDTO> itemDtoClass, ID id)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		Constructor<IS> itemServiceConstructor = itemServiceClass.getDeclaredConstructor();
		IS itemService = itemServiceConstructor.newInstance();
		Class<?> itemEntityClass = itemService.getEntityClass();

		String itemListMethodName = getDefaultItemEntityMethodPrefix() + itemEntityClass.getSimpleName();

		E entity = find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);
		List<Object> itemList = (List<Object>) itemListMethod.invoke(entity);

		List<IDTO> idtoList = new ArrayList<>();
		Constructor<IDTO> idtoConstructor = itemDtoClass.getDeclaredConstructor();

		for (Object item : itemList) {
			IDTO itemDto = idtoConstructor.newInstance();
			itemDto.copyFromObject(itemEntityClass.cast(item));
			idtoList.add(itemDto);
		}

		return idtoList;
	}

	@SuppressWarnings("unchecked")
	public <IS extends AbstractService<?, IID, IDTO, ?>, IDTO extends AbstractDto<?, IID>, IID> ResponseEntity<Void> insertDtoItem(
			Class<IS> itemServiceClass, Class<IDTO> itemDtoClass, ID id, IID itemEntityId, IS itemService)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Class<?> itemEntityClass = itemService.getEntityClass();

		String itemListMethodName = getDefaultItemEntityMethodPrefix() + itemEntityClass.getSimpleName();

		E entity = find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);
		List<Object> itemList = (List<Object>) itemListMethod.invoke(entity);

		boolean exists = false;
		for (Object item : itemList) {
			Object castItem = itemEntityClass.cast(item);
			Method itemGetId = castItem.getClass().getMethod(getDefaultGetIdMethodName());

			IID itemId = (IID) itemGetId.invoke(castItem);

			if (itemId == itemEntityId) {
				exists = true;
			}
		}

		if (!exists) {
			Object itemEntity = itemService.find(itemEntityId);
			itemList.add(itemEntity);

			update(entity);
		}

		return ResponseEntity.noContent().build();
	}

	@SuppressWarnings("unchecked")
	public <IS extends AbstractService<?, IID, IDTO, ?>, IDTO extends AbstractDto<?, IID>, IID> ResponseEntity<Void> deleteDtoItem(
			Class<IS> itemServiceClass, Class<IDTO> itemDtoClass, ID id, IID itemEntityId, IS itemService)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Class<?> itemEntityClass = itemService.getEntityClass();

		String itemListMethodName = getDefaultItemEntityMethodPrefix() + itemEntityClass.getSimpleName();

		E entity = find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);
		List<Object> itemList = (List<Object>) itemListMethod.invoke(entity);

		Integer itemPosition = null;
		for (int i = 0; i < itemList.size(); i++) {

			Object castItem = itemEntityClass.cast(itemList.get(i));
			Method itemGetId = castItem.getClass().getMethod(getDefaultGetIdMethodName());

			IID itemId = (IID) itemGetId.invoke(castItem);

			if (itemId == itemEntityId) {
				itemPosition = i;
			}
		}

		if (itemPosition != null) {
			itemList.remove(itemPosition.intValue());
			update(entity);
		}

		return ResponseEntity.noContent().build();
	}

}
