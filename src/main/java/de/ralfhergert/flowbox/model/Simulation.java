package de.ralfhergert.flowbox.model;

import de.ralfhergert.math.geom.Mesh;

/**
 * Represents a single simulation.
 */
public class Simulation {

	private Mesh outline;

	public Mesh getOutline() {
		return outline;
	}

	public Simulation setOutline(Mesh outline) {
		this.outline = outline;
		return this;
	}
}
