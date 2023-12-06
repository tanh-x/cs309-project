import cs309.backend.BackendApplication;
import cs309.backend.DTOs.LoginData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BackendApplication.class)
@RunWith(SpringRunner.class)

public class UserSystemTest {
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

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        Response response1 = RestAssured.given().
                header("Authorization", "Bearer " + jwtToken).
                pathParams("id", 123456789).
                get("/api/user/id/{id}");
        assertEquals(500, response1.getStatusCode());

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

    @Test
    public void getUserByUsername() {
        Response response = RestAssured.given().
                header("Authorization", "Bearer " + jwtToken).
                pathParams("username", "admin").
                get("/api/user/username/{username}");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        Response response1 = RestAssured.given().
                header("Authorization", "Bearer " + jwtToken).
                pathParams("username", "Blablabla").
                get("/api/user/username/{username}");
        assertEquals(500, response1.getStatusCode());

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals("admin@cs309.kewargs.com", returnObj.get("email"));
            assertEquals("Scheduler Admin", returnObj.get("displayName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getUserByEmail() {
        Response response = RestAssured.given().
                header("Authorization", "Bearer " + jwtToken).
                pathParams("email", "admin@cs309.kewargs.com").
                get("/api/user/email/{email}");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        Response response1 = RestAssured.given().
                header("Authorization", "Bearer " + jwtToken).
                pathParams("email", "123456789").
                get("/api/user/email/{email}");
        assertEquals(500, response1.getStatusCode());

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals("admin", returnObj.get("username"));
            assertEquals("Scheduler Admin", returnObj.get("displayName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //no jwt Token
    @Test
    public void noToken() {
        Response response = RestAssured.given().
                pathParams("username", "admin").
                get("/api/user/username/{username}");

        int statusCode = response.getStatusCode();
        assertEquals(403, statusCode);
    }

    @Test
    public void login() {
        LoginData login = new LoginData("admin@cs309.kewargs.com", "scheduler");
        Response response = RestAssured.given().contentType(ContentType.JSON).
                body(login).
                post("/api/auth/login");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void testReadDegreeAudit() {
        String filePath = "auditTest.pdf";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            assert inputStream != null;
            byte[] pdfContent = inputStream.readAllBytes();

            // Send a POST request to your endpoint
            Response response = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
                    .multiPart("file", filePath, pdfContent)
                    .when()
                    .post("/api/reader/pdf");

            // Check status code
            int statusCode = response.getStatusCode();
            assertEquals(200, statusCode);

            // Check response content type
            String contentType = response.getContentType();
            assertEquals(ContentType.JSON.toString(), contentType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
