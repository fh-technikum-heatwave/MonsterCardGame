package main.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Deck;
import main.Element;
import main.User;
import main.card.Card;
import main.card.MonsterCard;
import main.card.SpellCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException
        ;
import java.util.LinkedList;
import java.util.List;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class CardDao implements DAO<Card> {

    private Connection connection;

    public CardDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(Card card) throws SQLException {
        String query = "INSERT INTO card (cardid,name,damage,typ,weakness,typeweakness,nameandtype,packageid) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, card.getId());
        stmt.setString(2, card.getName());
        stmt.setInt(3, card.getDamage());
        stmt.setString(4, card.getType().toString());
        stmt.setString(5, card.getWeakness());
        stmt.setString(6, card.getTypeWeakness().toString());
        stmt.setString(7, card.getNameAndType());
        stmt.setString(8, card.getPackageid());
        stmt.execute();
    }

    @Override
    public List<Card> getAll() throws SQLException {

        List<Card> cards = new LinkedList<>();
        String query = "select * from card ";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String name = rs.getString(2);
            if (name.contains("Spell")) {
                cards.add(new SpellCard(Element.valueOf(rs.getString(4)), name, rs.getInt(3), rs.getString(5),
                        Element.valueOf(rs.getString(6)), rs.getString(1),
                        rs.getString(7), rs.getString(8), rs.getString(9)));
            } else {
                cards.add(new MonsterCard(Element.valueOf(rs.getString(4)), name, rs.getInt(3), rs.getString(5),
                        Element.valueOf(rs.getString(6)), rs.getString(1),
                        rs.getString(7), rs.getString(8), rs.getString(9)));
            }
        }

        return cards;
    }


    @Override
    public Card read(String t) throws SQLException {
        return null;
    }

    @Override
    public void update(Card card) {

    }

    public boolean updatePackageId(Card card) throws SQLException {
        String query = "UPDATE card SET packageid = ? WHERE cardid = ?";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, card.getPackageid());
        stmt.setString(2, card.getId());
        return stmt.execute();

    }

    public boolean updateUserId(Card card) throws SQLException {
        String query = "UPDATE card SET userid = ? WHERE cardid = ?";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, card.getUserID());
        stmt.setString(2, card.getId());
        return stmt.execute();
    }


    public boolean updateDeckID(String cardid, String deckid) throws SQLException {
        String query = "UPDATE card SET deckid = ? WHERE cardid = ?";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, deckid);
        stmt.setString(2,cardid);
        return stmt.execute();
    }


    @Override
    public void delete(String id) {

    }

    public List<Card> getByUserdID(String userID) throws SQLException {
        List<Card> cards = new LinkedList<>();
        String query = "select * from card where userid = ?";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, userID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String name = rs.getString(2);
            if (name.contains("Spell")) {
                cards.add(new SpellCard(Element.valueOf(rs.getString(4)), name, rs.getInt(3), rs.getString(5),
                        Element.valueOf(rs.getString(6)), rs.getString(1),
                        rs.getString(7), rs.getString(8), rs.getString(9)));
            } else {
                cards.add(new MonsterCard(Element.valueOf(rs.getString(4)), name, rs.getInt(3), rs.getString(5),
                        Element.valueOf(rs.getString(6)), rs.getString(1),
                        rs.getString(7), rs.getString(8), rs.getString(9)));
            }
        }

        return cards;
    }

}
