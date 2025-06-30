package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.contacts.Credentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITest extends BaseTest {

	String tokenId;
	
	@BeforeMethod
	public void getToken() {
		Credentials credential=
		Credentials.builder()
			.email("ayesha@gmail.com")
			.password("test@123")
			.build();
	
		Response loginResponsePOST=restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, credential, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(loginResponsePOST.getStatusCode(), 200);
		tokenId=loginResponsePOST.jsonPath().getString("token");
		System.out.println("Contacts Login JWT Token Id:"+tokenId);
		ConfigManager.set("bearertoken", tokenId);//updating the bearertoken in the config.properties
	}
	
	@Test
	public void getAllContactsTest() {
		Response responseGET=restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGET.getStatusLine(), 200);
	}

}
