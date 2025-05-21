/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Startups;

import Employee.EmployeeDashboard;
import admin.AdminDashboard;
import config.dbConnect;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import config.Session;
import config.passwordHasher;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author DANIEL FAILADONA
 */
public class Login extends javax.swing.JFrame {

    private Color H;
    Color h = new Color(51,51,255);
    private Color D;
    Color d = new Color(240,240,240);
    public Login() {
        initComponents();
    }

    static String status;
    static String type;
    

    
    public static boolean logAcc(String username, String password)
    {
        dbConnect connector = new dbConnect();
        try
        {
            String query = "SELECT * FROM tbl_accounts WHERE u_username='"+ username +"'";
            ResultSet resultSet = connector.getData(query);
            if(resultSet.next())
            {
                String hashedPass = resultSet.getString("u_password");
                String rehashedPass = passwordHasher.hashPassword(password);
                
                if (!hashedPass.equals(rehashedPass)) 
                {
                    JOptionPane.showMessageDialog(null, "Password is Incorrect");
                    return false;
                }else if(hashedPass.equals(rehashedPass))
                {
                    status = resultSet.getString("u_status");
                    type = resultSet.getString("u_type");
                    

                    //Shows the logged in user info
                    Session sess = Session.getInstance();
                    sess.setUid(resultSet.getInt("u_id"));
                    sess.setFname(resultSet.getString("u_fname"));
                    sess.setLname(resultSet.getString("u_lname"));
                    sess.setUname(resultSet.getString("u_username"));
                    sess.setType(resultSet.getString("u_type"));
                    sess.setPhone(resultSet.getString("u_phone")); // was not called before (Solved)
                    return true;
                }else
                {
                    return false;
                }
            }else
            {
                return false;
            }
        }catch(SQLException | NoSuchAlgorithmException ex)
        {
            System.out.println(""+ex); // Always put 
            return false;
        }
    }
    
    
    
    public void logEvent(int userId, String username, String action) 
    {
        dbConnect dbc = new dbConnect();
        Connection con = dbc.getConnection();
        PreparedStatement pstmt = null;
        Timestamp time = new Timestamp(new Date().getTime());

        try 
        {    
            String sql = "INSERT INTO tbl_logs (u_id, u_username, action_time, log_action) "
                                     + "VALUES ('" + userId + "', '" + username + "', '" + time + "', '" + action + "')";
            System.out.println("userId: "+userId);
            pstmt = con.prepareStatement(sql);

            /*            pstmt.setInt(1, userId);
            pstmt.setString(2, username);
            pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
            pstmt.setString(4, userType);*/

            pstmt.executeUpdate();
            System.out.println("Login log recorded successfully.");
        } catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "Error recording log: " + e.getMessage());
        } finally 
        {
            try 
            {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) 
            {
                JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
            }
        }
    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Manager_Login = new javax.swing.JPanel();
        Manager_Login1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        passwordML = new javax.swing.JPasswordField();
        usernameML = new javax.swing.JTextField();
        confirm1 = new javax.swing.JPanel();
        MR_clickhere = new javax.swing.JLabel();
        confirm = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        MR_clickhere1 = new javax.swing.JLabel();
        check = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Manager_Login.setBackground(new java.awt.Color(51, 51, 51));
        Manager_Login.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Manager_Login1.setBackground(new java.awt.Color(153, 255, 204));
        Manager_Login1.setForeground(new java.awt.Color(0, 153, 153));
        Manager_Login1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Username:");
        Manager_Login1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 80, 30));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Password:");
        Manager_Login1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 80, 30));

        passwordML.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordMLActionPerformed(evt);
            }
        });
        Manager_Login1.add(passwordML, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 330, 30));

        usernameML.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameMLActionPerformed(evt);
            }
        });
        Manager_Login1.add(usernameML, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 330, 30));

        confirm1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirm1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                confirm1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                confirm1MouseExited(evt);
            }
        });
        confirm1.setLayout(null);

        MR_clickhere.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        MR_clickhere.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MR_clickhere.setText("Register");
        MR_clickhere.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MR_clickhereMouseClicked(evt);
            }
        });
        confirm1.add(MR_clickhere);
        MR_clickhere.setBounds(0, 10, 90, 10);

        Manager_Login1.add(confirm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 90, 30));

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
        confirm.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Login");
        confirm.add(jLabel5);
        jLabel5.setBounds(0, 5, 90, 20);

        Manager_Login1.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 90, 30));
        Manager_Login1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 570, 240, 80));

        MR_clickhere1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        MR_clickhere1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MR_clickhere1.setText("Forgot Password? click here");
        MR_clickhere1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MR_clickhere1MouseClicked(evt);
            }
        });
        Manager_Login1.add(MR_clickhere1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 330, 20));

        check.setBackground(new java.awt.Color(102, 102, 102));
        check.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        check.setForeground(new java.awt.Color(255, 255, 255));
        check.setText("Show");
        check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActionPerformed(evt);
            }
        });
        Manager_Login1.add(check, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\Gwapo ko\\Downloads\\pngtree-airline-ticket-online-booking-design-concept-png-image_528742.jpg")); // NOI18N
        Manager_Login1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 360, -1));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LOG IN FORM APP");
        Manager_Login1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 540, -1));

        Manager_Login.add(Manager_Login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, 0, 900, 360));

        getContentPane().add(Manager_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActionPerformed
        boolean isSelected = check.isSelected();

        if (isSelected) {
            passwordML.setEchoChar((char)0);
        } else {
            passwordML.setEchoChar('*');
        }
    }//GEN-LAST:event_checkActionPerformed

    private void MR_clickhere1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MR_clickhere1MouseClicked
        ForgetPass efp = new ForgetPass();
        efp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MR_clickhere1MouseClicked

    private void confirmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseExited
        confirm.setBackground(d);
    }//GEN-LAST:event_confirmMouseExited

    private void confirmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseEntered
        confirm.setBackground(h);
    }//GEN-LAST:event_confirmMouseEntered

    private void confirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmMouseClicked

        Session sess = Session.getInstance();
        dbConnect connector = new dbConnect();
        String uname = usernameML.getText().trim();
        String pass = new String(passwordML.getPassword()).trim();
        int userId = sess.getUid();  // Initialize userId from the session
        String ac = null;

        if (pass.isEmpty() || uname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all boxes");
        } else if (logAcc(uname, pass)) {

            if (!status.equals("Active")) {
                JOptionPane.showMessageDialog(null, "Inactive account. Contact the Admin");

                logEvent(userId, uname, "Failed - Inactive Account");

            } else {

                // Retrieve the user ID properly
                try
                {
                    String query = "SELECT u_id FROM tbl_accounts WHERE u_username = '"+uname+"'";
                    PreparedStatement pstmt = connector.getConnection().prepareStatement(query);

                    ResultSet resultSet = pstmt.executeQuery();

                    if (resultSet.next())
                    {
                        userId = resultSet.getInt("u_id");   // Update the outer `userId` correctly
                    }
                } catch (SQLException ex) {
                    System.out.println("SQL Exception: " + ex);
                }

                // Handle different user types
                if (type.equals("Admin")) {

                    logEvent(userId, uname, "Logged as Admin");
                    JOptionPane.showMessageDialog(null, "Login Successfully");

                    AdminDashboard ad = new AdminDashboard();
                    ad.setVisible(true);
                    this.dispose();
                } else if (type.equals("Employee"))
                {

                    logEvent(userId, uname, "Logged as Employee");
                    JOptionPane.showMessageDialog(null, "Login Successfully");

                    EmployeeDashboard ed = new EmployeeDashboard();
                    ed.setVisible(true);
                    this.dispose();
                } else if(type.equals("Deleted"))
                {
                    JOptionPane.showMessageDialog(null, "Invalid account");
                }else {
                    JOptionPane.showMessageDialog(null, "Unknown account type. Contact the Admin");
                }
            }

        } else
        {
            /*            JOptionPane.showMessageDialog(null, "Invalid account");*/
            System.out.println("Unknown Error Occured");
        }

    }//GEN-LAST:event_confirmMouseClicked

    private void confirm1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirm1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_confirm1MouseExited

    private void confirm1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirm1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_confirm1MouseEntered

    private void confirm1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirm1MouseClicked
        Registration r = new Registration();
        r.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_confirm1MouseClicked

    private void MR_clickhereMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MR_clickhereMouseClicked
        Registration r = new Registration();
        r.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MR_clickhereMouseClicked

    private void usernameMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameMLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameMLActionPerformed

    private void passwordMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordMLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordMLActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MR_clickhere;
    private javax.swing.JLabel MR_clickhere1;
    private javax.swing.JPanel Manager_Login;
    private javax.swing.JPanel Manager_Login1;
    private javax.swing.JCheckBox check;
    private javax.swing.JPanel confirm;
    private javax.swing.JPanel confirm1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField passwordML;
    private javax.swing.JTextField usernameML;
    // End of variables declaration//GEN-END:variables
}
