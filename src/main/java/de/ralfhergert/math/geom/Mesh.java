package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	 * This method builds a lookup map for all edges in this mesh.
	 * @see #isImpermeable()
	 * @see #getOpenEdges()
	 */
	public Map<UnidirectionalEdge,List<Edge<Face>>> buildEdgeLookupMap() {
		final Map<UnidirectionalEdge,List<Edge<Face>>> edgeLookup = new HashMap<>();
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
		return edgeLookup;
	}

	/**
	 * This method checks whether all adjacent faces of the mesh connect
	 * at their edges. In other words it checks whether the mesh has leaks.
	 *
	 * @return true if this mesh has no leaks
	 */
	public boolean isImpermeable() {
		final Map<UnidirectionalEdge,List<Edge<Face>>> edgeLookup = buildEdgeLookupMap();
		// analyze the lookup map.
		if (edgeLookup.isEmpty()) {
			return false;
		}
		// each unidirectional edge should have two edges.
		for (List<Edge<Face>> edgePairs : edgeLookup.values()) {
			if (edgePairs.size() != 2) {
				return false;
			}
		}
		return true; // if this point is reached all checks have been positive - meaning the mesh has no leaks.
	}

	/**
	 * This method returns all edges which have no connecting edge.
	 */
	public Set<Edge<Face>> getOpenEdges() {
		return buildEdgeLookupMap()
			.entrySet()
			.stream()
			.filter(entry -> entry.getValue().size() != 2)
			.flatMap(entry -> entry.getValue().stream())
			.collect(Collectors.toSet());
	}

	/**
	 * This method finds all adjacent faces to the given face.
	 */
	public Set<Face> findAdjacentFaces(Face face) {
		return findAdjacentFaces(Collections.singletonList(face), buildEdgeLookupMap());
	}

	/**
	 * This method finds all adjacent faces to the given faces.
	 * It will use the given lookupMap.
	 */
	public Set<Face> findAdjacentFaces(final Collection<Face> faces, final Map<UnidirectionalEdge,List<Edge<Face>>> edgeLookup) {
		final Set<Face> adjacentFaces = new HashSet<>();
		for (Face face : faces) {
			for (Edge<Face> edge : face.getEdges()) {
				for (Edge<Face> foundEdge : edgeLookup.get(new UnidirectionalEdge(edge))) {
					if (!faces.contains(foundEdge.getParent())) {
						adjacentFaces.add(foundEdge.getParent());
					}
				}
			}
		}
		return adjacentFaces;
	}

	/**
	 * @return true if all face normals point inwards or if all face normals point outwards
	 */
	public boolean isConsistent() {
		// check the edge lookup map
		for (Map.Entry<UnidirectionalEdge,List<Edge<Face>>> entry : buildEdgeLookupMap().entrySet()) {
			final List<Edge<Face>> edgeList = entry.getValue();
			if (edgeList.size() == 2) {
				final Edge<Face> edge1 = edgeList.get(0);
				final Edge<Face> edge2 = edgeList.get(1);
				// if both edge start from the same vertex, then the faces they belong to have opposing normals.
				if (edge1.getVertices().get(0).getPosition().equals(edge2.getVertices().get(0).getPosition())) {
					return false;
				}
			}
		}
		return true;
	}

	public Mesh makeFaceNormalsConsistent() {
		if (faces.isEmpty()) {
			return this;
		}
		return makeAdjacentFacesNormalsConsistent(new ArrayList<>(Collections.singleton(faces.get(0))), buildEdgeLookupMap());
	}
	/**
	 * This method searches all adjacent faces of the given faces and tries to
	 * makes their normals consistent by flipping them.
	 */
	public Mesh makeAdjacentFacesNormalsConsistent(final Collection<Face> consistentFaces, final Map<UnidirectionalEdge,List<Edge<Face>>> edgeLookup) {
		if (consistentFaces == null || consistentFaces.isEmpty()) {
			return this;
		}
		Set<Face> adjacentFaces = findAdjacentFaces(consistentFaces, edgeLookup);
		if (!adjacentFaces.isEmpty()) {
			for (Face adjacentFace : adjacentFaces) {
				// get the pairing face out of the consistent faces.
				Set<Face> pairingFaces = findAdjacentFaces(adjacentFace);
				pairingFaces.retainAll(consistentFaces);
				// pairing faces can not be empty, else adjacentFace would have never been found.
				if (adjacentFace.getNormalConsistentWith(pairingFaces.iterator().next()) == Face.NormalConsistency.Inconsistent) {
					adjacentFace.flipNormal();
				}
				consistentFaces.add(adjacentFace);
			}
			makeAdjacentFacesNormalsConsistent(consistentFaces, edgeLookup);
		}
		return this;
	}

	public Mesh flipFaceNormals() {
		for (Face face : faces) {
			face.flipNormal();
		}
		return this;
	}

	/**
	 * This method calculates the volume of this mesh. To get the correct
	 * volume this method requires the mesh to be {@link #isImpermeable()} to
	 * be {@code true} and {@link #isConsistent()} to be {@code true}.
	 *
	 * @return a negative value if the faceNormals are pointing outwards or
	 *         a positive value is the faceNormals are pointing inwards; in
	 *         either case the absolute value is the volume.
	 * @see #calcVolume()
	 */
	public double calcVolumeNative() {
		if (faces.isEmpty()) {
			return 0;
		}
		double volume = 0;
		// use a vertex of the first face as origin.
		Vector origin = faces.get(0).getVertices().get(0).getPosition();
		// the first face can be skipped, because the origin is part of this face its volume will be 0.
		for (int i = 1; i < faces.size(); i++) {
			Face face = faces.get(i);
			Vector areaVector = face.calcAreaVector();
			Vector toOrigin = origin.sub(face.getVertices().get(0).getPosition());
			volume += areaVector.scalar(toOrigin) / 3;
		}
		return volume;
	}

	/**
	 * This method calculates the volume of this mesh. To get the correct
	 * volume this method requires the mesh to be {@link #isImpermeable()} to
	 * be {@code true} and {@link #isConsistent()} to be {@code true}.
	 *
	 * @return the volume of this mesh
	 * @see #calcVolumeNative()
	 */
	public double calcVolume() {
		return Math.abs(calcVolumeNative());
	}
}
