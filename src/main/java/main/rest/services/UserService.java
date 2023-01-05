package main.rest.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.GameDao;
import main.daos.UserDao;
import main.model.Statistik;
import main.model.User;

import java.sql.SQLException;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserService {
    private UserDao userDao;
    private GameDao gameDao;

    public UserService(UserDao userDao, GameDao gameDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
    }

    public boolean createUser(User user) {

        System.out.println(user);
        try {
            userDao.create(user);
            gameDao.create(new Statistik(user.getUsername(), 100, 0, 0, user.getId(), UUID.randomUUID().toString()));
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }


    public User login(String username) {
        try {
            return userDao.read(username);
        } catch (SQLException throwables) {
            return null;
        }
    }


    public User getByUsername(String username) {
        try {
            return userDao.read(username);
        } catch (SQLException throwables) {
            return null;
        }
    }

}
