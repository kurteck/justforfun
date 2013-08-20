package reader_writer.v4;

public class MessageWriter implements Runnable {
	
	private int id;
	private MessageBoard mb;
	private boolean keepAlive;
	
	public MessageWriter(int id, MessageBoard mb) {
		this.id = id;
		this.mb = mb;
		this.keepAlive = true;
	}
	
	
	@Override
	public void run() {
		
		int messageNumber = 1;
		while (keepAlive) {
			String message = "MW[" + id + "] says:  Write lock #" + messageNumber;
			mb.writeMessage(message);
			if (messageNumber++ > 10) {
				keepAlive = false;
			}
		}
	}
	
}