package main.daos;

import java.sql.SQLException;

public interface User<T> {

    T getByUserId(String userId) throws SQLException;

}
