package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Tuple.Tuple;
import main.model.Statistik;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.GameService;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
public class GameController extends Controller {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    public Response battle(String uid) throws JsonProcessingException {
        if (uid == null) {
            return new Response(
                    HttpStatus.Forbidden,
                    ContentType.TEXT,
                    "Token Missing/Token invalid"
            );
        }


        Tuple ergbnisse = gameService.battle(uid);


        String dataJson = getObjectMapper().writeValueAsString(ergbnisse);


        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + dataJson + ", \"error\": null }"
        );
    }


    public Response getStats(String userId) throws JsonProcessingException {
        if (userId == null) {
            return new Response(
                    HttpStatus.Forbidden,
                    ContentType.TEXT,
                    "Token Missing/Token invalid"
            );
        }


        Statistik statistik = gameService.getStats(userId);

        String dataJson = getObjectMapper().writeValueAsString(statistik);

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + dataJson + ", \"error\": null }"
        );
    }


    public Response getScores(String userId) throws JsonProcessingException {
        System.out.println(userId);

        if (userId == null) {
            return new Response(
                    HttpStatus.Forbidden,
                    ContentType.TEXT,
                    "Token Missing/Token invalid"
            );
        }


        List<Statistik> scores = gameService.getScores();

        String dataJson = getObjectMapper().writeValueAsString(scores);

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + dataJson + ", \"error\": null }"
        );
    }
}
