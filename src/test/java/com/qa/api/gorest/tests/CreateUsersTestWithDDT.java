package com.qa.api.gorest.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.gorest.User;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//RestAssured Session 6
public class CreateUsersTestWithDDT extends BaseTest{
	
	private static String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId="5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01";
		ConfigManager.set("bearertoken", tokenId);
	}
	
//	@DataProvider
//	public Object[][] getUserData(){
//		return new Object[][] {
//			{"Priyanka","female","active"},
//			{"Ranjit","male","inactive"},
//			{"Elmar","male","active"}
//		};
//	}
	
	@DataProvider
	public  Object[][] getUserExcelData() {
		return ExcelUtil.readData(AppConstants.CREATE_USER_SHEET_NAME);
	}

	@Test(dataProvider = "getUserExcelData")
	public void createUserPOJOTest(String name,String gender,String status) {
		//null is added for the id attribute while creating the User object 
		User user=new User(null,name,StringUtils.getRandomEmailId(),gender,status);
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),name);
		Assert.assertEquals(response.jsonPath().getString("gender"),gender);
		Assert.assertEquals(response.jsonPath().getString("status"),status);
		
		Assert.assertNotNull(response.jsonPath().getString("id"));
		ChainTestListener.log("User Id:"+response.jsonPath().getString("id"));
	}
	
	@Test(enabled=false)
	public void createUserWithJsonStringTest() {
		//The email is unique,need to give a new email each time a user is getting created
		
		String userJson = "{\n"
				+ "\"name\": \"naveen\",\n"
				+ "\"email\": \""+StringUtils.getRandomEmailId()+"\",\n"
				+ "\"gender\": \"male\",\n"
				+ "\"status\": \"active\"\n"
				+ "}";
		
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userJson, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),"naveen");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
	}
	
	@Test(enabled=false)//T doesnt not support of type File so the post() has to be overloaded
	
	public void createUserWithJsonFileTest() {
		File userFile=new File("./src/test/resources/jsons/user.json");
		
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userFile, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"),"Seema");
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	//HW-The same thing should be done for the contacts and the products api
	//HW-Create Pet using Pet Api using DDT
}

