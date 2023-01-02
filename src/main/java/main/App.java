package main;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.CardDao;
import main.daos.DeckDao;
import main.daos.PackageDao;
import main.daos.UserDao;
import main.Controller.*;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Request;
import main.rest.server.Response;
import main.rest.server.ServerApp;
import main.rest.services.BattleService;
import main.rest.services.DataBaseService;
import main.rest.services.DeckService;
import main.rest.services.PackageService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class App implements ServerApp {

    @Setter(AccessLevel.PRIVATE)
    private UserController userController;
    private CardController cardController;
    private PackageController packageController;

    private BattleController battleController;

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
        var battleService = new BattleService(userDao, cardDao, deckDao);


        packageController = new PackageController(packageService);
        deckController = new DeckController(deckService);
        battleController = new BattleController(battleService);

        userController = new UserController(userDao);
        cardController = new CardController(cardDao);


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
                    getPackageController().buyPackage(getUserController().getSession().get(token));
                } else if (request.getPathname().contains("/packages")) {
                    getPackageController().createPackage(request.getBody());
                } else if (request.getPathname().contains("/battles")) {
                    String token = request.getAuthorizationToken();
                    return getBattleController().battle(getUserController().getSession().get(token));
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
