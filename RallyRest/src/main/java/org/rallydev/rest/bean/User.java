package org.rallydev.rest.bean;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class User {
	private ResourceBundle bundle = PropertyResourceBundle.getBundle("user");

	private static User handler = new User();

	private User() {
	}

	public static User getInstance() {
		return handler;
	}

	public String getUser() {
		return bundle.getString("user.name");
	}

	public String getPassword() {
		return bundle.getString("user.password");
	}
}
