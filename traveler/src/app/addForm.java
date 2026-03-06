/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import config.passHasher;
import dashboard.adminDashboard;
import internalPages.usersTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author RageKing
 */
public class addForm extends javax.swing.JFrame {

    /**
     * Creates new form addForm
     */
    public addForm() {
        initComponents();
        
    }

     public void clearFields() {
    finame.setText("");
    laname.setText("");
    em.setText("");
    uname.setText("");
    pass.setText("");
    acctype.setSelectedIndex(0);
}
  
   int validateRegister() {
        if (finame.getText().isEmpty() || laname.getText().isEmpty() 
            || em.getText().isEmpty() || uname.getText().isEmpty() 
            || new String(pass.getPassword()).isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }
   
  public boolean duplicateCheck() {
   // This calls the static method we created
    config.dbConnector connector = config.dbConnector.getInstance();
    ResultSet rs = null;
    try {
        // Use a single query to check both
        String query = "SELECT * FROM tbl_account WHERE username = '" + uname.getText() 
                     + "' OR email = '" + em.getText() + "'";
        
        rs = connector.getData(query);
        
        if (rs.next()) {
            String existingEmail = rs.getString("email");
            String existingUname = rs.getString("username");
            
            if (existingEmail.equals(em.getText())) {
                JOptionPane.showMessageDialog(null, "Email is already taken!");
            } else if (existingUname.equals(uname.getText())) {
                JOptionPane.showMessageDialog(null, "Username is already taken!");
            }
            return true;
        }
        return false;
        
    } catch (SQLException ex) {
        System.out.println("Validation Error: " + ex.getMessage());
        return false;
    } finally {
        // CRITICAL: This ensures the database is not 'Busy' for the next operation
        try {
            if (rs != null) {
                if (rs.getStatement() != null) rs.getStatement().close();
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Closing Error: " + e.getMessage());
        }
    }
}
   public void close(){
    this.dispose();
    adminDashboard ad = new adminDashboard();
    ad.setVisible(true);
    
    usersTable ut = new usersTable();
    // Use the getter method instead of direct access
    ad.getMainPane().add(ut).setVisible(true);
}
  
 
  
    @SuppressWarnings("unchecked")
     private boolean isViewed = false;
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        save = new javax.swing.JPanel();
        savel = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        acctype = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        finame = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        laname = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        userid = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        uname = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        em = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pass = new javax.swing.JPasswordField();

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

        jPanel3.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, 130, 40));

        label.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        label.setText("Account Type :");
        jPanel3.add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 150, 20));

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

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 70));

        acctype.setEditable(true);
        acctype.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        acctype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Driver", "Customer", "Admin" }));
        acctype.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(acctype, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 240, 30));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setText("First Name :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 130, -1));

        finame.setBackground(new java.awt.Color(204, 204, 255));
        finame.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        finame.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(finame, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 240, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setText("Last Name:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 110, -1));

        laname.setBackground(new java.awt.Color(204, 204, 255));
        laname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        laname.setToolTipText("");
        laname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(laname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 240, 30));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setText("User ID:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 90, -1));

        userid.setEditable(false);
        userid.setBackground(new java.awt.Color(204, 204, 255));
        userid.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        userid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel3.add(userid, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 240, 30));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel11.setText("UserName:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        uname.setBackground(new java.awt.Color(204, 204, 255));
        uname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        uname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 240, 30));

        jLabel12.setBackground(new java.awt.Color(204, 204, 255));
        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel12.setText("Password :");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 110, -1));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Email:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        em.setBackground(new java.awt.Color(204, 204, 255));
        em.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        em.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(em, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 240, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eyeee.png"))); // NOI18N
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 390, 30, 30));

        pass.setBackground(new java.awt.Color(204, 204, 255));
        pass.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        pass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 240, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 510));

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

    private void savelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_savelMouseClicked

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked

        if (validateRegister() == 0) {
            JOptionPane.showMessageDialog(null, "All fields are required!");
            return;
        }

        // duplicateCheck now handles its own closing in 'finally' block
        if (duplicateCheck()) {
            return;
        }

        try {
            String hashedPass = passHasher.hashPassword(new String(pass.getPassword()));
            String selectedType = acctype.getSelectedItem().toString();
            // This calls the static method we created
            config.dbConnector connector = config.dbConnector.getInstance();

            String sql = "INSERT INTO tbl_account (firstname, lastname, email, username, password, type, status) "
            + "VALUES ('" + finame.getText() + "', '"
            + laname.getText() + "', '"
            + em.getText() + "', '"
            + uname.getText() + "', '"
            + hashedPass + "', '"
            + selectedType + "', 'Active')";

            if (connector.insertData(sql) == 1) {
                JOptionPane.showMessageDialog(null, "Account Created Successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Connection Error!");
            }
        } catch (Exception ex) {
            System.out.println("Insertion Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_saveMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
    close();
    
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if (isViewed) {
        pass.setEchoChar('*'); // Hide password
        isViewed = false;
    } else {
        pass.setEchoChar((char) 0); // Show password
        isViewed = true;
    }
    }//GEN-LAST:event_jLabel3MouseClicked

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
            java.util.logging.Logger.getLogger(addForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> acctype;
    private javax.swing.JTextField em;
    private javax.swing.JTextField finame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel label;
    private javax.swing.JTextField laname;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPanel save;
    private javax.swing.JLabel savel;
    private javax.swing.JTextField uname;
    private javax.swing.JTextField userid;
    // End of variables declaration//GEN-END:variables
}
