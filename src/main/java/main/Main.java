package main;

import main.card.Card;
import main.card.MonsterCard;
import main.rest.App;
import main.rest.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        App app = new App();
        Server server = new Server(app, 5543);
        server.start();


//        User user1 = new User("User1");
//        User user2 = new User("User2");
//
//        user1.buyPackage(4);
//        user2.buyPackage(4);
//
//        user1.openPackages();
//        user2.openPackages();
//
//        User winner = Battle.battle(user1, user2);
//
//        if (winner == null)
//            System.out.println("unentschieden");
//        else
//            System.out.println("winner " + winner.getUsername());

    }
}

