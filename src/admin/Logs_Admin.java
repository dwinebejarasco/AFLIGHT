/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.sql.ResultSet;           // For ResultSet
import java.sql.SQLException;         // For SQLException
import java.util.ArrayList;          // For ArrayList
import java.util.List;               // For List
import javax.swing.table.DefaultTableModel;  // For DefaultTableModel
import config.dbConnect;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;
import Startups.Login;
import config.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
public class Logs_Admin extends javax.swing.JFrame {

     private Color H;
    Color h = new Color(51,51,255);
    private Color D;
    Color d = new Color(240,240,240);
    
    public Logs_Admin() {
        initComponents();
//        NotShowDeletedUsers();
    displayData();
    }
    
    public void NotShowDeletedUsers() {
        // Create a list to store filtered row data
        List<Object[]> rowData = new ArrayList<>();

        try {
            dbConnect dbc = new dbConnect();
            ResultSet rs = dbc.getData("SELECT u_id, u_lname, u_fname, u_username, u_type, u_phone, u_status FROM tbl_accounts");

            while (rs.next()) {
                // Store each column value in a separate variable
                String u = rs.getString("u_id");
                String fn = rs.getString("u_fname");
                String ln = rs.getString("u_lname");
                String uname = rs.getString("u_username");
                //String npass = rs.getString("u_password");  // Note: This might not be used, but it can be checked if necessary
                String p = rs.getString("u_phone");
                String at = rs.getString("u_type");
                String status = rs.getString("u_status");

                // Check if the user status is not "Deleted"
                if (!status.equals("Deleted")) {
                    
                    // Add the row to the list
                    rowData.add(new Object[]{
                        u,
                        fn,
                        ln, 
                        uname, 
                        at, 
                        p,
                        status 
                    });
                    /*System.out.println("\n==========");
                    System.out.println(""+u);
                    System.out.println(""+fn);
                    System.out.println(""+ln);
                    System.out.println(""+uname);
                    System.out.println(""+at);
                    System.out.println(""+p);
                    System.out.println(""+status);*/
                }
            }

            // After processing all rows, update the table on the Swing event dispatch thread
            SwingUtilities.invokeLater(() -> {
                DefaultTableModel model = new DefaultTableModel(
                        new String[]{"ID", "First Name", "Last Name", "Username", "Account Type", "Phone", "Status"}, 0
                );
                for (Object[] row : rowData) {
                    model.addRow(row);
                }
                account_table.setModel(model);
                account_table.repaint(); // Force visual refresh
            });


            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    

    public void displayData()
    {
        try
        {
            dbConnect dbc = new dbConnect();
            ResultSet rs = dbc.getData("SELECT * FROM tbl_logs");
            account_table.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
        }catch(SQLException ex)
        {
            System.out.println("Errors: "+ex.getMessage());
        }
    }
    
    
    private void loadUsersData() 
{
    DefaultTableModel model = (DefaultTableModel) account_table.getModel();
    model.setRowCount(0); // Clear the table before reloading

    String sql = "SELECT * FROM tbl_accounts";

    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flights", "root", "");
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) 
    {

        while (rs.next()) 
        {
            // Check if the user's status is not "Deleted"
            String userStatus = rs.getString("u_status");
            if (!"Deleted".equals(userStatus)) 
            {
                model.addRow(new Object[]
                {
                    rs.getInt("u_id"),
                    rs.getString("u_fname"),
                    rs.getString("u_lname"),
                    rs.getString("u_username"),
                    rs.getString("u_type"),
                    rs.getString("u_phone"),
                    rs.getString("u_status")
                });
            }
        }
    } catch (SQLException ex) 
    {
        JOptionPane.showMessageDialog(this, "Error loading user data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    private void deleteUser()         
    {
        dbConnect dbc = new dbConnect();
        Session sess = Session.getInstance();
        dbConnect connector = new dbConnect();
//        int userId = 0;
        String uname3 = null;
        String uname2 = null;
        String uname = null;
        
        
        int selectedRow = account_table.getSelectedRow();
        if (selectedRow == -1) 
        {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        int userId = Integer.parseInt(account_table.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) 
        {

            try 
            {
                String query2 = "SELECT * FROM tbl_accounts WHERE u_id = '" + userId + "'";
                PreparedStatement pstmt = connector.getConnection().prepareStatement(query2);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) 
                {
                    String fn = rs.getString("u_fname");
                    String ln = rs.getString("u_lname");
                    uname = rs.getString("u_username");
                    String npass = rs.getString("u_password");
                    String p = rs.getString("u_phone");
                    String at = rs.getString("u_type");
                    String s = "Deleted";
                    String u = rs.getString("u_id");

                    dbc.updateData("UPDATE tbl_accounts SET u_fname = '" + fn + "', u_lname = '" + ln + "', u_username = '" + uname + "',"
                            + " u_password = '" + npass + "', u_phone = '" + p + "', u_type = '" + at + "', u_status = '" + s + "' WHERE u_id = '" + userId + "'");
                    
                    try 
                    {
                        String query = "SELECT * FROM tbl_accounts WHERE u_id = '" + sess.getUid() + "'";
                        PreparedStatement pstmt2 = connector.getConnection().prepareStatement(query);

                        ResultSet rs2 = pstmt2.executeQuery();

                        if (rs2.next()) 
                        {
                            userId = rs2.getInt("u_id");   
                            uname2 = rs2.getString("u_username");
                            logEvent(userId, uname2, "Admin Deleted Account: " + uname);
                            loadUsersData();
                        }
                    }catch (SQLException ex) 
                    {
                        System.out.println("" + ex);
                    }
                }
            } catch (SQLException ex) 
            {
                System.out.println("SQL Exception: " + ex);
            }
        }
    }
    
    
    public void logEvent(int userId, String username, String action) 
    {
        dbConnect dbc = new dbConnect();
        Connection con = dbc.getConnection();
        PreparedStatement pstmt = null;
        Timestamp time = new Timestamp(new Date().getTime());

        try {
            String sql = "INSERT INTO tbl_logs (u_id, u_username, action_time, log_action) "
                    + "VALUES ('" + userId + "', '" + username + "', '" + time + "', '" + action + "')";
            pstmt = con.prepareStatement(sql);

            /*            pstmt.setInt(1, userId);
            pstmt.setString(2, username);
            pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
            pstmt.setString(4, userType);*/
            pstmt.executeUpdate();
            System.out.println("Login log recorded successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error recording log: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
            }
        }
    }
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        account_table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Main.setBackground(new java.awt.Color(39, 39, 39));
        Main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setBackground(new java.awt.Color(102, 102, 102));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(0, 255, 0));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Logs Dashboard");
        Header.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 360, 40));

        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutMouseExited(evt);
            }
        });
        logout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Back");
        logout.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 130, -1));

        Header.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        Main.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 100));

        account_table.setBackground(new java.awt.Color(204, 204, 255));
        account_table.setBorder(new javax.swing.border.MatteBorder(null));
        account_table.setForeground(new java.awt.Color(204, 204, 204));
        account_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(account_table);

        Main.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 600, 540));

        getContentPane().add(Main, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        AdminDashboard as = new AdminDashboard();
        as.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void logoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseEntered
        logout.setBackground(h);
    }//GEN-LAST:event_logoutMouseEntered

    private void logoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseExited
        logout.setBackground(d);
    }//GEN-LAST:event_logoutMouseExited

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
            java.util.logging.Logger.getLogger(Logs_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Logs_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Logs_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Logs_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Logs_Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Main;
    private javax.swing.JTable account_table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel logout;
    // End of variables declaration//GEN-END:variables
}
