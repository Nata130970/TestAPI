package pojo;

import io.restassured.RestAssured;
import io.restassured.authentication.OAuth2Scheme;

import static io.restassured.RestAssured.given;

public class Authentification {

    public void Auth (String token){

        OAuth2Scheme auth = new OAuth2Scheme();
        auth.setAccessToken(token);
        RestAssured.authentication=auth;
    }
    public void getToken(){
        String token = given()
        .when()
            .post("/auth")
        .then()
            .extract()
            .jsonPath().getString("token");

    }
}
