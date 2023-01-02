package main.PublishSubscribe;

import lombok.Getter;
import main.Battle;
import main.dtos.UserDeckDTO;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public class Observer implements Listener {

    private UserDeckDTO user;

    private boolean isFinish;

    public Observer(UserDeckDTO user) {
        this.user = user;
    }

    @Override
    public void setResult(UserDeckDTO winner, UserDeckDTO looser) {
        System.out.println("Winner " + winner + "  " + Thread.currentThread().getName());
        System.out.println("Looser " + looser + " " + Thread.currentThread().getName());
    }

    @Override
    public void isFinished(boolean finish) {
        isFinish = finish;
    }
}
