/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPages;

import dashboard.adminDashboard;
import static java.awt.Frame.ICONIFIED;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RageKing
 */
public class bookingsTable extends javax.swing.JInternalFrame {

    /**
     * Creates new form bookingsTable
     */
    public bookingsTable() {
        initComponents();
        displayBookings(); // <--- CRITICAL: This must be called to show data
    
    // --- MATCHING DESIGN STYLING ---
    // 1. Header Styling (#021C3B)
    bookingstable.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
    bookingstable.getTableHeader().setBackground(java.awt.Color.decode("#021C3B"));
    bookingstable.getTableHeader().setForeground(java.awt.Color.BLACK); 
    bookingstable.getTableHeader().setOpaque(false);

    // 2. Table Body Styling (#03294E)
    bookingstable.setBackground(java.awt.Color.decode("#03294E"));
    bookingstable.setForeground(java.awt.Color.WHITE);
    bookingstable.setGridColor(java.awt.Color.decode("#233E5C"));
    bookingstable.setRowHeight(30);

    // 3. Selection Color (#256B97)
    bookingstable.setSelectionBackground(java.awt.Color.decode("#256B97"));
    bookingstable.setSelectionForeground(java.awt.Color.WHITE);
    
    // 4. ScrollPane & InternalFrame clean-up
    jScrollPane1.getViewport().setBackground(java.awt.Color.decode("#03294E"));
    jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
    
    javax.swing.plaf.basic.BasicInternalFrameUI bi = (javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI();
    bi.setNorthPane(null);
        
        
        
    }

public void displayBookings() {
    DefaultTableModel tblModel = new DefaultTableModel() {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };

    // 1. Updated column names
    String[] columnNames = {"Booking ID", "Customer ID", "Van ID", "Van Model", "Destination", "Driver", "Status"};
    for (String col : columnNames) { tblModel.addColumn(col); }

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        
        // 2. Updated Query: Selecting c.a_id (Customer ID) instead of c.firstname
        // Inside displayBookings() in bookingsTable.java
String query = "SELECT b.b_id, b.a_id AS customer_id, v.v_id, v.model, b.destination, "
             + "d.firstname AS driver_fname, d.lastname AS driver_lname, b.status "
             + "FROM tbl_bookings b "
             + "LEFT JOIN tbl_booking_items bi ON b.b_id = bi.b_id " 
             + "LEFT JOIN tbl_vans v ON bi.v_id = v.v_id "
             + "LEFT JOIN tbl_account d ON b.driver_id = d.a_id";

        java.sql.ResultSet rs = connector.getData(query);

        while (rs.next()) {
          
            String driverName = rs.getString("driver_fname");
            // If driver_id is 0 or null in DB, show warning
            if (driverName == null || driverName.isEmpty()) {
                driverName = "⚠️ NEEDS DRIVER";
            } else {
                driverName = driverName + " " + rs.getString("driver_lname");
            }

            tblModel.addRow(new Object[]{
                rs.getString("b_id"), 
                rs.getString("customer_id"),
                rs.getString("v_id"),
                rs.getString("model"), 
                rs.getString("destination"),
                driverName, 
                rs.getString("status")
            });
        }
        bookingstable.setModel(tblModel);
        styleTable(); 
        
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database Sync Error: " + ex.getMessage());
    }
}
 private void styleTable() {
    bookingstable.getTableHeader().setBackground(java.awt.Color.decode("#021C3B"));
    bookingstable.getTableHeader().setForeground(java.awt.Color.BLACK);
    bookingstable.setBackground(java.awt.Color.decode("#03294E"));
    bookingstable.setForeground(java.awt.Color.WHITE);
    bookingstable.setRowHeight(30);
}

private void openDriverPicker(int row) {
    // ... existing status checks ...

    String bID = bookingstable.getValueAt(row, 0).toString();
    String vID = (bookingstable.getValueAt(row, 2) != null) ? bookingstable.getValueAt(row, 2).toString() : "0";

    driversTable dt = new driversTable();
    dt.bookingID = bID;
    dt.vanID = vID;

    // FIX: Check if DesktopPane exists before adding
    javax.swing.JDesktopPane desktop = this.getDesktopPane();
    
    if (desktop != null) {
        desktop.add(dt);
        dt.setVisible(true);
        dt.toFront();
        dt.setLocation((desktop.getWidth() - dt.getWidth()) / 2,
                       (desktop.getHeight() - dt.getHeight()) / 2);
    } else {
        // Fallback: If it's not in a desktop pane, show it in a standard dialog or log error
        JOptionPane.showMessageDialog(null, "System Error: Desktop Pane not found.");
    }
}
    

  
  
    @SuppressWarnings("unchecked")
     
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookingstable = new javax.swing.JTable();
        assignDriver = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookingstable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        bookingstable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingstableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bookingstable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 730, 400));

        assignDriver.setBackground(new java.awt.Color(12, 33, 74));
        assignDriver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                assignDriverMouseClicked(evt);
            }
        });
        assignDriver.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ASSIGN DRIVER");
        assignDriver.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 180, 20));

        jPanel1.add(assignDriver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 180, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bookingstableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingstableMouseClicked
                                             
    int row = bookingstable.getSelectedRow();
    int col = bookingstable.getSelectedColumn();

    if (col == 2 || col == 5) {
        String currentDriver = bookingstable.getValueAt(row, 5).toString();
        
        // If driver is already assigned, ask if they want to CHANGE it
        if (!currentDriver.equals("UNASSIGNED") && !currentDriver.contains("⚠️")) {
            int confirm = JOptionPane.showConfirmDialog(null, 
                "This van already has a driver (" + currentDriver + "). Do you want to reassign?", 
                "Reassign Driver", JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Exit if they don't want to change
            }
        }
        
        // ... proceed with opening driversTable as you already have it ...
        openDriverPicker(row);
    }




    }//GEN-LAST:event_bookingstableMouseClicked

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
      
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
       
    }//GEN-LAST:event_jPanel1MousePressed

    private void assignDriverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_assignDriverMouseClicked
                                            
    int row = bookingstable.getSelectedRow();
    
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please select a row from the table first!");
        return;
    }

    String status = bookingstable.getValueAt(row, 6).toString(); 

    if (status.equalsIgnoreCase("Cancelled")) {
        JOptionPane.showMessageDialog(null, "Cannot assign a driver to a cancelled booking!");
        return;
    }

    // Get the IDs from the selected row
    String bID = bookingstable.getValueAt(row, 0).toString(); // Booking ID
    String vID = bookingstable.getValueAt(row, 2).toString(); // Van ID

    // Create the picker window
    driversTable dt = new driversTable();
    dt.bookingID = bID; 
    dt.vanID = vID; // Passing this ensures the driver is assigned to the correct van

    // Add listener to refresh THIS table when the driver picker closes
    dt.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
        @Override
        public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
            displayBookings(); 
        }
    });

    this.getDesktopPane().add(dt);
    dt.setVisible(true);
    dt.toFront();
    
    // Center the internal frame
    dt.setLocation((this.getDesktopPane().getWidth() - dt.getWidth()) / 2,
                  (this.getDesktopPane().getHeight() - dt.getHeight()) / 2);





    }//GEN-LAST:event_assignDriverMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel assignDriver;
    private javax.swing.JTable bookingstable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
