/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package APP;
import LIB.DatabaseConnection;
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
        setTitle("Manage Equipments");
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGES/bsulogo4.png"));
        setIconImage(icon.getImage());
        update();
    }

    public void update() {
        selectedEquipmentID = 0;
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.getDataVector().removeAllElements();

        idText.setText("");
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
                int id = rs.getInt("equipmentID");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String condition = rs.getString("condition");
                String location = rs.getString("location");
                int quantity = rs.getInt("quantity");

                String[] rowData = {String.valueOf(id), name, type, condition, location, String.valueOf(quantity)};
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
                int id = Integer.parseInt(idText.getText());
                String name = nameText.getText();
                String type = String.valueOf(typeBox.getSelectedItem());
                String condition = String.valueOf(conditionBox.getSelectedItem());
                String locationName = String.valueOf(locationBox.getSelectedItem());
                int quantity = Integer.parseInt(quantityText.getText());

                if ("".equals(name) || "".equals(type) || "".equals(condition) || "".equals(locationName)) {
                    JOptionPane.showMessageDialog(this, "ERROR: Insufficient Information!");
                } else {
                    Room location = new Room(locationName);
                    IFacade create = new Facade(new Equipment(id, name, type, condition, location, quantity));
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        typeBox = new javax.swing.JComboBox<>();
        idText = new javax.swing.JTextField();
        nameText = new javax.swing.JTextField();
        locationBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
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
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        conditionBox = new javax.swing.JComboBox<>();

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

        jPanel1.setBackground(new java.awt.Color(128, 0, 0));

        jTable1.setBackground(new java.awt.Color(248, 246, 240));
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

        jSeparator2.setForeground(new java.awt.Color(248, 246, 240));

        typeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Air Conditioner", "Electric Fan", "Personal Computer (PC)", "Television (TV)", "White Board" }));

        locationBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MKT310 ", "MKT410 ", "MKT501" }));

        jLabel1.setForeground(new java.awt.Color(248, 246, 240));
        jLabel1.setText("ID:");

        jLabel2.setForeground(new java.awt.Color(248, 246, 240));
        jLabel2.setText("Name:");

        jLabel3.setForeground(new java.awt.Color(248, 246, 240));
        jLabel3.setText("Type:");

        jLabel4.setForeground(new java.awt.Color(248, 246, 240));
        jLabel4.setText("Condition:");

        jLabel5.setForeground(new java.awt.Color(248, 246, 240));
        jLabel5.setText("Location:");

        jLabel6.setForeground(new java.awt.Color(248, 246, 240));
        jLabel6.setText("Quantity:");

        jButton1.setBackground(new java.awt.Color(128, 128, 128));
        jButton1.setForeground(new java.awt.Color(248, 246, 240));
        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(19, 62, 135));
        jButton4.setForeground(new java.awt.Color(248, 246, 240));
        jButton4.setText("Register");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 102, 102));
        jButton5.setForeground(new java.awt.Color(248, 246, 240));
        jButton5.setText("Remove");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(35, 194, 150));
        jButton6.setForeground(new java.awt.Color(248, 246, 240));
        jButton6.setText("Update");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(248, 246, 240));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(248, 246, 240));
        jLabel7.setText("BIRINGAN STATE UNIVERSITY");

        conditionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New", "Good", "Needs Repair", "For Replacement", "Lost" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(145, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(idText, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(conditionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(locationBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 136, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(96, 96, 96))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(locationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(conditionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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
            idText.setText(String.valueOf(jTable1.getValueAt(selectedRow, 0))); 
            nameText.setText(String.valueOf(jTable1.getValueAt(selectedRow, 1)));
            typeBox.setSelectedItem(String.valueOf(jTable1.getValueAt(selectedRow, 2)));
            conditionBox.setSelectedItem(String.valueOf(jTable1.getValueAt(selectedRow, 3)));
            locationBox.setSelectedItem(String.valueOf(jTable1.getValueAt(selectedRow, 4)));
            quantityText.setText(String.valueOf(jTable1.getValueAt(selectedRow, 5)));
        } else {
        selectedEquipmentID = 0; 
        idText.setText("");
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
    private javax.swing.JTextField idText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> locationBox;
    private javax.swing.JTextField nameText;
    private javax.swing.JTextField quantityText;
    private javax.swing.JComboBox<String> typeBox;
    // End of variables declaration//GEN-END:variables
}
