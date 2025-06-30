package com.qa.api.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

//RestAssured FW Session 5,5c
public class SchemaValidator {

	public static boolean validateSchema(Response response, String schemaFileName) {
		try {
			response.then()
						.assertThat()
							.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaFileName));
			System.out.println("Schema Validation is passed!!!");
			return true;
		} catch (Exception ex) {
			System.out.println("Schema Validation is failed!!!!" + ex.getMessage());
			return false;
		}
	}
}
