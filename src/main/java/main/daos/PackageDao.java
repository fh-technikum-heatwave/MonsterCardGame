package main.daos;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Element;
import main.Package;
import main.User;
import main.card.Card;
import main.card.MonsterCard;
import main.card.SpellCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class PackageDao implements DAO<Package> {

    private Connection connection;

    public PackageDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(Package aPackage) throws SQLException {
        String query = "INSERT INTO Package(packageid, cost) VALUES (?,?)";

        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, aPackage.getId());
        stmt.setInt(2, aPackage.getPACKAGE_COST());
        stmt.execute();

    }

    @Override
    public List<Package> getAll() throws SQLException {
        List<Package> packages = new LinkedList<>();
        String query = "select packageid from package";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            packages.add(new Package(rs.getString(1)));
        }

        return packages;
    }

    @Override
    public Package read(String t) throws SQLException {
        return null;
    }

    @Override
    public void update(Package apackage) {

    }

    @Override
    public void delete(String id) throws SQLException {
        String query = "DELETE FROM package WHERE packageid = ?";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, id);
       stmt.execute();

    }


    public String getPackageIdFromFirstRow() throws SQLException {
        String query = "select packageid from package limit 1";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery();


        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public Package getPackageWithCards(String id) throws SQLException {

        String query = "select cardid, name, damage, typ, weakness,typeweakness,nameandtype,Card.packageid, userid from Package join card on Package.packageid = Card.packageid where Package.packageid = ?";
        PreparedStatement stmt = getConnection().prepareStatement(query);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        List<Card> cards = new LinkedList<>();

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

        return new Package(cards.get(0).getPackageid(), cards);
    }
}
