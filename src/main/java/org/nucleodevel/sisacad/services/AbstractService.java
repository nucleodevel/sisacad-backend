package org.nucleodevel.sisacad.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.AbstractEntity;
import org.nucleodevel.sisacad.repositories.AbstractRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.NotGivenIdException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.nucleodevel.sisacad.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

public abstract class AbstractService<E extends AbstractEntity<ID>, ID, R extends AbstractRepository<E, ID>> {

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

	public abstract void validadeForInsertUpdate(E entity);

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

	/*
	 * 
	 * Entity operations
	 * 
	 */

	public E insert(E entity) {
		validadeForInsertUpdate(entity);
		entity.setId(null);
		return repository.save(entity);
	}

	public E update(E entity) {
		validadeForInsertUpdate(entity);
		find(entity.getId());
		return repository.save(entity);
	}

	public void delete(ID id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover");
		}
	}

	public List<E> findAll() {
		return repository.findAll();
	}

	public E find(ID id) {
		if (id == null) {
			throw new NotGivenIdException(getEntityClass());
		}
		Optional<E> entity = repository.findById(id);
		return entity.orElseThrow(() -> new ObjectNotFoundException((Integer) id, getEntityClass()));
	}

	@SuppressWarnings("unchecked")
	public <IE extends AbstractEntity<?>> List<IE> findAllSubList(Class<IE> subEntityClass, ID id)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		String subListMethodName = getDefaultSubListGetMethodPrefix() + subEntityClass.getSimpleName();

		E entity = find(id);
		Method subListMethod = entity.getClass().getMethod(subListMethodName);

		List<Object> subObjectList = (List<Object>) subListMethod.invoke(entity);

		List<IE> subEntityList = new ArrayList<>();
		subObjectList.forEach((x) -> subEntityList.add((IE) x));

		return subEntityList;
	}

	public <IS extends AbstractService<IE, IID, ?>, IE extends AbstractEntity<IID>, IID> ResponseEntity<Void> insertIntoSubList(
			ID id, IID subEntityId, IS subService, Class<IE> subEntityClass)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<IE> subEntityList = findAllSubList(subEntityClass, id);

		boolean exists = false;
		for (IE item : subEntityList) {
			Method subGetId = item.getClass().getMethod(getDefaultGetIdMethodName());

			@SuppressWarnings("unchecked")
			IID itemId = (IID) subGetId.invoke(item);

			if (itemId == subEntityId) {
				exists = true;
			}
		}

		if (!exists) {
			IE item = subService.find(subEntityId);

			E entity = find(id);

			String subListMethodName = getDefaultSubListInsertMethodPrefix() + subEntityClass.getSimpleName();
			Method subListMethod = entity.getClass().getMethod(subListMethodName, subEntityClass);
			subListMethod.invoke(entity, item);

			update(entity);
		}

		return ResponseEntity.noContent().build();
	}

	public <IS extends AbstractService<IE, IID, ?>, IE extends AbstractEntity<IID>, IID> ResponseEntity<Void> deleteFromSubList(
			ID id, IID subEntityId, IS subService, Class<IE> subEntityClass)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<IE> subEntityList = findAllSubList(subEntityClass, id);

		Integer subPosition = null;
		for (int i = 0; i < subEntityList.size(); i++) {

			IE item = subEntityList.get(i);
			Method itemGetId = item.getClass().getMethod(getDefaultGetIdMethodName());

			@SuppressWarnings("unchecked")
			IID itemId = (IID) itemGetId.invoke(item);

			if (itemId == subEntityId) {
				subPosition = i;
			}
		}

		if (subPosition != null) {
			IE item = subService.find(subEntityId);

			E entity = find(id);

			String subListMethodName = getDefaultSubListDeleteMethodPrefix() + subEntityClass.getSimpleName();
			Method subListMethod = entity.getClass().getMethod(subListMethodName, subEntityClass);
			subListMethod.invoke(entity, item);

			update(entity);
		}

		return ResponseEntity.noContent().build();
	}

}
