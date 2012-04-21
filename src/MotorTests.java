import com.ridgesoft.intellibrain.*;
import com.ridgesoft.robotics.*;
import com.ridgesoft.io.Display;

public class MotorTests implements Runnable {
	public static int ServoOutputPort =  3;
	public static int AnologueInput1  = 11;
	public static int AnologueInput2  = 10;
	
	public Display display;
	public AnalogInput thumbwheel;
	public Motor motor;
	public IntelliBrainShaftEncoder encoder;

	public MotorTests() {
		display = IntelliBrain.getLcdDisplay();
		thumbwheel = IntelliBrain.getThumbWheel();
		motor = new ContinuousRotationServo(IntelliBrain.getServo(ServoOutputPort), false);
		
		// Get a shaft encoder object and initialize it with the two digital inputs it uses.
		encoder = IntelliBrain.getShaftEncoder(1);
		encoder.initialize(IntelliBrain.getDigitalIO(AnologueInput1),
				IntelliBrain.getDigitalIO(AnologueInput2));
		
		motor.setPower(1);
		motor.setPower(0);
		motor.setPower(-1);
		
		try { Thread.sleep(2000); } catch (Throwable t) { t.printStackTrace(); }
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