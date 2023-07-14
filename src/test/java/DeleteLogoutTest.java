import io.restassured.response.Response;
import model.auth.DeleteLogout;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteLogoutTest extends BaseClass<DeleteLogout> {
    private static String endpoint = "deleteLogout";

    public DeleteLogoutTest() {
        super(endpoint);
    }

    @Override
    protected DeleteLogout createEndpointInstance() {
        return new DeleteLogout();
    }

    @Test
    public void checkThatUserIsLoggedOutWithValidToken() {
        model.setToken("Bearer " + getToken());

        given().
                header("Authorization", model.getToken()).
        when().
                delete(api).
        then().
                statusCode(200);
    }

    @Test
    public void checkTheSuccessMessageForLoggingOut() {
        model.setToken("Bearer " + getToken());

        Response response =
            given().
                    header("Authorization", model.getToken()).
            when().
                    delete(api).
            then().
                    extract().response();
        assert(response.path("Message").equals("Successfully logged out"));
    }

    @Test
    public void checkThatUserIsNotLoggedOutWithInvalidToken() {
        model.setToken("Bearer " + faker.lorem().sentence());

        given().
                header("Authorization", model.getToken()).
        when().
                delete(api).
        then().
                statusCode(401);
    }

    @Test
    public void checkTheErrorMessageLoggingOut() {
        model.setToken("Bearer " + faker.lorem().sentence());

        Response response =
                given().
                        header("Authorization", model.getToken()).
                when().
                        delete(api).
                then().
                        extract().response();

        assert (response.path("Error").equals("Unauthorized"));
    }
}
