package com.qa.api.pojo.pet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//RestAssured Session 6 - HW using DDT

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

	private int id;
	private String name;
	private String status;
	private Category category;
	private List<String> photoUrls;
	private List<Tag> tags;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Category{
		private int id;
		private String name;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Tag{
		private int id;
		private String name;
	}
}
