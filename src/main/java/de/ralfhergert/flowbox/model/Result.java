package de.ralfhergert.flowbox.model;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Result result = (Result)o;
		return isError == result.isError && Objects.equals(message, result.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isError, message);
	}
}
