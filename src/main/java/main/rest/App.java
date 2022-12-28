package main.rest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.CardDao;
import main.daos.PackageDao;
import main.daos.UserDao;
import main.rest.Controller.CardController;
import main.rest.Controller.UserPackageController;
import main.rest.Controller.PackageController;
import main.rest.Controller.UserController;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
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
    private PackageController packageController;
    private UserPackageController userCardController;
    private Connection connection;

    public App() {
        try {
            setConnection(new DataBaseService().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userController = new UserController(new UserDao(getConnection()));
        cardController = new CardController(new CardDao(getConnection()));
        packageController = new PackageController(new PackageDao(getConnection()));
        userCardController = new UserPackageController(new PackageDao(getConnection()), new CardDao(getConnection()), new UserDao(getConnection()));
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
                } else if (request.getPathname().contains("/transactions/packages")) {
                    userCardController.buyPackage();


                } else if (request.getPathname().contains("/packages")) {

                    String packageId = getPackageController().createPackage();

                    if (!packageId.isEmpty()) {
                        return getCardController().createPackageWithNewCards(request.getBody(), packageId);
                    } else {
                        return new Response(
                                HttpStatus.BAD_REQUEST,
                                ContentType.JSON,
                                "{ \"error\": \"Package could not be Created\", \"data\": null }"
                        );
                    }
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
