package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.model.card.Card;
import main.model.card.MonsterCard;
import main.model.card.SpellCard;
import main.daos.CardDao;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.CardService;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CardController extends Controller {

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private CardService cardService;


    public CardController(CardService cardService) {
        setCardService(cardService);
    }


    public Response getUserCard(String userID) throws JsonProcessingException {

        if (userID == null) {
            return new Response(
                    HttpStatus.Forbidden,
                    ContentType.TEXT,
                    "Token Missing/Token invalid"
            );
        }


        List<Card> card = cardService.getCardsByUserId(userID);
        String dataJSON = getObjectMapper().writeValueAsString(card);

        HttpStatus httpStatus = HttpStatus.OK;

        if (card == null ||card.size()==0) {
            httpStatus = HttpStatus.NO_CONTENT;
        }

        return new Response(
                httpStatus,
                ContentType.JSON,
                "{ \"data\": " + dataJSON + ", \"error\": null }"
        );


    }
}
