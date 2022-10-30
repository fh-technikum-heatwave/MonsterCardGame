package main;

import main.rest.App;
import main.rest.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        App app = new App();
        Server server = new Server(app, 5543);
        server.start();

    }
}

