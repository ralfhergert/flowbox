package de.ralfhergert.flowbox.xml.v1.converter;

import de.ralfhergert.flowbox.xml.v1.XmlEdge;
import de.ralfhergert.flowbox.xml.v1.XmlOutline;
import de.ralfhergert.flowbox.xml.v1.XmlSection;
import de.ralfhergert.flowbox.xml.v1.XmlVertex;
import de.ralfhergert.generic.converter.Converter;
import de.ralfhergert.math.geom.Edge;
import de.ralfhergert.math.geom.Face;
import de.ralfhergert.math.geom.Mesh;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This converter converts a 2D-{@link XmlOutline} into a 3D-{@link Mesh}.
 * The conversion is done by extruding the given outline to z=-0.5 and z=0.5.
 * Resulting in a mesh with a z-thickness of 1.
 */
public class XmlOutline2DToMeshConverter implements Converter<XmlOutline,Mesh> {

	@Override
	public Mesh convert(XmlOutline xmlOutline) {
		if (xmlOutline == null) {
			throw new IllegalArgumentException("outline can not be null");
		}
		Mesh mesh = new Mesh();
		List<Edge> edges = new ArrayList<>();
		for (XmlSection section : xmlOutline.getSections()) {
			for (XmlEdge edge : section.getEdges()) {
				if (edge.getVertices().size() != 2) {
					throw new IllegalArgumentException("edge is supposed to have two vertices");
				}
				XmlVertex v1 = edge.getVertices().get(0);
				XmlVertex v2 = edge.getVertices().get(1);
				edges.add(new Edge(new Vertex(new Vector(v1.getX(), v1.getY(), 0)), new Vertex(new Vector(v2.getX(), v2.getY(), 0))));
				mesh.addFace(new Face()
					.setProperty("name", section.getName())
					.addVertex(new Vertex(new Vector(v1.getX(), v1.getY(), -0.5)))
					.addVertex(new Vertex(new Vector(v2.getX(), v2.getY(), -0.5)))
					.addVertex(new Vertex(new Vector(v2.getX(), v2.getY(),  0.5)))
					.addVertex(new Vertex(new Vector(v1.getX(), v1.getY(),  0.5))));
			}
			/* the sections with the edges may be defined in random order.
			 * A lookupMap help in finding the adjacent edges. */
			final Map<Vertex,List<Edge>> edgeLookupMap = buildEdgeLookupMap(edges);
			// make sure that for each vertex two edge exist.
			for (Map.Entry<Vertex,List<Edge>> entry : edgeLookupMap.entrySet()) {
				final int size = entry.getValue().size();
				if (size < 2) {
					//throw new IllegalArgumentException("the mesh definition is not complete");
					return mesh;
				} else if (size > 2) {
					//throw new IllegalArgumentException("the mesh definition is ambiguous, use a 3D-definition instead");
					return mesh;
				}
			}
			// build the two faces.
			if (!edgeLookupMap.isEmpty()) {
				final Face zNeg = new Face().setProperty("name", "zNeg");
				final Face zPos = new Face().setProperty("name", "zPos");
				for (Vertex vertex : getVerticesFromMap(edgeLookupMap, edgeLookupMap.keySet().stream().findFirst().get())) {
					zNeg.addVertex(new Vertex(new Vector(vertex.getPosition()).set(2, -0.5)));
					zPos.addVertex(new Vertex(new Vector(vertex.getPosition()).set(2, +0.5)));
				}
				mesh.addFace(zNeg);
				mesh.addFace(zPos);
			}
		}
		return mesh;
	}

	public Map<Vertex,List<Edge>> buildEdgeLookupMap(Collection<Edge> edges) {
		Map<Vertex,List<Edge>> lookupMap = new HashMap<>();
		for (Edge edge : edges) {
			for (Vertex vertex : edge.getVertices()) {
				if (lookupMap.containsKey(vertex)) {
					lookupMap.get(vertex).add(edge);
				} else {
					lookupMap.put(vertex, new ArrayList<>(Collections.singletonList(edge)));
				}
			}
		}
		return lookupMap;
	}

	public List<Vertex> getVerticesFromMap(final Map<Vertex,List<Edge>> lookupEdge, final Vertex startingVertex) {
		final List<Vertex> vertices = new ArrayList<>();
		vertices.add(startingVertex);
		Edge edge = lookupEdge.get(startingVertex).get(0);
		Vertex vertex = startingVertex;
		while (true) {
			edge = getOtherEdge(lookupEdge, vertex, edge);
			vertex = getOtherVertex(edge, vertex);
			if (startingVertex.equals(vertex)) {
				break;
			} else {
				vertices.add(vertex);
			}
		}
		return vertices;
	}

	public Vertex getOtherVertex(Edge edge, Vertex vertex) {
		for (Vertex v : edge.getVertices()) {
			if (!v.equals(vertex)) {
				return v;
			}
		}
		return null;
	}

	public Edge getOtherEdge(Map<Vertex,List<Edge>> lookupEdge, Vertex vertex, Edge currentEdge) {
		for (Edge edge : lookupEdge.get(vertex)) {
			if (!edge.equals(currentEdge)) {
				return edge;
			}
		}
		return null;
	}
}
