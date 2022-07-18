package org.nucleodevel.sisacad.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.NotGivenIdException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> handleObjectNotFoundException(ObjectNotFoundException e,
			HttpServletRequest request) {

		StandardError erro = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);

	}

	@ExceptionHandler(NotGivenIdException.class)
	public ResponseEntity<StandardError> handleNotGivenIdException(NotGivenIdException e, HttpServletRequest request) {

		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

	@ExceptionHandler(FieldValidationException.class)
	public ResponseEntity<StandardError> handleFieldValidationException(FieldValidationException e,
			HttpServletRequest request) {

		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> handleDataIntegrityException(DataIntegrityException e,
			HttpServletRequest request) {

		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

}
