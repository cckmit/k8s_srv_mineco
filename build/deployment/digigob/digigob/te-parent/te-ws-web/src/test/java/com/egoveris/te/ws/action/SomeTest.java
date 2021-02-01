package com.egoveris.te.ws.action;

import org.junit.Test;

import junit.framework.Assert;

public class SomeTest {

	@Test
	public void test(){
		String val = "00000000123456";
		int len = val.length();
		Assert.assertEquals("123",val.substring(len-6, len-3));
		Assert.assertEquals("456",val.substring(len-3, len));
	}
}
