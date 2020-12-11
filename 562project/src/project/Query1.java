package project;

import java.sql.*;

/**
 * A JDBC SELECT (JDBC query) example program.
 */
class Query1 {
 
    public static void main (String[] args) {
        try {
            String url = "jdbc:postgresql://localhost/project";
            Connection conn = DriverManager.getConnection(url,"postgres","1234");
            Statement stmt = conn.createStatement();
            ResultSet rs;
 
            rs = stmt.executeQuery("SELECT cust, prod, avg(quant), max(quant) FROM sales where year=2009 group by cust, prod");
            while ( rs.next() ) {
            	System.out.println(rs.getString(1)); 
            	System.out.println(rs.getString(2));
            	System.out.println(rs.getString(3)); 
            	System.out.println(rs.getString(4)); 
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}