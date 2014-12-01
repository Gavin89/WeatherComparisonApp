package com.compareweather.test;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import WC.DataAnalyser;

public class GetDataFromDB {

	private Object data1;

	@Test
	public void test() throws JSONException {
		DataAnalyser data = new DataAnalyser();
		data.getData();
		Assert.assertTrue(true);
	}

}
