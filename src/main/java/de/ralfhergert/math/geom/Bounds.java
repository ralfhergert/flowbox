package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;

/**
 * Carries the min and max boundaries.
 */
public class Bounds {

	private Vector min;
	private Vector max;

	public Vector getMin() {
		return min;
	}

	public Bounds setMin(Vector min) {
		this.min = min;
		return this;
	}

	public Vector getMax() {
		return max;
	}

	public Bounds setMax(Vector max) {
		this.max = max;
		return this;
	}

	public int getDimension() {
		return min.getDimension();
	}

	public Bounds union(Bounds bounds) {
		for (int i = 0; i < min.getDimension(); i++) {
			min.set(i, Math.min(min.get(i), bounds.getMin().get(i)));
			max.set(i, Math.max(max.get(i), bounds.getMax().get(i)));
		}
		return this;
	}
}
