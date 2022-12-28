package main.rest.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Package;
import main.User;
import main.card.Card;
import main.daos.CardDao;
import main.daos.PackageDao;
import main.daos.UserDao;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

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


    public void createPackageWithCard() {

    }


    public Response buyPackage() {

        try {

            String id = packageDao.getPackageIdFromFirstRow();
            System.out.println(id);
            Package p = packageDao.getPackageWithCards(id);
            User user = userDao.read("marko");

            if (user.getCoins() >= p.getPACKAGE_COST()) {

                for (var c : p.getCards()) {
                    c.changePackageId(null);
                    cardDao.updatePackageId(c);
                }

                for (var c : p.getCards()) {
                    c.changeUserId(user.getId());
                    cardDao.updateUserId(c);
                }
                packageDao.delete(id);

                String userDataJSON = getObjectMapper().writeValueAsString(p.getCards());

                return new Response(
                        HttpStatus.OK,
                        ContentType.TEXT,
                        "Successfully bought"
                );

            } else {
                // not engough money

            }


        } catch (SQLException | JsonProcessingException throwables) {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "{ \"error\": \"Card already exists\", \"data\": null }"
            );
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "{ \"error\": \"Card already exists\", \"data\": null }"
        );
    }


}
