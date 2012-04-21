import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.AnalogInput;


public class Tester {
	public Tester(Engine eng) {
		AnalogInput thumbwheel = IntelliBrain.getThumbWheel();
		
		double power = 0;
		
		long time = System.currentTimeMillis();
		while (true) {
			try {
				
				power = (thumbwheel.sample() - 512) / 31;
			    eng.setSpeed(power);
			    IntelliBrain.getLcdDisplay().print(0, "Power: " + power);
				
			
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
}
