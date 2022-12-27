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

    @JsonAlias({"username"})
    private String username;
    @JsonAlias({"coins"})
    private int coins = 20;
    @JsonAlias({"password"})
    private String password;
    @JsonAlias({"id"})
    private int id;
    @JsonAlias({"deck"})
    private Deck deck = new Deck();
    @JsonAlias({"cards"})
    private List<Card> cards = new LinkedList<>(); //all Cards
    @JsonAlias({"packages"})
    private List<Package> packages = new LinkedList<>();

    public User(){}

    public User(int id, String username, String password){
        setUsername(username);
        setPassword(password);
        setId(id);
    }
}
