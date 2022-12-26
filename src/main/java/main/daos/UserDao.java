package main.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class UserDao implements DAO<User> {

    private Connection connection;

    public UserDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(User user) throws SQLException {
        String query = "INSERT INTO user (username,password, coins) VALUES (?,?,?)";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setInt(3, user.getCoins());

    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
