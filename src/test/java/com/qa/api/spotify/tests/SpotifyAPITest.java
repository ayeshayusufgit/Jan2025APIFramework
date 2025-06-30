package com.qa.api.spotify.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//RestAssured FW Session 4,HW 
public class SpotifyAPITest extends BaseTest{

	private String accessToken;
	
	@BeforeMethod
	public void getOAuth2Token() {
		Response response=restClient.post(BASE_URL_SPOTIFY_OAUTH2, SPOTIFY_OAUTH2_TOKEN_ENDPOINT, ConfigManager.get("clientid_spotify"), ConfigManager.get("clientsecret_spotify"), ConfigManager.get("granttype_spotify"), ContentType.URLENC);
		accessToken=response.jsonPath().getString("access_token");
		System.out.println("Access Token:"+accessToken);
		ConfigManager.set("bearertoken_spotify", accessToken);
	}
	
	@Test
	public void getTopTracksTest() {
		
		Response responseGET=restClient.get(BASE_URL_SPOTIFY, SPOTIFY_TOP_TRACKS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		//the BEARER_TOKEN in the setupRequest() should be ConfigManager.get("bearertoken_spotify")); instead of ConfigManager.get("bearertoken"));
		//for the spotify api
		Assert.assertEquals(responseGET.statusCode(), 200);		
	}
}
