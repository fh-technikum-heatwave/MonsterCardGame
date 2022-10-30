package main.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Setter;
import main.User;
import main.rest.Controller.CardController;
import main.rest.server.Request;
import main.rest.server.Response;
import main.rest.server.ServerApp;
import main.rest.services.CardService;

import java.util.LinkedList;
import java.util.List;

public class App implements ServerApp {

    @Setter(AccessLevel.PRIVATE)
    private CardController cardController;
    User user;

    public App() {
        cardController = new CardController(new CardService());
        user = new User("Marko");
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        switch (request.getMethod()) {
            case GET: {
                if (request.getPathname().contains("/login/")) {
                    String username = request.getPathname().split("/")[2];
                    return this.cardController.login(username);
                } else if (request.getPathname().contains("/openPackages/")) {
                    System.out.println("Line 35");
                    System.out.println(user);
                    return this.cardController.openPackages(user);
                }

                break;
            }

            case POST: {
                if (request.getPathname().contains("/buyPackage/")) {

                    String count = request.getPathname().split("/")[2];
                    return this.cardController.buyPackage(Integer.parseInt(count), user);
                }
                break;
            }
            default:
                return null;
        }
        return null;
    }
}
