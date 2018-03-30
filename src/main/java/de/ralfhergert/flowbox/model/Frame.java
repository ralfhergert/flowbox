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
}
