package org.nucleodevel.sisacad.dto;

import java.io.Serializable;

import org.nucleodevel.sisacad.domain.AbstractEntity;

public abstract class AbstractDto<E extends AbstractEntity<ID>, ID> implements Serializable {

	private static final long serialVersionUID = 2908007482627482921L;

	protected ID id;

	public AbstractDto() {

	}

	public abstract void copyFromEntity(E entity);

	@SuppressWarnings("unchecked")
	public void copyFromObject(Object entity) {
		copyFromEntity((E) entity);
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

}
