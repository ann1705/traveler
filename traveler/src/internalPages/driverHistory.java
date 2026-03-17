/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPages;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RageKing
 */
public class driverHistory extends javax.swing.JInternalFrame {

    /**
     * Creates new form driverHistory
     */
    public driverHistory() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        javax.swing.plaf.basic.BasicInternalFrameUI bi = (javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        
        styleTable();
        displayHistory();
    }

   private void styleTable() {
        // 1. Set Table Body to Navy [12, 33, 74]
        historyTable.setBackground(new java.awt.Color(12, 33, 74));
        historyTable.setForeground(java.awt.Color.WHITE); // White text for body
        historyTable.setRowHeight(35);
        historyTable.setGridColor(new java.awt.Color(30, 50, 90)); // Subtle darker grid
        
        // 2. Selection color (Light Blue so you can see what is selected)
        historyTable.setSelectionBackground(new java.awt.Color(76, 143, 209));
        historyTable.setSelectionForeground(java.awt.Color.WHITE);
        
        // 3. Set the ScrollPane Viewport to match the Navy background
        jScrollPane1.getViewport().setBackground(new java.awt.Color(12, 33, 74));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        // 4. Style the Header with White Background and Navy Font
        javax.swing.table.JTableHeader header = historyTable.getTableHeader();
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(java.awt.Color.WHITE); // White Background
                setForeground(new java.awt.Color(12, 33, 74)); // Navy Text
                setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
                return this;
            }
        });
    }

    private void applyCellStyling() {
        // Keeps the content centered and ensures the Navy background stays applied to cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(new java.awt.Color(12, 33, 74));
        centerRenderer.setForeground(java.awt.Color.WHITE);

        for(int i = 0; i < historyTable.getColumnCount(); i++){
            historyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    public void displayHistory() {
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        model.setColumnIdentifiers(new String[]{"Booking ID", "Pickup", "Destination", "Total Price", "Status"});
        model.setRowCount(0);

        try {
            config.dbConnector connector = config.dbConnector.getInstance();
             config.Session sess = config.Session.getInstance();
            int driverId = sess.getId();

            // Only show bookings for this driver that are Completed or Cancelled
            // We use driver_id is NOT NULL to ensure the admin still sees the link, 
            // but we can filter it out locally if needed.
            String query = "SELECT b_id, pick_up, destination, total_price, status FROM tbl_bookings " +
                           "WHERE driver_id = '" + driverId + "' AND (status = 'Completed' OR status = 'Cancelled')";

            ResultSet rs = connector.getData(query);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("b_id"),
                    rs.getString("pick_up"),
                    rs.getString("destination"),
                    "₱" + String.format("%.2f", rs.getDouble("total_price")),
                    rs.getString("status")
                });
                
               applyCellStyling(); 
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        delete = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        historyTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(historyTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 750, 360));

        delete.setBackground(new java.awt.Color(12, 33, 74));
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
        });
        delete.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DELETE");
        delete.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 160, 20));

        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 460, 160, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked
                                          
        int rowIndex = historyTable.getSelectedRow();
        
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a trip to remove from your history.");
            return;
        }

        String id = historyTable.getModel().getValueAt(rowIndex, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Remove this trip from your history? (Admin will still keep the record)", 
                "Remove History", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            config.dbConnector connector = config.dbConnector.getInstance();
            
            // This 'unassigns' the driver from the completed booking.
            // Admin can still see the booking, but this driver's history will be clear.
            String sql = "UPDATE tbl_bookings SET driver_id = NULL WHERE b_id = '" + id + "'";
            
            if (connector.updateData(sql)) {
                JOptionPane.showMessageDialog(this, "Trip record removed from your dashboard.");
                displayHistory(); 
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        }
    
    }//GEN-LAST:event_deleteMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel delete;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
