package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Factory.ResponseFactory;
import main.model.card.Card;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.CardService;

import java.util.List;

public class CardController extends Controller {

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private CardService cardService;


    public CardController(CardService cardService) {
        setCardService(cardService);
    }

    private boolean isMissingUser(String userId) {
        return userId == null;
    }


    public Response getUserCard(String userID) throws JsonProcessingException {

        if (isMissingUser(userID)) {
            ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }

        List<Card> card = cardService.getCardsByUserId(userID);
        String dataJSON = getObjectMapper().writeValueAsString(card);

        HttpStatus httpStatus = HttpStatus.OK;

        if (card == null || card.size() == 0) {
            httpStatus = HttpStatus.NO_CONTENT;
        }

        return ResponseFactory.buildResponse(ContentType.JSON, httpStatus, dataJSON);
    }
}
