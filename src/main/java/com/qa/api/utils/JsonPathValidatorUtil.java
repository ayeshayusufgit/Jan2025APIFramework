package com.qa.api.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.response.Response;

//This is as per Naveens code Rest Assured FW Session 4

public class JsonPathValidatorUtil {

	private static String getJsonResponseAsString(Response response) {
		return response.body().asString();	
	}

	public static <T> T readValue(Response response,String jsonPath) {//$.id=>123
		ReadContext ctx=JsonPath.parse(getJsonResponseAsString(response));
		return ctx.read(jsonPath);
	}
	
	public static <T> List<T> readList(Response response,String jsonPath) {//$.id=>123
		ReadContext ctx=JsonPath.parse(getJsonResponseAsString(response));
		return ctx.read(jsonPath);
	}
	
	public static <T> List<Map<String,T>> readListOfMaps(Response response,String jsonPath) {//$.id=>123
		ReadContext ctx=JsonPath.parse(getJsonResponseAsString(response));
		return ctx.read(jsonPath);
	}
}
