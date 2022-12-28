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
public class Package {
    @JsonAlias({"cost"})
    final int PACKAGE_COST = 5;
    @JsonAlias({"packageid"})
    private String id;
    @JsonAlias({"cards"})
    private List<Card> cards= new LinkedList<>();

    public Package(){
    }

    public Package(String id){
        setId(id);
    }
    public Package(String id, List<Card>cards){
        setCards(cards);
        setId(id);
    }
}
