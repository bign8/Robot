//import com.ridgesoft.intellibrain.IntelliBrain;
//import com.ridgesoft.robotics.AnalogInput;


public class Tester implements Runnable {

	public void run() {
		// nope
	}
	
	/* ENGINE TESTER
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
	//*/
	
	/* STEERING TESTER
	public SteeringWheel whe;
	
	public Tester(SteeringWheel w) {
		whe = w;
	}
	
	public void run() {
		AnalogInput thumbwheel = IntelliBrain.getThumbWheel();
		
		
		
		int dir = 0;
		
		long time = System.currentTimeMillis();
		while (true) {
			try {
				
				dir = thumbwheel.sample() / 10;
			    whe.setDirection(dir);
			    IntelliBrain.getLcdDisplay().print(0, "Turning: " + dir);
				
			
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
	//*/
}
