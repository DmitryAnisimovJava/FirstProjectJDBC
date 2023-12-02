package com.anisimov.jdbc.starter.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

	List<E> findAllColumns();

	boolean delete(K id);

	E create(E ticket);

	boolean update(E ticket, K id);

	Optional<E> findById(K id);

}
