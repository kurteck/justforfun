package reader_writer.v4;

public class MessageReader implements Runnable {
	
	private int id;
	private MessageBoard mb;
	private boolean keepAlive;
	
	public MessageReader(int id, MessageBoard mb) {
		this.id = id;
		this.mb = mb;
		this.keepAlive = true;
	}

	@Override
	public void run() {
		
		int numReads = 0;
		while (keepAlive) {
			String message = mb.readMessage();
			System.out.println(message);
			if ((message != null) && (numReads++ >= 1000)) {
				keepAlive = false;
			}
		}
	}
	
}