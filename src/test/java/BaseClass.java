import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.auth.PostLogin;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public abstract class BaseClass<T> {

    protected abstract T createEndpointInstance();
    protected T model;
    protected String endpoint;
    protected String api;
    protected static Properties properties;
    protected String token;
    protected Faker faker;
    protected static final Logger LOGGER = Logger.getLogger(BaseClass.class.getName());

    public BaseClass(String endpoint) {
        this.endpoint = endpoint;
    }

    @BeforeClass
    public void setUp() {
        LOGGER.info("Test class is started");

        faker = Faker.instance();
        try {
            InputStream input = new FileInputStream("src/main/resources/config.properties");
            properties = new Properties();
            properties.load(input);
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }

    protected String getToken() {
        PostLogin postLogin = new PostLogin();
        postLogin.setPassword(properties.getProperty("password"));
        postLogin.setEmail(properties.getProperty("email"));

        Response response =
            given().
                    contentType(ContentType.JSON).
                    body(postLogin).
            when().
                    post(properties.getProperty("baseUrl") + properties.getProperty("postLogin")).
            then().
                    extract().response();
        token = response.path("accessToken");
        return token;
    }

    @BeforeMethod
    public void setUpEach() {
        LOGGER.info("Test method is started");
        model = createEndpointInstance();
        api = properties.getProperty("baseUrl") + properties.getProperty(endpoint);
    }

    @AfterClass
    public void tearDown() {
        LOGGER.info("Test class is finished");
    }

}
