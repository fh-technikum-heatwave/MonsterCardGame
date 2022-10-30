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


    // GET /login/:username
    public Response login(String username) throws JsonProcessingException {

        System.out.println(username);



        User user = getCardService().login(username);
        String cityDataJSON = getObjectMapper().writeValueAsString(user);

        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + cityDataJSON + ", \"error\": null }"
        );

    }

}
