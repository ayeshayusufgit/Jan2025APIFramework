package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.fakeapi.Product;
import com.qa.api.pojo.fakeapi.Product.Rating;
import com.qa.api.utils.ExcelUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

//RestAssured Session 6 - HW using DDT 
public class CreateProductsTestWithDDT extends BaseTest{
	
	private static String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId="5766180a0a5e260969f49562de5438c84be74a66752f526afeb92257a0176c01";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@DataProvider
	public  Object[][] getProductsExcelData() {
		return ExcelUtil.readData("products");
	}

	@Test(dataProvider = "getProductsExcelData")
	public void createProductPOJOTest(String productId,String productTitle,String productPrice,String productDesc,String productCat,String productImageURL,String rate,String count) {
		//null is added for the id attribute while creating the User object 
		//Product product=new Product(null,productTitle,productDesc,productCat,imageURL);
	
		
		Rating rating=Rating.builder()
							.rate(Double.parseDouble(rate))
							.count(Integer.parseInt(count))
							.build();
		
		Product product=Product.builder()
							.id(Integer.parseInt(productId))
							.title(productTitle)
							.price(Double.parseDouble(productPrice))
							.description(productDesc)
							.category(productCat)
							.image(productImageURL)
							.rating(rating)
							.build();
		
		Response response=restClient.post(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, product, null, null,AuthType.BEARER_TOKEN,ContentType.JSON);
	
		Assert.assertEquals(response.getStatusCode(),200);
		Assert.assertEquals(response.jsonPath().getString("title"),productTitle);
		Assert.assertEquals(response.jsonPath().getString("price"),productPrice);
		Assert.assertEquals(response.jsonPath().getString("description"),productDesc);
		Assert.assertEquals(response.jsonPath().getString("image"),productImageURL);
		
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		ChainTestListener.log("User Id:"+response.jsonPath().getString("id"));
	}

}

