package com.qa.api.contacts.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.contacts.Credentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetContactsTest extends BaseTest{
	String tokenId;
	
	@BeforeMethod
	public void getToken() {
		Credentials credential=
		Credentials.builder()
			.email("ayesha.yusuf@gmail.com")
			.password("Kolkata05")
			.build();
	
		Response loginResponsePOST=restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, credential, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(loginResponsePOST.getStatusCode(), 200);
		tokenId=loginResponsePOST.jsonPath().getString("token");
		System.out.println("Contacts Login JWT Token Id:"+tokenId);
		ConfigManager.set("bearertoken", tokenId);//updating the bearertoken in the config.properties
	}
	
	@Test
	public void getAllContactsTest() {
		Response response = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Test
	public void getAllContactsWithQueryParamTest() {
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("email", "abu@gmail.com");
		Response response = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, queryParam, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Test
	public void getSingleUserTest() {
		String userId = "6803a9136c78580015f4f84d";
		Response response = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(response.jsonPath().getString("_id"),userId);
	}
}
