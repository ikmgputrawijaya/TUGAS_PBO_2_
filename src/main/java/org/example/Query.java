package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query {
    public static Connection connect() {
        // Ubah path dan nama file database sesuai dengan lokasi dan nama file SQLite Anda
        String url = "jdbc:sqlite:/path/to/database.db";
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




}
