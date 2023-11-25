import config.VideoGameConfig;
import config.VideoGameEndpoints;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import objects.VideoGame;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

public class VideoGameTests extends VideoGameConfig {
    String gameBodyJSON = "{\n" +
            "  \"category\": \"Platform\",\n" +
            "  \"name\": \"Mario\",\n" +
            "  \"rating\": \"Mature\",\n" +
            "  \"releaseDate\": \"2012-05-04\",\n" +
            "  \"reviewScore\": 85\n" +
            "}";

    @Test
    public void getAllGames() {
        given()
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then();
    }

    @Test
    public void createNewGameByJSON(){
        given()
                .body(gameBodyJSON)
                .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then();

    }

    @Test
    public void createNewGameByXML(){
        String gameBodyXml = "<VideoGameRequest>\n" +
                "\t<category>Platform</category>\n" +
                "\t<name>Mario</name>\n" +
                "\t<rating>Mature</rating>\n" +
                "\t<releaseDate>2012-05-04</releaseDate>\n" +
                "\t<reviewScore>85</reviewScore>\n" +
                "</VideoGameRequest>";

        given()
                .body(gameBodyXml)
                .contentType("application/xml")
                .accept("application/xml")
                .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then();
    }

    @Test
    public void updateGame(){

        given()
                .body(gameBodyJSON)
                .when()
                .put("videogame/3")
                .then();
    }
    
    @Test
    public void deleteGame(){
        given()
                .accept("text/plain")
                .when()
                .delete("videogame/8")
                .then();
    }

    @Test
    public void getSingleGame(){
        given()
                .pathParam("videoGameId" , 5)
                .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
                .then();
    }

    @Test
    public void testVideoGameSerializationByJSON(){
        VideoGame videoGame = new VideoGame("Shooter", "MyAwesomeGame", "Mature", "2023-04-05", 99);

        given()
                .body(videoGame)
                .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then();
    }

    @Test
    public void convertJSONToPojo(){
        Response response =
                given()
                        .pathParam("videoGameId", 4)
                        .when()
                        .get(VideoGameEndpoints.SINGLE_VIDEO_GAME);

        VideoGame videoGame = response.getBody().as(VideoGame.class);
        System.out.println(videoGame.toString());
    }

    @Test
    public void testVideoGameSchemaXML(){
        given()
                .pathParam("videoGameId", 5)
                .accept("application/xml")
                .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
                .then()
                .body(RestAssuredMatchers.matchesXsdInClasspath("VideoGameXSD.xsd"));
    }

    @Test
    public void testVideoGameSchemaJSON(){
        given()
                .pathParam("videoGameId", 3)
                .accept("application/json")
                .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("VideoGameJSONSchema.json"));
    }

    @Test
    public void captureResponseTime(){
        long responseTime = get(VideoGameEndpoints.ALL_VIDEO_GAMES).time();
        System.out.println(responseTime);
    }

    @Test
    public void assertOnResponseTime(){
        get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then().time(lessThan(2000L));
    }
}
