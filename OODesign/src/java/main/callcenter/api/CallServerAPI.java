package callcenter.api;
import callcenter.server.CallServer;
import callcenter.server.model.Agent;
import callcenter.server.model.Call;

public class CallServerAPI {
	
	/**
	 * 
	 */
	public void handleCall(Call call) {

		CallRequest request = new CallRequest(call);
		CallServer.getInstance().handleRequest(request);
	}
	
	/**
	 * 
	 */
	public void login(Agent agent) {

		LoginRequest request = new LoginRequest(agent);
		CallServer.getInstance().handleRequest(request);
	}
	


	private static final class CallServerAPISingleton {
		public static final CallServerAPI INSTANCE = new CallServerAPI();
	}
	
	public static final CallServerAPI getInstance() { 
		return CallServerAPISingleton.INSTANCE;
	}
}