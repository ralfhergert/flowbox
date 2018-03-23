package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;

import java.util.Objects;

/**
 * A vertex defined by a position vector.
 */
public class Vertex {

	private Vector position;

	public Vertex(Vector position) {
		if (position == null) {
			throw new IllegalArgumentException("position can not be null");
		}
		this.position = position;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		if (position == null) {
			throw new IllegalArgumentException("position can not be null");
		}
		this.position = position;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Vertex vertex = (Vertex)o;
		return Objects.equals(position, vertex.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(position);
	}

	@Override
	public String toString() {
		return "Vertex{position=" + position + '}';
	}
}
