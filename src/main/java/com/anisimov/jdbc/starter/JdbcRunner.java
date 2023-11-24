package com.anisimov.jdbc.starter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JdbcRunner {

	public static void main(String[] args) throws SQLException {
		Class<Driver> driverClass = Driver.class;
		String username = "admin";
		String password = "password";
		String urlString = "jdbc:postgresql://localhost:5432/flight_repository";
		try (Connection connector = DriverManager.getConnection(urlString, username, password)) {
			System.out.println(connector.getTransactionIsolation());
		}

	}
}
