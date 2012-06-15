package org.rallydev.rest.query;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.rallydev.rest.bean.Story;
import org.rallydev.rest.bean.User;
import org.rallydev.rest.service.StoryService;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

public class QueryExample {
	public static void main(String[] args) throws URISyntaxException,
			IOException {
		// test1();
		test2();
	}

	private static void test2() {
		System.out.println("query uncompleted soties for current user...");
		List<Story> stories = new StoryService().getSlefUnCompeletedStories();
		for (Story story : stories) {
			System.out.println(story);
		}
	}

	public static void test1() throws URISyntaxException, IOException {
		User user = User.getInstance();
		// Create and configure a new instance of RallyRestApi
		RallyRestApi restApi = new RallyRestApi(new URI(
				"https://rally1.rallydev.com"), user.getUser(),
				user.getPassword());
		restApi.setApplicationName("QueryExample");

		try {

			System.out
					.println("Querying for unaccepted stories for current user...");

			QueryRequest defects = new QueryRequest("HierarchicalRequirement");

			defects.setFetch(new Fetch("FormattedID", "Name", "ScheduleState"));
			QueryFilter qf1 = new QueryFilter("Owner.Name", "=", user.getUser());
			QueryFilter qf2 = new QueryFilter("ScheduleState", "<", "Accepted");
			defects.setQueryFilter(qf1.and(qf2));
			defects.setOrder("FormattedID DESC");

			// Return up to 20, 1 per page
			defects.setPageSize(1);
			defects.setLimit(20);

			QueryResponse queryResponse = restApi.query(defects);
			if (queryResponse.wasSuccessful()) {
				System.out.println(String.format("\nTotal results: %d",
						queryResponse.getTotalResultCount()));
				System.out.println("unaccept stories:");
				for (JsonElement result : queryResponse.getResults()) {
					JsonObject defect = result.getAsJsonObject();
					System.out.println(String.format(
							"\t%s - %s - ScheduleState:%s",
							defect.get("FormattedID").getAsString(), defect
									.get("Name").getAsString(),
							defect.get("ScheduleState").getAsString()));
				}
			} else {
				System.err.println("The following errors occurred: ");
				for (String err : queryResponse.getErrors()) {
					System.err.println("\t" + err);
				}
			}

		} finally {
			// Release resources
			restApi.close();
		}
	}
}
