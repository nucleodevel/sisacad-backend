package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.AbstractEntity;
import org.nucleodevel.sisacad.dto.AbstractDto;
import org.nucleodevel.sisacad.repositories.AbstractRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.NotGivenIdException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.nucleodevel.sisacad.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

public abstract class AbstractService<E extends AbstractEntity<ID>, ID, DTO extends AbstractDto<E, ID>, R extends AbstractRepository<E, ID>> {

	@Autowired
	protected R repo;

	public abstract void validadeForInsertUpdate(E entity);

	public abstract void validadeForInsertUpdate(DTO dto);

	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		Class<E> entityClass = (Class<E>) ReflectUtil.getParameterClassFromParameterizedClass(getClass(), 0);
		return entityClass;
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

}
