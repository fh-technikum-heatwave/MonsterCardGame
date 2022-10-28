package main;

import main.card.Card;

public class Battle {

    public static User battle(User u1, User u2) {
        //je nachdem was für ein Battle es ist wird die entsprechende Methode aufgerufen

        Card user1Card = u1.getCards().get(0);
        Card user2Card = u2.getCards().get(0);

        System.out.println("User1: " + user1Card.getName() + " " + user1Card.getType() + " Damage " + user1Card.getDamage());
        System.out.println("User2: " + user2Card.getName() + " " + user2Card.getType() + " Damage " + user2Card.getDamage());

        if (user1Card.getClass().getSimpleName().contains("Spell") ||
                user2Card.getClass().getSimpleName().contains("Spell")) {
            return spellFights(u1, u2, user1Card, user2Card);
        } else if (user1Card.getClass().getSimpleName().contains("Monster") ||
                user2Card.getClass().getSimpleName().contains("Monster")) {
            System.out.println("monster Fight");
            return monsterFight(u1, u2, user1Card, user2Card);
        } else {
            System.out.println("Mixed Fight");
            return mixedFight(u1, u2, user1Card, user2Card);
        }
    }


    private static User monsterFight(User u1, User u2, Card c1, Card c2) {
        String nameC1 = c1.getName();
        String nameC2 = c2.getName();

        if (nameC1.equals("Goblin") && nameC2.equals("Dragon")) {
            return u2;
        }
        if (nameC1.equals("Dragon") && nameC2.equals("Dragon")) {
            return u1;
        }

        if (nameC1.equals("Wizard") && nameC2.equals("Ork")) {
            return u1;
        }

        if (nameC1.equals("Ork") && nameC2.equals("Wizard")) {
            return u2;
        }


        if (nameC1.equals("Elve") && nameC2.equals("Dragon") && c1.getType() == Element.FIRE) {
            return u1;
        }

        if (nameC1.equals("Dragon") && nameC2.equals("Elve") && c2.getType() == Element.FIRE) {
            return u2;
        } else {
            return winner(u1, u2, c1.getDamage(), c2.getDamage());
        }

        //normaler Kampf


//        return null;
    }

    private static User spellFights(User u1, User u2, Card c1, Card c2) {
        Element t1 = c1.getType();
        Element t2 = c2.getType();

        if (t1 == t2) {
            return winner(u1, u2, c1.getDamage(), c2.getDamage());
        }

        if (t1 == Element.FIRE && t2 == Element.WATER) {//t2 Wins

            return winner(u1, u2, c1.getDamage() / 2.0, c2.getDamage() * 2);
        }

        if (t1 == Element.WATER && t2 == Element.FIRE) {
            return winner(u1, u2, c1.getDamage() * 2.0, c2.getDamage() / 2.0);
        }

        if (t1 == Element.NORMAL && t2 == Element.FIRE) {
            return winner(u1, u2, c1.getDamage() / 2.0, c2.getDamage() * 2);
        }

        if (t1 == Element.FIRE && t2 == Element.NORMAL) {

            return winner(u1, u2, c1.getDamage() * 2.0, c2.getDamage() / 2.0);
        }

        if (t1 == Element.WATER && t2 == Element.NORMAL) {
            return winner(u1, u2, c1.getDamage() / 2.0, c2.getDamage() * 2);
        }

        if (t1 == Element.NORMAL && t2 == Element.WATER) {
            return winner(u1, u2, c1.getDamage() * 2.0, c2.getDamage() / 2.0);
        }

        return null;
    }

    private static User mixedFight(User u1, User u2, Card c1, Card c2) {

        if (c1.getName().equals("Kraken") && c2.getName().contains("Spell")) {
            return u1;
        }

        if (c2.getName().equals("Kraken") && c1.getName().contains("Spell")) {
            return u2;
        }

        if (c1.getName().contains("Spell") && c1.getType() == Element.WATER && c2.getName().equals("Knight")) {
            return u1;
        }
        if (c2.getName().contains("Spell") && c2.getType() == Element.WATER && c1.getName().equals("Knight")) {
            return u2;
        } else {
            return spellFights(u1, u2, c1, c2);
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
