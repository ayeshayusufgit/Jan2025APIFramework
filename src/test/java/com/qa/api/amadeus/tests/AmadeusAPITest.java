package com.qa.api.amadeus.tests;

import java.util.HashMap;
import java.util.Map;

import org.apache.groovy.util.Maps;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//RestAssured FW Session 4
public class AmadeusAPITest extends BaseTest{
	private String accessToken;
	
	
	@BeforeMethod
	public void getOAuth2Token() {
		Response response=restClient.post(BASE_URL_AMADEUS, AMADEUS_OAUTH2_TOKEN_ENDPOINT, ConfigManager.get("clientid_amadeus"), ConfigManager.get("clientsecret_amadeus"), ConfigManager.get("granttype_amadeus"), ContentType.URLENC);
		//most of the api(99%) incase of oauth2 is ContentType.URLENC but sometimes it can be ContentType.JSON also thats why the ContentType is passed
		
		accessToken=response.jsonPath().getString("access_token");
		System.out.println("Access Token:"+accessToken);
		//config.properties file bearertoken field needs to be made blank
		ConfigManager.set("bearertoken", accessToken);
		//and set on the fly in the above line
		
	}
	
	
	@Test
	public void getFlightDetailsTest() {
		//https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200
		//Query param can be added in 2 ways as below: 
		//1.
		//Maps.of("origin", "PAR", "maxPrice", "200");
		//or
		//2.
		Map<String,String> queryParams=new HashMap<String,String>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", "200");
		
		Response responseGET=restClient.get(BASE_URL_AMADEUS, AMADEUS_FLIGHT_DESTINATION_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		//Since the bearertoken is already set in the config.properties
		//AuthType.BEARER_TOKEN will be passed into the get() of the RestClient
		//and the bearertoken will be picked accordingly and set in the switch/case of the setupRequest()
		//Thus another case of OAUTH2 can be removed from the switch/case of the setupRequest()		
		Assert.assertEquals(responseGET.statusCode(), 200);		
	}
	
	//HW the same thing needs to be done in the spotify api
	//validate 200 status code
	//validate with statusline
	//not so imp but some imp headers(response headers) can be validated
}
