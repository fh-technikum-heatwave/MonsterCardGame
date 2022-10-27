package main;

import main.card.Card;

public class Battle {

    public static void battle(Card cardU1, Card cardU2) {
        //je nachdem was für ein Battle es ist wird die entsprechende Methode aufgerufen
        spellFights(cardU1, cardU2);
    }


    private void monsterFight() {
    }

    private static void spellFights(User u1, User u) {
        Element t1 = cardU1.getType();
        Element t2 = cardU2.getType();

        if (t1.equals(t2)) {
        }

        if (t1.equals(Element.FIRE) && t2.equals(Element.WATER)) {//t2 Wins


        }

        if (t1.equals(Element.WATER) && t2.equals(Element.FIRE)) {
        }

        if (t1.equals(Element.NORMAL) && t2.equals(Element.FIRE)) {
        }

        if (t1.equals(Element.FIRE) && t2.equals(Element.NORMAL)) {
        }

        if (t1.equals(Element.WATER) && t2.equals(Element.NORMAL)) {
        }

        if (t1.equals(Element.NORMAL) && t2.equals(Element.WATER)) {
        }

    }

    private void mixedFights() {
    }

    public static Card winnerOfSpellFights(Card c1, Card c2, double damageC1, double damageC2) {
        if (damageC1 > damageC2) {
            return  c1;

        } else if (damageC1 < damageC2) {

        } else {
            //unentschieden
        }
    }


}
