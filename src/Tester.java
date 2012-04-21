import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.AnalogInput;


public class Tester implements Runnable {
	public Engine eng;
	
	public Tester(Engine e) {
		eng = e;
	}

	public void run() {
AnalogInput thumbwheel = IntelliBrain.getThumbWheel();
		
		
		
		int power = 0;
		
		long time = System.currentTimeMillis();
		while (true) {
			try {
				
				power = (thumbwheel.sample() - 512) / 31;
			    eng.setSpeed(power);
			    IntelliBrain.getLcdDisplay().print(0, "Velocity: " + power);
				
			
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
}
