/*
 * Copyright 2013 (c) S¿ren Palmund
 * 
 * Licensed under the License described in LICENSE (the "License"); you may not
 * use this file except in compliance with the License.
 */

package net.palmund.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

class PropertyManager {
	private static PropertyManager instance = null;

	/**
	 * Attempts to merge the supplied properties with the properties read from the file with the supplied filename.
	 *  
	 * @param propertyFileName the name of the file where the properties are stored
	 * @param defaults the default properties that must be set
	 * @return an merged instance of {@link Properties}
	 */
	public static PropertyManager getInstance(String propertyFileName, Map<String, String> defaults) {
		if (instance == null) {
			instance = new PropertyManager(propertyFileName, defaults);
		}
		if (!instance.propertyFileName.equals(propertyFileName)) {
			instance = new PropertyManager(propertyFileName, defaults);
		}
		return instance;
	}
	
	private final Properties properties;
	private final String propertyFileName;
	
	private PropertyManager(String propertyFileName, Map<String, String> defaults) {
		this.propertyFileName = propertyFileName;
		properties = new Properties();
		InputStream in = getClass().getResourceAsStream(propertyFileName);
		if (in == null) {
			properties.putAll(defaults);
		} else {
			try {
				properties.load(in);
				in.close();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
		merge(properties, defaults);
	}

	private void merge(Properties properties, Map<String, String> defaults) {
		Set<String> keys = defaults.keySet();
		for (String key : keys) {
			if (!properties.containsKey(key)) {
				properties.put(key, defaults.get(key));
			}
		}
	}
	
	public Properties getProperties() {
		return properties;
	}
}