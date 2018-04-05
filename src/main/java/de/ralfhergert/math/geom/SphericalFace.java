package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;

import java.util.List;

/**
 * A spherical triangle is a triangle formed on the surface of a sphere.
 *
 */
public class SphericalFace extends Face {

	@Override
	public double calcArea() {
		final double radius = 1;
		double sumOfRadianAngles = 0;
		final List<Vertex> vertices = getVertices();
		for (int i = 0; i < vertices.size(); i++) {
			final Vector currentPos = vertices.get(i).getPosition();
			final Vector prevPos = (i > 0 ? vertices.get(i - 1) : vertices.get(vertices.size() - 1)).getPosition();
			final Vector nextPos = (i < vertices.size() - 1 ? vertices.get(i + 1) : vertices.get(0)).getPosition();
			final Vector a = prevPos.sub(currentPos.project(prevPos));
			final Vector b = nextPos.sub(currentPos.project(nextPos));
			sumOfRadianAngles += a.angleBetween(b);
		}
		return radius * radius * (sumOfRadianAngles - Math.PI * (vertices.size() - 2));
	}
}
