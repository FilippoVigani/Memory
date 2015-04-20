package fvsl.memory.server.db;
import java.sql.DriverManager;
import java.sql.*;

public class DBHelper {
	private static Connection connection = null;
    //1.jdbc driver name 
    private static String SQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // 2. Database URL, V_UDAY\FRAMEWORK is ServerName and JSF is DataBase name 
    private static String URL = "jdbc:mysql://localhost:3306/MemoryDB";
    //3.Database credentials 
    private static String USERNAME = "root";//UserName
    private static String PASSWORD = "";//Password
	 
	public static Connection getLocalConnection() {
	    try { 
	        Class.forName(SQL_JDBC_DRIVER);// Register jdbc driver
	 
	        System.out.println("****Connect to Database****");
	 
	        //4. open a connection 
	        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	 
	        System.out.println("DataBase connect to: "+ connection.getMetaData().getDriverName());
	        System.out.println("URL: "+ connection.getMetaData().getURL());
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("Exception in getLocalConeection() "+e.getMessage());
	    } 
	    return connection;
	} 
	 
	public static void setConnectionClose() throws SQLException {
	    if (connection != null) {
	        connection.close();
	        System.out.println("Database Connection Closed");
	    } 
	} 
 
	public static void viewTable(Connection con, String dbName) throws SQLException {

		    Statement stmt = null;
		    String query = "select * from " + dbName + ".Players";
		    try {
		        stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		            String playerName = rs.getString("Name");
		            System.out.println(playerName);
		        }
		    } catch (SQLException e ) {
		        System.err.print(e);
		    } finally {
		        if (stmt != null) { stmt.close(); }
		    }
		}
	
} 
