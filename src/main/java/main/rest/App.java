package main.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.User;
import main.daos.UserDao;
import main.rest.Controller.CardController;
import main.rest.Controller.Controller;
import main.rest.Controller.UserController;
import main.rest.http.HttpStatus;
import main.rest.server.Request;
import main.rest.server.Response;
import main.rest.server.ServerApp;
import main.rest.services.CardService;
import main.rest.services.DataBaseService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class App implements ServerApp {

    @Setter(AccessLevel.PRIVATE)
    private UserController UserController;
    private Connection connection;

    public App() {
        try {
            setConnection(new DataBaseService().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserController = new UserController(new UserDao(getConnection()));
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        switch (request.getMethod()) {
            case GET: {
                if (request.getPathname().contains("/users/")) {
                    String username = request.getPathname().split("/")[2];

                    System.out.println(request.getAuthorizationToken());
                    return getUserController().getUserByUsername(username);

                }


                break;
            }

            case POST: {
                if (request.getPathname().contains("/users")) {
                    return getUserController().register(request.getBody());
                } else if (request.getPathname().contains("/sessions")) {
                    return getUserController().loginUser(request.getBody());
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
