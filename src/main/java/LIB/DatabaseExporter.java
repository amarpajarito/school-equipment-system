/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

import java.io.File;
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
import javax.swing.JFileChooser;
/**
 *
 * @author Amar Pajarito
 */
public class DatabaseExporter {

    public void exportAllEquipmentsToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("all_equipment_report.csv"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM EQUIPMENT");
                 FileWriter csvWriter = new FileWriter(fileToSave)) {

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

                System.out.println("All equipments successfully exported to " + fileToSave.getAbsolutePath());

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void exportAllEquipmentsToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("all_equipment_report.xlsx"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
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

                try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                    workbook.write(outputStream);
                }

                System.out.println("All equipments successfully exported to " + fileToSave.getAbsolutePath());

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void exportReplaceLostEquipmentsToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("replace_lost_equipment_report.csv"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String query = "SELECT * FROM EQUIPMENT WHERE condition IN ('For Replacement', 'Lost')";

            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query);
                 FileWriter csvWriter = new FileWriter(fileToSave)) {

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

                System.out.println("For Replacement and Lost equipment data successfully exported to " + fileToSave.getAbsolutePath());

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void exportReplaceLostEquipmentsToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("replace_lost_equipment_report.xlsx"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String query = "SELECT * FROM EQUIPMENT WHERE condition IN ('For Replacement', 'Lost')";

            try (Connection conn = DatabaseConnection.getInstance().getConnection();
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

                try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                    workbook.write(outputStream);
                }

                System.out.println("For Replacement and Lost equipment data successfully exported to " + fileToSave.getAbsolutePath());

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

