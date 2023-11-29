package com.anisimov.jdbc.starter;

import java.sql.Connection;
import java.sql.Statement;

import com.anisimov.jdbc.starter.util.ConnectionManager;

public class BatchTransactionTesting {

	public static void main(String[] args) throws Exception {
		deleteFlightByFlightId(8);
	}

	private static void deleteFlightByFlightId(int id) throws Exception {
		String deleteTicketSql = """
				DELETE FROM ticket WHERE flight_id =
				""" + id;
		String deleteFlightsSql = """
				DELETE FROM flight WHERE id =
				""" + id;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = ConnectionManager.open();
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.addBatch(deleteTicketSql);
			statement.addBatch(deleteFlightsSql);
			var executeBatch = statement.executeBatch();
			for (int i = 0; i < executeBatch.length; i++) {
				System.out.print(executeBatch[i] + " ");
			}
			connection.commit();
		} catch (Exception e) {
			if (connection != null) {
				connection.rollback();
			}
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
	}
}
