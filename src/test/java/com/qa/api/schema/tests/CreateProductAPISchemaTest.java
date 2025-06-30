package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.SchemaValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//Assigment, RestAssured FW Session 5
public class CreateProductAPISchemaTest extends BaseTest {
	
	@Test
	public void createProductAPISchemaTest() {
//		RestAssured.baseURI="https://gorest.co.in";
//		
//		RestAssured.given()
//						.header("Authorization","Bearer 5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01")
//						.body(new File("./src/test/resources/createUserSchema.json"))
//						.contentType(ContentType.JSON)
//					.when()
//						.post("/public/v2/users")
//					.then()
//						.assertThat()
//							.statusCode(201)
//							.and()
//							.body(matchesJsonSchemaInClasspath("createUserSchema.json"));
		
		//ConfigManager.set("bearertoken", "5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01");
		
		String productStr="{\r\n"
				+ "  \"id\": 1,\r\n"
				+ "  \"title\": \"Biba Salwar Suits\",\r\n"
				+ "  \"price\": 1000,\r\n"
				+ "  \"description\": \"Salwar Suits\",\r\n"
				+ "  \"category\": \"Clothes\",\r\n"
				+ "  \"image\": \"http://example.com\"\r\n"
				+ "}";
		Response response=restClient.post(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, productStr, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertTrue(SchemaValidator.validateSchema(response,"./schemas/createproductschema.json"));
	}
}
