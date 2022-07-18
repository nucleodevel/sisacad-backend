package org.nucleodevel.sisacad.services.exceptions;

public class NotGivenIdException extends RuntimeException {

	private static final long serialVersionUID = -1103990811718585957L;

	public NotGivenIdException(String msg) {
		super(msg);
	}

	public NotGivenIdException(Class<?> entityClass) {
		super(makeMsg(entityClass));
	}

	public static String makeMsg(Class<?> entityClass) {
		return "ID não fornecido. Tipo: " + entityClass.getSimpleName();
	}

}
