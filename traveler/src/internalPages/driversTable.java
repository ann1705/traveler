/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPages;

import dashboard.adminDashboard;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class driversTable extends javax.swing.JInternalFrame {

    
public String bookingID;
public int driverSlot;
public String vanID; // Add this line


   public driversTable() {
        initComponents();
        displayDrivers();
        styleTable();
        
        // Match the theme design
        drivertable.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        drivertable.getTableHeader().setBackground(java.awt.Color.decode("#021C3B"));
        drivertable.getTableHeader().setForeground(java.awt.Color.BLACK);
        
        drivertable.setBackground(java.awt.Color.decode("#03294E"));
        drivertable.setForeground(java.awt.Color.WHITE);
        drivertable.setRowHeight(35);
        drivertable.setGridColor(java.awt.Color.decode("#233E5C"));
        drivertable.setSelectionBackground(java.awt.Color.decode("#256B97"));
        
        jScrollPane1.getViewport().setBackground(java.awt.Color.decode("#03294E"));
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
    }

    private void styleTable() {
        // Matching fleetTable design
        drivertable.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        drivertable.getTableHeader().setBackground(java.awt.Color.decode("#021C3B"));
        drivertable.getTableHeader().setForeground(java.awt.Color.BLACK);
        
        drivertable.setBackground(java.awt.Color.decode("#03294E"));
        drivertable.setForeground(java.awt.Color.WHITE);
        drivertable.setRowHeight(30);
        drivertable.setGridColor(java.awt.Color.decode("#233E5C"));
        
        jScrollPane1.getViewport().setBackground(java.awt.Color.decode("#03294E"));
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
    }
    
    
public void displayDrivers() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        model.addColumn("ID"); 
        model.addColumn("Name"); 
        model.addColumn("Availability");

        try {
            config.dbConnector connector = config.dbConnector.getInstance();
            
            // LOGIC: A driver is BUSY if they are linked to ANY booking that is 'Dispatched' or 'Paid'
            String query = "SELECT a.a_id, a.firstname, " +
                           "(SELECT COUNT(*) FROM tbl_bookings b " +
                           " WHERE b.driver_id = a.a_id AND b.status IN ('Dispatched', 'Booked', 'Paid')) as active_jobs " +
                           "FROM tbl_account a " +
                           "WHERE LOWER(a.type) = 'driver' AND a.status = 'Active'";
            
            ResultSet rs = connector.getData(query);
            while (rs.next()) {
                int activeJobs = rs.getInt("active_jobs");
                String status = (activeJobs > 0) ? "BUSY" : "AVAILABLE";
                
                model.addRow(new Object[]{
                    rs.getString("a_id"), 
                    rs.getString("firstname"), 
                    status
                });
            }
            drivertable.setModel(model);
        } catch (Exception e) { 
            System.out.println("Display Error: " + e.getMessage()); 
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        drivertable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

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

        drivertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        drivertable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drivertableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(drivertable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 730, 400));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/back_black.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 70, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void drivertableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drivertableMouseClicked
                                           
    int row = drivertable.getSelectedRow();
    if (row == -1) return;

    String dID = drivertable.getValueAt(row, 0).toString();
    String dName = drivertable.getValueAt(row, 1).toString();
    String availability = drivertable.getValueAt(row, 2).toString();

    if (availability.equals("BUSY")) {
        JOptionPane.showMessageDialog(null, dName + " is already assigned to another van!", "Driver Unavailable", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(null, "Assign " + dName + " to Van ID: " + vanID + " (Booking #" + bookingID + ")?");
    
    if (confirm == JOptionPane.YES_OPTION) {
        config.dbConnector connector = config.dbConnector.getInstance();
        
        try {
            // Removed single quotes for Integer IDs: b_id and v_id
            String updateBooking = "UPDATE tbl_bookings SET driver_id = " + dID + ", status = 'Dispatched' " +
                                   "WHERE b_id = " + bookingID;
            
            String updateVan = "UPDATE tbl_vans SET status = 'Dispatched' WHERE v_id = " + vanID;

            if (connector.updateData(updateBooking)) {
                connector.updateData(updateVan); // Also update the van status
                JOptionPane.showMessageDialog(null, dName + " is now assigned to this trip.");
                this.dispose(); // This triggers the InternalFrameClosed listener in bookingsTable
            }
        } catch (Exception e) { 
            System.out.println("Assignment Error: " + e.getMessage()); 
        }
    }



    }//GEN-LAST:event_drivertableMouseClicked

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged

    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed

    }//GEN-LAST:event_jPanel1MousePressed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
                                        
    // 1. Get the DesktopPane from the current frame
    javax.swing.JDesktopPane desktop = this.getDesktopPane();
    
    // 2. Create the instance of the bookingsTable
    internalPages.bookingsTable bt = new internalPages.bookingsTable();
    
    // 3. Remove the current frame (driversTable)
    this.dispose();
    
    // 4. Add and show the bookingsTable
    desktop.add(bt);
    bt.setVisible(true);
    
    // Optional: Center it or make it fill the desktop
    try {
        bt.setMaximum(true); // If you want it full screen
    } catch (java.beans.PropertyVetoException e) {
        System.out.println("Error maximizing: " + e.getMessage());
    }

      
    }//GEN-LAST:event_jLabel2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable drivertable;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
