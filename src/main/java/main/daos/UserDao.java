package main.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class UserDao implements DAO<User> {

    private Connection connection;

    public UserDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(User user) throws SQLException {

        String query = "INSERT INTO users (username,password,coins) VALUES (?,?,?)";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setInt(3, user.getCoins());
        stmt.execute();

    }

    @Override
    public User read(String username) throws SQLException {
        String query = "select * from users where username = ?";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
        }

        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}