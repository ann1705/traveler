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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author RageKing
 */
public class editprofile extends javax.swing.JFrame {
    public File selectedFile;
    public String destinationPath;
    public String oldPath;
    private boolean isViewed1 = false;
    private boolean isViewed2 = false;
    public boolean isEditingOther = false; // Tracks if Admin is managing another user
    public String targetID;                // The ID of the user being edited
    
    
    public editprofile() {
        initComponents();
        displayUserDetails();
    }
public void populateForAdmin(String id, String fn, String ln, String un, String em, String st) {
    this.isEditingOther = true; 
    this.targetID = id;

    aid.setText(id);
    fname.setText(fn);
    lname.setText(ln);
    uname.setText(un);
    email.setText(em);
    status.setText(st);
    
    // Admins need to be able to change status to 'Inactive'
    status.setEditable(true);
    status.setEnabled(true);
}
private void displayUserDetails() {
        config.Session sess = config.Session.getInstance();
        
        // Populate Fields from Singleton
        aid.setText(String.valueOf(sess.getId()));
        fname.setText(sess.getFirstname());
        lname.setText(sess.getLastname());
        uname.setText(sess.getUsername());
        email.setText(sess.getEmail());
        contact.setText(sess.getContact());
        add.setText(sess.getAddress());
        gender.setSelectedItem(sess.getGender());
        
        // --- ROLE-BASED STATUS LOGIC ---
        status.setText(sess.getStatus());
        // Only allow status editing if the user is an Admin
        if (sess.getType() != null && sess.getType().equalsIgnoreCase("Admin")) {
            status.setEditable(true);
            status.setEnabled(true);
        } else {
            status.setEditable(false);
            status.setEnabled(false); // Visually locks the field for Customers/Drivers
        }
        
        // Load Profile Picture from path stored in Session
        if (sess.getImage() != null && !sess.getImage().isEmpty()) {
            destinationPath = sess.getImage();
            updateImagePreview(destinationPath);
        }
        uname.setEditable(true);
        email.setEditable(true);
    }

private void updateImagePreview(String path) {
        try {
            ImageIcon micon = new ImageIcon(path);
            Image img = micon.getImage().getScaledInstance(icon1.getWidth(), icon1.getHeight(), Image.SCALE_SMOOTH);
            icon1.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Image Preview Error: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        icon1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        aid = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        uname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        status = new javax.swing.JTextField();
        change = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        save = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        gender = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        address = new javax.swing.JScrollPane();
        add = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        contact = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        eye1 = new javax.swing.JLabel();
        newpass = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        eye2 = new javax.swing.JLabel();
        confirmpass = new javax.swing.JPasswordField();
        cancel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(154, 154, 238));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, true));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        icon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/person.png"))); // NOI18N
        icon1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        icon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icon1MouseClicked(evt);
            }
        });
        jPanel4.add(icon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 210));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 280, 240, 210));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setText("Account ID :");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 120, 30));

        aid.setEditable(false);
        aid.setBackground(new java.awt.Color(154, 154, 238));
        aid.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        aid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(aid, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 230, 30));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("First Name :");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 120, 30));

        fname.setBackground(new java.awt.Color(154, 154, 238));
        fname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        fname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 230, 30));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setText("Last Name :");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 120, 30));

        lname.setBackground(new java.awt.Color(154, 154, 238));
        lname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 230, 30));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setText("Username :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 120, 30));

        uname.setEditable(false);
        uname.setBackground(new java.awt.Color(154, 154, 238));
        uname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        uname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        uname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unameActionPerformed(evt);
            }
        });
        jPanel3.add(uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 230, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setText("Email :");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 120, 30));

        email.setBackground(new java.awt.Color(154, 154, 238));
        email.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 230, 30));

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel14.setText("Status :");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 120, 30));

        status.setBackground(new java.awt.Color(154, 154, 238));
        status.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        status.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 230, 30));

        change.setBackground(new java.awt.Color(204, 102, 255));
        change.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        change.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeMouseClicked(evt);
            }
        });
        change.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CHANGE");
        change.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 30));

        jPanel3.add(change, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 530, 150, 40));

        save.setBackground(new java.awt.Color(204, 102, 255));
        save.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveMouseClicked(evt);
            }
        });
        save.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("SAVE");
        save.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 30));

        jPanel3.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 580, 150, 40));

        jPanel5.setBackground(new java.awt.Color(76, 143, 209));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 70));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel7.setText("Gender :");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 120, 30));

        gender.setEditable(true);
        gender.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        gender.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 230, 30));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setText("Address :");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 84, 110, 30));

        add.setColumns(20);
        add.setRows(5);
        address.setViewportView(add);

        jPanel3.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 280, -1));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel11.setText("Contact :");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 120, 30));

        contact.setBackground(new java.awt.Color(154, 154, 238));
        contact.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        contact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 230, 30));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel12.setText("CHANGE PHOTO");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 490, -1, 30));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Change Password : ");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, 190, -1));

        eye1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eye1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eyeee.png"))); // NOI18N
        eye1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eye1MouseClicked(evt);
            }
        });
        jPanel3.add(eye1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 190, 30, 30));

        newpass.setBackground(new java.awt.Color(154, 154, 238));
        newpass.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        newpass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(newpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, 220, 30));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel13.setText("Confrim Password :");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, 180, -1));

        eye2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eye2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eyeee.png"))); // NOI18N
        eye2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eye2MouseClicked(evt);
            }
        });
        jPanel3.add(eye2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 244, 30, 20));

        confirmpass.setBackground(new java.awt.Color(154, 154, 238));
        confirmpass.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        confirmpass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(confirmpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 240, 220, 30));

        cancel.setBackground(new java.awt.Color(204, 102, 255));
        cancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });
        cancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("CANCEL");
        cancel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 7, 120, 30));

        jPanel3.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 460, 130, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void icon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icon1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_icon1MouseClicked

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseExited

    private void unameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unameActionPerformed

    private void changeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeMouseClicked
                                     
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) { 
            selectedFile = fileChooser.getSelectedFile();
            updateImagePreview(selectedFile.getAbsolutePath());
        
        }
    
    }//GEN-LAST:event_changeMouseClicked

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked
                                 
    config.Session sess = config.Session.getInstance();
    config.dbConnector connector = config.dbConnector.getInstance();
    
    // Determine the target: Are we editing someone else (Admin mode) or ourselves?
    String currentTargetID = isEditingOther ? targetID : String.valueOf(sess.getId());

    try {
        // 1. SECURITY CHECK: Prevent Admin from deactivating their own account
        if (currentTargetID.equals(String.valueOf(sess.getId())) && status.getText().equalsIgnoreCase("Inactive")) {
            JOptionPane.showMessageDialog(null, "Critical Error: You cannot set your own account to Inactive.", "Action Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. VALIDATION: Check for duplicate Username or Email (excluding the current user)
        String newUname = uname.getText().trim();
        String newEmail = email.getText().trim();
        String checkQuery = "SELECT * FROM tbl_account WHERE (username = '" + newUname + "' OR email = '" + newEmail + "') AND a_id != " + currentTargetID;
        
        if (connector.getData(checkQuery).next()) {
            JOptionPane.showMessageDialog(null, "Username or Email already taken by another user!");
            return;
        }

        // 3. IMAGE HANDLING: Process new image if selected, otherwise keep old path
        if (selectedFile != null) {
            File directory = new File("src/userimages");
            if (!directory.exists()) directory.mkdirs();
            
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
            String newFileName = "user_" + currentTargetID + "_" + System.currentTimeMillis() + extension;
            File dest = new File(directory, newFileName);
            
            Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            destinationPath = "src/userimages/" + newFileName;
        } else {
            // If no new file, use existing path (from session or previous populate)
            destinationPath = (isEditingOther) ? destinationPath : sess.getImage();
        }

        // 4. PASSWORD LOGIC: Only update password if fields are not empty
        String passwordUpdateQuery = "";
        String p1 = new String(newpass.getPassword());
        if (!p1.isEmpty()) {
            if(p1.equals(new String(confirmpass.getPassword()))){
                String hashedPass = config.passHasher.hashPassword(p1);
                passwordUpdateQuery = ", password = '" + hashedPass + "' ";
            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match!");
                return;
            }
        }

        // 5. DATABASE EXECUTION: Update the target user's record
        String query = "UPDATE tbl_account SET "
                + "firstname = '" + fname.getText() + "', "
                + "lastname = '" + lname.getText() + "', "
                + "username = '" + newUname + "', "
                + "email = '" + newEmail + "', "
                + "contact = '" + contact.getText() + "', "
                + "address = '" + add.getText() + "', "
                + "gender = '" + gender.getSelectedItem().toString() + "', "
                + "status = '" + status.getText() + "', "
                + "image = '" + destinationPath + "' "
                + passwordUpdateQuery 
                + " WHERE a_id = " + currentTargetID;

        if (connector.updateData(query)) {
            
            // 6. SESSION TRACKING: Only update local session if editing YOURSELF
            if (!isEditingOther) {
                sess.setFirstname(fname.getText());
                sess.setLastname(lname.getText());
                sess.setUsername(newUname);
                sess.setEmail(newEmail);
                sess.setContact(contact.getText());
                sess.setAddress(add.getText());
                sess.setGender(gender.getSelectedItem().toString());
                sess.setStatus(status.getText());
                sess.setImage(destinationPath);
                JOptionPane.showMessageDialog(null, "Your profile has been updated!");
            } else {
                JOptionPane.showMessageDialog(null, "User [ID: " + currentTargetID + "] updated successfully!");
            }

            // 7. DYNAMIC REDIRECT: Return to the correct dashboard based on WHO IS LOGGED IN
            this.dispose(); 
            String role = sess.getType(); 
            if (role.equalsIgnoreCase("Admin")) {
                new adminDashboard().setVisible(true);
            } else if (role.equalsIgnoreCase("Customer")) {
                new customerDashboard().setVisible(true);
            } else if (role.equalsIgnoreCase("Driver")) {
                new driverDashboard().setVisible(true);
            }
        }
    } catch (Exception e) {
        System.out.println("Update Error: " + e.getMessage());
        JOptionPane.showMessageDialog(null, "Update Failed: " + e.getMessage());
    }

    }//GEN-LAST:event_saveMouseClicked

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
 
    config.Session sess = config.Session.getInstance();
    
    // Check if any field differs from the Session data
    boolean isModified = !fname.getText().equals(sess.getFirstname()) ||
                         !lname.getText().equals(sess.getLastname()) ||
                         !uname.getText().equals(sess.getUsername()) ||
                         !email.getText().equals(sess.getEmail()) ||
                         !newpass.getText().isEmpty();

    if (isModified) {
       int res = JOptionPane.showConfirmDialog(null, "Discard changes?", "Exit", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) this.dispose();
        }
    

    }//GEN-LAST:event_cancelMouseClicked

    private void eye1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eye1MouseClicked
   if (isViewed1) {
            newpass.setEchoChar('•');
            isViewed1 = false;
        } else {
            newpass.setEchoChar((char) 0);
            isViewed1 = true;
        }
    }//GEN-LAST:event_eye1MouseClicked

    private void eye2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eye2MouseClicked
    if (isViewed2) {
            confirmpass.setEchoChar('•');
            isViewed2 = false;
        } else {
            confirmpass.setEchoChar((char) 0);
            isViewed2 = true;
        }
    }//GEN-LAST:event_eye2MouseClicked

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
            java.util.logging.Logger.getLogger(editprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editprofile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editprofile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea add;
    private javax.swing.JScrollPane address;
    private javax.swing.JTextField aid;
    private javax.swing.JPanel cancel;
    private javax.swing.JPanel change;
    private javax.swing.JPasswordField confirmpass;
    private javax.swing.JTextField contact;
    private javax.swing.JTextField email;
    private javax.swing.JLabel eye1;
    private javax.swing.JLabel eye2;
    private javax.swing.JTextField fname;
    private javax.swing.JComboBox<String> gender;
    private javax.swing.JLabel icon1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField lname;
    private javax.swing.JPasswordField newpass;
    private javax.swing.JPanel save;
    private javax.swing.JTextField status;
    private javax.swing.JTextField uname;
    // End of variables declaration//GEN-END:variables
}
