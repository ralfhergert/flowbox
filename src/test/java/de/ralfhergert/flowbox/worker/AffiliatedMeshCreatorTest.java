package de.ralfhergert.flowbox.worker;

import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that the {@link AffiliatedMeshCreator} is working correctly.
 */
public class AffiliatedMeshCreatorTest {

	@Test
	public void testNullIsReturnedForNullMesh() {
		Assert.assertNull("null should be given back", AffiliatedMeshCreator.createAffiliatedMesh(null));
	}

	@Test
	public void testNullIsReturnedForNullFace() {
		Assert.assertNull("null should be given back", AffiliatedMeshCreator.createAffiliatedFace(null));
	}
}
