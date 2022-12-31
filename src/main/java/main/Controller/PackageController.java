package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.model.Package;
import main.daos.PackageDao;
import main.model.card.Card;
import main.model.card.MonsterCard;
import main.model.card.SpellCard;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.PackageService;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class PackageController extends Controller {
    private PackageDao packageDao;
    private PackageService packageService;


    public PackageController(PackageService packageService) {
        setPackageService(packageService);
    }


    public String createPackage(String body) throws JsonProcessingException {


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

        try {
            getPackageService().createPackagesAndCards(cards);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }


    public Response buyPackage(String userId) {

        getPackageService().acquirePackage(userId);

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "{ \"error\": \"Card already exists\", \"data\": null }"
        );
    }


}
