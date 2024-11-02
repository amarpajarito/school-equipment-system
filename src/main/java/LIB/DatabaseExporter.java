/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
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
public class DatabaseExporter {
    private final String DB_URL = "jdbc:sqlite:SchoolEquipmentSystem.db";
    private static final String CSV_FILE_PATH = "C:/Users/Amar Pajarito/Desktop/College Files/2ND_TERM1/CSS123L/school-equipment-system/SchoolEquipmentSystem/src/CSV/equipment_export.csv";

    public void exportAllEquipmentsToCSV() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM EQUIPMENT");
             FileWriter csvWriter = new FileWriter(CSV_FILE_PATH)) {

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                csvWriter.append(rsMetaData.getColumnName(i));
                if (i < columnCount) csvWriter.append(",");
            }
            csvWriter.append("\n");

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    csvWriter.append(rs.getString(i));
                    if (i < columnCount) csvWriter.append(",");
                }
                csvWriter.append("\n");
            }

            System.out.println("All equipments successfully exported to " + CSV_FILE_PATH);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void exportReplaceLostEquipmentstToCSV() {
        String query = "SELECT * FROM EQUIPMENT WHERE condition IN ('For Replacement', 'Lost')";
        String csvFilePath = "C:/Users/Amar Pajarito/Desktop/College Files/2ND_TERM1/CSS123L/school-equipment-system/SchoolEquipmentSystem/src/CSV/damaged_equipment_export.csv"; // Change the path if needed

        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            FileWriter csvWriter = new FileWriter(csvFilePath)) {

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                csvWriter.append(rsMetaData.getColumnName(i));
                if (i < columnCount) csvWriter.append(",");
            }
            csvWriter.append("\n");

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    csvWriter.append(rs.getString(i));
                    if (i < columnCount) csvWriter.append(",");
                }
                csvWriter.append("\n");
            }

            System.out.println("For Replacement and Lost equipment data successfully exported to " + csvFilePath);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void exportAllEquipmentsToExcel() {
        String excelFilePath = "C:/Users/Amar Pajarito/Desktop/College Files/2ND_TERM1/CSS123L/school-equipment-system/SchoolEquipmentSystem/src/CSV/equipment_export.xlsx";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM EQUIPMENT");
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("All Equipment");
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(rsMetaData.getColumnName(i));
            }

            int rowCount = 1;
            while (rs.next()) {
                Row dataRow = sheet.createRow(rowCount++);
                for (int i = 1; i <= columnCount; i++) {
                    Cell cell = dataRow.createCell(i - 1);
                    cell.setCellValue(rs.getString(i));
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }

            System.out.println("All equipments successfully exported to " + excelFilePath);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void exportReplaceLostEquipmentsToExcel() {
        String query = "SELECT * FROM EQUIPMENT WHERE condition IN ('For Replacement', 'Lost')";
        String excelFilePath = "C:/Users/Amar Pajarito/Desktop/College Files/2ND_TERM1/CSS123L/school-equipment-system/SchoolEquipmentSystem/src/CSV/damaged_equipment_export.xlsx"; // Change the path if needed

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Damaged Equipment");
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(rsMetaData.getColumnName(i));
            }

            int rowCount = 1;
            while (rs.next()) {
                Row dataRow = sheet.createRow(rowCount++);
                for (int i = 1; i <= columnCount; i++) {
                    Cell cell = dataRow.createCell(i - 1);
                    cell.setCellValue(rs.getString(i));
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }

            System.out.println("For Replacement and Lost equipment data successfully exported to " + excelFilePath);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}