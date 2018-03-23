package de.ralfhergert.math.geom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * A mesh consisting of multiple faces.
 */
public class Mesh {

	private List<Face> faces = new ArrayList<>();

	public Mesh addFace(Face face) {
		faces.add(face);
		return this;
	}

	public List<Face> getFaces() {
		return Collections.unmodifiableList(faces);
	}
}
