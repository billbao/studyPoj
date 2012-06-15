package org.rallydev.rest.service.action;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.rallydev.rest.bean.User;

import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.Request;
import com.rallydev.rest.response.Response;

public abstract class BaseAction {
	private RallyRestApi api;

	private void open() throws URISyntaxException {
		User user = User.getInstance();
		api = new RallyRestApi(new URI("https://rally1.rallydev.com"),
				user.getUser(), user.getPassword());
	}

	private void close() throws IOException {
		api.close();
	}

	public Response preform(Request request) {
		try {
			open();
			try {
				Response response = doAction(api, request);

				if (response.wasSuccessful()) {
					return response;
				} else {
					System.err.println("The following errors occurred: ");
					for (String err : response.getErrors()) {
						System.err.println("\t" + err);
					}
				}

				return null;
			} finally {
				close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract Response doAction(RallyRestApi api, Request request)
			throws IOException;
}
