package parkinglot;

import org.joda.time.DateTime;

public class Meter {
	
	double amount;
	private DateTime startTime;
	private DateTime expireTime;
	
	public void addTime(double amount) {
		;
	}
	
	public boolean isExpired() {
		return true;
	}
	
	public void startTimer() {
		startTime = new DateTime();
		expireTime = startTime.plusMinutes(60);
	}
	
	public DateTime getExpireTime() {
		return expireTime;
	}
	
}