public class CarFactoryRunnable implements Runnable {

	private int id;
	
	public CarFactoryRunnable(int id) {
		this.id = id;
	}
	
	public void run() {
		System.out.println("CarFactory[" + id + "] got " + CarFactory.getInstance());
	}

}