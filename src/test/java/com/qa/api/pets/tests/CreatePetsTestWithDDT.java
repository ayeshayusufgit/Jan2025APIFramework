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

//RestAssured Session 6 - HW using DDT 
public class CreatePetsTestWithDDT extends BaseTest {
	
	@Test
	public void createPetTest() {

		Pet.Category category = new Pet.Category(1, "Dog");

		List<String> photoUrls = Arrays.asList("https://ex.com", "https://dog.com");

		Pet.Tag tag1 = new Pet.Tag(1, "Red");
		Pet.Tag tag2 = new Pet.Tag(2, "Black");

		List<Tag> tags = Arrays.asList(tag1, tag2);

		Pet pet = new Pet(101, "Ronney", "available", category, photoUrls, tags);

		// Serialization-when POJO is converted to JSON
		Response response = restClient.post(BASE_URL_PET, PET_ENDPOINT, pet, null, null, AuthType.NO_AUTH,
				ContentType.JSON);
		
		response.prettyPrint();
	}
}
