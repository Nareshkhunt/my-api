package com.api.training;


import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RegressionTestSuite extends RestServices {
    @Test
    public void userRegisterTest(){
        Response response=null;
        JsonObject payload=new JsonObject();
        payload.addProperty("username","vedant17");
        payload.addProperty("passwordConfirmation","vedant3");
        payload.addProperty("password","vedant3");


        response=postService(payload,"http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089/register");
      response.prettyPrint();
        int id = response.then().extract().path("id");
        String msg1=response.then().extract().path("message");
        System.out.println(msg1);
        System.out.println(id);
        //complete hamcrest
        assertThat(msg1,is(equalToIgnoringCase("successful")));
        assertThat(response.getStatusCode(),is(equalTo(200)));

        response=getByIdServices("http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089/register/",id);
        response.then().statusCode(200);

        response=deleteByIdServices("http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089/register/",id);
        String msg2=response.then().extract().path("message");
        System.out.println(msg2);
            //hamcrst
                assertThat(msg2,is(containsString("User has been removed")));
                assertThat(response.getStatusCode(),is(equalTo(200)));
                //rest assured
        response.then()
              .body(containsString(msg2))
               .statusCode(200);
        //rest assured
        response.then().body("message",
                is(equalTo("User has been removed "))).statusCode(200);

        //hamcrest
        assertThat(msg2,equalToIgnoringCase("User has been removed "));
        assertThat(response.getStatusCode(),is(equalTo(200)));

    }

    @BeforeClass
    public static void setBaseURI(){
        RestAssured.baseURI="http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089";

    }
    @Test
    public void blogTest(){
        String token=getAccessToken("admin","admin");
        JsonObject payload=new JsonObject();
        System.out.println(payload);
        payload.addProperty("body","welcome to java world");
        payload.addProperty("title","pear");

     Response response=given().contentType(ContentType.JSON).log().all()
                .auth().basic("my-trusted-client","secret")
                .queryParam("access_token",token)
                .when().body(payload)
                .post("/post");
     response.then().body("message",
             is(equalTo("Post was published"))).statusCode(200);
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



}


