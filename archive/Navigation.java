/*
 * IntelliBrainBotNavigateSquare - makes the IntelliBrain-Bot educational robot navigate
 * in a square pattern using wheel encoders, odometery and navigation software.
 *
 * Copyright 2005 by RidgeSoft, LLC., PO Box 482, Pleasanton, CA  94566, U.S.A.
 * www.ridgesoft.com
 *
 * RidgeSoft grants you the right to use, modify, make derivative works and
 * redistribute this source file provided you do not remove this copyright notice.
 */

import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.PushButton;
import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.ShaftEncoder;
import com.ridgesoft.robotics.DirectionListener;
import com.ridgesoft.robotics.AnalogShaftEncoder;
import com.ridgesoft.robotics.Localizer;
import com.ridgesoft.robotics.OdometricLocalizer;
import com.ridgesoft.robotics.Motor;
import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.Navigator;
import com.ridgesoft.robotics.DifferentialDriveNavigator;
import com.ridgesoft.intellibrain.IntelliBrain;

/**
 * This class is an example program that demonstrates how to program the
 * IntelliBrain-Bot educational robot to navigate a square pattern using wheel
 * encoder sensors to keep track of its position.
 */
public class Navigation {
	private static final float SIDE_LENGTH = 16.0f;

	public static void go() {
		try {
			Display display = IntelliBrain.getLcdDisplay();

			// Get intput objects for wheel sensors
			AnalogInput leftWheelInput = IntelliBrain.getAnalogInput(4);
			AnalogInput rightWheelInput = IntelliBrain.getAnalogInput(5);

			// Create wheel (shaft) encoders from your robot's infrared wheel
			// sensors.
			ShaftEncoder leftEncoder = new AnalogShaftEncoder(leftWheelInput,
					250, 750, 30, Thread.MAX_PRIORITY);
			ShaftEncoder rightEncoder = new AnalogShaftEncoder(rightWheelInput,
					250, 750, 30, Thread.MAX_PRIORITY);

			// Create a localizer to track your robot's position using the
			// wheel encoders.
			Localizer localizer = new OdometricLocalizer(leftEncoder,
					rightEncoder, 2.65f, 4.55f, 16, Thread.MAX_PRIORITY - 1, 30);

			// Create motor objects for servo motors
			Motor leftMotor = new ContinuousRotationServo(
					IntelliBrain.getServo(1), false, 14,
					(DirectionListener) leftEncoder);
			Motor rightMotor = new ContinuousRotationServo(
					IntelliBrain.getServo(2), true, 14,
					(DirectionListener) rightEncoder);

			// Create a navigator class to navigate your robot from place to
			// place.
			Navigator navigator = new DifferentialDriveNavigator(leftMotor,
					rightMotor, localizer, 8, 6, 25.0f, 0.5f, 0.08f,
					Thread.MAX_PRIORITY - 2, 50);

			PushButton startButton = IntelliBrain.getStartButton();
			startButton.waitReleased();

			// Display the sensor readings while waiting for start to be
			// pressed.
			while (!startButton.isPressed()) {
				display.print(0, "L wheel:  " + leftWheelInput);
				display.print(1, "R wheel:  " + rightWheelInput);
			}

			display.print(0, "Navigating a");
			display.print(1, "square");

			// Drive to the four corners of the square.
			navigator.moveTo(SIDE_LENGTH, 0.0f);
			navigator.moveTo(SIDE_LENGTH, -SIDE_LENGTH);
			navigator.moveTo(0.0f, -SIDE_LENGTH);
			navigator.moveTo(0.0f, 0.0f);
			navigator.turnTo(0.0f);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}