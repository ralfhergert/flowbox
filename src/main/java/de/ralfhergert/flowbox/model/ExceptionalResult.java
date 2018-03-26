package de.ralfhergert.flowbox.model;

/**
 * This is an error result due to a exception.
 */
public class ExceptionalResult extends Result {

	private final Exception exception;

	public ExceptionalResult(String message, Exception exception) {
		this(true, message, exception);
	}

	public ExceptionalResult(boolean isError, String message, Exception exception) {
		super(isError, message);
		this.exception = exception;
	}
}
