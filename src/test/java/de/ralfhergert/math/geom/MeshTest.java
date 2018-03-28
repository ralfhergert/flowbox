package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * This test verifies that {@link Mesh} is working correctly.
 */
public class MeshTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddFaceRejectsNull() {
		new Mesh().addFace(null);
	}

	@Test
	public void testEmptyMeshIsPermeable() {
		Assert.assertFalse("an empty mesh is considered to be not impermeable", new Mesh().isImpermeable());
	}

	@Test
	public void testMeshWithSingleFaceIsPermeable() {
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(2, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 2, 0)));
		Assert.assertFalse("a mesh with a single face can not be permeable", new Mesh().addFace(face).isImpermeable());
	}

	@Test
	public void testMeshWithIncompleteFace() {
		// create a face with only two vertices. Such a faces has no area.
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)));
		Assert.assertFalse("mesh with a incomplete face can not be permeable", new Mesh().addFace(face).isImpermeable());
	}

	@Test
	public void testMeshOfACubeIsImpermeable() {
		Mesh cube = new Mesh()
			.addFace(new Face() // z=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0))))
			.addFace(new Face() // z=1
				.addVertex(new Vertex(new Vector(0, 0, 1)))
				.addVertex(new Vertex(new Vector(1, 0, 1)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 1, 1))))
			.addFace(new Face() // y=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 1)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 1, 1))))
			.addFace(new Face() // x=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(1, 0, 1))));
		Assert.assertTrue("a cube is a complete/leakless mesh and therefor impermeable", cube.isImpermeable());
	}

	@Test
	public void testGetOpenEdgesOnLeakingPyramid() {
		// build a pyramid with a missing bottom face.
		Mesh pyramid = new Mesh()
			.addFace(new Face() // x=-1
				.addVertex(new Vertex(new Vector(-1, -1, 0)))
				.addVertex(new Vertex(new Vector(-1,  1, 0)))
				.addVertex(new Vertex(new Vector( 0,  0, 2))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, -1, 0)))
				.addVertex(new Vertex(new Vector(1,  1, 0)))
				.addVertex(new Vertex(new Vector(0,  0, 2))))
			.addFace(new Face() // y=-1
				.addVertex(new Vertex(new Vector(-1, -1, 0)))
				.addVertex(new Vertex(new Vector( 1, -1, 0)))
				.addVertex(new Vertex(new Vector( 0,  0, 2))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(-1, 1, 0)))
				.addVertex(new Vertex(new Vector( 1, 1, 0)))
				.addVertex(new Vertex(new Vector( 0, 0, 2))));
		Set<Edge<Face>> openEdges = pyramid.getOpenEdges();
		Assert.assertNotNull("Set should not be null", openEdges);
		Assert.assertEquals("number of openEdges should be", 4, openEdges.size());
		for (Edge<Face> edge : openEdges) {
			Assert.assertEquals("all open edges should be on z=0", 0, edge.getVertices().get(0).getPosition().get(2), 0.000001);
			Assert.assertEquals("all open edges should be on z=0", 0, edge.getVertices().get(1).getPosition().get(2), 0.000001);
		}
		Assert.assertFalse("pyramid should be considered leaky", pyramid.isImpermeable());
	}

	@Test
	public void testFindAdjacentFacesOfCube() {
		Face bottom = new Face() // z=0
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 1, 0)))
			.addVertex(new Vertex(new Vector(0, 1, 0)));
		Face top = new Face() // z=1
			.addVertex(new Vertex(new Vector(0, 0, 1)))
			.addVertex(new Vertex(new Vector(1, 0, 1)))
			.addVertex(new Vertex(new Vector(1, 1, 1)))
			.addVertex(new Vertex(new Vector(0, 1, 1)));
		Mesh cube = new Mesh()
			.addFace(bottom)
			.addFace(top)
			.addFace(new Face() // y=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 1)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 1, 1))))
			.addFace(new Face() // x=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(1, 0, 1))));
		Set<Face> sides = cube.findAdjacentFaces(bottom);
		Assert.assertNotNull("set of faces should not be null", sides);
		Assert.assertEquals("number of found side faces", 4, sides.size());
		Assert.assertFalse("set should not contain bottom face", sides.contains(bottom));
		Assert.assertFalse("set should not contain top face", sides.contains(top));
	}
}
