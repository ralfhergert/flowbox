package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;

/**
 * A vertex defined by a position vector.
 */
public class Vertex {

	private Vector position;

	public Vertex(Vector position) {
		this.position = position;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}
}
