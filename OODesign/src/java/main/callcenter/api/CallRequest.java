package callcenter.api;

import callcenter.server.model.Call;

public class CallRequest extends APIRequest {
	
	private Call call;
	
	public CallRequest(Call call) {
		super();
		this.call = call;
	}
	
	
	public Call getCall() {
		return call;
	}
}