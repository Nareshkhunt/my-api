package com.api.training;

import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestServices {
    public Response postService(JsonObject payload, String uri) {
        return given().contentType(ContentType.JSON).body(payload).when().post(uri);
    }
    public Response getServices(String uri){
        return  given().contentType("application/json").when().get(uri);
    }

    public Response getByIdServices(String uri,int id){
        return  given().contentType("application/json").when().get(uri+id);
    }

    public Response deleteByIdServices(String uri,int id){
        return  given().contentType("application/json").when().delete(uri+id);
    }
}
