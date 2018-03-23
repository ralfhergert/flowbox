package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link Vertex} is working correctly.
 */
public class VertexTest {

	@Test(expected = IllegalArgumentException.class)
	public void testVertexRejectsNullPosition() {
		new Vertex(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPositionRejectsNull() {
		new Vertex(new Vector(7,8,9)).setPosition(null);
	}

	@Test
	public void testAlteringPosition() {
		Vertex v = new Vertex(new Vector(7,8,9));
		Assert.assertEquals("position should be", new Vector(7,8,9), v.getPosition());
		v.setPosition(new Vector(5,6,7));
		Assert.assertEquals("position should be", new Vector(5,6,7), v.getPosition());
	}

	@Test
	public void testVertexEquals() {
		Vertex v1 = new Vertex(new Vector(1, 2, 3));
		Vertex v2 = new Vertex(new Vector(1, 2, 3));
		Assert.assertEquals("both vertices should equal", v1, v2);
		Assert.assertTrue("both vertices should equal", v1.equals(v2));
	}

	@Test
	public void testVerticesEqualsDespitePositiveAndNegativeZero() {
		Vertex v1 = new Vertex(new Vector(+0.0, 2, 3));
		Vertex v2 = new Vertex(new Vector(-0.0, 2, 3));
		Assert.assertEquals("both vertices should equal", v1, v2);
		Assert.assertTrue("both vertices should equal", v1.equals(v2));
	}

	@Test
	public void testVertexEqualsItself() {
		Vertex v = new Vertex(new Vector(1, 2, 3));
		Assert.assertTrue("vertex should equal itself", v.equals(v));
	}

	@Test
	public void testVertexNotEqualsNull() {
		Vertex v = new Vertex(new Vector(1, 2, 3));
		Assert.assertFalse("vertex should not equal other class", v.equals(null));
	}

	@Test
	public void testVertexNotEqualsOtherClass() {
		Vertex v = new Vertex(new Vector(1, 2, 3));
		Assert.assertFalse("vertex should not equal other class", v.equals(""));
	}

	@Test
	public void testVerticesHashCodeEqualsDespitePositiveAndNegativeZero() {
		Vertex v1 = new Vertex(new Vector(+0.0, 2, 3));
		Vertex v2 = new Vertex(new Vector(-0.0, 2, 3));
		Assert.assertEquals("both vertices hashCode should equal", v1.hashCode(), v2.hashCode());
	}

	@Test
	public void testToString() {
		Assert.assertEquals("both vertices should equal", "Vertex{position=Vector[4.0, 5.0, 6.0]}", new Vertex(new Vector(4, 5, 6)).toString());
	}
}
