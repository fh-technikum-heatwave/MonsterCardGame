package main.daos;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class PackageDao implements DAO<Package> {

    private Connection connection;
    public PackageDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(Package aPackage) throws SQLException {
        String query = "INSERT INTO pacakge(cost) VALUES (?)";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setInt(1, aPackage.getPACKAGE_COST());

        stmt.execute();

    }

    @Override
    public Package read(String t) throws SQLException {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
