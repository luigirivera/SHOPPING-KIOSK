package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StoreDB {
	private final static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://127.0.0.1:3306/";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "agentblue";
	public final static String DATABASE = "packnsave";
	
	public Connection getConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection connection = DriverManager.getConnection(URL + 
																DATABASE + 
																"?autoReconnect=true&useSSL=false",
																USERNAME,
																PASSWORD);
			System.out.println("[MYSQL] Connection Successful");
			return connection;
		}catch(SQLException e) {
			System.out.println("[MYSQL] Did not connect");
			e.printStackTrace();
			return null;
		}catch(ClassNotFoundException e) {
			System.out.println("[MYSQL] Unable to Connect");
			e.printStackTrace();
			return null;
		}
	}
}
