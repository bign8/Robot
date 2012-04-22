/*
 * This class is used to demonstrate the neural network for given inputs
 * TODO: Train robot to teach itself
 */
public class Nural {

	
	private static double[][] weights = {
		//   W     L     C     R     E     B
		{  -50,  -60,  -60,  -60,  -50,    4},  // motor
		{  500,  500,    0, -500, -500,   50},  // front steer
		{    0,  500,    0, -500,    0,   50}   // back steer
	};
	
	public static void run() {
		double sum0 = 0, sum1 = 0, sum2 = 0;
		double[] toAdd = new double[6];
		toAdd[5] = 1;
		sum0 = 0; sum1 = 0; sum2 = 0;

		toAdd[0] = 00.0;
		toAdd[1] = 00.0;
		toAdd[2] = 40.0;
		toAdd[3] = 00.0;
		toAdd[4] = 00.0;

		for (int i = 0; i < 6; i++) {
			if ( toAdd[i] > 0 ) { // exclude poor input
				
				//if ( i == 2 )
					//sum0 += toAdd[i] * weights[0][i];
				//else
				sum0 += 1./toAdd[i] * weights[0][i];
				sum1 += 1./toAdd[i] * weights[1][i];
				sum2 += 1./toAdd[i] * weights[2][i];
			}
		}
		
		// For actual neural network implementation
		//sum0 = sum0 * 32 - 16; // allow negative speeds
		//sum1 *= 100; // adjust to turning distances
		//sum2 *= 100;
		
		// set down the smarts
		System.out.println("Normal: | " + sum0 + " | " + sum1 + " | " + sum2 + " |");
		System.out.println("Capped: | " + capper(sum0, 4., -2.) + " | " + capper(sum1, 100., 0.) + " | " + capper(sum2, 100, 0) + " |");
	}
	
	public static double capper(double value, double max, double min) {
		return (value > max) ? max : (value < min) ? min : value ;
	}
	
	public static void main(String[] vars) { run(); }
}