public class CarFactoryTest {

	private static int MAX_THREADS = 5;
	
	public static void main(String[] args) {
		
		for (int iters = 0; iters < 10; iters++) {
			
			Thread[] threads = new Thread[MAX_THREADS];
			
			for (int i=0; i < MAX_THREADS; i++) {
				threads[i] = new Thread(new CarFactoryRunnable(i));
			}
			
			for (int i=0; i < MAX_THREADS; i++) {
				threads[i].start();
			}
	
//			CarFactory.destroy();

		}
		
//		CarFactory f1 = CarFactory.getInstance();
//		CarFactory f2 = CarFactory.getInstance();
		
	}
}