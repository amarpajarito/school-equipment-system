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
    protected String equipmentID;
    protected String name;
    protected String type;
    protected String condition;
    protected Room location; 
    protected String quantity;

    public Equipment(String id, String name, String type, String condition, Room location, String quantity) {
        this.equipmentID = id;
        this.name = name;
        this.type = type;
        this.condition = condition;
        this.location = location; 
        this.quantity = quantity;
    }

    public Equipment(String[] str) {
        if (str.length == 6) {
            this.equipmentID = str[0];
            this.name = str[1];
            this.type = str[2];
            this.condition = str[3];
            this.location = new Room(str[4]); 
            this.quantity = str[5];
        } else {
            throw new IllegalArgumentException("Array must have exactly 6 elements.");
        }
    }

    @Override
    public void register() {
        String query = "INSERT INTO EQUIPMENT (id, name, type, condition, location, quantity) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            DatabaseConnection connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, equipmentID);
            pstmt.setString(2, name);
            pstmt.setString(3, type);
            pstmt.setString(4, condition);
            pstmt.setString(5, location.getRoom());
            pstmt.setString(6, quantity);
            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}



    

