package callcenter.server;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import callcenter.api.APIRequest;
import callcenter.server.model.Agent;


/**
 * Responsible for coordinating all actions related to handling a call, including:

 * 1) Receiving incoming Call from phone system
 * 2) Dispatching Call to the next available Agent
 * 3) Logging in and Logging off Agents
 */
public class CallServer {
	
	public static int DEFAULT_REQ_HANDLERS = 10;
	public static int MAX_NUM_REQ_HANDLERS = 10;
	public static int MAX_REQUEST_IN_QUEUE = 100;
	
	List<RequestHandler> requestHandlers;
	PriorityQueue<APIRequest> requestQ;
	private boolean shutdown = false;
	int numProcessedRequests = 0;

	List<Agent> agents;

	

	/**
	 * Gets a free handler from the pool and tells him to start working.
	 */
	public void handleRequest(APIRequest request) {

		// Stop accepting new requests if server has been issued a shutdown command
		if (shutdown) {
			return;
		}
		
		// Wait while request queue is full	in order to prevent DOS attack via overloading server memory.
		if (requestQ.size() >= MAX_REQUEST_IN_QUEUE) {
			try {
				synchronized (requestQ) {
					requestQ.wait();
				}
			}
			catch (InterruptedException ie) {
				System.out.println("Call Server Interrupted" + ie);
			}
		}

		// Add Request to Queue
		synchronized (requestQ) {
			requestQ.add(request);
			requestQ.notifyAll();
		}
	}

	
	/**
	 * Start worker threads
	 */
	public void startRequestHandlers() {

		requestHandlers = new ArrayList<RequestHandler>(DEFAULT_REQ_HANDLERS);
		for (int i=0; i < DEFAULT_REQ_HANDLERS; i++) {
			RequestHandler handler = new RequestHandler("RH" + i, this);
			requestHandlers.add(handler);
			handler.start();
		}
	}

	/**
	 * Stop worker threads
	 */
	public void shutdown() {
		
		// Let running threads finish their work
		while (requestQ.size() > 0) {
			synchronized (requestQ) {
				try {
					requestQ.wait();
				}
				catch (InterruptedException ie) {
					System.out.println("Call Server Interrupted" + ie);
				}
			}
		}

		for (RequestHandler handler : requestHandlers) {
			handler.shutdown();
		}

		synchronized (requestQ) {
			requestQ.notifyAll();
		}
		
		System.out.println("Processed " + numProcessedRequests + " requests.");

	}
	
	
	// Creators
	protected CallServer() {
		requestQ = new PriorityQueue<APIRequest>();
		startRequestHandlers();
	}

	private static final class CallServerSingleton {
		public static final CallServer INSTANCE = new CallServer();
	}
	
	public static final CallServer getInstance() { 
		return CallServerSingleton.INSTANCE;
	}
}