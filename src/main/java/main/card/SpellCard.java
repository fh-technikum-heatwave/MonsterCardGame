package main.card;

import main.Element;

public class SpellCard extends Card{
    public SpellCard(String id, String name, int damage, String pacakgeID) {
        super(id, name, damage,pacakgeID);
    }

    public SpellCard(){
    }


    public SpellCard(Element type, String name, int damage, String weakness, Element typeWeakness, String id, String nameAndType, String packageid, String userID) {
        super(type, name, damage, weakness, typeWeakness, id, nameAndType, packageid, userID);
    }
}
