package main;

import main.rest.App;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Request;
import main.rest.server.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        App app = new App();
        System.out.println("Line 17");

        int port = 2540;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket;
        PrintWriter printWriter;
        BufferedReader inputStream;

        while (true) {
            clientSocket = serverSocket.accept();
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            var request = new Request(inputStream);
            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            Response response;
            if (request.getPathname() == null) {

                response =
                        new Response(
                                HttpStatus.BAD_REQUEST,
                                ContentType.TEXT,
                                ""
                        );
            } else {
                response = app.handleRequest(request);
                System.out.println("Main 43");
            }

            printWriter.write("OK");

        }

    }
}
