package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that {@link Face} is working correctly.
 */
public class FaceTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddVertexRejectsNull() {
		new Face().addVertex(null);
	}

	@Test
	public void testFaceWithNoVertexHasNoEdges() {
		List<Edge> edges = new Face().getEdges();
		Assert.assertNotNull("edges should not be null", edges);
		Assert.assertTrue("edges should be empty", edges.isEmpty());
	}

	@Test
	public void testFaceWithOneVertexHasNoEdges() {
		List<Edge> edges = new Face().addVertex(new Vertex(new Vector(0, 0, 0))).getEdges();
		Assert.assertNotNull("edges should not be null", edges);
		Assert.assertTrue("edges should be empty", edges.isEmpty());
	}

	@Test
	public void testFaceWithTwoVerticesHasTwoEdges() {
		List<Edge> edges = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.getEdges();
		Assert.assertNotNull("edges should not be null", edges);
		Assert.assertEquals("number of edges should be", 2, edges.size());
	}

	@Test
	public void testFaceWithNoVertexHasNoArea() {
		Assert.assertEquals("face with no vertices has no area", 0, new Face().calcArea(), 0.000001);
	}

	@Test
	public void testFaceWithTwoVerticesHasNoArea() {
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)));
		Assert.assertEquals("face with two vertices has no area", 0, face.calcArea(), 0.000001);
	}

	@Test
	public void testFaceWithNoArea() {
		// create a face with no area.
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 1, 0)))
			.addVertex(new Vertex(new Vector(0, 0, 0)));
		Assert.assertEquals("this face has no area", 0, face.calcArea(), 0.000001);
	}

	@Test
	public void testAreaOfTriangle() {
		// create a face with no area.
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 1, 0)));
		Assert.assertEquals("number of edges should be", 0.5, face.calcArea(), 0.000001);
	}

	@Test
	public void testAreaOfConvexFace() {
		// create a face with no area.
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 1, 0)))
			.addVertex(new Vertex(new Vector(-1, 1, 0)))
			.addVertex(new Vertex(new Vector(-1, -1, 0)))
			.addVertex(new Vertex(new Vector(0, -1, 0)));
		Assert.assertEquals("number of edges should be", 3, face.calcArea(), 0.000001);
	}

}
