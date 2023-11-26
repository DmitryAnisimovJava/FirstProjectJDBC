package com.anisimov.jdbc.starter.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

	private static final Properties PROPERTIES = new Properties();

	static {
		loadProperties();
	}

	private PropertiesUtil() {
	}

	// OR use
	// PropertiesUtil.class.getCLassLoader().getResourceAsStream("application.properties")
	// for absolute path to .classpath root folder
	private static void loadProperties() {
		try (InputStream inputStream = PropertiesUtil.class.getResourceAsStream("/application.properties")) {
			PROPERTIES.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
