/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import dashboard.customerDashboard;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RageKing
 */
public class bookingList extends javax.swing.JFrame {
    private String mode;
    private int xMouse, yMouse;
    
  
    
     
    public bookingList(String mode) {
        this.setUndecorated(true);
        this.mode = mode;
        initComponents();
        designTable();
        
        this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        
        jLabel2.setText(mode.equalsIgnoreCase("History") ? "BOOKING HISTORY" : "ACTIVE BOOKINGS");
        displayTable();
    }

    public bookingList() {
        this("Bookings");
    }
    
    
    private void designTable() {
        // 1. Style the Table Body
        bookingTable.setBackground(Color.WHITE);
        bookingTable.setGridColor(new Color(230, 230, 230));
        bookingTable.setRowHeight(35);
        bookingTable.setSelectionBackground(new Color(76, 143, 209));
        bookingTable.setSelectionForeground(Color.WHITE);
        bookingTable.setShowVerticalLines(false);
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        // 2. Style the Table Header
        javax.swing.table.JTableHeader header = bookingTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setReorderingAllowed(false);

        // Force the [12, 33, 74] color on the header
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(new Color(12, 33, 74));
                setForeground(Color.WHITE);
                setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(255, 255, 255, 50)));
                setHorizontalAlignment(javax.swing.JLabel.CENTER);
                return this;
            }
        });

        // 3. Center align cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        bookingTable.setDefaultRenderer(Object.class, centerRenderer);
    }
    @SuppressWarnings("unchecked")
    
    
public void displayTable() {
        try {
            config.dbConnector connector = config.dbConnector.getInstance();
            int sessionID = config.Session.getInstance().getId();
            
            DefaultTableModel model = (DefaultTableModel) bookingTable.getModel();
            model.setColumnIdentifiers(new String[]{"Booking ID", "Route", "Status", "Total Price"});
            model.setRowCount(0); 

            String statusFilter;
            if (mode.equalsIgnoreCase("History")) {
                // HISTORY: Only show finished or terminated bookings
                statusFilter = "IN ('Completed', 'Cancelled')";
            } else {
                // ACTIVE: Show everything else. 
                // This includes Pending, Booked, Assigned, and any intermediate states.
                statusFilter = "NOT IN ('Completed', 'Cancelled')";
            }

            String query = "SELECT b_id, pick_up, destination, status, total_price FROM tbl_bookings " +
                           "WHERE a_id = " + sessionID + " AND status " + statusFilter + 
                           " ORDER BY b_id DESC";

            java.sql.ResultSet rs = connector.getData(query);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("b_id"),
                    rs.getString("pick_up") + " to " + rs.getString("destination"),
                    rs.getString("status"),
                    "₱" + String.format("%.2f", rs.getDouble("total_price"))
                });
            }
        } catch (java.sql.SQLException ex) {
            System.out.println("Database Error: " + ex.getMessage());
        }
    }
private void openDetails(int bookingId) {
    config.Session sess = config.Session.getInstance();
    config.dbConnector connector = config.dbConnector.getInstance();
    
    try {
        // 1. Fetch Main Booking Data
        String bookingQuery = "SELECT * FROM tbl_bookings WHERE b_id = " + bookingId;
        java.sql.ResultSet rs = connector.getData(bookingQuery);
        
        if (rs.next()) {
            String bid = String.valueOf(rs.getInt("b_id"));
            String fullName = sess.getFirstname() + " " + sess.getLastname();
            String pUp = rs.getString("pick_up");
            String dest = rs.getString("destination");
            String sDate = rs.getString("start_date");
            String eDate = rs.getString("end_date");
            String tPrice = "₱" + String.format("%.2f", rs.getDouble("total_price"));
            String stat = rs.getString("status");

                // Initialize the Details view with the fetched data
            app.BookingDetails details = new app.BookingDetails(
                    bid, fullName, pUp, dest, sDate, eDate, tPrice, stat
            );
            
            details.setVisible(true);
            this.dispose();
        }
    } catch (java.sql.SQLException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error fetching details: " + ex.getMessage());
    }
}
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookingTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jPanel2.setBackground(new java.awt.Color(12, 33, 74));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/back_white.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 60, 40));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BOOKINGS");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 550, 30));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minimize_white.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel2.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 10, 40, 40));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close_white.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        jPanel2.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 40, 50));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 60));

        bookingTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        bookingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        bookingTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bookingTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 870, 420));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bookingTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingTableMouseClicked
                                             
    int row = bookingTable.getSelectedRow();
    if (row != -1) {
        int bid = Integer.parseInt(bookingTable.getValueAt(row, 0).toString());
        openDetails(bid); 
    }


    }//GEN-LAST:event_bookingTableMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
       customerDashboard cd = new customerDashboard();
       cd.setVisible(true);
       dispose();        
    }//GEN-LAST:event_jLabel1MouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
      int confirm = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to exit?", "Exit Confirmation", 
            javax.swing.JOptionPane.YES_NO_OPTION);
            
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        System.exit(0);
    }
    }//GEN-LAST:event_closeMouseClicked

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        this.setState(ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        xMouse = evt.getX();
       yMouse = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
    int x = evt.getXOnScreen();
    int y = evt.getYOnScreen();
    this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel1MouseDragged

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
            java.util.logging.Logger.getLogger(bookingList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bookingList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bookingList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bookingList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bookingList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookingTable;
    private javax.swing.JLabel close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel minimize;
    // End of variables declaration//GEN-END:variables
}
