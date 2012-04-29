import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.intellibrain.IntelliBrainShaftEncoder;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.Motor;
import com.ridgesoft.robotics.PIDController;

public class Engine implements Runnable, Debuggable {
	private boolean running;
	
	// PID Controller gain constants
	public float pGain = 0.060f; // 0.053
	public float iGain = 0.001f; // 0.001
	public float dGain = 0.005f;  // 0.01
	public float iCapp = 16.0f;  // 40.0
	
	// Port Reservations
	private static int ServoOutputPort =  3;
	private static int AnologueInput1  = 11;
	private static int AnologueInput2  = 10;
	
	// Stored devices
	private Motor motor;
	private IntelliBrainShaftEncoder encoder;
	private PIDController controller = new PIDController(pGain, iGain, dGain, iCapp, false);
	
	// Algorithum variables
	private int rpm = 0, velocity = 0, power = 0, desired = 0;
	//private int[] arrayOfSpeeds = {-120, -90, -60, 0, 30, 60, 90};
	
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
	
	public void updatePID() {
		controller.setConstants(pGain, iGain, dGain, iCapp);
	}
	
	public void run(){
		running = true;
		int previousCounts = 0, counts = 0;//, counter = 0;
		long time = System.currentTimeMillis();
		
		while (true) {
			try {

				if (!running) {
					time += 2000;
					Thread.sleep(time - System.currentTimeMillis());
				} else {
					
					//600 count intervals are taken per minute.
					//128 counts per revolution.
					counts = encoder.getCounts();
					rpm = ((counts - previousCounts) * 600) / 128;
					previousCounts = counts;
					
					desired = velocity * 40;
					power = (int)controller.control(desired, rpm);
					
					motor.setPower(power);
				    
				    // Pause thread execution
					time += 100;
					Thread.sleep(time - System.currentTimeMillis());
				}
				
			} catch (Throwable t) { t.printStackTrace(); }
		}
		
	}
	
	public void setSpeed( int v ) { 
		this.velocity = v;
		if (v == 0) {
			motor.brake();
		}
	}
	//public int getRPM() { return rpm; }
	//public void brake() { motor.brake(); this.velocity = 0; } // something else should probably happen here
	
	public String[] toDebugString(String in[]) {
		in[0] = "Engine " + desired;
		in[1] = "Pow:" + power + " RPM:" + rpm;
		return in;
	}
}
