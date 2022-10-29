package main.rest.services;

import main.User;
import main.card.Card;

import java.util.LinkedList;
import java.util.List;

public class CardService extends Service {
    private User user;

    public void login(String username) {
        user = new User(username);

        System.out.println("User " + user.getUsername() + " logged in");
    }


}
