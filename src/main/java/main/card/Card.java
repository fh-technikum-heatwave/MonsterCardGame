package main.card;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Element;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class Card {

    @JsonAlias({"Typ"})
    protected Element type;
    @JsonAlias({"Name"})
    protected String name;
    @JsonAlias({"Damage"})
    protected int damage;
    @JsonAlias({"Weakness"})
    private String weakness; //Weakness against an other Card
    @JsonAlias({"TypeWeakness"})
    private Element typeWeakness; //Weakness agains an element
    @JsonAlias({"Id"})
    private String id;
    @JsonAlias({"NameAndType"})
    private String nameAndType;
    @JsonAlias({"packageid"})
    private int packageid;


    public Card() {
    }

    public Card(String id, String name, int damage, int packageid) {

        setId(id);
        setName(name);
        setDamage(damage);
        setPackageid(packageid);
        setNameAndType(name.toLowerCase(Locale.ROOT));

        if (name.contains("Water")) {
            setType(Element.WATER);
            setTypeWeakness(Element.NORMAL);
        } else if (name.contains("Fire")) {
            setType(Element.FIRE);
            setTypeWeakness(Element.WATER);
        } else {
            setType(Element.NORMAL);
            setTypeWeakness(Element.FIRE);
            setNameAndType((type + "" + name).toLowerCase(Locale.ROOT));
        }

        //Set Weakness of certain Cards
        if (name.contains("Goblin")) {
            weakness = "Dragon";
        } else if (name.contains("Knight")) {
            weakness = "WaterSpell";
        } else if (name.contains("Dragon")) {
            weakness = "FireElve";
        } else if (name.contains("Ork")) {
            weakness = "Wizard";
        } else {
            weakness = "NO";
        }

    }
}
