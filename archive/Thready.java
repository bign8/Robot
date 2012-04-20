import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Speaker;

public class Thready{

	public Driver walter;
	public Speaker buzzer;

	public void go() {

		Data robo = new Data();
		walter = new Driver(robo);
		
		buzzer = IntelliBrain.getBuzzer();

		Thread thr1 = new Thread(lineFollower);
		Thread thr2 = new Thread(buzzerSound);
		thr1.setPriority(Thread.MIN_PRIORITY);
		thr1.start();
		thr2.setPriority(Thread.MAX_PRIORITY);
		thr2.start();
	}
	
	//////// Runnable Object 1 /////////////
	Runnable lineFollower = new Runnable() {
		public void run() {
			walter.go();
		}
	};
	
	//////// Runnable Object 2 //////////////
	Runnable buzzerSound = new Runnable() {
		public void run() {
			while(true){
				try {
					buzzer.play(500, 1);
					//buzzer.play(600, 1000);
					//buzzer.play(700, 1000);
				//	System.out.println("buzzered");
				} catch (Throwable t) {}
			}
		}
	};
}