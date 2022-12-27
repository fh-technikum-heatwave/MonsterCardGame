package main.rest.server;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ServerApp {
    Response handleRequest(Request request) throws IOException;
}
