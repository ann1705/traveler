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
import java.awt.Image;

// JAVA & SWING Imports
import java.io.FileOutputStream;
import java.util.List;
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
    
    public BookingDetails(String bid, String cName, String p, String d, String s, String e, String t, String stat, List<config.VanModel> vans, List<String> stops) {
    initComponents();
   this.bookedVans = vans;
    this.bookedStops = stops;
    
    // Set Fields
    bookId.setText(bid);       
    customerName.setText(cName);
    pickup.setText(p);
    finall.setText(d);
    stat_date.setText(s);
    end_date.setText(e);
    total.setText(t);
    status.setText(stat);

    // LOGIC FOR HEADER AND REBOOKING
    if (stat.equalsIgnoreCase("Cancelled") || stat.equalsIgnoreCase("Completed")) {
        header.setText("BOOKING HISTORY"); // Dynamic Header
        edit.setVisible(false);
        download.setVisible(stat.equalsIgnoreCase("Completed")); // Only allow download if trip happened
        
        jLabel9.setText("Rebook Trip");
        cancel.setBackground(new java.awt.Color(0, 153, 51)); // Success Green
        
        status.setForeground(stat.equalsIgnoreCase("Cancelled") ? java.awt.Color.RED : new java.awt.Color(0, 102, 204));
    } else {
        header.setText("BOOKING DETAILS");
        status.setForeground(java.awt.Color.BLACK);
    }

    lockFields();
    displayVans();      
    displayStopovers(); 
    this.setLocationRelativeTo(null);
}

    
    private void lockFields() {
        bookId.setEditable(false);
        customerName.setEditable(false);
        pickup.setEditable(false);
        finall.setEditable(false);
        stat_date.setEditable(false);
        end_date.setEditable(false);
        total.setEditable(false);
        status.setEditable(false);
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
        // Ensure table has columns defined
        model.setColumnIdentifiers(new String[]{"Location Name", "Price"});
        model.setRowCount(0);

        for (String stop : bookedStops) {
            // Assuming format: "Location Name - ₱Price [ID]"
            try {
                String name = stop.split("-")[0].trim();
                String price = stop.substring(stop.indexOf("₱"));
                model.addRow(new Object[]{name, price});
            } catch (Exception ex) {
                model.addRow(new Object[]{stop, ""});
            }
        }
    }
    
private void addInfoRow(PdfPTable table, String label, String value) {
    com.itextpdf.text.Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    com.itextpdf.text.Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
    
    PdfPCell cell1 = new PdfPCell(new Phrase(label, bold));
    cell1.setPadding(5);
    table.addCell(cell1);
    
    PdfPCell cell2 = new PdfPCell(new Phrase(value, normal));
    cell2.setPadding(5);
    table.addCell(cell2);
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
        stat_date = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        end_date = new javax.swing.JTextField();
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(76, 143, 209));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("BOOKING DETAILS");
        jPanel2.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 790, 30));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/goback.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 60));

        vandisplay.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(vandisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 980, 330));

        stopoverTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(stopoverTable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, -1, 160));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("STOPOVERS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 250, -1));
        jPanel1.add(pickup, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 510, 160, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setText("BOOKING DETAILS");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 400, 230, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setText("Pick-up Location :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 510, 130, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("Final Destination : ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 540, 140, -1));
        jPanel1.add(finall, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 540, 160, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setText("Start Date :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 570, 100, -1));
        jPanel1.add(stat_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 570, 160, -1));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setText("End Date :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 600, 90, -1));
        jPanel1.add(end_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 600, 160, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setText("Total Cost : ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 630, 90, -1));
        jPanel1.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 630, 160, -1));

        download.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadMouseClicked(evt);
            }
        });
        download.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Download");
        download.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 30));

        jPanel1.add(download, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 680, 110, 30));

        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });
        cancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cancel Booking");
        cancel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 30));

        jPanel1.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 180, 30));

        edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMouseClicked(evt);
            }
        });
        edit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Edit Booking");
        edit.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 30));

        jPanel1.add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 630, 160, 30));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setText("Status :");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 660, 60, -1));
        jPanel1.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 660, 160, 30));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setText("Booking ID :");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 440, -1, -1));
        jPanel1.add(bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 440, 160, -1));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setText("Customer Name :");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 480, -1, -1));
        jPanel1.add(customerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 480, 160, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseClicked
                               
    if (jLabel10.getText().equals("Edit Booking")) {
        pickup.setEditable(true);
        finall.setEditable(true);
        stat_date.setEditable(true);
        end_date.setEditable(true);
        jLabel10.setText("Save Changes");
    } else {
        // --- ACTUAL SQL UPDATE LOGIC ---
        String bid = bookId.getText(); // Get the REAL ID from the label
        String p = pickup.getText();
        String d = finall.getText();
        String s = stat_date.getText();
        String e = end_date.getText();

        config.dbConnector connector = config.dbConnector.getInstance();
        String query = "UPDATE tbl_bookings SET pick_up = '" + p + "', destination = '" + d + 
                       "', start_date = '" + s + "', end_date = '" + e + "' WHERE b_id = '" + bid + "'";

        if (connector.updateData(query)) {
            lockFields();
            jLabel10.setText("Edit Booking");
            JOptionPane.showMessageDialog(this, "Booking #" + bid + " updated successfully!");
        }
    }


    }//GEN-LAST:event_editMouseClicked

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
                                       
    config.dbConnector connector = config.dbConnector.getInstance();
    String id = bookId.getText();

    if (jLabel9.getText().equals("Cancel Booking")) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Update Booking Status to Cancelled
            String updateBooking = "UPDATE tbl_bookings SET status = 'Cancelled' WHERE b_id = '" + id + "'";
            
            if (connector.updateData(updateBooking)) {
                
                // 2. Loop through bookedVans and set each van back to 'Available'
                for (config.VanModel van : bookedVans) {
                    // Assuming your VanModel has an ID getter like getVid()
                    String updateVan = "UPDATE tbl_vans SET v_status = 'Available' WHERE v_id = '" + van.getVid() + "'";
                    connector.updateData(updateVan);
                }
                
                JOptionPane.showMessageDialog(this, "Booking Cancelled and Vans are now Available.");
                
                // Refresh UI
                new customerDashboard().setVisible(true);
                this.dispose();
            }
        }
    } else {
        // ... Your existing REBOOKING logic ...
        // (Note: If rebooking, you should set vans to 'Booked' again here)
    }





    }//GEN-LAST:event_cancelMouseClicked

    private void downloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadMouseClicked
                                      
    com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
    
    try {
        String path = System.getProperty("user.home") + "/Downloads/Receipt_Booking_" + bookId.getText() + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        // --- 1. COMPANY HEADER ---
        try {
            java.net.URL logoURL = getClass().getResource("/icons/logo.png"); // Ensure path is correct
            if (logoURL != null) {
                com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(logoURL);
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            }
        } catch (Exception e) { /* Skip logo if not found */ }

        Paragraph headerName = new Paragraph("VAN GO TRAVEL & TOURS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
        headerName.setAlignment(Element.ALIGN_CENTER);
        document.add(headerName);
        document.add(new Paragraph("Official Trip Receipt\n\n", FontFactory.getFont(FontFactory.HELVETICA, 12)));

        // --- 2. VAN IMAGES SECTION ---
        document.add(new Paragraph("SELECTED VEHICLES", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        document.add(new Paragraph(" "));
        
        PdfPTable vanTable = new PdfPTable(bookedVans.size());
        vanTable.setWidthPercentage(100);
        
        for (config.VanModel van : bookedVans) {
          try {
    String vPath = van.getVimage();
    // 1. Clean the path (remove "src" if it exists)
    String resPath = vPath.startsWith("src") ? vPath.substring(3) : vPath;
    if (!resPath.startsWith("/")) resPath = "/" + resPath;
    
    java.net.URL vUrl = getClass().getResource(resPath);
    
    // 2. Use the fully qualified name for the iText Image class
    com.itextpdf.text.Image pdfImg;
    
    if (vUrl != null) {
        // Load from project resources (JAR compatible)
        pdfImg = com.itextpdf.text.Image.getInstance(vUrl);
    } else {
        // Fallback to absolute file path
        pdfImg = com.itextpdf.text.Image.getInstance(vPath);
    }
    
    pdfImg.scaleToFit(140, 100);
    
    PdfPCell cell = new PdfPCell();
    cell.addElement(pdfImg);
    cell.addElement(new Paragraph(van.getModel(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    vanTable.addCell(cell);
    
} catch (Exception e) {
    // If the image absolutely cannot be found, add a text placeholder so the PDF doesn't crash
    PdfPCell errorCell = new PdfPCell(new Phrase("Image Error: " + van.getModel()));
    errorCell.setBorder(Rectangle.NO_BORDER);
    vanTable.addCell(errorCell);
    System.out.println("PDF Image Error for " + van.getModel() + ": " + e.getMessage());
}
        }
        document.add(vanTable);
        document.add(new Paragraph("\n"));

        // --- 3. BOOKING INFORMATION ---
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        
        // Currency Styling for Total
        com.itextpdf.text.Font priceFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLUE);

        addInfoRow(infoTable, "Booking ID:", bookId.getText());
        addInfoRow(infoTable, "Customer Name:", customerName.getText());
        addInfoRow(infoTable, "Pick-up Location:", pickup.getText());
        addInfoRow(infoTable, "Destination:", finall.getText());
        addInfoRow(infoTable, "Travel Dates:", stat_date.getText() + " to " + end_date.getText());
        
        // Custom Row for Total Price
        PdfPCell totalLabel = new PdfPCell(new Phrase("TOTAL AMOUNT:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        totalLabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
        infoTable.addCell(totalLabel);
        
        PdfPCell totalVal = new PdfPCell(new Phrase(total.getText(), priceFont));
        totalVal.setBackgroundColor(BaseColor.LIGHT_GRAY);
        infoTable.addCell(totalVal);
        
        document.add(infoTable);

        // --- 4. STOPOVERS TABLE ---
        document.add(new Paragraph("\nSTOPOVERS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        PdfPTable stopTable = new PdfPTable(2);
        stopTable.setWidthPercentage(100);
        stopTable.setSpacingBefore(5f);

        stopTable.addCell(new PdfPCell(new Phrase("Location", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
        stopTable.addCell(new PdfPCell(new Phrase("Surcharge", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

        for (int i = 0; i < stopoverTable.getRowCount(); i++) {
            stopTable.addCell(stopoverTable.getValueAt(i, 0).toString());
            stopTable.addCell(stopoverTable.getValueAt(i, 1).toString());
        }
        document.add(stopTable);

        document.close();
        JOptionPane.showMessageDialog(this, "Complete PDF Receipt created successfully!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "PDF Error: " + e.getMessage());
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
    private javax.swing.JTextField end_date;
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
    private javax.swing.JTextField stat_date;
    private javax.swing.JTextField status;
    private javax.swing.JTable stopoverTable;
    private javax.swing.JTextField total;
    private javax.swing.JScrollPane vandisplay;
    // End of variables declaration//GEN-END:variables
}
