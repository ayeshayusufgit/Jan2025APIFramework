package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.gorest.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

//RestAssured FW Session 5,5d
public class GoRestUserAPISchemaTest extends BaseTest{
	
	@Test
	public void getUsersAPISchemaTest() {
		
//			RestAssured.baseURI="https://gorest.co.in/";
//			
//			RestAssured.given()
//							.header("Authorization","Bearer 5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01")
//						.when()
//							.get("/public/v2/users")
//						.then()
//							.assertThat()
//								.statusCode(200)
//								.and()
//								.body(matchesJsonSchemaInClasspath("getUsersSchema.json"));
			
			ConfigManager.set("bearertoken", "5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01");
			Response response=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertTrue(SchemaValidator.validateSchema(response,"./schemas/getUsersSchema.json"));
			//if the schema file is directly under test/resources then only the file name has to be provided ie "getUsersSchema.json"
			//else
			//if its under a folder like test/resources/schemas then "./schemas/getUsersSchema.json" has to be provided			
		}

		

		@Test
		public void createUserAPISchemaTest() {
//			RestAssured.baseURI="https://gorest.co.in";
//			
//			RestAssured.given()
//							.header("Authorization","Bearer 5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01")
//							.body(new File("./src/test/resources/createUserSchema.json"))
//							.contentType(ContentType.JSON)
//						.when()
//							.post("/public/v2/users")
//						.then()
//							.assertThat()
//								.statusCode(201)
//								.and()
//								.body(matchesJsonSchemaInClasspath("createUserSchema.json"));
			
			ConfigManager.set("bearertoken", "5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01");
			User user=new User(null,"Priyanka",StringUtils.getRandomEmailId(),"female","active");
			Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
			Assert.assertTrue(SchemaValidator.validateSchema(response,"./schemas/createuserschema.json"));
		}
		
		//HW
		//create product.schema
		//get product.schema
		
}
