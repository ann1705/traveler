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
    fname.setText(sess.getFirstname() + " " + sess.getLastname());
    uname.setText(sess.getUsername());
    email.setText(sess.getEmail());
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
        fname = new javax.swing.JTextField();
        lname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        uname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        status = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        aid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        data = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        editprofile = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

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

        jPanel2.setBackground(new java.awt.Color(20, 20, 130));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(91, 100, 193));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(91, 100, 193));
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
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 170));

        fname.setBackground(new java.awt.Color(91, 100, 193));
        fname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        fname.setForeground(new java.awt.Color(255, 255, 255));
        fname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fname.setBorder(null);
        jPanel3.add(fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 220, 30));

        lname.setBackground(new java.awt.Color(204, 204, 255));
        lname.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lname.setBorder(null);
        lname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnameActionPerformed(evt);
            }
        });
        jPanel3.add(lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 110, 30));

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 240, 210));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("USERNAME: ");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 140, -1));

        uname.setBackground(new java.awt.Color(91, 100, 193));
        uname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        uname.setForeground(new java.awt.Color(255, 255, 255));
        uname.setBorder(null);
        uname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unameActionPerformed(evt);
            }
        });
        jPanel4.add(uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 270, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("EMAIL :");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 90, -1));

        email.setEditable(false);
        email.setBackground(new java.awt.Color(91, 100, 193));
        email.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setBorder(null);
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        jPanel4.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, 270, 30));

        status.setBackground(new java.awt.Color(91, 100, 193));
        status.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        status.setForeground(new java.awt.Color(255, 255, 255));
        status.setBorder(null);
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        jPanel4.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 190, 270, 30));

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("STATUS:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 80, -1));

        aid.setBackground(new java.awt.Color(91, 100, 193));
        aid.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        aid.setForeground(new java.awt.Color(255, 255, 255));
        aid.setBorder(null);
        jPanel4.add(aid, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 270, 30));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ACCOUNT ID:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 140, 30));

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/goback.png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        jPanel4.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 30, 60, 30));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 770, 280));

        data.setBackground(new java.awt.Color(91, 100, 193));
        data.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dataMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dataMouseExited(evt);
            }
        });
        data.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/data.png"))); // NOI18N
        data.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 170));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("DATA'S");
        data.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 240, -1));

        jPanel2.add(data, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, 240, 210));

        editprofile.setBackground(new java.awt.Color(91, 100, 193));
        editprofile.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        editprofile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editprofileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editprofileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editprofileMouseExited(evt);
            }
        });
        editprofile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editprofile.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 210));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("EDIT PROFILE");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        editprofile.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 240, -1));

        jPanel2.add(editprofile, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 240, 210));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void dataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataMouseEntered
        data.setBackground(new java.awt.Color(102, 102, 255));
    }//GEN-LAST:event_dataMouseEntered

    private void dataMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataMouseExited
        data.setBackground(new java.awt.Color(204,204,255));
    }//GEN-LAST:event_dataMouseExited

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked

    }//GEN-LAST:event_jLabel13MouseClicked

    private void editprofileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editprofileMouseClicked
                                        
   editprofile ep = new editprofile();
   ep.displayUserDetails(); // <--- THIS LOADS THE SESSION DATA
   ep.setVisible(true);
   this.dispose();

        
    }//GEN-LAST:event_editprofileMouseClicked

    private void editprofileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editprofileMouseEntered
        editprofile.setBackground(new java.awt.Color(102, 102, 255));
    }//GEN-LAST:event_editprofileMouseEntered

    private void editprofileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editprofileMouseExited
        editprofile.setBackground(new java.awt.Color(204,204,255));
    }//GEN-LAST:event_editprofileMouseExited

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
    private javax.swing.JPanel data;
    private javax.swing.JPanel editprofile;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField lname;
    private javax.swing.JTextField status;
    private javax.swing.JTextField uname;
    // End of variables declaration//GEN-END:variables
}
