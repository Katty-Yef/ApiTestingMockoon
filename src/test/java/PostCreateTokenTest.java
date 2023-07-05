import io.restassured.response.Response;
import org.auth.PostCreateToken;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;

public class PostCreateTokenTest extends BaseClass<PostCreateToken> {
    private static String endpoint = "postLogin";
    protected String token;

    public PostCreateTokenTest() {
        super(endpoint);
    }

    @Override
    protected PostCreateToken createEndpointInstance() {
        return new PostCreateToken();
    }

    @Test
    public void checkThatTokenCanBeCreatedWithValidCredentials() {
        model.setPassword("qwerty123");
        model.setEmail("kate@gmail.com");

        Response response =
        given().
                contentType(ContentType.JSON).
                body(model).
        when().
                post(api).
        then().
                statusCode(200).
                extract().response();
        token = response.path("accessToken");
    }

    @Test
    public void checkThatTokenCannotBeCreatedWithInvalidEmail() {
        model.setPassword("qwerty123");
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
        model.setEmail("kate@gmail.com");

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
}
