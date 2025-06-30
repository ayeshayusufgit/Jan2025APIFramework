package com.qa.api.reqres.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;

import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ReqResTest extends BaseTest{

	@Test
	public void getUserTest() {
		
		Map<String,String> queryParamMap=new HashMap<String, String>();
		queryParamMap.put("page", "2");
		
		Response responseGET=restClient.get(BASE_URL_REQRES, REQRES_ENDPOINT, queryParamMap, null, AuthType.NO_AUTH, ContentType.ANY);
		//if not sure about ContentType then ANY can be used
		
		Assert.assertEquals(responseGET.getStatusCode(),200);
	}
}
