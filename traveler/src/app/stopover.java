/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import config.Session;
import config.dbConnector;
import dashboard.adminDashboard;
import java.sql.Connection;

/**
 *
 * @author RageKing
 */
public class stopover extends javax.swing.JFrame {

    /**
     * Creates new form stopover
     */
    public stopover() {
        initComponents();
        saveStopoverData();
    }

   private void saveStopoverData() {
    // 1. Get the Session instance (to track the user if needed)
    Session sess = Session.getInstance();
    
    // 2. Validate Inputs
    String location = loc.getText().trim();
    String priceVal = price.getText().trim();

    if (location.isEmpty() || priceVal.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please fill in all fields!");
        return;
    }

    try {
        // 3. Get the Singleton DB Connection
        Connection conn = dbConnector.getInstance().getConnection();
        
        // 4. Prepare SQL Statement
        String sql = "INSERT INTO tbl_stopovers (location, price) VALUES (?, ?)";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        
        pst.setString(1, location);
        pst.setString(2, priceVal); // Or pst.setDouble if using numeric type

        // 5. Execute
        int rows = pst.executeUpdate();
        if (rows > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Stopover Saved Successfully!");
            
            // Navigate back to Dashboard
            adminDashboard ad = new adminDashboard();
            ad.setVisible(true);
            this.dispose();
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        save = new javax.swing.JPanel();
        savel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        loc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        stopid = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(76, 143, 209));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(76, 143, 209));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        save.setBackground(new java.awt.Color(204, 102, 255));
        save.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveMouseClicked(evt);
            }
        });
        save.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        savel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        savel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        savel.setText("SAVE");
        savel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        savel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savelMouseClicked(evt);
            }
        });
        save.add(savel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, 20));

        jPanel3.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 130, 40));

        jPanel4.setBackground(new java.awt.Color(76, 143, 209));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/goback.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 60, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 70));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setText("Location :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 130, -1));

        loc.setBackground(new java.awt.Color(204, 204, 255));
        loc.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        loc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(loc, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 240, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setText("Price :");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 110, -1));

        price.setBackground(new java.awt.Color(204, 204, 255));
        price.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        price.setToolTipText("");
        price.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 240, 30));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setText("Stopover ID:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 120, -1));

        stopid.setEditable(false);
        stopid.setBackground(new java.awt.Color(204, 204, 255));
        stopid.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        stopid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel3.add(stopid, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 240, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 330));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void savelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_savelMouseClicked

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked
       saveStopoverData();
      
    }//GEN-LAST:event_saveMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        adminDashboard ad = new adminDashboard();
        ad.setVisible(true);
        dispose();

    }//GEN-LAST:event_jLabel2MouseClicked

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
            java.util.logging.Logger.getLogger(stopover.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stopover.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stopover.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stopover.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new stopover().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField loc;
    private javax.swing.JTextField price;
    private javax.swing.JPanel save;
    private javax.swing.JLabel savel;
    private javax.swing.JTextField stopid;
    // End of variables declaration//GEN-END:variables
}
