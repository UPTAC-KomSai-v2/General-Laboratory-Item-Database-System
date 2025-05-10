import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Queries {
    static final String DB_URL = "jdbc:mysql://caboose.proxy.rlwy.net:51384/genlab_db";
    //private String user = "root";
    //private String pass = "weoZeizOaesHkpjieIetoaQTyKfFwjKm";
    private String user;
    private String pass;
    private Boolean connected;
    private Connection conn;
    private Statement stmt;
    private PreparedStatement ptmt;
    private int currentCategoryID = -1;
    
    public Queries(){}

    public void setUser(String user){
        this.user = user;
    }

    public void setPass(String pass){
        this.pass = pass;
    }

    public Boolean login(String username, String password, JFrame mainFrame){
        setUser(username);
        setPass(password);
        try{
            conn = DriverManager.getConnection(DB_URL, user, pass);
            stmt = conn.createStatement();
            connected = true;
        }catch(SQLException e){
            connected = false;
            JOptionPane.showMessageDialog(mainFrame, 
                "Login failed: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        return connected;
    }

    //This only prints out the items to the terminal (removed from Graphical User Interface Class)
    public void borrowItems(){
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
    
    public void setCurrentCategoryID(int categoryID){
        this.currentCategoryID = categoryID;
    }

    public int getCurrentCategoryID(){
        return currentCategoryID;
    }

    public List<String[]> getAllCategories() {
        List<String[]> categories = new ArrayList<>();
        try {
            String query = "SELECT category_id, category_name FROM category ORDER BY category_name";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String categoryId = String.valueOf(rs.getInt("category_id"));
                String categoryName = rs.getString("category_name");
                categories.add(new String[]{categoryId, categoryName});
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading categories: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return categories;
    }

    public String getCategoryName(int categoryID) {
        String query = "SELECT category_name FROM category WHERE category_id = ?";
        String categoryName = null;
        
        try {
            ptmt = conn.prepareStatement(query);
            ptmt.setInt(1, categoryID);
            ResultSet rs = ptmt.executeQuery();
            
            if (rs.next()) {
                categoryName = rs.getString("category_name");
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        
        return categoryName;
    }
    
    public int getItemQty(int categoryID){
        
        String query = "SELECT COUNT(*) FROM item WHERE category_id = ?";
        try{
            ptmt = conn.prepareStatement(query);
            ptmt.setInt(1, categoryID);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }

            rs.close();
        }catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return 0;
    }

    public String[] getItems(int categoryID) {
        String[] items = new String[getItemQty(categoryID)];
        String query = "SELECT item_name, unit FROM item WHERE category_id = ? ORDER BY item_name";
    
        try {
            PreparedStatement ptmt = conn.prepareStatement(query);
    
            ptmt.setInt(1, categoryID);
            ResultSet rs = ptmt.executeQuery();
    
            int index = 0;
            while (rs.next()) {
                String name = rs.getString("item_name");
                String unit = rs.getString("unit");  // may be null
                if (unit == null || unit.trim().isEmpty()) {
                    items[index++] = name;
                }else{
                    items[index++] = name + " - " + unit;
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public int getItemIDWithoutUnit(String itemName){
        String query = "SELECT item_id FROM item WHERE item_name = ? AND unit IS NULL";
        int itemID = -1;
        
        try {
            ptmt = conn.prepareStatement(query);
            ptmt.setString(1, itemName);
            ResultSet rs = ptmt.executeQuery();
            
            if (rs.next()) {
                itemID = rs.getInt("item_id");
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        
        return itemID;
    }

    public int getCourseQty(){
        String query = "SELECT COUNT(*) FROM course";
        try{
            ptmt = conn.prepareStatement(query);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }

            rs.close();
        }catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return 0;
    }

    public String getCourseID(String courseName){
        String query = "SELECT course_id FROM course WHERE course_name = ?";
        String courseID = null;
        
        try {
            ptmt = conn.prepareStatement(query);
            ptmt.setString(1, courseName);
            ResultSet rs = ptmt.executeQuery();
            
            if (rs.next()) {
                courseID = rs.getString("course_id");
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        
        return courseID;
    }

    public int getSectionQtyWithoutNullValues(String courseID){
        String query = "SELECT COUNT(*) " +
                        "FROM section s " +
                        "JOIN course_section cs ON s.section_id = cs.section_id " +
                        "WHERE cs.course_id = ?";
        try{
            ptmt = conn.prepareStatement(query);
            ptmt.setString(1, courseID);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }

            rs.close();
        }catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return 0;
    }

    public int getSectionID(String sectionName){
        String query = "SELECT section_id FROM section WHERE section_name = ?";
        int sectionID = -1;
        
        try {
            ptmt = conn.prepareStatement(query);
            ptmt.setString(1, sectionName);
            ResultSet rs = ptmt.executeQuery();
            
            if (rs.next()) {
                sectionID = rs.getInt("section_id");
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        
        return sectionID;
    }
    
    public String[] getInstructorOption(String courseID, String sectionName){
        String query = "SELECT i.first_name AS first_name, i.surname AS surname " +
                        "FROM instructor i " +
                        "JOIN course_section cs ON i.instructor_id = cs.instructor_id " + 
                        "WHERE cs.course_id = ? AND cs.section_id = ?";
        String[] instructorOption = new String[getSectionQtyWithoutNullValues(courseID)];
        try { 
            PreparedStatement ptmt = conn.prepareStatement(query);
            ptmt.setString(1, courseID);
            //System.out.println("Course ID: " + courseID);
            ptmt.setInt(2, getSectionID(sectionName));
            //System.out.println("Section ID: " + getSectionID(sectionName));
            ResultSet rs = ptmt.executeQuery();
            //System.out.println("Query executed successfully.");
            int index = 0;
            while (rs.next()) {
                instructorOption[index++] = rs.getString("first_name") + " " + rs.getString("surname");
                //System.out.println("Instructor: " + rs.getString("first_name") + " " + rs.getString("surname"));
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return instructorOption;
    }
    public String[] getSectionOption(String courseID){
        String query = "SELECT s.section_name " +
                        "FROM section s " +
                        "JOIN course_section cs ON s.section_id = cs.section_id " +
                        "WHERE cs.course_id = ?";
        String[] sectionOption = new String[getSectionQtyWithoutNullValues(courseID)];
        try {
            ptmt = conn.prepareStatement(query);
            ptmt.setString(1, courseID);
            ResultSet rs = ptmt.executeQuery();
            
            int index = 0;
            while (rs.next()) {
                sectionOption[index++] = rs.getString("section_name");
                //System.out.println("Section: " + rs.getString("section_name"));
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return sectionOption;
    }

    public String[] getCourseOption(){
        String query = "SELECT course_id FROM course ORDER BY course_name";
        String[] courseOption = new String[getCourseQty()];
        try {
            ptmt = conn.prepareStatement(query);
            ResultSet rs = ptmt.executeQuery();
            
            int index = 0;
            while (rs.next()) {
                courseOption[index++] = rs.getString("course_id");
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return courseOption;
    }

    public int getItemIDWithUnit(String itemNameWithUnit) {
        String query = "SELECT item_id FROM item WHERE item_name = ? AND unit = ?";
        int itemID = -1;

        String[] parts = itemNameWithUnit.split(" - ", 2);
        if (parts.length < 2) {
            return getItemIDWithoutUnit(itemNameWithUnit);
        }

        String itemName = parts[0].trim();
        String unit = parts[1].trim();

        try {
            PreparedStatement ptmt = conn.prepareStatement(query);

            ptmt.setString(1, itemName);
            ptmt.setString(2, unit);
            ResultSet rs = ptmt.executeQuery();

            if (rs.next()) {
                itemID = rs.getInt("item_id");
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }

        return itemID;
    }

    public int getCategoryID(String categoryName){
        String query = "SELECT category_id FROM category WHERE category_name = ?";
        int categoryID = -1;
        
        try {
            ptmt = conn.prepareStatement(query);
            ptmt.setString(1, categoryName);
            ResultSet rs = ptmt.executeQuery();
            
            if (rs.next()) {
                categoryID = rs.getInt("category_id");
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        
        return categoryID;
    }
    
    public String getItemName(int itemID) {
        String query = "SELECT item_name FROM item WHERE item_id = ?";
        String itemName = null;
        
        try {
            ptmt = conn.prepareStatement(query);
            ptmt.setInt(1, itemID);
            ResultSet rs = ptmt.executeQuery();
            
            if (rs.next()) {
                itemName = rs.getString("item_name");

            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        
        return itemName;
    }

    public String getItemNameWithUnit(int itemID) {
        String query = "SELECT item_name, unit FROM item WHERE item_id = ?";
        String itemName = null;
        
        try {
            ptmt = conn.prepareStatement(query);
            ptmt.setInt(1, itemID);
            ResultSet rs = ptmt.executeQuery();
            
            if (rs.next()) {
                if (rs.getString("unit") == null || rs.getString("unit").trim().isEmpty()) {
                    itemName = rs.getString("item_name");
                } else {
                    itemName = rs.getString("item_name") + " - " + rs.getString("unit");
                }
            }
            
            rs.close();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        
        return itemName;
    }

    public int getLatestBorrowID(){
        String query = "SELECT borrow_id FROM borrow ORDER BY borrow_id DESC LIMIT 1";
        try{
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                return rs.getInt(1);
            }

            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void borrowItem(int borrowID, int itemID, String borrowerID, String courseID, int sectionID, int qtyBorrowed) {
        String query = "INSERT INTO borrow(borrow_id, item_id, borrower_id, course_id, section_id, qty_borrowed) VALUES(?, ?, ?, ?, ?, ?)";
        
        try{ 
            PreparedStatement ptmt = conn.prepareStatement(query);
            conn.setAutoCommit(false);
            ptmt.setInt(1, borrowID);
            ptmt.setInt(2, itemID);
            ptmt.setString(3, borrowerID);
            ptmt.setString(4, courseID);
            ptmt.setInt(5, sectionID);
            ptmt.setInt(6, qtyBorrowed);
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            try{
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback Error: " + ex.getMessage());
            }
        }
    }

    public void insertBorrowerInfo(String borrowerID, String fullName, String email, String contactNumber, String degreeProgram){
        String query = "INSERT IGNORE INTO borrower(borrower_id, full_name, email, degree_prog, contact_number) VALUES(?, ?, ?, ?, ?)";
        try(Connection conn = DriverManager.getConnection(DB_URL, user, pass); 
            PreparedStatement ptmt = conn.prepareStatement(query)){
            conn.setAutoCommit(false);
            ptmt.setString(1, borrowerID);
            ptmt.setString(2, fullName);
            ptmt.setString(3, email);
            ptmt.setString(4, degreeProgram);
            ptmt.setString(5, contactNumber);
            System.out.println("String: "+ degreeProgram);
            ptmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {  
            System.err.println("SQL Error: " + e.getMessage());
            try{
                conn.rollback();
            }catch(SQLException ex){
                System.err.println("Rollback Error: " + ex.getMessage());
            }
        }
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

    public void updateActualReturnDate(int borrowID, String borrowerID) {
        String updateQuery = "UPDATE borrow SET actual_return_date = ? WHERE borrow_id = ?";
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts.setNanos(0);

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(updateQuery);
            ptmt.setTimestamp(1, ts);
            ptmt.setInt(2, borrowID);
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
        String addQuery = "INSERT INTO return_log(borrow_id, borrower_id, return_date, item_condition) VALUES(?, ?, ?, ?)";

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement ptmt1 = conn.prepareStatement(addQuery);
            ptmt1.setInt(1, borrowID);
            ptmt1.setString(2, borrowerID);
            ptmt1.setTimestamp(3, ts);
            ptmt1.setString(4, "Good Condition");
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
    
    public List<String[]> getBorrowList() {
        String query = "SELECT DISTINCT full_name, borrower_id, date_borrowed, expected_return_date, " +
                        "degree_prog, course_id, section_name FROM borrow JOIN borrower USING(borrower_id) JOIN course USING(course_id) JOIN section USING(section_id) WHERE actual_return_date IS NULL ORDER BY date_borrowed DESC, expected_return_date DESC";

        // Updated Query as of May 7 2025: "SELECT DISTINCT full_name, borrower_id," + "degree_prog, course_id, section_name FROM borrow JOIN borrower USING(borrower_id) JOIN course USING(course_id) JOIN section USING(section_id)"
        List<String[]> data = new ArrayList<>();

        try{
            ptmt = conn.prepareStatement(query);
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                String[] row = new String[7];
                row[0] = rs.getString("full_name");
                row[1] = rs.getString("borrower_id");
                row[2] = rs.getString("date_borrowed");
                row[3] = rs.getString("expected_return_date");
                row[4] = rs.getString("degree_prog");
                row[5] = rs.getString("course_id");
                row[6] = rs.getString("section_name");
                data.add(row);
            }
        }catch(SQLException e){
            System.err.println("SQL Error 2C: " + e.getMessage());
        }

        return data;
    }


    // used in controller to load all items borrowed by each user
    public List<String[]> getAllItemsBorrowed() {
        List<String[]> dataList = new ArrayList<>();
        String query = "SELECT qty_borrowed, item_name, unit, item_id, borrow_id, borrower_id, date_borrowed, expected_return_date FROM borrow JOIN borrower USING(borrower_id) JOIN item USING(item_id) WHERE actual_return_date IS NULL ORDER BY date_borrowed DESC";

        try {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(query);
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                String[] row = new String[]{
                    rs.getString("qty_borrowed"),
                    rs.getString("item_name"),
                    rs.getString("unit"),
                    rs.getString("item_id"),
                    rs.getString("borrow_id"),
                    rs.getString("borrower_id"),
                    rs.getString("date_borrowed"),
                    rs.getString("expected_return_date")
                };
                dataList.add(row);
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error: " + e1.getMessage());
            }
            System.err.println("SQL Error: " + e.getMessage());
        }

        return dataList;
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
                System.err.println("SQL Error Count1: " + e1.getMessage());
            }
            System.err.println("SQL Error Count1: " + e.getMessage());
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

    public List<String[]> getTransactionHistory() {
        // Get borrow details
        String borrowQuery = "SELECT DISTINCT date_borrowed, full_name, borrower_id, expected_return_date FROM borrow JOIN borrower USING(borrower_id)";
        // Get return details
        String returnQuery = "SELECT r.return_date, br.full_name, i.item_name, r.item_condition, r.late_fee FROM return_log r\r\n" + //
                        "JOIN borrow b ON r.borrow_id = b.borrow_id\r\n" + //
                        "JOIN borrower br ON b.borrower_id = br.borrower_id\r\n" + //
                        "JOIN item i ON b.item_id = i.item_id\r\n" + //
                        "ORDER BY r.return_date DESC";
        List<String[]> data = new ArrayList<>();

        try{
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement ptmt1 = conn.prepareStatement(returnQuery);
            ResultSet rsReturn = ptmt1.executeQuery();
            while(rsReturn.next()) {
                String[] row = new String[6];
                row[0] = "Return";
                row[1] = rsReturn.getString("return_date");
                row[2] = rsReturn.getString("full_name");
                row[3] = rsReturn.getString("item_name");
                row[4] = rsReturn.getString("item_condition");
                row[5] = rsReturn.getString("late_fee");
                data.add(row);
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ptmt = conn.prepareStatement(borrowQuery);
            ResultSet rsBorrow = ptmt.executeQuery();
            while (rsBorrow.next()) {
                String[] row = new String[6];
                row[0] = "Borrow";
                row[1] = rsBorrow.getString("date_borrowed");
                row[2] = rsBorrow.getString("full_name");
                row[3] = rsBorrow.getString("borrower_id");
                row[4] = rsBorrow.getString("expected_return_date");
                row[5] = null;
                data.add(row);
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            data.sort(Comparator.comparing((String[] row) -> row[1]).reversed());
        }catch(SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.err.println("SQL Error 1D: " + e1.getMessage());
            }
            System.err.println("SQL Error 2D: " + e.getMessage());
        }

        return data;
    }
}
