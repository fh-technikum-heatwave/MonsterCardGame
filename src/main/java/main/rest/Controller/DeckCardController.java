package main.rest.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.CardDao;
import main.daos.DeckDao;
import main.Deck;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;

import java.sql.SQLException;
import java.util.UUID;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class DeckCardController extends Controller {


    private DeckDao deckDao;
    private CardDao cardDao;


    public DeckCardController(DeckDao deckDao, CardDao cardDao) {
        setDeckDao(deckDao);
        setCardDao(cardDao);
    }

    public Response createDeck(String userId, String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(body);

        if (actualObj.isArray()) {
            UUID uuid = UUID.randomUUID();
            Deck deck = new Deck(uuid.toString(), userId);
            try {
                deckDao.create(deck);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            for (var jsonObj : actualObj) {




                try {

                    cardDao.updateDeckID(jsonObj.asText(), deck.getDeckId());



                } catch (SQLException throwables) {

                    System.out.println(throwables);

                    return new Response(
                            HttpStatus.BAD_REQUEST,
                            ContentType.TEXT,
                            "Could not create"
                    );
                }
            }
        }
        return new Response(
                HttpStatus.OK,
                ContentType.TEXT,
                "Successfully bought"
        );
    }
}
