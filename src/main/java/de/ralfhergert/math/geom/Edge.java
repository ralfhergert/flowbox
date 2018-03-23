package de.ralfhergert.math.geom;

import java.util.Arrays;
import java.util.Collection;

/**
 * An edge is leading from one vertex to another.
 * An edge is directional.
 */
public class Edge {

	private Vertex[] vertices = new Vertex[2];

	public Edge(Vertex vertex1, Vertex vertex2) {
		if (vertex1 == null) {
			throw new IllegalArgumentException("vertex1 can not be null");
		}
		if (vertex2 == null) {
			throw new IllegalArgumentException("vertex2 can not be null");
		}
		vertices[0] = vertex1;
		vertices[1] = vertex2;
	}

	public Collection<Vertex> getVertices() {
		return Arrays.asList(vertices);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Edge edge = (Edge)o;
		return Arrays.equals(vertices, edge.vertices);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(vertices);
	}

	@Override
	public String toString() {
		return "Edge" + Arrays.toString(vertices);
	}
}
