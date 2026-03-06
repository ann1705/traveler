/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import config.Session;
import config.VanModel;
import dashboard.customerDashboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RageKing
 */
public class bookingForm extends javax.swing.JFrame {
    private ArrayList<String> temporaryStopovers = new ArrayList<>();

  public bookingForm() {
    initComponents();
    
    

    
    
    reset.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        resetMouseClicked(evt);
    }
});
    
    displaySelectedVans();
    updateCartTable();
    loadStopovers();
    
    // NEW: Add listener to stoplist for real-time price calculation
    stopoverList.addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            calculateGrandTotal();
        }
    });
    
    config.Session sess = config.Session.getInstance();
    jLabel2.setText("Logged as : " + sess.getFirstname() + " " + sess.getLastname());
    
    calculateGrandTotal();
}
private void confirmBooking() {
    config.Session sess = config.Session.getInstance();
    config.dbConnector connector = config.dbConnector.getInstance();
    
    try {
        for (config.VanModel van : sess.getSelectedVans()) {
            String query = "INSERT INTO tbl_bookings (a_id, v_id, s_loc, total_price, status) "
                    + "VALUES (" + sess.getId() + ", " 
                    + van.getVid() + ", '" 
                    + sess.getS_loc() + "', '" 
                    + sess.getTprice() + "', 'Pending')";
            connector.insertData(query);
        }
        
        JOptionPane.showMessageDialog(this, "Booking confirmed!");
        sess.clearVans(); // Reset for next time
        new dashboard.customerDashboard().setVisible(true);
        this.dispose();
    } catch (Exception e) { System.out.println(e.getMessage()); }
}
private void displaySelectedVans() {
    Session sess = Session.getInstance();
    List<VanModel> vanList = sess.getSelectedVans();

    JPanel mainContainer = new JPanel();
    mainContainer.setLayout(new javax.swing.BoxLayout(mainContainer, javax.swing.BoxLayout.Y_AXIS));
    mainContainer.setBackground(new java.awt.Color(204, 204, 255));

    for (VanModel van : vanList) {
        JPanel card = new JPanel();
        // Set a fixed size so the BoxLayout respects it
        card.setMinimumSize(new Dimension(300, 250));
        card.setMaximumSize(new Dimension(300, 250));
        card.setPreferredSize(new Dimension(300, 250));
        
        card.setBackground(new java.awt.Color(255, 255, 255));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        card.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JLabel pic = new JLabel();
        pic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));

        String path = van.getVimage(); // "src/fleet_images/camp.jpg"
        
        if (path != null && !path.isEmpty()) {
            // FIX: Convert "src/folder/img.jpg" to "/folder/img.jpg" for getResource
            String resourcePath = path.startsWith("src") ? path.substring(3) : path;
            if (!resourcePath.startsWith("/")) {
                resourcePath = "/" + resourcePath;
            }

            java.net.URL imgURL = getClass().getResource(resourcePath);

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(280, 150, Image.SCALE_SMOOTH);
                pic.setIcon(new ImageIcon(img));
            } else {
                // Fallback: If it's an absolute path on the hard drive
                ImageIcon icon = new ImageIcon(path);
                if (icon.getIconWidth() > 0) {
                    Image img = icon.getImage().getScaledInstance(280, 150, Image.SCALE_SMOOTH);
                    pic.setIcon(new ImageIcon(img));
                } else {
                    pic.setText("Image Not Found");
                    pic.setFont(new Font("SansSerif", Font.ITALIC, 10));
                }
            }
        }

        card.add(pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, 150));

        JLabel modelLabel = new JLabel("Model: " + van.getModel());
        modelLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        card.add(modelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 280, 25));

        JLabel rateLabel = new JLabel("Daily Rate: ₱" + van.getDaily_rate());
        card.add(rateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 280, 20));

        mainContainer.add(card);
        mainContainer.add(javax.swing.Box.createRigidArea(new Dimension(0, 10)));
    }

    fleetdisplay.setViewportView(mainContainer);
    mainContainer.revalidate();
    mainContainer.repaint();
}

public void calculateFinalTotal() {
    config.Session sess = config.Session.getInstance();
    double grandTotal = 0;

    // Sum all vans
    for (config.VanModel v : sess.getSelectedVans()) {
        grandTotal += Double.parseDouble(v.getDaily_rate());
    }

    // Add stop-over fee (only once)
    if (sess.getS_price() != null) {
        grandTotal += Double.parseDouble(sess.getS_price());
    }

    // Update your total label
    totalPriceDisplay.setText("₱" + String.format("%.2f", grandTotal));
    sess.setTprice(String.valueOf(grandTotal));
}

private void loadStopovers() {
        javax.swing.DefaultListModel<String> listModel = new javax.swing.DefaultListModel<>();
        try {
            config.dbConnector connector = config.dbConnector.getInstance();
            java.sql.ResultSet rs = connector.getData("SELECT s_id, location, price FROM tbl_stopovers");
            while (rs.next()) {
                listModel.addElement("[" + rs.getInt("s_id") + "] " + rs.getString("location") + " - ₱" + rs.getString("price"));
            }
            stopoverList.setModel(listModel);
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading stopovers: " + e.getMessage());
        }
    }

public void loadBookingTable() {
    config.Session sess = config.Session.getInstance();
    DefaultTableModel model = (DefaultTableModel) bookingTable.getModel();
    model.setRowCount(0); // Clear current rows

    // Loop through the selected vans
    for (config.VanModel van : sess.getSelectedVans()) {
        model.addRow(new Object[]{
            van.getVid(),
            van.getModel(),
            van.getDaily_rate(),
            (sess.getS_loc() != null) ? sess.getS_loc() : "Select a Stop-over",
            (sess.getS_price() != null) ? sess.getS_price() : "0.00"
        });
    }
    
    // Always call the total calculation after updating the table
    calculateFinalTotal();
}
private void calculateGrandTotal() {
        double vanTotal = 0;
        double stopoverTotal = 0;
        config.Session sess = config.Session.getInstance();

        // 1. Van Totals
        for (config.VanModel van : sess.getSelectedVans()) {
            try {
                String price = van.getDaily_rate().replace("₱", "").replace(",", "").trim();
                vanTotal += Double.parseDouble(price);
            } catch (Exception e) {}
        }

        // 2. Cumulative Stopover Totals (from our temporary list)
        for (String stop : temporaryStopovers) {
            try {
                String pricePart = stop.substring(stop.indexOf("₱") + 1).replace(",", "").trim();
                stopoverTotal += Double.parseDouble(pricePart);
            } catch (Exception e) {}
        }

        double grandTotal = vanTotal + stopoverTotal;
        totalPriceDisplay.setText("₱" + String.format("%.2f", grandTotal));
    }


private void updateCartTable() {
        config.Session sess = config.Session.getInstance();
        List<config.VanModel> cart = sess.getSelectedVans();
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) bookingTable.getModel();
        
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{"Type", "Model / Location", "Price"});

        // Display Vans
        for (config.VanModel van : cart) {
            model.addRow(new Object[]{"VAN", van.getModel(), "₱" + van.getDaily_rate()});
        }

        // Display Added Stopovers
        for (String stop : temporaryStopovers) {
            String location = stop.split("-")[0].trim();
            String price = stop.substring(stop.indexOf("₱"));
            model.addRow(new Object[]{"STOPOVER", location, price});
        }
        
        calculateGrandTotal();
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        fleetdisplay = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        pickup = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        destination = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stopoverList = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        sdate = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        edate = new javax.swing.JTextField();
        confirm = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        totalPriceDisplay = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookingTable = new javax.swing.JTable();
        add = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        reset = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        removeVan = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(76, 143, 209));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Logged as :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 300, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/goback.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 60, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 70));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(fleetdisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 340, 317));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel3.setText("Pick-up Location");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 150, -1));

        pickup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(pickup, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 180, 30));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("Final Destination");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, -1, -1));

        destination.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(destination, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 180, 30));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setText("Stopover Destinations");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 144, 180, 20));

        stopoverList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        stopoverList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopoverListMouseClicked(evt);
            }
        });
        stopoverList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                stopoverListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(stopoverList);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 310, 90));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setText("Start Date ");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 120, 20));

        sdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, 220, 30));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText("End Date");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 390, 80, -1));

        edate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(edate, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 380, 220, 30));

        confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmMouseClicked(evt);
            }
        });
        confirm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("CONFIRM BOOKING");
        confirm.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 5, 180, 20));

        jPanel2.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 620, 180, 30));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel14.setText("Total Cost: ");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, 100, 20));

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ADD MORE ");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 4, 130, 30));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, 140, 40));

        totalPriceDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        totalPriceDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                totalPriceDisplayMouseClicked(evt);
            }
        });
        jPanel2.add(totalPriceDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, 220, 30));

        bookingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(bookingTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 750, 110));

        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
        });
        add.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ADD ");
        add.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 5, 80, -1));

        jPanel2.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 270, 90, 30));

        reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetMouseClicked(evt);
            }
        });
        reset.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("RESET");
        reset.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jPanel2.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, 90, 30));

        removeVan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeVanMouseClicked(evt);
            }
        });
        removeVan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("REMOVE");
        removeVan.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 40));

        jPanel2.add(removeVan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 120, 40));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setText("back to fleet");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 110, 20));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 130, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 770, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
      if (Session.getInstance().getSelectedVans().size() >= 3) {
        JOptionPane.showMessageDialog(this, "You can only book up to 3 vans at a time.");
    } else {
        fleet.fleet fleetPage = new fleet.fleet();
        fleetPage.setVisible(true);
        this.dispose();
    }
      
    }//GEN-LAST:event_jPanel3MouseClicked

    
    
    private void confirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseClicked
   
    // 1. Get the Session Instance (Use Session, not Singleton, for van list support)
    config.Session sess = config.Session.getInstance();
    int customerId = sess.getId();
    List<config.VanModel> cart = sess.getSelectedVans();

    // 2. Validation
    if (cart.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Your cart is empty!");
        return;
    }
    if (pickup.getText().isEmpty() || destination.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in the Pick-up and Destination fields.");
        return;
    }

    // 3. Stringify Van IDs (e.g., "1,2,3")
    StringBuilder vanSb = new StringBuilder();
    for (int i = 0; i < cart.size(); i++) {
        vanSb.append(cart.get(i).getVid());
        if (i < cart.size() - 1) vanSb.append(",");
    }
    String vanIds = vanSb.toString();

    // 4. Stringify Stopover IDs
    StringBuilder stopSb = new StringBuilder();
    for (int i = 0; i < temporaryStopovers.size(); i++) {
        String val = temporaryStopovers.get(i);
        // Extracting ID from "Location Name [ID]" format
        String id = val.substring(val.indexOf("[") + 1, val.indexOf("]"));
        stopSb.append(id);
        if (i < temporaryStopovers.size() - 1) stopSb.append(",");
    }
    String stopoverIds = stopSb.toString(); 

    try {
        config.dbConnector connector = config.dbConnector.getInstance();
        String rawPrice = totalPriceDisplay.getText().replace("₱", "").replace(",", "").trim();
        double finalPrice = Double.parseDouble(rawPrice);

        // 5. Insert Query
        String query = "INSERT INTO tbl_bookings (a_id, van_id, pick_up, destination, stopover_id, start_date, end_date, total_price, status) "
                     + "VALUES (" + customerId + ", '" + vanIds + "', '" + pickup.getText() + "', '" 
                     + destination.getText() + "', '" + stopoverIds + "', '" + sdate.getText() + "', '" 
                     + edate.getText() + "', " + finalPrice + ", 'Pending')";
        
        // This now returns the ACTUAL b_id from the database
        int generatedId = connector.insertData(query);
        
        if (generatedId > 0) {
            JOptionPane.showMessageDialog(this, "Booking Successful! ID: " + generatedId);

            // 6. Create Snapshot for Details Form
            String fullName = sess.getFirstname() + " " + sess.getLastname();
            List<config.VanModel> snapshotVans = new java.util.ArrayList<>(cart);
            List<String> snapshotStops = new java.util.ArrayList<>(temporaryStopovers);

            // Open BookingDetails with the real generatedId
            app.BookingDetails detailsForm = new app.BookingDetails(
                String.valueOf(generatedId),
                fullName,
                pickup.getText(),
                destination.getText(),
                sdate.getText(),
                edate.getText(),
                totalPriceDisplay.getText(),
                "Pending",
                snapshotVans,
                snapshotStops
            );
            
            detailsForm.setVisible(true);
            sess.clearVans();
            this.dispose();
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error processing booking: " + e.getMessage());
    }

    }//GEN-LAST:event_confirmMouseClicked

    private void totalPriceDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalPriceDisplayMouseClicked
    
    double total = 0;
    for (config.VanModel v : config.Session.getInstance().getSelectedVans()) {
        try {
            // Cleans the ₱ symbol and spaces before parsing
            String price = v.getDaily_rate().replace("₱", "").trim();
            total += Double.parseDouble(price);
        } catch (Exception e) { }
    }
    jLabel14.setText("Total Cost: ₱" + total);

    }//GEN-LAST:event_totalPriceDisplayMouseClicked

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
                               
        String selected = stopoverList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a stopover from the list.");
            return;
        }

        if (temporaryStopovers.contains(selected)) {
            JOptionPane.showMessageDialog(this, "This stopover is already added.");
            return;
        }

        if (temporaryStopovers.size() >= 3) {
            JOptionPane.showMessageDialog(this, "Maximum of 3 stopovers allowed.");
            return;
        }

        // Incremental add
        temporaryStopovers.add(selected);
        updateCartTable();

        if (temporaryStopovers.size() == 3) {
            stopoverList.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Stopover limit reached (3/3).");
        }
    

    }//GEN-LAST:event_addMouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2MouseClicked

    private void resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetMouseClicked
                                     
        temporaryStopovers.clear();
        stopoverList.setEnabled(true);
        stopoverList.clearSelection();
        updateCartTable();

    }//GEN-LAST:event_resetMouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
       customerDashboard cd = new customerDashboard();
       cd.setVisible(true);
       dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void removeVanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeVanMouseClicked
  
    // 1. Get the table model and the selected row index
    DefaultTableModel model = (DefaultTableModel) bookingTable.getModel();
    int rowIndex = bookingTable.getSelectedRow();

    // 2. Check if a row is actually selected
    if (rowIndex != -1) {
        // Get the Van ID from the first column (assuming index 0 is the v_id)
        int vanIdToRemove = Integer.parseInt(model.getValueAt(rowIndex, 0).toString());

        // 3. Remove the van from the Session list
        config.Session sess = config.Session.getInstance();
        sess.getSelectedVans().removeIf(van -> van.getVid() == vanIdToRemove);

        // 4. Refresh the UI
        loadBookingTable(); 
        JOptionPane.showMessageDialog(this, "Van removed from selection.");
    } else {
        JOptionPane.showMessageDialog(this, "Please select a van from the table to remove.");
    }

    }//GEN-LAST:event_removeVanMouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
      new fleet.fleet().setVisible(true);
      this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void stopoverListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_stopoverListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_stopoverListValueChanged

    private void stopoverListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopoverListMouseClicked
                                         
    // 1. Get the index of the clicked item
    int index = stopoverList.getSelectedIndex();
    if (index == -1) return;

    // 2. Fetch data from your database or list model
    // Assuming your list displays "Location - Price"
    String selectedValue = stopoverList.getSelectedValue().toString();
    
    try {
        // Split "Bohol - 1500" into Location and Price
        String[] parts = selectedValue.split(" - ");
        String location = parts[0];
        String price = parts[1].replace("₱", "").trim();

        // 3. Update Session
        config.Session sess = config.Session.getInstance();
        sess.setS_loc(location);
        sess.setS_price(price);

        // 4. Refresh your booking table to show the new stop-over details
        loadBookingTable();
        
    } catch (Exception e) {
        System.out.println("Error parsing stop-over: " + e.getMessage());
    }

    }//GEN-LAST:event_stopoverListMouseClicked

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
            java.util.logging.Logger.getLogger(bookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bookingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bookingForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel add;
    private javax.swing.JTable bookingTable;
    private javax.swing.JPanel confirm;
    private javax.swing.JTextField destination;
    private javax.swing.JTextField edate;
    private javax.swing.JScrollPane fleetdisplay;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField pickup;
    private javax.swing.JPanel removeVan;
    private javax.swing.JPanel reset;
    private javax.swing.JTextField sdate;
    private javax.swing.JList<String> stopoverList;
    private javax.swing.JTextField totalPriceDisplay;
    // End of variables declaration//GEN-END:variables
}
