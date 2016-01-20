package com.alex.gitbrancher.ajax.model;

import org.junit.Assert;
import org.junit.Test;

public class AjaxStatusTest {

	@Test
	public void testEnumCount() {
		Assert.assertEquals(2, AjaxStatus.values().length);
	}

	@Test
	public void testEnumMessageValue() {
		Assert.assertEquals("success", AjaxStatus.SUCCESS.getMessage());
		Assert.assertEquals("failure", AjaxStatus.FAILURE.getMessage());
	}
}
