package tests.auth;

import tests.BaseClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteLogoutTest extends BaseClass {
    private static String endpoint = "deleteLogout";

    public DeleteLogoutTest() {
        super(endpoint);
    }

    @Test
    public void checkThatUserIsLoggedOutWithValidToken() {
        given()
            .header("Authorization", getToken())
        .when()
            .delete(api)
        .then()
            .statusCode(200);
    }

    @Test
    public void checkTheSuccessMessageForLoggingOut() {
        given()
            .header("Authorization", getToken())
        .when()
            .delete(api)
        .then()
            .extract().response().path("Message").equals("Successfully logged out");
    }

    @Test
    public void checkThatUserIsNotLoggedOutWithInvalidToken() {
        given()
            .header("Authorization", faker.lorem().sentence())
        .when()
            .delete(api)
        .then()
            .statusCode(401);
    }

    @Test
    public void checkTheErrorMessageLoggingOut() {
        given()
            .header("Authorization", faker.lorem().sentence())
        .when()
            .delete(api)
        .then()
            .extract().response().path("Error").equals("Unauthorized");
    }
}
