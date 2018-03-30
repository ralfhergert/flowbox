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
 * This test ensure that {@link FillOutlineWithParticles} is working correctly.
 */
public class FillOutlineWithParticlesTest {

	@Test(expected = IllegalArgumentException.class)
	public void testFillOutlineWithParticlesRejectsZeroParticles() {
		new FillOutlineWithParticles(0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFillOutlineWithParticlesRejectsNegativeDensity() {
		new FillOutlineWithParticles(1, -1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFillOutlineWithParticlesRejectsNegativeTemperature() {
		new FillOutlineWithParticles(1, 1, -1);
	}

	@Test
	public void testFillingNoSimulation() {
		Result result = new FillOutlineWithParticles(1, 0, 0).applyTo(null);
		Assert.assertFalse("result should not be a success", result.isSuccess());
		Assert.assertEquals("result should be", FillOutlineWithParticles.NoSimulationGiven.class, result.getClass());
	}

	@Test
	public void testFillingEmptySimulation() {
		Result result = new FillOutlineWithParticles(1, 0, 0).applyTo(new Simulation());
		Assert.assertFalse("result should not be a success", result.isSuccess());
		Assert.assertEquals("result should be", FillOutlineWithParticles.NoSimulationOutlineGiven.class, result.getClass());
	}

	@Test
	public void testFillingPermeableSimulationOutline() {
		// create a simulation with an incomplete outline.
		final Simulation simulation = new Simulation().setOutline(new Mesh()
			.addFace(new Face()
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))));
		Result result = new FillOutlineWithParticles(1, 0, 0).applyTo(simulation);
		Assert.assertFalse("result should not be a success", result.isSuccess());
		Assert.assertEquals("result should be", FillOutlineWithParticles.SimulationOutlinePermeable.class, result.getClass());
	}
}
