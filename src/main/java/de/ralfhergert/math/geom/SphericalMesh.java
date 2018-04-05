package de.ralfhergert.math.geom;

import java.util.ArrayList;
import java.util.List;

/**
 * This helps projecting a complete mesh onto a sphere.
 */
public class SphericalMesh {

	private final List<SphericalFace> triangles = new ArrayList<>();

	public SphericalMesh addTriangle(SphericalFace triangle) {
		triangles.add(triangle);
		return this;
	}

	public double calcArea() {
		double area = 0;
		for (SphericalFace face : triangles) {
			/* use the area vector to see whether the face shows its
			 * front or backside. */
			final double scalar = face.calcAreaVector().scalar(face.getVertices().get(0).getPosition());
			if (-0.000001 < scalar && scalar < 0.000001) {
				continue;
			}
			final int factor = (scalar < 0) ? -1 : 1;
			final double faceArea = face.calcArea();
			area += factor * faceArea;
		}
		return area;
	}
}
