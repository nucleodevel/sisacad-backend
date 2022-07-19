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

	@Autowired
	protected R repo;

	public abstract E mergeDtoIntoEntity(DTO dto, E entity);

	public abstract void validadeForInsertUpdate(DTO dto);

	public void validadeForInsertUpdate(E entity) {
		DTO dto = getDtoFromEntity(entity);
		validadeForInsertUpdate(dto);
	}

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

	public E find(ID id) {
		if (id == null) {
			throw new NotGivenIdException(getEntityClass());
		}
		Optional<E> entity = repo.findById(id);
		return entity.orElseThrow(() -> new ObjectNotFoundException((Integer) id, getEntityClass()));
	}

	public E insert(E entity) {
		validadeForInsertUpdate(entity);
		entity.setId(null);
		return repo.save(entity);
	}

	public E update(E entity) {
		validadeForInsertUpdate(entity);
		find(entity.getId());
		return repo.save(entity);
	}

	public void delete(ID id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover");
		}
	}

	public List<E> findAll() {
		return repo.findAll();
	}

	public DTO findDto(ID id) {
		E entity = find(id);
		return getDtoFromEntity(entity);
	}

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

	public List<DTO> findAllDto() {
		List<E> listAll = findAll();

		List<DTO> listAllDto = listAll.stream().map(entity -> getDtoFromEntity(entity)).collect(Collectors.toList());
		return listAllDto;
	}

	@SuppressWarnings("unchecked")
	public <IS extends AbstractService<?, ?, IDTO, ?>, IDTO extends AbstractDto<?, ?>> List<IDTO> findAllDtoItem(
			Class<IS> itemServiceClass, Class<IDTO> itemDtoClass, ID id, String itemListMethodName)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		Constructor<IS> itemServiceConstructor = itemServiceClass.getDeclaredConstructor();
		IS itemService = itemServiceConstructor.newInstance();
		Class<?> itemEntityClass = itemService.getEntityClass();

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
	public <IS extends AbstractService<?, ID, IDTO, ?>, IDTO extends AbstractDto<?, ID>> ResponseEntity<Void> insertDtoItem(
			Class<IS> itemServiceClass, Class<IDTO> itemDtoClass, ID id, String itemListMethodName, ID itemEntityId,
			IS itemService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> itemEntityClass = itemService.getEntityClass();

		E entity = find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);
		List<Object> itemList = (List<Object>) itemListMethod.invoke(entity);

		boolean exists = false;
		for (Object item : itemList) {
			Object castItem = itemEntityClass.cast(item);
			Method itemGetId = castItem.getClass().getMethod("getId");

			ID itemId = (ID) itemGetId.invoke(castItem);

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
	public <IS extends AbstractService<?, ID, IDTO, ?>, IDTO extends AbstractDto<?, ID>> ResponseEntity<Void> deleteDtoItem(
			Class<IS> itemServiceClass, Class<IDTO> itemDtoClass, ID id, String itemListMethodName, ID itemEntityId,
			IS itemService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> itemEntityClass = itemService.getEntityClass();

		E entity = find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);
		List<Object> itemList = (List<Object>) itemListMethod.invoke(entity);

		Integer itemPosition = null;
		for (int i = 0; i < itemList.size(); i++) {

			Object castItem = itemEntityClass.cast(itemList.get(i));
			Method itemGetId = castItem.getClass().getMethod("getId");

			ID itemId = (ID) itemGetId.invoke(castItem);

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
