package main.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.CardDao;
import main.daos.UserDao;
import main.rest.Controller.CardController;
import main.rest.Controller.UserController;
import main.rest.server.Request;
import main.rest.server.Response;
import main.rest.server.ServerApp;
import main.rest.services.DataBaseService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class App implements ServerApp {

    @Setter(AccessLevel.PRIVATE)
    private UserController userController;
    private CardController cardController;
    private Connection connection;

    public App() {
        try {
            setConnection(new DataBaseService().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userController = new UserController(new UserDao(getConnection()));
        cardController = new CardController(new CardDao(getConnection()));
    }

    @Override
    public Response handleRequest(Request request) throws IOException {
        switch (request.getMethod()) {
            case GET: {
                if (request.getPathname().contains("/users/")) {
                    String username = request.getPathname().split("/")[2];

                    System.out.println(request.getAuthorizationToken());
                    return getUserController().getUserByUsername(username);

                } else if (request.getPathname().contains("/cards")) {

                }


                break;
            }

            case POST: {
                if (request.getPathname().contains("/users")) {
                    return getUserController().register(request.getBody());
                } else if (request.getPathname().contains("/sessions")) {
                    return getUserController().loginUser(request.getBody());
                } else if (request.getPathname().contains("/packages")) {

                    getCardController().createPackageWithNewCards(request.getBody());
                }
                break;
            }


            case PUT: {
                if (request.getPathname().contains("/users/")) {


                }
            }


            default:
                return null;
        }
        return null;
    }
}
