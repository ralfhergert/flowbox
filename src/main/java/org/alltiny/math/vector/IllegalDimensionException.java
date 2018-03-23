package org.alltiny.math.vector;

/**
 * This exception will be thrown when a vector's or matrix's dimension did not match expectations.
 *
 * TODO use the same class from https://github.com/alltiny/alltiny-math-vector
 */
public class IllegalDimensionException extends IllegalArgumentException {

	public IllegalDimensionException(String s) {
		super(s);
	}
}
