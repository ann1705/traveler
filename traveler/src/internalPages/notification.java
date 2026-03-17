/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPages;


import config.dbConnector;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RageKing
 */
public class notification extends javax.swing.JInternalFrame {

    /**
     * Creates new form notification
     */
    public notification() {
        initComponents();
    }

public void displayInbox(int customerID) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        // We add two columns: one for display and one hidden for the ID
        model.addColumn("Notification"); 
        model.addColumn("b_id"); // Hidden column for logic

        try {
            dbConnector connector = dbConnector.getInstance();
            // Using a_id as the customer link based on your schema
            String query = "SELECT n_id, b_id, message, status FROM tbl_notifications "
                         + "WHERE a_id = '" + customerID + "' ORDER BY n_id DESC";
            
            ResultSet rs = connector.getData(query);
            while (rs.next()) {
                String prefix = rs.getString("status").equals("Unread") ? "• " : "  ";
                String msg = prefix + rs.getString("message") + " (Booking #" + rs.getString("b_id") + ")";
                
                model.addRow(new Object[]{
                    msg,
                    rs.getString("b_id") // Keep the ID handy for clicking
                });
            }
            inboxTable.setModel(model);
            styleInbox();
            
            // Hide the ID column from the user
            inboxTable.getColumnModel().getColumn(1).setMinWidth(0);
            inboxTable.getColumnModel().getColumn(1).setMaxWidth(0);
            inboxTable.getColumnModel().getColumn(1).setPreferredWidth(0);
            
        } catch (Exception e) { System.out.println("Inbox Error: " + e.getMessage()); }
    }

    private void styleInbox() {
        inboxTable.setBackground(java.awt.Color.decode("#03294E"));
        inboxTable.setForeground(java.awt.Color.WHITE);
        inboxTable.setRowHeight(60); 
        inboxTable.setShowGrid(false);
        inboxTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        inboxTable.setTableHeader(null); 
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inboxTable = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(20, 20, 130));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inboxTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        inboxTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        inboxTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inboxTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(inboxTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inboxTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inboxTableMouseClicked
   int row = inboxTable.getSelectedRow();
        if (row == -1) return;

        // Retrieve the hidden b_id
        String bookingID = inboxTable.getValueAt(row, 1).toString();

        try {
            dbConnector connector = dbConnector.getInstance();
            
            // 1. Mark notification as Read in the DB
            connector.updateData("UPDATE tbl_notifications SET status = 'Read' WHERE b_id = '" + bookingID + "'");

            // 2. Fetch current status of the booking to pass to the next frame
            String query = "SELECT status FROM tbl_bookings WHERE b_id = '" + bookingID + "'";
            ResultSet rs = connector.getData(query);

            if (rs.next()) {
                String currentStat = rs.getString("status");

                // 3. Open BookingDetails with the simplified constructor we made earlier
               // BookingDetails bd = new BookingDetails(bookingID, currentStat);
                //bd.setVisible(true);
                
                // Close the internal frame or the parent if needed
                this.getDesktopPane().getTopLevelAncestor().setVisible(false);
            }
        } catch (Exception e) {
            System.out.println("Redirect Error: " + e.getMessage());
        }
    
 // Close inbox

    }//GEN-LAST:event_inboxTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable inboxTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
