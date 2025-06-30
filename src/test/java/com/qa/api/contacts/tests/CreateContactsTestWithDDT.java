package com.qa.api.contacts.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.contacts.Contact;
import com.qa.api.pojo.contacts.Credentials;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//RestAssured Session 6 - HW using DDT
public class CreateContactsTestWithDDT extends BaseTest{
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
	
	@Test(dataProvider = "getUserDataFromExcel")
	public void createUserPOJOTest(String firstName,String lastName,String mobile,String street1,String street2,String city,String province,String postalCode,String country) {
		String email=StringUtils.getRandomEmailId();
		Contact contact= Contact.builder()
						.firstName(firstName)
						.lastName(lastName)
						.email(email)
						.phone(mobile)
						.street1(street1)
						.street2(street2)
						.city(city)
						.stateProvince(province)
						.postalCode(postalCode)
						.country(country).build();
				
		Response responsePOST=restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contact, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("firstName"),contact.getFirstName());
		Assert.assertEquals(responsePOST.jsonPath().getString("lastName"),contact.getLastName());
		Assert.assertNotNull(responsePOST.jsonPath().getString("_id"));
		Assert.assertEquals(responsePOST.getStatusCode(),201);
	}
	
	@DataProvider
	public Object[][] getUserDataFromExcel(){
		return ExcelUtil.readData("contacts");
	}
	
	@Test
	
	public void creatUserWithJsonFileTest() {
		File contactsFile=new File("./src/test/resources/jsons/contact.json");
		
		Response responsePOST=restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contactsFile, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("firstName"),"Zainab");
		Assert.assertEquals(responsePOST.jsonPath().getString("lastName"),"Naveen");
		Assert.assertNotNull(responsePOST.jsonPath().getString("_id"));
	}
}
