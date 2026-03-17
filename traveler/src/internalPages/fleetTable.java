/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPages;

import app.addFleet;
import config.dbConnector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RageKing
 */
public class fleetTable extends javax.swing.JInternalFrame {

   public boolean isUpdate = false;
   
    public fleetTable() {
        initComponents();
        displayData();
        
   fleettable.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
    fleettable.getTableHeader().setBackground(java.awt.Color.decode("#021C3B"));
    fleettable.getTableHeader().setForeground(java.awt.Color.BLACK); // Set to WHITE if black is hard to read on deep blue
    fleettable.getTableHeader().setOpaque(false);

    // 2. Style the Table Body (Palette #5: #03294E)
    fleettable.setBackground(java.awt.Color.decode("#03294E"));
    fleettable.setForeground(java.awt.Color.WHITE);
    fleettable.setGridColor(java.awt.Color.decode("#233E5C"));
    fleettable.setRowHeight(30);

    // 3. Selection Color (Palette #7: #256B97)
    fleettable.setSelectionBackground(java.awt.Color.decode("#256B97"));
    fleettable.setSelectionForeground(java.awt.Color.WHITE);
    
    // Remove borders and title bar for InternalFrame
    this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
    javax.swing.plaf.basic.BasicInternalFrameUI bi = (javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI();
    bi.setNorthPane(null);
    
    // Styling the ScrollPane
    jScrollPane1.getViewport().setBackground(java.awt.Color.decode("#03294E"));
    jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    
    // Searchfield styling (Matching your usersTable)
    searchfield.setBackground(java.awt.Color.decode("#233E5C")); 
    searchfield.setForeground(java.awt.Color.WHITE);
    searchfield.setCaretColor(java.awt.Color.WHITE); 
    searchfield.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
    
    fleettable.setRowSelectionAllowed(true);
    fleettable.setFocusable(false);
    }
    
   public void displayData() {
    DefaultTableModel tblModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; 
        }
    };

    String[] columnNames = {"ID", "Category", "Model", "Plate No", "Capacity", "Rate", "Status"};
    for (String col : columnNames) { tblModel.addColumn(col); }

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        String query = "SELECT v_id, category, model, plate_number, capacity, daily_rate, status FROM tbl_vans";
        java.sql.ResultSet rs = connector.getData(query);

        while (rs.next()) {
            tblModel.addRow(new Object[]{
                rs.getString("v_id"),
                rs.getString("category"),
                rs.getString("model"),
                rs.getString("plate_number"),
                rs.getString("capacity"),
                rs.getString("daily_rate"),
                rs.getString("status")
            });
        }
        fleettable.setModel(tblModel);
    } catch (java.sql.SQLException ex) {
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
        fleettable = new javax.swing.JTable();

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
        search.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sead.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        sead.setForeground(new java.awt.Color(255, 255, 255));
        sead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sead.setText("SEARCH");
        search.add(sead, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 30));

        jPanel2.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 80, 30));

        searchfield.setBackground(new java.awt.Color(2, 54, 85));
        searchfield.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
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

        fleettable.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        fleettable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        fleettable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fleettableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(fleettable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 720, 370));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
       addFleet af = new addFleet();
        af.setVisible(true);
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        mainFrame.dispose();

    }//GEN-LAST:event_addMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked

    }//GEN-LAST:event_jLabel2MouseClicked

    private void editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseClicked
                                         
       int row = fleettable.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please select a van to modify!");
        return;
    }

        String id = fleettable.getValueAt(row, 0).toString();

        try {
            dbConnector connector = dbConnector.getInstance();
            java.sql.ResultSet rs = connector.getData("SELECT * FROM tbl_vans WHERE v_id = '" + id + "'");

            if (rs.next()) {
                addFleet af = new addFleet();
                af.setVisible(true);
                
                // Populate the addFleet form with existing data
                af.fillForm(
                    rs.getString("v_id"),
                    rs.getString("category"),
                    rs.getString("model"),
                    rs.getString("plate_number"),
                    rs.getString("capacity"),
                    rs.getString("daily_rate"),
                    rs.getString("status"),
                    rs.getString("image")
                );

                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                mainFrame.dispose();
            }
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
        }

    }//GEN-LAST:event_editMouseClicked

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked
                                  
    int row = fleettable.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please select a van!");
        return;
    }

    String id = fleettable.getValueAt(row, 0).toString();
    String modelName = fleettable.getValueAt(row, 2).toString(); // Column 2 is "Model"

    int confirm = JOptionPane.showConfirmDialog(
        null, 
        "Are you sure you want to delete " + modelName + "?", 
        "Warning", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        config.dbConnector connector = config.dbConnector.getInstance();
        connector.insertData("DELETE FROM tbl_vans WHERE v_id = '" + id + "'");
        displayData();
    }

    }//GEN-LAST:event_deleteMouseClicked

    private void refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseClicked

        displayData();
        JOptionPane.showMessageDialog(null, "Table Refreshed!");

    }//GEN-LAST:event_refreshMouseClicked

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed

    }//GEN-LAST:event_searchfieldActionPerformed

    private void searchfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchfieldKeyReleased
                                         
    String text = searchfield.getText().trim();

    // Use 'tblModel' here as well
    DefaultTableModel tblModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    String[] columnNames = {"ID", "Category", "Model", "Plate No", "Capacity", "Rate", "Status"};
    for (String col : columnNames) { tblModel.addColumn(col); }

    try {
        dbConnector connector = dbConnector.getInstance();
        // Updated query to search through van-specific columns
        String query = "SELECT * FROM tbl_vans WHERE model LIKE '%"+text+"%' "
                     + "OR plate_number LIKE '%"+text+"%' OR category LIKE '%"+text+"%'";
        java.sql.ResultSet rs = connector.getData(query);

        while (rs.next()) {
            tblModel.addRow(new Object[]{
                rs.getString("v_id"), 
                rs.getString("category"), 
                rs.getString("model"),
                rs.getString("plate_number"),
                rs.getString("capacity"), 
                rs.getString("daily_rate"), 
                rs.getString("status")
            });
        }
        fleettable.setModel(tblModel);
    } catch (java.sql.SQLException ex) {
        System.out.println("Search Error: " + ex.getMessage());
    }



    }//GEN-LAST:event_searchfieldKeyReleased

    private void fleettableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fleettableMouseClicked
        if (evt.getClickCount() == 2) {
            editMouseClicked(evt);

        }
    }//GEN-LAST:event_fleettableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel add;
    private javax.swing.JPanel delete;
    private javax.swing.JPanel edit;
    private javax.swing.JTable fleettable;
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
    // End of variables declaration//GEN-END:variables
}
