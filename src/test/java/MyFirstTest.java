import config.VideoGameConfig;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class MyFirstTest extends VideoGameConfig {
    @Test
    public void MyFirstTest() {
        given()
                .log().all()
                .when()
                .get("videogame")
                .then()
                .log().all();
    }
}
