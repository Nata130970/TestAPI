package pojo;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ApiRequestWithOutPojoTest {

    private final static String URL="https://reqres.in/";

    @Test
    void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec(200));
        Response response = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .body("data.id", notNullValue())
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", notNullValue())
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<Integer> ids = jsonPath.get("data.id");
        List<String> emails = jsonPath.get("data.email");
        List<String> avatars = jsonPath.get("data.avatar");
        for (var i = 0; i < avatars.size(); i++) {
            assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }
        assertTrue(emails.stream().allMatch(x->x.endsWith("@reqres.in")));
    }

    @Test
    public void successeUserReqWithOutPojoTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec(200));
        Map<String,String> user = new HashMap<>();
        user.put("email","eve.holt@reqres.in");
        user.put("password","pistol");
        Response response = given()
        .body(user)
        .when()
        .post("api/register")
        .then().log().all()
        .body("id",equalTo(4))
        .body("token",equalTo("QpwL5tke4Pnpja7X4"))
        .extract().response();
        JsonPath jsonPath =  response.jsonPath();
        String token = jsonPath.get("token");
        int id = jsonPath.get("id");
        assertEquals(4, id);
        assertEquals("QpwL5tke4Pnpja7X4",token);
    }
    @Test
    public void noSuccesseUserReqWithOutPojoTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec(400));
        String email = "sydney@fife";
        String error = "Missing password";
        Map<String,String> user = new HashMap<>();
        user.put("email",email);
        user.put("password","");
        Response response = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath =  response.jsonPath();
        String err = jsonPath.get("error");
        assertEquals(err,error);




    }
}

