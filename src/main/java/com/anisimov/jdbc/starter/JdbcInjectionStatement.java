package com.anisimov.jdbc.starter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anisimov.jdbc.starter.util.ConnectionManager;

public class JdbcInjectionStatement {

	public static void main(String[] args) throws SQLException {
		String cost = "300 OR ''=''";
		System.out.println(JdbcInjectionStatement.getNameByHigherCost(cost));
	}

	private static List<String> getNameByHigherCost(String cost) throws SQLException {
		String sqlString = """
				SELECT passenger_name
				FROM ticket
				WHERE cost >= %s
				""".formatted(cost);
		List<String> result = new ArrayList<>();
		try (var connection = ConnectionManager.open(); var statement = connection.createStatement()) {
			var executeQuery = statement.executeQuery(sqlString);
			while (executeQuery.next()) {
				result.add(executeQuery.getString("passenger_name"));
			}
		}
		return result;
	}
}
