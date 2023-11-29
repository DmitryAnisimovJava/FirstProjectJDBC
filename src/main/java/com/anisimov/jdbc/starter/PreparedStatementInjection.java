package com.anisimov.jdbc.starter;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.anisimov.jdbc.starter.util.ConnectionManager;

public class PreparedStatementInjection {

	public static void main(String[] args) throws SQLException {
		PreparedStatementInjection.getFlightIdBetweenDate(LocalDate.of(2020, 10, 1).atStartOfDay(),
				LocalDateTime.now());
	}

	private static List<Integer> getFlightIdBetweenDate(LocalDateTime starTime, LocalDateTime endTime)
			throws SQLException {
		String sql = """
				SELECT id
				FROM flight
				WHERE departure_date >= ? AND departure_date <= ?;
				""";
		List<Integer> result = new ArrayList<>();
		try (var connection = ConnectionManager.open(); var prepareStatement = connection.prepareStatement(sql)) {
			prepareStatement.setFetchSize(50);
			prepareStatement.setQueryTimeout(10);
			prepareStatement.setMaxRows(100);
			prepareStatement.setTimestamp(1, Timestamp.valueOf(starTime));
			System.out.println(prepareStatement);
			prepareStatement.setTimestamp(2, Timestamp.valueOf(endTime));
			System.out.println(prepareStatement);
			var execute = prepareStatement.executeQuery();
			while (execute.next()) {
				result.add(execute.getInt("id"));
			}
			System.out.println(result);
		}
		return result;
	}

	private static List<String> getNameByHigherCost(int cost) throws SQLException {
		String sqlString = """
				SELECT passenger_name
				FROM ticket
				WHERE cost >= ?
				""";
		List<String> result = new ArrayList<>();
		try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(sqlString)) {
			statement.setInt(1, cost);
			var executeQuery = statement.executeQuery();
			while (executeQuery.next()) {
				result.add(executeQuery.getString("passenger_name"));
			}
		}
		return result;
	}
}
