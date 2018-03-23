package org.alltiny.math.vector;

import java.util.Arrays;

/**
 * This vector class represents vectors of any dimension.
 * It provides common vector operations.
 *
 * TODO use the same class from https://github.com/alltiny/alltiny-math-vector
 */
public class Vector {

	private final double[] values;

	public Vector(double... values) {
		this.values = values;
	}

	/** Copy-Constructor */
	public Vector(Vector vector) {
		this.values = vector.values;
	}

	/** Creates a 0-vector with the given dimension. */
	public Vector(final int dimension) {
		values = new double[dimension];
	}

	public double get(int index) {
		return values[index];
	}

	/**
	 * This method modifies the value of the given index.
	 */
	public Vector set(int index, double value) {
		values[index] = value;
		return this;
	}

	/**
	 * @return the dimension of this vector.
	 */
	public int getDimension() {
		return values.length;
	}

	/**
	 * This method calculates the square of the length of this vector.
	 * Hint: This method is a bit faster then {@link #getLength()}.
	 * @return the square of the length of this vector.
	 */
	public double getLengthSquare() {
		double result = 0;
		for (double element : values) {
			result += element * element;
		}
		return result;
	}

	/**
	 * This method calculates the length of this vector.
	 * @return length of this vector.
	 */
	public double getLength() {
		return Math.sqrt(getLengthSquare());
	}

	/**
	 * @return a vector pointing into the same direction like this vector but with length of 1 (called normalized vector)
	 * @since 1.0
	 */
	public Vector normalize() {
		final double length = getLength();
		Vector normalized = new Vector(getDimension());
		for (int i = 0; i < getDimension(); i++) {
			normalized.set(i, values[i] / length);
		}
		return normalized;
	}

	/**
	 * This method adds the given vector to this vector and returns the resulting vector.
	 * This method does not change this vector. Both vectors must have the same dimensions.
	 * @param other to add
	 * @return added vector of this vector and the given vector
	 * @throws IllegalDimensionException if this and the given vector have unequal dimensions.
	 * @since 1.0
	 */
	public Vector add(Vector other) {
		if (getDimension() != other.getDimension()) {
			throw new IllegalDimensionException("both vectors must have the same dimension");
		}
		Vector added = new Vector(getDimension());
		for (int i = 0; i < getDimension(); i++) {
			added.set(i, values[i] + other.get(i));
		}
		return added;
	}

	/**
	 * This method subtracts the given vector from this vector and returns the resulting vector.
	 * This method does not change this vector. Both vectors must have the same dimensions.
	 * @param other to sub from this one
	 * @return subtracted vector
	 * @throws IllegalDimensionException if this and the given vector have unequal dimensions.
	 * @since 1.0
	 */
	public Vector sub(Vector other) {
		if (getDimension() != other.getDimension()) {
			throw new IllegalDimensionException("both vectors must have the same dimension");
		}
		Vector sub = new Vector(getDimension());
		for (int i = 0; i < getDimension(); i++) {
			sub.set(i, values[i] - other.get(i));
		}
		return sub;
	}

	/**
	 * Multiplies this vector with the given scalar.
	 * @param scalar to multiply this vector with.
	 * @return the scaled vector
	 * @see #scale(double)
	 * @since 1.0
	 */
	public Vector mul(double scalar) {
		Vector vector = new Vector(getDimension());
		for (int i = 0; i < getDimension(); i++) {
			vector.set(i, values[i] * scalar);
		}
		return vector;
	}

	/**
	 * Scales this vector with the given scalar.
	 * @param scalar to multiply this vector with.
	 * @return the scaled vector
	 * @see #mul(double)
	 * @since 1.1
	 */
	public Vector scale(double scalar) {
		Vector vector = new Vector(getDimension());
		for (int i = 0; i < getDimension(); i++) {
			vector.set(i, values[i] * scalar);
		}
		return vector;
	}

	/**
	 * Calculates the scalar product. Both vectors must have the same dimensions.
	 * @param vector to create the scalar product with
	 * @return scalar product of this vector and the given vector
	 * @throws IllegalDimensionException if this and the given vector have unequal dimensions.
	 * @since 1.0
	 */
	public double scalar(Vector vector) {
		if (getDimension() != vector.getDimension()) {
			throw new IllegalDimensionException("both vectors must have the same dimension");
		}
		double scalar = 0;
		for (int i = 0; i < getDimension(); i++) {
			scalar += get(i) * vector.get(i);
		}
		return scalar;
	}

	/**
	 * This method calculates the cross product between this vector and the
	 * given vector and returns the resulting vector. This method will not
	 * change this vector. Note that the cross product is only defined for
	 * three dimensional vectors (resp. in R3).
	 * @param vector to create the cross product with
	 * @return scalar product of this vector and the given vector
	 * @throws IllegalDimensionException if this or the given vector has another dimension than 3.
	 * @since 1.0
	 */
	public Vector cross(Vector vector) {
		if (getDimension() != 3 || vector.getDimension() != 3) {
			throw new IllegalDimensionException("both vectors must have 3 dimensions");
		}
		return new Vector(
			get(1) * vector.get(2) - get(2) * vector.get(1),
			get(2) * vector.get(0) - get(0) * vector.get(2),
			get(0) * vector.get(1) - get(1) * vector.get(0)
		);
	}

	/**
	 * This method projects the other vector onto this vector.
	 * @return projection of the other vector onto this vector.
	 * @since 1.1
	 */
	public Vector project(Vector other) {
		Vector normal = normalize();
		return normal.scale(normal.scalar(other));
	}

	/**
	 * This method projects this vector onto the other.
	 * @return projection of this vector onto the other vector.
	 * @since 1.1
	 */
	public Vector projectOn(Vector other) {
		Vector normal = other.normalize();
		return normal.scale(normal.scalar(this));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Vector) {
			Vector v = (Vector)o;
			// compare the array of values; note that Arrays.equals can't do it, because it handles "-0" versus "0" wrong.
			if (values.length == v.values.length) {
				for (int i = 0; i < values.length; i++) {
					if (values[i] != v.values[i]) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (double value : values) {
			hash = 31 * hash + (int)Double.doubleToLongBits(value == 0.0 ? 0 : value); // treat -0.0 and 0.0 as 0
		}
		return hash;
	}

	@Override
	public String toString() {
		return "Vector" + Arrays.toString(values);
	}
}
