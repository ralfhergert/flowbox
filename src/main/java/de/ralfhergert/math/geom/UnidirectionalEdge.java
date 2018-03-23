package de.ralfhergert.math.geom;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * An unidirectional edge is an edge with no direction.
 * It is meant to be a helper class used in {@link Mesh}.
 */
public class UnidirectionalEdge {

	// with using a HashSet the order of vertices becomes irrelevant.
	private Set<Vertex> vertices = new HashSet<>();

	public UnidirectionalEdge(Edge edge) {
		if (edge == null) {
			throw new IllegalArgumentException("edge can not be null");
		}
		this.vertices.addAll(edge.getVertices());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UnidirectionalEdge that = (UnidirectionalEdge)o;
		return Objects.equals(vertices, that.vertices);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vertices);
	}
}
