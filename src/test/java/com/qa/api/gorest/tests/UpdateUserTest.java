package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.gorest.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest {

	//1.Create a user - POST(No hardcoded value should be there)
	@Test
	public void updateUserTest() {
		//1.	Create a user - POST
		//User user=new User("Priyanka",StringUtils.getRandomEmailId(),"female","active");
		
		User user=
		User.builder()
			.name("Revathy")
			.email(StringUtils.getRandomEmailId())
			.status("active")
			.gender("female")
			.build();
		
		Response responsePOST=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("name"),"Revathy");
		Assert.assertNotNull(responsePOST.jsonPath().getString("id"));
		
		//fetch the userids
		String userId=responsePOST.jsonPath().getString("id");
		System.out.println("===========User Id=========="+userId);
		
		//2.	GET : Fetch the same user using the same userid
		Response responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("id"),userId);
		
		//3. UPDATE: the user using the same userId
		user.setName("Revathy Automation");
		user.setStatus("inactive");
		Response responsePUT = restClient.put(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		//The latest response should be got
		Assert.assertEquals(responsePUT.jsonPath().getString("id"),userId);
		Assert.assertEquals(responsePUT.jsonPath().getString("name"),"Revathy Automation");
		Assert.assertEquals(responsePUT.jsonPath().getString("status"),"inactive");
		Assert.assertTrue(responsePUT.statusLine().contains("OK"));
		
		//4.	GET : Fetch the same user using the same userid for the updated values
		responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGET.jsonPath().getString("id"),userId);
		Assert.assertEquals(responseGET.jsonPath().getString("name"),"Revathy Automation");
		Assert.assertEquals(responseGET.jsonPath().getString("status"),"inactive");
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		
		//The complete workflow should be a part of a single test	
	}
	
	
}
