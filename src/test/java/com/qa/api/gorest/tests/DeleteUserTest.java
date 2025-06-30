package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.gorest.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest {
	
	@Test
	public void deleteUserTest() {
		User user=User.builder()
					.name("Rahul")
					.email(StringUtils.getRandomEmailId())
					.status("inactive")
					.gender("male")
					.build();
		
		Response responsePOST=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("name"), "Rahul");
		Assert.assertNotNull(responsePOST.jsonPath().getString("id"));
		
		//fetch the userId:
		String userId = responsePOST.jsonPath().getString("id");
		System.out.println("user id ====>" +  userId);
		ChainTestListener.log("User Id:"+userId);
		
		//2. GET: fetch the user using the same user id:
		Response responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));		
		Assert.assertEquals(responseGET.jsonPath().getString("id"), userId);
		
		//3. Delete the user using the same user id:
		Response responseDELETE = restClient.delete(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseDELETE.statusLine().contains("No Content"));
		//Assert.assertEquals(responseGET.jsonPath().getString("message"), "Resource not found");
		
		//4. GET: fetch the deleted user using the same user id:
		responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("Not Found"));
		Assert.assertEquals(responseGET.statusCode(), 404);
		//This assertion is added, and the status code is checked for 404
		Assert.assertEquals(responseGET.jsonPath().getString("message"), "Resource not found");		
	}
}
