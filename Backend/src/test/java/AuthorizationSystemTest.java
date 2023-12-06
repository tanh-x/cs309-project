import cs309.backend.BackendApplication;
import cs309.backend.DTOs.LoginData;
import cs309.backend.jpa.repo.StudentRepository;
import cs309.backend.jpa.repo.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void login() {
        LoginData login = new LoginData("admin@cs309.kewargs.com", "scheduler");
        Response response = RestAssured.given().contentType(ContentType.JSON).
                body(login).
                post("/api/auth/login");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }
}
