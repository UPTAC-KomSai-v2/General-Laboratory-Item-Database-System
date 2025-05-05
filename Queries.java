import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Queries {
    static final String DB_URL = "jdbc:mysql://caboose.proxy.rlwy.net:51384/genlab_db";
    private Connection conn;
    private Statement stmt;
    private PreparedStatement ptmt;
    private boolean bypassDB = false;
    
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

        if (bypassDB) {
            // Skip DB connection and go straight to mainPanel
            JOptionPane.showMessageDialog(mainFrame, "Login bypassed for testing.", "Bypass Mode", JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Login Success (bypassed)");
            statusLabel.setForeground(Color.GREEN);
            mainFrame.remove(loginPanel);
            mainFrame.add(mainPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
            return;
        }

        String user = "root";
        String pass = "weoZeizOaesHkpjieIetoaQTyKfFwjKm";
        // String user = "sql12773093";
        // String pass = "6e6zJ2BwSj";
        
        try{
            conn = DriverManager.getConnection(DB_URL, user, pass);
            stmt = conn.createStatement();
            
            statusLabel.setText("Login Success");
            statusLabel.setForeground(Color.GREEN);
            JOptionPane.showMessageDialog(mainFrame, new JLabel("Login successful!", SwingConstants.CENTER), "Success", JOptionPane.PLAIN_MESSAGE );
            username.setText("");
            password.setText("");
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
        
        //selectItems(scn);   
        
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

    // ----------------------------------------------------------
    //                 JHUN KENNETH INIEGO QUERIES
    // ----------------------------------------------------------
    public int getNoOfItems() {
        int n = 0;
        String query = "SELECT COUNT(item_id) AS no_of_items FROM item";

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(query);
            ResultSet rs = ptmt.executeQuery();

            while(rs.next()) n = rs.getInt("no_of_items");

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }

        return n;
    }

    public void importToItems(String filePath, String[][] items) {
        try {
            for(String[] tuple : items) {
                String query = "INSERT INTO item(item_name, unit, qty, category_id, status) VALUES(?, ?, ?, ?, ?)";

                conn.setAutoCommit(false);
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                ptmt = conn.prepareStatement(query);
                ptmt.setString(1, tuple[0]);
                if(!tuple[1].equals("null")) ptmt.setString(2, tuple[1]);
                else ptmt.setNull(2, java.sql.Types.VARCHAR);
                ptmt.setInt(3, Integer.parseInt(tuple[2]));
                ptmt.setInt(4, Integer.parseInt(tuple[3]));
                ptmt.setString(5, tuple[4]);
                int rowsInserted = ptmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("A new item was inserted successfully! ==> " + tuple[0]);
                }

                conn.commit();
                conn.setAutoCommit(true);
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    public int getCategoryID(String categoryName) {
        String query = "SELECT category_id FROM category WHERE category_name = ?";
        int categoryID = 0;

        try{
            ptmt = conn.prepareStatement(query);
            ptmt.setString(1, categoryName);
            ResultSet rs = ptmt.executeQuery();

            while(rs.next()) {
                categoryID = rs.getInt("category_id");
            }
        }catch(SQLException e){
            System.err.println("SQL Error: " + e.getMessage());
        }

        return categoryID;
    }

    public String[][] getItemsPerCategory(int categoryID) {
        String query = "SELECT item_id, item_name, unit, qty FROM item WHERE category_id = ?";
        String[][] data = null;

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(query);
            ptmt.setInt(1, categoryID);
            ResultSet rs1 = ptmt.executeQuery();

            int rows = 0;
            while (rs1.next()) {
                rows++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement ptmt1 = conn.prepareStatement(query);
            ptmt1.setInt(1, categoryID);
            ResultSet rs = ptmt1.executeQuery();

            data = new String[rows][4];
            int rowIndex = 0;
            while (rs.next()) {
                data[rowIndex][0] = rs.getString("item_id");
                data[rowIndex][1] = rs.getString("item_name");
                data[rowIndex][2] = rs.getString("unit");
                data[rowIndex][3] = rs.getString("qty");
                rowIndex++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }

        return data;
    }

    public void addItemToDatabase(String itemName, String unit, int qty, int categoryID) {
        String insertQuery = "INSERT INTO item(item_name, unit, qty, category_id, status) VALUES(?, ?, ?, ?, ?)";

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(insertQuery);
            ptmt.setString(1, itemName);
            if(unit.equals("null")) ptmt.setNull(2, java.sql.Types.VARCHAR);
            else ptmt.setString(2, unit);
            ptmt.setInt(3, qty);
            ptmt.setInt(4, categoryID);
            ptmt.setString(5, "Available");

            ptmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    public void removeItemFromDatabase(int itemID) {
        String deleteQuery = "DELETE FROM item WHERE item_id = ?";

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(deleteQuery);
            ptmt.setInt(1, itemID);
            ptmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    public void updateActualReturnDate(int borrowID) {
        String updateQuery = "UPDATE borrow SET actual_return_date = CURRENT_DATE WHERE borrow_id = ?";

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(updateQuery);
            ptmt.setInt(1, borrowID);
            ptmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error1A: " + e1.getMessage());
            }
            System.err.println("SQL Error2A: " + e.getMessage());
        }

        System.out.println("Updated actual return date");
        String addQuery = "INSERT INTO return_log(borrow_id, return_date, item_condition) VALUES(?, CURRENT_DATE, ?)";

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement ptmt1 = conn.prepareStatement(addQuery);
            ptmt1.setInt(1, borrowID);
            ptmt1.setString(2, "Good Condition");
            ptmt1.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error1B: " + e1.getMessage());
            }
            System.err.println("SQL Error2B: " + e.getMessage());
        }
    }
    public String[][] getBorrowList() {
        String query = "SELECT DISTINCT full_name, borrower_id, date_borrowed, expected_return_date, degree_prog, course_id, section_name FROM borrow JOIN borrower USING(borrower_id) JOIN course USING(course_id) JOIN section USING(section_id) WHERE actual_return_date IS NULL ORDER BY date_borrowed DESC, expected_return_date DESC";
        String[][] data = null;

        try{
            ptmt = conn.prepareStatement(query);
            ResultSet rs = ptmt.executeQuery();

            data = new String[getRows(query)][7];
            int rowIndex = 0;
            while (rs.next()) {
                data[rowIndex][0] = rs.getString("full_name");
                data[rowIndex][1] = rs.getString("borrower_id");
                data[rowIndex][2] = rs.getString("date_borrowed");
                data[rowIndex][3] = rs.getString("expected_return_date");
                data[rowIndex][4] = rs.getString("degree_prog");
                data[rowIndex][5] = rs.getString("course_id");
                data[rowIndex][6] = rs.getString("section_name");
                rowIndex++;
            }
        }catch(SQLException e){
            System.err.println("SQL Error 2C: " + e.getMessage());
        }

        return data;
    }

    public String[][] getItemsBorrowed(String borrowerID, String dateBorrowed, String expectedReturnDate) {
        String query = "SELECT qty_borrowed, item_name, unit, item_id, borrow_id FROM borrow JOIN borrower USING(borrower_id) JOIN item USING(item_id) WHERE borrower_id = ? AND date_borrowed = ? and expected_return_date = ? AND actual_return_date IS NULL ORDER BY date_borrowed DESC";
        String[][] data = null;

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(query);
            ptmt.setString(1, borrowerID);
            ptmt.setString(2, dateBorrowed);
            ptmt.setString(3, expectedReturnDate);
            ResultSet rs1 = ptmt.executeQuery();

            int rows = 0;
            while (rs1.next()) {
                rows++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement ptmt1 = conn.prepareStatement(query);
            ptmt1.setString(1, borrowerID);
            ptmt1.setString(2, dateBorrowed);
            ptmt1.setString(3, expectedReturnDate);
            ResultSet rs = ptmt1.executeQuery();

            data = new String[rows][5];
            int rowIndex = 0;
            while (rs.next()) {
                data[rowIndex][0] = rs.getString("qty_borrowed");
                data[rowIndex][1] = rs.getString("item_name");
                data[rowIndex][2] = rs.getString("unit");
                data[rowIndex][3] = rs.getString("item_id");
                data[rowIndex][4] = rs.getString("borrow_id");
                rowIndex++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }

        return data;
    }

    public int getRows(String query) {
        int rows = 0;

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(query);
            ResultSet rs1 = ptmt.executeQuery();

            while (rs1.next()) {
                rows++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }

        return rows;
    }

    public void getReturnLog() {
        String query = "SELECT qty_borrowed, item_name, unit, item_id, borrow_id FROM borrow JOIN borrower USING(borrower_id) JOIN item USING(item_id) WHERE borrower_id = ? AND date_borrowed = ? and expected_return_date = ? AND actual_return_date IS NULL ORDER BY date_borrowed DESC";
        String[][] data = null;

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(query);
            ResultSet rs1 = ptmt.executeQuery();

            int rows = 0;
            while (rs1.next()) {
                rows++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement ptmt1 = conn.prepareStatement(query);
            ResultSet rs = ptmt1.executeQuery();

            data = new String[rows][5];
            int rowIndex = 0;
            while (rs.next()) {
                data[rowIndex][0] = rs.getString("qty_borrowed");
                data[rowIndex][1] = rs.getString("item_name");
                data[rowIndex][2] = rs.getString("unit");
                data[rowIndex][3] = rs.getString("item_id");
                data[rowIndex][4] = rs.getString("borrow_id");
                rowIndex++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    public String[][] getTransactionHistory() {
        // Get borrow details
        String borrowQuery = "SELECT DISTINCT date_borrowed, full_name, borrower_id, expected_return_date FROM borrow JOIN borrower USING(borrower_id)";
        // Get return details
        String returnQuery = "SELECT return_date, full_name, item_name, item_condition, late_fee FROM (return_log JOIN borrow USING(borrow_id) JOIN borrower USING(borrower_id)) JOIN item USING(item_id) ORDER BY return_date DESC";
        String data[][] = null;

        try{
            // Get borrow and query countquery count
            int rows = getRows(borrowQuery) + getRows(returnQuery);
            System.out.println(rows);

            data = new String[rows][6];
            int rowIndex = 0;

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement ptmt1 = conn.prepareStatement(returnQuery);
            ResultSet rsReturn = ptmt1.executeQuery();
            while(rsReturn.next()) {
                data[rowIndex][0] = "Return";
                data[rowIndex][1] = rsReturn.getString("return_date");
                data[rowIndex][2] = rsReturn.getString("full_name");
                data[rowIndex][3] = rsReturn.getString("item_name");
                data[rowIndex][4] = rsReturn.getString("item_condition");
                data[rowIndex][5] = rsReturn.getString("late_fee");
                rowIndex++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(borrowQuery);
            ResultSet rsBorrow = ptmt.executeQuery();
            while(rsBorrow.next()) {
                data[rowIndex][0] = "Borrow";
                data[rowIndex][1] = rsBorrow.getString("date_borrowed");
                data[rowIndex][2] = rsBorrow.getString("full_name");
                data[rowIndex][3] = rsBorrow.getString("borrower_id");
                data[rowIndex][4] = rsBorrow.getString("expected_return_date");
                data[rowIndex][5] = null;
                rowIndex++;
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            Arrays.sort(data, Comparator.comparing((String[] row) -> row[1]).reversed());
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }

        return data;
    }
}
