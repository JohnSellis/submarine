package com.submarine.exception;

public class InvalidSubmarineNameException extends SubmarineRuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidSubmarineNameException(String message) {
		super(message);
	}

}
