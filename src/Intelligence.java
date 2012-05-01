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
				{
					{0.3919637458444665, 0.5148335903930069, 0.39296228136554606, 0.5569770116856183, 0.3214649420778039, 0.3029038872007337, 0.0},
					{0.18792739034171993, 0.2261885761007507, 0.043796140722086256, 0.2682011171643941, 0.166646479394004, 0.6191795361463575, 0.0},
					{0.12467984606043488, 0.8947394214545892, 0.7751816818391876, 0.13826724951665628, 0.48779397972851474, 0.19758738395928777, 0.0},
					{0.44930546793319226, 0.7780932072658214, 0.8738009625991046, 0.11065334022559317, 0.4757649008107054, 0.09240062276950045, 0.0},
					{0.15743776437169985, 0.8275739804705499, 0.5737598920111403, 0.3062500523724559, 0.9732607691825292, 0.03134465289306443, 0.0},
					{0.43696544328496334, 0.46204933890416794, 0.659114304136127, 0.3832788797061212, 0.6079258236060988, 0.6216312962546087, 0.0},
					{0.47788123885176614, 0.7315442134920582, 0.09328832178546578, 0.4399786172460134, 0.4480553492187654, 0.2531101683460143, 0.0},
					{0.06771954051791695, 0.8733707518369408, 0.8803523400994522, 0.13757029082373085, 0.8687857836436035, 0.4033860890486921, 0.0}
				},
				{
					{0.8970259356884612, 0.48507596945252257, 0.23159912405474767, 0.9013244930266894, 0.78759826550033, 0.8076920843038594, 0.3809782936976238},
					{0.19569072399641055, 0.05886327729433753, 0.8967922506230802, 0.16641866957966692, 0.4657767739659676, 0.4059847303493864, 0.8329156617525234},
					{0.8806823260798824, 0.8326872814541483, 0.6688044260036426, 0.6315650982486143, 0.8292181748176032, 0.8867149719427383, 0.014528950102788242},
					{0.3211137176695596, 0.9263792801739068, 0.4389229493315423, 0.20665837678835922, 0.2596377202227547, 0.8742980782869081, 0.5071965171552435},
					{0.8665515630414021, 0.18098610246231192, 0.5170300537785137, 0.37538303683726426, 0.0319367934222836, 0.7152779924384065, 0.9883065842899369},
					{0.5836319725508673, 0.1554346901423804, 0.13735056093413991, 0.18621962310640872, 0.8885716123364189, 0.9374129133790865, 0.7026511743993871},
					{0.9169042722666887, 0.546361449295587, 0.02087427437546746, 0.2096171301382831, 0.783579886835372, 0.757718110003754, 0.6369237076840361},
					{0.36242880651401643, 0.5085829849438023, 0.7428841782014978, 0.733480490031168, 0.10180231153809938, 0.9412914923675016, 0.7321386340224582}
				},
				{
					{0.06979502842568709, 0.3690681217284528, 0.47073375006655077, 0.12922624201524166, 0.5641408231601287, 0.2693575596591018, 0.5198711892463452},
					{0.8351650687185056, 0.19783801598668513, 0.4068312561148407, 0.320792513271198, 0.1901378886492663, 0.6384200551712166, 0.25661244385881077},
					{0.10050976289397273, 0.35079170274454735, 0.4435999416726126, 0.4529562593479693, 0.5088263269688218, 0.8213172375648374, 0.5523331115855378},
					{0.36894831324877897, 0.5075661552329883, 0.35885056238271956, 0.4865091920158619, 0.8994803159017883, 0.22665893432247244, 0.5024880650544938},
					{0.07382418006780762, 0.20099365459152935, 0.2571928655463004, 0.32341004269465273, 0.15259702668014452, 0.4669896663303483, 0.45092564154106235},
					{0.11163698672079081, 0.4901833455246748, 0.12646017619480707, 0.4890701602908552, 0.25322080645012235, 0.6046565433470824, 0.46140718517559176},
					{0.20975347795608798, 0.7928268798746246, 0.5359026622121955, 0.515127871343693, 0.7676090802932469, 0.4818192460509024, 0.3805909000820104},
					{0.15409311367027845, 0.7108139573325593, 0.4306204876278723, 0.3171521585744822, 0.8397905208760518, 0.5792891043417278, 0.7277672865545153}
				},
				{
					{-1.0298729906236233, -1.691522428872681, -1.0548141869740002, -1.7813787646946848, -0.9621670436885125, -1.590362657604082, -1.629299457830967},
					{-1.0043251251695304, -1.052514039442297, -0.827962939815075, -0.6845150049383278, -0.3671229670060189, -0.3945893401375062, -0.48204311490831575},
					{-1.137929697396158, -0.8529876228587168, -0.6673336809328086, -0.41597402038518894, -0.5333205626915452, -0.84005556539693, -0.3793138630237639},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
				}
			};
		
		boolean[][] active = { // last column is bias!
				{ true, true, true, true, true, true, false, false }, // first one should always be true
				{ true, true, true, true, true, true, true, true },
				{ true, true, true, true, true, true, true, true },
				{ true, true, true, true, true, true, true, true },
				{ true, true, true, false, false, false, false, false } // last one should only have three true
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
					outputs[0][1] = (outputs[0][1] < 0) ? 100.0 : outputs[0][1] ;
					outputs[0][2] = (outputs[0][2] < 0) ? 100.0 : outputs[0][2] ;
					outputs[0][3] = (outputs[0][3] < 0) ? 100.0 : outputs[0][3] ;
					outputs[0][4] = (outputs[0][4] < 0) ? 100.0 : outputs[0][4] ;
					
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