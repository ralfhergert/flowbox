package de.ralfhergert.flowbox.worker;

import de.ralfhergert.flowbox.model.Frame;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link FrameClusterer} is working correctly.
 */
public class FrameClustererTest {

	@Test(expected = IllegalArgumentException.class)
	public void testFrameClustererRejectNullFrame() {
		new FrameClusterer().clusterFrame(null);
	}

	@Test
	public void testFrameIsFlaggedAsClusteredAfterExecution() {
		final Frame frame = new Frame(0);
		Assert.assertFalse("frame should not be flagged as clustered", frame.isClustered());
		new FrameClusterer().clusterFrame(frame);
		Assert.assertNotNull("frame should not be null", frame);
		Assert.assertTrue("frame should be flagged as clustered", frame.isClustered());
	}
}
