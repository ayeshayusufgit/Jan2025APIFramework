package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//Rest Assured FW Session 4, 4d
public class ProductAPITestWithJsonPath extends BaseTest{
	
	@Test
	public void getProductTest() {
		Response response=restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		
		List<Number> prices=JsonPathValidatorUtil.readList(response, "$.[?(@.price>50)].price");
		System.out.println("prices:"+prices);
		
		
		List<Number> ids=JsonPathValidatorUtil.readList(response, "$.[?(@.price>50)].id");
		System.out.println("ids:"+ids);
		
		List<Number> rates=JsonPathValidatorUtil.readList(response, "$.[?(@.price>50)].rating.rate");
		System.out.println("rates:"+rates);
		
		List<Number> ratingsCount=JsonPathValidatorUtil.readList(response, "$.[?(@.price>50)].rating.count");
		System.out.println("ratingsCount:"+ratingsCount);
		
		//On fetching 2 or more attributes returns a list of Maps of attributes
		List<Map<String,Object>> idTitleList=JsonPathValidatorUtil.readList(response, "$.[*].['id','title']");
		System.out.println("idTitleList:"+idTitleList);
		
		List<Map<String,Object>> idTitleCatList=JsonPathValidatorUtil.readList(response, "$.[*].['id','title','category']");
		System.out.println("idTitleCatList:"+idTitleCatList);
		
		List<Map<String,Object>> jewIdTitleList=JsonPathValidatorUtil.readList(response, "$.[?(@.category=='jewelery')]");
		System.out.println("jewIdTitleList:"+jewIdTitleList);
		
		
		Double minPrice=JsonPathValidatorUtil.readValue(response, "min($.[*].price)");
		System.out.println("min price:"+minPrice);
		
	}
}
