package org.example;

import java.sql.*;
import java.util.ArrayList;

public class Query {
    public static Connection connect() {
//         Ubah path dan nama file database sesuai dengan lokasi dan nama file SQLite Anda
        String url = "jdbc:sqlite:C:\\Dev\\Tutorial java\\Belajar Java\\TUGAS_PBO_2_\\src\\DB_Api.db";
        String rootPath = System.getProperty("user.dir");
        url = "jdbc:sqlite:" + rootPath + "/DB_Api.db";
        Connection connection = null;

        try {
            // Mendaftarkan driver JDBC untuk SQLite
            Class.forName("org.sqlite.JDBC");

            // Membuat koneksi ke database
            connection = DriverManager.getConnection(url);
            System.out.println("Berhasil terhubung ke database SQLite");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite tidak ditemukan");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Gagal terhubung ke database SQLite");
            e.printStackTrace();
        }
        return connection;
    }

    public void inputAddresses(int users, String type, String line1, String line2, String city, String province, String postcode){
        String sql = "INSERT INTO addresses(users, type, line1, line2, city, province, postcode) VALUES(?,?,?,?,?,?,?)";
        try {
            Connection connection = this.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, users);
            pstmt.setString(2, type);
            pstmt.setString(3, line1);
            pstmt.setString(4, line2);
            pstmt.setString(5, city);
            pstmt.setString(6, province);
            pstmt.setString(7, postcode);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void inputOrderDetails(int order_id, int product, int quantity, int price){
        String sql = "INSERT INTO order_details(order_id, product, quantity, price) VALUES(?,?,?,?)";
        try {
            Connection connection = this.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, order_id);
            pstmt.setInt(2, product);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, price);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void inputOrders(int id, int buyer, int note, int total, int discount, String is_paid){
        String sql = "INSERT INTO orders(id, buyer, note, total, discount, is_paid) VALUES(?,?,?,?,?,?)";
        try {
            Connection connection = this.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, buyer);
            pstmt.setInt(3, note);
            pstmt.setInt(4, total);
            pstmt.setInt(5, discount);
            pstmt.setString(6, is_paid);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void inputProducts(int id, int seller, String title, String description, int price, int stock){
        String sql = "INSERT INTO products(id, seller, title, description, price, stock) VALUES(?,?,?,?,?,?)";
        try {
            Connection connection = this.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, seller);
            pstmt.setString(3, title);
            pstmt.setString(4, description);
            pstmt.setInt(5, price);
            pstmt.setInt(6, stock);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void inputReviews(int order_id, int star, String description){
        String sql = "INSERT INTO reviews(order_id, star, description) VALUES(?,?,?)";
        try {
            Connection connection = this.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, order_id);
            pstmt.setInt(2, star);
            pstmt.setString(3, description);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void inputUsers(int id, String first_name, String last_name, String email, String phone_number, String type){
        String sql = "INSERT INTO users(id, first_name, last_name, email, phone_number, type) VALUES(?,?,?,?,?,?)";
        try {
            Connection connection = this.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, email);
            pstmt.setString(5, phone_number);
            pstmt.setString(6, type);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public String appendString (ArrayList<String> json){
        StringBuffer group = new StringBuffer();
        for (String s : json) {
            group.append(s);
            group.append("\n\n");
        }
        String result = group.toString();
        return result;
    }

    public ArrayList<String> selectAll(String table) {
        ArrayList<String> result = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + table + ";";
            ResultSet resultSet = statement.executeQuery(sql);

            if (table.equals("users")) {
                while (resultSet.next()) {
                    result.add(Struktur.users(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("type")));
                }
            }else if (table.equals("addresses")){
                while (resultSet.next()){
                    result.add(Struktur.addresses(resultSet.getInt("users"), resultSet.getString("type"), resultSet.getString("line1"), resultSet.getString("line2"), resultSet.getString("city"), resultSet.getString("province"), resultSet.getString("postcode")));
                }
            } else if (table.equals("order_details")){
                while (resultSet.next()){
                    result.add(Struktur.order_details(resultSet.getInt("order_id"), resultSet.getInt("product"), resultSet.getInt("quantity"), resultSet.getInt("price")));
                }
            } else if (table.equals("orders")){
                while (resultSet.next()){
                    result.add(Struktur.orders(resultSet.getInt("id"), resultSet.getInt("buyer"), resultSet.getInt("note"), resultSet.getInt("total"), resultSet.getInt("discount"), resultSet.getString("is_paid")));
                }
            } else if (table.equals("products")) {
                while (resultSet.next()){
                    result.add(Struktur.products(resultSet.getInt("id"), resultSet.getInt("seller"), resultSet.getString("title"), resultSet.getString("descripton"), resultSet.getInt("price"), resultSet.getInt("stock")));
                }
            } else if (table.equals("reviews")) {
                while (resultSet.next()) {
                    result.add(Struktur.reviews(resultSet.getInt("order_id"), resultSet.getInt("star"), resultSet.getString("description")));
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAddress(int userId) {
        String sql = "DELETE FROM addresses WHERE users = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Addresses deleted successfully");
            } else {
                System.out.println("No addresses found for the user");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteOrderDetails(int orderId) {
        String sql = "DELETE FROM order_details WHERE order_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order details deleted successfully");
            } else {
                System.out.println("No order details found for the order");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order deleted successfully");
            } else {
                System.out.println("Order not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully");
            } else {
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteReview(int orderId) {
        String sql = "DELETE FROM reviews WHERE order_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Review deleted successfully");
            } else {
                System.out.println("Review not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
