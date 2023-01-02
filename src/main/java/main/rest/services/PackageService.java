package main.rest.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.CardDao;
import main.daos.PackageDao;
import main.daos.UserDao;
import main.model.Package;
import main.model.User;
import main.model.card.Card;
import main.model.card.MonsterCard;
import main.model.card.SpellCard;
import main.rest.server.Response;
import org.postgresql.core.Tuple;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)

public class PackageService {
    PackageDao packageDao;
    CardDao cardDao;
    UserDao userDao;

    public PackageService(PackageDao packageDao, CardDao cardDao, UserDao userDao) {
        setPackageDao(packageDao);
        setCardDao(cardDao);
        setUserDao(userDao);
    }


    public String createPackagesAndCards(List<Card> cards) {

        Package p = new Package(UUID.randomUUID().toString());
        List<Card> createdCards = new LinkedList<>();


        try {
            packageDao.create(p);

            for (var c : cards) {
                c.changePackageId(p.getId());
                cardDao.create(c);
                createdCards.add(c);
            }

            return "201";
        } catch (SQLException throwables) {

            try {
                for (var c : createdCards) {
                    cardDao.delete(c.getId());
                }
                packageDao.delete(p.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return throwables.getSQLState();
        }
    }


    public User getUserById(String uid){
        try {
            return userDao.getById(uid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public boolean checkUserMoney(String uid) {
        try {
            Package apackage = getPackageDao().getOnePackage();
            User user = getUserDao().getById(uid);
            if (user.getCoins() >= apackage.getPACKAGE_COST()) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }


    public boolean checkForPackage() {
        try {
            Package p = packageDao.getOnePackage();

            if (p == null) {
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public List<Card> acquirePackage(String userId) {
        List<Card> cards = null;

        try {
            Package apackage = getPackageDao().getOnePackage();

            if (apackage == null) {
                return null;
            }

            User user = userDao.getById(userId);
            cards = cardDao.getCardsOfSpecificPackage(apackage.getId());
            for (var c : cards) {
                cardDao.updateUserId(c.getId(), userId);
                cardDao.updatePackageId(null, c.getId());
            }
            userDao.updateUserCoins(userId, user.getCoins() - apackage.getPACKAGE_COST());
            getPackageDao().delete(apackage.getId());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }
}
