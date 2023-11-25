package com.anisimov.jdbc.starter;

import java.sql.SQLException;

import org.postgresql.Driver;

import com.anisimov.jdbc.starter.util.ConnectionManager;

public class JdbcRunner {

	public static void main(String[] args) throws SQLException {
		Class<Driver> driverClass = Driver.class;
		try (var connection = ConnectionManager.open()) {
			System.out.println(connection.getTransactionIsolation());
		}

	}
}
