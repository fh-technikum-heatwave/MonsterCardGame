package main.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.Factory.ResponseFactory;
import main.model.Trading;
import main.model.card.Card;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;
import main.rest.services.TradingService;

import java.util.List;

public class TradingController extends Controller {
    private final TradingService tradingService;

    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;
    }

    private boolean isMissingBody(String body) {
        return body == null || body.isEmpty();
    }

    private boolean isMissingUser(String userId) {
        return userId == null;
    }

    public Response createTrading(String userId, String body) throws JsonProcessingException {
        if (isMissingBody(body)) {

            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.BAD_REQUEST, "Body missing");
        }

        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }

        Trading trading = getObjectMapper().readValue(body, Trading.class);

        Card c = tradingService.checkIfCardIsLocked(userId, trading.getCardToTrade());


        if (c != null) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Forbidden,
                    "The deal contains a card that is not owned by the user or locked in the deck");

        }

        boolean exists = tradingService.checkIfIdExists(trading.getId());

        if (exists) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Conflict,
                    "A deal with this deal ID already exists.");
        }

        HttpStatus httpStatus = HttpStatus.CREATED;
        String message = "Created";
        if (!tradingService.createTrading(trading)) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Internal Server Error";
        }

        return ResponseFactory.buildResponse(ContentType.TEXT, httpStatus, message);
    }


    public Response getTradingDeals(String userId) throws JsonProcessingException {
        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }


        List<Trading> tradingList = tradingService.getAllTrades();

        if (tradingList == null || tradingList.size() == 0) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.NO_CONTENT, "No Trading deals available");
        }

        String dataJson = getObjectMapper().writeValueAsString(tradingList);

        return ResponseFactory.buildResponse(ContentType.JSON, HttpStatus.OK, dataJson);
    }


    public Response deleteTrading(String userId, String tradingId) {
        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }

        boolean exists = tradingService.checkIfIdExists(tradingId);

        if (!exists) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.NOT_FOUND, "The provided deal ID was not found.");
        }

        Card c = tradingService.checkIfCardIsLocked(userId, tradingId);

        if (c != null) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Forbidden,
                    "The deal contains a card that is not owned by the user");
        }


        boolean worked = tradingService.deleteTrade(tradingId);

        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Trading was deleted";

        if (!worked) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Internal Error";
        }

        return ResponseFactory.buildResponse(ContentType.TEXT, httpStatus, message);

    }

    public Response trade(String userId, String myTradingCardId, String tradingId) throws JsonProcessingException {

        if (isMissingBody(tradingId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.BAD_REQUEST, "Body missing");
        }

        myTradingCardId = getObjectMapper().readValue(myTradingCardId, String.class);

        if (isMissingUser(userId)) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Unauthorized, "Token Missing/Token invalid");
        }


        Card c = tradingService.checkIfCardIsLocked(userId, myTradingCardId);

        if (c != null) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.Forbidden,
                    "The deal contains a card that is not owned by the user or locked in the deck.");
        }

        boolean exists = tradingService.checkIfIdExists(tradingId);

        if (!exists) {
            return ResponseFactory.buildResponse(ContentType.TEXT, HttpStatus.NOT_FOUND,
                    "The provided deal ID was not found.");
        }

        boolean worked = tradingService.trade(userId, tradingId, myTradingCardId);

        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Trading was executed";

        if (!worked) {
            httpStatus = HttpStatus.Forbidden;
            message = "Requirements are not met (Type, MinimumDamage) or you tried to trade with yourself";
        }
        return ResponseFactory.buildResponse(ContentType.TEXT, httpStatus, message);
    }
}
