package de.ralfhergert.math.geom;

import java.util.*;

/***
 * A mesh consisting of multiple faces.
 */
public class Mesh {

	private List<Face> faces = new ArrayList<>();

	public Mesh addFace(Face face) {
		if (face == null) {
			throw new IllegalArgumentException("face can not be null");
		}
		faces.add(face);
		return this;
	}

	public List<Face> getFaces() {
		return Collections.unmodifiableList(faces);
	}

	/**
	 * This method checks whether all adjacent faces of the mesh connect
	 * at their edges. In other words it checks whether the mesh has leaks.
	 *
	 * @return true if this mesh has no leaks
	 */
	public boolean isImpermeable() {
		final Map<UnidirectionalEdge,List<Edge>> edgeLookup = new HashMap<>();
		// build a lookup map.
		faces.stream()
			.filter(face -> face.calcArea() > 0.000001) // ignore all faces with no area.
			.flatMap(face -> face.getEdges().stream()).forEach(edge -> {
				final UnidirectionalEdge unidirectionalEdge = new UnidirectionalEdge(edge);
				if (edgeLookup.containsKey(unidirectionalEdge)) {
					edgeLookup.get(unidirectionalEdge).add(edge);
				} else {
					edgeLookup.put(unidirectionalEdge, new ArrayList<>(Collections.singletonList(edge)));
				}
			});
		// analyze the lookup map.
		if (edgeLookup.isEmpty()) {
			return false;
		}
		// each unidirectional edge should have two edges.
		for (List<Edge> edgePairs : edgeLookup.values()) {
			if (edgePairs.size() != 2) {
				return false;
			}
		}
		return true; // if this point is reached all checks have been positive - meaning the mesh has no leaks.
	}
}
