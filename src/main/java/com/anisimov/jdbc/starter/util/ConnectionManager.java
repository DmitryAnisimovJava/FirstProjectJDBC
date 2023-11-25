package com.anisimov.jdbc.starter.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "password";
	private static final String URL = "jdbc:postgresql://localhost:5432/flight_repository";

	static {
		loadDriver();
	}

	private ConnectionManager() {
	}

	private static void loadDriver() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection open() {
		try {
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
}