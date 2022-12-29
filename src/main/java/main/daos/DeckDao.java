package main.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Deck;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public void create(Deck deck) throws SQLException {
        String query = "INSERT INTO deck (deckid,userid) VALUES (?,?)";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, deck.getDeckId());
        stmt.setString(2, deck.getUserId());
        stmt.execute();

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
}
