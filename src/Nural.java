public class Nural {

	
	private static double[][] weights = {
	//     SL    L     C    R    SR   bias
		{   0,  -.05, .2, -.05,   0,  0},  // motor
		{ -10,  -25, -.5,   25,  10, 50},  // front steer
		{ -10,   25,  .5,  -25, -10, 50} // back steer
	};


	private static double[][] training = {
	//	 SL   L   C   R  SR  R1, R2, R3
		{ 80, 80, 80, 80, 80, 16, 0, 0}, // regular driving
		{}
	};
	
	public static void main(String[] vars) {
		run();
	}
	
	public static void run() {
		double sum0 = 0, sum1 = 0, sum2 = 0;
		double[] toAdd = new double[6];
		toAdd[5] = 1;
		sum0 = 0; sum1 = 0; sum2 = 0;

		toAdd[0] = 0.0;
		toAdd[1] = 0.0;
		toAdd[2] = 5.0;
		toAdd[3] = 0.0;
		toAdd[4] = 0.0;


		// NATE: TODO
		// WHY YOU SO DUMB
		// This logic is the 'inverse' *hint hint* of what you want!

		for (int i = 0; i < 6; i++) {
			if ( toAdd[i] > 0 ) { // exclude poor input
				sum0 += 1/toAdd[i] * weights[0][i];
				sum1 += 1/toAdd[i] * weights[1][i];
				sum2 += 1/toAdd[i] * weights[2][i];
			}
		}
		
		sum0 = sum0 * 32 - 16; // allow negative speeds
		sum1 *= 100; // adjust to turning distances
		sum2 *= 100;
		
		// set down the smarts
		System.out.println("| " + sum0 + " | " + sum1 + " | " + sum2 + " |");
	}
}