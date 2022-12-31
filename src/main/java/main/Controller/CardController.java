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

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CardController extends Controller {

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private CardDao cardDao;


    public CardController(CardDao cardDao) {
        setCardDao(cardDao);
    }


    public List<Card> getAllCards() {

        return null;
    }

    public Response createCards(String body, String packageId) throws JsonProcessingException {


        List<Card> cards = new LinkedList<>();
        JsonNode actualObj = getObjectMapper().readTree(body);
        Card c;

        if (actualObj.isArray()) {
            for (var jsonObj : actualObj) {

                if (jsonObj.get("Name").asText().contains("Spell")) {
                    c = getObjectMapper().readValue(jsonObj.toString(), SpellCard.class);

                } else {
                    c = getObjectMapper().readValue(jsonObj.toString(), MonsterCard.class);
                }

                cards.add(c);

            }
        }

        for (var ca: cards) {
            System.out.println(ca);

        }


//        if (actualObj.isArray()) {
//            for (var jsonObj : actualObj) {
//                Card c;
//                if (jsonObj.get("Name").asText().contains("Spell")) {
//                    c = new SpellCard(jsonObj.get("Id").asText(), jsonObj.get("Name").asText(), jsonObj.get("Damage").asInt(), packageId);
//                } else {
//                    c = new MonsterCard(jsonObj.get("Id").asText(), jsonObj.get("Name").asText(), jsonObj.get("Damage").asInt(), packageId);
//                }
//                try {
//
//                    cardDao.create(c);
//                } catch (SQLException throwables) {
//                    return new Response(
//                            HttpStatus.BAD_REQUEST,
//                            ContentType.JSON,
//                            "{ \"error\": \"Card already exists\", \"data\": null }"
//                    );
//                }
//            }
//        }
//        return new Response(
//                HttpStatus.OK,
//                ContentType.TEXT,
//                "Cards successfully created"
//        );


        return null;
    }


    public Response getUserCard(String userID) {
        try {
            List<Card> card = cardDao.getByUserdID(userID);
            String dataJSON = getObjectMapper().writeValueAsString(card);

            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"data\": " + dataJSON + ", \"error\": null }"
            );

        } catch (SQLException | JsonProcessingException throwables) {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.TEXT,
                    "Could not get Cards"
            );
        }
    }


}
