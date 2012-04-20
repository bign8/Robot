/*
 * AnalogShaftEncoder.java
 * 
 * Copyright 2009 by RidgeSoft, LLC., PO Box 482, Pleasanton, CA  94566, U.S.A.
 * www.ridgesoft.com
 *
 * RidgeSoft grants you the right to use, modify, make derivative works and
 * redistribute this source file provided you do not remove this copyright notice.
 */

import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.ShaftEncoder;
import com.ridgesoft.robotics.DirectionListener;

public class AnalogShaftEncoder extends Thread implements ShaftEncoder,
		DirectionListener {
	private AnalogInput input;
	private int lowThreshold;
	private int highThreshold;
	private boolean isForward;
	private int counts;
	private int period;

	public AnalogShaftEncoder(AnalogInput input, int lowThreshold,
			int highThreshold, int period, int threadPriority) {
		this.input = input;
		this.lowThreshold = lowThreshold;
		this.highThreshold = highThreshold;
		this.period = period;
		isForward = true;
		counts = 0;
		setPriority(threadPriority);
		setDaemon(true);
		start();
	}

	public int getRate() {
		return 0; // rate calculation not supported
	}

	public int getCounts() {
		return counts;
	}

	public void updateDirection(boolean isForward) {
		this.isForward = isForward;
	}

	public void run() {
		try {
			boolean wasHigh = false;
			if (input.sample() > lowThreshold)
				wasHigh = true;
			while (true) {
				int value = input.sample();
				if (wasHigh) {
					if (value < lowThreshold) {
						if (isForward)
							counts++;
						else
							counts--;
						wasHigh = false;
					}
				} else {
					if (value > highThreshold) {
						if (isForward)
							counts++;
						else
							counts--;
						wasHigh = true;
					}
				}
				Thread.sleep(period);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}