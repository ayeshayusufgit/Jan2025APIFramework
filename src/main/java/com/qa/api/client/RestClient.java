package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import org.hamcrest.core.AnyOf;
import org.testng.internal.invokers.ExpectedExceptionsHolder;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.APIException;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

public class RestClient<T> {

	// define response specs:
	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);
	private ResponseSpecification responseSpec404 = expect().statusCode(404);
	private ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));

	// if a user is found it returns 200 and not found returns 404
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));

	private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {
		ChainTestListener.log("API Base Url:"+baseUrl);
		ChainTestListener.log("Auth Type:"+authType.toString());
		
		RequestSpecification request = RestAssured.given().baseUri(baseUrl).contentType(contentType).accept(contentType)
				.log().all();
		// switch/case works with String,integer,character and Enum

		switch (authType) {// switch/case is used with Enum in our case
		case BEARER_TOKEN:
			//request.header("Authorization", "Bearer " + ConfigManager.get("bearertoken_spotify"));
			request.header("Authorization", "Bearer " + ConfigManager.get("bearertoken"));
			break;
		/*case OAUTH2:
			request.header("Authorization", "Bearer " + "==Oauth2 Token==");
			break;*/
			
		case BASIC_AUTH:
			request.header("Authorization", "Basic " + generateBasicAuthToken());
			break;
		case API_KEY:
			request.header("x-api-key", "==Api Key==");
			break;
		case NO_AUTH:
			System.out.println("Auth not required");
			break;

		default:
			System.out.println("This Auth is not supported...Pls pass the right Auth Type");
			throw new APIException("==API Exception==");
		}
		return request;
	}

	private String generateBasicAuthToken() {
		String credentials=ConfigManager.get("basic_auth_username")+":"+ConfigManager.get("basic_auth_password");
		//admin:admin --> "YWRtaW46YWRtaW4=" (base64 encoded value)
		//Interview Question:How to convert the String into Base64?
		return Base64.getEncoder().encodeToString(credentials.getBytes());
	}
	private void applyParams(RequestSpecification request, Map<String, String> queryParam,
			Map<String, String> pathParam) {
		ChainTestListener.log("Query Param:"+queryParam);
		ChainTestListener.log("Path Param:"+pathParam);
		
		if (queryParam != null) {
			request.queryParams(queryParam);
		}

		if (pathParam != null) {
			request.pathParams(pathParam);
		}
	}

	// CRUD
	// get()
	// This method will be exposed to testNG and will be called by testNG
	/**
	 * This method is used to call the GET API
	 * 
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParam
	 * @param pathParam
	 * @param authType
	 * @param contentType
	 * @return it returns the Response of the GET API
	 */
	@Step("Calling the GET API with base url:{0}")
	//{0} is the index  of the parameter
	public Response get(String baseUrl, String endPoint, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		//Suppose if there is a bug such that it is giving 200 instead of 404
		//the above statement will pass so another assertion is required
		//This assertion is added in the @Test
		
		response.prettyPrint();
		return response;
	}

	// post() there is a slight difference as the body needs to be supplied also
	// The body can be a string,file,POJO or LOMBOK etc so thats why the body will
	// be of type T
	// if body is String then T will be String
	// if body is File then T will be of type file, this doesnt work, an overloaded
	// method has to be created for this
	// if body is POJO then T will be of type POJO
	// The meaning of T is any type and the return also T has to be
	// Generally Query Param in POST is very rare but Path Param can happen
	@Step("Calling the POST API")
	public <T> Response post(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
	}

	// The post() has to be overloaded with the body of type File, the T from the
	// return should be removed and should be only of type Response
	@Step("Calling the overloaded POST API")
	public Response post(String baseUrl, String endPoint, File fileBodyPath, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(fileBodyPath).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
	}
	
	//The earlier post() method doesn't have the provision for the Grant Type,Client Secret and Client Token to generate the Access Token in OAUTH2
	//The post() needs to be overloaded as to generate the Access Token in OAUTH2 the Grant Type,Client Secret and Client Token needs to be passed  
	@Step("Calling the overloaded POST API for OAUTH")
	public Response post(String baseUrl, String endPoint, String clientId,String clientSecret,String grantType, ContentType contentType) {

		Response response=RestAssured.given()
										.baseUri(baseUrl)
										.contentType(contentType)
										.formParam("grant_type", grantType)
										.formParam("client_id", clientId)
										.formParam("client_secret", clientSecret)
									  .when()
									  		.post(endPoint);
		response.prettyPrint();
		return response;
	}
	

	// Complete body has to be added with the updated fields incase of PUT()
	@Step("Calling the PUT API")
	public <T> Response put(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(body).put(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
	}

	// Complete body has to be added with the updated fields incase of PUT()
	@Step("Calling the overloaded PUT API")
	public Response put(String baseUrl, String endPoint, File fileBodyPath, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(fileBodyPath).put(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}

	// Updated fields have to be added in the body incase of PATCH()
	@Step("Calling the PATCH API")
	public <T> Response patch(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}

	// Updated fields have to be added in the body incase of PATCH()
	@Step("Calling the overloaded PATCH API")
	public Response patch(String baseUrl, String endPoint, File fileBodyPath, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(fileBodyPath).patch(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}
	
	// DELETE(), no body is required
	// On deleting 204 status code is returned
	@Step("Calling the DELETE API")
	public <T> Response delete(String baseUrl, String endPoint, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.delete(endPoint).then().spec(responseSpec204).extract().response();
		response.prettyPrint();
		return response;
	}
}
