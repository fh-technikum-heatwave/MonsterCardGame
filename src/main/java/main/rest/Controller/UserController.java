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

        User user = new User(actualObj.get("Username").asText(),actualObj.get("Password").asText());
        try {
            userDao.create(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + ", \"error\": null }"
        );
    }


}
