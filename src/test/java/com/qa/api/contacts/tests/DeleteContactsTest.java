package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.contacts.Contact;
import com.qa.api.pojo.contacts.Credentials;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteContactsTest extends BaseTest{

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
	public void deleteContactsTest() {
		//1.	Create a contact
		Contact contact= Contact.builder()
				.firstName("Ayesha")
				.lastName("Yusuf")
				.email(StringUtils.getRandomEmailId())
				.phone("9999999999")
				.street1("Street1")
				.street2("Street2")
				.city("Bangalore")
				.stateProvince("Karnataka")
				.postalCode("560008")
				.country("India").build();
		
		Response responsePOST=restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contact, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("firstName"),contact.getFirstName());
		Assert.assertEquals(responsePOST.jsonPath().getString("lastName"),contact.getLastName());
		Assert.assertNotNull(responsePOST.jsonPath().getString("_id"));
		Assert.assertEquals(responsePOST.getStatusCode(),201);
		
		//fetch the userId:
		String userId = responsePOST.jsonPath().getString("_id");
		System.out.println("user id ====>" +  userId);
		
		//2.	GET: fetch the contact using the same user id:
		Response responseGET = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));		
		Assert.assertEquals(responseGET.jsonPath().getString("_id"), userId);
		
		//3.	DELETE: the user using the same user id
		Response responseDELETE = restClient.delete(BASE_URL_CONTACTS, CONTACTS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(responseDELETE.asString().contains("Contact deleted"));
		Assert.assertEquals(responseDELETE.getStatusCode(),204);//response is 200 should be 204		
	} 
}
