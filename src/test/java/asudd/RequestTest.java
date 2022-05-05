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

  //  public String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJVSlE4blcxRFFILUhnTHdMQ1JZV29UR2d0YlVwbWJvX1RfRlctWkFUZkI4In0.eyJqdGkiOiI3ZGU4MjIzOC1mYThiLTQzNjUtOWU0MS05NzBmMzZkOTc0ZmIiLCJleHAiOjE2NTE3MzY3OTMsIm5iZiI6MCwiaWF0IjoxNjUxNzM1ODkzLCJpc3MiOiJodHRwOi8vYXV0aC5hc3VkZC5zaS5ieS9hdXRoL3JlYWxtcy9hc3VkZCIsImF1ZCI6WyJzd2FnZ2VyQXBwIiwicmVhbG0tbWFuYWdlbWVudCIsImFjY291bnQiXSwic3ViIjoiY2MzMjQ0NjktYjQ0Mi00YTM4LTlmYTYtZDAzMTFjNDI5YmU5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3dhZ2dlckFwcCIsImF1dGhfdGltZSI6MTY1MTczNDU1Mywic2Vzc2lvbl9zdGF0ZSI6ImZkNmY4NWY4LTVmMjgtNDU1Yy1iMWIxLTc0ODU1NjNmYWVmMSIsImFjciI6IjAiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2Ntcy5hcGkubG9jYWx0ZXN0Lm1lL3N3YWdnZXIiLCJodHRwOi8vbG9jYWxob3N0OjU2NTAyL3N3YWdnZXIvKiIsImh0dHBzOi8vYXBpLmFzdWRkLnNpLmJ5L3N3YWdnZXIiLCJodHRwOi8vYXBpLmFzdWRkLnNpLmJ5L3N3YWdnZXIiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIndyaXRlOlRyYWNvbiIsIndyaXRlOkxpY2Vuc2UiLCJ3cml0ZTpRdWVyaWVzIiwid3JpdGU6Q29udHJvbGxlcnMiLCJyZWFkOlF1ZXJpZXMiLCJyZWFkOkNvbW1hbmQiLCJ3cml0ZTpHcmVlblN0cmVldCIsIndyaXRlOkNvbnRyb2xsZXJUeXBlIiwicmVhZDpab25lcyIsInJlYWQ6RGV0ZWN0b3IiLCJvZmZsaW5lX2FjY2VzcyIsIndyaXRlOlpvbmVzIiwicmVhZDpDdXN0b21NYXAiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIndyaXRlOkRldGVjdG9yIiwicmVhZDpIb2xpZGF5IiwicmVhZDpDb250cm9sbGVycyIsInJlYWQ6VHJhY29uIiwid3JpdGU6Q29tbWFuZCIsIndyaXRlOlByb2dyYW0iLCJ3cml0ZTpDdXN0b21NYXAiLCJyZWFkOkNvbnRyb2xsZXJUeXBlIiwicmVhZDpQcm9ncmFtIiwid3JpdGU6SG9saWRheSIsIndyaXRlOkNpdGllcyIsInJlYWQ6Q2l0aWVzIiwicmVhZDpHcmVlblN0cmVldCJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsibWFuYWdlLXJlYWxtIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctdXNlcnMiLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiLCJxdWVyeS11c2VycyJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBzd2FnZ2VyX2FwaSBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJncm91cHMiOlsid3JpdGU6VHJhY29uIiwid3JpdGU6TGljZW5zZSIsIndyaXRlOlF1ZXJpZXMiLCJ3cml0ZTpDb250cm9sbGVycyIsInJlYWQ6UXVlcmllcyIsInJlYWQ6Q29tbWFuZCIsIndyaXRlOkdyZWVuU3RyZWV0Iiwid3JpdGU6Q29udHJvbGxlclR5cGUiLCJyZWFkOlpvbmVzIiwicmVhZDpEZXRlY3RvciIsIm9mZmxpbmVfYWNjZXNzIiwid3JpdGU6Wm9uZXMiLCJyZWFkOkN1c3RvbU1hcCIsInVtYV9hdXRob3JpemF0aW9uIiwid3JpdGU6RGV0ZWN0b3IiLCJyZWFkOkhvbGlkYXkiLCJyZWFkOkNvbnRyb2xsZXJzIiwicmVhZDpUcmFjb24iLCJ3cml0ZTpDb21tYW5kIiwid3JpdGU6UHJvZ3JhbSIsIndyaXRlOkN1c3RvbU1hcCIsInJlYWQ6Q29udHJvbGxlclR5cGUiLCJyZWFkOlByb2dyYW0iLCJ3cml0ZTpIb2xpZGF5Iiwid3JpdGU6Q2l0aWVzIiwicmVhZDpDaXRpZXMiLCJyZWFkOkdyZWVuU3RyZWV0Il0sInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIiLCJsb2NhbGUiOiJydSJ9.Sn9cRCiJSJGOk4k1NxjUOcGLK_X3zemCIKPLqURNCZckM1Rha_6zZehWB2-8ROzKNlBc2ed9b1_LZKkWeSI0wzts9YBO7YvXPrMe1uETImMUw4NlA_dM-lWOSpLxjDyduMwCf87RrLCJhLNB5dmdU9C_KoHf_61aqnl5wTOMJorrBdmb28LuKSOwdzlAZaBVIzb-UP_nerAImYVN45yKBWZLGaJMG0dV0YGs-5RDxzFi7tYG5mN5LHaWTc64eQ9wJWQZsvdDFVF-6PH3LXwRyXad2CzHydZVrLs9BdObgwcv81hyj8zh_p2XgBJm3qsRrqK1xpiukwRDN4rPkExMpw";
    public  String URL = "http://api.asudd.si.by/api/";

    @Test
    public void allCityTest(){
        String token=getToken();
        RestAssured.baseURI = URL;
//        pojo.Specifications.installSpecification(pojo.Specifications.requestSpec(URL), Specifications.responseSpec(200));
        Map<String,String> header = new HashMap<>();
  //      header.put("accept","*/*");
  //      header.put("Accept-Encoding","gzip, deflate");
   //     header.put("Authorization","Bearer " + token);
    //    header.put("Connection","keep-alive");

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("Cities/GetAll")
                .then().log().all()
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

    public static void getUsers(String accessToken) {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/users")
                .then()
                .statusCode(200); }

}
