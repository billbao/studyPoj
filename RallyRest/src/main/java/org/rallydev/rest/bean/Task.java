package org.rallydev.rest.bean;

import com.google.gson.JsonObject;

public class Task extends Artifact {
	// public double estimate;
	// public double actuals;
	// public double todo;
	// public String project;
	// public String release;
	// public String iteration;
	public String state;

	public Task(JsonObject jsObj) {
		super(jsObj);
		state = self.get("State").getAsString();
	}

	public String toString() {
		return String.format("\t\t%s - %s - State:%s", uID, name, state);
	}
}
