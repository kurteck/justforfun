package callcenter.api;

import callcenter.server.model.Agent;
import callcenter.server.model.Priority;

public class LoginRequest extends APIRequest {
	
	private Agent agent;
	
	public LoginRequest(Agent agent) {
		super(Priority.HIGH);
		this.agent = agent;
	}
	
	
	public Agent getAgent() {
		return agent;
	}
}