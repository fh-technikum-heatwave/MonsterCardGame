package main;

import main.PublishSubscribe.Observer;
import main.dtos.UserDeckDTO;
import main.model.card.Card;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Battle implements Runnable {


    private static BlockingQueue<Observer> waiter = new LinkedBlockingQueue<>();

    public static void registerForBattle(Observer observer) {
        waiter.add(observer);

        if (waiter.size() >= 2) {

            Observer observer1 = waiter.poll();
            Observer observer2 = waiter.poll();

            System.out.println(observer1);
            System.out.println(observer2);

            Thread t = new Thread(new Battle(observer1, observer2));
            t.run();

        }
    }

    private Observer observer1;
    private Observer observer2;

    public Battle(Observer observer1, Observer observer2) {
        this.observer1 = observer1;
        this.observer2 = observer2;
    }


    private void battle(UserDeckDTO u1, UserDeckDTO u2) {

        for (int i = 0; i < 100; i++) {

            Collections.shuffle(u1.getDeck());
            Collections.shuffle(u2.getDeck());


            if (u1.getDeck().size() == 0 || u2.getDeck().size() == 0) {
                break;
            }

            Card user1Card = u1.getDeck().get(0);
            Card user2Card = u2.getDeck().get(0);


            if (user1Card.getClass().getSimpleName().contains("Spell") ||
                    user2Card.getClass().getSimpleName().contains("Spell")) {
                elementFight(u1, u2, user1Card, user2Card);
            } else {
                monsterFight(u1, u2, user1Card, user2Card);
            }


        }


        if (u1.getDeck().size() > u2.getDeck().size()) {
            observer1.setResult(u1, u2);
            observer2.setResult(u1, u2);
        } else if (u2.getDeck().size() > u1.getDeck().size()) {
            observer1.setResult(u2, u1);
            observer2.setResult(u2, u1);
        } else {
            observer1.setResult(null, null);
            observer2.setResult(null, null);
        }


        observer1.isFinished(true);
        observer2.isFinished(true);
    }


    private UserDeckDTO monsterFight(UserDeckDTO u1, UserDeckDTO u2, Card c1, Card c2) {

        if (c1.getNameAndType().contains(c2.getWeakness().toLowerCase(Locale.ROOT))) {
            return u1;
        } else if (c2.getNameAndType().contains(c1.getWeakness().toLowerCase(Locale.ROOT))) {
            return u2;
        } else {
            if (c1.getClass().getSimpleName().contains("Monster") && c2.getClass().getSimpleName().contains("Monster"))
                return winner(u1, u2, c1.getDamage(), c2.getDamage(), c1, c2);
            else
                return elementFight(u1, u2, c1, c2);
        }
    }

    private UserDeckDTO elementFight(UserDeckDTO u1, UserDeckDTO u2, Card c1, Card c2) {
        Element t1 = c1.getType();
        Element t2 = c2.getType();

        if (t1 == c2.getTypeWeakness()) {
            return winner(u1, u2, c1.getDamage() * 2.0, c2.getDamage() / 2.0, c1, c2);
        } else if (t2 == c1.getTypeWeakness()) {
            return winner(u1, u2, c1.getDamage() / 2.0, c2.getDamage() * 2, c1, c2);
        } else {
            return winner(u1, u2, c1.getDamage(), c2.getDamage(), c1, c2);
        }
    }

    private UserDeckDTO winner(UserDeckDTO u1, UserDeckDTO u2, double damageC1, double damageC2, Card c1, Card c2) {
        if (damageC1 > damageC2) {
            u1.getDeck().add(c2);
            u2.getDeck().remove(c2);
            return u1;
        } else if (damageC1 < damageC2) {
            u2.getDeck().add(c1);
            u1.getDeck().remove(c1);
            return u2;
        }
        return null;
    }

    @Override
    public void run() {
        battle(observer1.getUser(), observer2.getUser());
    }
}
