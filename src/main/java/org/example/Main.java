package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", 8082), 0);

        httpServer.createContext("/ecommerce", new EcommerceHandler());
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.start();
    }

    public static class EcommerceHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange exchange) throws IOException{
            // POST -> create
            // Get -> read
            // PUT -> update
            // DELETE -> delete
            // CRUD

            if("GET".equals(exchange.getRequestMethod())) {
                // send something interesting to the user
                OutputStream outputStream = exchange.getResponseBody();
                String responseToBeSentBack = "Hello from the other side";
                exchange.sendResponseHeaders(200, responseToBeSentBack.length());

                outputStream.write(responseToBeSentBack.getBytes());
                outputStream.flush();
                outputStream.close();


            }
        }
    }
}