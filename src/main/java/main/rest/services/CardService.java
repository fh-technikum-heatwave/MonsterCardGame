package main.rest.services;

import main.User;
import main.card.Card;

import java.util.LinkedList;
import java.util.List;

public class CardService extends Service {
    private User user;

    public User login(String username) {
        user = new User(username);

        System.out.println("User " + user.getUsername() + " logged in");
        return user;
    }

    public void buyPackage(int count, User userr) {
        userr.buyPackage(count);
    }

    public User openPackages(User userr) {

        userr.openPackages();
        return userr;
    }

}
