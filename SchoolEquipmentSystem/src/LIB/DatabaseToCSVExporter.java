/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 *
 * @author Amar Pajarito
 */
public class DatabaseToCSVExporter {
    private final String DB_URL = "jdbc:sqlite:SchoolEquipmentSystem.db";
    private static final String CSV_FILE_PATH = "C:/Users/Amar Pajarito/Desktop/College Files/2ND_TERM1/CSS123L/school-equipment-system/SchoolEquipmentSystem/src/CSV/equipment_export.csv";

    public void exportToCSV() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM EQUIPMENT");
             FileWriter csvWriter = new FileWriter(CSV_FILE_PATH)) {

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            // Write column headers
            for (int i = 1; i <= columnCount; i++) {
                csvWriter.append(rsMetaData.getColumnName(i));
                if (i < columnCount) csvWriter.append(",");
            }
            csvWriter.append("\n");

            // Write data rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    csvWriter.append(rs.getString(i));
                    if (i < columnCount) csvWriter.append(",");
                }
                csvWriter.append("\n");
            }

            System.out.println("Data successfully exported to " + CSV_FILE_PATH);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}