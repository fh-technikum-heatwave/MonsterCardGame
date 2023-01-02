package main.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.model.Deck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
public class DeckDao implements DAO<Deck> {

    private Connection connection;

    public DeckDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public boolean create(Deck deck) throws SQLException {
        String query = "INSERT INTO deck (deckid,userid) VALUES (?,?)";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, deck.getDeckId());
        stmt.setString(2, deck.getUserId());
        return stmt.execute();

    }

    @Override
    public List<Deck> getAll() throws SQLException {
        return null;
    }

    @Override
    public Deck read(String t) throws SQLException {
        return null;
    }

    @Override
    public void update(Deck deck) {

    }

    @Override
    public void delete(String id) throws SQLException {

    }

    public String getDeckIdByUserId(String userId) throws SQLException {
        String query = "select deckid from deck where userid=?";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, userId);
        ResultSet rs = stmt.executeQuery();


        while (rs.next()) {
            return rs.getString(1);
        }

        return null;
    }
}
