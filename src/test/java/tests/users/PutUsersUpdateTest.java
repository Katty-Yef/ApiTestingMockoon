package tests.users;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.requestModels.users.PutUsersUpdate;
import org.testng.annotations.Test;
import tests.BaseClass;

import static io.restassured.RestAssured.given;

public class PutUsersUpdateTest extends BaseClass {
    private PutUsersUpdate model;
    private static String endpoint = "putUpdateUser";

    public PutUsersUpdateTest() {
        super(endpoint);
    }

    @Override
    protected void createEndpointInstance() {
        model = new PutUsersUpdate();
    }

    @Test
    public void checkThatUserCanBeUpdatedWithValidTokenAndProvidedInfo() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .put(api)
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
                .put(api)
            .then()
                .extract().response();

        assert (response.path("id").equals(model.getId()));
        assert (response.path("firstName").equals(model.getFirstName()));
        assert (response.path("lastName").equals(model.getLastName()));
        assert (response.path("email").equals(model.getEmail()));
    }

    @Test
    public void checkThatUserCannotBeUpdatedWithInvalidToken() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", faker.lorem().sentence())
            .body(model)
        .when()
            .put(api)
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
            .put(api)
        .then()
            .extract().response().path("Error").equals("Unauthorized");
    }

    @Test
    public void checkThatUserCannotBeUpdatedWithEmptyId() {
        model.setId(null);
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .put(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatUserCannotBeUpdatedWithEmptyEmail() {
        model.setId(faker.idNumber().toString());
        model.setEmail(null);
        model.setLastName(faker.name().lastName());
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .put(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatUserCannotBeUpdatedWithEmptyLastName() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(null);
        model.setFirstName(faker.name().firstName());

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .put(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatUserCannotBeUpdatedWithEmptyFirstName() {
        model.setId(faker.idNumber().toString());
        model.setEmail(faker.internet().emailAddress());
        model.setLastName(faker.name().lastName());
        model.setFirstName(null);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .put(api)
        .then()
            .statusCode(400);
    }

    @Test
    public void checkThatUserCannotBeUpdatedWithEmptyFields() {
        model.setId(null);
        model.setEmail(null);
        model.setLastName(null);
        model.setFirstName(null);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", getToken())
            .body(model)
        .when()
            .put(api)
        .then()
            .extract().response().path("Error").equals("Bad request");
    }
}
