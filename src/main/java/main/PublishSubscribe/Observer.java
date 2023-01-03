package main.PublishSubscribe;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Battle;
import main.dtos.UserDeckDTO;

import java.util.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class Observer implements Listener {

    public List<UserDeckDTO> list =
            Collections.synchronizedList(new ArrayList<>());

    private UserDeckDTO user;
    private boolean isFinish;


    public Observer(UserDeckDTO user) {
        this.user = user;
    }

    @Override
    public void setResult(UserDeckDTO winner, UserDeckDTO looser) {
        list.add(winner);
        list.add(looser);
    }

    @Override
    public void isFinished(boolean finish) {
        isFinish = finish;
    }
}