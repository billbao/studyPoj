package org.rallydev.rest.bean;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Story extends Artifact {
	private String[] fetchItems = { "ScheduleState", "Parent", "Children",
			"Tasks" };

	public String[] getFetchItems() {
		return fetchItems;
	}

	public Story(JsonObject jsObj) {
		super(jsObj);
		status = self.get("ScheduleState").getAsString();
		JsonElement parentEle = self.get("Parent");
		if (parentEle.isJsonNull()) {
			parent = parentEle.getAsJsonObject().get("FormattedID")
					.getAsString();
		}
		JsonArray taskArr = self.getAsJsonArray("Tasks");
		int size = taskArr.size();
		if (size > 0) {
			tasks = new Task[size];
			for (int i = 0; i < size; i++) {
				tasks[i] = new Task(taskArr.get(i).getAsJsonObject());
			}
		}
	}

	public String parent;
	public String status;
	public Task[] tasks;

	// public String iteration;
	//
	// public String planEst;
	//
	// public String release;

	public String toString() {
		String story = String.format("\t%s - %s - ScheduleState:%s", uID, name,
				status);
		if (tasks != null) {
			for (Task t : tasks) {
				story += "\r\n" + t;
			}
		}
		return story;
	}
}
