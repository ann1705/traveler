/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import config.Session;
import config.VanModel;
import config.dbConnector;
import dashboard.customerDashboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
    private String editingBookingID = null; // Important flag

  public bookingForm() {
    this.setUndecorated(true);
    initComponents();
    
    
     this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        pickup.setEditable(true);
        pickup.setEnabled(true);
        pickup.setFocusable(true); // Added for keyboard focus
        destination.setEditable(true);
        destination.setEnabled(true);
        destination.setFocusable(true);
    // Initialize JDateChooser constraints
        Date today = new Date();
        sdate.getJCalendar().setMinSelectableDate(today);
        edate.getJCalendar().setMinSelectableDate(today);

        // Real-time price calculation when dates are picked
        sdate.addPropertyChangeListener("date", evt -> calculateGrandTotal());
        edate.addPropertyChangeListener("date", evt -> calculateGrandTotal());

        displaySelectedVans();
        updateCartTable();
        loadStopovers();
        
        config.Session sess = config.Session.getInstance();
        jLabel2.setText("Logged as : " + sess.getFirstname() + " " + sess.getLastname());
        
        calculateGrandTotal();
        
  }
  
  



    // 2. THIS METHOD MUST BE INSIDE THE CLASS
public void setEditMode(String bid, String p, String d, java.util.Date s, java.util.Date e) {
            this.editingBookingID = bid;

            // 1. Set the basic text and dates
            pickup.setText(p);
            destination.setText(d);
            sdate.setDate(s);
            edate.setDate(e);

            // 2. Load Stopovers from the database into the temporary list
            loadExistingStopovers(bid); 

            // 3. REFRESH EVERYTHING
            // Since Session already has the vans (from Step 1), these will fill the UI
            displaySelectedVans(); 
            updateCartTable(); 
            calculateGrandTotal();

            jLabel13.setText("UPDATE BOOKING");
}  
    
    private void loadExistingStopovers(String bid) {
        try {
            dbConnector connector = dbConnector.getInstance();
            String query = "SELECT s.s_id, s.location, s.price FROM tbl_stopovers s " +
                           "JOIN tbl_bookings_stopovers bs ON s.s_id = bs.s_id " +
                           "WHERE bs.b_id = " + bid;
            java.sql.ResultSet rs = connector.getData(query);
            temporaryStopovers.clear();
            while(rs.next()) {
                temporaryStopovers.add("[" + rs.getInt("s_id") + "] " + rs.getString("location") + " - ₱" + rs.getString("price"));
            }
        } catch (Exception err) {
            System.out.println("Error loading existing stopovers: " + err.getMessage());
        }
    }
  
  private void calculateGrandTotal() {
        double vanDailyRateTotal = 0;
        double stopoverTotal = 0;
        long days = 1; 
        config.Session sess = config.Session.getInstance();

        // Calculate Days between start and end
        if (sdate.getDate() != null && edate.getDate() != null) {
            long diffInMillies = Math.abs(edate.getDate().getTime() - sdate.getDate().getTime());
            days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (days <= 0) days = 1; // Minimum charge is 1 day
        }

        // Sum Van Rates
        for (config.VanModel van : sess.getSelectedVans()) {
            try {
                String price = van.getDaily_rate().replace("₱", "").replace(",", "").trim();
                vanDailyRateTotal += Double.parseDouble(price);
            } catch (Exception e) {}
        }

        // Sum Stopover Fees
        for (String stop : temporaryStopovers) {
            try {
                String pricePart = stop.substring(stop.indexOf("₱") + 1).replace(",", "").trim();
                stopoverTotal += Double.parseDouble(pricePart);
            } catch (Exception e) {}
        }

        double grandTotal = (vanDailyRateTotal * days) + stopoverTotal;
        totalPriceDisplay.setText("₱" + String.format("%.2f", grandTotal));
        sess.setTprice(String.valueOf(grandTotal));
    }
  private void updateCartTable() {
        config.Session sess = config.Session.getInstance();
        DefaultTableModel model = (DefaultTableModel) bookingTable.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{"Type", "Model / Location", "Price"});

        for (VanModel van : sess.getSelectedVans()) {
            model.addRow(new Object[]{"VAN", van.getModel(), "₱" + van.getDaily_rate()});
        }
        for (String stop : temporaryStopovers) {
            String location = stop.split("-")[0].trim();
            String price = stop.substring(stop.indexOf("₱"));
            model.addRow(new Object[]{"STOPOVER", location, price});
        }
        calculateGrandTotal();
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




    @SuppressWarnings("unchecked")
     private int xMouse, yMouse;
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
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
        jLabel7 = new javax.swing.JLabel();
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
        sdate = new com.toedter.calendar.JDateChooser();
        edate = new com.toedter.calendar.JDateChooser();

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

        jPanel1.setBackground(new java.awt.Color(12, 33, 74));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Logged as :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 300, 30));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/back_white.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 60, 40));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minimize_white.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 30, 40));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close_white.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        jPanel1.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 30, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 70));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(fleetdisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 340, 317));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel3.setText("Pick-up Location");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 150, 30));

        pickup.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        pickup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(pickup, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 180, 30));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setText("Final Destination");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, -1, -1));

        destination.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        destination.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(destination, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 180, 30));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
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

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText("End Date");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 390, 80, -1));

        confirm.setBackground(new java.awt.Color(12, 33, 74));
        confirm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                confirmMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                confirmMouseExited(evt);
            }
        });
        confirm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("CONFIRM BOOKING");
        confirm.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 5, 180, 20));

        jPanel2.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 620, 180, 30));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel14.setText("Total Cost: ");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 430, 130, 20));

        jPanel3.setBackground(new java.awt.Color(12, 33, 74));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ADD MORE ");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 140, 20));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, 140, 40));

        totalPriceDisplay.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        totalPriceDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        totalPriceDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                totalPriceDisplayMouseClicked(evt);
            }
        });
        jPanel2.add(totalPriceDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, 210, 30));

        bookingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(bookingTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 750, 110));

        add.setBackground(new java.awt.Color(12, 33, 74));
        add.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addMouseExited(evt);
            }
        });
        add.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ADD ");
        add.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 90, 20));

        jPanel2.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 270, 90, 40));

        reset.setBackground(new java.awt.Color(12, 33, 74));
        reset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                resetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetMouseExited(evt);
            }
        });
        reset.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("RESET");
        reset.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 100, 20));

        jPanel2.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, 100, 40));

        removeVan.setBackground(new java.awt.Color(12, 33, 74));
        removeVan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        removeVan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeVanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeVanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeVanMouseExited(evt);
            }
        });
        removeVan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("REMOVE");
        removeVan.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 120, 20));

        jPanel2.add(removeVan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 120, 40));

        sdate.setBackground(new java.awt.Color(2, 54, 87));
        jPanel2.add(sdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, 210, 30));
        jPanel2.add(edate, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 380, 210, 30));

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
                                      
    config.Session sess = config.Session.getInstance();
  
        List<VanModel> cart = sess.getSelectedVans();

        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one van.");
            return;
        }
        if (pickup.getText().isEmpty() || destination.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all location fields.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dbConnector connector = dbConnector.getInstance();
            double finalPrice = Double.parseDouble(totalPriceDisplay.getText().replace("₱", "").replace(",", "").trim());
            int b_id;

            if (editingBookingID != null) {
                // --- UPDATE LOGIC ---
                b_id = Integer.parseInt(editingBookingID);

                // 1. Reset previous van statuses to Available
                String releaseQuery = "UPDATE tbl_vans SET status = 'Available' WHERE v_id IN " +
                                     "(SELECT v_id FROM tbl_booking_items WHERE b_id = " + b_id + ")";
                connector.updateData(releaseQuery);

                // 2. Clear old mappings
                connector.updateData("DELETE FROM tbl_booking_items WHERE b_id = " + b_id);
                connector.updateData("DELETE FROM tbl_bookings_stopovers WHERE b_id = " + b_id);

                // 3. Update main record
                String updateMain = "UPDATE tbl_bookings SET pick_up = '" + pickup.getText() + "', " +
                                    "destination = '" + destination.getText() + "', " +
                                    "start_date = '" + sdf.format(sdate.getDate()) + "', " +
                                    "end_date = '" + sdf.format(edate.getDate()) + "', " +
                                    "total_price = " + finalPrice + " WHERE b_id = " + b_id;
                connector.updateData(updateMain);

            } else {
                // --- INSERT LOGIC ---
                String insertMain = "INSERT INTO tbl_bookings (a_id, pick_up, destination, start_date, end_date, total_price, status) "
                                  + "VALUES (" + sess.getId() + ", '" + pickup.getText() + "', '" + destination.getText() + "', '" 
                                  + sdf.format(sdate.getDate()) + "', '" + sdf.format(edate.getDate()) + "', " + finalPrice + ", 'Pending')";
                b_id = connector.insertData(insertMain);
            }

            if (b_id > 0) {
                // 4. Re-insert items (Vans)
                for (VanModel v : cart) {
                    connector.insertData("INSERT INTO tbl_booking_items (b_id, v_id) VALUES (" + b_id + ", " + v.getVid() + ")");
                    connector.updateData("UPDATE tbl_vans SET status = 'Booked' WHERE v_id = " + v.getVid());
                }

                // 5. Re-insert Stopovers
                for (String val : temporaryStopovers) {
                    String s_id = val.substring(val.indexOf("[") + 1, val.indexOf("]"));
                    connector.insertData("INSERT INTO tbl_bookings_stopovers (b_id, s_id) VALUES (" + b_id + ", " + s_id + ")");
                }

                JOptionPane.showMessageDialog(this, (editingBookingID != null ? "Booking Updated!" : "Booking Confirmed!"));
                
                // Redirect back to details
                new app.BookingDetails(String.valueOf(b_id), sess.getFirstname() + " " + sess.getLastname(), 
                                       pickup.getText(), destination.getText(), sdf.format(sdate.getDate()), 
                                       sdf.format(edate.getDate()), totalPriceDisplay.getText(), "Pending").setVisible(true);

                sess.clearVans();
                this.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    

    }//GEN-LAST:event_confirmMouseClicked

    private void totalPriceDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_totalPriceDisplayMouseClicked
 
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
        updateCartTable();
        JOptionPane.showMessageDialog(this, "Van removed from selection.");
    } else {
        JOptionPane.showMessageDialog(this, "Please select a van from the table to remove.");
    }

    }//GEN-LAST:event_removeVanMouseClicked

    private void stopoverListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_stopoverListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_stopoverListValueChanged

    private void stopoverListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopoverListMouseClicked

    }//GEN-LAST:event_stopoverListMouseClicked

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

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
    int y = evt.getYOnScreen();
    this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
      xMouse = evt.getX();
      yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
         jPanel3.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        jPanel3.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_jPanel3MouseExited

    private void removeVanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeVanMouseEntered
         removeVan.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_removeVanMouseEntered

    private void removeVanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeVanMouseExited
        removeVan.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_removeVanMouseExited

    private void resetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetMouseEntered
        reset.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_resetMouseEntered

    private void resetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetMouseExited
       reset.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_resetMouseExited

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
         add.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited
        add.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_addMouseExited

    private void confirmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseEntered
         confirm.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_confirmMouseEntered

    private void confirmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseExited
       confirm.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_confirmMouseExited

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
    private javax.swing.JLabel close;
    private javax.swing.JPanel confirm;
    private javax.swing.JTextField destination;
    private com.toedter.calendar.JDateChooser edate;
    private javax.swing.JScrollPane fleetdisplay;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel minimize;
    private javax.swing.JTextField pickup;
    private javax.swing.JPanel removeVan;
    private javax.swing.JPanel reset;
    private com.toedter.calendar.JDateChooser sdate;
    private javax.swing.JList<String> stopoverList;
    private javax.swing.JTextField totalPriceDisplay;
    // End of variables declaration//GEN-END:variables
}
