package org.nucleodevel.sisacad.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1103990811718585957L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}

	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ObjectNotFoundException(Object entityId, Class<?> entityClass) {
		super(makeMsg(entityId, entityClass));
	}

	public ObjectNotFoundException(Object entityId, Class<?> entityClass, Throwable cause) {
		super(makeMsg(entityId, entityClass), cause);
	}

	public static String makeMsg(Object entityId, Class<?> entityClass) {
		return "Objeto não encontrado. ID " + entityId + ", Tipo: " + entityClass.getSimpleName();
	}

}
