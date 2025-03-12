/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package APP;
import LIB.DatabaseConnection;
import java.awt.Color;
import LIB.Room;
import LIB.Equipment;
import LIB.Facade;
import LIB.IFacade;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Amar Pajarito
 */
public class ManageEquipments extends javax.swing.JFrame {

    private int selectedEquipmentID = 0;

    public ManageEquipments() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("SEAM Manage Equipments");
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGES/ssulogo.png"));
        setIconImage(icon.getImage());
        update();
    }

    public void update() {
        selectedEquipmentID = 0;
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.getDataVector().removeAllElements();

        nameText.setText("");
        typeBox.setSelectedIndex(0);
        locationBox.setSelectedIndex(0);
        quantityText.setText("");

        try {
            DatabaseConnection connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EQUIPMENT;");

            while (rs.next()) {
                int equipmentID = rs.getInt("equipmentID");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String condition = rs.getString("condition");
                String location = rs.getString("location");
                int quantity = rs.getInt("quantity");

                String[] rowData = {String.valueOf(equipmentID), name, type, condition, location, String.valueOf(quantity)};
                tableModel.addRow(rowData);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void register() {
        if (selectedEquipmentID != 0) {
            JOptionPane.showMessageDialog(this, "ERROR: Equipment Exists In The Database!");
        } else {
            try {
                String name = nameText.getText();
                String type = String.valueOf(typeBox.getSelectedItem());
                String condition = String.valueOf(conditionBox.getSelectedItem());
                String locationName = String.valueOf(locationBox.getSelectedItem());
                int quantity = Integer.parseInt(quantityText.getText());

                if ("".equals(name) || "".equals(type) || "".equals(condition) || "".equals(locationName)) {
                    JOptionPane.showMessageDialog(this, "ERROR: Insufficient Information!");
                } else {
                    Room location = new Room(locationName);
                    IFacade create = new Facade(new Equipment(name, type, condition, location, quantity));
                    create.registerEquipment();
                    update();
                }
            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ERROR: Please Check Your Entries and Try Again!");
            }
        }
    }

    public void updateEquipment() {
        if (selectedEquipmentID == 0) {
            JOptionPane.showMessageDialog(this, "ERROR: No Equipment Selected!");
        } else {
            try {
                String name = nameText.getText();
                String type = String.valueOf(typeBox.getSelectedItem());
                String condition = String.valueOf(conditionBox.getSelectedItem());
                String location = String.valueOf(locationBox.getSelectedItem());
                int quantity = Integer.parseInt(quantityText.getText());

                if ("".equals(name) || "".equals(type) || "".equals(condition) || "".equals(location)) {
                    JOptionPane.showMessageDialog(this, "ERROR: Insufficient Information!");
                    return;
                }

                String query = "UPDATE EQUIPMENT SET name=?, type=?, condition=?, location=?, quantity=? WHERE equipmentID=?";
                DatabaseConnection connect = DatabaseConnection.getInstance();
                Connection conn = connect.getConnection();

                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, type);
                    pstmt.setString(3, condition);
                    pstmt.setString(4, location);
                    pstmt.setInt(5, quantity);
                    pstmt.setInt(6, selectedEquipmentID);

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Equipment details updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "ERROR: Update failed. Equipment ID not found.");
                    }
                }

                update();

            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ERROR: Please Check Your Entries and Try Again!");
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
                JOptionPane.showMessageDialog(this, "ERROR: Failed to update equipment details. Please try again.");
            }
        }
    }

    public void delete() {
        if (selectedEquipmentID == 0) {
            JOptionPane.showMessageDialog(this, "ERROR: No Equipment Selected!");
        } else {
            int input = JOptionPane.showConfirmDialog(this, "Confirm Equipment Deletion.");
            if (input == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM EQUIPMENT WHERE equipmentID = " + selectedEquipmentID + ";";
                try {
                    DatabaseConnection connect = DatabaseConnection.getInstance();
                    Connection conn = connect.getConnection();
                    Statement stmt = conn.createStatement();
                    int rowsDeleted = stmt.executeUpdate(query);
                
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(this, "Equipment record deleted successfully.");
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        int selectedRow = jTable1.getSelectedRow();
                        if (selectedRow != -1) {
                        model.removeRow(selectedRow);
                        }
                        selectedEquipmentID = 0;
                    
                        String checkQuery = "SELECT COUNT(*) AS count FROM EQUIPMENT;";
                        ResultSet rs = stmt.executeQuery(checkQuery);
                        if (rs.next() && rs.getInt("count") == 0) {
                            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='EQUIPMENT';");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "ERROR: Record not found.");
                    }
                    conn.close();
                    update();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "ERROR: " + e.getMessage());
                }
            }
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        typeBox = new javax.swing.JComboBox<>();
        nameText = new javax.swing.JTextField();
        locationBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        quantityText = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        conditionBox = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(125, 10, 10));
        jPanel3.setPreferredSize(new java.awt.Dimension(875, 101));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(236, 220, 191));
        jLabel7.setText("MANAGE EQUIPMENTS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(158, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(144, 144, 144))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel4.setBackground(new java.awt.Color(236, 220, 191));
        jPanel4.setPreferredSize(new java.awt.Dimension(876, 207));

        typeBox.setBackground(new java.awt.Color(248, 242, 222));
        typeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Air Conditioner", "Electric Fan", "Personal Computer (PC)", "Television (TV)", "White Board" }));

        nameText.setBackground(new java.awt.Color(248, 242, 222));

        locationBox.setBackground(new java.awt.Color(248, 242, 222));
        locationBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MKT310 ", "MKT410 ", "MKT501" }));

        jLabel2.setText("Name:");

        jLabel3.setText("Type:");

        jLabel4.setText("Condition:");

        jLabel5.setText("Location:");

        jLabel6.setText("Quantity:");

        quantityText.setBackground(new java.awt.Color(248, 242, 222));

        jButton1.setBackground(new java.awt.Color(248, 242, 222));
        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(125, 10, 10));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Register");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(184, 33, 50));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Remove");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 48, 146));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Update");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        conditionBox.setBackground(new java.awt.Color(248, 242, 222));
        conditionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New", "Good", "Needs Repair", "For Replacement", "Lost" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(conditionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addGap(12, 12, 12)
                                .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(locationBox, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(locationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(conditionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(128, 0, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(876, 230));

        jTable1.setBackground(new java.awt.Color(236, 220, 191));
        jTable1.getTableHeader().setBackground(new Color(248,242,222));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Type", "Condition", "Location", "Quantity"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel5, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        register();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        updateEquipment();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) { 
            selectedEquipmentID = Integer.parseInt(String.valueOf(jTable1.getValueAt(selectedRow, 0)));
            nameText.setText(String.valueOf(jTable1.getValueAt(selectedRow, 1)));
            typeBox.setSelectedItem(String.valueOf(jTable1.getValueAt(selectedRow, 2)));
            conditionBox.setSelectedItem(String.valueOf(jTable1.getValueAt(selectedRow, 3)));
            locationBox.setSelectedItem(String.valueOf(jTable1.getValueAt(selectedRow, 4)));
            quantityText.setText(String.valueOf(jTable1.getValueAt(selectedRow, 5)));
        } else {
        selectedEquipmentID = 0; 
        nameText.setText("");
        typeBox.setSelectedIndex(0);
        conditionBox.setSelectedIndex(0);
        locationBox.setSelectedIndex(0);
        quantityText.setText("");
        }   
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    private void formWindowClosed(java.awt.event.WindowEvent evt) {                                  
        // TODO add your handling code here:
        new Menu().setVisible(true);
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> conditionBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> locationBox;
    private javax.swing.JTextField nameText;
    private javax.swing.JTextField quantityText;
    private javax.swing.JComboBox<String> typeBox;
    // End of variables declaration//GEN-END:variables
}
