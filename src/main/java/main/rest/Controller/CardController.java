package main.rest.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.User;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.CardService;

public class CardController extends Controller {

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private CardService cardService;


    public CardController(CardService cardService) {
        setCardService(cardService);
    }


    public Response login(String username) throws JsonProcessingException {
        User user = getCardService().login(username);
        String userDataJSON = getObjectMapper().writeValueAsString(user);

        System.out.println(user.getCards());

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + userDataJSON + ", \"error\": null }"
        );
    }

    public Response openPackages(User user) throws JsonProcessingException {
    getCardService().openPackages(user);
        String userDataJSON = getObjectMapper().writeValueAsString(user);

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + userDataJSON + ", \"error\": null }"
        );
    }

    //POST
    public Response buyPackage(int count, User user) throws JsonProcessingException {
        getCardService().buyPackage(count, user);

        String userDataJSON = getObjectMapper().writeValueAsString(user);

        System.out.println("46");

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + userDataJSON + ", \"error\": null }"
        );
    }



}
