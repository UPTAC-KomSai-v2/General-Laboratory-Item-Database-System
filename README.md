# General Laboratory Items Management System (GLIMS)

![Project Status](https://img.shields.io/badge/status-stable-green.svg)
![Java Version](https://img.shields.io/badge/java-11%2B-blue.svg)
![Database](https://img.shields.io/badge/database-MySQL_8.0-orange.svg)
![License](https://img.shields.io/badge/license-MIT-lightgrey.svg)

A robust, full-stack desktop application built with Java Swing and MySQL for managing and tracking laboratory equipment and materials for the UP Tacloban College General Laboratory.

---

## Table of Contents

- [About The Project](#about-the-project)
- [Key Features](#key-features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Project Architecture](#project-architecture)
- [Database Schema](#database-schema)
- [Authors](#authors)
- [License](#license)

---

## About The Project

GLIMS is an all-in-one solution designed to replace manual, paper-based inventory tracking with an efficient, reliable, and user-friendly digital system. It provides laboratory staff with the tools to oversee the entire lifecycle of lab items—from borrowing and returning to inventory updates and historical tracking.

The system is built with data integrity and user experience as top priorities, featuring a clean MVC architecture, robust database transaction management, and a responsive Swing-based user interface.

---

## Key Features

✨ **Secure Authentication:** A dedicated login screen to ensure only authorized staff can access the system.

📋 **Multi-Step Borrowing Process:**
- Browse items by category.
- Add multiple items to a "borrow basket".
- Adjust quantities before checkout.
- Record detailed borrower and course information.

↩️ **Efficient Item Returns:**
- View a list of all active borrowers.
- Select specific items and quantities to return.
- Automatic calculation of late fees based on database triggers.

📦 **Comprehensive Inventory Management:**
- View item stock levels by category.
- Directly update item quantities in a table view.
- Add new items or permanently remove obsolete ones.
- One-click import of initial inventory from a CSV file.

📈 **Complete Transaction History:**
- A chronological log of all borrow and return events.
- Filter the view to see only borrows, only returns, or all transactions.

🧾 **Automated Receipt Generation:**
- Automatically creates and saves `.txt` receipts for both borrow and return transactions for auditing and record-keeping.

🚀 **Automated Database Deployment:**
- On first login with root/admin credentials, the system automatically creates the database, schema, tables, triggers, and populates initial data.

---

## Screenshots

| Login Screen                                     | Main Menu                               |
| ------------------------------------------------ | --------------------------------------- |
| ![Login Screen](docs/screenshots/login.png)      | ![Main Menu](docs/screenshots/main_menu.png) |
| **Borrowing Process (Step 1: Item Selection)**   | **Inventory Management Panel**          |
| ![Borrow Items](docs/screenshots/borrow_items.png) | ![Update Inventory](docs/screenshots/inventory.png) |

---

## Tech Stack

- **Frontend:** Java Swing
- **Backend:** Java
- **Database:** MySQL
- **Connectivity:** JDBC (Java Database Connectivity)

---

## Getting Started

Follow these instructions to get a local copy of GLIMS up and running on your machine.

### Prerequisites

You must have the following software installed on your system:

- **Java Development Kit (JDK):** Version 24 or newer.
- **MySQL Server:** Version 8.0 or newer.
- **An IDE (Optional but Recommended):** IntelliJ IDEA, Eclipse, or VS Code with Java extensions.

### Installation

1.  **Clone the Repository**
    ```sh
    git clone https://github.com/your-username/glims-repo.git
    ```

2.  **Set Up MySQL User**
    Ensure you have a MySQL user with privileges to `CREATE DATABASE`. The `root` user will work, or you can create a dedicated user for this application.
    ```sql
    -- Example of creating a new user in MySQL
    CREATE USER 'glims_admin'@'localhost' IDENTIFIED BY 'your_password';
    GRANT ALL PRIVILEGES ON *.* TO 'glims_admin'@'localhost' WITH GRANT OPTION;
    FLUSH PRIVILEGES;
    ```

3.  **Run the Application for the First Time**
    - Open the project in your IDE.
    - Locate and run the `Main.java` file.
    - The application will launch, presenting the login screen.
    - **Crucially**, for the very first run, log in using the MySQL user credentials you set up in the previous step (e.g., username `glims_admin` or `root`).
    - The application will detect that the `genlab_db` database does not exist and will **automatically create it**, import the schema, populate it with data, and set up all necessary triggers. This process is handled by the `ImportSQLWithJDBC` class.

4.  **Subsequent Logins**
    After the initial setup, the database and a default staff user will be created. You can then log in with the application-specific credentials for daily use.

---

## Usage

1.  **Login** with your staff credentials.
2.  Use the four main buttons on the **Main Menu** to navigate to the desired function:
    - **Borrow Item:** To check out new items for a borrower.
    - **Borrower List:** To view active loans and process returns.
    - **Update Inventory:** To manage the master item list.
    - **Transaction History:** To review past activities.
3.  Follow the on-screen instructions and prompts within each panel.
4.  Use the **"Go Back"** button to return to the Main Menu from any sub-panel.
5.  Always use the **"Logout"** button to securely exit the application.

---

## Project Architecture

The project is structured using the **Model-View-Controller (MVC)** design pattern to ensure a clean separation of concerns.

-   **Model:** Represents the data and business logic.
    -   `genlab_schema.txt`: The database schema defines the data structure.
    -   `Queries.java`: Acts as the Data Access Object (DAO), handling all JDBC communication and SQL queries. It uses `PreparedStatement` to prevent SQL injection and manages database transactions for data integrity.
    -   Database Triggers: Core business logic (e.g., updating stock on borrow) is embedded in the database itself for maximum robustness.

-   **View:** The user interface of the application.
    -   `GUI*.java` files: All classes prefixed with `GUI` are responsible for rendering the UI components. They do not contain business logic.
    -   `Branding.java`: A centralized class for managing UI constants like colors, fonts, and icons, ensuring a consistent look and feel.
    -   `LoadingScreen.java`: Provides user feedback during long-running initial data loads.

-   **Controller:** Acts as the intermediary between the Model and the View.
    -   `Controller.java`: Manages the application's state by caching data from the database on startup. It handles user input from the View, processes it, and calls the appropriate methods in the Model. It then updates the View with the results.
    -   `GraphicalUserInterface.java`: Acts as the main event handler, orchestrating the swapping of different View panels and delegating actions to the `Controller`. It uses `SwingWorker` for background processing to keep the UI responsive.

---

## Database Schema

The database is designed to be normalized and robust, with foreign key constraints, indexes for performance, and views for simplifying complex queries.

A brief overview of the key tables:
- `borrower`: Stores information about individuals who borrow items.
- `item`: The master list of all laboratory items, their quantities, and categories.
- `borrow`: A transactional table linking borrowers to the items they've borrowed, including dates and quantities.
- `return_log`: A log of all returned items, which automatically calculates and stores any applicable late fees.
- `category`, `course`, `section`, `instructor`: Supporting tables that normalize the data.

An Entity-Relationship Diagram (ERD) would look like this:

![ERD](docs/erd/database_schema.png)

---

## Authors

This project was developed by:

- **Sean Harvey Bantanos** - *Database Architect*
- **Mac Darren Louis Calimba** - *Frontend Developer*
- **Norman Enrico Eulin** - *Frontend Developer*
- **Rolf Genree Garces** - *Backend Developer*
- **Jhun Kenneth Iniego** - *Backend Developer*
- **Jade Eric Petilla** - *Database Architect*
- **Gian Angelo Tongzon** - *Frontend Developer*

---

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
