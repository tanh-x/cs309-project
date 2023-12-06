import cs309.backend.BackendApplication;
import cs309.backend.jpa.repo.StudentRepository;
import cs309.backend.jpa.repo.UserRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BackendApplication.class)
@RunWith(SpringRunner.class)
public class AuthorizationSystemTest {
    @LocalServerPort
    int port;

    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    //no jwt Token
    @Test
    public void getUserByUsername() {
        Response response = RestAssured.given().
                pathParams("username", "admin").
                get("/api/user/username/{username}");

        int statusCode = response.getStatusCode();
        assertEquals(403, statusCode);
    }
}
