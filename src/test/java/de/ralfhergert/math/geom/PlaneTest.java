package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensure that {@link Plane} is working correctly.
 */
public class PlaneTest {

	@Test(expected = IllegalArgumentException.class)
	public void testPlaneRejectsNullPosition() {
		new Plane(null, new Vector(1, 0, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPlaneRejectsNullNormal() {
		new Plane(new Vertex(new Vector(3)), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPlaneRejectsZeroLengthNormalVector() {
		new Plane(new Vertex(new Vector(3)), new Vector(3));
	}

	@Test
	public void testCreatePlane() {
		final Plane plane = new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0));
		Assert.assertEquals("position should be", new Vector(0, 0, 0), plane.getPosition().getPosition());
		Assert.assertEquals("normal should be", new Vector(1, 0, 0), plane.getNormal());
	}

	@Test
	public void testVertexLocationOfNullVertexIsNull() {
		Assert.assertNull("location of null should be null", new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0)).calcVertexLocation(null));
	}

	@Test
	public void testCalcVertexLocationOutOfBounds() {
		final Plane plane = new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0));
		final Vertex vertex = new Vertex(new Vector(-1, 0, 0));
		Assert.assertEquals("location should be", VertexLocation.OutBounds, plane.calcVertexLocation(vertex));
	}

	@Test
	public void testCalcVertexLocationOnBounds() {
		final Plane plane = new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0));
		final Vertex vertex = new Vertex(new Vector(0, 0, 0));
		Assert.assertEquals("location should be", VertexLocation.OnBounds, plane.calcVertexLocation(vertex));
	}

	@Test
	public void testCalcVertexLocationInBounds() {
		final Plane plane = new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0));
		final Vertex vertex = new Vertex(new Vector(1, 0, 0));
		Assert.assertEquals("location should be", VertexLocation.InBounds, plane.calcVertexLocation(vertex));
	}

	@Test
	public void testIntersectionWithNullEdgeResultsInNull() {
		Assert.assertNull("for null edge null should be returned", new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0)).intersect(null));
	}

	@Test
	public void testIntersectionWithOutOfBoundsEdge1() {
		final Plane plane = new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0));
		final Edge edge = new Edge(new Vertex(new Vector(-2, 0, 0)), new Vertex(new Vector(-1, 0, 0)));
		Assert.assertNull("vertex should be null because edge does not intersect with plane", plane.intersect(edge));
	}

	@Test
	public void testIntersectionWithOutOfBoundsEdge2() {
		final Plane plane = new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0));
		final Edge edge = new Edge(new Vertex(new Vector(1, 0, 0)), new Vertex(new Vector(2, 0, 0)));
		Assert.assertNull("vertex should be null because edge does not intersect with plane", plane.intersect(edge));
	}

	@Test
	public void testIntersectionWithEdge() {
		final Plane plane = new Plane(new Vertex(new Vector(3)), new Vector(1, 0, 0));
		final Edge edge = new Edge(new Vertex(new Vector(-1, 0, 1)), new Vertex(new Vector(2, 0, 1)));
		Assert.assertEquals("vertex position should be", new Vector(0, 0, 1), plane.intersect(edge).getPosition());
	}
}
