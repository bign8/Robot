/*
 * IntelliBrainWW01
 *
 * Copyright 2004, 2005 by RidgeSoft, LLC., PO Box 482, Pleasanton, CA  94566, U.S.A.
 * www.ridgesoft.com
 *
 * RidgeSoft grants you the right to use, modify, make derivative works and
 * redistribute this source file provided you do not remove this copyright notice.
 */

import com.ridgesoft.intellibrain.*;
import com.ridgesoft.robotics.*;
import com.ridgesoft.io.Display;

/**
 * This class provides an example of using the Nubotics WheelWatcher WW-01 sensor
 * with the IntelliBrain robotics controller.  The example allows the user to set the
 * servo power using the IntelliBrain's thumbwheel input.  The current power level and
 * the wheel speed, in RPM, are displayed on the LCD display.
 *
 * This example requires:
 *			- IntelliBrain controller main board
 *			- 1 WheelWatcher WW-01 sensor (attached to digital I/O ports 9 (yellow) and 10 (blue))
 *			- 1 Servo motor modified for continuous rotation (attached to servo port 2)
 *			- 1 injection molded wheel
 */
public class Speedometer {
    public static void run(String args[]) {
		try {
			System.out.println("WheelWatcher WW-01");

			// Get the user interface objects
			Display display = IntelliBrain.getLcdDisplay();
			AnalogInput thumbwheel = IntelliBrain.getThumbWheel();

			// Get the servo object and give it a Motor interface by encapsulating it
			// in a ContinuousRotationServo object.
			Motor motor = new ContinuousRotationServo(IntelliBrain.getServo(3), true);

			motor.setPower(1);
			motor.setPower(0);
			motor.setPower(-1);
			Thread.sleep(2000);
			
			// Get a shaft encoder object and initialize it with the two digital inputs it uses.
			IntelliBrainShaftEncoder encoder = IntelliBrain.getShaftEncoder(1);
			encoder.initialize(IntelliBrain.getDigitalIO(11), IntelliBrain.getDigitalIO(10));
			
			int previousCounts = 0;
			int power = 0;
			int rpm = 0;
			long time = System.currentTimeMillis();
			while (true) {
				// Calculate RPM (Revolutions Per Minute)
				// RPM = countsThisIteration * iterationsPerMinute / countsPerRevolution
				// countsThisIteration - current encoder counts minus previous count
				// iterationsPerMinute = 600 - ten loops per second
				// countsPerRevolution = 128 - fixed by design of sensor/codewheel
				int counts = encoder.getCounts();
				display.print(1, "RPM: " + ((counts - previousCounts) * 600) / 128);
				previousCounts = counts;

				// Read the thumbwheel and scale the value to set the motor power.
				int velocity = (thumbwheel.sample() - 512) / 31;
				while (velocity == 3 || velocity == 4) {
					if (velocity == 3)
						velocity = (thumbwheel.sample() - 512) / 31;
					else 
						velocity = -(thumbwheel.sample() - 512) / 31;
					
					//counts and rpm logic
					counts = encoder.getCounts();
					previousCounts = counts;
					rpm = ((counts - previousCounts) * 600) / 128;
					display.print(0, "RPM: " + rpm);
					
					//governer logic
					power += (velocity - (rpm/16.25));

					//limiter
					if (power > 16) 
						power = 16;
					if (power < -16)
						power = -16;
					display.print(0, "Power: " + power);

					//set power
			    		motor.setPower(power);
			    		
			    		time += 100;
					Thread.sleep(time - System.currentTimeMillis());
					
				}
				motor.setPower(velocity);
				display.print(0, "Power: " + velocity);

				// Calculate sleep time so the next iteration starts 1/2 second after the
				// the previous one.
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			}
		}
		catch (Throwable t) {
			// Display a stack trace if an exception occurs.
			t.printStackTrace();
		}
    }
}