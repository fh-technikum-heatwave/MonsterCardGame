package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Factory.ResponseFactory;
import main.daos.GameDao;
import main.model.User;
import main.daos.UserDao;
import main.model.UserProfile;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.UserService;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter(AccessLevel.PRIVATE)
public class UserController extends Controller {
    private UserDao userDao;
    private GameDao gameDao;
    private Map<String, String> session = new HashMap<>();

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private boolean isMissingBody(String body) {
        return body == null || body.isEmpty();
    }

    private boolean isMissingUser(String userId) {
        return userId == null;
    }

    public Response register(String body) throws JsonProcessingException {

        if (isMissingBody(body)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.BAD_REQUEST, "Body missing");
        }

        User user = getObjectMapper().readValue(body, User.class);
        boolean worked = userService.createUser(user);

        System.out.println(worked);

        if (worked) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.CREATED, "User Successfully created");
        } else {
            String errorMessage = "This user already exists";
            return ResponseFactory.buildResponse(ContentType.JSON, HttpStatus.Conflict, errorMessage);
        }
    }

    public Response getUserByUsername(String username, String token) throws JsonProcessingException {

        HttpStatus httpStatus = HttpStatus.OK;

        User user = userService.getByUsername(username);

        if (user == null) {
            httpStatus = HttpStatus.NOT_FOUND;

        } else {
            if (token == null || getSession().get(token) == null ||
                    !getSession().get(token).equals(user.getId())) {
                return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
            }
            String userDataJSON = getObjectMapper().writeValueAsString(user);
            return ResponseFactory.buildResponse(ContentType.JSON, httpStatus, userDataJSON);
        }
        return ResponseFactory.buildResponse(ContentType.JSON, httpStatus, null);
    }

    public Response loginUser(String body) throws JsonProcessingException {

        if (isMissingBody(body)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.BAD_REQUEST, "Body missing");
        }

        JsonNode actualObj = getObjectMapper().readTree(body);
        String username = actualObj.get("Username").asText();
        String pw = actualObj.get("Password").asText();

        User user = userService.login(username);

        if (user != null && pw.equals(user.getPassword())) {
            session.put(username + "-mtcgToken", user.getId());
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.OK, username + "-mtcgToken");
        } else {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Invalid username/password provided");
        }
    }

    public Response updateUserProfile(String token, String username, String body) throws JsonProcessingException {
        if (isMissingBody(body)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.BAD_REQUEST, "Body missing");
        }
        if (getSession().get(token) == null || !token.contains(username)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }
        User user = userService.getByUsername(username);
        if (user == null) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.NOT_FOUND, "User does not exist");
        }
        UserProfile userProfile = getObjectMapper().readValue(body, UserProfile.class);
        boolean worked = userService.updateUserProfile(getSession().get(token), userProfile.getName(), userProfile.getBio(), userProfile.getImage());

        if (worked) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.OK, "Userprofile successfully updated");
        }
        return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error");
    }
}
