package main.rest.services;

import main.daos.CardDao;
import main.daos.DeckDao;
import main.model.card.Card;

import java.sql.SQLException;
import java.util.List;

public class CardService {


    private CardDao cardDao;

    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }


    public List<Card> getCardsByUserId(String uid) {
        try {
            return cardDao.getByUserdID(uid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

}
