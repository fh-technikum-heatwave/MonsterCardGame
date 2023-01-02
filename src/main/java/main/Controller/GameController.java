package main.Controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.GameService;

@Getter
@Setter(AccessLevel.PRIVATE)
public class GameController extends Controller {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    public Response battle(String uid) {
        String s = gameService.battle(uid);


        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + s + ", \"error\": null }"
        );
    }
}
