package com.anisimov.jdbc.starter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.anisimov.jdbc.starter.util.ConnectionManager;

public class TransactionRunner {

	public static void main(String[] args) throws SQLException {
		deleteFlightsByFlightId(9);
	}

	private static void deleteFlightsByFlightId(int id) throws SQLException {
		String deleteFromTicket = """
				DELETE FROM ticket
				WHERE flight_id = ?;
				""";
		String deleteFromFlight = """
				DELETE FROM flight
				WHERE id = ?;
				""";
		Connection connection = null;
		PreparedStatement deleteTicketStatement = null;
		PreparedStatement deleteFlightsStatement = null;
		try {
			connection = ConnectionManager.open();
			connection.setAutoCommit(false);
			deleteTicketStatement = connection.prepareStatement(deleteFromTicket);
			deleteFlightsStatement = connection.prepareStatement(deleteFromFlight);

			deleteTicketStatement.setInt(1, id);
			deleteFlightsStatement.setInt(1, id);

			var executeTicketStatement = deleteTicketStatement.executeUpdate();
			var executeFlightsStatement = deleteFlightsStatement.executeUpdate();
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
			if (deleteTicketStatement != null) {
				deleteTicketStatement.close();
			}
			if (deleteFlightsStatement != null) {
				deleteFlightsStatement.close();
			}
		}
	}

}
