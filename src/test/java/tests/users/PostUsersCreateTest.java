package tests.users;

import tests.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.requestModels.users.PostUsersCreate;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostUsersCreateTest extends BaseClass {
    private PostUsersCreate model;
    private static String endpoint = "postCreateUser";

    public PostUsersCreateTest() {
        super(endpoint);
    }

    @Override
    protected void createEndpointInstance() {
        model = new PostUsersCreate();
    }

    @Test
    public void checkThatUserCanBeCreatedWithValidTokenAndProvidedInfo() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(200);
    }

    @Test
    public void checkThatEnteredValuesAreReturnedInBody() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", getToken())
                .body(model)
            .when()
                .post(api)
            .then()
                .extract().response();

        assert (response.path("id").equals(model.getId()));
        assert (response.path("firstName").equals(model.getFirstName()));
        assert (response.path("lastName").equals(model.getLastName()));
        assert (response.path("email").equals(model.getEmail()));
    }

    @Test
    public void checkThatUserCannotBeCreatedWithInvalidToken() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", faker.lorem().sentence())
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(401);
    }

    @Test
    public void checkTheErrorMessageIfTokenIsInvalid() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", faker.lorem().sentence())
            .body(model)
        .when()
            .post(api)
        .then()
            .extract().response().path("Error").equals("Unauthorized");
    }

    @Test
    public void checkThatUserCannotBeCreatedWithEmptyId() {
        model.setId(null);
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatUserCannotBeCreatedWithEmptyEmail() {
        model.setId(faker.idNumber().toString());
        model.setEmail(null);
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatUserCannotBeCreatedWithEmptyLastName() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(null);
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatUserCannotBeCreatedWithEmptyFirstName() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(null);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .post(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatUserCannotBeCreatedWithEmptyFields() {
        model.setId(null);
        model.setEmail(null);
        model.setLastName(null);
        model.setFirstName(null);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .post(api)
        .then()
            .extract().response().path("Error").equals("Bad request");
    }
}
