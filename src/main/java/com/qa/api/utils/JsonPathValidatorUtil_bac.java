package com.qa.api.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.response.Response;

public class JsonPathValidatorUtil_bac {

	private static ReadContext getReadContext(Response response) {
		String jsonResponse=response.body().asString();
		return JsonPath.parse(jsonResponse);//returns of type ReadContext	
	}
	
	//The return from the read method can be a single value or List of values or List of Maps 
	//Object as return type can also be written but it'll be slow T is preferred
	//The value of T can be boolean, int, double or String(any type of data)
	
	public static <T> T readValue(Response response,String jsonPath) {//$.id=>123
		/*String jsonResponse=response.body().asString();
		ReadContext ctx=JsonPath.parse(jsonResponse);*/
		//These 2 lines are getting repetitive, a generic function can be written
		
		return getReadContext(response).read(jsonPath);
	}
	
	public static <T> List<T> readList(Response response,String jsonPath) {//$.id=>123
		return getReadContext(response).read(jsonPath);
	}
	
	public static <T> List<Map<String,T>> readListOfMaps(Response response,String jsonPath) {//$.id=>123
		return getReadContext(response).read(jsonPath);
	}
}
