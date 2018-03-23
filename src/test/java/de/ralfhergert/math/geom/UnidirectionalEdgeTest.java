package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link UnidirectionalEdge} is working correctly.
 */
public class UnidirectionalEdgeTest {

	@Test(expected = IllegalArgumentException.class)
	public void testUnidirectionalEdgeRejectsNull() {
		new UnidirectionalEdge(null);
	}

	@Test
	public void testEqualsOnUnidirectionalEdge() {
		Edge e1 = new Edge(new Vertex(new Vector(3, 4, 5)), new Vertex(new Vector(7, 8, 9)));
		Edge e2 = new Edge(new Vertex(new Vector(7, 8, 9)), new Vertex(new Vector(3, 4, 5)));
		Assert.assertNotEquals("both edges should not be equal", e1, e2);
		Assert.assertEquals("both edges should be equal when wrapping them with UnidirectionalEdge", new UnidirectionalEdge(e1), new UnidirectionalEdge(e2));
	}

	@Test
	public void testHashCodeOnUnidirectionalEdge() {
		Edge e1 = new Edge(new Vertex(new Vector(3, 4, 5)), new Vertex(new Vector(7, 8, 9)));
		Edge e2 = new Edge(new Vertex(new Vector(7, 8, 9)), new Vertex(new Vector(3, 4, 5)));
		Assert.assertEquals("both edges should have the same hashCode when being wrapped in UnidirectionalEdge", new UnidirectionalEdge(e1).hashCode(), new UnidirectionalEdge(e2).hashCode());
	}

	@Test
	public void testUnidirectionalEdgeEqualsItself() {
		UnidirectionalEdge e = new UnidirectionalEdge(new Edge(new Vertex(new Vector(3, 4, 5)), new Vertex(new Vector(7, 8, 9))));
		Assert.assertTrue("UnidirectionalEdge does not equal null", e.equals(e));
	}

	@Test
	public void testUnidirectionalEdgeNotEqualsNull() {
		Assert.assertFalse("UnidirectionalEdge does not equal null", new UnidirectionalEdge(new Edge(new Vertex(new Vector(3, 4, 5)), new Vertex(new Vector(7, 8, 9)))).equals(null));
	}

	@Test
	public void testUnidirectionalEdgeNotEqualsOtherClass() {
		Assert.assertFalse("UnidirectionalEdge does not equal other class", new UnidirectionalEdge(new Edge(new Vertex(new Vector(3, 4, 5)), new Vertex(new Vector(7, 8, 9)))).equals(""));
	}
}
