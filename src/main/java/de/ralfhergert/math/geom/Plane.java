package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;

/***
 * This plane is defined by a vertex position and a normal vector.
 * A plane is indefinitely wide.
 */
public class Plane {

	private final Vertex position;
	private final Vector normal;

	public Plane(Vertex position, Vector normal) {
		if (position == null) {
			throw new IllegalArgumentException("position can not be null");
		}
		if (normal == null) {
			throw new IllegalArgumentException("normal can not be null");
		}
		if (normal.getLength() == 0) {
			throw new IllegalArgumentException("normal can not be of zero length");
		}
		this.position = position;
		this.normal = normal;
	}

	public Vertex getPosition() {
		return position;
	}

	public Vector getNormal() {
		return normal;
	}

	public VertexLocation calcVertexLocation(Vertex vertex) {
		if (vertex == null) {
			return null;
		}
		final Vector toVertex = vertex.getPosition().sub(position.getPosition());
		final double scalar = toVertex.scalar(normal);
		if (scalar > 0.000001) {
			return VertexLocation.InBounds;
		} else if (scalar < -0.000001) {
			return VertexLocation.OutBounds;
		} else {
			return VertexLocation.OnBounds;
		}
	}

	public Vertex intersect(Edge<?> edge) {
		if (edge == null) {
			return null;
		}
		Vertex v1 = edge.getVertices().get(0);
		Vertex v2 = edge.getVertices().get(1);
		Vector normalizedNormal = normal.normalize();
		final double v1scale = v1.getPosition().sub(position.getPosition()).scalar(normalizedNormal);
		final double v2scale = v2.getPosition().sub(position.getPosition()).scalar(normalizedNormal);
		final double ratio = v1scale / (v1scale - v2scale);
		if (ratio >= 0 && ratio <= 1) {
			return new Vertex(v2.getPosition().sub(v1.getPosition()).scale(ratio).add(v1.getPosition()));
		} else {
			return null;
		}
	}
}
