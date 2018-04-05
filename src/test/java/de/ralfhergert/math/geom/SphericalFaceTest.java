package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link SphericalFace} is working correctly.
 */
public class SphericalFaceTest {

	/**
	 * Calculate the area for an area with all angles 90°.
	 * Area should be 1/8 of a sphere.
	 */
	@Test
	public void testCalcAreaAllAngles90() {
		final SphericalFace face = (SphericalFace)new SphericalFace()
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 1, 0)))
			.addVertex(new Vertex(new Vector(0, 0, 1)));
		Assert.assertEquals("area should be 1/8 of a full sphere", 0.5 * Math.PI, face.calcArea(), 0.000001);
	}

	/**
	 * In this test a square at z=0 is used.
	 * The spherical area should equal half a sphere.
	 */
	@Test
	public void testCalcAreaAllAngles180() {
		final SphericalFace face = (SphericalFace)new SphericalFace()
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 1, 0)))
			.addVertex(new Vertex(new Vector(-1, 0, 0)))
			.addVertex(new Vertex(new Vector(0, -1, 0)));
		Assert.assertEquals("area should be half a sphere", 2 * Math.PI, face.calcArea(), 0.000001);
	}
}
