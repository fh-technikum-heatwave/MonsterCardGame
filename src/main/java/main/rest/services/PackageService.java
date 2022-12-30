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
import main.model.card.Card;
import main.model.card.MonsterCard;
import main.model.card.SpellCard;

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


    public void createPackagesAndCards(List<Card> cards) throws SQLException {

        Package p = new Package(UUID.randomUUID().toString());
        List<Card> createdCards = new LinkedList<>();
        boolean packageCreated = false;

        try {
            packageCreated = packageDao.create(p);

            for (var c : cards) {
                c.changePackageId(p.getId());
                cardDao.create(c);
                createdCards.add(c);
            }
        } catch (SQLException throwables) {
            for (var c : createdCards) {
                cardDao.delete(c.getId());
            }

            if (packageCreated) {
                packageDao.delete(p.getId());
            }
        }
    }


    public void acquirePackage(){

    }
}
