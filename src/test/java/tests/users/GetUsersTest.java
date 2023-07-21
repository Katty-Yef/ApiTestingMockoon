package tests.users;

import io.restassured.response.Response;
import models.responseModels.users.GetUsersResponse;
import org.testng.annotations.Test;
import tests.BaseClass;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetUsersTest extends BaseClass {
    private static String endpoint = "getUsers";

    public GetUsersTest() {
        super(endpoint);
    }

    @Test
    public void checkThatUsersListCanBeReceived() {
        given()
            .header("Authorization", getToken())
        .when()
            .get(api)
        .then()
            .statusCode(200);
    }

    @Test
    public void checkThatCorrectUsersAreReceivedInTheBody() {
        List<GetUsersResponse> users = given()
                .header("Authorization", getToken())
            .when()
                .get(api)
            .then()
                .extract().response().jsonPath().getList("data", GetUsersResponse.class);

        for (GetUsersResponse user : users) {
            assert(user.email.contains("@"));
        }
    }

    @Test
    public void checkThatCorrectNumberOfUsersIsReceivedInTheBody() {
        List<GetUsersResponse> users = given()
                .header("Authorization", getToken())
            .when()
                .get(api)
            .then()
                .extract().response().jsonPath().getList("data", GetUsersResponse.class);

        assert(users.size() == 10);
    }

    @Test
    public void checkThatCorrectCounterIsReceivedInTheBody() {
        Response response = given()
                .header("Authorization", getToken())
            .when()
                .get(api)
            .then()
                .extract().response();

        List<GetUsersResponse> users = response.jsonPath().getList("data", GetUsersResponse.class);

        assert(response.path("totalCount").equals(users.size()));
    }
}