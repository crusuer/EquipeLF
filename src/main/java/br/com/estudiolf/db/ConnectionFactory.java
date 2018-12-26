package br.com.estudiolf.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection getConnection() {
		try {
			String dbUrl = System.getenv("JDBC_DATABASE_URL");
			return DriverManager.getConnection(dbUrl);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
