package com.api.training;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegressionTest1Suite extends RestServices {
    @BeforeClass
    public static void setBaseURI(){
        RestAssured.baseURI="http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089";

    }
    public String getAccessToken(String username,String password){
        Response response =given().auth().basic("my-trusted-client","secret")
                .queryParam("grant_type","password")
                .queryParam("username",username)
                .queryParam("password",password)
                .when().post("/oauth/token");
        response.then().statusCode(200);
        response.prettyPrint();
        return response.then().extract().path("access_token").toString();


    }
    @Test
    public void blogTest(){

        String token=getAccessToken("admin","admin");
        JsonObject payload=new JsonObject();
        System.out.println(payload);
        payload.addProperty("body","my india");
        payload.addProperty("title","great");

        Response response=given().contentType(ContentType.JSON).log().all()
                .auth().basic("my-trusted-client","secret")
                .queryParam("access_token",token)
                .when().body(payload)
                .post("/post");
       //int id =response.then().extract().path("id"); there is
        //no id in post response
      // System.out.println(id);
        //assertion
        response.then().body("message",
                is(equalTo("Post was published"))).statusCode(200);
        //getservice
       Response response1=given().contentType("application/json").log().all()
                .auth().basic("my-trusted-client","secret")
                .queryParam("access_token",token).when()
                .get("/posts");
       // deteteservice
        given().contentType("application/json").log().all()
                .auth().basic("my-trusted-client","secret")
                .queryParam("access_token",token).when()
                .delete("/post/");



    }




}
