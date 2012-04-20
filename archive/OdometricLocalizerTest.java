import com.ridgesoft.robotics.Localizer;
import com.ridgesoft.robotics.Motor; 
import com.ridgesoft.robotics.ContinuousRotationServo; 
import com.ridgesoft.robotics.Pose;
import com.ridgesoft.robotics.ShaftEncoder; 
import com.ridgesoft.robotics.AnalogShaftEncoder; 
import com.ridgesoft.robotics.DirectionListener;
import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;

public class OdometricLocalizerTest {
	private static Motor leftMotor; 
	private static Motor rightMotor;
	
	static ShaftEncoder leftEncoder = new AnalogShaftEncoder( 
		IntelliBrain.getAnalogInput(4), 250, 750, 30, 
		Thread.MAX_PRIORITY); 
	static ShaftEncoder rightEncoder = new AnalogShaftEncoder( 
		IntelliBrain.getAnalogInput(5), 250, 750, 30, 
		Thread.MAX_PRIORITY); 
	
	public static void main(String[] x) {
		Localizer localizer = new OdometricLocalizer( 
			leftEncoder, rightEncoder, 
			2.65f, 4.55f, 16, 
			Thread.MAX_PRIORITY - 1, 30);
			
		leftMotor = new ContinuousRotationServo( 
			IntelliBrain.getServo(1), false, 14,
			(DirectionListener)leftEncoder); 
		rightMotor = new ContinuousRotationServo( 
			IntelliBrain.getServo(2), true, 14, 
			(DirectionListener)rightEncoder); 
			
		forward(5000); 
		rotate(500); 
		forward(3000);
		
		Pose pose = localizer.getPose(); 
		Display display = IntelliBrain.getLcdDisplay();
		display.print(0, 
			"x: " + (int)pose.x + ", y: " + (int)pose.y); 
		display.print(1,  
			"heading: " + (int)Math.toDegrees(pose.heading)); 
	}
	
	public static void forward(int time) { 
		leftMotor.setPower(Motor.MAX_FORWARD); 
		rightMotor.setPower(Motor.MAX_FORWARD); 
		try { 
			Thread.sleep(time); 
		} 
		catch (InterruptedException e) {} 
		leftMotor.stop(); 
		rightMotor.stop(); 
	} 
	
	public static void rotate(int time) { 
		leftMotor.setPower(-8); 
		rightMotor.setPower(8); 
		try { 
			Thread.sleep(time); 
		} 
		catch (InterruptedException e) {} 
		leftMotor.stop(); 
		rightMotor.stop(); 
	} 
}