package com.qa.api.contacts.tests;

import java.io.File;

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

public class CreateContactsTest2 extends BaseTest{
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
	public void createUserPOJOTest() {
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
	}
	
	@Test
	public void createUserWithJsonStringTest() {
		//The email is unique,need to give a new email each time a user is getting created
		
		String contactsJson = "{\r\n"
				+ "    \"firstName\": \"Zainab\",\r\n"
				+ "    \"lastName\": \"Naveen\",\r\n"
				+ "    \"email\": \""+StringUtils.getRandomEmailId()+"\",\r\n"
				+ "    \"phone\": \"999999999\",\r\n"
				+ "    \"street1\": \"House number: 563,10th main, 5th cross,\",\r\n"
				+ "    \"street2\": \"Defence Colony\",\r\n"
				+ "    \"city\": \"Bangalore\",\r\n"
				+ "    \"stateProvince\": \"Karnataka\",\r\n"
				+ "    \"postalCode\": \"560008\",\r\n"
				+ "    \"country\": \"India\"\r\n"
				+ "}";
		
		Response responsePOST=restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contactsJson, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("firstName"),"Zainab");
		Assert.assertEquals(responsePOST.jsonPath().getString("lastName"),"Naveen");
		Assert.assertNotNull(responsePOST.jsonPath().getString("_id"));
		Assert.assertEquals(responsePOST.getStatusCode(),201);
	}
	
	@Test
	
	public void createUserWithJsonFileTest() {
		File contactsFile=new File("./src/test/resources/jsons/contact.json");
		
		Response responsePOST=restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contactsFile, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("firstName"),"Zainab");
		Assert.assertEquals(responsePOST.jsonPath().getString("lastName"),"Naveen");
		Assert.assertNotNull(responsePOST.jsonPath().getString("_id"));
	}
}
