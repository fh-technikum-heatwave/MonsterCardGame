package main.Factory;

import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;

public class ResponseFactory {

    public static Response buildResponse(ContentType contentType, HttpStatus httpStatus, String message) {

        String msg = "";

        switch (contentType) {
            case TEXT -> msg = buildTextMessage(message);
            case JSON -> {
                if (httpStatus == HttpStatus.CREATED || httpStatus == HttpStatus.OK || httpStatus == HttpStatus.NO_CONTENT) {
                    msg = buildJSONMessage(message);
                } else {
                    msg = buildJSONMessageError(message);
                }
            }
        }

        return new Response(
                httpStatus,
                contentType,
                msg
        );
    }


    public static String buildJSONMessage(String message) {
        return "{ \"data\": " + message + ", \"error\": null }";
    }


    public static String buildJSONMessageError(String message) {
        return "{ \"error\": " + message + ", \"data\": null }";
    }

    public static String buildTextMessage(String message) {
        return message;
    }


}
