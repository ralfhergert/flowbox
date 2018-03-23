package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

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
}
