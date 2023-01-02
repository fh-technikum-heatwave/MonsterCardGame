package main.PublishSubscribe;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import main.Battle;
import main.dtos.UserDeckDTO;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public class Observer implements Listener {

    public Observer() {

    }

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
    }

    @Override
    public void isFinished(boolean finish) {
        isFinish = finish;
    }
}
