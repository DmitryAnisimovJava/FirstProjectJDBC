package com.anisimov.jdbc.starter;

import java.sql.SQLException;

import com.anisimov.jdbc.starter.util.ConnectionManager;

public class LearningMetaData {

	public static void main(String[] args) throws SQLException {
		LearningMetaData.checkMetadate();
	}

	private static void checkMetadate() throws SQLException {
		try (var connection = ConnectionManager.open()) {
			var metaData = connection.getMetaData();
			var catalogs = metaData.getCatalogs();
			String catalog = null;
			while (catalogs.next()) {
				if (catalogs.getString(1).equals("flight_repository")) {
					catalog = catalogs.getString(1);
					break;
				}
			}
			var schemas = metaData.getSchemas(catalog, "%");
			String schema = null;
			while (schemas.next()) {
				if (schemas.getString("TABLE_SCHEM").equals("public")) {
					schema = schemas.getString("TABLE_SCHEM");
					break;
				}
			}
			var tables = metaData.getTables(catalog, schema, "%", new String[] { "TABLE" });
			while (tables.next()) {
				System.out.println(tables.getString("TABLE_NAME"));
			}
		}
	}

}
