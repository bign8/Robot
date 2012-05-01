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
		/*double[][][] weights = {
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
		};*/
		double[][][] weights = {
				{
					{0.06298084023582806, 0.4161174958569185, 0.05614867762868836, 0.7661999738965372, 0.3812486536687415, 0.23411593589861746},
					{0.8655286619560624, 0.14910711970836166, 0.12098219942818511, 0.005662584705574236, 0.9507808834303776, 0.8960439801012674},
					{0.4955909436226459, 0.057320115000558225, 0.2760134197967703, 0.24512740688588464, 0.9753951941174757, 0.7670769671962586},
					{0.8422500725327811, 0.797208416576071, 0.19713940122498141, 0.4908511966713154, 0.2568465217424021, 1.3523661898677963},
					{0.468923522109745, 0.7491603862806412, 0.740198164565606, 0.8798147788740336, 0.37973814099041825, 0.23891826754726364}
				},
				{
					{-0.9936815671744001, -1.2875625269779494, -1.6017523316238917, -1.848930024807652, -1.124324023781227, -1.4351451877132486},
					{-0.0904491516858231, -1.004462146237652, -0.272795764208999, -1.2389248719556798, -0.2040978329429829, -0.8724460101502859},
					{-0.04171044431223468, -0.8965778859270992, -0.3795586860447999, -1.102217456036569, -0.6198108153900446, -0.7858901388432556},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
				}
			};
		
		boolean[][] active = { // last column is bias!
			{ true, true, true, true, true, true }, // first one should always be true
			{ true, true, true, true, true, true },//*TODO: change bias back to true on this line after devl
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
					
					outputs[0][0] = eyes.getDist('w'); // get data
					outputs[0][1] = eyes.getDist('l');
					outputs[0][2] = eyes.getDist('c');
					outputs[0][3] = eyes.getDist('r');
					outputs[0][4] = eyes.getDist('e');
					
					outputs[0][0] = (outputs[0][0] < 0) ? 100.0 : outputs[0][0] ; // clear -1's
					outputs[0][1] = (outputs[0][1] < 0) ? 100.0 : outputs[0][0] ;
					outputs[0][2] = (outputs[0][2] < 0) ? 100.0 : outputs[0][0] ;
					outputs[0][3] = (outputs[0][3] < 0) ? 100.0 : outputs[0][0] ;
					outputs[0][4] = (outputs[0][4] < 0) ? 100.0 : outputs[0][0] ;
					
					outputs[0][0] = capper(outputs[0][0] / 100.0, 1.0, 0.0); // cap for neural
					outputs[0][1] = capper(outputs[0][1] / 100.0, 1.0, 0.0);
					outputs[0][2] = capper(outputs[0][2] / 100.0, 1.0, 0.0);
					outputs[0][3] = capper(outputs[0][3] / 100.0, 1.0, 0.0);
					outputs[0][4] = capper(outputs[0][4] / 100.0, 1.0, 0.0); // for sensors

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