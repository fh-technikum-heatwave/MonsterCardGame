package main.rest.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.User;
import main.card.Card;
import main.card.MonsterCard;
import main.card.SpellCard;
import main.daos.CardDao;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.CardService;

import java.io.DataInput;
import java.io.IOException;
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

    public void createPackageWithNewCards(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(body);

        List<Card> cards = new LinkedList<>();

        if (actualObj.isArray()) {
            for (var jsonObj : actualObj) {
                Card c;
                if (jsonObj.get("Name").asText().contains("Spell")) {
                    c = new SpellCard(jsonObj.get("Id").toString(), jsonObj.get("Name").toString(), jsonObj.get("Damage").asInt(), 1);
                } else {
                    c = new MonsterCard(jsonObj.get("Id").toString(), jsonObj.get("Name").toString(), jsonObj.get("Damage").asInt(), 1);
                }
                try {
                    cardDao.create(c);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


}
