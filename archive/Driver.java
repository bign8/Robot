//Note that these imports require the RoboJDE.jar file
//to exist in the build path.
import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.PushButton;
import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.Motor;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.SonarRangeFinder;
import com.ridgesoft.robotics.sensors.ParallaxPing;

/*
 The Driver class came mostly from an example line follower robot.  We modified it
 to call variables from a separate data class to modularize our robot.
 
 The basic design is a state machine that determines the location of the robot
 relative to the line: UNKNOWN, CENTER, ONE RIGHT, ONE LEFT, TWO RIGHT, TWO LEFT
 
 This class also incorporates range finding logic so that the robot dose not get closer
 than 6 inches to an object in front of it.
 */


public class Driver {
	Data robo; // Variables live here
	protected static SonarRangeFinder sonarSensor; // Instantiate a range finder

	public Driver(Data robo) { // Main constructor, passes data vars
		this.robo = robo;
	}

	public void go() { // Main executing thread
		try {	//All robot control may throw an exception

			// Gain control of hardware
			sonarSensor = new ParallaxPing(IntelliBrain.getDigitalIO(3));

			AnalogInput leftLineSensor = IntelliBrain.getAnalogInput(6);
			AnalogInput rightLineSensor = IntelliBrain.getAnalogInput(7);

			Motor leftMotor = new ContinuousRotationServo(
					IntelliBrain.getServo(1), false, 14);
			Motor rightMotor = new ContinuousRotationServo(
					IntelliBrain.getServo(2), true, 14);

			PushButton startButton = IntelliBrain.getStartButton();
			startButton.waitReleased();

			AnalogInput dial = IntelliBrain.getThumbWheel();

			String stateString = "Starting";
			int state = robo.UNK;
			boolean go = false;
			String dispString = "";
			byte counter = 0;
			while (true) {
				if (counter % 5 == 0)
					sonarSensor.ping();

				int leftSample = leftLineSensor.sample();
				int rightSample = rightLineSensor.sample();
				robo.THRESHOLD = dial.sample();

				int index = 0;
				
				if (leftSample > robo.THRESHOLD)	// Change the sign for white / black
					index |= 0x2;
				if (rightSample > robo.THRESHOLD)	// Change the sign for white / black
					index |= 0x1;

				// Update the current state
				state = robo.NEXT_STATE[state][index];

				if (go) {
					leftMotor.setPower(robo.POWER[state][robo.LEFT]);
					rightMotor.setPower(robo.POWER[state][robo.RIGHT]);
				} else if (startButton.isPressed()) {
					go = true;
				}

				stateString = robo.getState(state);

				robo.display.print(0, "L"+Integer.toString(leftSample)+" R" + Integer.toString(rightSample)	+ ' ' + stateString);

				// Logic for Ultrasonic range finder
				if (counter % 5 == 0) {
					float range = sonarSensor.getDistanceInches();

					if (range > 0.0f/* && range < 20.0f */) {
						int low = (int) (range - 6.0f);		//Follow at 6 inches
						int normal = low * 2;

						// no backward
						normal = (normal >= 0) ? normal : 0;
						low = (low >= 0) ? low : 0;

						// fixed domain - not jutty navigation
						if (normal >= 8) {
							normal = 8;
							low = 4;
						}
						dispString = " Hi: " + Integer.toString(normal)
								+ " Lo: " + Integer.toString(low);

						robo.setSpeed((byte) normal, (byte) low);
					}
					if (range > 0.0f)
						robo.display.print(1,
								Integer.toString((int) (range + 0.5f)) + '"'
										+ dispString);
					else
						robo.display.print(1, "--\"" + dispString);
				}
				counter++;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
