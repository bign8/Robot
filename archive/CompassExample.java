/*
 * IntelliBrainCMPS03I2C
 *
 * Copyright 2004 by RidgeSoft, LLC., PO Box 482, Pleasanton, CA  94566, U.S.A.
 * www.ridgesoft.com
 *
 * RidgeSoft grants you the right to use, modify, make derivative works and
 * redistribute this source file provided you do not remove this copyright
 * notice.
 */

import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.Compass;
import com.ridgesoft.robotics.PushButton;
import com.ridgesoft.robotics.sensors.DevantechCMPS03I2C;

/**
 * This class provides an example of the Devantech CMPS03 magnetic compass
 * attached to the IntelliBrain via the I2C bus.
 * 
 * This class can also be used to calibrate the compass. Pressing the START
 * button enters calibration mode. The compass is calibrated by laying it flat
 * and pointing it to each the direction indicated on the display, pressing the
 * START button, then repeating for the next direction until all 4 directions
 * have been calibrated.
 */
public class CompassExample {
	public static void go() {
		try {
			DevantechCMPS03I2C compass = new DevantechCMPS03I2C(
					IntelliBrain.getI2CMaster());
			System.out.println("CMPS03, SW Version:  "
					+ compass.getSoftwareRevision());
			System.out.println("Press START to calibrate");

			Display display = IntelliBrain.getLcdDisplay();
			display.print(0, "Devantech CMPS03");
			display.print(1, "");

			PushButton startButton = IntelliBrain.getStartButton();
			startButton.waitReleased();

			// Loop forever.
			while (true) {
				if (startButton.isPressed()) {
					startButton.waitReleased();
					display.print(1, "Calibrate North");
					startButton.waitPressed();
					startButton.waitReleased();
					display.print(1, "Calibrate East");
					startButton.waitPressed();
					startButton.waitReleased();
					display.print(1, "Calibrate South");
					startButton.waitPressed();
					startButton.waitReleased();
					display.print(1, "Calibrate West");
					startButton.waitPressed();
					startButton.waitReleased();
				}
				int heading = compass.heading();
				display.print(1, compass.direction(heading) + ' ' + heading);
				Thread.sleep(250);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}