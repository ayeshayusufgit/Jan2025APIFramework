package com.qa.api.utils;

import org.checkerframework.checker.units.qual.t;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class JsonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	// The Class<T> is because it can be of any type of class like Product or
	// Contact or User etc
	public static <T> T deserialize(Response response, Class<T> targetClass) {
		// During deserialization an Exception is thrown, so it needs to be handled by
		// using a try-catch block

		try {
			return objectMapper.readValue(response.getBody().asString(), targetClass);
		} catch (Exception e) {
			throw new RuntimeException("Deserialization is failed..." + targetClass.getName());
		}
	}
}
