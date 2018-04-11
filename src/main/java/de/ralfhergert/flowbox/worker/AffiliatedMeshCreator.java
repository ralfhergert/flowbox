package de.ralfhergert.flowbox.worker;

import de.ralfhergert.math.geom.Face;
import de.ralfhergert.math.geom.Mesh;
import de.ralfhergert.math.geom.Vertex;
import org.alltiny.math.vector.Vector;

/**
 * This worker creates a mesh which is affiliated to the given mesh.
 */
public class AffiliatedMeshCreator {

	private AffiliatedMeshCreator() {
		/* no initialization necessary */
	}

	public static Mesh createAffiliatedMesh(Mesh mesh) {
		if (mesh == null) {
			return null;
		}
		final Mesh affiliatedMesh = new Mesh();
		for (Face face : mesh.getFaces()) {
			affiliatedMesh.addFace(createAffiliatedFace(face));
		}
		return affiliatedMesh;
	}

	public static Face createAffiliatedFace(Face face) {
		if (face == null) {
			return null;
		}
		Face affiliatedFace = new Face().setProperty("affiliate", face);
		for (Vertex vertex : face.getVertices()) {
			affiliatedFace.addVertex(new Vertex(new Vector(vertex.getPosition())));
		}
		return face;
	}
}
