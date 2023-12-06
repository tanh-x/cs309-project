import cs309.backend.BackendApplication;
import cs309.backend.jpa.repo.*;
import cs309.backend.services.UserService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BackendApplication.class)
@RunWith(SpringRunner.class)

public class TestingSystemTest {
    String jwtToken = "eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6ImFkbWluIiwiaWF0IjoxNjk4NzI0NjIwLCJleHAiOjE3MzAyNjA2MjB9.gm4PTI169mmfAndcZQKFPwo3Ctl247a7gG-GQkIhw_LZoRVcgS9-8Z76sRNfcZXs";
    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void getUserByUid() {
        Response response = RestAssured.given().
                header("Authorization", "Bearer " + jwtToken).
                pathParams("id", 1).
                get("/api/user/id/{id}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals("admin", returnObj.get("username"));
            assertEquals("admin@cs309.kewargs.com", returnObj.get("email"));
            assertEquals("Scheduler Admin", returnObj.get("displayName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
