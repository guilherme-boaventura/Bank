package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ConnectionFactory {

	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final static String URL = "jdbc:mysql://192.168.0.10:3306/bank";
	private final static String USER = "user";
	private final static String PASSWORD = "pass";

	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Driver do banco de dados não encontrado");
		} catch (SQLException e) {
			throw new RuntimeException("Conexão com banco de dados impossibilitada: " + e.getMessage());
		}
	}

	public static void closeConnection(Connection con) {
		try {
			if(con!=null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt) {
		closeConnection(con);
		try {
			if(stmt!=null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
		closeConnection(con, stmt);
		try {
			if(rs!=null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}