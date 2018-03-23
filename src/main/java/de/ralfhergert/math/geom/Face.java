package de.ralfhergert.math.geom;

import java.util.*;

/**
 * A face defined by multiple vertices.
 */
public class Face {

	private List<Vertex> vertices = new ArrayList<>();
	private Map<String,Object> properties = new HashMap<>();

	public Face addVertex(Vertex vertex) {
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
}
