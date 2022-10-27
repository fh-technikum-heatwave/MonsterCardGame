package main;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.card.Card;
import main.card.MonsterCard;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Package {
    static final int CARD_COUNT = 5;
    static final int PACKAGE_COST = 5;
    private static Random random = new Random();

    public List<Card> openPackage() {
        List<Card> cards = new LinkedList<>();
        for (int i = 0; i < CARD_COUNT; i++) {
            Card card = CardFactory.createCard(random.nextInt(2));
            cards.add(card);
        }

        return cards;
    }
}