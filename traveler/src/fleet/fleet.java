/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleet;


import dashboard.customerDashboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author RageKing
 */
public class fleet extends javax.swing.JFrame {

    /**
     * Creates new form fleet
     */
    public fleet() {
        
         this.setUndecorated(true);
        initComponents();
        this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        
        displayPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 20));
        loadFleet("SELECT * FROM tbl_vans");
        
        
        config.Session sess = config.Session.getInstance();
    if (sess.getId() != 0) {
        login.setVisible(false); // Hides the login panel/button if logged in
    } else {
        login.setVisible(true);  // Shows it if it's a guest
    }
    
    }
     
  public void loadFleet(String query) {
        displayPanel.removeAll();
        try {
            config.dbConnector connector = config.dbConnector.getInstance();
            ResultSet rs = connector.getData(query);

            while (rs.next()) {
                String vStatus = rs.getString("status");
                String vId = rs.getString("v_id");
                String vModel = rs.getString("model");
                String vRate = rs.getString("daily_rate");
                String vCap = rs.getString("capacity");
                String vPath = rs.getString("image");

                boolean isAvailable = vStatus.equalsIgnoreCase("Available");

                // --- CARD PANEL ---
                JPanel card = new JPanel();
                card.setPreferredSize(new Dimension(250, 320));
                
                // Color Variables
                Color lightBlue = new Color(173, 216, 230);
                Color hoverBlue = new Color(135, 206, 235);
                Color bookedGrey = new Color(225, 225, 225);

                card.setBackground(isAvailable ? lightBlue : bookedGrey);
                card.setBorder(BorderFactory.createLineBorder(isAvailable ? Color.BLACK : Color.LIGHT_GRAY, 1));
                card.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                // --- HOVER EFFECT LOGIC ---
                if (isAvailable) {
                    card.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) {
                            card.setBackground(hoverBlue);
                            card.setBorder(BorderFactory.createLineBorder(new Color(76, 143, 209), 2));
                        }
                        public void mouseExited(MouseEvent e) {
                            card.setBackground(lightBlue);
                            card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        }
                    });
                }

                // --- IMAGE HANDLING ---
                JLabel pic = new JLabel();
                if (vPath != null && !vPath.isEmpty()) {
                    // Resource path handling
                    String resPath = vPath.startsWith("src") ? vPath.substring(3) : vPath;
                    if (!resPath.startsWith("/")) resPath = "/" + resPath;
                    
                    java.net.URL imgURL = getClass().getResource(resPath);
                    ImageIcon icon = (imgURL != null) ? new ImageIcon(imgURL) : new ImageIcon(vPath);
                    
                    Image img = icon.getImage().getScaledInstance(230, 150, Image.SCALE_SMOOTH);
                    pic.setIcon(new ImageIcon(img));
                } else {
                    pic.setText("No Image Available");
                    pic.setHorizontalAlignment(SwingConstants.CENTER);
                }
                card.add(pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 230, 150));

                // --- LABELS ---
                JLabel modelLabel = new JLabel("Model: " + vModel);
                modelLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                card.add(modelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 230, 20));

                JLabel rateLabel = new JLabel("Daily Rate: ₱" + vRate);
                card.add(rateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 195, 230, 20));
                
                JLabel capacityLabel = new JLabel("Capacity: " + vCap);
                card.add(capacityLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 230, 20));

                JLabel statusLabel = new JLabel("Status: " + vStatus.toUpperCase());
                statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
                statusLabel.setForeground(isAvailable ? new Color(0, 153, 51) : Color.RED);
                card.add(statusLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 230, 20));

                // --- BUTTON ---
                JButton bookBtn = new JButton(isAvailable ? "ADD TO BOOKING" : "BOOKED");
                bookBtn.setBackground(isAvailable ? new Color(76, 143, 209) : new Color(150, 150, 150));
                bookBtn.setForeground(Color.WHITE);
                bookBtn.setFocusPainted(false);
                bookBtn.setEnabled(isAvailable);
                
                if (isAvailable) {
                    bookBtn.addActionListener(e -> openBooking(vId));
                }
                card.add(bookBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 150, 35));

                displayPanel.add(card);
            }
        } catch (SQLException e) {
            System.out.println("Error Loading Fleet: " + e.getMessage());
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }
  
  
private void openBooking(String Vid) {
    config.Session sess = config.Session.getInstance();
    
    // 1. Security check
    if (sess.getId() == 0) {
        JOptionPane.showMessageDialog(this, "Please login first!");
        new app.loginForm().setVisible(true); // Open login form
        this.dispose(); // Close fleet form
        return;
    }

    // 2. Limit & Duplicate Check
    int targetVid = Integer.parseInt(Vid);
    if (sess.getSelectedVans().size() >= 3) {
        JOptionPane.showMessageDialog(this, "Limit reached: 3 vans maximum.");
        return;
    }
    for (config.VanModel v : sess.getSelectedVans()) {
        if (v.getVid() == targetVid) {
            JOptionPane.showMessageDialog(this, "Van already selected!");
            return;
        }
    }

    // 3. Add to Session and redirect
    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        ResultSet rs = connector.getData("SELECT * FROM tbl_vans WHERE v_id = " + targetVid);
        if (rs.next()) {
            config.VanModel vm = new config.VanModel();
            vm.setVid(rs.getInt("v_id"));
            vm.setModel(rs.getString("model"));
            vm.setDaily_rate(rs.getString("daily_rate"));
            
            // --- FIX: CAPTURE THE IMAGE PATH ---
            vm.setVimage(rs.getString("image")); 
            // -----------------------------------
            
            sess.addVan(vm);
            JOptionPane.showMessageDialog(this, vm.getModel() + " added to your booking list.");
            
            // Go to booking form
            new app.bookingForm().setVisible(true);
            this.dispose();
        }
    } catch (SQLException e) { 
        System.out.println("SQL Error in openBooking: " + e.getMessage()); 
    }
}
   
    @SuppressWarnings("unchecked")
      private int xMouse, yMouse;
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        search = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayPanel = new javax.swing.JPanel();
        statusMsg = new javax.swing.JLabel();
        login = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/goback.png"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 20, 70, 40));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 0, 40, 60));

        close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close.png"))); // NOI18N
        close.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                closeMouseDragged(evt);
            }
        });
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                closeMousePressed(evt);
            }
        });
        jPanel1.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 0, 50, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 70));

        jPanel2.setBackground(new java.awt.Color(12, 33, 74));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        search.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });
        jPanel2.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 30));

        jPanel3.setBackground(new java.awt.Color(20, 20, 130));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SEARCH");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 80, 30));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        displayPanel.setBackground(new java.awt.Color(255, 255, 255));
        displayPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        displayPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        displayPanel.add(statusMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, 170, 20));

        jScrollPane1.setViewportView(displayPanel);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 60, 950, 470));

        login.setBackground(new java.awt.Color(20, 20, 130));
        login.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginMouseClicked(evt);
            }
        });
        login.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LOGIN / REGISTER");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        login.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 30));

        jPanel2.add(login, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 140, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 990, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
                                     
    String text = search.getText().trim();
    
    // If search is empty, just reload everything and stop
    if(text.isEmpty()){
        loadFleet("SELECT * FROM tbl_vans WHERE status = 'Available'");
        return;
    }

    // Expanded query to include Model, Category, Capacity, and Daily Rate
    String query = "SELECT * FROM tbl_vans WHERE status = 'Available' AND ("
                 + "model LIKE '%"+text+"%' OR "
                 + "category LIKE '%"+text+"%' OR "
                 + "capacity LIKE '%"+text+"%' OR "
                 + "daily_rate LIKE '%"+text+"%')";
    
    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        ResultSet rs = connector.getData(query);
        
        if (!rs.isBeforeFirst()) { // This checks if the result set is empty
            JOptionPane.showMessageDialog(this, "No vans found matching: " + text);
            search.setText(""); // Clear search field
            loadFleet("SELECT * FROM tbl_vans WHERE status = 'Available'"); // Reset display
        } else {
            loadFleet(query); // Display filtered results
        }
    } catch (SQLException e) {
        System.out.println("Search Error: " + e.getMessage());
    }

    }//GEN-LAST:event_searchActionPerformed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
                                
                                  
    String text = search.getText().trim();
    
    if(text.isEmpty()){
        statusMsg.setVisible(false);
        loadFleet("SELECT * FROM tbl_vans");
        return;
    }

    // Removed 'Available' filter to show full fleet status
    String query = "SELECT * FROM tbl_vans WHERE ("
                 + "model LIKE '%"+text+"%' OR "
                 + "category LIKE '%"+text+"%' OR "
                 + "capacity LIKE '%"+text+"%' OR "
                 + "daily_rate LIKE '%"+text+"%')";

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        ResultSet rs = connector.getData(query);

        if (rs.isBeforeFirst()) { 
            statusMsg.setVisible(false);
            loadFleet(query); 
        } else {
            displayPanel.removeAll();
            displayPanel.revalidate();
            displayPanel.repaint();
            statusMsg.setText("No results found for: '" + text + "'");
            statusMsg.setVisible(true);
        }
    } catch (SQLException e) {
        System.out.println("Search Error: " + e.getMessage());
    }


    }//GEN-LAST:event_searchKeyReleased

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
                                    
    config.Session sess = config.Session.getInstance();
    
    // Check if a user is logged in (ID is usually 0 if not logged in)
    if (sess.getId() != 0) {
        // If logged in, go back to Customer Dashboard
        customerDashboard cd = new customerDashboard();
        cd.setVisible(true);
    } else {
        // If GUEST, go back to the Login Form
        // Replace 'login.loginForm' with your actual login package/class
        new app.loginForm().setVisible(true); 
    }
    
    this.dispose();      

    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
      new app.loginForm().setVisible(true);
     this.dispose();
        
    }//GEN-LAST:event_jLabel3MouseClicked

    private void loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMouseClicked
    
    }//GEN-LAST:event_loginMouseClicked

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

    private void closeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_closeMouseDragged

    private void closeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_closeMousePressed

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
       
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
   
    }//GEN-LAST:event_jPanel1MouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
         xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
           int confirm = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to exit?", "Exit Confirmation", 
            javax.swing.JOptionPane.YES_NO_OPTION);
            
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        System.exit(0);
    }
    }//GEN-LAST:event_formMouseDragged

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
            java.util.logging.Logger.getLogger(fleet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fleet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fleet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fleet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new fleet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel close;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel login;
    private javax.swing.JLabel minimize;
    private javax.swing.JTextField search;
    private javax.swing.JLabel statusMsg;
    // End of variables declaration//GEN-END:variables
}
