package com.anisimov.jdbc.starter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;

import com.anisimov.jdbc.starter.util.ConnectionManager;

public class BlobClassTesting {

	public static void main(String[] args) throws SQLException, IOException {
		getImage();
	}

	private static void getImage() throws SQLException, IOException {
		String saveSql = """
				SELECT image
				FROM aircraft
				WHERE id = 1
				""";
		try (var connection = ConnectionManager.open(); var prepareStatement = connection.prepareStatement(saveSql)) {
			var executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				Files.write(Path.of("src", "main", "resources", "Boeing_777_new.jpg"), executeQuery.getBytes("image"),
						StandardOpenOption.CREATE);
			}
		}
	}

	private static void saveImage() throws SQLException, IOException {
		String saveSql = """
				UPDATE aircraft
				SET image = ?
				WHERE id = 1
				""";
		try (var connection = ConnectionManager.open(); var createStatement = connection.prepareStatement(saveSql)) {
			createStatement.setBytes(1, Files.readAllBytes(Path.of("src", "main", "resources", "Boeing_777.jpg")));
			createStatement.executeUpdate();
		}
	}

}
