package main;

import main.PublishSubscribe.Listener;
import main.PublishSubscribe.Observer;
import main.dtos.UserDeckDTO;
import main.model.User;
import main.model.card.Card;
import main.model.card.MonsterCard;
import main.rest.services.BattleService;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Battle implements Runnable {


    private static BlockingQueue<Observer> waiter = new LinkedBlockingQueue<>();

    public static void registerForBattle(Observer observer) {
        waiter.add(observer);

        if (waiter.size() >= 2) {
            System.out.println(waiter.size());
            Observer u1 = waiter.poll();
            Observer u2 = waiter.poll();


            System.out.println(u1);
            System.out.println(u2);

            Battle b = new Battle(u1, u2);

            Thread t = new Thread(new Battle(u1, u2));
            t.run();
            System.out.println("Battle in Thread: " + t.getName());
            b.run();
        }
    }

    private Observer o1;
    private Observer o2;

    public Battle(Observer u1, Observer u2) {
        this.o1 = u1;
        this.o2 = u2;
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


            UserDeckDTO winner = null;

            if (user1Card.getClass().getSimpleName().contains("Spell") ||
                    user2Card.getClass().getSimpleName().contains("Spell")) {
                winner = elementFight(u1, u2, user1Card, user2Card);
            } else {
                winner = monsterFight(u1, u2, user1Card, user2Card);
            }


        }


        o1.isFinished(true);
        o2.isFinished(true);

        if (u1.getDeck().size() > u2.getDeck().size()) {
            o1.setResult(u1, u2);
            o2.setResult(u1, u2);
        } else if (u2.getDeck().size() > u1.getDeck().size()) {
            o1.setResult(u2, u1);
            o2.setResult(u2, u1);
        } else {
            o1.setResult(null, null);
            o2.setResult(null, null);
        }
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
        battle(o1.getUser(), o2.getUser());
    }
}
