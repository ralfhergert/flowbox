package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link Edge} is working correctly.
 */
public class EdgeTest {

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeRejectsVertex1ToBeNull() {
		new Edge(null, new Vertex(new Vector(1, 2, 3)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEdgeRejectsVertex2ToBeNull() {
		new Edge(new Vertex(new Vector(1, 2, 3)), null);
	}

	@Test
	public void testEdgeEquals() {
		Edge e1 = new Edge(new Vertex(new Vector(1, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Edge e2 = new Edge(new Vertex(new Vector(1, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Assert.assertEquals("both edges should equal", e1, e2);
		Assert.assertTrue("both edges should equal", e1.equals(e2));
	}

	@Test
	public void testEdgeEqualsItself() {
		Edge e = new Edge(new Vertex(new Vector(1, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Assert.assertTrue("edges should equal itself", e.equals(e));
	}

	@Test
	public void testEdgeNotEqualsNull() {
		Edge e = new Edge(new Vertex(new Vector(1, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Assert.assertFalse("edges should not equal null", e.equals(null));
	}

	@Test
	public void testEdgeNotEqualsOtherClass() {
		Edge e = new Edge(new Vertex(new Vector(1, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Assert.assertFalse("edges should not equal other class", e.equals(""));
	}

	@Test
	public void testEdgeEqualsDespitePositiveAndNegativeZero() {
		Edge e1 = new Edge(new Vertex(new Vector(+0.0, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Edge e2 = new Edge(new Vertex(new Vector(-0.0, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Assert.assertEquals("both edges should equal", e1, e2);
		Assert.assertTrue("both edges should equal", e1.equals(e2));
	}

	@Test
	public void testEdgesWithDifferentParentsDoNotEqual() {
		Edge<String> e1 = new Edge<String>(new Vertex(new Vector(1, 2, 3)), new Vertex(new Vector(5, 6, 7))).setParent("foo");
		Edge e2 = new Edge(new Vertex(new Vector(1, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Assert.assertEquals("parent of e1 is", "foo", e1.getParent());
		Assert.assertEquals("parent of e2 is", null, e2.getParent());
		Assert.assertNotEquals("both edges should not equal", e1, e2);
	}

	@Test
	public void testEdgeHashCodeEqualsDespitePositiveAndNegativeZero() {
		Edge e1 = new Edge(new Vertex(new Vector(+0.0, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Edge e2 = new Edge(new Vertex(new Vector(-0.0, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Assert.assertEquals("hashCodes should equal", e1.hashCode(), e2.hashCode());
	}

	@Test
	public void testEdgeToString() {
		Edge e = new Edge(new Vertex(new Vector(1, 2, 3)), new Vertex(new Vector(5, 6, 7)));
		Assert.assertEquals("toString should be", "Edge{[Vertex{position=Vector[1.0, 2.0, 3.0]}, Vertex{position=Vector[5.0, 6.0, 7.0]}],parent=null}", e.toString());
	}
}
