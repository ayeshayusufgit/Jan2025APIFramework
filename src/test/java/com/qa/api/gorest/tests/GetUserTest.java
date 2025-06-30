package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Epic("Epic 100 : GoRest Get User API Feature")
@Story("US 100 : Feature GoRest API - Get User API")
//Epic is a collection of User Stories
public class GetUserTest extends BaseTest {

	@Description("Getting all the Users...")
	@Owner("Ayesha Yusuf")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void getAllUsersTest() {
		ChainTestListener.log("Get All Users API Test");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}

	@Test
	public void getAllUsersWithQueryParamTest() {
		Map<String, String> queryParam = new HashMap<String, String>();
		// queryParam.put("name", "ayesha");
		queryParam.put("status", "inactive");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, queryParam, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}

	@Test(enabled=false)
	public void getSingleUserTest() {
		String userId = "7932182";
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(response.jsonPath().getString("id"),userId);
	}
}
