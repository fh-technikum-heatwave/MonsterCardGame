package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.Factory.ResponseFactory;
import main.model.card.Card;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.DeckService;

import java.util.List;

public class DeckController extends Controller {
    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    private boolean isMissingBody(String body) {
        return body == null || body.isEmpty();
    }

    private boolean isMissingUser(String userId) {
        return userId == null;
    }


    public Response configureDeck(String userId, String body) throws JsonProcessingException {

        if (isMissingBody(body)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.BAD_REQUEST, "Body missing");
        }

        List<String> cardIds = getObjectMapper().readValue(body, List.class);

        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }

        if (cardIds.size() != 4) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.BAD_REQUEST,
                    "The provided deck did not include the required amount of cards");
        }

        boolean worked = deckService.configureDeck(userId, cardIds);

        String message = "";
        HttpStatus httpStatus = HttpStatus.OK;

        if (worked) {
            message = "The deck has been successfully configured";
        } else {
            message = "At least one of the provided cards does not belong to the user or is not available.";
            httpStatus = HttpStatus.Forbidden;
        }

        return ResponseFactory.buildResponse(ContentType.TEXT, httpStatus, message);

    }

    public Response getDeck(String userId, String format) throws JsonProcessingException {
        boolean asPlain = false;

        if (format.contains("plain")) {
            asPlain = true;
        }

        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT,HttpStatus.Unauthorized,"Token Missing/Token invalid");
        }

        List<Card> card = deckService.getDeck(userId);
        HttpStatus httpStatus = HttpStatus.OK;

        if (card.size() == 0) {
            httpStatus = HttpStatus.NO_CONTENT;
        }

        String dataJSON = getObjectMapper().writeValueAsString(card);

        if (asPlain) {
            return ResponseFactory.buildResponse(ContentType.TEXT,httpStatus,card.toString());
        } else {
            return ResponseFactory.buildResponse(ContentType.JSON,httpStatus,dataJSON);
        }
    }
}
