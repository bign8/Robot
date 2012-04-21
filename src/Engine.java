import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.intellibrain.IntelliBrainShaftEncoder;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.Motor;
import com.ridgesoft.io.Display;

public class Engine implements Runnable{
	
	// Port Reservations
	private static int ServoOutputPort =  3;
	private static int AnologueInput1  = 11;
	private static int AnologueInput2  = 10;
	
	// Stored devices
	private Motor motor;
	private Display display;
	private IntelliBrainShaftEncoder encoder;
	
	// Public variables
	private double rpm = 0.0f;
	private double velocity = 0;
	private double power = 0;
	private boolean testing = false;
	
	public Engine(boolean test){
		
		// debug stuff
		testing = test;
		if (testing) display = IntelliBrain.getLcdDisplay();
		
		// get what I need (or want)
		motor = new ContinuousRotationServo(IntelliBrain.getServo(ServoOutputPort), false);
		encoder = IntelliBrain.getShaftEncoder(1);
		encoder.initialize(IntelliBrain.getDigitalIO(AnologueInput1),
				IntelliBrain.getDigitalIO(AnologueInput2));
		
		// Initialization routine for the axial ae-l esc with reverse
		motor.setPower(1);
		motor.setPower(0);
		motor.setPower(-1);
		try { Thread.sleep(2000); } catch (Throwable t) { t.printStackTrace(); }
	}
	
	public void run(){
		int previousCounts = 0, counts = 0;
		long time = System.currentTimeMillis();
		
		while (true) {
			try {
				encoder.getCounts();
				counts = encoder.getCounts();
				rpm = ((counts - previousCounts) * 120) / 128;
				previousCounts = counts;
				
				//Governer Logic
				power =  power - ((rpm - velocity) / 31);
			    motor.setPower((int)power);
				
				if (testing) display.print(1, "RPM:" + Double.toString(rpm) + " Set:" + Double.toString(velocity));
				
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable t) { t.printStackTrace(); }
		}
		
	}
	
	public void setSpeed(double velocity) { this.velocity = velocity; }
	public void appendSpeed(double velocity) { this.velocity += velocity; }
	public double getRPM() { return rpm; }
	public void brake() { motor.brake(); } // something else should probably happen here
}