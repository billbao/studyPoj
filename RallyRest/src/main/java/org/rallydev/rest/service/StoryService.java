package org.rallydev.rest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.rallydev.rest.bean.RallyConstants;
import org.rallydev.rest.bean.RallyConstants.QueryOper;
import org.rallydev.rest.bean.RallyConstants.ScheduleState;
import org.rallydev.rest.bean.Story;
import org.rallydev.rest.bean.User;
import org.rallydev.rest.service.action.Action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

public class StoryService {
	public List<Story> getSlefUnCompeletedStories() {
		return getUnCompeletedStories(Arrays.asList(User.getInstance()
				.getUser()));
	}

	protected List<Story> getUnCompeletedStories(List<String> users) {
		return getStories(users, QueryOper.LessThan.toString(),
				ScheduleState.Completed.toString());
	}

	protected List<Story> getStories(List<String> users, String statusOp,
			String status) {
		QueryRequest qReq = new QueryRequest(RallyConstants.DEF_STORY);
		qReq.setFetch(new Fetch("FormattedID", "Children"));
		QueryFilter qf_staus = new QueryFilter(RallyConstants.DEF_STORY_STATE, statusOp,
				status);

		List<QueryFilter> qfs = new ArrayList<QueryFilter>();
		for (String user : users) {
			QueryFilter qf = new QueryFilter("Owner.Name",
					QueryOper.Equals.toString(), user);
			qfs.add(qf);
		}
		QueryFilter qf_users = QueryFilter.or(qfs.toArray(new QueryFilter[qfs
				.size()]));
		qReq.setQueryFilter(QueryFilter.and(qf_staus, qf_users));

		qReq.setOrder("FormattedID DESC");

		// Return up to 20, 1 per page
		qReq.setPageSize(1);
		qReq.setLimit(20);

		List<Story> results = new ArrayList<Story>();
		QueryResponse queryResponse = Action.query(qReq);
		JsonArray queryResults = queryResponse.getResults();
		System.out.println("Total count: " + queryResults.size());
		for (JsonElement result : queryResults) {
			JsonObject storyObj = result.getAsJsonObject();
			JsonArray childrenObj = storyObj.getAsJsonArray("Children");
			if (childrenObj.size() > 0) {
				System.out.println(storyObj.get("FormattedID").getAsString()
						+ " is a parent story, not care");
				continue;
			}

			results.add(new Story(storyObj));
		}

		return results;
	}
}
