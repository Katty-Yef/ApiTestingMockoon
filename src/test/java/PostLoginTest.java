import io.restassured.response.Response;
import model.auth.PostLogin;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;

public class PostLoginTest extends BaseClass<PostLogin> {
    private static String endpoint = "postLogin";

    public PostLoginTest() {
        super(endpoint);
    }

    @Override
    protected PostLogin createEndpointInstance() {
        return new PostLogin();
    }

    @Test
    public void checkThatTokenCanBeCreatedWithValidCredentials() {
        model.setPassword(properties.getProperty("password"));
        model.setEmail(properties.getProperty("email"));

        given().
                contentType(ContentType.JSON).
                body(model).
        when().
                post(api).
        then().
                statusCode(200);
    }

    @Test
    public void checkThatTokenIsReceivedInTheResponse() {
        model.setPassword(properties.getProperty("password"));
        model.setEmail(properties.getProperty("email"));

        Response response =
            given().
                    contentType(ContentType.JSON).
                    body(model).
            when().
                    post(api).
            then().
                    extract().response();

        assert(response.body().asString().contains("accessToken"));
    }

    @Test
    public void checkThatTokenCannotBeCreatedWithInvalidEmail() {
        model.setPassword(properties.getProperty("password"));
        model.setEmail(faker.internet().emailAddress());

        given().
                contentType(ContentType.JSON).
                body(model).
        when().
                post(api).
        then().
                statusCode(400);
    }

    @Test
    public void checkThatTokenCannotBeCreatedWithInvalidPassword() {
        model.setPassword(faker.internet().password());
        model.setEmail(properties.getProperty("email"));

        given().
                contentType(ContentType.JSON).
                body(model).
        when().
                post(api).
        then().
                statusCode(400);
    }

    @Test
    public void checkThatTokenCannotBeCreatedWithInvalidCredentials() {
        model.setPassword(faker.internet().password());
        model.setEmail(faker.internet().emailAddress());

        given().
                contentType(ContentType.JSON).
                body(model).
        when().
                post(api).
        then().
                statusCode(400);
    }

    @Test
    public void checkTheErrorMessageForInvalidCredentials() {
        model.setPassword(faker.internet().password());
        model.setEmail(faker.internet().emailAddress());

        Response response =
            given().
                    contentType(ContentType.JSON).
                    body(model).
            when().
                    post(api).
            then().
                    extract().response();

        assert(response.path("Error").equals("Invalid credentials"));
    }
}
