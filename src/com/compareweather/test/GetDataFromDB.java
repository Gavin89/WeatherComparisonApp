package com.compareweather.test;

import org.junit.Assert;
import org.junit.Test;

import WC.DataAnalyser;

public class GetDataFromDB {

	@Test
	public void test() {
		DataAnalyser data = new DataAnalyser();
		data.getData();
		Assert.assertTrue(true);
	}

}
