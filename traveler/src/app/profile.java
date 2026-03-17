/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import dashboard.adminDashboard;
import dashboard.customerDashboard;
import dashboard.driverDashboard;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author RageKing
 */
public class profile extends javax.swing.JFrame {

      private String userRole;

    
    public profile(String role) {
        this.setUndecorated(true);
        initComponents();
        this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        this.userRole = role;
    }
    
    // Keep a default constructor just in case, or remove it to force role-passing
    public profile() {
        initComponents();
    }
    
    private void refreshProfileDisplay() {
    config.Session sess = config.Session.getInstance();
    
    // Display updated data
    aid.setText("" + sess.getId());
    fname.setText(sess.getFirstname());
    lname.setText (sess.getLastname());
    uname.setText(sess.getUsername());
    email.setText(sess.getEmail());
    type.setText(sess.getType());
    status.setText(sess.getStatus());
    
    // Update the Profile Picture (jLabel1)
    if(sess.getImage() != null){
        ImageIcon micon = new ImageIcon(sess.getImage());
        Image img = micon.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(img));
    }
}
    
   
  

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        aid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        lname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        uname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        type = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        status = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        back = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(76, 143, 209));
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/person.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 210));

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 210));

        aid.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        aid.setBorder(null);
        jPanel4.add(aid, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 100, 30));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setText("Account ID:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 140, 30));

        fname.setEditable(false);
        fname.setBackground(new java.awt.Color(216, 236, 236));
        fname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        fname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fname.setBorder(null);
        jPanel4.add(fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 230, 30));

        lname.setBackground(new java.awt.Color(216, 236, 236));
        lname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lname.setBorder(null);
        lname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnameActionPerformed(evt);
            }
        });
        jPanel4.add(lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 220, 30));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("First Name");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText(" Last Name");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 100, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setText("Username");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 140, -1));

        uname.setBackground(new java.awt.Color(216, 236, 236));
        uname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        uname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        uname.setBorder(null);
        uname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unameActionPerformed(evt);
            }
        });
        jPanel4.add(uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 230, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setText("Email");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 90, -1));

        email.setEditable(false);
        email.setBackground(new java.awt.Color(216, 236, 236));
        email.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        email.setBorder(null);
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        jPanel4.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 190, 230, 30));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setText("Role");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 60, -1));

        type.setEditable(false);
        type.setBackground(new java.awt.Color(216, 236, 236));
        type.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        type.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        type.setBorder(null);
        jPanel4.add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 230, 30));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel14.setText("Status");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 250, 80, -1));

        status.setEditable(false);
        status.setBackground(new java.awt.Color(216, 236, 236));
        status.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        status.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        status.setBorder(null);
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        jPanel4.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 280, 230, 30));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/addphoto (2).png"))); // NOI18N
        jLabel9.setText(" Edit Profile");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 130, 40));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 250, 40));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 810, 330));

        jPanel5.setBackground(new java.awt.Color(12, 33, 74));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/back_white.png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        jPanel5.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 60, 30));
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 14, 310, 30));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 390));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private int xMouse, yMouse;
      
    private void lnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnameActionPerformed
       
    }//GEN-LAST:event_lnameActionPerformed

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
         //jPanel3.setBackground(new java.awt.Color(102, 102, 255));
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        //jPanel3.setBackground(new java.awt.Color(204,204,255));      // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseExited

    private void unameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unameActionPerformed

    }//GEN-LAST:event_unameActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
                                
    refreshProfileDisplay();
    
    // Best practice: Keep these uneditable in the "View Only" profile frame
    aid.setEditable(false);
    fname.setEditable(false);
    uname.setEditable(false);
    email.setEditable(false);
    status.setEditable(false);
    

    }//GEN-LAST:event_formWindowOpened

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
                                    
    config.Session sess = config.Session.getInstance();
    
    // IMPORTANT: Use getType() instead of getStatus()
    String role = sess.getType(); 
    
    // Debugging line: Look at your output console to see what is actually being retrieved
    System.out.println("Debugging Back Button - Role found: " + role);

    if (role == null || role.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No Session Role Found. Returning to login.");
        // new loginForm().setVisible(true); // Uncomment this if you have a login frame
        this.dispose();
        return;
    }

    this.dispose(); 
    
    // Using equalsIgnoreCase to prevent case-sensitive errors
    if (role.equalsIgnoreCase("Admin")) {
        new adminDashboard().setVisible(true);
    } else if (role.equalsIgnoreCase("Customer")) {
        new customerDashboard().setVisible(true);
    } else if (role.equalsIgnoreCase("Driver")) {
        new driverDashboard().setVisible(true);
    } else {
        // This triggers if the role is something like "Active" or "User" instead of the 3 above
        JOptionPane.showMessageDialog(null, "Role '" + role + "' not recognized. Returning to login.");
    }

    }//GEN-LAST:event_backMouseClicked

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
      xMouse = evt.getX();
      yMouse = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
    int x = evt.getXOnScreen();
    int y = evt.getYOnScreen();
    this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
    editprofile ep = new editprofile();
   ep.displayUserDetails(); // <--- THIS LOADS THE SESSION DATA
   ep.setVisible(true);
   this.dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

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
            java.util.logging.Logger.getLogger(profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aid;
    private javax.swing.JLabel back;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField status;
    private javax.swing.JTextField type;
    private javax.swing.JTextField uname;
    // End of variables declaration//GEN-END:variables
}
