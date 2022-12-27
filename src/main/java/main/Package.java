package main;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.card.Card;
import main.card.MonsterCard;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class Package {
    @JsonAlias({"cost"})
    final int PACKAGE_COST = 5;
    @JsonAlias({"packageid"})
    private String id;

    public Package(){
    }

    public Package(String id){
        setId(id);
    }
}
