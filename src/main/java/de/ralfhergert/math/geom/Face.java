package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	/**
	 * This method calculates the normal of this face by not relying on the area vector.
	 */
	public Vector getNormalFast() {
		if (vertices.size() > 1) {
			final Vector p0 = vertices.get(0).getPosition();
			for (int i = 2; i < vertices.size(); i++) {
				Vector p1 = vertices.get(i - 1).getPosition().sub(p0);
				Vector p2 = vertices.get(i).getPosition().sub(p0);
				final Vector normal = p1.cross(p2);
				final double length = normal.getLength();
				if (length > 0) {
					return normal.scale(1 / length);
				}
			}
		}
		return null;
	}

	public Map<UnidirectionalEdge,Edge<Face>> getUnidirectionalEdgeLookup() {
		Map<UnidirectionalEdge,Edge<Face>> lookupMap = new HashMap<>();
		for (Edge<Face> edge : getEdges()) {
			lookupMap.put(new UnidirectionalEdge(edge), edge);
		}
		return lookupMap;
	}

	/**
	 * Defines the different outcomes of a face-consistency-check.
	 * @see #getNormalConsistentWith(Face)
	 */
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

	public Bounds getBounds() {
		if (vertices.isEmpty()) {
			return null;
		}
		Vector v1 = vertices.get(0).getPosition();
		double minX = v1.get(0);
		double maxX = minX;
		double minY = v1.get(1);
		double maxY = minY;
		double minZ = v1.get(2);
		double maxZ = minZ;
		for (int i = 1; i < vertices.size(); i++) {
			final Vector v = vertices.get(i).getPosition();
			minX = Math.min(minX, v.get(0));
			maxX = Math.max(maxX, v.get(0));
			minY = Math.min(minY, v.get(1));
			maxY = Math.max(maxY, v.get(1));
			minZ = Math.min(minZ, v.get(2));
			maxZ = Math.max(maxZ, v.get(2));
		}
		return new Bounds()
			.setMin(new Vector(minX, minY, minZ))
			.setMax(new Vector(maxX, maxY, maxZ));
	}

	/**
	 * This method will cut off the parts of the current face which are offside the given plane.
	 */
	public Face intersect(Plane plane) {
		if (plane == null) {
			return null;
		}
		Face face = new Face();
		// copy all properties.
		face.properties.putAll(properties);
		// check the vertices.
		Vertex previousVertex = vertices.size() > 1 ? vertices.get(vertices.size() - 1) : null;
		VertexLocation previousVertexLocation = previousVertex != null ? plane.calcVertexLocation(previousVertex) : null;
		for (Vertex vertex : vertices) {
			VertexLocation currentVertexLocation = plane.calcVertexLocation(vertex);
			if (currentVertexLocation != VertexLocation.OutBounds) {
				if (previousVertexLocation == VertexLocation.OutBounds) {
					face.addVertex(plane.intersect(new Edge(previousVertex, vertex)));
				}
				face.addVertex(vertex);
			} else if (previousVertex != null && previousVertexLocation != VertexLocation.OutBounds) {
				face.addVertex(plane.intersect(new Edge(previousVertex, vertex)));
			}
			previousVertex = vertex;
			previousVertexLocation = currentVertexLocation;
		}
		return face;
	}

	public Face projectOn(Plane plane) {
		if (plane == null) {
			return null;
		}
		Face face = new Face();
		// copy all properties.
		face.properties.putAll(properties);
		// calc all vertices
		for (Vertex vertex : vertices) {
			Vector fromPlaneToVertex = vertex.getPosition().sub(plane.getPosition().getPosition());
			face.addVertex(new Vertex(vertex.getPosition().sub(fromPlaneToVertex.projectOn(plane.getNormal()))));
		}
		return face;
	}
}
