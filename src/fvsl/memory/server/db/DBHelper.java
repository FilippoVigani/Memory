package fvsl.memory.server.db;

import java.sql.*;

import fvsl.memory.common.util.StringResources;

// TODO: Auto-generated Javadoc
/**
 * The Class DBHelper.
 */
public class DBHelper {
	private static Connection connection = null;
	// 1.jdbc driver name
	private static String SQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	// 2. Database URL, V_UDAY\FRAMEWORK is ServerName and JSF is DataBase name
	private static String URL = "jdbc:mysql://localhost:3306/MemoryDB";
	// 3.Database credentials
	private static String USERNAME = "root";// UserName
	private static String PASSWORD = "";// Password

	/**
	 * Gets the local connection.
	 *
	 * @return the local connection
	 */
	public static Connection getLocalConnection() {
		try {
			Class.forName(SQL_JDBC_DRIVER);// Register jdbc driver

			System.out.println(StringResources.conToDB);

			// 4. open a connection
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			System.out.println(StringResources.conDBTo + connection.getMetaData().getDriverName());
			System.out.println(StringResources.url + connection.getMetaData().getURL());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(StringResources.conExc + e.getMessage());
		}
		return connection;
	}

	/**
	 * Sets the connection close.
	 *
	 * @throws SQLException the SQL exception
	 */
	public static void setConnectionClose() throws SQLException {
		if (connection != null) {
			connection.close();
			System.out.println(StringResources.closedDBCon);
		}
	}

	/**
	 * View table.
	 *
	 * @param con the con
	 * @param dbName the db name
	 * @throws SQLException the SQL exception
	 */
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
		} catch (SQLException e) {
			System.err.print(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

}
