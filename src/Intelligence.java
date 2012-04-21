public class Intelligence implements Runnable{
	
	private Engine motor;
	private SteeringWheel steer;
	private Sonar eyes;
	
	//								SL	L	C	R	SR  bias
	private double[][] weights = { {-1, -2, -5, -2, -1, 9},  // motor
								   { 1,  1,  5, -1, -1, 0},  // front steer
								   { 0,  1, -5, -1,  0, 0} };// back steer
	
	public Intelligence(Engine e, SteeringWheel w, Sonar s) {
		motor = e;
		steer = w;
		eyes = s;
	}

	public void run() {
		
		double sum0 = 0, sum1 = 0, sum2 = 0;
		double[] toAdd = new double[6];
		toAdd[5] = 1;
		
		try {
			while (true) {
				
				//motor.display.print(0, "C Dist: " + Integer.toString(eyes.getDist('c')) );
				sum0 = 0; sum1 = 0; sum2 = 0;
				
				toAdd[0] = eyes.getDist('w');
				toAdd[1] = eyes.getDist('l');
				toAdd[2] = eyes.getDist('c');
				toAdd[3] = eyes.getDist('r');
				toAdd[4] = eyes.getDist('e');
				
				// NATE: TODO
				// WHY YOU SO DUMB
				// This logic is the 'inverse' *hint hint* of what you want!
				
				for (int i = 0; i > 6; i++) {
					sum0 += toAdd[i] * weights[0][i];
					sum1 += toAdd[i] * weights[1][i];
					sum2 += toAdd[i] * weights[2][i];
				}
				
				//motor.setPower((int)sum0);
				steer.setFrontWheels((int)sum1);
				steer.setBackWheels((int)sum2);
				
				/*
				if ( (cen < 10 && cen != -1) || (right < 10 && right != -1) || (left < 10 && left != -1) ) {
					motor.setPower(motor.STOP);
					steer.setDirection(steer.CENTERED);
				} else if (cen > 50 || cen == -1) { // smooth sailing
					motor.setPower(motor.MED_FORWARD);
					
					if (left < 20 && left != -1) {// headding correction
						steer.setDirection(steer.HALF_RIGHT);
					} else if (right < 20 && right != -1) {
						steer.setDirection(steer.HALF_LEFT);
					} else {
						steer.setDirection(steer.CENTERED);
					}
					
					
				} else if (cen > 20 && ( left > cen || right > cen ) ) { // possible better route
					motor.setPower(motor.MIN_FORWARD);
					//if (left > cen && right > cen) { // center
						// randomly choose
					//} else 
					
					if (left > cen) { // left
						steer.setDirection(steer.FULL_LEFT);
					} else { // right
						steer.setDirection(steer.FULL_RIGHT);
					}
				} else {
					motor.setPower(motor.STOP);
					steer.setDirection(steer.CENTERED);
				}
				//*/
				
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}