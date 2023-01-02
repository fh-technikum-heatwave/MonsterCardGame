package main;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.CardDao;
import main.daos.DeckDao;
import main.daos.PackageDao;
import main.daos.UserDao;
import main.Controller.*;
import main.rest.server.Request;
import main.rest.server.Response;
import main.rest.server.ServerApp;
import main.rest.services.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class App implements ServerApp {

    @Setter(AccessLevel.PRIVATE)
    private UserController userController;
    private CardController cardController;
    private PackageController packageController;

    private GameController gameController;

    private DeckController deckController;
    private Connection connection;

    public App() {
        try {
            setConnection(new DataBaseService().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserDao userDao = new UserDao(getConnection());
        CardDao cardDao = new CardDao(getConnection());
        PackageDao packageDao = new PackageDao(getConnection());
        DeckDao deckDao = new DeckDao(getConnection());

        var packageService = new PackageService(packageDao, cardDao, userDao);
        var deckService = new DeckService(deckDao, cardDao);
        var battleService = new GameService(userDao, cardDao, deckDao);
        var cardService = new CardService(cardDao);


        packageController = new PackageController(packageService);
        deckController = new DeckController(deckService);
        gameController = new GameController(battleService);

        userController = new UserController(userDao);
        cardController = new CardController(cardService);


    }

    @Override
    public Response handleRequest(Request request) throws IOException {
        switch (request.getMethod()) {
            case GET: {
                if (request.getPathname().contains("/users/")) {
                    String username = request.getPathname().split("/")[2];
                    return getUserController().getUserByUsername(username, request.getAuthorizationToken());

                } else if (request.getPathname().contains("/cards")) {
                    String token = request.getAuthorizationToken();

                    return getCardController().getUserCard(getUserController().getSession().get(token));
                } else if (request.getPathname().contains("/decks")) {
                    String token = request.getAuthorizationToken();

                    return deckController.getDeck(getUserController().getSession().get(token));
                }
                break;
            }

            case POST: {
                if (request.getPathname().contains("/users")) {
                    return getUserController().register(request.getBody());
                } else if (request.getPathname().contains("/sessions")) {
                    return getUserController().loginUser(request.getBody());
                } else if (request.getPathname().contains("/transactions/packages")) {

                    String token = request.getAuthorizationToken();
                    return getPackageController().buyPackage(getUserController().getSession().get(token));
                } else if (request.getPathname().contains("/packages")) {
                    String token = request.getAuthorizationToken();
                    System.out.println("-------------token-----------");
                    System.out.println(token);
                    System.out.println(getUserController().getSession());
                    return getPackageController().createPackage(getUserController().getSession().get(token), request.getBody());
                } else if (request.getPathname().contains("/battles")) {
                    String token = request.getAuthorizationToken();
                    return getGameController().battle(getUserController().getSession().get(token));
                }
                break;
            }


            case PUT: {
                if (request.getPathname().contains("/users/")) {

                } else if (request.getPathname().contains("/decks")) {
                    String token = request.getAuthorizationToken();
                    System.out.println(token);
                    deckController.configureDeck(getUserController().getSession().get(token), request.getBody());

                }
            }


            default:
                return null;
        }
        return null;
    }
}
