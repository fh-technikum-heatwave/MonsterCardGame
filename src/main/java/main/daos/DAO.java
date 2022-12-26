package main.daos;

import java.sql.SQLException;

public interface DAO<T> {

    void create(T t) throws SQLException;

    void read();

    void update();

    void delete();
}
