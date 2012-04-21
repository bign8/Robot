import com.ridgesoft.intellibrain.*;
import com.ridgesoft.robotics.*;
import com.ridgesoft.io.Display;

public class MotorTests implements Runnable {
	public static int ServoOutputPort =  3;
	public static int AnologueInput1  = 10;
	public static int AnologueInput2  = 11;
	
	public Display display;
	public AnalogInput thumbwheel;
	public Motor motor;
	public IntelliBrainShaftEncoder encoder;

	public MotorTests() {
		display = IntelliBrain.getLcdDisplay();
		thumbwheel = IntelliBrain.getThumbWheel();
		motor = new ContinuousRotationServo(IntelliBrain.getServo(ServoOutputPort), true);
		
		// Get a shaft encoder object and initialize it with the two digital inputs it uses.
		encoder = IntelliBrain.getShaftEncoder(1);
		encoder.initialize(IntelliBrain.getDigitalIO(AnologueInput1),
				IntelliBrain.getDigitalIO(AnologueInput2));
		
		try {
			ease(16, 0);
			ease(-16, 16);
			ease(0, -16);
		} catch (Throwable t) { t.printStackTrace(); }
	}
	
	
	public void ease(int to, int from) {
		int add = (to > from) ? 1 : -1;
		try {
			for (int i = from; i != to; i += add) {
				motor.setPower(i);
				display.print(0, "power:" + Integer.toString(i));
				Thread.sleep(50);
			}
		} catch (Throwable t) { t.printStackTrace(); }
	}
	
	
	public void run() {
		int previousCounts = 0, counts = 0, power = 0;
		long time = System.currentTimeMillis();
		
		
		while (true) {
			try {
				
				// Calculate RPM (Revolutions Per Minute)
				// RPM = countsThisIteration * iterationsPerMinute /
				// countsPerRevolution
				// countsThisIteration - current encoder counts minus previous
				// count
				// iterationsPerMinute = 120 - two loops per second
				// countsPerRevolution = 128 - fixed by design of
				// sensor/codewheel
				counts = encoder.getCounts();
				display.print(1, "RPM: " + ((counts - previousCounts) * 120) / 128);
				previousCounts = counts;

				// Read the thumbwheel and scale the value to set the motor power.
				power = (thumbwheel.sample() - 512) / 31;
			    motor.setPower(power);
				display.print(0, "Power: " + power);

				// Calculate sleep time so the next iteration starts 1/2 second after the the previous one.
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable t) { t.printStackTrace(); }
		}
	}
}