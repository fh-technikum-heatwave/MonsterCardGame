package main.rest.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.User;
import main.daos.UserDao;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;

import java.sql.SQLException;
import java.util.UUID;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class UserController extends Controller {
    private UserDao userDao;

    public UserController(UserDao userDao) {
        setUserDao(userDao);
    }

    public Response register(String body) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(body);
        UUID uuid = UUID.randomUUID();

        //id= -1 => the user currently does not have an id
        User user = new User(uuid.toString(), actualObj.get("Username").asText(), actualObj.get("Password").asText());

        try {
            userDao.create(user);
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"data\": null" + ", \"error\": null }"
            );
        } catch (SQLException throwables) {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "{ \"error\": \"This username already exists\", \"data\": null }"
            );
        }


    }

    public Response getUserByUsername(String username) throws JsonProcessingException {
        try {
            User user = userDao.read(username);
            String userDataJSON = getObjectMapper().writeValueAsString(user);

            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ \"data\": " + userDataJSON + ", \"error\": null }"
            );


        } catch (SQLException e) {

            System.out.println(e);

            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "{ \"error\": \"User not found\", \"data\": null }"
            );
        }

    }

    public Response loginUser(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(body);

        String username = actualObj.get("Username").asText();
        String pw = actualObj.get("Password").asText();

        try {
            User user = userDao.read(username);

            if (pw.equals(user.getPassword())) {

                return new Response(
                        HttpStatus.OK,
                        ContentType.TEXT,
                        username + "-mtcgToken"
                );
            }else {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.TEXT,
                        "Invalid username/password provided"
                );
            }

        } catch (SQLException e) {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "{ \"error\": \"User not found\", \"data\": null }"
            );
        }

    }

}
