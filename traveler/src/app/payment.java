/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

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
import config.dbConnector;
import dashboard.customerDashboard;
import java.awt.Color;
import java.awt.Font;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


public class payment extends javax.swing.JFrame {
  
    private String b_id, route, total, firstName, lastName;
   
   
  public payment(String bid, String route, String total, String fname, String lname) {
       
        this.setUndecorated(true);
        initComponents();
     
        this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        
        this.b_id = bid;
        this.route = route;
        this.total = total;
        this.firstName = fname;
        this.lastName = lname;

        // Display basic info
        bookingID.setText(bid);
        total_amount.setText(total);
        name.setText(fname + " " + lname); // Set the customer name
        
        checkExistingStatus();
        
        // --- UI INITIALIZATION ---
        download.setVisible(false); // Hide download by default
        handleSelection(); // Check if Gcash is default to show/hide QR
        
        // Add Listener to ComboBox for Dynamic QR Display
        paymentOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSelection();
            }
        });
        
        this.setLocationRelativeTo(null);
    }

  public payment() {
        this("0", "Sample Route", "₱0.00", "Firstname", "Lastname");
    }
  
  
  private void checkExistingStatus() {
        try {
            config.dbConnector connector = config.dbConnector.getInstance();
            String query = "SELECT status, ref_no FROM tbl_bookings WHERE b_id = '" + b_id + "'";
            java.sql.ResultSet rs = connector.getData(query);

            if (rs.next()) {
                String status = rs.getString("status");
                String ref = rs.getString("ref_no");

                if ("Paid".equalsIgnoreCase(status)) {
                    download.setVisible(true);
                    ref_number.setText(ref);
                    ref_number.setEditable(false);
                    ref_number.setBackground(new Color(204, 255, 204));
                }
            }
        } catch (Exception e) {
            System.out.println("Status Check Error: " + e.getMessage());
        }
    }
  
 private void handleSelection() {
        // Logic: Show QR only if Gcash is selected
        if (paymentOption.getSelectedItem().toString().equalsIgnoreCase("Gcash")) {
            qr_holder.setVisible(true);
            jLabel2.setText("Scan the GCash QR and enter Ref #:");
        } else {
            qr_holder.setVisible(false);
            jLabel2.setText("Choose payment option:");
        }
    }
  
 
 private void processPayment() {
        String ref = ref_number.getText().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (ref.isEmpty() || ref.length() < 5) {
            JOptionPane.showMessageDialog(this, "Please enter a valid GCash Reference Number.");
            return;
        }

        try {
            config.dbConnector connector = config.dbConnector.getInstance();
            
            // Insert into your payment table
            String insertPayment = "INSERT INTO tbl_payment (b_id, a_id, amount, method, ref_no, date) "
                    + "VALUES ('" + b_id + "', '1', '" + total + "', 'GCash', '" + ref + "', '" + date + "')";

            // Update booking status
            String updateBooking = "UPDATE tbl_bookings SET status = 'Paid', ref_no = '"+ref+"' WHERE b_id = '" + b_id + "'";

            if (connector.insertData(insertPayment) > 0) {
                connector.updateData(updateBooking);
                
                download.setVisible(true);
                JOptionPane.showMessageDialog(this, "Payment Recorded Successfully!");
                
                ref_number.setEditable(false);
                ref_number.setBackground(new Color(204, 255, 204));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

private void generateReceiptPDF() {
    Document document = new Document(PageSize.A4);
    try {
        // Automatically locate the Windows Downloads folder
        String userHome = System.getProperty("user.home");
        String fileName = "TravelerExpress_Receipt_" + b_id + ".pdf";
        File downloadPath = new File(userHome + "/Downloads/" + fileName);

        PdfWriter.getInstance(document, new FileOutputStream(downloadPath));
        document.open();

        // --- FONTS ---
        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
        com.itextpdf.text.Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        com.itextpdf.text.Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        com.itextpdf.text.Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 14);
        com.itextpdf.text.Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, com.itextpdf.text.Font.ITALIC);

        // --- HEADER ---
        Paragraph title = new Paragraph("TRAVELER EXPRESS CEBU", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph subTitle = new Paragraph("Official Payment Receipt", subTitleFont);
        subTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitle);

        document.add(new Paragraph("\n")); // Spacer
        document.add(new Paragraph("=========================================================="));
        document.add(new Paragraph("\n")); 

        // --- CENTERED CONTENT (No Table Borders) ---
        // We still use a table as a layout tool because it's the best way to center content in iText, 
        // but we set the border to NONE.
        PdfPTable layoutTable = new PdfPTable(2);
        layoutTable.setWidthPercentage(70); // Narrower for a centered look
        layoutTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        layoutTable.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Helper to add centered rows
        addProfessionalRow(layoutTable, "Booking Reference:", b_id, labelFont, valueFont);
        addProfessionalRow(layoutTable, "Passenger Name:", firstName + " " + lastName, labelFont, valueFont);
        addProfessionalRow(layoutTable, "Pick-up Point:",route, labelFont, valueFont);
      //  addProfessionalRow(layoutTable, "Final Destination:", finall.getText(), labelFont, valueFont);
        addProfessionalRow(layoutTable, "Total Paid:" ,"PHP " + total_amount.getText(), labelFont, valueFont);
        addProfessionalRow(layoutTable, "Payment Status:", "VERIFIED & PAID", labelFont, valueFont);

        document.add(layoutTable);

        // --- FOOTER ---
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("=========================================================="));
        
        Paragraph thankYou = new Paragraph("Thank you for choosing Traveler Express! Have a safe trip.", subTitleFont);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        document.add(thankYou);

        Paragraph dateGen = new Paragraph("Generated on: " + new SimpleDateFormat("MMMM dd, yyyy - hh:mm a").format(new Date()), footerFont);
        dateGen.setAlignment(Element.ALIGN_CENTER);
        document.add(dateGen);

        document.close();

        JOptionPane.showMessageDialog(this, "Receipt successfully downloaded to Downloads folder!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

// Helper method to keep the layout code clean
private void addProfessionalRow(PdfPTable table, String label, String value, com.itextpdf.text.Font lFont, com.itextpdf.text.Font vFont) {
    PdfPCell cell1 = new PdfPCell(new Phrase(label, lFont));
    cell1.setBorder(PdfPCell.NO_BORDER);
    cell1.setPadding(8);
    table.addCell(cell1);

    PdfPCell cell2 = new PdfPCell(new Phrase(value, vFont));
    cell2.setBorder(PdfPCell.NO_BORDER);
    cell2.setPadding(8);
    table.addCell(cell2);
}
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        paymentOption = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        download = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        bookingID = new javax.swing.JTextField();
        ref_number = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        total_amount = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        qr_holder = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        paymentOption.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        paymentOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gcash", "Credit/Debit Cards" }));
        jPanel1.add(paymentOption, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 520, 40));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setText("Choose payment option:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 160, -1));

        download.setBackground(new java.awt.Color(12, 33, 74));
        download.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadMouseClicked(evt);
            }
        });
        download.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DOWNLOAD RECEIPT");
        download.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 170, -1));

        jPanel1.add(download, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 770, 170, 40));

        bookingID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        bookingID.setBorder(null);
        jPanel1.add(bookingID, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 260, 40));

        ref_number.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        ref_number.setBorder(null);
        ref_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ref_numberActionPerformed(evt);
            }
        });
        jPanel1.add(ref_number, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 230, 40));

        jPanel2.setBackground(new java.awt.Color(12, 33, 74));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PAYMENT");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 750, 30));

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/back_white.png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        jPanel2.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 14, 50, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 60));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("Booking Id:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 80, 40));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setText(" Customer Name :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 130, -1));

        name.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        name.setBorder(null);
        jPanel1.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 230, 40));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setText("Total Amount:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 100, 20));

        total_amount.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        total_amount.setBorder(null);
        jPanel1.add(total_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 230, 40));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setText("Reference #:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, -1, -1));

        qr_holder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/gcash_qr.jpg"))); // NOI18N
        jPanel1.add(qr_holder, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 330, 350));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void downloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadMouseClicked
       if(download.isVisible()){
           generateReceiptPDF();
           
       }
        
    }//GEN-LAST:event_downloadMouseClicked

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
       customerDashboard cd = new customerDashboard();
       cd.setVisible(true);
       dispose();
    }//GEN-LAST:event_backMouseClicked

    private void ref_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ref_numberActionPerformed
        processPayment();
    }//GEN-LAST:event_ref_numberActionPerformed

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
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new payment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel back;
    private javax.swing.JTextField bookingID;
    private javax.swing.JPanel download;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField name;
    private javax.swing.JComboBox<String> paymentOption;
    private javax.swing.JLabel qr_holder;
    private javax.swing.JTextField ref_number;
    private javax.swing.JTextField total_amount;
    // End of variables declaration//GEN-END:variables
}
