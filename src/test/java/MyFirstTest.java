import org.junit.Test;

import static io.restassured.RestAssured.given;

public class MyFirstTest {
    @Test
    public void MyFirstTest() {
        given()
                .log().all()
                .when()
                .get("https://videogamedb.uk/api/videogame")
                .then()
                .log().all();
    }
}
