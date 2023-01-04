package main.rest.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.GameDao;
import main.daos.UserDao;
import main.model.User;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserService {
    private UserDao userDao;
    private GameDao gameDao;

    public void createUser(User user){


    }

}
