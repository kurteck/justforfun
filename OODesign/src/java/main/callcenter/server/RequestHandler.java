package callcenter.server;

import callcenter.api.APIRequest;
import callcenter.api.CallRequest;
import callcenter.api.LoginRequest;
import callcenter.server.model.Agent;
import callcenter.server.model.Call;

public class RequestHandler extends Thread {
	
	private CallServer server;
	private boolean keepAlive = true;

	/**
	 * 
	 */
	public RequestHandler(String name, CallServer server) {
		super(name);
		this.server = server;
	}
	
	
	/**
	 * 
	 */
	public void run() {
		
		System.out.println("Starting Request Handler[" + getName() + "]");

		while (keepAlive) {
			
			APIRequest request = null;
			if (server.requestQ.isEmpty()) {
				synchronized (server.requestQ) {
					try {
						server.requestQ.wait();
					}
					catch (InterruptedException e) {
						System.out.println("Thread[" + getName() + "] interrupted: " + e);
						keepAlive = false;
					}
				}
			}

			synchronized (server.requestQ) {
				request = server.requestQ.poll();
				if (request != null) {
					server.numProcessedRequests++;
				}
				server.requestQ.notifyAll();
			}

			handleRequest(request);
		}

		System.out.println("Stopping Request Handler[" + getName() + "]");
	}


	/**
	 * 
	 */
	protected void handleRequest(APIRequest request) {
		
		if (request == null) {
			return;
		}
		else if (request instanceof CallRequest) {
			handleCallRequest((CallRequest)request);
		}
		else if (request instanceof LoginRequest) {
			handleLoginRequest((LoginRequest)request);
		}
		else {
			System.out.println("Unknown Request " + request);
		}
	}
	
	
	protected void handleCallRequest(CallRequest request) {

		Call call = request.getCall();
		System.out.println("Handler " + getName() + " working on " + call);
		try {
			sleep(50);
		}
		catch (InterruptedException ie) {
			System.out.println(ie);
		}
	}
	
	
	public void handleLoginRequest(LoginRequest request) {
		
		Agent agent = request.getAgent();
		System.out.println("Handler " + getName() + " logging in " + agent);
		try {
			sleep(50);
		}
		catch (InterruptedException ie) {
			System.out.println(ie);
		}
	}

	
	public void shutdown() {
		keepAlive = false;
	}
}