package org.nucleodevel.sisacad.services.exceptions;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1302531436989055900L;

	private static final String DEFAULT_MSG = "Acesso não permitido";

	public ForbiddenException() {
		super(DEFAULT_MSG);
	}

	public ForbiddenException(String msg) {
		super(msg);
	}

}
