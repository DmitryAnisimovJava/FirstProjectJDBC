package com.anisimov.jdbc.starter.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.anisimov.jdbc.starter.entity.TicketEntity;
import com.anisimov.jdbc.starter.exceptions.DaoException;
import com.anisimov.jdbc.starter.util.ConnectionPoolManager;

public class TicketDao {

	private static final TicketDao INSTANCE = new TicketDao();
	private static String SQL_DELETE = """
			DELETE FROM ticket
			WHERE id = ?
			""";
	private static String SQL_CREATE = """
			INSERT INTO ticket (passenger_no, passenger_name, seat_no, flight_id, cost)
			VALUES (?, ?, ?, ?, ?)
			""";

	private static String SQL_UPDATE = """
			UPDATE ticket
			SET passenger_no = ?
				passenger_name = ?
				seat_no = ?
				flight_id = ?
				cost = ?
			WHERE id = ?
			""";

	private static String FIND_BY_ID = """
			SELECT id,
				passenger_no,
				passenger_name,
				seat_no,
				flight_id,
				cost
			FROM ticket
			WHERE id = ?
			""";

	private TicketDao() {
	}

	public static TicketDao getInstance() {
		return INSTANCE;
	}

	public static boolean delete(Integer id) {
		try (var connectionPool = ConnectionPoolManager.get();
				var prepareStatement = connectionPool.prepareStatement(SQL_DELETE)) {
			prepareStatement.setInt(1, id);
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public static TicketEntity create(TicketEntity ticket) {
		try (var connection = ConnectionPoolManager.get();
				var prepareStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
			prepareStatement.setString(1, ticket.getPassengerNo());
			prepareStatement.setString(2, ticket.getPassengerName());
			prepareStatement.setString(3, ticket.getSeatNo());
			prepareStatement.setInt(4, ticket.getFlightId());
			prepareStatement.setInt(5, ticket.getCost());
			prepareStatement.executeUpdate();
			var generatedKeys = prepareStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				ticket.setId(generatedKeys.getInt("id"));
			}
			return ticket;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public static boolean update(TicketEntity ticket, Integer id) {
		try (var connection = ConnectionPoolManager.get();
				var prepareStatement = connection.prepareStatement(SQL_UPDATE)) {
			prepareStatement.setString(1, ticket.getPassengerNo());
			prepareStatement.setString(2, ticket.getPassengerName());
			prepareStatement.setString(3, ticket.getSeatNo());
			prepareStatement.setInt(4, ticket.getFlightId());
			prepareStatement.setInt(5, ticket.getCost());
			prepareStatement.setInt(6, id);
			var executeUpdate = prepareStatement.executeUpdate();
			return executeUpdate != 1;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	private static Optional<TicketEntity> findById(Integer id) {
		try (var connection = ConnectionPoolManager.get();
				var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
			prepareStatement.setInt(1, id);
			var executeQuery = prepareStatement.executeQuery();
			if (executeQuery.next()) {
				executeQuery.
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}
