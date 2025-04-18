import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Queries {
    static final String DB_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12773093?useSSL=false";
    private Connection conn;
    private Statement stmt;
    private PreparedStatement ptmt;
    
    public Queries(){}

    public Connection getConn(){
        return conn;
    }

    public Statement getStmt(){
        return stmt;
    }   

    public PreparedStatement getPtmt(){
        return ptmt;
    }

    public void login(JTextField username, JPasswordField password, JPanel loginPanel, JFrame mainFrame, JPanel mainPanel){
        String user = username.getText();
        String pass = new String(password.getPassword());
        
        try{
            conn = DriverManager.getConnection(DB_URL, user, pass);
            stmt = conn.createStatement();
            
            JOptionPane.showMessageDialog(mainFrame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

            mainFrame.remove(loginPanel);
            mainFrame.add(mainPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(mainFrame, 
                "Login failed: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
