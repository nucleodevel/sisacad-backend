package org.nucleodevel.sisacad.services.exceptions;

public class FieldValidationException extends RuntimeException {

	private static final long serialVersionUID = -1103990811718585957L;

	public FieldValidationException(String msg) {
		super(msg);
	}

	public FieldValidationException(Object entityId, Class<?> entityClass, String msg) {
		super(makeMsg(entityId, entityClass, msg));
	}

	public static String makeMsg(Object entityId, Class<?> entityClass, String msg) {
		return "Field validation - ID " + entityId + ", Tipo: " + entityClass.getSimpleName() + " -> " + msg;
	}

}
