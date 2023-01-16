package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.Factory.ResponseFactory;
import main.model.FriendRequest;
import main.model.FriendsList;
import main.model.User;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.FriendService;

import java.util.List;

public class FriendController extends Controller {

    private FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    private boolean isMissingBody(String body) {
        return body == null || body.isEmpty();
    }

    private boolean isMissingUser(String userId) {
        return userId == null;
    }


    public Response sendFriendReuest(String userId, String friendname) {
        if (isMissingUser(userId)) {

            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");

        }

        User friend = friendService.checkIfFriendExists(friendname);
        if (friend == null || friend.getId().equals(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Forbidden,
                    "User does not exist or you tried to send a friend request to you");
        }

        if (friendService.checkIfFriendRquestExists(userId, friendname)) {

            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Conflict,
                    "A friend Request was already sent");

        }


        boolean worked = friendService.createFriendRequest(friendname, userId);

        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Friend Request is sent";

        if (!worked) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Server Error";
        }

        return ResponseFactory.buildResponse(ContentType.TEXT, httpStatus,
                message);

    }


    public Response acceptFriendRequest(String userId, String friendRequestId) {

        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized,
                    "Token Missing/Token invalid");
        }

        FriendRequest friendRequest = friendService.getFriendRequestById(friendRequestId);

        if (friendRequest == null) {

            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.NOT_FOUND,
                    "The provided Id was not found");

        }

        if (!friendRequest.getUserid().equals(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Forbidden,
                    "You are not allowed to accept the request");
        }

        friendService.acceptFriendRequest(userId, friendRequestId);

        return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.OK,
                "Friend request accepted");

    }

    public Response getMyFriendRequest(String userId) throws JsonProcessingException {

        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized,
                    "Token Missing/Token invalid");
        }


        List<FriendRequest> friendRequests = friendService.getMyFriendRequest(userId);

        System.out.println(friendRequests);

        String dataJson = getObjectMapper().writeValueAsString(friendRequests);


        HttpStatus httpStatus = HttpStatus.OK;


        if (friendRequests.size() == 0) {
            httpStatus = HttpStatus.NO_CONTENT;
        }

        return ResponseFactory.buildResponse(ContentType.JSON, httpStatus,
                dataJson);
    }


    public Response getMyFriends(String userId) throws JsonProcessingException {

        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized,
                    "Token Missing/Token invalid");

        }


        List<FriendsList> friendsLists = friendService.getMyFriendList(userId);
        String dataJson = getObjectMapper().writeValueAsString(friendsLists);


        HttpStatus httpStatus = HttpStatus.OK;


        if (friendsLists.size() == 0) {
            httpStatus = HttpStatus.NO_CONTENT;

        }
        return ResponseFactory.buildResponse(ContentType.JSON, httpStatus,
                dataJson);
    }


}
