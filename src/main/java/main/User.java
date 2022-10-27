package main;

import lombok.Getter;
import main.card.Card;

import java.util.List;

@Getter
public class User {
    private int coins;
    private String username;
    private List<Card> cards;
    private Deck deck = new Deck();

    public User(List<Card> cards) {

        this.cards = cards;
    }

    public void selectDeck() {
        deck.selectCards(cards);
    }

    public void buyPackage(int count){
        // neue Instanz von Package anlegen, da man ja neue Karten kauft
        // Die Packages in einer Liste speichern und dann öffnen

    }

    public void tradeCards(){}
}
