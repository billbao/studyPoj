package org.rallydev.rest.bean;

import org.rallydev.rest.service.action.Action;

import com.google.gson.JsonObject;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.util.Ref;

public abstract class Artifact {
	protected Artifact(JsonObject jsObj) {
		ref = Ref.getRelativeRef(jsObj.get("_ref").getAsString());
		GetRequest getRequest = new GetRequest(ref);
		GetResponse getResponse = Action.read(getRequest);
		self = getResponse.getObject();

		uID = self.get("FormattedID").getAsString();
		name = self.get("Name").getAsString();
	}

	protected JsonObject self;

	public String uID;
	public String name;
	// public String desc;
	// public Date lastUpdate;
	// public String owner;
	// public String project;
	public String ref;
}
