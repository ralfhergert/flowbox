package de.ralfhergert.flowbox.model;

/**
 * This class is the super type of all operational results.
 * They may be successful or error result.
 */
public class Result {

	private final boolean isError;
	private final String message;

	public Result(boolean isError, String message) {
		this.isError = isError;
		this.message = message;
	}
}
