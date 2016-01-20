package com.alex.gitbrancher.ajax.model;

import org.junit.Assert;
import org.junit.Test;

public class AjaxResponseTest {

	@Test
	public void testConstructorObjectOnly() {
		String someObject = "hello";
		AjaxResponse response = new AjaxResponse(someObject);
		Assert.assertEquals("Response should contain the original object", someObject, response.getResult());
		Assert.assertEquals("Response status should be success", AjaxStatus.SUCCESS, response.getStatus());
	}

	@Test
	public void testConstructorObjectAndStatus() {
		String someObject = "hello";
		AjaxResponse response = new AjaxResponse(someObject, AjaxStatus.FAILURE);
		Assert.assertEquals("Response should contain the original object", someObject, response.getResult());
		Assert.assertEquals("Response status should be success", AjaxStatus.FAILURE, response.getStatus());
	}
}
