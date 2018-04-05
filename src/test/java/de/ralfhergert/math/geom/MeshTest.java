package de.ralfhergert.math.geom;

import org.alltiny.math.vector.Vector;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

/**
 * This test verifies that {@link Mesh} is working correctly.
 */
public class MeshTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddFaceRejectsNull() {
		new Mesh().addFace(null);
	}

	@Test
	public void testEmptyMeshIsPermeable() {
		Assert.assertFalse("an empty mesh is considered to be not impermeable", new Mesh().isImpermeable());
	}

	@Test
	public void testMeshWithSingleFaceIsPermeable() {
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(2, 0, 0)))
			.addVertex(new Vertex(new Vector(0, 2, 0)));
		Assert.assertFalse("a mesh with a single face can not be permeable", new Mesh().addFace(face).isImpermeable());
	}

	@Test
	public void testMeshWithIncompleteFace() {
		// create a face with only two vertices. Such a faces has no area.
		Face face = new Face()
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)));
		Assert.assertFalse("mesh with a incomplete face can not be permeable", new Mesh().addFace(face).isImpermeable());
	}

	@Test
	public void testMeshOfACubeIsImpermeable() {
		Mesh cube = new Mesh()
			.addFace(new Face() // z=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0))))
			.addFace(new Face() // z=1
				.addVertex(new Vertex(new Vector(0, 0, 1)))
				.addVertex(new Vertex(new Vector(1, 0, 1)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 1, 1))))
			.addFace(new Face() // y=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 1)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 1, 1))))
			.addFace(new Face() // x=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(1, 0, 1))));
		Assert.assertTrue("a cube is a complete/leakless mesh and therefor impermeable", cube.isImpermeable());
	}

	@Test
	public void testGetOpenEdgesOnLeakingPyramid() {
		// build a pyramid with a missing bottom face.
		Mesh pyramid = new Mesh()
			.addFace(new Face() // x=-1
				.addVertex(new Vertex(new Vector(-1, -1, 0)))
				.addVertex(new Vertex(new Vector(-1,  1, 0)))
				.addVertex(new Vertex(new Vector( 0,  0, 2))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, -1, 0)))
				.addVertex(new Vertex(new Vector(1,  1, 0)))
				.addVertex(new Vertex(new Vector(0,  0, 2))))
			.addFace(new Face() // y=-1
				.addVertex(new Vertex(new Vector(-1, -1, 0)))
				.addVertex(new Vertex(new Vector( 1, -1, 0)))
				.addVertex(new Vertex(new Vector( 0,  0, 2))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(-1, 1, 0)))
				.addVertex(new Vertex(new Vector( 1, 1, 0)))
				.addVertex(new Vertex(new Vector( 0, 0, 2))));
		Set<Edge<Face>> openEdges = pyramid.getOpenEdges();
		Assert.assertNotNull("Set should not be null", openEdges);
		Assert.assertEquals("number of openEdges should be", 4, openEdges.size());
		for (Edge<Face> edge : openEdges) {
			Assert.assertEquals("all open edges should be on z=0", 0, edge.getVertices().get(0).getPosition().get(2), 0.000001);
			Assert.assertEquals("all open edges should be on z=0", 0, edge.getVertices().get(1).getPosition().get(2), 0.000001);
		}
		Assert.assertFalse("pyramid should be considered leaky", pyramid.isImpermeable());
	}

	@Test
	public void testFindAdjacentFacesOfCube() {
		Face bottom = new Face() // z=0
			.addVertex(new Vertex(new Vector(0, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 0, 0)))
			.addVertex(new Vertex(new Vector(1, 1, 0)))
			.addVertex(new Vertex(new Vector(0, 1, 0)));
		Face top = new Face() // z=1
			.addVertex(new Vertex(new Vector(0, 0, 1)))
			.addVertex(new Vertex(new Vector(1, 0, 1)))
			.addVertex(new Vertex(new Vector(1, 1, 1)))
			.addVertex(new Vertex(new Vector(0, 1, 1)));
		Mesh cube = new Mesh()
			.addFace(bottom)
			.addFace(top)
			.addFace(new Face() // y=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 1)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 1, 1))))
			.addFace(new Face() // x=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 1)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 1)))
				.addVertex(new Vertex(new Vector(1, 0, 1))));
		Set<Face> sides = cube.findAdjacentFaces(bottom);
		Assert.assertNotNull("set of faces should not be null", sides);
		Assert.assertEquals("number of found side faces", 4, sides.size());
		Assert.assertFalse("set should not contain bottom face", sides.contains(bottom));
		Assert.assertFalse("set should not contain top face", sides.contains(top));
	}

	@Test
	public void testMakeInconsistentMeshConsistent() {
		// create a mesh with two faces which have normals pointing into the opposing directions.
		Mesh mesh = new Mesh()
			.addFace(new Face()
				.addVertex(new Vertex(new Vector(0, -1, 0)))
				.addVertex(new Vertex(new Vector(0,  1, 0)))
				.addVertex(new Vertex(new Vector(1,  0, 0))))
			.addFace(new Face()
				.addVertex(new Vertex(new Vector( 0, -1, 0)))
				.addVertex(new Vertex(new Vector( 0,  1, 0)))
				.addVertex(new Vertex(new Vector(-1,  0, 0))));
		Assert.assertFalse("mesh should be inconsistent", mesh.isConsistent());
		mesh.makeFaceNormalsConsistent();
		Assert.assertTrue("mesh should be consistent", mesh.isConsistent());
	}

	@Test
	public void testMakeConsistentMeshConsistent() {
		// create a consistent mesh with two faces.
		Mesh mesh = new Mesh()
			.addFace(new Face()
				.addVertex(new Vertex(new Vector(0, -1, 0)))
				.addVertex(new Vertex(new Vector(0,  1, 0)))
				.addVertex(new Vertex(new Vector(1,  0, 0))))
			.addFace(new Face()
				.addVertex(new Vertex(new Vector( 0,  1, 0)))
				.addVertex(new Vertex(new Vector( 0, -1, 0)))
				.addVertex(new Vertex(new Vector(-1,  0, 0))));
		Assert.assertTrue("mesh should be consistent", mesh.isConsistent());
		mesh.makeFaceNormalsConsistent();
		Assert.assertTrue("mesh should be consistent", mesh.isConsistent());
	}

	@Test
	public void testCallMakeConsistentOnEmptyMeshRaisesNoErrors() {
		Mesh mesh = new Mesh().makeFaceNormalsConsistent();
		Assert.assertTrue("empty mesh should be considered consistent", mesh.isConsistent());
	}

	@Test
	public void testCallMakeConsistentWithNullTrustedFacesToStartDoesNotRaiseAnyErrors() {
		Mesh mesh = new Mesh().makeAdjacentFacesNormalsConsistent(null, null);
		Assert.assertTrue("empty mesh should be considered consistent", mesh.isConsistent());
	}

	@Test
	public void testCallMakeConsistentWithEmptyTrustedFacesToStartDoesNotRaiseAnyErrors() {
		Mesh mesh = new Mesh().makeAdjacentFacesNormalsConsistent(new ArrayList<>(), null);
		Assert.assertTrue("empty mesh should be considered consistent", mesh.isConsistent());
	}

	@Test
	public void testCalculateVolumeNativeOnEmptyMesh() {
		Assert.assertEquals("volume of an empty mesh should be", 0, new Mesh().calcVolumeNative(), 0.000001);
	}

	@Test
	public void testCalculateVolumeOnEmptyMesh() {
		Assert.assertEquals("volume of an empty mesh should be", 0, new Mesh().calcVolume(), 0.000001);
	}

	@Test
	public void testCalculateVolumeNativeOnPyramid() {
		// create a pyramid.
		Mesh mesh = new Mesh()
			.addFace(new Face() // bottom
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0))))
			.addFace(new Face() // x=0
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // y=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 1))));
		Assert.assertTrue("mesh should be impermeable", mesh.isImpermeable());
		Assert.assertTrue("mesh should be consistent", mesh.isConsistent());
		Assert.assertEquals("native volume should be", -1.0 / 3, mesh.calcVolumeNative(), 0.000001);
		Assert.assertEquals("volume should be", 1.0 / 3, mesh.calcVolume(), 0.000001);
		mesh.flipFaceNormals();
		Assert.assertEquals("native volume should be", 1.0 / 3, mesh.calcVolumeNative(), 0.000001);
		Assert.assertEquals("volume should be", 1.0 / 3, mesh.calcVolume(), 0.000001);
	}

	@Test
	public void testCalculateVolumeOnCube() {
		// create a cube with a=2.
		Mesh mesh = new Mesh()
			.addFace(new Face() // x=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 2)))
				.addVertex(new Vertex(new Vector(0, 2, 2)))
				.addVertex(new Vertex(new Vector(0, 2, 0))))
			.addFace(new Face() // x=2
				.addVertex(new Vertex(new Vector(2, 2, 2)))
				.addVertex(new Vertex(new Vector(2, 0, 2)))
				.addVertex(new Vertex(new Vector(2, 0, 0)))
				.addVertex(new Vertex(new Vector(2, 2, 0))))
			.addFace(new Face() // y=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(2, 0, 0)))
				.addVertex(new Vertex(new Vector(2, 0, 2)))
				.addVertex(new Vertex(new Vector(0, 0, 2))))
			.addFace(new Face() // y=2
				.addVertex(new Vertex(new Vector(2, 2, 2)))
				.addVertex(new Vertex(new Vector(2, 2, 0)))
				.addVertex(new Vertex(new Vector(0, 2, 0)))
				.addVertex(new Vertex(new Vector(0, 2, 2))))
			.addFace(new Face() // z=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 2, 0)))
				.addVertex(new Vertex(new Vector(2, 2, 0)))
				.addVertex(new Vertex(new Vector(2, 0, 0))))
			.addFace(new Face() // z=2
				.addVertex(new Vertex(new Vector(2, 2, 2)))
				.addVertex(new Vertex(new Vector(0, 2, 2)))
				.addVertex(new Vertex(new Vector(0, 0, 2)))
				.addVertex(new Vertex(new Vector(2, 0, 2))));
		Assert.assertTrue("mesh should be impermeable", mesh.isImpermeable());
		Assert.assertTrue("mesh should be consistent", mesh.isConsistent());
		Assert.assertEquals("native volume should be", -8, mesh.calcVolumeNative(), 0.000001);
		Assert.assertEquals("volume should be", 8, mesh.calcVolume(), 0.000001);
		mesh.flipFaceNormals();
		Assert.assertEquals("native volume should be", 8, mesh.calcVolumeNative(), 0.000001);
		Assert.assertEquals("volume should be", 8, mesh.calcVolume(), 0.000001);
	}

	@Test
	public void testVertexLocationOnBoundsOnPyramid() {
		// create a pyramid.
		Mesh mesh = new Mesh()
			.addFace(new Face() // bottom
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0))))
			.addFace(new Face() // x=0
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // y=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 1))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 1))));
		Assert.assertTrue("mesh should be impermeable", mesh.isImpermeable());
		Assert.assertTrue("mesh should be consistent", mesh.isConsistent());
		Assert.assertEquals("Vertex(0,0,0) should be on bounds", VertexLocation.OnBounds, mesh.calcVertexLocation(new Vertex(new Vector(0, 0, 0))));
	}

	/**
	 * This utility method creates a pyramid.
	 */
	@Ignore
	protected Mesh createPyramid() {
		return new Mesh()
			.addFace(new Face() // bottom
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0))))
			.addFace(new Face() // x=0
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(0.5, 0.5, 1))))
			.addFace(new Face() // x=1
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(0.5, 0.5, 1))))
			.addFace(new Face() // y=0
				.addVertex(new Vertex(new Vector(0, 0, 0)))
				.addVertex(new Vertex(new Vector(1, 0, 0)))
				.addVertex(new Vertex(new Vector(0.5, 0.5, 1))))
			.addFace(new Face() // y=1
				.addVertex(new Vertex(new Vector(1, 1, 0)))
				.addVertex(new Vertex(new Vector(0, 1, 0)))
				.addVertex(new Vertex(new Vector(0.5, 0.5, 1))));
	}

	@Test
	public void testEnsurePyramidIsImpermeable() {
		Assert.assertTrue("pyramid should be impermeable", createPyramid().isImpermeable());
	}

	@Test
	public void testEnsurePyramidIsConsistent() {
		Assert.assertTrue("pyramid should be consistent", createPyramid().isConsistent());
	}

	@Test
	public void testVertexLocationOnBoundsOnPyramidOnVertex() {
		final Vertex vertex = new Vertex(new Vector(0.5, 0.5, 1));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.OnBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationOutOfBoundsOnPyramidCloseToVertex() {
		final Vertex vertex = new Vertex(new Vector(0.5, 0.5, 1.001));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.OutBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationInBoundsOnPyramidCloseToVertex() {
		final Vertex vertex = new Vertex(new Vector(0.5, 0.5, 0.999));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.InBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationOnBoundsOnPyramidOnEdge() {
		final Vertex vertex = new Vertex(new Vector(1, 0.5, 0));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.OnBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationOutOfBoundsOnPyramidCloseToEdge() {
		final Vertex vertex = new Vertex(new Vector(1.001, 0.5, 0));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.OutBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationInBoundsOnPyramidCloseToEdge() {
		final Vertex vertex = new Vertex(new Vector(0.999, 0.5, 0.001));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.InBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationInBoundsOnPyramidCenter() {
		final Vertex vertex = new Vertex(new Vector(0.5, 0.5, 0.5));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.InBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationInBoundsOnPyramidBottomCenter() {
		final Vertex vertex = new Vertex(new Vector(0.5, 0.5, 0.001));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.InBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationOnBoundsOnPyramidBottomCenter() {
		final Vertex vertex = new Vertex(new Vector(0.5, 0.5, 0));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.OnBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationOutOfBoundsOnPyramidBottomCenter() {
		final Vertex vertex = new Vertex(new Vector(0.5, 0.5, -0.001));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.OutBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationOnBoundsClosePyramidSideCenter() {
		final Vertex vertex = new Vertex(new Vector(0.75, 0.5, 0.5));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.OnBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationInBoundsClosePyramidSideCenter() {
		final Vertex vertex = new Vertex(new Vector(0.75, 0.5, 0.499));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.InBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationOutOfBoundsClosePyramidSideCenter() {
		final Vertex vertex = new Vertex(new Vector(0.75, 0.5, 0.501));
		Assert.assertEquals(vertex + " should be in bounds", VertexLocation.OutBounds, createPyramid().calcVertexLocation(vertex));
	}

	@Test
	public void testVertexLocationOutOfBoundsOnPyramid() {
		final Vertex vertex = new Vertex(new Vector(2, 0.5, 0));
		Assert.assertEquals(vertex + " should be out of bounds", VertexLocation.OutBounds, createPyramid().calcVertexLocation(vertex));
	}
}
