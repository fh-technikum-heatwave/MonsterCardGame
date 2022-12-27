package main.rest.Controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Package;
import main.daos.CardDao;
import main.daos.PackageDao;

import java.sql.SQLException;

public class PackageController extends Controller{
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private PackageDao packageDao;


    public PackageController(PackageDao packageDao) {
        setPackageDao(packageDao);
    }


    public void createPackage() throws SQLException {
        packageDao.create(new Package());
    }
}
