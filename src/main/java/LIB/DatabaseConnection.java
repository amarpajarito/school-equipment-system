/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Amar Pajarito
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection conn; 
    private final String dburl = "jdbc:sqlite:SchoolEquipmentSystem.db";
    
    private DatabaseConnection(){
        try{
            this.conn = DriverManager.getConnection(dburl);
        } catch (SQLException e){
            System.out.println("Database Connection and Creation Failed: " + e.getMessage());
        }
    }
    
    public Connection getConnection(){
        return conn;
    }
    
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()){
            instance = new DatabaseConnection();
        }
        return instance;
    }
}

