package org.nucleodevel.sisacad.services.exceptions;

public class FieldValidationException extends RuntimeException {

	private static final long serialVersionUID = -1103990811718585957L;

	private Object entityId;
	private Class<?> entityClass;
	private String msg;

	public FieldValidationException(String msg) {
		super(msg);
	}

	public FieldValidationException(Object entityId, Class<?> entityClass, String msg) {
		super(msg);

		this.entityId = entityId;
		this.entityClass = entityClass;
		this.msg = msg;
	}

	public Object getEntityId() {
		return entityId;
	}

	public void setEntityId(Object entityId) {
		this.entityId = entityId;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
