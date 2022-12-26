package main.daos;

import java.sql.SQLException;

public interface DAO<T> {

    void create(T t) throws SQLException;

    T read(String t) throws SQLException;

    void update();

    void delete();
}
