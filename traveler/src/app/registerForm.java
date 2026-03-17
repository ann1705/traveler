/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import config.passHasher;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static org.eclipse.persistence.internal.helper.Helper.close;



public class registerForm extends javax.swing.JFrame {
     private boolean isViewed1 = false;
     private boolean isViewed2 = false;

  
   
    public registerForm() {
        this.setUndecorated(true);
        
        initComponents();
        
        this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
    }

    int validateRegister() {
        if (firstname.getText().isEmpty() || lastname.getText().isEmpty() 
            || email.getText().isEmpty() || username.getText().isEmpty() 
            || new String(ps.getPassword()).isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }
   
  public boolean duplicateCheck() {
    config.dbConnector connector = config.dbConnector.getInstance();
    try {
        // We check both username and email
        String query = "SELECT username, email FROM tbl_account WHERE username = '" 
                     + username.getText() + "' OR email = '" + email.getText() + "'";
        ResultSet rs = connector.getData(query);

        if (rs.next()) {
            String existingEmail = rs.getString("email");
            if (existingEmail.equalsIgnoreCase(email.getText())) {
                JOptionPane.showMessageDialog(null, "Email is already used!");
                return true; // Duplicate found
            }
            
            String existingUser = rs.getString("username");
            if (existingUser.equalsIgnoreCase(username.getText())) {
                JOptionPane.showMessageDialog(null, "Username is already taken!");
                return true; // Duplicate found
            }
        }
        return false; // No duplicates
    } catch (SQLException ex) {
        System.out.println("Validation Error: " + ex.getMessage());
        return false;
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        header = new javax.swing.JLabel();
        firstname = new javax.swing.JTextField();
        lastname = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        eye1 = new javax.swing.JLabel();
        ps = new javax.swing.JPasswordField();
        cancel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        register = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        eye2 = new javax.swing.JLabel();
        ps_error = new javax.swing.JLabel();
        confps = new javax.swing.JPasswordField();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        strengthBar = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jPanel2.setBackground(new java.awt.Color(12, 33, 74));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("REGISTRATION FORM");
        jPanel3.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 540, 50));

        firstname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        firstname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        firstname.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "First Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 18))); // NOI18N
        jPanel3.add(firstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 390, 60));

        lastname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lastname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lastname.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Last Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 18))); // NOI18N
        jPanel3.add(lastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 390, 60));

        email.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        email.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Email", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 18)), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
        jPanel3.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 390, 60));

        username.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        username.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Username", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 18))); // NOI18N
        jPanel3.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 390, 60));

        eye1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eye1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eyeee.png"))); // NOI18N
        eye1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        eye1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eye1MouseClicked(evt);
            }
        });
        jPanel3.add(eye1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 40, 40));

        ps.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        ps.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 18))); // NOI18N
        ps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                psKeyReleased(evt);
            }
        });
        jPanel3.add(ps, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 390, 60));

        cancel.setBackground(new java.awt.Color(12, 33, 74));
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelMouseExited(evt);
            }
        });
        cancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CANCEL");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 140, 20));

        jPanel3.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 590, 140, 40));

        register.setBackground(new java.awt.Color(12, 33, 74));
        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerMouseExited(evt);
            }
        });
        register.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("REGISTER");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        register.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 150, 20));

        jPanel3.add(register, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 590, 150, 40));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Already Have an Account? Click Here to Login");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 650, 410, 40));

        eye2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eyeee.png"))); // NOI18N
        eye2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eye2MouseClicked(evt);
            }
        });
        jPanel3.add(eye2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 500, 50, 40));

        ps_error.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ps_error.setForeground(new java.awt.Color(211, 18, 18));
        ps_error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(ps_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, 380, 20));

        confps.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        confps.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Confirm Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 18))); // NOI18N
        confps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                confpsKeyReleased(evt);
            }
        });
        jPanel3.add(confps, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, 390, 60));

        minimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minimize.png"))); // NOI18N
        minimize.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeMouseExited(evt);
            }
        });
        jPanel3.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 30, 30));

        close.setBackground(new java.awt.Color(204, 0, 0));
        close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                closeMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                closeMouseReleased(evt);
            }
        });
        jPanel3.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 40, 40));

        strengthBar.setBackground(new java.awt.Color(204, 204, 255));
        strengthBar.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        strengthBar.setForeground(new java.awt.Color(255, 255, 255));
        strengthBar.setStringPainted(true);
        jPanel3.add(strengthBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 450, 80, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 540, 720));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logowhite.png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 4, 520, 710));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
    
private void checkPasswordMatch() {
    String p1 = new String(ps.getPassword());
    String p2 = new String(confps.getPassword());

    if (p1.isEmpty() || p2.isEmpty()) {
        ps_error.setText(""); 
    } else if (!p1.equals(p2)) {
        ps_error.setForeground(java.awt.Color.RED);
        ps_error.setText("Passwords do not match!");
    } else {
        ps_error.setForeground(new java.awt.Color(0, 153, 0)); // Green color
        ps_error.setText("Passwords match.");
    }
}
    private void resetFields() {
   
    firstname.setText("");
    lastname.setText("");
    email.setText("");
    username.setText("");
    
    ps.setText("");
    confps.setText("");
    
    ps_error.setText(" ");
     
    firstname.requestFocus();
}
    
    private void checkStrength(String password) {
    int score = 0;
    
    if (password.length() >= 6) score += 40;
    if (password.length() >= 10) score += 20;
    if (password.matches(".*[A-Z].*")) score += 20; // Has Uppercase
    if (password.matches(".*[0-9].*")) score += 20; // Has Number

    strengthBar.setValue(score);

    if (score <= 40) {
        strengthBar.setForeground(java.awt.Color.RED);
        strengthBar.setString("Weak");
    } else if (score <= 70) {
        strengthBar.setForeground(java.awt.Color.ORANGE);
        strengthBar.setString("Medium");
    } else {
        strengthBar.setForeground(new java.awt.Color(0, 153, 0)); // Green
        strengthBar.setString("Strong");
    }
}
    
    private void registerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseClicked
                                  
    String p1 = new String(ps.getPassword());
    String p2 = new String(confps.getPassword());

    
    if (validateRegister() == 0 || p2.isEmpty()) {
        JOptionPane.showMessageDialog(null, "All fields are required!");
    } 
  
    else if (!p1.equals(p2)) {
        JOptionPane.showMessageDialog(null, "Passwords do not match!");
    } 
   
    else if (duplicateCheck()) {
      
    } 
    else {
        try {
           
            String hashedPass = config.passHasher.hashPassword(p1); 

            config.dbConnector connector = config.dbConnector.getInstance();

            String sql = "INSERT INTO tbl_account (firstname, lastname, email, username, password, type, status) "
                       + "VALUES ('" + firstname.getText() + "', '"
                       + lastname.getText() + "', '"
                       + email.getText() + "', '"
                       + username.getText() + "', '"
                       + hashedPass + "', 'Customer', 'Active')";

            if (connector.insertData(sql) > 0) { 
                JOptionPane.showMessageDialog(null, "Successfully Registered!");
                new loginForm().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Saving Data Failed!");
            }
        } catch (Exception ex) {
            System.out.println("Registration Logic Error: " + ex.getMessage());
        }
    }

    



    }//GEN-LAST:event_registerMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        loginForm lf = new loginForm();
        lf.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void eye1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eye1MouseClicked
       if (isViewed1) {
            ps.setEchoChar('•');
            isViewed1 = false;
        } else {
            ps.setEchoChar((char) 0);
            isViewed1 = true;
        }
    }//GEN-LAST:event_eye1MouseClicked

    private void confpsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confpsKeyReleased
      checkPasswordMatch();
    }//GEN-LAST:event_confpsKeyReleased

    private void eye2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eye2MouseClicked
  if (isViewed2) {
            confps.setEchoChar('•');
            isViewed2 = false;
        } else {
            confps.setEchoChar((char) 0);
            isViewed2 = true;
        }
    }//GEN-LAST:event_eye2MouseClicked

    private void psKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_psKeyReleased
       String password = new String(ps.getPassword());
       checkStrength(password);
        
        checkPasswordMatch();
    }//GEN-LAST:event_psKeyReleased

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked

    resetFields();
    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel registration?", "Confirm", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        new loginForm().setVisible(true);
        this.dispose();
    }

    }//GEN-LAST:event_cancelMouseClicked

    private void cancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseEntered
        cancel.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_cancelMouseEntered

    private void cancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseExited
      cancel.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_cancelMouseExited

    private void registerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseEntered
        register.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_registerMouseEntered

    private void registerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseExited
        register.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_registerMouseExited

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
     xMouse = evt.getX();
     yMouse = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
    int x = evt.getXOnScreen();
    int y = evt.getYOnScreen();
    this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
     int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        System.exit(0); // Closes the entire application
    }
    }//GEN-LAST:event_closeMouseClicked

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
       this.setState(ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
     xMouse = evt.getX();
    yMouse = evt.getY();
    }//GEN-LAST:event_jPanel3MousePressed

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
    int x = evt.getXOnScreen();
    int y = evt.getYOnScreen();
    this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel3MouseDragged

    private void closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseEntered
  
    }//GEN-LAST:event_closeMouseEntered

    private void closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseExited
    
    }//GEN-LAST:event_closeMouseExited

    private void minimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseEntered
       minimize.setForeground(java.awt.Color.GRAY);
    }//GEN-LAST:event_minimizeMouseEntered

    private void minimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseExited
        minimize.setForeground(java.awt.Color.BLACK);
    }//GEN-LAST:event_minimizeMouseExited

    private void closeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMousePressed
    close.setOpaque(true);
    close.setBackground(java.awt.Color.RED);
    close.setForeground(java.awt.Color.WHITE);
    }//GEN-LAST:event_closeMousePressed

    private void closeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseReleased
       close.setForeground(java.awt.Color.BLACK);
    }//GEN-LAST:event_closeMouseReleased

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
            java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registerForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cancel;
    private javax.swing.JLabel close;
    private javax.swing.JPasswordField confps;
    private javax.swing.JTextField email;
    private javax.swing.JLabel eye1;
    private javax.swing.JLabel eye2;
    private javax.swing.JTextField firstname;
    private javax.swing.JLabel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField lastname;
    private javax.swing.JLabel minimize;
    private javax.swing.JPasswordField ps;
    private javax.swing.JLabel ps_error;
    private javax.swing.JPanel register;
    private javax.swing.JProgressBar strengthBar;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
