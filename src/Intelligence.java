public class Intelligence implements Runnable, Debuggable {

	private boolean running;

	private Engine motor;
	private SteeringWheel steer;
	private Sonar eyes;
	private Remote remote;

	private int fsum0 = 0, fsum1 = 0, fsum2 = 0;
	private boolean forcedRemote = false;

	/*
	private static double[][] weights = {
		//   W     L     C     R     E     B
		{  -50, -100,   .2, -100,  -50,    0},  // motor
		{  500,  500, -500, -500, -500,   50},  // front steer
		{    0,  500, -500, -500,    0,   50}   // back steer
	};//*/

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

		// -------------------------------------------------------
		// |               START VARIABLES FOR AI                |
		// -------------------------------------------------------
		double[][][] weights = {
			{   //  W    L    C    R    E    B	
				{ 0.1, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 1 // Layer 1
				{ 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 2
				{ 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 },  // Hidden node 3
				{ 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 },  // Hidden node 4
				{ 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 }   // Hidden node 5
			},{
				{ 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 1 // Layer 2
				{ 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 2
				{ 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 },  // Hidden node 3
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 4
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }   // Hidden node 5
			}
		};
		
		boolean[][] active = { // last column is bias!
			{ true, true, true, true, true, false }, // first one should always be true
			{ true, true, true, true, true, false },//*TODO: change bias back to true on this line after devl
			{ true, true, true, false, false, false } // last one should only have three true
		};
		
		double[][] outputs = new double[active.length][active[0].length]; // allows storage of past calcuations
		int i, j, k;
		// -------------------------------------------------------
		// |              ENDING VARIABLES FOR AI                |
		// -------------------------------------------------------
		
		//double sum0 = 0, sum1 = 0, sum2 = 0;
		//double[] toAdd = new double[6];
		//toAdd[5] = 1.0;
		long time = System.currentTimeMillis();

		try {
			while (true) {

				//check Remote
				if (remote.isOn() || forcedRemote){
					motor.setSpeed(remote.getPort1());
					steer.setBackWheels(remote.getPort2()*15+50);
					steer.setFrontWheels(remote.getPort2()*15+50);
				} else{
					
					if (!running) {
						time += 2000;
						Thread.sleep(time - System.currentTimeMillis());
						continue;
					}
					
					outputs[0][0] = capper(eyes.getDist('w') / 100.0, 1.0, 0.0);
					outputs[0][1] = capper(eyes.getDist('l') / 100.0, 1.0, 0.0);
					outputs[0][2] = capper(eyes.getDist('c') / 100.0, 1.0, 0.0);
					outputs[0][3] = capper(eyes.getDist('r') / 100.0, 1.0, 0.0);
					outputs[0][4] = capper(eyes.getDist('e') / 100.0, 1.0, 0.0); // for sensors

					for (i = 0; i < weights.length; i++) outputs[i][5] = 1.0; // setup biases
					
					// -------------------------------------------------------
					// |                    QUERY NEURONS                    |
					// -------------------------------------------------------
					
					// looping through the layers
					for ( i = 0; i < weights.length-1; i++ ) {
						
						// loop through rows of output
						for ( j = 0; j < weights[0].length; j++ ) {
							if ( active[i+1][j] ) {
								for ( k = 0; k < weights[0][0].length; k++ ) if ( active[i][k] ) outputs[i+1][j] += outputs[i][k] * weights[i][j][k]; // sum rows
								
								outputs[i+1][j] = sigmoid( outputs[i+1][j] , 0 /*ofset[i][j]*/); // perform threshold on sums
							}
						}
					}
					
					// scaling outputs of network - for actual use of neural network
					fsum0 = (int) capper( outputs[i+1][0] * 6 - 3 , -3.0 , 3.0 ); // [-3,3]
					fsum1 = (int) capper( outputs[i+1][1] * 100 , 0.0 , 100.0 );  // [0:100]
					fsum2 = (int) capper( outputs[i+1][2] * 100 , 0.0 , 100.0 );  // [0:100]
					
					motor.setSpeed( fsum0 );
					steer.setFrontWheels( fsum1 );
					steer.setBackWheels( fsum2 );
				}
				time += 200;
				Thread.sleep(time - System.currentTimeMillis());
			}
		} catch (Throwable e) { e.printStackTrace(); }

		// if driver dies we want to stop dead!
		motor.setSpeed( 0 );
		steer.setFrontWheels( 50 );
		steer.setBackWheels( 50 );
	}
	
	public static double sigmoid( double x , double thresh ) { return 1.0 / ( 1.0 + Math.exp(thresh-x) ); }
	
	public void setRemoteStatus(boolean x) {forcedRemote = x;}
	
	public static double capper(double value, double max, double min) {
		return (value > max) ? max : (value < min) ? min : value ;
	}
	public static double negativeMultiply(double value, double mult) {
		return (value < 0) ? value * mult : value;
	}

	public String[] toDebugString(String in[]) {
		in[0] = "Intel Debug";
		in[1] = "Nor: " + fsum0 + " " + fsum1 + " " + fsum2;
		return in;
	}
}