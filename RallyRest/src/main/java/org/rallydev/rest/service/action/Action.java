package org.rallydev.rest.service.action;

import java.io.IOException;

import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.DeleteRequest;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.request.Request;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.DeleteResponse;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.response.UpdateResponse;

public class Action {
	public static QueryResponse query(QueryRequest request) {
		return (QueryResponse) new Query().preform(request);
	}

	public static CreateResponse create(CreateRequest request) {
		return (CreateResponse) new Create().preform(request);
	}

	public static GetResponse read(GetRequest request) {
		return (GetResponse) new Read().preform(request);
	}

	public static UpdateResponse update(UpdateRequest request) {
		return (UpdateResponse) new Update().preform(request);
	}

	public static DeleteResponse delete(DeleteRequest request) {
		return (DeleteResponse) new Delete().preform(request);
	}

	static class Query extends BaseAction {
		@Override
		protected QueryResponse doAction(RallyRestApi api, Request request)
				throws IOException {
			QueryRequest req = (QueryRequest) request;
			return api.query(req);
		}
	}

	static class Create extends BaseAction {
		@Override
		protected CreateResponse doAction(RallyRestApi api, Request request)
				throws IOException {
			CreateRequest req = (CreateRequest) request;
			return api.create(req);
		}
	}

	static class Read extends BaseAction {
		@Override
		protected GetResponse doAction(RallyRestApi api, Request request)
				throws IOException {
			GetRequest req = (GetRequest) request;
			return api.get(req);
		}
	}

	static class Update extends BaseAction {
		@Override
		protected UpdateResponse doAction(RallyRestApi api, Request request)
				throws IOException {
			UpdateRequest req = (UpdateRequest) request;
			return api.update(req);
		}
	}

	static class Delete extends BaseAction {
		@Override
		protected DeleteResponse doAction(RallyRestApi api, Request request)
				throws IOException {
			DeleteRequest req = (DeleteRequest) request;
			return api.delete(req);
		}
	}
}
