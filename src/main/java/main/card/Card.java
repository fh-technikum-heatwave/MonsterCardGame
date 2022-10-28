package main.card;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Element;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class Card {
    protected Element type;
    protected String name;
    protected double damage;
    private static Random random = new Random();
    private List<String> monsterCardNames = Arrays.asList("Wizard", "Goblin", "Knight", "Dragon", "Ork", "Kraken", "Elves");

    public Card(String name) {

        if (name.equals("Monster")) {
            Collections.shuffle(monsterCardNames);
            setName(monsterCardNames.get(0));
        } else {
            setName(name);
        }

        int rnd = random.nextInt(3);
        if (rnd == 0) {
            type = Element.WATER;
            damage = 100;
        } else if (rnd == 1) {
            type = Element.FIRE;
            damage = 100;
        } else {
            type = Element.NORMAL;
            damage = 100;
        }

    }

//    public Card(String name, double damage, Element type) {
//        setDamage(damage);
//        setName(name);
//        setType(type);
//    }
//    protected final int DAMAGE = ;
}
