package callcenter;
import org.joda.time.DateTime;

import callcenter.api.CallServerAPI;
import callcenter.server.CallServer;
import callcenter.server.model.Agent;
import callcenter.server.model.Call;
import callcenter.server.model.Priority;

public class CallProducer {

	public static final int MAX_CALLS = 1000;
	
	public static void main(String[] args) {
		
		CallServerAPI api = CallServerAPI.getInstance();
		
		for (int i=0; i < MAX_CALLS; i++) {
			
			DateTime startTime = new DateTime();
			api.handleCall(new Call(i, Priority.NORMAL, startTime));
			
			if (i % 10 == 0) {
				api.login(new Agent("Agent " + i));
			}
		}
	
		CallServer.getInstance().shutdown();
	
	}
	
}