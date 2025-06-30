package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.contacts.Contact;
import com.qa.api.pojo.contacts.Credentials;
import com.qa.api.pojo.gorest.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateContactsTest extends BaseTest{
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
	public void updateUserTest() {
		//1.	Create a user - POST
		//User user=new User("Priyanka",StringUtils.getRandomEmailId(),"female","active");
		
		Contact contact=
		Contact.builder().firstName("Reetha")
						 .lastName("Bheemaiah")
						 .email(StringUtils.getRandomEmailId())
						 .phone("1111111111")
						 .street1("Indiranagar")
						 .street2("Def colony")
						 .city("Bangalore")
						 .stateProvince("Karnataka")
						 .postalCode("560008")
						 .country("India")
						 .build();
		
		Response response=restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contact, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("firstName"),contact.getFirstName());
		Assert.assertEquals(response.jsonPath().getString("lastName"),contact.getLastName());
		Assert.assertEquals(response.jsonPath().getString("email"),contact.getEmail());
		Assert.assertEquals(response.jsonPath().getString("phone"),contact.getPhone());
		Assert.assertEquals(response.jsonPath().getString("street1"),contact.getStreet1());
		Assert.assertEquals(response.jsonPath().getString("street2"),contact.getStreet2());
		Assert.assertEquals(response.jsonPath().getString("city"),contact.getCity());
		Assert.assertEquals(response.jsonPath().getString("stateProvince"),contact.getStateProvince());
		Assert.assertEquals(response.jsonPath().getString("postalCode"),contact.getPostalCode());
		Assert.assertEquals(response.jsonPath().getString("country"),contact.getCountry());
		Assert.assertNotNull(response.jsonPath().getString("_id"));
		Assert.assertNotNull(response.jsonPath().getString("owner"));
		
		//fetch the userids
		String userId=response.jsonPath().getString("_id");
		System.out.println("===========User Id=========="+userId);
		
		//2.	GET : Fetch the same user using the same userid
		Response responseGET = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("_id"),userId);
		
		//3. UPDATE: the user using the same userId
		contact.setFirstName("Revathy Automation");
		contact.setLastName("Naveen Automation");
		Response responsePUT = restClient.patch(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contact, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		//The latest response should be got
		Assert.assertEquals(responsePUT.jsonPath().getString("_id"),userId);
		Assert.assertEquals(responsePUT.jsonPath().getString("firstName"),"Revathy Automation");
		Assert.assertEquals(responsePUT.jsonPath().getString("lastName"),"Naveen Automation");
		Assert.assertTrue(responsePUT.statusLine().contains("OK"));
		Assert.assertEquals(responsePUT.statusCode(),200);
		
		/*
		//2.	GET : Fetch the same user using the same userid for the updated values
		responseGET = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("_id"),userId);
		Assert.assertEquals(response.jsonPath().getString("firstName"),"Revathy Automation");
		Assert.assertEquals(response.jsonPath().getString("lastName"),"Naveen Automation");
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(responsePUT.statusCode(),200);
		
		//The complete workflow should be a part of a single test	*/
	}
	
	//This needs to be revisted
	
	
}
