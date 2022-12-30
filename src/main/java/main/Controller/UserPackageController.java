package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.model.Package;
import main.model.User;
import main.daos.CardDao;
import main.daos.PackageDao;
import main.daos.UserDao;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;

import java.sql.SQLException;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class UserPackageController extends Controller {


    private PackageDao packageDao;
    private CardDao cardDao;
    private UserDao userDao;


    public UserPackageController(PackageDao packageDao, CardDao cardDao, UserDao userDao) {
        setPackageDao(packageDao);
        setCardDao(cardDao);
        setUserDao(userDao);
    }



}
