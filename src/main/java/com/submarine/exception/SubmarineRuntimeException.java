package com.submarine.exception;

public class SubmarineRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public SubmarineRuntimeException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
