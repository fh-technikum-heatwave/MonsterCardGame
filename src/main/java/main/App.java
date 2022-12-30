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
import main.rest.services.DataBaseService;
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
    private UserPackageController userCardController;
    private DeckCardController deckCardController;
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

        packageController = new PackageController(packageService);

        userController = new UserController(userDao);
        cardController = new CardController(cardDao);
        userCardController = new UserPackageController(packageDao, cardDao, userDao);
        deckCardController = new DeckCardController(deckDao, cardDao);
    }

    @Override
    public Response handleRequest(Request request) throws IOException {
        switch (request.getMethod()) {
            case GET: {
                if (request.getPathname().contains("/users/")) {
                    String username = request.getPathname().split("/")[2];
                    return getUserController().getUserByUsername(username, request.getAuthorizationToken());

                } else if (request.getPathname().contains("/cards")) {
                    String id = "099a034a-30d4-4361-830a-4263356d35e5";

                    return getCardController().getUserCard(id);
                }
                break;
            }

            case POST: {
                if (request.getPathname().contains("/users")) {
                    return getUserController().register(request.getBody());
                } else if (request.getPathname().contains("/sessions")) {
                    return getUserController().loginUser(request.getBody());
                } else if (request.getPathname().contains("/transactions/packages")) {
                    getPackageController().buyPackage();
                } else if (request.getPathname().contains("/packages")) {
                    getPackageController().createPackage(request.getBody());
                }
                break;
            }


            case PUT: {
                if (request.getPathname().contains("/users/")) {


                } else if (request.getPathname().contains("/decks")) {
                    return deckCardController.createDeck("099a034a-30d4-4361-830a-4263356d35e5", request.getBody());
                }
            }


            default:
                return null;
        }
        return null;
    }
}
