package main;

import main.card.Card;
import main.card.MonsterCard;
import main.card.SpellCard;

public class CardFactory {

    public static Card createCard(int number) {
        switch (number) {
            case 0: {
                return new MonsterCard();
            }

            case 1: {
                return new SpellCard();
            }

            default:
                return null;
        }
    }
}
