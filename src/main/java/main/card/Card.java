package main.card;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Element;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class Card {
    protected Element type;
    protected String name;
    protected double damage;

//    public Card(String name, double damage, Element type) {
//        setDamage(damage);
//        setName(name);
//        setType(type);
//    }
//    protected final int DAMAGE = ;
}
