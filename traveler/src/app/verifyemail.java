/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author RageKing
 */
public class verifyemail extends javax.swing.JFrame {
   private int xMouse, yMouse;
   
    public verifyemail() {
        
         this.setUndecorated(true);
        initComponents();
        
        this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
    }

    
    
  

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        header = new javax.swing.JLabel();
        go = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        em = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(76, 143, 209));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("Forgot Password");
        jPanel3.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 530, 50));

        go.setBackground(new java.awt.Color(12, 33, 74));
        go.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                goMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                goMouseExited(evt);
            }
        });
        go.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Continue");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        go.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 9, 220, 20));

        jPanel3.add(go, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 230, 40));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minimizeMousePressed(evt);
            }
        });
        jPanel3.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 30, 30));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                closeMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                closeMouseReleased(evt);
            }
        });
        jPanel3.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 40, 40));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Enter the email associated with your account.");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 510, 40));

        em.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel3.add(em, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 400, 40));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setText("Email:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 40, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("Remember Password? ");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 150, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setText("Login");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 50, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 540, 550));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logowhite.png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 550));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 550));

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

    private void goMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goMouseClicked
                                 
                                      
    String email = em.getText().trim();
    
    if (email.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please enter your email address.");
        return;
    }

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        // Ensure the column name 'email' matches your DB exactly
        String query = "SELECT * FROM tbl_account WHERE email = '" + email + "'";
        java.sql.ResultSet rs = connector.getData(query);

        if (rs != null && rs.next()) {
            // Success: A row was found
            javax.swing.JOptionPane.showMessageDialog(this, "Account verified. Proceeding to reset password.");
            
            forgotpass fp = new forgotpass(email);
            fp.setVisible(true);
            this.dispose(); 
        } else {
            // Fail: rs.next() returned false (No such email)
            javax.swing.JOptionPane.showMessageDialog(this, "Email not found in our records. Please check your input.");
        }
    } catch (java.sql.SQLException e) {
        System.out.println("Database Error: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(this, "A database error occurred.");
    }

    }//GEN-LAST:event_goMouseClicked

    private void goMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goMouseEntered
        go.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_goMouseEntered

    private void goMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goMouseExited
        go.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_goMouseExited

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        this.setState(ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void minimizeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_minimizeMousePressed

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked

        int confirm = javax.swing.JOptionPane.showConfirmDialog(null,
            "Are you sure you want to exit?", "Exit Confirmation",
            javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_closeMouseClicked

    private void closeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMousePressed

        close.setOpaque(true);
        close.setBackground(java.awt.Color.RED);
        close.setForeground(java.awt.Color.WHITE);
    }//GEN-LAST:event_closeMousePressed

    private void closeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseReleased
        close.setForeground(java.awt.Color.BLACK);
    }//GEN-LAST:event_closeMouseReleased

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        loginForm lf = new loginForm();
        lf.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        goMouseClicked(null);
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
            java.util.logging.Logger.getLogger(verifyemail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verifyemail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verifyemail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verifyemail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new verifyemail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel close;
    private javax.swing.JTextField em;
    private javax.swing.JPanel go;
    private javax.swing.JLabel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel minimize;
    // End of variables declaration//GEN-END:variables
}
