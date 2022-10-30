package main.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Setter;
import main.rest.Controller.CardController;
import main.rest.server.Request;
import main.rest.server.Response;
import main.rest.server.ServerApp;
import main.rest.services.CardService;
import main.rest.services.UserService;

public class App implements ServerApp {

    @Setter(AccessLevel.PRIVATE)
    private CardController cardController;


    public App() {
        cardController = new CardController(new CardService());
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {


        switch (request.getMethod()) {
            case GET: {
                if (request.getPathname().contains("/login/")) {
                    String username = request.getPathname().split("/")[2];
                    Response response = this.cardController.login(username);
                    System.out.println(response);
                    return response;
                }

                break;
            }

            default:
                return null;
        }

        return null;
    }
}
