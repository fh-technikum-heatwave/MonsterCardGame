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
    private Element typeWeakness; //Weakness against an element
    @JsonAlias({"Id"})
    private String id;
    @JsonAlias({"NameAndType"})
    private String nameAndType;
    @JsonAlias({"packageid"})
    private String packageid;
    @JsonAlias({"packageid"})
    private String userID;


    public Card() {
    }

    public Card(Element type, String name, int damage, String weakness, Element typeWeakness,
                String id, String nameAndType, String packageid, String userID) {
        this.type = type;
        this.name = name;
        this.damage = damage;
        this.weakness = weakness;
        this.typeWeakness = typeWeakness;
        this.id = id;
        this.nameAndType = nameAndType;
        this.packageid = packageid;
        this.userID = userID;
    }

    public Card(String id, String name, int damage, String packageid) {

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

    public void changePackageId(String packageid) {
        setPackageid(packageid);
    }

    public void changeUserId(String userID) {
        setUserID(userID);
    }
}
