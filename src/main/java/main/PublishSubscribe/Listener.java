package main.PublishSubscribe;

import main.dtos.UserDeckDTO;

public interface Listener {

    void setResult(UserDeckDTO winner, UserDeckDTO looser);

    void isFinished(boolean finish);

}
