package main.rest.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.CardDao;
import main.daos.DeckDao;
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

    public boolean configureDeck(String userId, List<String> cardIds) {

        boolean back = false;
        Deck deck = null;


        List<Card> oldDeck = getDeck(userId);


        try {

            String oldDeckId = deckDao.getDeckIdByUserId(userId);

            String deckId = oldDeckId;

            if (oldDeckId == null) {
                deck = new Deck(UUID.randomUUID().toString(), userId);
                deckId = deck.getDeckId();
                deckDao.create(deck);
            }
            System.out.println(" IN Deck Service");

            for (var cardId : cardIds) {

                System.out.println("Deck loop");

                int retunValue = getCardDao().updateDeckID(cardId, deckId, userId);

                System.out.println("retunrValue " + retunValue);
                System.out.println("------------------------");
                System.out.println(retunValue);

                if (retunValue == 0) {
                    back = true;
                    break;
                }
            }

            if (back) {
                for (var card : oldDeck) {
                    getCardDao().updateDeckID(card.getId(), oldDeckId, userId);// man muss hier userId extra angeben wegen der Möglichkeit dass es davor noch keinen user gab
                }
                return false;
            }


            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
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
