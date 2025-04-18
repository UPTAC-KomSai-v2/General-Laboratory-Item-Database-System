// This class will serve as a test for the database connection and query execution. 
// Bagan la mysql cli version HAHAHAHA

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12773093?useSSL=false";
        String username = "sql12773093";
        String password = "6e6zJ2BwSj";
        Scanner scn = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connection successful!");

            while (true) {
                System.out.println("\nEnter your SQL query (or type 'exit' to quit):");
                String query = scn.nextLine().trim();

                if (query.equalsIgnoreCase("exit")) {
                    break;
                }

                if (query.isEmpty()) {
                    System.out.println("Query cannot be empty. Try again.");
                    continue;
                }

                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    boolean isResultSet = pstmt.execute();

                    // Handle results (for SELECT) or update count (for INSERT/UPDATE/DELETE)
                    if (isResultSet) {
                        try (ResultSet rs = pstmt.getResultSet()) {
                            ResultSetMetaData metaData = rs.getMetaData();
                            int columnCount = metaData.getColumnCount();

                            // Print column headers
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(metaData.getColumnName(i) + "\t");
                            }
                            System.out.println();

                            // Print rows
                            while (rs.next()) {
                                for (int i = 1; i <= columnCount; i++) {
                                    System.out.print(rs.getString(i) + "\t");
                                }
                                System.out.println();
                            }
                        }
                    } else {
                        int updateCount = pstmt.getUpdateCount();
                        System.out.println("Query executed successfully. Rows affected: " + updateCount);
                    }
                } catch (SQLException e) {
                    System.err.println("SQL Error: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        } finally {
            scn.close();
            System.out.println("Program terminated.");
        }
    }
}