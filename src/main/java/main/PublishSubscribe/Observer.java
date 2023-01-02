package main.PublishSubscribe;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import main.Battle;
import main.dtos.UserDeckDTO;

import java.util.*;

@Getter
public class Observer implements Listener {

    public List<UserDeckDTO> list =
            Collections.synchronizedList(new ArrayList<>());

    public UserDeckDTO winner;
    public UserDeckDTO looser;

    private UserDeckDTO user;
    private boolean isFinish;


    public Observer(UserDeckDTO user) {
        this.user = user;
    }

    @Override
    public void setResult(UserDeckDTO winner, UserDeckDTO looser) {
        this.winner = winner;
        this.looser = looser;

        list.add(winner);
        list.add(looser);
    }

    @Override
    public void isFinished(boolean finish) {
        isFinish = finish;
    }
}
