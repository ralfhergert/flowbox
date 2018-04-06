package de.ralfhergert.flowbox.initializer;

import de.ralfhergert.flowbox.model.Frame;
import de.ralfhergert.flowbox.model.Particle;
import de.ralfhergert.flowbox.model.Result;
import de.ralfhergert.flowbox.model.Simulation;
import de.ralfhergert.math.geom.*;
import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensure that {@link FillOutlineWithParticles} is working correctly.
 */
public class FillOutlineWithParticlesTest {

	@Test(expected = IllegalArgumentException.class)
	public void testFillOutlineWithParticlesRejectsZeroParticles() {
		new FillOutlineWithParticles(0, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFillOutlineWithParticlesRejectsNegativeDensity() {
		new FillOutlineWithParticles(1, -1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFillOutlineWithParticlesRejectsZeroGasConstant() {
		new FillOutlineWithParticles(1, 1, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFillOutlineWithParticlesRejectsNegativeTemperature() {
		new FillOutlineWithParticles(1, 1, 1, -1);
	}

	@Test
	public void testFillingNoSimulation() {
		Result result = new FillOutlineWithParticles(1, 0, 1, 0).applyTo(null);
		Assert.assertFalse("result should not be a success", result.isSuccess());
		Assert.assertEquals("result should be", FillOutlineWithParticles.NoSimulationGiven.class, result.getClass());
	}

	@Test
	public void testFillingEmptySimulation() {
		Result result = new FillOutlineWithParticles(1, 0, 1, 0).applyTo(new Simulation());
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
		Result result = new FillOutlineWithParticles(1, 0, 1, 0).applyTo(simulation);
		Assert.assertFalse("result should not be a success", result.isSuccess());
		Assert.assertEquals("result should be", FillOutlineWithParticles.SimulationOutlinePermeable.class, result.getClass());
	}

	@Test
	public void testCreatingRandomVectorForNullBounds() {
		Vector vector = FillOutlineWithParticles.createRandomVectorInBounds(null);
		Assert.assertNull("vector should be null", vector);
	}

	@Test
	public void testCreatingRandomOneDimensionalVector() {
		Vector vector = FillOutlineWithParticles.createRandomVectorInBounds(new Bounds()
			.setMin(new Vector(2.0))
			.setMax(new Vector(4.0)));
		Assert.assertNotNull("vector should not be null", vector);
		Assert.assertEquals("dimensions of vector", 1, vector.getDimension());
		Assert.assertTrue("value is within bounds", 2 <= vector.get(0) && vector.get(0) < 4);
	}

	@Test
	public void testCreatingRandomTwoDimensionalVector() {
		Vector vector = FillOutlineWithParticles.createRandomVectorInBounds(new Bounds()
			.setMin(new Vector(-1.0, -4.0))
			.setMax(new Vector(1.0, 4.0)));
		Assert.assertNotNull("vector should not be null", vector);
		Assert.assertEquals("dimensions of vector", 2, vector.getDimension());
		Assert.assertTrue("x value is within bounds", -1 <= vector.get(0) && vector.get(0) < 1);
		Assert.assertTrue("y value is within bounds", -4 <= vector.get(1) && vector.get(1) < 4);
	}

	@Test
	public void testFillingSimulationWithCubeOutlineWithNoFrames() {
		// create a simulation with a cube outline.
		final Simulation simulation = new Simulation().setOutline(MeshTest.createCube(1));
		new FillOutlineWithParticles(10, 1.2, 287, 293).applyTo(simulation);
		Assert.assertNotNull("simulation should now have frames", simulation.getFrames());
		Assert.assertEquals("simulation should now have one frame", 1, simulation.getFrames().size());
		final Frame frame = simulation.getFrames().get(0);
		Assert.assertNotNull("frame should not be null", frame);
		Assert.assertEquals("frame's timestamp should be", 0, frame.getTimestampNs());
		Assert.assertNotNull("frame should have a particle list", frame.getParticles());
		Assert.assertEquals("number of particles in frame", 10, frame.getParticles().size());
		for (Particle particle : frame.getParticles()) {
			Assert.assertNotNull("particle should not be null", particle);
			Assert.assertEquals("particle's mass should be", 0.12, particle.getMass(), 0.00001);
			Assert.assertEquals("particle's specificGasConstant should be", 287, particle.getSpecificGasConstant(), 0.00001);
			Assert.assertEquals("particle's temperature should be", 293, particle.getTemperature(), 0.00001);
			Assert.assertNotNull("particle's position should not be null", particle.getPosition());
			Assert.assertEquals("particle should be in bounds", VertexLocation.InBounds, simulation.getOutline().calcVertexLocation(particle.getPosition()));
			Assert.assertEquals("particle's velocity should be", new Vector(3), particle.getVelocity());
		}
	}

	@Test
	public void testFillingSimulationWithCubeOutlineWithOneFrame() {
		// create a simulation with a cube outline and one prepared frame.
		final Simulation simulation = new Simulation()
			.setOutline(MeshTest.createCube(1))
			.appendFrame(new Frame(1));
		Assert.assertNotNull("simulation should have frames", simulation.getFrames());
		Assert.assertEquals("simulation should now have one frame", 1, simulation.getFrames().size());
		new FillOutlineWithParticles(10, 1.2, 287, 293).applyTo(simulation);
		Assert.assertEquals("simulation should now have one frame", 1, simulation.getFrames().size());
		final Frame frame = simulation.getFrames().get(0);
		Assert.assertNotNull("frame should not be null", frame);
		Assert.assertEquals("frame's timestamp should be", 1, frame.getTimestampNs());
		Assert.assertNotNull("frame should have a particle list", frame.getParticles());
		Assert.assertEquals("number of particles in frame", 10, frame.getParticles().size());
	}
}
