import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.intellibrain.IntelliBrainShaftEncoder;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.Motor;
//import com.ridgesoft.robotics.Servo;
import com.ridgesoft.io.Display;


/*
 * This class controls the engine and wheel power.
 */

public class Engine implements Runnable{
	
	// Port Reservations
	public static int ServoOutputPort =  3;
	public static int AnologueInput1  = 11;
	public static int AnologueInput2  = 10;
	
	
	//private Servo motor;
	private Motor motor;
	private Display display;
	private IntelliBrainShaftEncoder encoder;
	
	private double rpm = 0.0f;
	private double velocity = 0;
	private double power = 0;
	private boolean testing = false;
	
	/*
	public int STOP = 40;
	public int MAX_FORWARD = 127;
	public int MED_FORWARD = 55;
	public int MIN_FORWARD = 50;
	public int MAX_REVERSE = 0;
	public int MIN_REVERSE = 35;
	
	public int NEW_STOP = 0;
	public int NEW_MAX_FORWARD = 1000;
	public int NEW_MED_FORWARD = 660;
	public int NEW_MIN_FORWARD = 330;
	public int NEW_MAX_REVERSE = -1000;
	public int NEW_MED_REVERSE = -660;
	public int NEW_MIN_REVERSE = -330;
	//*/
	
	public Engine() { new Engine(false); }
	
	public Engine(boolean test){
		testing = test;
		if (testing) display = IntelliBrain.getLcdDisplay();
		
		motor = new ContinuousRotationServo(IntelliBrain.getServo(ServoOutputPort), false);
		
		encoder = IntelliBrain.getShaftEncoder(1);
		encoder.initialize(IntelliBrain.getDigitalIO(AnologueInput1), IntelliBrain.getDigitalIO(AnologueInput2));
		
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
	public void brake() { motor.brake(); }
}