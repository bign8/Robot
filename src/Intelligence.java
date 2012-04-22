public class Intelligence implements Runnable{
	
	private Engine motor;
	private SteeringWheel steer;
	private Sonar eyes;
	
	private static double[][] weights = {
		//   W     L     C     R     E     B
		{  -50, -100,   .2, -100,  -50,    0},  // motor
		{  500,  500, -500, -500, -500,   50},  // front steer
		{    0,  500, -500, -500,    0,   50}   // back steer
	};
	
	public Intelligence(Engine e, SteeringWheel w, Sonar s) {
		motor = e;
		steer = w;
		eyes = s;
	}

	public void run() {
		
		// TODO : Another thing to do is remember the last 200 commands or so
		// This would help  if the robot is stuck with no-motion for more than
		// a set number of iterations - the robot could then reverse the past 
		// operations - see white board
		
		double sum0 = 0, sum1 = 0, sum2 = 0;
		double[] toAdd = new double[6];
		toAdd[5] = 1.0;
		long time = System.currentTimeMillis();
		
		try {
			while (true) {
				
				sum0 = 0; sum1 = 0; sum2 = 0;
				
				toAdd[0] = eyes.getDist('w');
				toAdd[1] = eyes.getDist('l');
				toAdd[2] = eyes.getDist('c');
				toAdd[3] = eyes.getDist('r');
				toAdd[4] = eyes.getDist('e');
				
				// NATE: WHY YOU SO DUMB
				
				for (int i = 0; i < 6; i++) {
					if ( toAdd[i] > 0 ) { // exclude poor input
						
						if ( i == 2 )
							sum0 += toAdd[i] * weights[0][i];
						else
							sum0 += 1./toAdd[i] * weights[0][i];
						
						sum1 += 1./toAdd[i] * weights[1][i];
						sum2 += 1./toAdd[i] * weights[2][i];
					}
				}
				
				//System.out.println("W:" + Double.toString(toAdd[0]) + " L:" + Double.toString(toAdd[1]) + " C:" + Double.toString(toAdd[2]) + " R:" + Double.toString(toAdd[3]) + " E:" + Double.toString(toAdd[4]));
				//System.out.println("Normal: | " + sum0 + " | " + sum1 + " | " + sum2 + " |");
				//System.out.println("Capped: | " + capper(sum0, 16., -16.) + " | " + capper(sum1, 100., 0.) + " | " + capper(sum2, 100, 0) + " |\n");
				
				// set down the smarts
				motor.setSpeed( (int) capper(sum0, 6., -6.) );
				steer.setFrontWheels( (int) capper(sum1, 100., 0.) );
				steer.setBackWheels( (int) capper(sum2, 100, 0) );
				
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
				//Thread.sleep(100);
			}
		} catch (Throwable e) { e.printStackTrace(); }
	}
	
	public static double capper(double value, double max, double min) {
		return (value > max) ? max : (value < min) ? min : value ;
	}
}