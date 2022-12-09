package main;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.card.Card;

import java.util.LinkedList;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class User {

    private String username;
    private List<Card> cards = new LinkedList<>(); //all Cards
    private int coins = 20;
    private Deck deck = new Deck();
    @Getter(AccessLevel.PRIVATE)
    private List<Package> packages = new LinkedList<>();

    public User(String username) {
        setUsername(username);
    }

    public void selectDeck() {
        deck.selectCards(cards);
    }

    public void buyPackage(int count) {
        for (int i = 0; i < count; i++) {
            packages.add(new Package());
        }
    }

    public void openPackages() {
        for (var p : packages) {
            var openCards = p.openPackage();
            cards.addAll(openCards);
        }
    }

    public void tradeCards() {
    }
}
