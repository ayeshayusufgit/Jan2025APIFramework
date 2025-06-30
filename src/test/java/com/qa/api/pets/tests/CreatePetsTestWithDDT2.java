package com.qa.api.pets.tests;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.fakeapi.Product;
import com.qa.api.pojo.fakeapi.Product.Rating;
import com.qa.api.pojo.pet.Pet;
import com.qa.api.pojo.pet.Pet.Tag;
import com.qa.api.utils.ExcelUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;


//RestAssured Session 6 - HW using DDT 

public class CreatePetsTestWithDDT2 extends BaseTest {

	@DataProvider
	public Object[][] getPetsDataFromExcel(){
		return ExcelUtil.readData("pets");
	}
	
	
	@Test(dataProvider = "getPetsDataFromExcel")
	public void createPetTest(String categoryId,String categoryName,String photoUrl1,String photoUrl2,String tagId1,String tagName1,String tagId2,String tagName2,String petId,String petName,String petStatus) {

		
		Pet.Category category = new Pet.Category(Integer.parseInt(categoryId), categoryName);

		List<String> photoUrls = Arrays.asList(photoUrl1, photoUrl2);

		System.out.println(tagName1);
		Pet.Tag tag1 = new Pet.Tag(Integer.parseInt(tagId1), tagName1);
		Pet.Tag tag2 = new Pet.Tag(Integer.parseInt(tagId2), tagName2);

		List<Tag> tags = Arrays.asList(tag1, tag2);

		Pet pet = new Pet(Integer.parseInt(petId), petName, petStatus, category, photoUrls, tags);

		// Serialization-when POJO is converted to JSON
		Response response = restClient.post(BASE_URL_PET, PET_ENDPOINT, pet, null, null, AuthType.NO_AUTH,
				ContentType.JSON);
		
		Assert.assertEquals(response.getStatusCode(),200);
	}
}
