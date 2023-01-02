package main.Controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.rest.services.BattleService;

@Getter
@Setter(AccessLevel.PRIVATE)
public class BattleController extends Controller {

    private BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }


    public void battle(String uid) {
        battleService.battle(uid);
    }
}
