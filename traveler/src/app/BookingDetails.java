/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;


// ITEXT PDF Imports
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dashboard.customerDashboard;


// JAVA & SWING Imports
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RageKing
 */
public class BookingDetails extends javax.swing.JFrame {
    
    private List<config.VanModel> bookedVans;
    private List<String> bookedStops;
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public BookingDetails(String bid, String cName, String p, String d, String s, String e, String t, String stat, List<config.VanModel> vans, List<String> stops) {
    initComponents();
    this.bookedVans = vans;
    this.bookedStops = stops;
    
    // Set Fields
    bookId.setText(bid);       
        customerName.setText(cName);
        pickup.setText(p);
        finall.setText(d);
        total.setText(t);
        status.setText(stat);

        // --- UPDATED: Setting JDateChooser Dates ---
        try {
            Date startDate = dateFormat.parse(s);
            Date endDate = dateFormat.parse(e);
            stat_date.setDate(startDate);
            end_date.setDate(endDate);
        } catch (Exception ex) {
            System.out.println("Date Parsing Error: " + ex.getMessage());
        }

        if (stat.equalsIgnoreCase("Cancelled") || stat.equalsIgnoreCase("Completed")) {
            header.setText("BOOKING HISTORY");
            edit.setVisible(false);
            download.setVisible(stat.equalsIgnoreCase("Completed"));
            jLabel9.setText("Rebook Trip");
            cancel.setBackground(new java.awt.Color(0, 153, 51));
            status.setForeground(stat.equalsIgnoreCase("Cancelled") ? java.awt.Color.RED : new java.awt.Color(0, 102, 204));
        } else {
            header.setText("BOOKING DETAILS");
            status.setForeground(java.awt.Color.BLACK);
        }

        lockFields();
        displayVans();      
        displayStopovers(); 
        this.setLocationRelativeTo(null);
        
        // Prevents selecting any date before today
        stat_date.setMinSelectableDate(new Date()); 
        end_date.setMinSelectableDate(new Date());
    }

    
    private void lockFields() {
        bookId.setEditable(false);
        customerName.setEditable(false);
        pickup.setEditable(false);
        finall.setEditable(false);
        stat_date.setEnabled(false); // Use setEnabled for JDateChooser
        end_date.setEnabled(false);
        total.setEditable(false);
        status.setEditable(false);
    }
    
    private double calculateNewTotal(long days) {
        double ratePerDay = 2500.00; // Base rate per van
        double stopoverTotal = 0;

        for (int i = 0; i < stopoverTable.getRowCount(); i++) {
            try {
                String priceStr = stopoverTable.getValueAt(i, 1).toString().replace("₱", "").trim();
                stopoverTotal += Double.parseDouble(priceStr);
            } catch (Exception e) {
                stopoverTotal += 0;
            }
        }
        return (ratePerDay * bookedVans.size() * days) + stopoverTotal;
    }
    
private void releaseVans() {
    config.dbConnector connector = config.dbConnector.getInstance();
    for (config.VanModel van : bookedVans) {
        // Sets van to Available UNLESS admin manually flagged it for maintenance
        String query = "UPDATE tbl_vans SET v_status = 'Available' "
                     + "WHERE v_id = '" + van.getVid() + "' "
                     + "AND v_status != 'Under Maintenance'";
        connector.updateData(query);
    }
}
    
private void displayVans() {
    javax.swing.JPanel container = new javax.swing.JPanel();
    container.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 20));
    container.setBackground(java.awt.Color.WHITE);

    for (config.VanModel van : bookedVans) {
        javax.swing.JPanel card = new javax.swing.JPanel();
        card.setPreferredSize(new java.awt.Dimension(280, 220));
        card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 2));

        JLabel pic = new JLabel();
        pic.setHorizontalAlignment(JLabel.CENTER);
        
        String path = van.getVimage(); 
        System.out.println("Debug: Attempting to load image from: " + path); // Check your console!

        if (path != null && !path.isEmpty()) {
            // Step A: Try to load as a Resource (Internal to project)
            String resPath = path.startsWith("src") ? path.substring(3) : path;
            if (!resPath.startsWith("/")) resPath = "/" + resPath;
            java.net.URL imgURL = getClass().getResource(resPath);

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                java.awt.Image img = icon.getImage().getScaledInstance(240, 140, java.awt.Image.SCALE_SMOOTH);
                pic.setIcon(new ImageIcon(img));
            } else {
                // Step B: Try to load as an Absolute Path (External file)
                java.io.File file = new java.io.File(path);
                if (file.exists()) {
                    ImageIcon icon = new ImageIcon(path);
                    java.awt.Image img = icon.getImage().getScaledInstance(240, 140, java.awt.Image.SCALE_SMOOTH);
                    pic.setIcon(new ImageIcon(img));
                } else {
                    pic.setText("Image Not Found");
                    System.out.println("Debug: File does not exist at " + path);
                }
            }
        }
        
        JLabel model = new JLabel(van.getModel());
        model.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 15));
        model.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        pic.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        card.add(javax.swing.Box.createVerticalStrut(10));
        card.add(pic);
        card.add(javax.swing.Box.createVerticalStrut(10));
        card.add(model);
        container.add(card);
    }
    vandisplay.setViewportView(container);
}

   private void displayStopovers() {
        DefaultTableModel model = (DefaultTableModel) stopoverTable.getModel();
        model.setColumnIdentifiers(new String[]{"Location Name", "Price"});
        model.setRowCount(0);

        for (String stop : bookedStops) {
            try {
                String name = stop.split("-")[0].trim();
                String price = stop.substring(stop.indexOf("₱"));
                model.addRow(new Object[]{name, price});
            } catch (Exception ex) {
                model.addRow(new Object[]{stop, "₱0.00"});
            }
        }
    }
    
private void addInfoRow(PdfPTable table, String label, String value) {
        com.itextpdf.text.Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        com.itextpdf.text.Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
        table.addCell(new PdfPCell(new Phrase(label, bold)));
        table.addCell(new PdfPCell(new Phrase(value, normal)));
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        header = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        vandisplay = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        stopoverTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        pickup = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        finall = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        stat_date = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        end_date = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        download = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cancel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        edit = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        status = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        bookId = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        customerName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(12, 33, 74));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(76, 143, 209));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("BOOKING DETAILS");
        jPanel2.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 760, 30));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/goback.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 60));

        vandisplay.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(vandisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 850, 330));

        stopoverTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(stopoverTable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, 160));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("STOPOVERS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 250, -1));
        jPanel1.add(pickup, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 510, 160, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BOOKING DETAILS");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 400, 230, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Pick-up Location :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 510, 130, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Final Destination : ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 540, 140, -1));
        jPanel1.add(finall, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 540, 160, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Start Date :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 570, 100, -1));
        jPanel1.add(stat_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 570, 160, -1));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("End Date :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 600, 90, -1));
        jPanel1.add(end_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 600, 160, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Total Cost : ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 630, 90, -1));
        jPanel1.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 630, 160, -1));

        download.setBackground(new java.awt.Color(20, 20, 130));
        download.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        download.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadMouseClicked(evt);
            }
        });
        download.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Download");
        download.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 140, 20));

        jPanel1.add(download, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 710, 140, 40));

        cancel.setBackground(new java.awt.Color(20, 20, 130));
        cancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });
        cancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cancel Booking");
        cancel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 180, 20));

        jPanel1.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 710, 180, 40));

        edit.setBackground(new java.awt.Color(20, 20, 130));
        edit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMouseClicked(evt);
            }
        });
        edit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Edit Booking");
        edit.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 160, 20));

        jPanel1.add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 710, 160, 40));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Status :");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 660, 60, -1));
        jPanel1.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 660, 160, 30));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Booking ID :");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 440, -1, -1));
        jPanel1.add(bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, 160, 30));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Customer Name :");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 480, -1, -1));
        jPanel1.add(customerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 470, 160, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseClicked
                                     
        if (jLabel10.getText().equals("Edit Booking")) {
            pickup.setEditable(true);
            finall.setEditable(true);
            stat_date.setEnabled(true); 
            end_date.setEnabled(true);
            jLabel10.setText("Save Changes");
        } else {
            Date sDate = stat_date.getDate();
            Date eDate = end_date.getDate();

            // 1. Validation
            if (sDate == null || eDate == null) {
                JOptionPane.showMessageDialog(this, "Please select both dates.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (eDate.before(sDate)) {
                JOptionPane.showMessageDialog(this, "End Date cannot be before Start Date!", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Calculate Duration and Price
            long diffInMillies = Math.abs(eDate.getTime() - sDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
            double newPrice = calculateNewTotal(diffInDays);
            String formattedPrice = "₱" + String.format("%.2f", newPrice);

            // 3. --- NEW: CONFIRMATION DIALOG ---
            String message = "Updated Trip Duration: " + diffInDays + " day(s)\n" +
                             "New Calculated Total: " + formattedPrice + "\n\n" +
                             "Do you want to save these changes?";
            
            int confirm = JOptionPane.showConfirmDialog(this, message, "Confirm Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // 4. SQL Update
                String bid = bookId.getText();
                String sStr = dateFormat.format(sDate);
                String eStr = dateFormat.format(eDate);

                config.dbConnector connector = config.dbConnector.getInstance();
                String query = "UPDATE tbl_bookings SET pick_up = '" + pickup.getText() + 
                               "', destination = '" + finall.getText() + 
                               "', start_date = '" + sStr + 
                               "', end_date = '" + eStr + 
                               "', total_price = '" + newPrice + "' WHERE b_id = '" + bid + "'";

                if (connector.updateData(query)) {
                    total.setText(formattedPrice); // Update UI text field
                    lockFields();
                    jLabel10.setText("Edit Booking");
                    JOptionPane.showMessageDialog(this, "Booking updated successfully!");
                }
            }
        }
    
    }//GEN-LAST:event_editMouseClicked

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
                                   
    // Check if the label says Cancel (this handles the Rebook toggle logic)
    if (jLabel9.getText().equalsIgnoreCase("Cancel Booking")) {
        
        // 1. Double-check with the user
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel this booking?\nThis will release all reserved vans.", 
            "Confirm Cancellation", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            config.dbConnector connector = config.dbConnector.getInstance();
            String bid = bookId.getText();

            // 2. Update the Booking Table
            String updateBookingQuery = "UPDATE tbl_bookings SET status = 'Cancelled' WHERE b_id = '" + bid + "'";
            
            if (connector.updateData(updateBookingQuery)) {
                
                // 3. Update the Fleet (The Van Table)
                releaseVans();
                
                // 4. Update UI and Notify
                status.setText("Cancelled");
                status.setForeground(java.awt.Color.RED);
                edit.setVisible(false); // Hide edit button for cancelled trips
                
                JOptionPane.showMessageDialog(this, "Booking successfully cancelled and fleet updated.");
                
                // 5. Redirect back to Dashboard
                new dashboard.customerDashboard().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Database Error: Could not update booking status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else if (jLabel9.getText().equalsIgnoreCase("Rebook Trip")) {
        // Optional: Logic to send user back to the booking selection screen
        JOptionPane.showMessageDialog(this, "Redirecting to booking page...");
    }



    



    }//GEN-LAST:event_cancelMouseClicked

    private void downloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadMouseClicked
                                      
                                        
    // 1. Initial Check: Don't allow download if the booking isn't ready
    if (status.getText().equalsIgnoreCase("Cancelled")) {
        JOptionPane.showMessageDialog(this, "Cannot download receipt for a cancelled booking.");
        return;
    }

    com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
    
    try {
        // Create the file path
        String fileName = "Receipt_Booking_" + bookId.getText() + ".pdf";
        String path = System.getProperty("user.home") + "/Downloads/" + fileName;
        PdfWriter.getInstance(document, new FileOutputStream(path));
        
        document.open();

        // --- 1. HEADER ---
        Paragraph companyName = new Paragraph("VAN GO TRAVEL & TOURS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
        companyName.setAlignment(Element.ALIGN_CENTER);
        document.add(companyName);
        
        Paragraph subHeader = new Paragraph("Official Trip Receipt\nGenerated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + "\n\n");
        subHeader.setAlignment(Element.ALIGN_CENTER);
        document.add(subHeader);

        // --- 2. VEHICLE DETAILS ---
        document.add(new Paragraph("VEHICLE SUMMARY", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        document.add(new Paragraph(" "));
        
        PdfPTable vanTable = new PdfPTable(2); // 2 columns: Image and Model Name
        vanTable.setWidthPercentage(100);
        
        for (config.VanModel van : bookedVans) {
            try {
                // Image Cell
                com.itextpdf.text.Image pdfImg = com.itextpdf.text.Image.getInstance(van.getVimage());
                pdfImg.scaleToFit(100, 70);
                PdfPCell imgCell = new PdfPCell(pdfImg);
                imgCell.setBorder(Rectangle.NO_BORDER);
                vanTable.addCell(imgCell);
                
                // Text Cell
                PdfPCell textCell = new PdfPCell(new Phrase(van.getModel(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                textCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                textCell.setBorder(Rectangle.NO_BORDER);
                vanTable.addCell(textCell);
            } catch (Exception e) {
                vanTable.addCell(new Phrase("Image Missing"));
                vanTable.addCell(new Phrase(van.getModel()));
            }
        }
        document.add(vanTable);
        document.add(new Paragraph("\n"));

        // --- 3. BOOKING INFORMATION ---
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        
        addInfoRow(infoTable, "Booking ID:", bookId.getText());
        addInfoRow(infoTable, "Customer Name:", customerName.getText());
        addInfoRow(infoTable, "Route:", pickup.getText() + " to " + finall.getText());
        addInfoRow(infoTable, "Travel Dates:", dateFormat.format(stat_date.getDate()) + " to " + dateFormat.format(end_date.getDate()));
        
        // Highlight Total
        PdfPCell totalLabel = new PdfPCell(new Phrase("TOTAL PAID:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        totalLabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
        infoTable.addCell(totalLabel);
        
        PdfPCell totalVal = new PdfPCell(new Phrase(total.getText(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLUE)));
        totalVal.setBackgroundColor(BaseColor.LIGHT_GRAY);
        infoTable.addCell(totalVal);
        
        document.add(infoTable);

        // --- 4. STOPOVERS ---
        if (stopoverTable.getRowCount() > 0) {
            document.add(new Paragraph("\nSTOPOVERS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            PdfPTable stopTable = new PdfPTable(2);
            stopTable.setWidthPercentage(100);
            for (int i = 0; i < stopoverTable.getRowCount(); i++) {
                stopTable.addCell(stopoverTable.getValueAt(i, 0).toString());
                stopTable.addCell(stopoverTable.getValueAt(i, 1).toString());
            }
            document.add(stopTable);
        }

        document.close();
        
        // --- 5. FINAL TOUCH: AUTO-OPEN FILE ---
        int open = JOptionPane.showConfirmDialog(this, "Receipt saved to Downloads. Would you like to open it now?", "Success", JOptionPane.YES_NO_OPTION);
        if (open == JOptionPane.YES_OPTION) {
            java.awt.Desktop.getDesktop().open(new java.io.File(path));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error generating PDF: " + e.getMessage());
    }

    }//GEN-LAST:event_downloadMouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
       customerDashboard cd = new customerDashboard();
       cd.setVisible(true);
       dispose();
    }//GEN-LAST:event_jLabel15MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      /* Create and display the form */
java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        // Pass null or empty values just to satisfy the compiler for testing
        new BookingDetails("0", "Test User", "A", "B", "C", "D", "0", "Pending", 
            new java.util.ArrayList<>(), new java.util.ArrayList<>()).setVisible(true);
    }
});
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bookId;
    private javax.swing.JPanel cancel;
    private javax.swing.JTextField customerName;
    private javax.swing.JPanel download;
    private javax.swing.JPanel edit;
    private com.toedter.calendar.JDateChooser end_date;
    private javax.swing.JTextField finall;
    private javax.swing.JLabel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField pickup;
    private com.toedter.calendar.JDateChooser stat_date;
    private javax.swing.JTextField status;
    private javax.swing.JTable stopoverTable;
    private javax.swing.JTextField total;
    private javax.swing.JScrollPane vandisplay;
    // End of variables declaration//GEN-END:variables
}
