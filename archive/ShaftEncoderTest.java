/*
 * ShaftEncoderTest.java
 * 
 * Copyright 2009 by RidgeSoft, LLC., PO Box 482, Pleasanton, CA  94566, U.S.A.
 * www.ridgesoft.com
 *
 * RidgeSoft grants you the right to use, modify, make derivative works and
 * redistribute this source file provided you do not remove this copyright notice.
 */
/*
import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.Motor;
import com.ridgesoft.robotics.ShaftEncoder;

public class ShaftEncoderTest extends Thread {
	private static final float PI = 3.14159f; 
	private static final float TWO_PI = PI * 2.0f;

	private Motor leftMotor, rightMotor;
	private int leftCounts = 0, rightCounts = 0;
	ShaftEncoder leftEncoder, rightEncoder;
	private Pose pose;
	
	private float distancePerCount; 
	private float radiansPerCount;

	public void run() {
		pose = new Pose();
	
		setPriority(Thread.MAX_PRIORITY-1);

		AnalogInput leftWheelSensor = IntelliBrain.getAnalogInput(4);
		AnalogInput rightWheelSensor = IntelliBrain.getAnalogInput(5);

		leftEncoder = new AnalogShaftEncoder(leftWheelSensor, 250, 750, 30, Thread.MAX_PRIORITY);
		rightEncoder = new AnalogShaftEncoder(rightWheelSensor, 250, 750, 30, Thread.MAX_PRIORITY);
		
		//This should be in another class, but it is basic motor control
		leftMotor = new ContinuousRotationServo( IntelliBrain.getServo(1), false);
		rightMotor = new ContinuousRotationServo( IntelliBrain.getServo(2), true);
		
		int oldLeftCount = 0, oldRightCount = 0, counter = 0;
		
		
		float countsPerRevolution = 16;
		float diameterWheel = 8; // measure in centimeters
		float distancePerCount = Pi * diameterWheel / countsPerRevolution;
		try {
			while (true) {
				leftCounts = leftEncoder.getCounts();
				rightCounts = rightEncoder.getCounts();
				
				// math to add movement to dead heading
				// THIS NEEDS TO BE IMPLEMENTED NOW ___________________________________________________________
				pose.addToPose(0f, 0f, 0f);
				
				oldLeftCount = leftCounts;
				oldRightCount = rightCounts;
				
				Thread.sleep(100);
				if (counter++ % 20 == 0) {
					System.out.println("Debugging? " + counter);
				}
			}
		} catch (Exception e) {
			System.out.println("Error in localizer");
		}
	}
	
	public Pose getPose() { // synchronised
		return pose;
	}
	
	public void setMotorsPower(int leftPower, int rightPower) {
		this.leftPower = leftPower;
		this.rightPower = rightPower;
		leftMotor.setPower(leftPower);
		rightMotor.setPower(rightPower);
		leftEncoder.updateDirection(leftPower > 0);
		rightEncoder.updateDirection(rightPower > 0);
	}
}

//*/