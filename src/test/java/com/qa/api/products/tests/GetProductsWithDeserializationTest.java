package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.fakeapi.Product;
import com.qa.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//Rest Assured FW Session 4, 4a
//The response is array of products(ie Product[])
public class GetProductsWithDeserializationTest extends BaseTest{

	
	@Test
	public void getProductsTest() {
		Response response=
		restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Product[] product=JsonUtils.deserialize(response, Product[].class);
		for(Product p:product) {
			System.out.println("id:"+p.getId());
			System.out.println("title:"+p.getTitle());
			System.out.println("price:"+p.getPrice());
			System.out.println("description:"+p.getDescription());
			System.out.println("image:"+p.getImage());
			System.out.println("category:"+p.getCategory());
			
			System.out.println("rate:"+p.getRating().getRate());
			System.out.println("count:"+p.getRating().getCount());
				
		}
	}
}
