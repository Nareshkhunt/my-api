package com.api.training;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;

public class GetUserBasic {
    @Test
    public void getUserTest() {
        Response response = given()
                .when()
                .get("http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089/register");
        List<Integer> ids= response.then().extract().path("id");
        System.out.println(ids);
        //Hamcreset style
       assertThat(ids,hasItem(6));
        //restassured
         response.then().body("id",hasItem(8))
               .statusCode(200);

    }



}
