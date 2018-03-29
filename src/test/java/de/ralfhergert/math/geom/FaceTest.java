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
		List<Edge<Face>> edges = new Face().getEdges();
		Assert.assertNotNull("edges should not be null", edges);
		Assert.assertTrue("edges should be empty", edges.isEmpty());
	}

	@Test
	public void testFaceWithNoVertexHasNoNormal() {
		Assert.assertNull("face with no edges has no normal", new Face().getNormal());
	}

	@Test
	public void testFaceWithOneVertexHasNoEdges() {
		List<Edge<Face>> edges = new Face().addVertex(new Vertex(new Vector(0, 0, 0))).getEdges();
		Assert.assertNotNull("edges should not be null", edges);
		Assert.assertTrue("edges should be empty", edges.isEmpty());
	}

	@Test
	public void testFaceWithTwoVerticesHasTwoEdges() {
		List<Edge<Face>> edges = new Face()
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
	public void testCalculationOfAreaVectorOfFaceWithNoArea() {
		// create a face with no area.
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 1, 0)))
			.addVertex(new Vertex(new Vector(0, 0, 0)));
		Vector areaVector = face.calcAreaVector();
		Assert.assertNotNull("area vector should not be null", areaVector);
		Assert.assertEquals("area vector should have 0 length", 0, areaVector.getLength(), 0.000001);
		Assert.assertNull("normal vector does not exist", face.getNormal());
	}

	@Test
	public void testAreaOfTriangle() {
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 1, 0)));
		Assert.assertEquals("area of face should be", 0.5, face.calcArea(), 0.000001);
	}

	@Test
	public void testAreaOfConvexFace() {
		// create a convex face at z=0.
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 1, 0)))
			.addVertex(new Vertex(new Vector(-1, 1, 0)))
			.addVertex(new Vertex(new Vector(-1, -1, 0)))
			.addVertex(new Vertex(new Vector(0, -1, 0)));
		Assert.assertEquals("area of face should be", 3, face.calcArea(), 0.000001);
	}

	@Test
	public void testFlippingFaceNormal() {
		// create a face with no area.
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 1, 0)));
		final Vector normal1 = face.getNormal();
		Assert.assertNotNull("normal1 should not be null", normal1);
		Assert.assertTrue("normal1 should have a length greater 0", normal1.getLength() > 0);
		final Vector normal2 = face.flipNormal().getNormal();
		Assert.assertNotNull("normal2 should not be null", normal2);
		Assert.assertTrue("normal2 should have a length greater 0", normal2.getLength() > 0);
		Assert.assertEquals("both normal vectors should cancel each other out", 0, normal1.add(normal2).getLength(), 0.000001);
	}

	@Test
	public void testConsistentFaces() {
		// create two faces which touch at x=0 and have normals pointing into the same directions.
		Face face1 = new Face()
			.addVertex(new Vertex(new Vector(0,  1, 0)))
			.addVertex(new Vertex(new Vector(0, -1, 0)))
			.addVertex(new Vertex(new Vector(1,  0, 0)));
		Face face2 = new Face()
			.addVertex(new Vertex(new Vector( 0, -1, 0)))
			.addVertex(new Vertex(new Vector( 0,  1, 0)))
			.addVertex(new Vertex(new Vector(-1,  0, 0)));
		Assert.assertEquals("both faces should be consistent", Face.NormalConsistency.Consistent, face1.getNormalConsistentWith(face2));
	}

	@Test
	public void testInconsistentFaces() {
		// create two faces which touch at x=0 but have normals pointing into opposing directions.
		Face face1 = new Face()
			.addVertex(new Vertex(new Vector(0, -1, 0)))
			.addVertex(new Vertex(new Vector(0,  1, 0)))
			.addVertex(new Vertex(new Vector(1,  0, 0)));
		Face face2 = new Face()
			.addVertex(new Vertex(new Vector( 0, -1, 0)))
			.addVertex(new Vertex(new Vector( 0,  1, 0)))
			.addVertex(new Vertex(new Vector(-1,  0, 0)));
		Assert.assertEquals("both faces should be inconsistent", Face.NormalConsistency.Inconsistent, face1.getNormalConsistentWith(face2));
	}

	@Test
	public void testNormalConsistencyForNotConnectedFaces() {
		// create two faces which do not touch on their edges.
		Face face1 = new Face()
			.addVertex(new Vertex(new Vector(0, -1, 0)))
			.addVertex(new Vertex(new Vector(0,  1, 0)))
			.addVertex(new Vertex(new Vector(1,  0, 0)));
		Face face2 = new Face()
			.addVertex(new Vertex(new Vector( 0, -1, 0)))
			.addVertex(new Vertex(new Vector( 0,  1, 0.5)))
			.addVertex(new Vertex(new Vector(-1,  0, 0)));
		Assert.assertEquals("both faces should be not connected", Face.NormalConsistency.NotConnected, face1.getNormalConsistentWith(face2));
	}
}
