package br.com.estudiolf.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection getConnection() {
		try {
			// System.getenv("JDBC_DATABASE_URL");
			final String dbUrl = "jdbc:postgresql://ec2-54-247-125-116.eu-west-1.compute.amazonaws.com:5432/d9bb8kmgv0vekg?user=xwbcoowsjsdgxi&password=49b1d4650e21ec803d8172614a2f04515b506fcdedd80a715bb66a0a8c8ed2a1&sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
			//final String dbUrl = "jdbc:postgresql://localhost:5432/equipe?user=postgres&password=shadow&verifyServerCertificate=false&useSSL=false&requireSSL=false";
			return DriverManager.getConnection(dbUrl);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
