package callcenter.api;

import callcenter.server.model.Priority;

public class APIRequest implements Comparable<APIRequest> {
	
	private Priority priority;
	
	
	public APIRequest() {
		this.priority = Priority.NORMAL;
	}

	public APIRequest(Priority p) {
		this.priority = p;
	}
	
	public Priority getPriority() {
		return priority;
	}

	@Override
	public int compareTo(APIRequest other) {
		return other.priority.getIntValue() - this.priority.getIntValue(); 
	}
}