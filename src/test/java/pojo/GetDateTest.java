package pojo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetDateTest {

    private final static String URL="https://reqres.in/";

    @Test
    void checkAvatarAndIdTest(){
        List <UserData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath()
                .getList("data", UserData.class);

   //     users.forEach(x->assertTrue(x.getEmail().endsWith("@reqres.in")));
        assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
        //     users.forEach(x->assertTrue(x.getAvatar().contains(x.getId().toString())));
       List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
       List<String> id = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());
       for( var i = 0; i < avatars.size(); i++){
       assertTrue(avatars.get(i).contains(id.get(i)));
       }

    }

    @Test
    void checkAvatarAndIdWithSpecTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec(200));
        List <UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath()
                .getList("data", UserData.class);

        //users.forEach(x->assertTrue(x.getEmail().endsWith("@reqres.in")));
        assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

       // users.forEach(x->assertTrue(x.getAvatar().contains(x.getId().toString())));
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> id = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());
        for( var i = 0; i < avatars.size(); i++){
            assertTrue(avatars.get(i).contains(id.get(i)));
        }

    }

}
