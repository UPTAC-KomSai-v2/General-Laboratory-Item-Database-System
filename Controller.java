import java.awt.Color;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Frame;

public class Controller {
    private Queries queries;

    // Data structures to use during runtime
    private List<String[]> borrowerListEntries;
    private List<String[]> allBorrowRecords;
    private List<String[]> allTransactionHistory;
    private List<String[]> categoryList;
    private Map<Integer, List<String>> itemsByCategory; 
    private Map<String, Integer> itemIDMap; // Maps item name with unit to item ID
    private Map<Integer, String[]> itemDetailsMap; // Maps itemID to [itemName, unit]
    private Map<String, String[]> courseSectionsMap; // Maps course ID to available sections
    private Map<String, Map<String, String[]>> courseInstructorsMap; // Maps course+section to available instructors
    private List<String[]> allCourses; // All courses data

    public Controller(){
        this.queries = new Queries();
        this.itemsByCategory = new HashMap<>();
        this.itemIDMap = new HashMap<>();
        this.itemDetailsMap = new HashMap<>();
        this.courseSectionsMap = new HashMap<>();
        this.courseInstructorsMap = new HashMap<>();
    }


    // GETS ALL INFORMATION FROM THE DATABASE AFTER LOG-IN
    public void controllerGetAllDatabaseInformation(){
            System.out.println("GETTING DATABASE DATA (1/7)");
            this.borrowerListEntries = queries.getBorrowList();
            System.out.println("GETTING DATABASE DATA (2/7)");
            this.allBorrowRecords = queries.getAllItemsBorrowed();
            System.out.println("GETTING DATABASE DATA (3/7)");
            this.allTransactionHistory = queries.getTransactionHistory();
            System.out.println("GETTING DATABASE DATA (4/7)");
            this.categoryList = queries.getAllCategories();
            System.out.println("GETTING DATABASE DATA (5/7)");
            loadData();
            System.out.println("GETTING DATABASE DATA (6/7)");
            loadCourseData();
            System.out.println("GETTING DATABASE DATA (7/7)");
            System.out.println("DATABASE DATA RETRIEVED SUCCESSFULLY");
    }

    
    public void controllerGetAllDatabaseInformationWithProgress() {
        // Create progress dialog components
        JDialog progressDialog = new JDialog((Frame) null, "Loading Database", true);
        JProgressBar progressBar = new JProgressBar(0, 7);
        progressBar.setStringPainted(true);
        progressDialog.setLayout(new BorderLayout());
        progressDialog.add(new JLabel("Fetching data, please wait...", SwingConstants.CENTER), BorderLayout.NORTH);
        progressDialog.add(progressBar, BorderLayout.CENTER);
        progressDialog.setSize(300, 100);
        progressDialog.setLocationRelativeTo(null);

        // Create a worker thread to perform database loading
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish(1);
                borrowerListEntries = queries.getBorrowList();
                publish(2);
                allBorrowRecords = queries.getAllItemsBorrowed();
                publish(3);
                allTransactionHistory = queries.getTransactionHistory();
                publish(4);
                categoryList = queries.getAllCategories();
                publish(5);
                loadData();
                publish(6);
                loadCourseData();
                publish(7);
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int latestProgress = chunks.get(chunks.size() - 1);
                progressBar.setValue(latestProgress);
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                JOptionPane.showMessageDialog(null, "Database data retrieved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
    }


    // Cache category data for quick access
     private void loadData() {
        // Load all categories into memory
        this.categoryList = queries.getAllCategories();

        // Preload items for each category
        for (String[] category : categoryList) {
            int categoryId = Integer.parseInt(category[0]);
            String[] items = queries.getItems(categoryId);
            itemsByCategory.put(categoryId, Arrays.asList(items));

            // Populate itemIDMap for fast lookup
            for (String item : items) {
                int itemID = queries.getItemIDWithUnit(item);
                itemIDMap.put(item, itemID);

                // Populate itemDetailsMap
                String[] parts = item.split(" - ", 2); // Extract name and unit
                String itemName = parts[0].trim();
                String unit = (parts.length > 1) ? parts[1].trim() : null;
                itemDetailsMap.put(itemID, new String[]{itemName, unit});
            }
        }
    }

    private void loadCourseData() {
        // Load course data
        String[] courseIDs = queries.getCourseOption();
        allCourses = new ArrayList<>();
        
        // Store course data and pre-load sections for each course
        for (String courseID : courseIDs) {
            allCourses.add(new String[]{courseID});
            
            // Pre-load sections for this course
            String[] sections = queries.getSectionOption(courseID);
            courseSectionsMap.put(courseID, sections);
            
            // Initialize course-section-instructor map
            Map<String, String[]> sectionInstructorsMap = new HashMap<>();
            courseInstructorsMap.put(courseID, sectionInstructorsMap);
            
            // Pre-load instructors for each section
            for (String section : sections) {
                String[] instructors = queries.getInstructorOption(courseID, section);
                sectionInstructorsMap.put(section, instructors);
            }
        }
    }
    
    // =========================================
    // ========== Login Panel Methods ==========
    // =========================================

    public boolean controllerLogin(GUILoginPanel loginPanel, JFrame mainFrame, GUIMainPanel mainPanel, JLabel statusLabel){
        statusLabel.setText("Logging in...");
        statusLabel.setForeground(Color.BLACK);
        boolean loginSuccess = false;
        try {
            String userInput = loginPanel.lgnInputUsernameField.getText();
            char[] passInputChars = loginPanel.lgnInputPasswordField.getPassword();
            String passInput = new String(passInputChars);

            System.out.println("Fetched Username: " + userInput);
            System.out.println("Fetched Password: " + passInput);

            if (queries.login(userInput, passInput, mainFrame)){
                System.out.println("Login Successful");
                loginPanel.lgnStatusLabel.setText("Login Success");
                loginPanel.lgnStatusLabel.setForeground(Color.GREEN);
                JOptionPane.showMessageDialog(mainFrame, new JLabel("Login successful!", SwingConstants.CENTER), "Success", JOptionPane.PLAIN_MESSAGE );

                //controllerGetAllDatabaseInformation();
                controllerGetAllDatabaseInformationWithProgress();
                loginSuccess = true;

                loginPanel.lgnInputUsernameField.setText("");
                loginPanel.lgnInputPasswordField.setText("");
                mainFrame.remove(loginPanel);
                mainFrame.add(mainPanel);
                mainFrame.revalidate();
                mainFrame.repaint();
            } else{
                statusLabel.setText("Login Denied.\n Please Try Again.");
                statusLabel.setForeground(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error occurred.");
            statusLabel.setForeground(Color.RED);
        }
        return loginSuccess;
    }
    
    // ========================================
    // ========== Main Panel Methods ==========
    // ========================================

    // ================================================
    // ========== Borrow Items Panel Methods ==========
    // ================================================

    public String getCategoryName(int categoryID) {
        for (String[] category : categoryList) {
            if (Integer.parseInt(category[0]) == categoryID) {
                return category[1];
            }
        }
        return null;
    }

    public String[] getItemsInCategory(int categoryID) {
        List<String> items = itemsByCategory.get(categoryID);
        return items != null ? items.toArray(new String[0]) : new String[0];
    }
    
    public int getCategoryID(String categoryName) {
        for (String[] category : categoryList) {
            if (category[1].equals(categoryName)) {
                return Integer.parseInt(category[0]);
            }
        }
        return -1;
    }

    public int getItemIDWithUnit(String itemNameWithUnit) {
        return itemIDMap.getOrDefault(itemNameWithUnit, -1);
    }

    public String getItemNameWithUnit(int itemID) {
        String[] details = itemDetailsMap.get(itemID);
        if (details == null) return null;

        String itemName = details[0];
        String unit = details[1];
        return (unit != null && !unit.isEmpty()) ? itemName + " - " + unit : itemName;
    }

    // Retrieve item name by item ID
    public String getItemName(int itemID) {
            String[] details = itemDetailsMap.get(itemID);
            return (details != null) ? details[0] : null;
    }
    
    public List<String[]> getCategoryList() {
        return categoryList;
    }

    public String[] getCourseOptions() {
        if (allCourses == null || allCourses.isEmpty()) {
            return new String[]{"Select a Course"};
        }
        
        String[] courseOptions = new String[allCourses.size() + 1];
        courseOptions[0] = "Select a Course";
        
        for (int i = 0; i < allCourses.size(); i++) {
            courseOptions[i+1] = allCourses.get(i)[0];
        }
        
        return courseOptions;
    }

    public String[] getSectionOptions(String courseID) {
        if (courseID == null || courseID.equals("Select a Course") || !courseSectionsMap.containsKey(courseID)) {
            return new String[]{"Select a Section"};
        }
        
        String[] sections = courseSectionsMap.get(courseID);
        String[] sectionOptions = new String[sections.length + 1];
        sectionOptions[0] = "Select a Section";
        
        System.arraycopy(sections, 0, sectionOptions, 1, sections.length);
        
        return sectionOptions;
    }

    public String[] getInstructorOptions(String courseID, String sectionName) {
        if (courseID == null || sectionName == null || 
            courseID.equals("Select a Course") || sectionName.equals("Select a Section") ||
            !courseSectionsMap.containsKey(courseID) || 
            !courseInstructorsMap.get(courseID).containsKey(sectionName)) {
            return new String[]{"Select an Instructor"};
        }
        
        String[] instructors = courseInstructorsMap.get(courseID).get(sectionName);
        String[] instructorOptions = new String[instructors.length + 1];
        instructorOptions[0] = "Select an Instructor";
        
        System.arraycopy(instructors, 0, instructorOptions, 1, instructors.length);
        
        return instructorOptions;
    }

    public boolean validateBorrowerInfoInputs(
            List<JTextField> studentNumberFields,
            List<JTextField> fullNameFields,
            List<JTextField> emailAddressFields,
            List<JTextField> contactNumberFields,
            List<JComboBox<String>> degreeProgramComboBoxes) {

        for (int i = 0; i < studentNumberFields.size(); i++) {
            String studentNumber = studentNumberFields.get(i).getText().trim();
            String fullName = fullNameFields.get(i).getText().trim();
            String email = emailAddressFields.get(i).getText().trim();
            String contact = contactNumberFields.get(i).getText().trim();
            String degreeProgram = (String) degreeProgramComboBoxes.get(i).getSelectedItem();

            // Validate student number: YYYY-XXXXX
            if (!studentNumber.matches("\\d{4}-\\d{5}")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid student number at row " + (i + 1) + ". Format must be YYYY-XXXXX.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Validate email
            if (!email.matches("^[a-zA-Z0-9._%+-]+@up\\.edu\\.ph$")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid email at row " + (i + 1) + ". Email must end with '@up.edu.ph'.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Validate contact number: 09xxxxxxxxx (11 digits)
            if (!contact.matches("^09\\d{9}$")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid contact number at row " + (i + 1) + ". Must start with 09 and be 11 digits long.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Validate degree program selection
            if ("Select an Option".equals(degreeProgram)) {
                JOptionPane.showMessageDialog(null,
                        "Please select a degree program at row " + (i + 1) + ".",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Optional: check full name is not empty
            if (fullName.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Full name cannot be empty at row " + (i + 1) + ".",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true; // All inputs are valid
    }

    public int getLatestBorrowID() {
        return queries.getLatestBorrowID();
    }

    // Insert borrower information into the database
    public boolean insertBorrowerInfo(String borrowerID, String fullName, String email, 
                                String contactNumber, String degreeProgram) {
        return queries.insertBorrowerInfo(borrowerID, fullName, email, contactNumber, degreeProgram);
    }

    // Get section ID by section name
    public int getSectionID(String sectionName) {
        return queries.getSectionID(sectionName);
    }

    // Insert borrow record into the database
    public boolean borrowItem(int borrowID, int itemID, String borrowerID, 
                        String courseID, int sectionID, int qtyBorrowed, Timestamp ts) {
        return queries.borrowItem(borrowID, itemID, borrowerID, courseID, sectionID, qtyBorrowed, ts);
    }

    // Refresh cached data after transaction
    public void refreshCachedData() {
        // Refresh all the cached data structures
        this.borrowerListEntries = queries.getBorrowList();
        this.allBorrowRecords = queries.getAllItemsBorrowed();
        this.allTransactionHistory = queries.getTransactionHistory();
        // You might want to refresh other cached data as needed
        System.out.println("Cached data refreshed");
    }

    // =================================================
    // ========== Borrower List Panel Methods ==========
    // =================================================

    // get items borrowed by specific borrower
    public List<String[]> getItemsBorrowedByBorrower(String borrowerID, String dateBorrowed, String expectedReturnDate) {
        List<String[]> filteredList = new ArrayList<>();

        for (String[] row : allBorrowRecords) {
            String rowBorrowerId = row[5]; // borrower_id
            String rowDateBorrowed = row[6]; // date_borrowed
            String rowExpectedReturnDate = row[7]; // expected_return_date

            if (rowBorrowerId.equals(borrowerID) &&
                rowDateBorrowed.equals(dateBorrowed) &&
                rowExpectedReturnDate.equals(expectedReturnDate)) {
                
                // Convert to String[] directly
                filteredList.add(new String[] { row[0], row[1], row[2], row[3], row[4] });
            }
        }
        return filteredList;
    }

    // Getters
    public List<String[]> getBorrowerListEntries(){
        return borrowerListEntries;
    }

    // ====================================================
    // ========== Update Inventory Panel Methods ==========
    // ====================================================

    // Get items for a specific category
    public String[][] getItemsPerCategory(int categoryId) {
        return queries.getItemsPerCategory(categoryId);
    }

    // Get total number of items
    public int getNoOfItems() {
        return queries.getNoOfItems();
    }

    // Import items from CSV
    public void importItemsFromCSV(String filepath, String[][] data) {
        queries.importToItems(filepath, data);
        // Refresh cached data after import
        refreshCachedData();
        // Update category-specific data structures
        loadData();
    }

    // Remove item from database
    public void removeItemFromDatabase(int itemId) {
        queries.removeItemFromDatabase(itemId);
        // Refresh cached data after removal
        refreshCachedData();
        // Update category-specific data structures
        loadData();
    }

    // Add item to database
    public void addItemToDatabase(String name, String unit, int quantity, int categoryId) {
        queries.addItemToDatabase(name, unit, quantity, categoryId);
        // Refresh cached data after adding
        refreshCachedData();
        // Update category-specific data structures
        loadData();
    }

    // =======================================================
    // ========== Transaction History Panel Methods ==========
    // =======================================================

    public List<String[]>  getAllTransactionHistory(){
        return allTransactionHistory;
    }

    // =======================================================
    // ================== Get queries class ==================
    // =======================================================
    public Queries getQueries() {
        return queries;
    }
}
