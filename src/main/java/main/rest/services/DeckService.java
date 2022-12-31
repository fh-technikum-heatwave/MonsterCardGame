package main.rest.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.CardDao;
import main.daos.DeckDao;
import main.daos.UserDao;
import main.model.Deck;
import main.model.card.Card;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
public class DeckService {

    private DeckDao deckDao;

    private CardDao cardDao;

    public DeckService(DeckDao deckDao, CardDao cardDao) {
        this.deckDao = deckDao;
        this.cardDao = cardDao;
    }

    public void configureDeck(String userId, List<String> cardIds) throws SQLException {


        System.out.println(userId);
        System.out.println("token----------");
        Deck deck = new Deck(UUID.randomUUID().toString(), userId);


        deckDao.create(deck);

        for (var cardId : cardIds) {
            getCardDao().updateDeckID(cardId, deck.getDeckId());
        }
    }

    public List<Card> getDeck(String userId) {
        try {
            String deckId = deckDao.getDeckIdByUserId(userId);

            List<Card> cards = cardDao.getByDeckid(deckId);

            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
