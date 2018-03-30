package de.ralfhergert.flowbox.initializer;

import de.ralfhergert.flowbox.model.Result;
import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.math.geom.Face;
import de.ralfhergert.math.geom.Mesh;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensure that {@link AddParticle} is working correctly.
 */
public class AddParticleTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddParticleRejectsZeroMass() {
		new AddParticle(0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddParticleRejectsNegativeTemperature() {
		new AddParticle(1, -1);
	}

	@Test
	public void testApplyingToNoSimulation() {
		Result result = new AddParticle(1, 0).applyTo(null);
		Assert.assertFalse("result should not be a success", result.isSuccess());
		Assert.assertEquals("result should be", AddParticle.NoSimulationGiven.class, result.getClass());
	}

	@Test
	public void testApplyingToEmptySimulation() {
		Result result = new AddParticle(1, 0).applyTo(new Simulation());
		Assert.assertFalse("result should not be a success", result.isSuccess());
		Assert.assertEquals("result should be", AddParticle.NoSimulationOutlineGiven.class, result.getClass());
	}

	@Test
	public void testApplyingToPermeableSimulationOutline() {
		// create a simulation with an incomplete outline.
		final Simulation simulation = new Simulation().setOutline(new Mesh()
			.addFace(new Face()
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))));
		Result result = new AddParticle(1, 0).applyTo(simulation);
		Assert.assertFalse("result should not be a success", result.isSuccess());
		Assert.assertEquals("result should be", AddParticle.SimulationOutlinePermeable.class, result.getClass());
	}
}
