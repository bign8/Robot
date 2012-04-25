import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.intellibrain.IntelliBrainShaftEncoder;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.Motor;

public class Engine implements Runnable, Debuggable {
	private boolean running;
	
	// Port Reservations
	private static int ServoOutputPort =  3;
	private static int AnologueInput1  = 11;
	private static int AnologueInput2  = 10;
	
	// Stored devices
	private Motor motor;
	private IntelliBrainShaftEncoder encoder;
	
	// Public variables
	private int rpm = 0;
	private int velocity = 0;
	private int power = 0;
	
	public Engine(){
		
		// get what I need (or want)
		motor = new ContinuousRotationServo(IntelliBrain.getServo(ServoOutputPort), false);
		encoder = IntelliBrain.getShaftEncoder(1);
		encoder.initialize(IntelliBrain.getDigitalIO(AnologueInput1),
				IntelliBrain.getDigitalIO(AnologueInput2));
		
		// Initialization routine for the axial ae-l esc with reverse
		// Thanks : http://axialracing.com/wordpress/2011/08/18/axial-ae-2-esc-set-up-and-programming/
		motor.setPower(1);
		motor.setPower(0);
		motor.setPower(-1);
		try { Thread.sleep(2000); } catch (Throwable t) { t.printStackTrace(); }
	}
	
	public void setRunning(boolean run) {
		running = run;
		setSpeed(0);
	}
	
	public void run(){
		running = true;
		int previousCounts = 0, counts = 0;
		long time = System.currentTimeMillis();
		
		while (true) {
			try {

				if (!running) {
					time += 2000;
					Thread.sleep(time - System.currentTimeMillis());
					continue;
				}
				
				//600 count intervals are taken per minute.
				//128 counts per revolution.
				counts = encoder.getCounts();

				rpm = ((counts - previousCounts) * 600) / 128;
				previousCounts = counts;
				power = velocity;

				
				//Self adjusting power, covers a quantized 10rpm per 1 power map.
				//Currently functions under the assumption that rpm goes from -160 to 160.
				power += power - (rpm/10);
			    	motor.setPower(power);


				//Self adjusting power, covers a quantized 10rpm per 1 power map.
				//Currently functions under the assumption that rpm goes from -260 to 260.
				power += power - (rpm/16.25);

				
				//limiter
				if (power > 16) 
				power = 16;
				if (power < -16)
				power = -16;
				
				// FOREST MAGIC GOES HERE
				
				//set power
			    motor.setPower(velocity);
			    
			    // Pause thread execution
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
				
			} catch (Throwable t) { t.printStackTrace(); }
		}
		
	}
	
	public void setSpeed(int velocity) { this.velocity = velocity; motor.setPower(velocity);}
	public void appendSpeed(int velocity) { this.velocity += velocity; }
	public int getRPM() { return rpm; }
	public void brake() { motor.brake(); this.velocity = 0; } // something else should probably happen here
	
	public String[] toDebugString(String in[]) {
		in[0] = "Engine Debug";
		in[1] = "Pow:" + Integer.toString(power) + " RPM:" + Integer.toString(rpm);
		return in;
	}
}
