package de.ralfhergert.flowbox.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A time-frame stores all momentary information for a particular time.
 */
public class Frame {

	private final long timestampNs;
	private final List<Particle> particles = new ArrayList<>();

	private Simulation simulation;
	private boolean isClustered = false;

	public Frame(long timestampNs) {
		this.timestampNs = timestampNs;
	}

	public long getTimestampNs() {
		return timestampNs;
	}

	public List<Particle> getParticles() {
		return Collections.unmodifiableList(particles);
	}

	public Frame addParticle(Particle particle) {
		particles.add(particle);
		return this;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}

	public boolean isClustered() {
		return isClustered;
	}

	public Frame setClustered(boolean clustered) {
		isClustered = clustered;
		return this;
	}
}
