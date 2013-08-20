public class CarFactory {


	private CarFactory() {
		// Singleton pattern
		System.out.println("CarFactory.  Constructor");
		double k = 1;
		for (int i=0; i < 100000000; i++) {
			k = k * i / new Double(3.1415265438979) / 4; 
		}
	}
	
	private static class SingletonHolder {
		public static final CarFactory INSTANCE = new CarFactory();
		
	}
	
	public static CarFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	
	public Integer getCar() {
		return null;
	}
}