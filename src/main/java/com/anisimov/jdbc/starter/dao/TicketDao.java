package com.anisimov.jdbc.starter.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
			SET passenger_no = ?,
				passenger_name = ?,
				seat_no = ?,
				flight_id = ?,
				cost = ?
			WHERE id = ?
			""";

	private static String FIND_ALL = """
			SELECT id,
				passenger_no,
				passenger_name,
				seat_no,
				flight_id,
				cost
			FROM ticket
			""";

	private static String FIND_BY_ID = FIND_ALL + """
			WHERE id = ?
			""";

	private TicketDao() {
	}

	public static TicketDao getInstance() {
		return INSTANCE;
	}

	public List<TicketEntity> findAllColumns() {
		try (var connection = ConnectionPoolManager.get();
				var prepareStatement = connection.prepareStatement(FIND_ALL)) {
			var executeQuery = prepareStatement.executeQuery();
			List<TicketEntity> listOfRows = new ArrayList<>();
			while (executeQuery.next()) {
				listOfRows.add(buildTicket(executeQuery));
			}
			return listOfRows;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	private TicketEntity buildTicket(ResultSet executeQuery) throws SQLException {
		return new TicketEntity(executeQuery.getInt("id"), executeQuery.getString("passenger_no"),
				executeQuery.getString("passenger_name"), executeQuery.getString("seat_no"),
				executeQuery.getInt("flight_id"), executeQuery.getInt("cost"));
	}

	public boolean delete(Integer id) {
		try (var connectionPool = ConnectionPoolManager.get();
				var prepareStatement = connectionPool.prepareStatement(SQL_DELETE)) {
			prepareStatement.setInt(1, id);
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public TicketEntity create(TicketEntity ticket) {
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

	public boolean update(TicketEntity ticket, Integer id) {
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

	public Optional<TicketEntity> findById(Integer id) {
		try (var connection = ConnectionPoolManager.get();
				var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
			prepareStatement.setInt(1, id);
			var executeQuery = prepareStatement.executeQuery();
			TicketEntity ticket = null;
			if (executeQuery.next()) {
				ticket = buildTicket(executeQuery);
			}

			return Optional.ofNullable(ticket);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}
