package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class Handler {

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


            if ("POST".equals(exchange.getRequestMethod())) {
                // Read the request body
                InputStream requestBody = exchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));

                StringBuilder requestBodyContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBodyContent.append(line);
                }
                // Process the request body
                String requestData = requestBodyContent.toString();
                // Lakukan apa yang perlu dilakukan dengan data yang diterima dari permintaan POST
                // Send the response
                OutputStream outputStream = exchange.getResponseBody();
                String responseToBeSentBack = "Data received successfully";
                exchange.sendResponseHeaders(200, responseToBeSentBack.length());

                outputStream.write(responseToBeSentBack.getBytes());
                outputStream.flush();
                outputStream.close();
            }


            if ("PUT".equals(exchange.getRequestMethod())) {
                InputStream inputStream = exchange.getRequestBody();
                // Read the request body from the input stream

                // Process the data received from the client
                String response = "Data received and processed successfully";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                String response = "Invalid request method";
                exchange.sendResponseHeaders(405, response.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }


            if ("DELETE".equals(exchange.getRequestMethod())) {
                // Lakukan operasi delete yang diinginkan
                // ...
                String responseToBeSentBack = "Data berhasil dihapus";
                exchange.sendResponseHeaders(200, responseToBeSentBack.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(responseToBeSentBack.getBytes());
                outputStream.flush();
                outputStream.close();
            } else {
                // Jika metode permintaan bukan DELETE, kirim respon metode tidak diizinkan
                String responseToBeSentBack = "Metode tidak diizinkan";
                exchange.sendResponseHeaders(405, responseToBeSentBack.length());
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(responseToBeSentBack.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
    }

}
