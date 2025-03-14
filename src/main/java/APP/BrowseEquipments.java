/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package APP;

import LIB.DatabaseConnection;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amar Pajarito
 */
public class BrowseEquipments extends javax.swing.JFrame {

    DatabaseConnection connect;

    public BrowseEquipments() {
        initComponents();
        setLocationRelativeTo(null); 
        setTitle("SEAM Browse Equipments"); 
        setResizable(false);
        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGES/ssulogo.png"));
        setIconImage(icon.getImage());
        update("SELECT * FROM EQUIPMENT;");
        fetchType();
        fetchCondition();
        fetchLocation();
    }
    
    public void fetchType() {
        try {
            connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT(type) FROM EQUIPMENT;");
            while (rs.next()) {
                typeBox.addItem(rs.getString("type"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    public void fetchCondition() {
        try {
            connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT(condition) FROM EQUIPMENT;");
            while (rs.next()) {
                conditionBox.addItem(rs.getString("condition"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    public void fetchLocation() {
        try {
            connect = DatabaseConnection.getInstance();
            Connection conn = connect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT(location) FROM EQUIPMENT;");
            while (rs.next()) {
                locationBox.addItem(rs.getString("location"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    public void update(String query) {
        Connection conn;
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.getDataVector().removeAllElements();
        try {
            connect = DatabaseConnection.getInstance();
            conn = connect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        
            while (rs.next()) {
                int equipmentID = rs.getInt("equipmentID");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String condition = rs.getString("condition");
                String location = rs.getString("location");
                int quantity = rs.getInt("quantity");
            
                String rowData[] = {String.valueOf(equipmentID), name, type, condition, location, String.valueOf(quantity)};
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
            conn.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    
    private void filterResults() {
        String type = String.valueOf(typeBox.getSelectedItem());
        String condition = String.valueOf(conditionBox.getSelectedItem());
        String location = String.valueOf(locationBox.getSelectedItem());

        String query;

        if ("Any".equals(type) && "Any".equals(condition) && "Any".equals(location)) {
            query = "SELECT * FROM EQUIPMENT;";
        } else {
            query = "SELECT * FROM EQUIPMENT WHERE";
            boolean filterExists = false;

            if (!"Any".equals(type)) {
                query += " type = '" + type + "'";
                filterExists = true;
            }   

            if (!"Any".equals(condition)) {
                if (filterExists) query += " AND ";
                    query += " condition = '" + condition + "'";
                filterExists = true;
            }

            if (!"Any".equals(location)) {
                if (filterExists) query += " AND ";
                query += " location = '" + location + "'";
            }
        }

        update(query);
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
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        typeBox = new javax.swing.JComboBox<>();
        conditionBox = new javax.swing.JComboBox<>();
        locationBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(125, 10, 10));
        jPanel2.setPreferredSize(new java.awt.Dimension(819, 100));

        jLabel7.setBackground(new java.awt.Color(163, 29, 29));
        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(236, 220, 191));
        jLabel7.setText("BROWSE EQUIPMENTS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(158, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(124, 124, 124))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(236, 220, 191));
        jPanel3.setPreferredSize(new java.awt.Dimension(819, 141));

        jButton5.setBackground(new java.awt.Color(125, 10, 10));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Reset");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        typeBox.setBackground(new java.awt.Color(248, 242, 222));
        typeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Any" }));
        typeBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeBoxItemStateChanged(evt);
            }
        });
        typeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeBoxActionPerformed(evt);
            }
        });

        conditionBox.setBackground(new java.awt.Color(248, 242, 222));
        conditionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Any" }));
        conditionBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeBoxItemStateChanged(evt);
            }
        });

        locationBox.setBackground(new java.awt.Color(248, 242, 222));
        locationBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Any" }));
        locationBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeBoxItemStateChanged(evt);
            }
        });

        jLabel3.setText("Type:");

        jLabel5.setText("Condition:");

        jLabel8.setText("Location:");

        jButton2.setBackground(new java.awt.Color(248, 242, 222));
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(293, 293, 293))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(conditionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(locationBox, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(locationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(conditionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(236, 220, 191));
        jPanel4.setPreferredSize(new java.awt.Dimension(819, 245));

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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addGap(0, 14, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        typeBox.setSelectedIndex(0);
        conditionBox.setSelectedIndex(0);
        locationBox.setSelectedIndex(0);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void typeBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeBoxItemStateChanged
        // TODO add your handling code here:
        filterResults();
    }//GEN-LAST:event_typeBoxItemStateChanged

    private void typeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BrowseEquipments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BrowseEquipments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BrowseEquipments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BrowseEquipments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BrowseEquipments().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> conditionBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> locationBox;
    private javax.swing.JComboBox<String> typeBox;
    // End of variables declaration//GEN-END:variables
}
