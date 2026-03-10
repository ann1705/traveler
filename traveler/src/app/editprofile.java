
package app;


import dashboard.adminDashboard;
import dashboard.customerDashboard;
import dashboard.driverDashboard;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class editprofile extends javax.swing.JFrame {
    
    
    
    
    public File selectedFile;
    public String destinationPath;
    public String oldPath;
    private boolean isViewed1 = false;
    private boolean isViewed2 = false;
    
    public boolean isEditingOther = false; 
    public String targetID;                
    
    
    public editprofile() {
         this.setUndecorated(true);
        
        initComponents();
        
        this.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        
    }
public void populateForAdmin(String id, String fn, String ln, String un, String em, String ad, String gen, String con, String cvl, String st) {
    this.isEditingOther = true; 
    this.targetID = id;

    // Fix: Make ID visible but not editable
    aid.setText(id);
    aid.setEditable(false);
    aid.setFocusable(false);
    aid.setDisabledTextColor(java.awt.Color.BLACK); 

    fname.setText(fn);
    lname.setText(ln);
    
    // Fix: Make Username visible but not editable (usually unique in DB)
    uname.setText(un);
    uname.setEditable(false);
    uname.setDisabledTextColor(java.awt.Color.BLACK);

    email.setText(em);
    add.setText(ad);             
    gender.setSelectedItem(gen); 
    contact.setText(con);        
    cstatus.setSelectedItem(cvl);
    acc_status.setSelectedItem(st); 
    
    acc_status.setEnabled(true); // Admin can change others' status
}


public void displayUserDetails() {
    config.Session sess = config.Session.getInstance();
    this.isEditingOther = false;
    this.targetID = String.valueOf(sess.getId());
    
    // Set the Text Fields
    aid.setText(targetID);
    fname.setText(sess.getFirstname());
    lname.setText(sess.getLastname());
    uname.setText(sess.getUsername());
    email.setText(sess.getEmail());
    contact.setText(sess.getContact());
    add.setText(sess.getAddress()); // JTextArea

    // Set the Dropdowns (JComboBox)
    // We use setSelectedItem to match the string in the session
    cstatus.setSelectedItem(sess.getC_status());
    gender.setSelectedItem(sess.getGender());
    acc_status.setSelectedItem(sess.getStatus()); 
    
    // Formatting: Make ID and Username read-only
    aid.setEditable(false);
    aid.setDisabledTextColor(java.awt.Color.BLACK);
    uname.setEditable(false);
    uname.setDisabledTextColor(java.awt.Color.BLACK);
    
    // Strictly restrict Account Status editing for self
    acc_status.setEnabled(false); 
    
    if (sess.getImage() != null && !sess.getImage().isEmpty()) {
        destinationPath = sess.getImage();
        updateImagePreview(destinationPath);
    }
}



public boolean duplicateCheck() {
    config.dbConnector connector = config.dbConnector.getInstance();
    config.Session sess = config.Session.getInstance();
    String currentID = isEditingOther ? targetID : String.valueOf(sess.getId());
    
    try {
        // Exclude current user ID from the search using AND a_id != ...
        String query = "SELECT username, email FROM tbl_account WHERE (username = '" 
                     + uname.getText() + "' OR email = '" + email.getText() + "') "
                     + "AND a_id != '" + currentID + "'";
        ResultSet rs = connector.getData(query);

        if (rs.next()) {
            String existingEmail = rs.getString("email");
            if (existingEmail.equalsIgnoreCase(email.getText())) {
                JOptionPane.showMessageDialog(null, "Email is already used by another account!");
                return true;
            }
            
            String existingUser = rs.getString("username");
            if (existingUser.equalsIgnoreCase(uname.getText())) {
                JOptionPane.showMessageDialog(null, "Username is already taken!");
                return true;
            }
        }
        return false;
    } catch (SQLException ex) {
        System.out.println("Validation Error: " + ex.getMessage());
        return false;
    }
}

private void handleNavigation(String userRole) {
        if (isEditingOther || userRole.equalsIgnoreCase("Admin")) {
            new adminDashboard().setVisible(true);
        } else if (userRole.equalsIgnoreCase("User") || userRole.equalsIgnoreCase("Customer")) {
            new customerDashboard().setVisible(true);
        } else if (userRole.equalsIgnoreCase("Driver")) {
            new driverDashboard().setVisible(true);
        }
        this.dispose();
    }

// Logic for Password Strength (updates the progress bar)
private void checkStrength(String password) {
    int score = 0;
    if (password.length() >= 6) score += 40;
    if (password.length() >= 10) score += 20;
    if (password.matches(".*[A-Z].*")) score += 20; 
    if (password.matches(".*[0-9].*")) score += 20; 

    strengthBar.setValue(score); // Ensure your JProgressBar is named strengthBar

    if (score <= 40) {
        strengthBar.setForeground(java.awt.Color.RED);
        strengthBar.setString("Weak");
    } else if (score <= 70) {
        strengthBar.setForeground(java.awt.Color.ORANGE);
        strengthBar.setString("Medium");
    } else {
        strengthBar.setForeground(new java.awt.Color(0, 153, 0));
        strengthBar.setString("Strong");
    }
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
    
     private int xMouse, yMouse;

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
        cstatus = new javax.swing.JComboBox<>();
        change = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        save = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        minimize = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
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
        strengthBar = new javax.swing.JProgressBar();
        jLabel13 = new javax.swing.JLabel();
        eye2 = new javax.swing.JLabel();
        confirmpass = new javax.swing.JPasswordField();
        ps_error = new javax.swing.JLabel();
        cancel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        acc_status = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(12, 33, 74));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
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

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 240, 210));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Account ID :");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 140, 30));

        aid.setEditable(false);
        aid.setBackground(new java.awt.Color(2, 54, 87));
        aid.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        aid.setForeground(new java.awt.Color(255, 255, 255));
        aid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel3.add(aid, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 200, 30));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("First Name :");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 150, 30));

        fname.setBackground(new java.awt.Color(2, 54, 87));
        fname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        fname.setForeground(new java.awt.Color(255, 255, 255));
        fname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel3.add(fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, 200, 30));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Last Name :");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 150, 30));

        lname.setEditable(false);
        lname.setBackground(new java.awt.Color(2, 54, 87));
        lname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lname.setForeground(new java.awt.Color(255, 255, 255));
        lname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel3.add(lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 530, 200, 30));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Username :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 150, 30));

        uname.setEditable(false);
        uname.setBackground(new java.awt.Color(2, 54, 87));
        uname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        uname.setForeground(new java.awt.Color(255, 255, 255));
        uname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        uname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unameActionPerformed(evt);
            }
        });
        jPanel3.add(uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 580, 200, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Email :");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 150, 30));

        email.setBackground(new java.awt.Color(2, 54, 87));
        email.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel3.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 630, 200, 30));

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Civil Status :");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, 150, 30));

        cstatus.setEditable(true);
        cstatus.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        cstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Single", "Married", "Widowed", "Annuled" }));
        cstatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(cstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 680, 200, 30));

        change.setBackground(new java.awt.Color(20, 20, 130));
        change.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        change.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeMouseClicked(evt);
            }
        });
        change.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CHANGE");
        change.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 150, 20));

        jPanel3.add(change, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, 150, 40));

        save.setBackground(new java.awt.Color(20, 20, 130));
        save.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveMouseClicked(evt);
            }
        });
        save.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("SAVE");
        save.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 150, 20));

        jPanel3.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 820, 150, 40));

        jPanel5.setBackground(new java.awt.Color(76, 143, 209));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel5.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 30, 60));

        close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        jPanel5.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 50, 70));

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/goback.png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        jPanel5.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 70, 50));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 70));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Gender :");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 720, 120, 30));

        gender.setEditable(true);
        gender.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        gender.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 720, 200, 30));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Address :");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 400, 110, 30));

        add.setColumns(20);
        add.setRows(5);
        address.setViewportView(add);

        jPanel3.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 400, 280, -1));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Contact :");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 760, 120, 30));

        contact.setBackground(new java.awt.Color(2, 54, 87));
        contact.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        contact.setForeground(new java.awt.Color(255, 255, 255));
        contact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel3.add(contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 760, 200, 30));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("PROFILE PHOTO");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, 30));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Change Password : ");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 540, 190, -1));

        eye1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eye1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eyeee.png"))); // NOI18N
        eye1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eye1MouseClicked(evt);
            }
        });
        jPanel3.add(eye1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 540, 30, 30));

        newpass.setBackground(new java.awt.Color(2, 54, 87));
        newpass.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        newpass.setForeground(new java.awt.Color(255, 255, 255));
        newpass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        newpass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newpassKeyReleased(evt);
            }
        });
        jPanel3.add(newpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 540, 220, 30));

        strengthBar.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        strengthBar.setForeground(new java.awt.Color(255, 255, 255));
        strengthBar.setStringPainted(true);
        jPanel3.add(strengthBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 574, 80, 30));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Confrim Password :");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 630, 180, -1));

        eye2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eye2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eyeee.png"))); // NOI18N
        eye2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eye2MouseClicked(evt);
            }
        });
        jPanel3.add(eye2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 630, -1, 30));

        confirmpass.setBackground(new java.awt.Color(2, 54, 87));
        confirmpass.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        confirmpass.setForeground(new java.awt.Color(255, 255, 255));
        confirmpass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        confirmpass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                confirmpassKeyReleased(evt);
            }
        });
        jPanel3.add(confirmpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 630, 220, 30));

        ps_error.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        ps_error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(ps_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 670, 220, 30));

        cancel.setBackground(new java.awt.Color(20, 20, 130));
        cancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });
        cancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("CANCEL");
        cancel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, 20));

        jPanel3.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 820, 130, 40));

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Account Status :");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 150, 30));

        acc_status.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        acc_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));
        acc_status.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.add(acc_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 200, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
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
    System.out.println("DEBUG: Save button was clicked!");    
                                       
                                  
    config.Session sess = config.Session.getInstance();
    config.dbConnector connector = config.dbConnector.getInstance();
    String currentTargetID = isEditingOther ? targetID : String.valueOf(sess.getId());

    // --- IMAGE HANDLING LOGIC ---
    String imagePath = (destinationPath != null) ? destinationPath.replace("\\", "/") : "";
    
    if (selectedFile != null) {
        try {
            // Create a folder to store images if it doesn't exist
            File folder = new File("src/userimages");
            if (!folder.exists()) folder.mkdirs();

            // Create a unique name for the image to avoid overwriting
            String newImageName = "user_" + currentTargetID + "_" + System.currentTimeMillis() + ".jpg";
            File destinationFile = new File(folder, newImageName);
            
            // Copy the file
            java.nio.file.Files.copy(selectedFile.toPath(), destinationFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            imagePath = "src/userimages/" + newImageName;
        } catch (Exception e) {
            System.out.println("File Copy Error: " + e.getMessage());
        }
    }

    // 1. GATHER DATA
    String fn = fname.getText().trim();
    String ln = lname.getText().trim();
    String un = uname.getText().trim();
    String em = email.getText().trim();
    String ad = add.getText().trim(); 
    String cn = contact.getText().trim();
    String gn = (gender.getSelectedItem() != null) ? gender.getSelectedItem().toString() : "";
    String cv = (cstatus.getSelectedItem() != null) ? cstatus.getSelectedItem().toString() : "";
    String st = (acc_status.getSelectedItem() != null) ? acc_status.getSelectedItem().toString() : "Active";
    
    // 2. THE UPDATE QUERY (Added a_image)
    try {
        String query = "UPDATE tbl_account SET "
                + "firstname = '" + fn + "', lastname = '" + ln + "', "
                + "username = '" + un + "', email = '" + em + "', "
                + "contact = '" + cn + "', address = '" + ad + "', "
                + "gender = '" + gn + "', civil_status = '" + cv + "', "
                + "status = '" + st + "', "
                + "image = '" + imagePath + "' " // UPDATE THE IMAGE PATH
                + " WHERE a_id = '" + currentTargetID + "'";

        if (connector.updateData(query)) {
            JOptionPane.showMessageDialog(null, "Account successfully updated!");

            // Update Session so the new image shows up immediately
            if (!isEditingOther) {
                sess.setFirstname(fn); sess.setLastname(ln);
                sess.setImage(imagePath); // Save to session
                // ... (set other session variables)
            }
            handleNavigation(sess.getType());
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "System Error: " + e.getMessage());
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

    private void newpassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newpassKeyReleased
       checkStrength(new String(newpass.getPassword()));
    }//GEN-LAST:event_newpassKeyReleased

    private void confirmpassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmpassKeyReleased
       // You can use your checkPasswordMatch() logic here to show the green/red label
        String pass = new String(newpass.getPassword());
        String conf = new String(confirmpass.getPassword());
        if(pass.equals(conf)){
            ps_error.setText("Match");
            ps_error.setForeground(Color.GREEN);
        } else {
            ps_error.setText("No Match");
            ps_error.setForeground(Color.RED);
        }
    }//GEN-LAST:event_confirmpassKeyReleased

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
  
    int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to go back? Any unsaved changes will be lost.", 
            "Confirm Exit", JOptionPane.YES_NO_OPTION);
            
    if (confirm == JOptionPane.YES_OPTION) {
        config.Session sess = config.Session.getInstance();
        String role = sess.getType();

        // Redirect based on role or editing status
        if (isEditingOther || role.equalsIgnoreCase("Admin")) {
            new dashboard.adminDashboard().setVisible(true);
        } else if (role.equalsIgnoreCase("Driver")) {
            new dashboard.driverDashboard().setVisible(true);
        } else {
            new dashboard.customerDashboard().setVisible(true);
        }
        this.dispose();
    }

    }//GEN-LAST:event_backMouseClicked

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
       xMouse = evt.getX();
       yMouse = evt.getY();
    }//GEN-LAST:event_jPanel3MousePressed

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
    int x = evt.getXOnScreen();
    int y = evt.getYOnScreen();
    this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel3MouseDragged

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
    private javax.swing.JComboBox<String> acc_status;
    private javax.swing.JTextArea add;
    private javax.swing.JScrollPane address;
    private javax.swing.JTextField aid;
    private javax.swing.JLabel back;
    private javax.swing.JPanel cancel;
    private javax.swing.JPanel change;
    private javax.swing.JLabel close;
    private javax.swing.JPasswordField confirmpass;
    private javax.swing.JTextField contact;
    private javax.swing.JComboBox<String> cstatus;
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
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JLabel minimize;
    private javax.swing.JPasswordField newpass;
    private javax.swing.JLabel ps_error;
    private javax.swing.JPanel save;
    private javax.swing.JProgressBar strengthBar;
    private javax.swing.JTextField uname;
    // End of variables declaration//GEN-END:variables

   
}
