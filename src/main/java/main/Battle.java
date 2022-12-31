package main;

import main.model.User;
import main.model.card.Card;
import main.model.card.MonsterCard;

import java.util.Locale;

public class Battle {

    public static User battle(User u1, User u2) {

//        Card user1Card = u1.getCards().get(0);
//        Card user2Card = u2.getCards().get(0);

        Card user1Card = null;
        Card user2Card = null;

        System.out.println("User1: " + user1Card.getName() + " " + user1Card.getType() + " Damage " + user1Card.getDamage());
        System.out.println("User2: " + user2Card.getName() + " " + user2Card.getType() + " Damage " + user2Card.getDamage());


        for (int i = 0; i < 100; i++) {
            if (user1Card.getClass().getSimpleName().contains("Spell") ||
                    user2Card.getClass().getSimpleName().contains("Spell")) {
                return elementFight(u1, u2, user1Card, user2Card);
            } else {
                return monsterFight(u1, u2, user1Card, user2Card);
            }
        }

        return null;
    }


    private static User monsterFight(User u1, User u2, Card c1, Card c2) {

//        if (c1.getNameAndType().contains(c2.getWeakness().toLowerCase(Locale.ROOT))) {
//            return u1;
//        } else if (c2.getNameAndType().contains(c1.getWeakness().toLowerCase(Locale.ROOT))) {
//            return u2;
//        } else {
//            if (c1.getClass().getSimpleName().contains("Monster") && c2.getClass().getSimpleName().contains("Monster"))
//                return winner(u1, u2, c1.getDamage(), c2.getDamage());
//            else
//                return elementFight(u1, u2, c1, c2);
//        }
        return null;
    }

    private static User elementFight(User u1, User u2, Card c1, Card c2) {
        Element t1 = c1.getType();
        Element t2 = c2.getType();

        if (t1 == c2.getTypeWeakness()) {
            return winner(u1, u2, c1.getDamage() * 2.0, c2.getDamage() / 2.0);
        } else if (t2 == c1.getTypeWeakness()) {
            return winner(u1, u2, c1.getDamage() / 2.0, c2.getDamage() * 2);
        } else {
            return winner(u1, u2, c1.getDamage(), c2.getDamage());
        }
    }

    private static User winner(User u1, User u2, double damageC1, double damageC2) {
        if (damageC1 > damageC2) {
            return u1;
        } else if (damageC1 < damageC2) {
            return u2;
        }
        return null;
    }
}
