package main.rest.server;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ServerApp {
    Response handleRequest(Request request) throws JsonProcessingException;
}
