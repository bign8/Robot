public class Intelligence implements Runnable, Debuggable {

	private boolean running = true, forcedRemote = false;

	private Engine motor;
	private SteeringWheel steer;
	private Sonar eyes;
	private Remote remote;

	private int fsum0 = 0, fsum1 = 0, fsum2 = 0;

	private static double[][] weights = {
		//   W     L     C     R     E     B
		{  -50, -100,   .2, -100,  -50,    0},  // motor
		{  500,  500, -500, -500, -500,   50},  // front steer
		{    0,  500, -500, -500,    0,   50}   // back steer
	};

	public Intelligence(Engine e, SteeringWheel w, Sonar s, Remote r) {
		motor = e;
		steer = w;
		eyes = s;
		remote = r;
	}

	public void setRunning( boolean run ) { running = run; }

	public void run() {
		running = true;

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

				//check Remote
				if (remote.isOn() || forcedRemote){
					motor.setSpeed(remote.getPort1());
					steer.setBackWheels(remote.getPort2()*15+50);
					steer.setFrontWheels(remote.getPort2()*15+50);
				}
				else{

					if (!running) {
						time += 2000;
						Thread.sleep(time - System.currentTimeMillis());
						continue;
					}

					sum0 = 0; sum1 = 0; sum2 = 0;

					toAdd[0] = eyes.getDist('w');
					toAdd[1] = eyes.getDist('l');
					toAdd[2] = eyes.getDist('c');
					toAdd[3] = eyes.getDist('r');
					toAdd[4] = eyes.getDist('e');

					toAdd[0] = (toAdd[0] < 0) ? 100 : toAdd[0];
					toAdd[1] = (toAdd[1] < 0) ? 100 : toAdd[1];
					toAdd[2] = (toAdd[2] < 0) ? 100 : toAdd[2];
					toAdd[3] = (toAdd[3] < 0) ? 100 : toAdd[3];
					toAdd[4] = (toAdd[4] < 0) ? 100 : toAdd[4];

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

					fsum0 = (int) negativeMultiply(capper(sum0, 4., -2.), 2);
					fsum1 = (int) capper(sum1, 100., 0.);
					fsum2 = (int) capper(sum2, 100, 0);

					// set down the smarts
					motor.setSpeed( fsum0 );
					steer.setFrontWheels( fsum1 );
					steer.setBackWheels( fsum2 );
				}
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			}
		} catch (Throwable e) { e.printStackTrace(); }

		// if driver dies we want to stop dead!
		motor.setSpeed( 0 );
		steer.setFrontWheels( 50 );
		steer.setBackWheels( 50 );
	}

	public static double capper(double value, double max, double min) {
		return (value > max) ? max : (value < min) ? min : value ;
	}
	public static double negativeMultiply(double value, double mult) {
		return (value < 0) ? value * mult : value;
	}

	public void setRemoteStatus(boolean x) {forcedRemote = x;}
	
	public String[] toDebugString(String in[]) {
		in[0] = "Intel Debug";
		in[1] = "Nor: " + fsum0 + " " + fsum1 + " " + fsum2;
		return in;
	}
}