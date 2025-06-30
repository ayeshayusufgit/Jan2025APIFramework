package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.fakeapi.Product;
import com.qa.api.pojo.gorest.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//Rest Assured FW Session 4, 4c
//Assignment
//The response is just a array of objects(ie User[])
public class GetMultipleUsersWithDeserializationTest extends BaseTest {

	private static String tokenId;

	@BeforeClass
	public void setUpToken() {
		tokenId = "5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01";
		ConfigManager.set("bearertoken", tokenId);
	}

	@Test
	public void createUserPOJOTest() {
		
		Response responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGET.getStatusCode(), 200);
		User[] userResponse=JsonUtils.deserialize(responseGET, User[].class);
		
		for(User user:userResponse) {
			System.out.println("id:"+user.getId());
			System.out.println("name:"+user.getName());
			System.out.println("email:"+user.getEmail());
			System.out.println("status:"+user.getStatus());
			System.out.println("==========================");
		}
	}
}