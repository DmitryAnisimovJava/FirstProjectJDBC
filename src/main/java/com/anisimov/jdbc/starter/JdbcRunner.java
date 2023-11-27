package com.anisimov.jdbc.starter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.Driver;

import com.anisimov.jdbc.starter.util.ConnectionManager;

public class JdbcRunner {

	public static void main(String[] args) throws SQLException {
		Class<Driver> driverClass = Driver.class;
		String sql = """
				CREATE TABLE IF NOT EXISTS info (
					id SERIAL PRIMARY KEY,
					name TEXT NOT NULL
				);
				""";
		String sqlSecond = """
				INSERT INTO info (name)
				VALUES
				('Test1'),
				('Test2'),
				('Test3'),
				('Test4');
				INSERT INTO info (name)
				VALUES
				('Test1'),
				('Test2'),
				('Test3'),
				('Test4');
				""";
		String sqlThird = """
				UPDATE info
				SET name = 'ShitFest'
				WHERE id < 2 OR id > 7
				RETURNING *;
				""";
		String sqlSelect = """
				SELECT *
				FROM ticket;
				""";
		String sqlInsert = """
				INSERT INTO info (name)
				VALUES
				('TestGenerated');
				""";
		try (var connection = ConnectionManager.open();
				var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			System.out.println(connection.getTransactionIsolation());
			var result = statement.executeUpdate(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			var generatedKeys = statement.getGeneratedKeys();
//			while (result.next()) {
//				System.out.println(result.getLong("id") + "  " + result.getString("passenger_name") + "  "
//						+ result.getBigDecimal("cost"));
			if (generatedKeys.next()) {
				System.out.println(generatedKeys.getInt("id"));
			}
		}
	}
}
