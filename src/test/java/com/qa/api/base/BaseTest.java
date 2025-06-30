package com.qa.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.qa.api.client.RestClient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	protected RestClient restClient;
	
	//non test files should be moved to src/main/java
	//test files should be moved to src/test/java
	
	//******All the API Base Urls have to be maintained here*******
	//So that it doesn't have to be added in the child test classes
	
	//protected is used as only the urls can be inherited to the child classes
	protected final static String BASE_URL_GOREST="https://gorest.co.in";
	protected final static String BASE_URL_CONTACTS="https://thinking-tester-contact-list.herokuapp.com";
	protected final static String BASE_URL_REQRES="https://reqres.in";
	protected final static String BASE_URL_BASIC_AUTH="https://the-internet.herokuapp.com";
	protected final static String BASE_URL_PRODUCTS="https://fakestoreapi.com";
	protected final static String BASE_URL_AMADEUS="https://test.api.amadeus.com";
	protected final static String BASE_URL_SPOTIFY_OAUTH2="https://accounts.spotify.com";
	protected final static String BASE_URL_SPOTIFY="https://api.spotify.com";
	protected final static String BASE_URL_ERGAST_CIRCUIT="http://ergast.com";
	protected final static String BASE_URL_PET="https://petstore3.swagger.io";
		
	//******API ENDPOINTS*******//
	protected final static String GOREST_USERS_ENDPOINT="/public/v2/users";
	protected final static String CONTACTS_LOGIN_ENDPOINT="/users/login";
	protected final static String CONTACTS_ENDPOINT="/contacts";
	protected final static String REQRES_ENDPOINT="/api/users";
	protected final static String BASIC_AUTH_ENDPOINT="/basic_auth";
	protected final static String PRODUCTS_ENDPOINT="/products";
	protected final static String AMADEUS_OAUTH2_TOKEN_ENDPOINT="/v1/security/oauth2/token";
	protected final static String AMADEUS_FLIGHT_DESTINATION_ENDPOINT="/v1/shopping/flight-destinations";
	protected final static String SPOTIFY_OAUTH2_TOKEN_ENDPOINT="/api/token";
	protected final static String SPOTIFY_TOP_TRACKS_ENDPOINT="/v1/albums/4aawyAB9vmqN3uQ7FjRGTy";
	protected final static String ERGAST_CIRCUIT_ENDPOINT="/api/f1/2017/circuits.xml";
	protected final static String PET_ENDPOINT="/api/v3/pet";
	
	
	@BeforeSuite
	public void setupAllureReport() {
		RestAssured.filters(new AllureRestAssured());
	}
	
	@BeforeTest
	public void setUp() {
		restClient=new RestClient();
	}
}
