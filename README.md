# **General Laboratory Item Management System (GLIMS)**
A Java-based system for managing laboratory equipment and materials in educational institutions.
## ⚙️ **System Requirements**
- Java Runtime Environment (JRE) 11+
- MySQL Server 8.0+
- MySQL Connector/J (JDBC driver)
- Minimum 4GB RAM (8GB recommended)
- 100MB disk space (excluding database storage)
## **Installation Guide**
1. Clone the repository:
   git clone https://github.com/UPTAC-KomSai-v2/General-Laboratory-Item-Database-System.git
2. Set up the MySQL database:
   Import the provided SQL dump:
   mysql -u root -p genlab_db < dumpfile.sql
3. Configure database credentials in the Queries.java code for localization:
   db.url=jdbc:mysql://localhost:3306/genlab_db
   db.username = _username_
   db.password = _password_
4. Run the application:
   java -jar GLIMS.jar
## 🔐**User Authentication**
1. Open Command Prompt (Windows) or Terminal (macOS/Linux).
2. Log in to MySQL:
   mysql -u _username_ -p
3. Create a user with privileges:
   CREATE USER _username_@_hostname_ IDENTIFIED BY _password_;
   GRANT ALL PRIVILEGES ON genlab_db.* TO _username_@_hostname_;
   FLUSH PRIVILEGES;
4. Update Queries.java with the newly created credentials.
## 🖥️ **System Operations**
- **Borrow Item**
  1. Select items (highlighted gray) from categories.
  2. Click "Add to basket" (items turn yellow).
  3. Adjust quantities with +/- buttons.
  4. Click "Continue", enter borrower details, and confirm with "Borrow Now".
- **Borrower List**
  1. View all borrowers and their details.
  2. Click a borrower to see borrowed items.
  3. Use "Return" or "Return All" to process returns.
  4. Click "Confirm" to update and commite operations done.
- **Update Inventory**
  1. Modify item quantities by clicking table cells.
  2. Add Item: Enter details in the prompted window forms.
  3. Remove Item: Select and click "Remove Item".
- Transaction History
  1. Default view: All transactions.
  2. Filter by:
     - "View Borrow" (outgoing).
     - "View Return" (ingoing).
