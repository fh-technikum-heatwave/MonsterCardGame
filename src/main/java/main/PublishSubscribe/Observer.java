package main.PublishSubscribe;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Tuple.Tuple;
import main.dtos.UserDeckDTO;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class Observer implements Listener {

    public List<UserDeckDTO> list =
            Collections.synchronizedList(new ArrayList<>());

    private UserDeckDTO user;
    private boolean isFinish;
    private BlockingQueue<Tuple<UserDeckDTO, UserDeckDTO>> blockingQueue = new LinkedBlockingQueue(1);


    public Observer(UserDeckDTO user) {
        this.user = user;
    }

    @Override
    public void setResult(UserDeckDTO winner, UserDeckDTO looser) {
        list.add(winner);
        list.add(looser);

        try {
            blockingQueue.put(new Tuple<>(winner, looser,null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isFinished(boolean finish) {
        isFinish = finish;
    }
}
