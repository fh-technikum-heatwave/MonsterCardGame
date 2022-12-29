package main;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.card.Card;

import java.util.List;
import java.util.Stack;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Deck {
    @JsonAlias({"userid"})
    private String userId;
    @JsonAlias({"deckid"})
    private String deckId;

    public Deck() {
    }

    public Deck(String deckId, String userId) {
        setDeckId(deckId);
        setUserId(userId);
    }
}
