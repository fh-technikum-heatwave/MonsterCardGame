package main.card;

import main.Element;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MonsterCard extends Card {

    public MonsterCard(String id, String name, int damage, String packageID) {
        super(id, name, damage,packageID);
    }

    public MonsterCard(){}

    public MonsterCard(Element type, String name, int damage, String weakness, Element typeWeakness, String id, String nameAndType, String packageid, String userID) {
        super(type, name, damage, weakness, typeWeakness, id, nameAndType, packageid, userID);
    }
}
