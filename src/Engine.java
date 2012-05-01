import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.intellibrain.IntelliBrainShaftEncoder;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.Motor;
import com.ridgesoft.robotics.PIDController;

public class Engine implements Runnable, Debuggable {
	
	// Port Reservations
	private static final int ServoOutputPort =  3;
	private static final int AnologueInput1  = 11;
	private static final int AnologueInput2  = 10;
	
	// PID Controller gain constants
	public float pGain = 0.060f; // 0.053
	public float iGain = 0.001f; // 0.001
	public float dGain = 0.005f;  // 0.01
	public float iCapp = 16.0f;  // 40.0
	
	// Stored devices
	private boolean running = true;
	private Motor motor;
	private IntelliBrainShaftEncoder encoder;
	private PIDController controller = new PIDController(pGain, iGain, dGain, iCapp, false);
	
	// Algorithum variables
	private int rpm = 0, velocity = 0, power = 0, desired = 0;
	
	// Constructor - get all required variables and execute a quick startup on motor controler
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
	
	// Main running thread - calculate rpm and use PID controler to set power based on set velocity
	public void run(){
		int previousCounts = 0, counts = 0;
		long time = System.currentTimeMillis();
		
		while (true) {

			if (running) {
				
				//600 count intervals are taken per minute.
				//128 counts per revolution.
				counts = encoder.getCounts();
				rpm = ((counts - previousCounts) * 600) / 128;
				previousCounts = counts;
				
				desired = velocity * 30;
				power = (int)controller.control(desired, rpm);
				
				motor.setPower(power);
			    
			} else {
				motor.setPower(0);
			}
			
			// Pause thread execution
			try {
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable t) { t.printStackTrace(); }
		}
		
	}
	
	// Method to pause the execution of this thread
	public void setRunning(boolean run) { 
		running = run;
	}
	
	// Method to define speed of the motor
	public void setSpeed( int v ) { 
		velocity = v;
		if (v == 0) motor.brake();
	}
	
	// Debug Methods (private accessorss)
	public int getSpeed() { return velocity; }
	//public int getRPM() { return rpm; }
	public void updatePID() { controller.setConstants(pGain, iGain, dGain, iCapp); }
	
	public String[] toDebugString(String in[]) {
		in[0] = "Engine " + desired;
		in[1] = "Pow:" + power + " RPM:" + rpm;
		return in;
	}
}
