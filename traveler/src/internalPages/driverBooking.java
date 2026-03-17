/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPages;

import config.dbConnector;
import java.awt.Color;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author RageKing
 */
public class driverBooking extends javax.swing.JInternalFrame {

    /**
     * Creates new form driverBooking
     */
    public driverBooking() {
        initComponents();
        displayBookings();
        designTable();
        
    this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
    
    javax.swing.plaf.basic.BasicInternalFrameUI bi = (javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI();
    bi.setNorthPane(null);
    }

private void designTable() {
    // 1. Style the Table Body to Navy [12, 33, 74]
    bookingTable.setBackground(new java.awt.Color(12, 33, 74));
    bookingTable.setForeground(java.awt.Color.WHITE); // White text for the navy body
    
    // Selection highlight color
    bookingTable.setSelectionBackground(new java.awt.Color(76, 143, 209)); 
    bookingTable.setSelectionForeground(java.awt.Color.WHITE);
    
    bookingTable.setRowHeight(35); 
    bookingTable.setShowGrid(true);
    bookingTable.setGridColor(new java.awt.Color(30, 50, 90)); // Darker grid for the dark body
    
    // 2. Style the ScrollPane Viewport to match the Navy background
    jScrollPane1.getViewport().setBackground(new java.awt.Color(12, 33, 74));
    jScrollPane1.setBackground(new java.awt.Color(12, 33, 74));
    jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    
    // 3. Style the Table Header (White Background / White Font)
    javax.swing.table.JTableHeader header = bookingTable.getTableHeader();
    header.setPreferredSize(new java.awt.Dimension(header.getWidth(), 40));
    header.setReorderingAllowed(false);
    
    header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBackground(java.awt.Color.WHITE); // White Background
            setForeground(java.awt.Color.BLACK); // White Font as requested
            setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 15));
            setHorizontalAlignment(javax.swing.JLabel.CENTER);
            setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));
            return this;
        }
    });
}

private void applyCellStyling() {
    javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    centerRenderer.setBackground(new java.awt.Color(12, 33, 74)); // Navy cells
    centerRenderer.setForeground(java.awt.Color.WHITE); // White text

    for (int i = 0; i < bookingTable.getColumnCount(); i++) {
        bookingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
}
    

   private void updateBookingStatus(String newStatus) {
    int selectedRow = bookingTable.getSelectedRow();
    if (selectedRow == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please select a booking.");
        return;
    }

    // Index 0 is "Booking ID" based on the new columnNames array above
    String bookingId = bookingTable.getValueAt(selectedRow, 0).toString();
    config.dbConnector connector = config.dbConnector.getInstance();
    
    String sql = "UPDATE tbl_bookings SET status = '" + newStatus + "' WHERE b_id = '" + bookingId + "'";
    
    if (connector.updateData(sql)) {
        javax.swing.JOptionPane.showMessageDialog(this, "Status updated!");
        displayBookings(); 
    }
}
    
public void displayBookings() {
    config.Session sess = config.Session.getInstance();
    int currentDriverId = sess.getId(); 

    String[] columnNames = {"Booking ID", "First Name", "Last Name", "Pickup", "Destination", "Status"};
    javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    bookingTable.setModel(model);

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        
        // ADDED: Logic to exclude 'Completed' and 'Cancelled'
        String query = "SELECT b.b_id, a.firstname, a.lastname, b.pick_up, b.destination, b.status " +
                       "FROM tbl_bookings b " +
                       "INNER JOIN tbl_account a ON b.a_id = a.a_id " +
                       "WHERE b.driver_id = " + currentDriverId + 
                       " AND b.status NOT IN ('Completed', 'Cancelled')"; // This hides them
        
        java.sql.ResultSet rs = connector.getData(query);
        
        while (rs.next()) {
            Object[] row = {
                rs.getInt("b_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("pick_up"),
                rs.getString("destination"),
                rs.getString("status")
            };
            model.addRow(row);
        }
        
        applyCellStyling(); 
        
    } catch (java.sql.SQLException e) {
        System.out.println("Query Error: " + e.getMessage());
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookingTable = new javax.swing.JTable();
        pickup = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        completed = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookingTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        bookingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(bookingTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 750, 360));

        pickup.setBackground(new java.awt.Color(12, 33, 74));
        pickup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pickupMouseClicked(evt);
            }
        });
        pickup.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PICK UP");
        pickup.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, 20));

        jPanel1.add(pickup, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 130, 40));

        completed.setBackground(new java.awt.Color(12, 33, 74));
        completed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                completedMouseClicked(evt);
            }
        });
        completed.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("COMPLETED");
        completed.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 160, 20));

        jPanel1.add(completed, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 460, 160, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void completedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_completedMouseClicked
       updateBookingStatus("Completed");
    }//GEN-LAST:event_completedMouseClicked

    private void pickupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pickupMouseClicked
        updateBookingStatus("Picked Up");
    }//GEN-LAST:event_pickupMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookingTable;
    private javax.swing.JPanel completed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pickup;
    // End of variables declaration//GEN-END:variables
}
