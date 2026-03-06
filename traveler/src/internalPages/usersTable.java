/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPages;

import app.addForm;
import app.editprofile;
import config.dbConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RageKing
 */
public class usersTable extends javax.swing.JInternalFrame {

    /**
     * Creates new form usersTable
     */
    public usersTable() {
        initComponents();
        displayData();
        
      
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
    }

 
    
   public void displayData() {

    DefaultTableModel model = new DefaultTableModel();
    // Re-define columns explicitly to ensure they show up
    String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Type", "Status"};
    for (String col : columnNames) {
        model.addColumn(col);
    }

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        String query = "SELECT a_id, firstname, lastname, email, type, status FROM tbl_account";
        java.sql.ResultSet rs = connector.getData(query);

        if (rs == null) {
            System.out.println("Error: ResultSet is null.");
            return;
        }

        int rowCount = 0;
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("a_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("type"),
                rs.getString("status")
            });
            rowCount++;
        }
        
        // CRITICAL: Bind the populated model to the actual UI component
        usertable.setModel(model);
        System.out.println("Successfully loaded " + rowCount + " rows.");

    } catch (SQLException ex) {
        System.out.println("SQL ERROR: " + ex.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        add = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        edit = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        delete = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        refresh = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        search = new javax.swing.JPanel();
        sead = new javax.swing.JLabel();
        searchfield = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        usertable = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(154, 154, 238));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(154, 154, 238));
        jPanel2.setPreferredSize(new java.awt.Dimension(798, 602));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add.setBackground(new java.awt.Color(204, 102, 255));
        add.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
        });
        add.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(204, 102, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADD");
        add.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel2.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, 30));

        edit.setBackground(new java.awt.Color(204, 102, 255));
        edit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMouseClicked(evt);
            }
        });
        edit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("EDIT");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        edit.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel2.add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 90, 30));

        delete.setBackground(new java.awt.Color(204, 102, 255));
        delete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
        });
        delete.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DELETE");
        delete.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 30));

        jPanel2.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 80, 30));

        refresh.setBackground(new java.awt.Color(204, 102, 255));
        refresh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshMouseClicked(evt);
            }
        });
        refresh.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("REFRESH");
        refresh.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel2.add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 90, 30));

        search.setBackground(new java.awt.Color(204, 102, 255));
        search.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        search.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sead.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        sead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sead.setText("SEARCH");
        search.add(sead, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 30));

        jPanel2.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 80, 30));

        searchfield.setBackground(new java.awt.Color(154, 154, 238));
        searchfield.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        searchfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        jPanel2.add(searchfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 180, 30));

        usertable.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        usertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        usertable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usertableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(usertable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 720, 370));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 530));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
         mainFrame.dispose();
        addForm af = new addForm();
       af.setVisible(true);
        

    }//GEN-LAST:event_addMouseClicked

    private void editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseClicked
     jLabel2MouseClicked(null);
    }//GEN-LAST:event_editMouseClicked

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked

        int row = usertable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user!");
        } else {
            String id = usertable.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(null, "Delete ID: " + id + "?", "Warning", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dbConnector.getInstance().insertData("DELETE FROM tbl_account WHERE a_id = '" + id + "'");
                displayData();
            }
        }
    }//GEN-LAST:event_deleteMouseClicked

    private void refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseClicked

        displayData();
        JOptionPane.showMessageDialog(null, "Table Refreshed!");
        
    }//GEN-LAST:event_refreshMouseClicked

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed

        String text = searchfield.getText();
        config.dbConnector connector = config.dbConnector.getInstance();
        String query = "SELECT * FROM tbl_account WHERE firstname LIKE '%" + text + "%' OR lastname LIKE '%" + text + "%'";

        try (ResultSet rs = connector.getData(query)) {
            DefaultTableModel model = (DefaultTableModel) usertable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("a_id"), rs.getString("firstname"), rs.getString("lastname"),
                    rs.getString("email"), rs.getString("type"), rs.getString("status")
                });
            }
        } catch (SQLException ex) {
            System.out.println("Search Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_searchfieldActionPerformed

    private void usertableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usertableMouseClicked

    }//GEN-LAST:event_usertableMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
                                    
        int row = usertable.getSelectedRow();
        config.Session sess = config.Session.getInstance();
        
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to modify!");
            return;
        }

        // Get the ID from the first column
        String targetId = usertable.getValueAt(row, 0).toString();

        // GUARD: Prevent Admin from editing themselves through the table 
        // (They should use their own 'My Profile' button instead)
        if (targetId.equals(String.valueOf(sess.getId()))) {
            JOptionPane.showMessageDialog(null, "To edit your own account, please use the 'My Profile' settings.", "Notice", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Extract data from the table row
        String fn = usertable.getValueAt(row, 1).toString();
        String ln = usertable.getValueAt(row, 2).toString();
        String em = usertable.getValueAt(row, 3).toString();
        String ty = usertable.getValueAt(row, 4).toString(); // User Type
        String st = usertable.getValueAt(row, 5).toString(); // Status

        // Open the Edit Profile form
        editprofile ep = new editprofile();
        
        // Use the specialized method we created to set 'isEditingOther' to true
        // Note: Make sure the username is also passed if needed, 
        // or modify populateForAdmin to fetch it via SQL in editprofile
        ep.populateForAdmin(targetId, fn, ln, "", em, st); 
        
        ep.setVisible(true);
        
        // Close the current Dashboard/Frame to prevent window cluttering
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        mainFrame.dispose();
    
   
    }//GEN-LAST:event_jLabel2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel add;
    private javax.swing.JPanel delete;
    private javax.swing.JPanel edit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel refresh;
    private javax.swing.JLabel sead;
    private javax.swing.JPanel search;
    private javax.swing.JTextField searchfield;
    private javax.swing.JTable usertable;
    // End of variables declaration//GEN-END:variables
}
