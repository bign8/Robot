public class Intelligence implements Runnable{
	
	private Engine motor;
	private SteeringWheel steer;
	private Sonar eyes;
	
	public Intelligence(Engine e, SteeringWheel w, Sonar s) {
		motor = e;
		steer = w;
		eyes = s;
		int x = 4;
	}

	public void run() {
		
		int left = 0, right = 0, cen = 0;
		
		try {
			while (true) {
				
				//motor.display.print(0, "C Dist: " + Integer.toString(eyes.getDist('c')) );
				
				left = eyes.getDist('l');
				right = eyes.getDist('r');
				cen = eyes.getDist('c');
				
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
					/*
					if (left > cen && right > cen) { // center
						// randomly choose
					} else */
					
					if (left > cen) { // left
						steer.setDirection(steer.FULL_LEFT);
					} else { // right
						steer.setDirection(steer.FULL_RIGHT);
					}
				} else {
					motor.setPower(motor.STOP);
					steer.setDirection(steer.CENTERED);
				}
				
				
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}