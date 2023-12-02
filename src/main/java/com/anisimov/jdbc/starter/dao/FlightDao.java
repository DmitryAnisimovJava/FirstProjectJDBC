package com.anisimov.jdbc.starter.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.anisimov.jdbc.starter.entity.FlightEntity;
import com.anisimov.jdbc.starter.exceptions.DaoException;
import com.anisimov.jdbc.starter.util.ConnectionPoolManager;

public class FlightDao implements Dao<Integer, FlightEntity> {

	private static final FlightDao INSTANCE = new FlightDao();

	private static final String SQL_FIND_BY_ID = """
			SELECT *
			FROM flight
			WHERE id = ?;
			""";

	private FlightDao() {
	}

	public static FlightDao getInstance() {
		return INSTANCE;
	}

	@Override
	public List<FlightEntity> findAllColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FlightEntity create(FlightEntity ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(FlightEntity ticket, Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<FlightEntity> findById(Integer id) {
		try (var connection = ConnectionPoolManager.get()) {
			return findById(id, connection);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Optional<FlightEntity> findById(Integer id, Connection connection) {
		try (var prepareStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
			prepareStatement.setInt(1, id);
			var executeQuery = prepareStatement.executeQuery();
			FlightEntity flightEntity = null;
			if (executeQuery.next()) {
				flightEntity = new FlightEntity(executeQuery.getInt("id"), executeQuery.getString("flight_no"),
						executeQuery.getTimestamp("departure_date").toLocalDateTime(),
						executeQuery.getString("departure_airport_code"),
						executeQuery.getTimestamp("arrival_date").toLocalDateTime(),
						executeQuery.getString("arrival_airport_code"), executeQuery.getInt("aircraft_id"),
						executeQuery.getString("status"));

			}
			return Optional.ofNullable(flightEntity);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}
