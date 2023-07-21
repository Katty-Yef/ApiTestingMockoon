package tests.users;

import org.testng.annotations.Test;
import tests.BaseClass;

import static io.restassured.RestAssured.given;

public class DeleteUsersTest extends BaseClass {
    private static String endpoint = "deleteUser";

    public DeleteUsersTest() {
        super(endpoint);
    }

    @Test
    public void checkThatUserCanBeDeletedWithValidToken() {
        given()
            .header("Authorization", getToken())
        .when()
            .delete(String.format(api, faker.idNumber()))
        .then()
            .statusCode(200);
    }

    @Test
    public void checkThatUserCannotBeDeletedWithInvalidToken() {
        given()
            .header("Authorization", faker.lorem().sentence())
        .when()
            .delete(String.format(api, faker.idNumber()))
        .then()
            .statusCode(401);
    }

    @Test
    public void checkTheErrorMessageIfTokenIsInvalid() {
        given()
            .header("Authorization", faker.lorem().sentence())
        .when()
            .delete(String.format(api, faker.idNumber()))
        .then()
            .extract().response().path("Error").equals("Unauthorized");
    }
}
