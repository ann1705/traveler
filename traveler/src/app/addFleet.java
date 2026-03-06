/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import config.Session;
import config.dbConnector;
import dashboard.adminDashboard;
import internalPages.usersTable;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static javafx.scene.input.KeyCode.J;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author RageKing
 */
public class addFleet extends javax.swing.JFrame {

   String imagePath = ""; 
   public String destination = ""; 
   File selectedFile;
   
    public addFleet() {
        initComponents();
    }
 
    public void close(){
    this.dispose();
    adminDashboard ad = new adminDashboard();
    ad.setVisible(true);
    usersTable ut = new usersTable();
    ad.getMainPane().add(ut).setVisible(true);
}
       
       
    public ImageIcon ResizeImage(String ImagePath, byte[] pic, JLabel label) {
        ImageIcon MyImage = (ImagePath != null) ? new ImageIcon(ImagePath) : new ImageIcon(pic);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
       
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        save = new javax.swing.JPanel();
        savel = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        category = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        model = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        plateno = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        vanid = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        dailyrate = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        capacity = new javax.swing.JTextField();
        status = new javax.swing.JComboBox<>();
        photo = new javax.swing.JPanel();
        imagelabel = new javax.swing.JLabel();
        cancel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        browse = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        save.setBackground(new java.awt.Color(204, 102, 255));
        save.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveMouseEntered(evt);
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

        jPanel3.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, 130, 40));

        label.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        label.setText("Category :");
        jPanel3.add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 150, 30));

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

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 70));

        category.setEditable(true);
        category.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Luxury", "Economy", "Off-road" }));
        category.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(category, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 240, 30));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setText("Capacity :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 294, 130, 30));

        model.setBackground(new java.awt.Color(204, 204, 255));
        model.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        model.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(model, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 240, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setText("Daily Rate :");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 344, 110, 30));

        plateno.setBackground(new java.awt.Color(204, 204, 255));
        plateno.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        plateno.setToolTipText("");
        plateno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(plateno, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 240, 30));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setText("Van ID:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 90, -1));

        vanid.setEditable(false);
        vanid.setBackground(new java.awt.Color(204, 204, 255));
        vanid.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        vanid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel3.add(vanid, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 240, 30));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel11.setText("Plate No. :");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, 30));

        dailyrate.setBackground(new java.awt.Color(204, 204, 255));
        dailyrate.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        dailyrate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(dailyrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 240, 30));

        jLabel12.setBackground(new java.awt.Color(204, 204, 255));
        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel12.setText("Status :");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 394, 110, 30));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Model :");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, 30));

        capacity.setBackground(new java.awt.Color(204, 204, 255));
        capacity.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        capacity.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(capacity, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 240, 30));

        status.setEditable(true);
        status.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Booked", "Maintenace" }));
        status.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 240, 30));

        photo.setBackground(new java.awt.Color(255, 255, 255));
        photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        photo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                photoMouseClicked(evt);
            }
        });
        photo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        photo.add(imagelabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 5, 310, 270));

        jPanel3.add(photo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, 320, 280));

        cancel.setBackground(new java.awt.Color(204, 102, 255));
        cancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });
        cancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CANCEL");
        cancel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 120, 20));

        jPanel3.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 480, 120, 40));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("BROWSE");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, 140, 40));

        browse.setBackground(new java.awt.Color(204, 102, 255));
        browse.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        browse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                browseMouseClicked(evt);
            }
        });
        browse.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(browse, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, 140, 40));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 1, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 830, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void savelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savelMouseClicked
        
    }//GEN-LAST:event_savelMouseClicked

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked
                                 
                                  
        // 1. Validation check
        if(model.getText().isEmpty() || plateno.getText().isEmpty() || dailyrate.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields!");
            return;
        }

        try {
            // 2. Handle Image Storage (Physical Copy)
            String finalPath = "";
            if(selectedFile != null) {
                File folder = new File("src/fleet_images");
                if (!folder.exists()) folder.mkdirs();
                
                // Set destination path
                finalPath = "src/fleet_images/" + selectedFile.getName();
                File dest = new File(finalPath);
                
                // Copy file to project folder
                Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // 3. Database Connection and Insert
            Connection con = dbConnector.getInstance().getConnection();
            String sql = "INSERT INTO tbl_vans (category, model, plate_number, capacity, daily_rate, status, image) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, category.getSelectedItem().toString());
            pst.setString(2, model.getText());
            pst.setString(3, plateno.getText());
            pst.setString(4, capacity.getText());
            pst.setString(5, dailyrate.getText());
            pst.setString(6, status.getSelectedItem().toString());
            pst.setString(7, finalPath); // Save the project-relative path

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Fleet saved successfully!");
            
            close(); // Return to dashboard
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    


      
           
    }//GEN-LAST:event_saveMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        close();

    }//GEN-LAST:event_jLabel2MouseClicked

    private void saveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseEntered
       
    }//GEN-LAST:event_saveMouseEntered

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
                                     
    int confirm = javax.swing.JOptionPane.showConfirmDialog(
        this, 
        "Are you sure you want to cancel? Any unsaved data will be lost.", 
        "Exit Confirmation", 
        javax.swing.JOptionPane.YES_NO_OPTION,
        javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        close(); // Disposes current window and opens dashboard
    }

    }//GEN-LAST:event_cancelMouseClicked

    private void photoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_photoMouseClicked

    }//GEN-LAST:event_photoMouseClicked

    private void browseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseMouseClicked
                                       
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            // Display the image immediately on the imagelabel
            imagelabel.setIcon(ResizeImage(selectedFile.getAbsolutePath(), null, imagelabel));
            // Optional: Hide the Browse text if it overlaps
            jLabel4.setText(""); 
        }
    
    }//GEN-LAST:event_browseMouseClicked

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
            java.util.logging.Logger.getLogger(addFleet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addFleet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addFleet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addFleet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addFleet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel browse;
    private javax.swing.JPanel cancel;
    private javax.swing.JTextField capacity;
    private javax.swing.JComboBox<String> category;
    private javax.swing.JTextField dailyrate;
    private javax.swing.JLabel imagelabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel label;
    private javax.swing.JTextField model;
    private javax.swing.JPanel photo;
    private javax.swing.JTextField plateno;
    private javax.swing.JPanel save;
    private javax.swing.JLabel savel;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JTextField vanid;
    // End of variables declaration//GEN-END:variables
}
