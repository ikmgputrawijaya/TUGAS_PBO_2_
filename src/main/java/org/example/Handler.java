package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.sql.*;

import static org.example.Query.connect;

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


    // Mendapatkan informasi user berdasarkan ID
    private static void getUser(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Periksa apakah hasil query kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("HTTP error response: User not found");
                return;
            }

            // Ambil informasi user dan alamatnya
            while (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                System.out.println("id: " + id);
                System.out.println("name: " + name);
                System.out.println("address: " + address);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mendapatkan daftar produk milik user berdasarkan ID user
    private static void getUserProducts(int id) {
        String sql = "SELECT * FROM products WHERE user_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Periksa apakah hasil query kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("User has no products.");
                return;
            }

            // Ambil daftar produk milik user
            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                System.out.println("Product ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mendapatkan daftar order milik user berdasarkan ID user
    private static void getUserOrders(int id) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Periksa apakah hasil query kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("User has no orders.");
                return;
            }

            // Ambil daftar order milik user
            while (rs.next()) {
                int orderId = rs.getInt("id");
                String orderDetails = rs.getString("details");
                System.out.println("Order ID: " + id);
                System.out.println("Order Details: " + orderDetails);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mendapatkan daftar review yang dibuat oleh user berdasarkan ID user
    private static void getUserReviews(int id, int order_id) {
        String sql = "SELECT * FROM reviews WHERE user_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Periksa apakah hasil query kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("Order has no reviews.");
                return;
            }

            // Ambil daftar review yang dibuat oleh user
            while (rs.next()) {
                int reviewId = rs.getInt("id");
                String reviewText = rs.getString("text");
                System.out.println("Order ID: " + order_id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mendapatkan informasi order berdasarkan ID order
    private static void getOrder(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Periksa apakah hasil query kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("HTTP error response: Order not found");
                return;
            }

            // Ambil informasi order, buyer, order detail, review, dan produk
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int buyerId = rs.getInt("buyer_id");
                int productId = rs.getInt("product_id");
                String orderDetails = rs.getString("details");

                // Dapatkan informasi buyer
                String buyerName = getUserNameById(buyerId);

                // Dapatkan informasi produk
                String productName = getProductNameById(productId);
                double productPrice = getProductPriceById(productId);

                System.out.println("Order ID: " + orderId);
                System.out.println("Buyer Name: " + buyerName);
                System.out.println("Order Details: " + orderDetails);
                System.out.println("Product Name: " + productName);
                System.out.println("Product Price: " + productPrice);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mendapatkan nama user berdasarkan ID user
    private static String getUserNameById(int id) {
        String sql = "SELECT name FROM users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    // Mendapatkan nama produk berdasarkan ID produk
    private static String getProductNameById(int id) {
        String sql = "SELECT name FROM products WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    // Mendapatkan harga produk berdasarkan ID produk
    private static double getProductPriceById(int id) {
        String sql = "SELECT price FROM products WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0.0;
    }

    // Mendapatkan daftar semua produk
    private static void getAllProducts() {
        String sql = "SELECT * FROM products";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = ((Statement) stmt).executeQuery(sql)) {

            // Periksa apakah hasil query kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("No products found.");
                return;
            }

            // Ambil daftar semua produk
            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                int sellerId = rs.getInt("seller");
                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productName);
                System.out.println("Seller ID: " + sellerId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Mendapatkan informasi produk berdasarkan ID produk
    private static void getProduct(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            // Periksa apakah hasil query kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("HTTP error response: Product not found");
                return;
            }

            // Ambil informasi produk dan seller
            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                int sellerId = rs.getInt("seller_id");
                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productName);
                System.out.println("Seller ID: " + sellerId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Filter dengan query params
    private static void filterProducts(String id, String seller_id, int val) {
        String sql = "SELECT * FROM products WHERE " + id + " " + seller_id  + " ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, val);
            ResultSet rs = pstmt.executeQuery();

            // Periksa apakah hasil query kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("No products found.");
                return;
            }

            // Ambil daftar produk yang sesuai dengan filter
            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("name");
                int sellerId = rs.getInt("seller_id");
                System.out.println("Product ID: " + productId);
                System.out.println("Product Name: " + productName);
                System.out.println("Seller ID: " + sellerId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Contoh penggunaan API GET
        getUser(1);
        getUserProducts(1);
        getUserOrders(1);
        getUserReviews(1, 1);
        getOrder(1);
        getAllProducts();
        getProduct(1);
        filterProducts("stock", ">=", 10);
    }
}
