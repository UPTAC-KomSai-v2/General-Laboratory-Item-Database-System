# **General Laboratory Item Management System (GLIMS)**
A Java-based system for managing laboratory equipment and materials at the University of the Philippines Tacloban.
## ⚙️ **System Requirements**
- Java Runtime Environment (JRE) 11+
- MySQL Server 8.0+
- MySQL Connector/J (JDBC driver)
- Minimum 4GB RAM (8GB recommended)
- 100MB disk space (excluding database storage)
## ⬇️ **Installation Guide**
1. Clone the repository:
   git clone https://github.com/UPTAC-KomSai-v2/General-Laboratory-Item-Database-System.git
   - You may just opt to run the installer GLIMS.exe for program usage (no need to proceed the succeeding step).
   > **Note**: Before running the application, make sure to accomplish user authentication and necessary user additions for the database.
2. Run the application:
   java -jar GLIMS.jar
   > **Note**: Before running the application, make sure to accomplish user authentication and necessary user additions for the database.
## 🔐 **User Authentication**
1. Open Command Prompt (Windows) or Terminal (macOS/Linux).
2. Log in to MySQL:
   mysql -u _username_ -p
3. Create a user with privileges:
   - CREATE USER _username_@_hostname_ IDENTIFIED BY _password_;
   - GRANT ALL PRIVILEGES ON genlab_db.* TO _username_@_hostname_;
   - FLUSH PRIVILEGES;
   > **Note**: For this implementation,  any of the following users should be added since the triggers were created online and the definers are the following users:
      - USE genlab_db;
      - CREATE USER 'rolf'@'%';
      - CREATE USER 'jade'@'%';
      - CREATE USER 'root'@'%';
      - GRANT ALL PRIVILEGES ON genlab_db.* TO 'rolf'@'%';
      - GRANT ALL PRIVILEGES ON genlab_db.* TO 'jade'@'%';
      - GRANT ALL PRIVILEGES ON genlab_db.* TO 'root'@'%';
      - FLUSH PRIVILEGES;
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
4. Click "Confirm" to update and commit operations done.
- **Update Inventory**
1. Modify item quantities by clicking table cells.
2. Add Item: Enter details in the prompted window forms.
3. Remove Item: Select and click "Remove Item".
- Transaction History
1. Default view: All transactions.
2. Filter by:
   - "View Borrow" (outgoing).
   - "View Return" (ingoing).
## 🗃️ **Database Schema**
### Core Tables
|  **Table** |     **Description**	  |          **Attributes**           |
| ---------- | ---------------------- | --------------------------------- |
| item	    | Laboratory items	     | item_id, item_name, status        |
| borrower	 | Student borrowers	     | borrower_id, full_name, email     |
| borrow	    | Borrowing transactions | borrow_id, item_id, date_borrowed |
| return_log |	Item returns	        | return_id, late_fee               |
### Administrative Tables
|  **Table** |     **Description**	  |          **Attributes**           |
| ---------- | ---------------------- | --------------------------------- |
| staff_user | Staff accounts	        | log_ID, username, last_login      |
> [!Note] > Full schema details in [Manual](https://drive.google.com/file/d/1W5zm0mHQmzFvs8H9uMmxPTNqgcPY5iYR/view?usp=drive_link)

For troubleshooting and technical support, contact rlgarces@up.edu.ph
