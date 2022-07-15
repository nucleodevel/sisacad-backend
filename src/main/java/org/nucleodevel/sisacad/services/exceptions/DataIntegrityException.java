package org.nucleodevel.sisacad.services.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 3386799489485682929L;

	public DataIntegrityException(String msg) {
		super(msg);
	}

	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
