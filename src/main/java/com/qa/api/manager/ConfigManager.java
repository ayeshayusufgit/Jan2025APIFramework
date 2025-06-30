package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties prop = new Properties();

	// The moment the class is loaded the static block will be called
	static {
		InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties");
		// getResourceAsStream() this makes connection with the config.properties,
		// and returns the InputStream reference(IS comes from java.io package)
		// Once the connection is made it returns the InputStream
		// This is called the Reflection Property or API or Concept
		// If its used with the Reflection Api its faster than the FIS

		if (input != null) {
			try {
				prop.load(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//On calling ConfigManager.set(),the static block is loaded in the CMS(Common Memory Space)
	//So the prop reference need not be taken
	
	public static String get(String key) {
		return prop.getProperty(key).trim();//trim the extra spaces while reading from the prop file
	}

	// On runtime values can be set/updated
	public static void set(String key, String value) {
		prop.setProperty(key, value);
	}
}
