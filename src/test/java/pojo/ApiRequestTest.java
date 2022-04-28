package pojo;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ApiRequestTest {

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
    @Test
    void successReqisterTest(){
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        String email = "eve.holt@reqres.in";
        String password = "pistol";

        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec(200));
        Register user = new Register(email,password);
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        assertEquals(id,successReg.getId());
        assertEquals(token,successReg.getToken());
    }

    @Test
    public void unSuccessReqisterTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec(400));

        String email = "sydney@fife";
        String error = "Missing password";
        Register user = new Register(email,"");
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        assertEquals(error,unSuccessReg.getError());
    }

    @Test
    void sortListYearTest (){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec(200));
        List <ListResurce> listResurces = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath()
                .getList("data", ListResurce.class);

        List<Integer> year = listResurces.stream().map(ListResurce::getYear).collect(Collectors.toList());
        List<Integer> sortYear = year.stream().sorted().collect(Collectors.toList());
        assertEquals(sortYear,year);

    }
    @Test
    void deleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpec(204));
        given()
       .when()
       .delete("api/users/2")
       .then().log().all();
    }


}
