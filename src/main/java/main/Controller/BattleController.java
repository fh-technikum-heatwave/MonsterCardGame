package main.Controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.BattleService;

@Getter
@Setter(AccessLevel.PRIVATE)
public class BattleController extends Controller {

    private BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }


    public Response battle(String uid) {
        String s = battleService.battle(uid);


        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + s + ", \"error\": null }"
        );
    }
}
