package com.anisimov.jdbc.starter.util;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolManager {

	private static final String USERNAME_KEY = "db.username";
	private static final String PASSWORD_KEY = "db.password";
	private static final String URL_KEY = "db.url";
	private static final String POOL_SIZE_KEY = "db.max.pool";
	private static final Integer DEFAULT_POOL_SIZE = 10;
	private static BlockingQueue<Connection> connectionPool;
	private static List<Connection> openedConnections;

	static {
		loadDriver();
		initPool();
	}

	private ConnectionPoolManager() {
	}

	private static void initPool() {
		String poolSizeFromKey = PropertiesUtil.get(POOL_SIZE_KEY);
		int poolSize = poolSizeFromKey == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSizeFromKey);
		connectionPool = new ArrayBlockingQueue<>(poolSize);
		openedConnections = new ArrayList<>();
		for (int i = 0; i < poolSize; i++) {
			var connection = open();
			var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionPoolManager.class.getClassLoader(),
					new Class[] { Connection.class },
					(proxy, method, args) -> method.getName().equals("close") ? connectionPool.add((Connection) proxy)
							: method.invoke(connection, args));
			connectionPool.add(proxyConnection);
			openedConnections.add(connection);
		}
	}

	private static void loadDriver() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection get() {
		try {
			return connectionPool.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static Connection open() {
		try {
			return DriverManager.getConnection(PropertiesUtil.get(URL_KEY), PropertiesUtil.get(USERNAME_KEY),
					PropertiesUtil.get(PASSWORD_KEY));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void closePool() {
		for (Connection connection : openedConnections) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
