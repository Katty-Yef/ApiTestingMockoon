package tests.users;

import io.restassured.response.Response;
import models.responseModels.users.User;
import models.requestModels.users.GetUsers;
import org.testng.annotations.Test;
import tests.BaseClass;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetUsersTest extends BaseClass<GetUsers> {
    private static String endpoint = "getUsers";

    public GetUsersTest() {
        super(endpoint);
    }

    @Override
    protected GetUsers createEndpointInstance() {
        return new GetUsers();
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
        List<User> users = given()
                .header("Authorization", getToken())
            .when()
                .get(api)
            .then()
                .extract().response().jsonPath().getList("data", User.class);

        for (User user : users) {
            assert(user.email.contains("@"));
        }
    }

    @Test
    public void checkThatCorrectNumberOfUsersIsReceivedInTheBody() {
        List<User> users = given()
                .header("Authorization", getToken())
            .when()
                .get(api)
            .then()
                .extract().response().jsonPath().getList("data", User.class);

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

        List<User> users = response.jsonPath().getList("data", User.class);

        assert(response.path("totalCount").equals(users.size()));
    }
}