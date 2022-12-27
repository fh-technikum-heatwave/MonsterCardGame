package main.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.card.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException
        ;
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class CardDao implements DAO<Card>{

    private Connection connection;

    public CardDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(Card card) throws SQLException {
        String query = "INSERT INTO card (cardid,name,damage,typ,weakness,tpyeweakness,nameandtype,packageid) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1,card.getId());
        stmt.setString(2, card.getName());
        stmt.setInt(3, card.getDamage());
        stmt.setString(4, card.getType().toString());
        stmt.setString(5, card.getWeakness());
        stmt.setString(6, card.getTypeWeakness().toString());
        stmt.setString(7, card.getNameAndType());
        stmt.setInt(8, card.getPackageid());
        stmt.execute();
    }

    @Override
    public Card read(String t) throws SQLException {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
