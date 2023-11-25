import config.FootballConfig;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Map;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GPathJSONTests extends FootballConfig {

    @Test
    public void extractMapOfElementsWithFind(){
        Response response = get("competitions/2021/teams");

        Map<String, ?> allTeamDataForSingleTeam = response.path("teams.find {it.name == 'Manchester United FC'}");

        System.out.println("Map of team data = " + allTeamDataForSingleTeam);
    }

    @Test
    public void extractSingleValueWithFind(){
        Response response = get("teams/57");
        String specificPlayer = response.path("squad.find {it.id == 4832}.name");
        System.out.println("Name of the player: " + specificPlayer);
    }

    @Test
    public void extractListOfValuesWithFindAll(){
        Response response = get("teams/57");
        List<String> playerNames = response.path("squad.findAll {it.id >= 6154}");
        System.out.println("List of players = " + playerNames);
    }
}
