package com.lulobank;

import java.util.HashMap;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

/**
 * Unit test for simple App.
 */
public class AppTest{
    HashMap<String,String> map = new HashMap<String, String>();

    /**
     * Validates POST to create a new user
     */

    @Test (priority=1)
    public void createNewUser() {
        Faker faker = new Faker();
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
        Response response = given().log().all().headers("Content-Type","application/json","app-id",com.lulobank.Utilities.getAppId())
        .body(com.lulobank.Utilities.createBody(faker.name().firstName(),faker.name().lastName(),faker.internet().emailAddress())).when().post("/user/create").
        then().log().all().assertThat().statusCode(200).extract().response();

        String responseString = response.asString();
        JsonPath js = new JsonPath(responseString);

        map.put("id", js.get("id").toString());
        map.put("firstName", js.get("firstName").toString());
        map.put("lastName", js.get("lastName").toString());
        map.put("email", js.get("email").toString());

        Reporter.log("Status: "+ response.getStatusCode());
        Reporter.log("created id: "+ js.get("id"));
        Reporter.log("created firstName: "+ js.get("firstName"));
        Reporter.log("created lastName: "+ js.get("lastName"));
        Reporter.log("created email: "+ js.get("email"));  
               
    }

    /**
     * Validates GET to show the created user
     */

     @Test (priority = 2)
     public void getUser() {

        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
        Response response = given().log().all().params("id", map.get("id"),"created",map.get("id"))
        .headers("Content-Type","application/json","app-id",com.lulobank.Utilities.getAppId())
        .when().get("/user/").then().log().all().assertThat().statusCode(200).extract().response();

        String responseString = response.asString();
        JsonPath js = new JsonPath(responseString);       

        Reporter.log("Status: "+ response.getStatusCode());
        Reporter.log("id: "+ js.get("data.id")); 
        Reporter.log("first Name: "+ js.get("data.firstName"));
        Reporter.log("last Name: "+ js.get("data.lastName"));
        
     }

     /**
      * Validates PUT to modify an user
      */
      @Test (priority = 3)
      public void modifyUser() {

        Faker faker = new Faker();
        String resource = "/user/"+map.get("id");
        Response response = given().log().all().headers("Content-Type","application/json","app-id",com.lulobank.Utilities.getAppId()).body(com.lulobank.Utilities.modifyBody(faker.name().firstName(),faker.name().lastName())).
        when().put(resource).then().log().all().assertThat().statusCode(200).extract().response();

        String responseString = response.asString();
        JsonPath js = new JsonPath(responseString);

        map.put("modifiedFirstName", js.get("firstName").toString());
        map.put("modifiedLastName", js.get("lastName").toString());

        Reporter.log("Status: "+ response.getStatusCode());
        Reporter.log("Modified First Name: "+ js.get("firstName").toString());
        Reporter.log("Modified Last Name: "+ js.get("lastName").toString());
        Reporter.log("Updated At: "+ js.get("updatedDate").toString());
      }

      /**
     * Validate DELETE to delete an user
     */
    @Test (priority=4)
    public void deleteUser() {
        Response response = given().log().all().headers("Content-Type","application/json","app-id",com.lulobank.Utilities.getAppId()).
        when().delete("/user/"+map.get("id")).then().log().all().assertThat().statusCode(200).extract().response(); 
        String responseString = response.asString();
        JsonPath js = new JsonPath(responseString);       
        Reporter.log("Status: "+ response.getStatusCode());
        Reporter.log("Deleted Id: "+ js.get("id"));
    }

    
}
