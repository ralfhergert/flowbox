package de.ralfhergert.flowbox.worker;

import de.ralfhergert.flowbox.model.Frame;
import de.ralfhergert.flowbox.model.Particle;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This worker clusters the particles of a frame.
 */
public class FrameClusterer {

	public Frame clusterFrame(Frame frame) {
		if (frame == null) {
			throw new IllegalArgumentException("frame can not be null");
		}
		final List<Particle> particles = frame.getParticles();
		// initialize each particle with the simulation outline.
		for (Particle particle : particles) {
			particle.setOutline(AffiliatedMeshCreator.createAffiliatedMesh(frame.getSimulation().getOutline()));
		}
		// cross-check all particles whether their meshes intersect.
		for (int i = 0; i < particles.size(); i++) {
			final Particle currentParticle = particles.get(i);
			for (int j = i + 1; j < particles.size(); j++) {
				final Particle otherParticle = particles.get(j);
				final Vector halfWay = otherParticle.getPosition().getPosition().sub(currentParticle.getPosition().getPosition()).scale(0.5);
				final Set<Vertex> offsideVertices = getAllOffsideVertices(currentParticle, halfWay);
				if (!offsideVertices.isEmpty()) {
					// cut the offsideVertices off the mesh.
				}
			}
		}
		frame.setClustered(true);
		return frame;
	}

	public Set<Vertex> getAllOffsideVertices(Particle particle, Vector offside) {
		final Set<Vertex> vertices = new HashSet<>();
		final double offsideLength = offside.getLength();
		for (Vertex vertex : particle.getOutline().getVertices()) {
			final Vector toVertex = vertex.getPosition().sub(particle.getPosition().getPosition());
			final double scalar = toVertex.scalar(offside) / offsideLength;
			if (scalar > offsideLength) {
				vertices.add(vertex);
			}
		}
		return vertices;
	}
}
