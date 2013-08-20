package callcenter.server.model;

import org.joda.time.DateTime;

public class Call {
	
	int id;
	DateTime startTime;
	Priority priority;

	public Call(int id, Priority priority, DateTime startTime) {
		this.id = id;
		this.priority = priority;
		this.startTime = startTime;
	}
	
	public String toString() {
		return "Call #" + id;
	}
	
}