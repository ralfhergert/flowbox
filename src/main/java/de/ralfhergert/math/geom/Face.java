package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import java.util.*;

/**
 * A face defined by multiple vertices.
 */
public class Face {

	private List<Vertex> vertices = new ArrayList<>();
	private Map<String,Object> properties = new HashMap<>();

	public Face addVertex(Vertex vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException("vertex can not be null");
		}
		vertices.add(vertex);
		return this;
	}

	public List<Vertex> getVertices() {
		return Collections.unmodifiableList(vertices);
	}

	public Face setProperty(final String name, Object value) {
		properties.put(name, value);
		return this;
	}

	public Object getProperty(final String name) {
		return properties.get(name);
	}

	public List<Edge> getEdges() {
		List<Edge> edges = new ArrayList<>();
		for (int i = 1; i < vertices.size(); i++) {
			edges.add(new Edge(vertices.get(i - 1), vertices.get(i)));
		}
		// final closing edge
		if (vertices.size() > 1) {
			edges.add(new Edge(vertices.get(vertices.size() - 1), vertices.get(0)));
		}
		return edges;
	}

	/**
	 * This method calculates the area of this face.
	 */
	public double calcArea() {
		if (vertices.size() > 1) {
			final Vector p0 = vertices.get(0).getPosition();
			Vector area = new Vector(p0.getDimension());
			for (int i = 2; i < vertices.size(); i++) {
				Vector p1 = vertices.get(i - 1).getPosition().sub(p0);
				Vector p2 = vertices.get(i).getPosition().sub(p0);
				area = area.add(p1.cross(p2).scale(0.5));
			}
			return area.getLength();
		}
		return 0;
	}
}
