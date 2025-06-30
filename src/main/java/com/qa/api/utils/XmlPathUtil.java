package com.qa.api.utils;

import java.util.List;
import java.util.Map;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

//RestAssured FW Session 5a

public class XmlPathUtil {

	private static XmlPath getXmlPath(Response response) {
		String responseBody=response.getBody().asString();
		return new XmlPath(responseBody);
	}
	public static <T> T readValue(Response response,String xmlPathExpression) {//$.id=>123
		XmlPath xmlPath= getXmlPath(response);
		return xmlPath.get(xmlPathExpression);
	}
	
	public static <T> List<T> readList(Response response,String xmlPathExpression) {//$.id=>123
		XmlPath xmlPath= getXmlPath(response);
		return xmlPath.getList(xmlPathExpression);
	}
	
	//This is mostly not used so can be removed or commented
//	public static <T> List<Map<String,T>> readListOfMaps(Response response,String xmlPathExpression) {//$.id=>123
//		XmlPath xmlPath= getXmlPath(response);
//		return xmlPath.getList(xmlPathExpression);
//	}
}