package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Factory.ResponseFactory;
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

    private boolean isMissingBody(String body) {
        return body == null || body.isEmpty();
    }

    private boolean isMissingUser(String userId) {
        return userId == null;
    }


    public Response battle(String uid) throws JsonProcessingException {
        if (isMissingUser(uid)) {

            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized,
                    "Token Missing/Token invalid");
        }

        boolean configured = gameService.checkIfDeckIsConfigured(uid);

        if (!configured) {

            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.NO_CONTENT,
                    "Your Deck is not configured");

        }

        System.out.println("in battle controller");
        Tuple<String, String> ergbnisse = gameService.battle(uid);

        String dataJson = getObjectMapper().writeValueAsString(ergbnisse);

        return ResponseFactory.buildResponse(ContentType.JSON, HttpStatus.OK, dataJson);
    }


    public Response getStats(String userId) throws JsonProcessingException {
        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }


        Statistik statistik = gameService.getStats(userId);

        String dataJson = getObjectMapper().writeValueAsString(statistik);
        return ResponseFactory.buildResponse(ContentType.JSON, HttpStatus.OK, dataJson);
    }


    public Response battleWithAFriend(String uid, String friendname) throws JsonProcessingException {
        if (isMissingUser(uid)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }

        boolean friends = gameService.checkIfFriends(friendname, uid);

        if (!friends) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.NOT_ALLOWED,
                    "You cant play against this user since you are not friends");
        }

        boolean configured = gameService.checkIfDeckIsConfigured(uid);

        if (!configured) {

            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.NO_CONTENT, "Your Deck is not configured");
        }


        Tuple<String, String> ergbnisse = gameService.battleAgainstAFriend(uid, friendname);

        String dataJson = getObjectMapper().writeValueAsString(ergbnisse);

        return ResponseFactory.buildResponse(ContentType.JSON, HttpStatus.OK, dataJson);
    }


    public Response getScores(String userId) throws JsonProcessingException {
        System.out.println(userId);

        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }

        List<Statistik> scores = gameService.getScores();

        String dataJson = getObjectMapper().writeValueAsString(scores);
        return ResponseFactory.buildResponse(ContentType.JSON, HttpStatus.OK, dataJson);
    }
}
