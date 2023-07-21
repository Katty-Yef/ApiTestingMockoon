package tests.auth;

import tests.BaseClass;
import models.requestModels.auth.PostLogin;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;

public class PostLoginTest extends BaseClass {
    private PostLogin model;
    private static String endpoint = "postLogin";

    public PostLoginTest() {
        super(endpoint);
    }

    @Override
    protected void createEndpointInstance() {
        model = new PostLogin();
    }

    @Test
    public void checkThatTokenCanBeCreatedWithValidCredentials() {
        model.setPassword(properties.getProperty("password"));
        model.setEmail(properties.getProperty("email"));

        given()
            .contentType(ContentType.JSON)
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(200);
    }

    @Test
    public void checkThatTokenIsReceivedInTheResponse() {
        model.setPassword(properties.getProperty("password"));
        model.setEmail(properties.getProperty("email"));

        given()
            .contentType(ContentType.JSON)
            .body(model)
        .when()
            .post(api)
        .then()
            .extract().response().body().asString().contains("accessToken");
    }

    @Test
    public void checkThatTokenCannotBeCreatedWithInvalidEmail() {
        model.setPassword(properties.getProperty("password"));
        model.setEmail(faker.internet().emailAddress());

        given()
            .contentType(ContentType.JSON)
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatTokenCannotBeCreatedWithInvalidPassword() {
        model.setPassword(faker.internet().password());
        model.setEmail(properties.getProperty("email"));

        given()
            .contentType(ContentType.JSON)
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatTokenCannotBeCreatedWithInvalidCredentials() {
        model.setPassword(faker.internet().password());
        model.setEmail(faker.internet().emailAddress());

        given()
            .contentType(ContentType.JSON)
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkTheErrorMessageForInvalidCredentials() {
        model.setPassword(faker.internet().password());
        model.setEmail(faker.internet().emailAddress());

        given()
            .contentType(ContentType.JSON)
            .body(model)
        .when()
            .post(api)
        .then()
            .extract().response().path("Error").equals("Invalid credentials");
    }
}
