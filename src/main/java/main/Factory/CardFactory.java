package main.Factory;

import main.Element;
import main.model.card.Card;
import main.model.card.MonsterCard;
import main.model.card.SpellCard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardFactory {

    public static Card createCard(ResultSet rs) throws SQLException {
        String cardId = rs.getString(1);
        String name = rs.getString(2);
        int damage = rs.getInt(3);
        Element type = Element.valueOf(rs.getString(4));
        String weakness = rs.getString(5);
        Element typeWeakness = Element.valueOf(rs.getString(6));
        String nameAndType = rs.getString(7);
        String packageId = rs.getString(8);
        String userID = rs.getString(9);

        if (name.contains("Spell")) {
            return new SpellCard(type, name, damage, weakness, typeWeakness, cardId, nameAndType, packageId, userID);
        } else {
            return new MonsterCard(type, name, damage, weakness, typeWeakness, cardId, nameAndType, packageId, userID);
        }
    }

}
