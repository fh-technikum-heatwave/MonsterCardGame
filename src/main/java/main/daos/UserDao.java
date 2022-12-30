package main.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class UserDao implements DAO<User> {

    private Connection connection;


    public UserDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public boolean create(User user) throws SQLException {

        String query = "INSERT INTO users (username,password,coins, UserId) VALUES (?,?,?,?)";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setInt(3, user.getCoins());
        stmt.setString(4, user.getId());
        return stmt.execute();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User read(String username) throws SQLException {
        String query = "select * from users where username = ?";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            return new User(rs.getString(2), rs.getInt(4), rs.getString(3), rs.getString(1));
        }

        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String id) {

    }
}
