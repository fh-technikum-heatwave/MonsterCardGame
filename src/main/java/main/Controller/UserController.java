package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.daos.GameDao;
import main.model.Statistik;
import main.model.User;
import main.daos.UserDao;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserController extends Controller {
    private UserDao userDao;
    private GameDao gameDao;
    private Map<String, String> session = new HashMap<>();

    public UserController(UserDao userDao, GameDao gameDao) {
        setUserDao(userDao);
        setGameDao(gameDao);
    }

    public Response register(String body) throws JsonProcessingException {

        User user = getObjectMapper().readValue(body, User.class);
        try {
            userDao.create(user);
            gameDao.create(new Statistik(user.getUsername(), 0, 0, 0, user.getId(), UUID.randomUUID().toString()));

            return new Response(
                    HttpStatus.CREATED,
                    ContentType.TEXT,
                    "User Successfully created"
            );
        } catch (SQLException throwables) {

            System.out.println(throwables.getMessage());

            String s = throwables.getSQLState();
            String errorMessage = "";
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;


            if (s.equals("23505")) {
                errorMessage = "This user already exists";
                httpStatus = HttpStatus.Conflict;
            }

            return new Response(
                    httpStatus,
                    ContentType.JSON,
                    "{ \"error\": " + errorMessage + ", \"data\": null }"
            );

        }
    }

    public Response getUserByUsername(String username, String token) throws JsonProcessingException {

        HttpStatus httpStatus = HttpStatus.OK;
        try {
            User user = userDao.read(username);
            System.out.println(user);

            if (user == null) {
                httpStatus = HttpStatus.NOT_FOUND;

            } else {
                if (token == null || getSession().get(token) == null ||
                        !getSession().get(token).equals(user.getId())) {
                    return new Response(
                            HttpStatus.Unauthorized,
                            ContentType.TEXT,
                            "Token Missing/Token invalid"
                    );
                }
                String userDataJSON = getObjectMapper().writeValueAsString(user);
                return new Response(
                        httpStatus,
                        ContentType.JSON,
                        "{ \"data\": " + userDataJSON + ", \"error\": null }"
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println(e.getSQLState());

        }
        return new Response(
                httpStatus,
                ContentType.JSON,
                "{ \"data\": " + null + ", \"error\": null }"
        );
    }

    public Response loginUser(String body) throws JsonProcessingException {
        JsonNode actualObj = getObjectMapper().readTree(body);
        String username = actualObj.get("Username").asText();
        String pw = actualObj.get("Password").asText();

        try {
            User user = userDao.read(username);

            if (user != null && pw.equals(user.getPassword())) {

                session.put(username + "-mtcgToken", user.getId());

                return new Response(
                        HttpStatus.OK,
                        ContentType.TEXT,
                        username + "-mtcgToken"
                );
            } else {
                return new Response(
                        HttpStatus.Unauthorized,
                        ContentType.TEXT,
                        "Invalid username/password provided"
                );
            }

        } catch (SQLException e) {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "{ \"data\": " + null + ", \"error\": null }"
            );
        }

    }
}
