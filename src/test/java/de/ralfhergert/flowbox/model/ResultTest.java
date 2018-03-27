package de.ralfhergert.flowbox.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link Result} is working correctly.
 */
public class ResultTest {

	@Test
	public void testResultEqualsOnSame() {
		final Result result = new Result(false, "foo");
		Assert.assertTrue("result should equal itself", result.equals(result));
	}

	@Test
	public void testResultNotEqualsNull() {
		Assert.assertFalse("result should not equal null", new Result(false, "foo").equals(null));
	}

	@Test
	public void testResultNotEqualsOtherClass() {
		Assert.assertFalse("result should not equal other class", new Result(false, "foo").equals(""));
	}

	@Test
	public void testResultsDoNotEqual1() {
		Assert.assertNotEquals("results should not equal", new Result(true, "foo"), new Result(false, "foo"));
	}

	@Test
	public void testResultsDoNotEqual2() {
		Assert.assertNotEquals("results should not equal", new Result(true, "foo"), new Result(true, "bar"));
	}

	@Test
	public void testEqualResultsDeliverSameHashCode() {
		Assert.assertEquals("hashCode should be the same", new Result(false, "foo").hashCode(), new Result(false, "foo").hashCode());
	}

	@Test
	public void testDifferentResultsDeliverDifferentHashCodes() {
		Assert.assertNotEquals("hashCode should not be the same", new Result(true, "foo").hashCode(), new Result(false, "foo").hashCode());
	}
}
