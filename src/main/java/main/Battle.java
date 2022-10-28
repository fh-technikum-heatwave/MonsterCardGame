package main;

import main.card.Card;

public class Battle {

    public static User battle(User u1, User u2) {
        //je nachdem was für ein Battle es ist wird die entsprechende Methode aufgerufen

        Card user1Card = u1.getCards().get(0);
        Card user2Card = u2.getCards().get(0);

        System.out.println("User1: " + user1Card.getClass().getSimpleName() + " " + user1Card.getType());
        System.out.println("User2: " + user2Card.getClass().getSimpleName() + " " + user2Card.getType());

        if (user1Card.getClass().getSimpleName().contains("Spell") ||
                user2Card.getClass().getSimpleName().contains("Spell")) {
            return spellFights(u1, u2, user1Card, user2Card);
        } else if (user1Card.getClass().getSimpleName().contains("Monster") ||
                user2Card.getClass().getSimpleName().contains("Monster")) {
            System.out.println("monster Fight");
            return monsterFight(u1, u2, user1Card, user2Card);
        }


        return null;

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

        if (nameC1.equals("Wizzard") && nameC2.equals("Ork")) {
            return u1;
        }

        if (nameC1.equals("Ork") && nameC2.equals("Wizzard")) {
            return u2;
        }


        return null;
    }

    private static User spellFights(User u1, User u2, Card c1, Card c2) {
        Element t1 = c1.getType();
        Element t2 = c2.getType();

        if (t1 == t2) {
            return winnerOfSpellFights(u1, u2, c1.getDamage(), c2.getDamage());
        }

        if (t1 == Element.FIRE && t2 == Element.WATER) {//t2 Wins

            return winnerOfSpellFights(u1, u2, c1.getDamage() / 2.0, c2.getDamage() * 2);
        }

        if (t1 == Element.WATER && t2 == Element.FIRE) {
            return winnerOfSpellFights(u1, u2, c1.getDamage() * 2.0, c2.getDamage() / 2.0);
        }

        if (t1 == Element.NORMAL && t2 == Element.FIRE) {
            return winnerOfSpellFights(u1, u2, c1.getDamage() / 2.0, c2.getDamage() * 2);
        }

        if (t1 == Element.FIRE && t2 == Element.NORMAL) {

            return winnerOfSpellFights(u1, u2, c1.getDamage() * 2.0, c2.getDamage() / 2.0);
        }

        if (t1 == Element.WATER && t2 == Element.NORMAL) {
            return winnerOfSpellFights(u1, u2, c1.getDamage() / 2.0, c2.getDamage() * 2);
        }

        if (t1 == Element.NORMAL && t2 == Element.WATER) {
            return winnerOfSpellFights(u1, u2, c1.getDamage() * 2.0, c2.getDamage() / 2.0);
        }

        return null;
    }

    private void mixedFights() {

    }


    private static User winner(User u1, User u2, double damageC1, double damageC2) {
        if (damageC1 > damageC2) {
            return u1;
        } else if (damageC1 < damageC2) {
            return u2;
        } else {
            return null;
        }
    }

    private static User winnerOfSpellFights(User u1, User u2, double damageC1, double damageC2) {
        if (damageC1 > damageC2) {
            return u1;
        } else if (damageC1 < damageC2) {
            return u2;
        } else {
            return null;
        }
    }


}
