package main;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.card.Card;

import java.util.LinkedList;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class User {

    @JsonAlias({"name"})
    private String username;
    @JsonAlias({"coins"})
    private int coins = 20;
    @JsonAlias({"password"})
    private String password;

    private Deck deck = new Deck();

    private List<Card> cards = new LinkedList<>(); //all Cards
    @Getter(AccessLevel.PRIVATE)
    private List<Package> packages = new LinkedList<>();

    public User(){}

    public User(String username, String password){
        setUsername(username);
        setPassword(password);
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
