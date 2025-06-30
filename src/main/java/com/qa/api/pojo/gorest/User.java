package com.qa.api.pojo.gorest;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(Include.NON_NULL)
//when a JSON file is generated from this POJO include only NON NULL values
public class User {

	//Adding id attribute but this will result in the exception with Serialization
	//so the value for id ie "null" has to be passed during object creation
	//Only the attributes/fields that are required have to supplied to the server
	
	private Integer id;
	private String name;
	private String email;
	private String gender;
	private String status;
}
