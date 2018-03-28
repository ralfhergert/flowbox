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

	public List<Edge<Face>> getEdges() {
		List<Edge<Face>> edges = new ArrayList<>();
		for (int i = 1; i < vertices.size(); i++) {
			edges.add(new Edge<Face>(vertices.get(i - 1), vertices.get(i)).setParent(this));
		}
		// final closing edge
		if (vertices.size() > 1) {
			edges.add(new Edge<Face>(vertices.get(vertices.size() - 1), vertices.get(0)).setParent(this));
		}
		return edges;
	}

	/**
	 * This method calculates the area as a vector.
	 * Normalizing this vector will give the normal of this face.
	 * The length of this vector is equal the area of this face.
	 */
	public Vector calcAreaVector() {
		if (vertices.size() > 1) {
			final Vector p0 = vertices.get(0).getPosition();
			Vector area = new Vector(p0.getDimension());
			for (int i = 2; i < vertices.size(); i++) {
				Vector p1 = vertices.get(i - 1).getPosition().sub(p0);
				Vector p2 = vertices.get(i).getPosition().sub(p0);
				area = area.add(p1.cross(p2).scale(0.5));
			}
			return area;
		}
		return null;
	}

	/**
	 * This method calculates the area of this face.
	 * @see #calcAreaVector()
	 */
	public double calcArea() {
		final Vector areaVector = calcAreaVector();
		return areaVector != null ? areaVector.getLength() : 0;
	}

	/**
	 * This method calculates the normal of this face.
	 * @see #calcAreaVector()
	 */
	public Vector getNormal() {
		final Vector areaVector = calcAreaVector();
		if (areaVector == null) {
			return null;
		}
		final double length = areaVector.getLength();
		return length > 0 ? areaVector.scale(1 / length) : null;
	}

	public Map<UnidirectionalEdge,Edge<Face>> getUnidirectionalEdgeLookup() {
		Map<UnidirectionalEdge,Edge<Face>> lookupMap = new HashMap<>();
		for (Edge<Face> edge : getEdges()) {
			lookupMap.put(new UnidirectionalEdge(edge), edge);
		}
		return lookupMap;
	}

	public enum NormalConsistency {
		Consistent,
		Inconsistent,
		NotConnected
	}

	public NormalConsistency getNormalConsistentWith(Face other) {
		final Map<UnidirectionalEdge,Edge<Face>> thisEdgeLookup = getUnidirectionalEdgeLookup();
		final Map<UnidirectionalEdge,Edge<Face>> otherEdgeLookup = other.getUnidirectionalEdgeLookup();
		// find the common unidirectional edge.
		Set<UnidirectionalEdge> commonUnidirectionalEdge = thisEdgeLookup.keySet();
		commonUnidirectionalEdge.retainAll(otherEdgeLookup.keySet());
		if (commonUnidirectionalEdge.isEmpty()) {
			return NormalConsistency.NotConnected;
		}
		// get both edges.
		Edge<Face> thisEdge = thisEdgeLookup.get(commonUnidirectionalEdge.iterator().next());
		Edge<Face> otherEdge = otherEdgeLookup.get(commonUnidirectionalEdge.iterator().next());
		return thisEdge.getVertices().get(0).getPosition().equals(otherEdge.getVertices().get(1).getPosition())
			? NormalConsistency.Consistent
			: NormalConsistency.Inconsistent;
	}

	/**
	 * This method reverses the order of vertices, which flips all edges and thereby
	 * flips the normal of this face.
	 */
	public Face flipNormal() {
		Collections.reverse(vertices);
		return this;
	}
}
