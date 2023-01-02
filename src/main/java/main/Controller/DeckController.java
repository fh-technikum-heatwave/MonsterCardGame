package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import main.model.card.Card;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.DeckService;

import java.sql.SQLException;
import java.util.List;

public class DeckController extends Controller {
    private DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }



    public void configureDeck(String userId, String body) throws JsonProcessingException {

        List<String> cardIds = getObjectMapper().readValue(body, new TypeReference<List<String>>() {
        });

        System.out.println(userId);

        try {
            deckService.configureDeck(userId, cardIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Response getDeck(String userId) throws JsonProcessingException {
        List<Card> card = deckService.getDeck(userId);

        String dataJSON = getObjectMapper().writeValueAsString(card);

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + dataJSON + ", \"error\": null }"
        );


    }
}
