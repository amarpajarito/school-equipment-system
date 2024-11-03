/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Amar Pajarito
 */

public class Equipment implements Backend {
    protected String name;
    protected String type;
    protected String condition;
    protected Room location; 
    protected int quantity;

    public Equipment(String name, String type, String condition, Room location, int quantity) {
        this.name = name;
        this.type = type;
        this.condition = condition;
        this.location = location; 
        this.quantity = quantity;
    }

    public Equipment(String[] str) {
        if (str.length == 5) {
            this.name = str[0];
            this.type = str[1];
            this.condition = str[2];
            this.location = new Room(str[3]); 
            this.quantity = Integer.parseInt(str[4]);
        } else {
            throw new IllegalArgumentException("Array must have exactly 5 elements.");
        }
    }

    @Override
    public void register() {
        String query = "INSERT INTO EQUIPMENT (name, type, condition, location, quantity) VALUES (?, ?, ?, ?, ?)";

        try {
            DatabaseConnection connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setString(3, condition);
            pstmt.setString(4, location.getRoom());
            pstmt.setInt(5, quantity);
            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}




    

