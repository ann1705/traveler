/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPages;

import app.addForm;
import app.editprofile;
import config.dbConnector;

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
   
    public usersTable() {
        initComponents();
        usertable.setToolTipText("Double-click to edit user details");
        displayData();
        
        // 1. Style the Table Header (Palette #4: #021C3B)
        usertable.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        usertable.getTableHeader().setBackground(java.awt.Color.decode("#021C3B"));
        usertable.getTableHeader().setForeground(java.awt.Color.BLACK);
        usertable.getTableHeader().setOpaque(false);

        // 2. Style the Table Body (Palette #5: #03294E)
        usertable.setBackground(java.awt.Color.decode("#03294E"));
        usertable.setForeground(java.awt.Color.WHITE);
        usertable.setGridColor(java.awt.Color.decode("#233E5C"));
        usertable.setRowHeight(30);

        // 3. Selection Color (Palette #7: #256B97)
        usertable.setSelectionBackground(java.awt.Color.decode("#256B97"));
        usertable.setSelectionForeground(java.awt.Color.WHITE);
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        
       
        jScrollPane1.getViewport().setBackground(java.awt.Color.decode("#03294E"));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        // searchfield styling
        searchfield.setBackground(java.awt.Color.decode("#233E5C")); 
        searchfield.setForeground(java.awt.Color.WHITE);
        searchfield.setCaretColor(java.awt.Color.WHITE); 
        searchfield.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
        
        usertable.setRowSelectionAllowed(true);
        usertable.setColumnSelectionAllowed(false);
        usertable.setFocusable(false); 
    }

 
    
public void displayData() {
    // This specific override is what stops the double-click editing
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // All cells are now read-only
        }
    };

    String[] columnNames = {"ID", "First Name", "Last Name", "Username", "Email", "Type", "Status"};
    for (String col : columnNames) {
        model.addColumn(col);
    }

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        String query = "SELECT a_id, firstname, lastname, username, email, type, status FROM tbl_account";
        java.sql.ResultSet rs = connector.getData(query);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("a_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("type"),
                rs.getString("status")
            });
        }
        usertable.setModel(model);

    } catch (java.sql.SQLException ex) {
        System.out.println("SQL ERROR: " + ex.getMessage());
    }
}


    private void updateTable(String query) {
    // Create a model that prevents cell editing
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Table is read-only
        }
    };

    // Define columns to match your displayData()
    String[] columnNames = {"ID", "First Name", "Last Name", "Username", "Email", "Type", "Status"};
    for (String col : columnNames) {
        model.addColumn(col);
    }

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        java.sql.ResultSet rs = connector.getData(query);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("a_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("username"), // Ensure this matches your DB column name
                rs.getString("email"),
                rs.getString("type"),
                rs.getString("status")
            });
        }
        usertable.setModel(model);
        
    } catch (java.sql.SQLException ex) {
        System.out.println("Search Update Error: " + ex.getMessage());
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(798, 602));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add.setBackground(new java.awt.Color(2, 54, 85));
        add.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addMouseExited(evt);
            }
        });
        add.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(204, 102, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADD");
        add.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel2.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, 30));

        edit.setBackground(new java.awt.Color(2, 54, 85));
        edit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editMouseExited(evt);
            }
        });
        edit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("EDIT");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        edit.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel2.add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 90, 30));

        delete.setBackground(new java.awt.Color(2, 54, 85));
        delete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteMouseExited(evt);
            }
        });
        delete.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DELETE");
        delete.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 30));

        jPanel2.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 80, 30));

        refresh.setBackground(new java.awt.Color(2, 54, 85));
        refresh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshMouseExited(evt);
            }
        });
        refresh.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("REFRESH");
        refresh.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel2.add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 90, 30));

        search.setBackground(new java.awt.Color(2, 54, 85));
        search.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchMouseExited(evt);
            }
        });
        search.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sead.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        sead.setForeground(new java.awt.Color(255, 255, 255));
        sead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sead.setText("SEARCH");
        search.add(sead, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 30));

        jPanel2.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 80, 30));

        searchfield.setBackground(new java.awt.Color(2, 54, 85));
        searchfield.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        searchfield.setForeground(new java.awt.Color(255, 255, 255));
        searchfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });
        searchfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchfieldKeyReleased(evt);
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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 570));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                                   
    int row = usertable.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please select a user to modify!");
        return;
    }

    String targetId = usertable.getValueAt(row, 0).toString();

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        String query = "SELECT * FROM tbl_account WHERE a_id = '" + targetId + "'";
        java.sql.ResultSet rs = connector.getData(query);

        if (rs.next()) {
            editprofile ep = new editprofile();
            
            // Pass all 10 strings directly from the database result set
            ep.populateForAdmin(
                rs.getString("a_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("address"),   
                rs.getString("gender"),    
                rs.getString("contact"),   
                rs.getString("civil_status"), // Fix: This correctly maps to Civil Status
                rs.getString("status")     
            );

            ep.setVisible(true);
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            mainFrame.dispose();
        }
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
    }


    
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

    }//GEN-LAST:event_searchfieldActionPerformed

    private void usertableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usertableMouseClicked
       if (evt.getClickCount() == 2) {
           editMouseClicked(evt);
        
    }
    }//GEN-LAST:event_usertableMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
                                    
      
    
   
    }//GEN-LAST:event_jLabel2MouseClicked

    private void searchfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchfieldKeyReleased
                                    
    String text = searchfield.getText().trim();
    
    // Create the same Non-Editable Model here
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; 
        }
    };

    String[] columnNames = {"ID", "First Name", "Last Name", "Username", "Email", "Type", "Status"};
    for (String col : columnNames) { model.addColumn(col); }

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        String query = "SELECT * FROM tbl_account WHERE firstname LIKE '%"+text+"%' "
                     + "OR lastname LIKE '%"+text+"%' OR username LIKE '%"+text+"%'";
        java.sql.ResultSet rs = connector.getData(query);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("a_id"), rs.getString("firstname"), rs.getString("lastname"),
                rs.getString("username"), rs.getString("email"), rs.getString("type"), rs.getString("status")
            });
        }
        usertable.setModel(model);
    } catch (java.sql.SQLException ex) {
        System.out.println("Search Error: " + ex.getMessage());
    }




    }//GEN-LAST:event_searchfieldKeyReleased

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
        add.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
        add.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_addMouseExited

    private void editMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseEntered
       edit.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_editMouseEntered

    private void editMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseExited
       edit.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_editMouseExited

    private void deleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseEntered
        delete.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_deleteMouseEntered

    private void refreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseEntered
       refresh.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_refreshMouseEntered

    private void searchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseEntered
        search.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_searchMouseEntered

    private void deleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseExited
       delete.setBackground(java.awt.Color.decode("#233E5C"));       
    }//GEN-LAST:event_deleteMouseExited

    private void refreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseExited
      refresh.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_refreshMouseExited

    private void searchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseExited
      search.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_searchMouseExited


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
