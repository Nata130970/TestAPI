package asudd;

//https://invest-map-nnov.com/using-rest-assured-test-oauth-2


import io.restassured.RestAssured;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.Specifications;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestTest {
    public String authURL = "http://auth.asudd.si.by/auth/realms/asudd/protocol/openid-connect/";

    public String clientId = "Postman";
    public String secret="4a9d762b-a50f-41e6-b22d-8ed271bb911f";
    public String grantType = "client_credentials";

    public String token = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzMDcwZDI1ZS02MWI0LTRiZGUtOTU3Zi02ZjRmMDMwZDY4OTkifQ.eyJqdGkiOiI1YjI2NDAyOC05ZWFkLTQ1NDktOTMxMS0zNTRlZTM1OTAwNWMiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNjUyMjcwNjg5LCJpc3MiOiJodHRwOi8vYXV0aC5hc3VkZC5zaS5ieS9hdXRoL3JlYWxtcy9hc3VkZCIsImF1ZCI6Imh0dHA6Ly9hdXRoLmFzdWRkLnNpLmJ5L2F1dGgvcmVhbG1zL2FzdWRkIiwidHlwIjoiUmVnaXN0cmF0aW9uQWNjZXNzVG9rZW4iLCJyZWdpc3RyYXRpb25fYXV0aCI6ImF1dGhlbnRpY2F0ZWQifQ.PFYXhwAWHA5fJlJSJ-rTDZ6i91mLvatSDHQe0SfxF60";
    public  String URL = "http://api.asudd.si.by/api/";

    @Test
    public void allCityTest(){

        //String token=getToken();
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzMDcwZDI1ZS02MWI0LTRiZGUtOTU3Zi02ZjRmMDMwZDY4OTkifQ.eyJqdGkiOiI1YjI2NDAyOC05ZWFkLTQ1NDktOTMxMS0zNTRlZTM1OTAwNWMiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNjUyMjcwNjg5LCJpc3MiOiJodHRwOi8vYXV0aC5hc3VkZC5zaS5ieS9hdXRoL3JlYWxtcy9hc3VkZCIsImF1ZCI6Imh0dHA6Ly9hdXRoLmFzdWRkLnNpLmJ5L2F1dGgvcmVhbG1zL2FzdWRkIiwidHlwIjoiUmVnaXN0cmF0aW9uQWNjZXNzVG9rZW4iLCJyZWdpc3RyYXRpb25fYXV0aCI6ImF1dGhlbnRpY2F0ZWQifQ.PFYXhwAWHA5fJlJSJ-rTDZ6i91mLvatSDHQe0SfxF60";
        RestAssured.baseURI = URL;
//        pojo.Specifications.installSpecification(pojo.Specifications.requestSpec(URL), Specifications.responseSpec(200));
        Map<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer " + token);
        header.put("accept","*/*");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Connection","keep-alive");

        Response response = given()
                .headers(header)
                .when()
                .get("Cities/GetAll")
                .then().log().all()
                .statusCode(200)
                .extract().response();


    }

    public String getToken(){
        RestAssured.baseURI = authURL;
        pojo.Specifications.installCode(pojo.Specifications.responseSpec(200));

           String access_token  = given()
                .formParam("client_id",clientId)
                .formParam("client_secret",secret)
                .formParam("grant_type",grantType)
                .when()
                .post("token")
                 .then()
                 .extract()
                 .jsonPath().getString("access_token");
           return access_token;

    }
    @Test
    public void getUserTest() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzMDcwZDI1ZS02MWI0LTRiZGUtOTU3Zi02ZjRmMDMwZDY4OTkifQ.eyJqdGkiOiI1YjI2NDAyOC05ZWFkLTQ1NDktOTMxMS0zNTRlZTM1OTAwNWMiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNjUyMjcwNjg5LCJpc3MiOiJodHRwOi8vYXV0aC5hc3VkZC5zaS5ieS9hdXRoL3JlYWxtcy9hc3VkZCIsImF1ZCI6Imh0dHA6Ly9hdXRoLmFzdWRkLnNpLmJ5L2F1dGgvcmVhbG1zL2FzdWRkIiwidHlwIjoiUmVnaXN0cmF0aW9uQWNjZXNzVG9rZW4iLCJyZWdpc3RyYXRpb25fYXV0aCI6ImF1dGhlbnRpY2F0ZWQifQ.PFYXhwAWHA5fJlJSJ-rTDZ6i91mLvatSDHQe0SfxF60";
        RestAssured.baseURI = URL;
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("Cities/GetAll")
                .then()
                .statusCode(200);
    }

}
