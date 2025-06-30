package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.gorest.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//Rest Assured FW Session 4, 4b
//The response is just a single object(ie User)
public class GetAUserWithDeserializationTest2 extends BaseTest {

	private static String tokenId;

	@BeforeClass
	public void setUpToken() {
		tokenId = "5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01";
		ConfigManager.set("bearertoken", tokenId);
	}

	@Test
	public void createUserPOJOTest() {
		User user = new User(null,"Priyanka", StringUtils.getRandomEmailId(), "female", "active");
		// the null will be ignored during serialization during the creation of the User object
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), "Priyanka");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		String userId = response.jsonPath().getString("id");

		// 2. GET: fetch the user using the same user id:
		Response responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		User userResponse=JsonUtils.deserialize(responseGET, User.class);
		Assert.assertEquals(userResponse.getName(),user.getName());
	}
}