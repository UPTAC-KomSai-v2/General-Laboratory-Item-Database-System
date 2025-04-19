import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
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

    public void login(JTextField username, JPasswordField password, JPanel loginPanel, JFrame mainFrame, JPanel mainPanel, JLabel statusLabel){
        statusLabel.setText("Logging in...");
        statusLabel.setForeground(Color.BLACK);

        //String user = username.getText();
        //String pass = new String(password.getPassword());

        String user = "sql12773093";
        String pass = "6e6zJ2BwSj";
        
        try{
            conn = DriverManager.getConnection(DB_URL, user, pass);
            stmt = conn.createStatement();
            
            JOptionPane.showMessageDialog(mainFrame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Login Success");
            statusLabel.setForeground(Color.GREEN);
            mainFrame.remove(loginPanel);
            mainFrame.add(mainPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(mainFrame, 
                "Login failed: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("Login Denied.\n Please Try Again.");
                statusLabel.setForeground(Color.RED);
        }
    }

    public void borrowItems(Scanner scn){
        String query = "SELECT * FROM item WHERE status = 'Available' ORDER BY category_id, item_name";
        try{
            ptmt = conn.prepareStatement(query);
            ResultSet rs = ptmt.executeQuery();
            while(rs.next()){
                System.out.println("Item ID: " + rs.getInt("item_id") + ", Item Name: " + rs.getString("item_name"));
            }
        }catch(SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
        }

        selectItems(scn);
    }

    public void showBorrowerList(){
        String query = "SELECT * FROM borrower ORDER BY borrower_id";
        try{
            ptmt = conn.prepareStatement(query);
            ResultSet rs = ptmt.executeQuery();
            while(rs.next()){
                System.out.println("Borrower ID: " + rs.getInt("borrower_id") + ", Borrower Name: " + rs.getString("borrower_name"));
            }
        }catch(SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    public void selectItems(Scanner scn){
        int itemID[] = new int[50];
        int itemCount = 0;
        System.out.println("Enter the item ID you want to borrow (0 to finish): ");
        
         do{
            try{
                System.out.print("Item ID: ");
                itemID[itemCount] = scn.nextInt();
                if(itemID[itemCount] == 0){
                    break;
                }
                itemCount++;
            }catch(InputMismatchException e){
                System.out.println("Error: " + e.getMessage());
                scn.nextLine(); // Clear the invalid input
            }
        }while (itemID[itemCount] != 0);

        System.out.println("You have selected the following items: ");
        String query = "SELECT item_id, item_name FROM item WHERE item_id = ?";
        
        try{
            ptmt = conn.prepareStatement(query);
            for(int i = 0; i < itemCount; i++){
                ptmt.setInt(1, itemID[i]);
                ResultSet rs = ptmt.executeQuery();
                while(rs.next()){
                    System.out.println("Item ID: " + rs.getInt("item_id") + ", Item Name: " + rs.getString("item_name"));
                }
            }
        }catch(SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
        }

    }
    public void updateInventory(){

    }
}
