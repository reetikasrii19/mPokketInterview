package com.interview.mPokket.testcases;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.HashMap;

public class CreateEmployee {

	public static Object idString =null;

	@BeforeSuite
	public void setup() {

		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
	}


	@Test(priority=1,invocationCount = 1)
	public void createEmp() {

		HashMap<String,String> reqBody= new HashMap<String, String>();
		reqBody.put("name", "Tester1");
		reqBody.put("salary", "123");
		reqBody.put("age", "23");

		Response res=
				given().body(reqBody).post("/create");
		//res.prettyPrint();
		
		Assert.assertEquals(res.statusCode(), 200);
		if(res.statusCode()==200)
		{

			String json=res.asString();
			JSONObject jsonObject = new JSONObject(json);
			System.out.println(jsonObject);
			JSONObject data = (JSONObject) jsonObject.get("data");
			idString = data.get("id");
		}
		
		Assert.assertEquals(res.statusCode(), 200);


	}


	@Test(priority=2,dependsOnMethods = {"createEmp"})
	public void CheckEmp() {

		Response res1= 
				given().get("/employee/"+idString);
		Object name=null;
		
		if(res1.statusCode()==200)
		{

			String json=res1.asString();
			JSONObject jsonObject = new JSONObject(json);
			System.out.println(jsonObject);
			JSONObject data = (JSONObject) jsonObject.get("data");
			name = data.get("employee_name");
		}

		System.out.println(res1.statusCode());

		Assert.assertEquals(name, "Tester1");


	}

	@Test(priority=3,dependsOnMethods = {"createEmp"})
	public void DelEmp() {

		Response res= 
				given().delete("/delete/"+idString);

		System.out.println(res.statusCode());

		Assert.assertEquals(res.statusCode(), 200);


	}

}
