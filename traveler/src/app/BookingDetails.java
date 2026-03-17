
package app;


// ITEXT PDF Imports

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import config.Session;
import dashboard.customerDashboard;



// JAVA & SWING Imports
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import config.VanModel; // Add this line
import config.dbConnector;
import javax.swing.table.DefaultTableCellRenderer;


// ITEXT PDF Imports


/**
 *
 * @author RageKing
 */
public class BookingDetails extends javax.swing.JFrame {
    private String bid;
    private List<VanModel> bookedVans = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int xMouse, yMouse;
    
    
   public BookingDetails(String bid, String cName, String p, String d, String s, String e, String t, String stat) {
        this.setUndecorated(true);
        initComponents();
        this.bid = bid;

        // UI Styling
        styleTable(stopoverTable);
        styleTable(assignedTable);
        
        // Populate Initial Data
        bookId.setText(bid);
        customerName.setText(cName);
        pickup.setText(p);
        finall.setText(d);
        total.setText(t);
        status.setText(stat);
        this.bid = bid; 
       bookId.setText(bid);
        // Load Relational Data
        setupTableModels();
        loadFullBookingData(bid); 
        loadVansFromDB();      
       displayVans();         
        displayStopovers();    
       loadDrivers();

        // Security & UI State
        setFieldsLocked(true); 
        refreshUIState(stat);
        
        // Window Rounding
        this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        this.setLocationRelativeTo(null);
    }

    public BookingDetails() {
        initComponents();
    }
     
    private void setupTableModels() {
        assignedTable.setDefaultEditor(Object.class, null);
        stopoverTable.setDefaultEditor(Object.class, null);
    }

    private void loadFullBookingData(String bid) {
        try {
            dbConnector connector = dbConnector.getInstance();
            String query = "SELECT b.*, a.firstname, a.lastname FROM tbl_bookings b " +
                           "JOIN tbl_account a ON b.a_id = a.a_id " +
                           "WHERE b.b_id = '" + bid + "'";
                               
            ResultSet rs = connector.getData(query);
            if (rs.next()) {
                customerName.setText(rs.getString("firstname") + " " + rs.getString("lastname"));
                status.setText(rs.getString("status"));
                pickup.setText(rs.getString("pick_up"));
                finall.setText(rs.getString("destination"));
                total.setText("₱" + String.format("%.2f", rs.getDouble("total_price")));
                stat_date.setDate(dateFormat.parse(rs.getString("start_date")));
                end_date.setDate(dateFormat.parse(rs.getString("end_date")));
            }
        } catch (Exception e) {
            System.err.println("Load Error: " + e.getMessage());
        }
    }

private void loadDrivers() {
    DefaultTableModel model = (DefaultTableModel) assignedTable.getModel();
    model.setColumnIdentifiers(new String[]{"Driver Name", "Van Model"});
    model.setRowCount(0);

    try {
        dbConnector connector = dbConnector.getInstance();
        // Use tbl_booking_items as the bridge between bookings and vans
        String query = "SELECT a.firstname, a.lastname, v.model " +
                       "FROM tbl_booking_items bi " +
                       "JOIN tbl_vans v ON bi.v_id = v.v_id " +
                       "JOIN tbl_bookings b ON bi.b_id = b.b_id " +
                       "LEFT JOIN tbl_account a ON b.driver_id = a.a_id " +
                       "WHERE bi.b_id = " + bid;
            
        ResultSet rs = connector.getData(query);
        boolean found = false;
        while (rs.next()) {
            String fName = rs.getString("firstname");
            String lName = rs.getString("lastname");
            String dName = (fName == null) ? "Pending Assignment" : fName + " " + lName;
            model.addRow(new Object[]{dName, rs.getString("model")});
            found = true;
        }
        if (!found) model.addRow(new Object[]{"Unassigned", "No Vans Found"});
    } catch (Exception e) { 
        System.err.println("Driver/Van Load Error: " + e.getMessage()); 
    }
}
  
    
private void loadVansFromDB() {
    try {
        dbConnector connector = dbConnector.getInstance();
        // Using your tbl_booking_items bridge
        String query = "SELECT v.* FROM tbl_vans v " +
                       "JOIN tbl_booking_items bi ON v.v_id = bi.v_id " +
                       "WHERE bi.b_id = " + bid;
        
        ResultSet rs = connector.getData(query);
        bookedVans.clear();
        while (rs.next()) {
            bookedVans.add(new VanModel(
                rs.getInt("v_id"),
                rs.getString("model"),
                rs.getString("daily_rate"),
                rs.getString("capacity"),
                rs.getString("image")
            ));
        }
    } catch (Exception e) {
        System.out.println("Image Load Error: " + e.getMessage());
    }
}

   private void setLocked(boolean isLocked) {
    boolean canEdit = !isLocked;
    
    // Disable Text Fields
    pickup.setEditable(canEdit);
    finall.setEditable(canEdit);
    // These should always be uneditable to maintain data integrity
    total.setEditable(false);
    bookId.setEditable(false);
    customerName.setEditable(false);
    status.setEditable(false);

    // Disable Date Choosers
    stat_date.setEnabled(canEdit);
    end_date.setEnabled(canEdit);

    // Disable Tables (Prevents clicking/editing cells)
    stopoverTable.setEnabled(canEdit);
    assignedTable.setEnabled(canEdit);
}

   private void styleTable(javax.swing.JTable table) {
        table.setBackground(java.awt.Color.WHITE);
        table.setRowHeight(28);
        table.setGridColor(new java.awt.Color(235, 235, 235));
        table.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(new java.awt.Color(12, 33, 74));
                setForeground(java.awt.Color.WHITE);
                setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
                setHorizontalAlignment(JLabel.CENTER);
                return this;
            }
        };
        table.getTableHeader().setDefaultRenderer(headerRenderer);
    }
   
// Inside your BookingDetails constructor or data loader:
    public void checkStatusPermissions(String currentStatus) {
    // List of statuses where editing is FORBIDDEN
    if (currentStatus.equalsIgnoreCase("Paid") || 
        currentStatus.equalsIgnoreCase("Dispatched") || 
        currentStatus.equalsIgnoreCase("Pick-up") || 
        currentStatus.equalsIgnoreCase("Completed")) {
        
        edit.setVisible(false);
        paynow.setVisible(false);
    } else {
        edit.setVisible(true);
        paynow.setVisible(true);
    }
}
    private void styleAssignedTable() {
    assignedTable.setBackground(new java.awt.Color(255, 255, 255));
    assignedTable.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
    assignedTable.setRowHeight(25);
    assignedTable.setShowGrid(true);
    assignedTable.setGridColor(new java.awt.Color(230, 230, 230));
}
    // Inside your BookingDetails constructor or data loader
public void refreshData(String bid) {
    this.bid = bid;
    loadFullBookingData(bid); // Loads basic info (names, dates)
    loadVansFromDB();        // <--- CRITICAL: Refresh the van list
    displayVans();           // <--- CRITICAL: Re-render the UI cards
   // loadStopoversFromDB();   // <--- CRITICAL: Pull stopovers from tbl_stopovers
}
   
 private void refreshUIState(String stat) {
        if (stat == null) return;
        String currentStatus = stat.trim();

        edit.setVisible(false);
        paynow.setVisible(false);
        cancel.setVisible(true);

        switch (currentStatus.toLowerCase()) {
            case "pending":
            case "booked":
                status.setForeground(new java.awt.Color(255, 153, 0));
                edit.setVisible(true);
                paynow.setVisible(true);
                jLabel9.setText("Cancel Booking");
                break;
            case "paid":
            case "completed":
                status.setForeground(new java.awt.Color(0, 153, 0));
                paynow.setVisible(false);
                break;
            case "cancelled":
                status.setForeground(java.awt.Color.RED);
                jLabel9.setText("Rebook Trip");
                break;
        }
    }

private void setFieldsLocked(boolean locked) {
        boolean canEdit = !locked;
        pickup.setEditable(canEdit);
        finall.setEditable(canEdit);
        stat_date.setEnabled(canEdit);
        end_date.setEnabled(canEdit);
        
        // These fields are always read-only
        bookId.setEditable(false);
        customerName.setEditable(false);
        total.setEditable(false);
        status.setEditable(false);
    }



     
    private double calculateNewTotal(long days) {
        double ratePerDay = 2500.00; 
        double stopoverTotal = 0;

        for (int i = 0; i < stopoverTable.getRowCount(); i++) {
            try {
                String priceStr = stopoverTable.getValueAt(i, 1).toString()
                        .replace("₱", "").replace(",", "").trim();
                stopoverTotal += Double.parseDouble(priceStr);
            } catch (Exception e) {
                stopoverTotal += 0;
            }
        }
        return (ratePerDay * bookedVans.size() * days) + stopoverTotal;
    }
    
private void handleRebooking() {
    config.dbConnector connector = config.dbConnector.getInstance();
    List<String> busyVans = new ArrayList<>();
    
    try {
        // 1. Check availability for all previously booked vans
        for (config.VanModel van : bookedVans) {
            String query = "SELECT status FROM tbl_vans WHERE v_id = " + van.getVid();
            ResultSet rs = connector.getData(query);
            
            if (rs.next()) {
                String vStat = rs.getString("status");
                // If any van is not "Available", add it to the 'busy' list
                if (!vStat.equalsIgnoreCase("Available")) {
                    busyVans.add(van.getModel() + " (" + vStat + ")");
                }
            }
        }

        // 2. Logic decision based on van availability
        if (busyVans.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "All vans are available. Rebook this trip?", "Confirm Rebooking", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Update booking status back to 'Booked'
                connector.updateData("UPDATE tbl_bookings SET status = 'Booked' WHERE b_id = '" + bookId.getText() + "'");
                
                // Update each van's status to 'Booked'
                for (config.VanModel v : bookedVans) {
                    connector.updateData("UPDATE tbl_vans SET status = 'Booked' WHERE v_id = " + v.getVid());
                }

                JOptionPane.showMessageDialog(this, "Trip Rebooked Successfully!");

                // Sync UI
                status.setText("Booked");
                refreshUIState("Booked"); 
            }
        } else {
            // Display which vans are unavailable
            String list = String.join("\n", busyVans);
            JOptionPane.showMessageDialog(this, 
                "Cannot rebook. The following vans are currently unavailable:\n\n" + list + 
                "\n\nPlease create a new booking with different vans.");
            
            this.dispose();
            new app.bookingForm().setVisible(true);
        }
    } catch (Exception e) { 
        System.err.println("Rebook logic error: " + e.getMessage()); 
        JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
    }
}

private void releaseVans() {
        dbConnector connector = dbConnector.getInstance();
        for (VanModel van : bookedVans) {
            connector.updateData("UPDATE tbl_vans SET status = 'Available' WHERE v_id = " + van.getVid());
        }
    }
    
private void displayVans() {
        javax.swing.JPanel container = new javax.swing.JPanel();
        container.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 20));
        container.setBackground(java.awt.Color.WHITE);
 
        for (VanModel van : bookedVans) {
            javax.swing.JPanel card = new javax.swing.JPanel();
            card.setPreferredSize(new java.awt.Dimension(280, 220));
            card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));
            card.setBackground(java.awt.Color.WHITE);
            card.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230), 2));
     
            JLabel pic = new JLabel();
            pic.setHorizontalAlignment(JLabel.CENTER);

            String path = van.getVimage(); 
            if (path != null && !path.isEmpty()) {
                java.io.File file = new java.io.File(path);
                if (file.exists()) {
                    ImageIcon icon = new ImageIcon(path);
                    java.awt.Image img = icon.getImage().getScaledInstance(240, 140, java.awt.Image.SCALE_SMOOTH);
                    pic.setIcon(new ImageIcon(img));
                } else {
                    pic.setText("No Image");
                }
            }

            JLabel modelLabel = new JLabel(van.getModel());
            modelLabel.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 15));
            modelLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            pic.setAlignmentX(JLabel.CENTER_ALIGNMENT);

            card.add(javax.swing.Box.createVerticalStrut(10));
            card.add(pic);
            card.add(javax.swing.Box.createVerticalStrut(10));
            card.add(modelLabel);
            container.add(card);
        }
        vandisplay.setViewportView(container);
    }

private void displayStopovers() {
    DefaultTableModel model = (DefaultTableModel) stopoverTable.getModel();
    model.setColumnIdentifiers(new String[]{"Location", "Price"});
    model.setRowCount(0);
    try {
        dbConnector connector = dbConnector.getInstance();
        // Matching tbl_bookings_stopovers (bs) with tbl_stopovers (s)
        String query = "SELECT s.location, s.price FROM tbl_stopovers s " +
                       "JOIN tbl_bookings_stopovers bs ON s.s_id = bs.s_id " +
                       "WHERE bs.b_id = " + bid; // bid is an INTEGER, no quotes
        
        ResultSet rs = connector.getData(query);
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("location"), 
                "₱" + String.format("%.2f", rs.getDouble("price"))
            });
        }
    } catch (Exception ex) { 
        System.out.println("Stopover Sync Error: " + ex.getMessage()); 
    }
}

private void addInfoRow(PdfPTable table, String label, String value) {
        table.addCell(new PdfPCell(new Phrase(label, FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
        table.addCell(new PdfPCell(new Phrase(value)));
    }

    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        header = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
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
        paynow = new javax.swing.JPanel();
        pay = new javax.swing.JLabel();
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
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        assignedTable = new javax.swing.JTable();
        download = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
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

        header.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        header.setForeground(new java.awt.Color(255, 255, 255));
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("BOOKING DETAILS");
        jPanel2.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 620, 30));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/back_white.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 60));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minimize_white.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel2.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 30, 60));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close_white.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        jPanel2.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 50, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 60));

        vandisplay.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(vandisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 850, 330));

        stopoverTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        stopoverTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopoverTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(stopoverTable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, 160));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("STOPOVERS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 250, -1));

        pickup.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        pickup.setBorder(null);
        jPanel1.add(pickup, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 510, 160, 20));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setText("BOOKING DETAILS");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 400, 230, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setText("Pick-up Location :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 510, 130, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setText("Final Destination : ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 540, 140, -1));

        finall.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        finall.setBorder(null);
        finall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finallActionPerformed(evt);
            }
        });
        jPanel1.add(finall, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 540, 160, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setText("Start Date :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 570, 100, -1));

        stat_date.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel1.add(stat_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 560, 160, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setText("End Date :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 600, 90, -1));

        end_date.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel1.add(end_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 590, 160, 30));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setText("Total Cost : ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 640, 90, -1));

        total.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        total.setBorder(null);
        jPanel1.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 630, 160, 30));

        paynow.setBackground(new java.awt.Color(12, 33, 74));
        paynow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        paynow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paynowMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                paynowMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                paynowMouseExited(evt);
            }
        });
        paynow.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pay.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        pay.setForeground(new java.awt.Color(255, 255, 255));
        pay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pay.setText("Proceed to payment");
        paynow.add(pay, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 180, 20));

        jPanel1.add(paynow, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 770, 180, 40));

        cancel.setBackground(new java.awt.Color(12, 33, 74));
        cancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
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

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cancel Booking");
        cancel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 180, 20));

        jPanel1.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 770, 180, 40));

        edit.setBackground(new java.awt.Color(12, 33, 74));
        edit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editMouseExited(evt);
            }
        });
        edit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Edit Booking");
        edit.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 160, 20));

        jPanel1.add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 770, 160, 40));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel11.setText("Status :");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 680, 60, -1));

        status.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        status.setBorder(null);
        jPanel1.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 680, 160, 20));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel12.setText("Booking ID :");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 440, -1, -1));

        bookId.setEditable(false);
        bookId.setBackground(new java.awt.Color(255, 255, 255));
        bookId.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        bookId.setBorder(null);
        bookId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookIdActionPerformed(evt);
            }
        });
        jPanel1.add(bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 440, 170, 20));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel13.setText("Customer Name :");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 480, -1, -1));

        customerName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        customerName.setBorder(null);
        jPanel1.add(customerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 480, 160, 20));

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel14.setText("DRIVER");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 100, -1));

        assignedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        assignedTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                assignedTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(assignedTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 440, 100));

        download.setBackground(new java.awt.Color(12, 33, 74));
        download.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                downloadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                downloadMouseExited(evt);
            }
        });
        download.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Download Receipt");
        download.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 150, 20));

        jPanel1.add(download, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 770, 150, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseClicked
 
        if (!edit.isVisible()) return;

    }//GEN-LAST:event_editMouseClicked

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
                                      
        if (status.getText().equalsIgnoreCase("Cancelled")) {
            handleRebooking();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Cancel this booking?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dbConnector connector = dbConnector.getInstance();
            connector.updateData("UPDATE tbl_bookings SET status = 'Cancelled' WHERE b_id = '" + bid + "'");
            releaseVans();
            status.setText("Cancelled");
            refreshUIState("Cancelled");
            JOptionPane.showMessageDialog(this, "Booking Cancelled.");
        }
    
                           
    
       
   
    
    }//GEN-LAST:event_cancelMouseClicked

    private void paynowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paynowMouseClicked
                                       

   
    if (!paynow.isVisible()) return;
    
    // 1. Get the basic info
    String id = bookId.getText();
    String routeStr = pickup.getText() + " to " + finall.getText();
    String amt = total.getText();

    // 2. Get the Name and split it into First and Last
    // This prevents the "5 arguments expected" error
    String fullCName = customerName.getText();
    String fName = "";
    String lName = "";
        
    if (fullCName.contains(" ")) {
        fName = fullCName.substring(0, fullCName.lastIndexOf(" "));
        lName = fullCName.substring(fullCName.lastIndexOf(" ") + 1);
    } else {
        fName = fullCName;
        lName = ""; // Fallback if there's only one name
    }

    int confirm = JOptionPane.showConfirmDialog(this, 
        "Proceed to payment? You cannot edit details after paying.", 
        "Payment Confirmation", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        // FIXED: Now passing 5 arguments to match your payment.java constructor
        payment payFrame = new payment(id, routeStr, amt, fName, lName);
        payFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_paynowMouseClicked
    }
    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
       customerDashboard cd = new customerDashboard();
       cd.setVisible(true);
       dispose();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void finallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finallActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_finallActionPerformed

    private void cancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseEntered
         cancel.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_cancelMouseEntered

    private void cancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseExited
        cancel.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_cancelMouseExited

    private void editMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseEntered
        edit.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_editMouseEntered

    private void editMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseExited
        edit.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_editMouseExited

    private void paynowMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paynowMouseEntered
       paynow.setBackground(java.awt.Color.decode("#256B97"));
    }//GEN-LAST:event_paynowMouseEntered

    private void paynowMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paynowMouseExited
        paynow.setBackground(java.awt.Color.decode("#233E5C"));
    }//GEN-LAST:event_paynowMouseExited

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
       int confirm = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", javax.swing.JOptionPane.YES_NO_OPTION);
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        System.exit(0);
    }
    }//GEN-LAST:event_closeMouseClicked

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
      this.setState(ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        xMouse = evt.getX();
      yMouse = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
      int x = evt.getXOnScreen();
    int y = evt.getYOnScreen();
    this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void downloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadMouseClicked
     if (download.isVisible()) {
           // generateReceiptPDF();
     }
    }//GEN-LAST:event_downloadMouseClicked

    private void downloadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_downloadMouseEntered

    private void downloadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_downloadMouseExited

    private void bookIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookIdActionPerformed

    private void assignedTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_assignedTableMouseClicked
     
    }//GEN-LAST:event_assignedTableMouseClicked

    private void stopoverTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopoverTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_stopoverTableMouseClicked

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            // Updated to 8 arguments: bid, cName, p, d, s, e, t, stat
            // The vans and stopovers are now loaded internally via loadVansFromDB()
            new BookingDetails("0", "Test User", "Pickup", "Destination", 
                               "2026-03-12", "2026-03-13", "0.00", "Pending").setVisible(true);
        }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable assignedTable;
    private javax.swing.JTextField bookId;
    private javax.swing.JPanel cancel;
    private javax.swing.JLabel close;
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
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel minimize;
    private javax.swing.JLabel pay;
    private javax.swing.JPanel paynow;
    private javax.swing.JTextField pickup;
    private com.toedter.calendar.JDateChooser stat_date;
    private javax.swing.JTextField status;
    private javax.swing.JTable stopoverTable;
    private javax.swing.JTextField total;
    private javax.swing.JScrollPane vandisplay;
    // End of variables declaration//GEN-END:variables
}
