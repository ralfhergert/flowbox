package de.ralfhergert.math.geom;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * An edge is leading from one vertex to another.
 * An edge is directional.
 */
public class Edge<Parent> {

	private Vertex[] vertices = new Vertex[2];
	private Parent parent;

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

	public List<Vertex> getVertices() {
		return Arrays.asList(vertices);
	}

	public Parent getParent() {
		return parent;
	}

	public Edge<Parent> setParent(Parent parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Edge<?> edge = (Edge<?>) o;
		return Arrays.equals(vertices, edge.vertices) &&
			Objects.equals(parent, edge.parent);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(parent);
		result = 31 * result + Arrays.hashCode(vertices);
		return result;
	}

	@Override
	public String toString() {
		return "Edge{" + Arrays.toString(vertices) + ",parent=" + parent + "}";
	}
}
