/*
 * IntelliBrainBotSonarFollower - demonstrates the IntelliBrain-Bot educational robot
 * using a Parallax Ping))) sonar range finder maintain a fixed distance from an
 * object in front of the robot. 
 *
 * Copyright 2005 by RidgeSoft, LLC., PO Box 482, Pleasanton, CA  94566, U.S.A.
 * www.ridgesoft.com
 *
 * RidgeSoft grants you the right to use, modify, make derivative works and
 * redistribute this source file provided you do not remove this copyright notice.
 */

import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.PushButton;
import com.ridgesoft.robotics.Motor;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.SonarRangeFinder;
import com.ridgesoft.robotics.sensors.ParallaxPing;

/**
 * This class is an example program that demonstrates how to program the
 * IntelliBrain-Bot to use sonar to maintain a set distance from an object in
 * front of the robot.
 */

public class Follower {

	public void go() {
		try {
			Display display = IntelliBrain.getLcdDisplay();
			display.print(0, "Sonar Follower");
			display.print(1, "");

			// Create motor objects for servo motors
			Motor leftMotor = new ContinuousRotationServo(
					IntelliBrain.getServo(1), false, 14);
			Motor rightMotor = new ContinuousRotationServo(
					IntelliBrain.getServo(2), true, 14);

			// Create Ping))) sensor object
			SonarRangeFinder sonarSensor = new ParallaxPing(
					IntelliBrain.getDigitalIO(3));

			PushButton startButton = IntelliBrain.getStartButton();
			startButton.waitReleased();

			boolean go = false;
			while (true) {
				sonarSensor.ping();
				Thread.sleep(500);

				float range = sonarSensor.getDistanceInches();

				if (go) {
					if (range > 0.0f && range < 20.0f) {
						int power = ((int) (range - 6.0f)) * 3;
						// leftMotor.setPower(power);
						// rightMotor.setPower(power);
						// man.go();
					} else {
						// leftMotor.stop();
						// rightMotor.stop();
					}
				} else if (startButton.isPressed()) {
					go = true;
				}

				if (range > 0.0f)
					display.print(1,
							Integer.toString((int) (range + 0.5f)) + '"');
				else
					display.print(1, "--");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}