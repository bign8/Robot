public class Nural {

	//        							 SL   L   C   R  SR  bias
	private static double[][] weights = { {-1, -2, -5, -2, -1, 9},  // motor
		{ 1,  1,  5, -1, -1, 0},  // front steer
		{ 0,  1, -5, -1,  0, 0} };// back steer


	public static void main(String[] vars) {

		double sum0 = 0, sum1 = 0, sum2 = 0;
		double[] toAdd = new double[6];
		toAdd[5] = 1;
		sum0 = 0; sum1 = 0; sum2 = 0;

		toAdd[0] = 5.0;
		toAdd[1] = 5.0;
		toAdd[2] = 0.0;
		toAdd[3] = 0.0;
		toAdd[4] = 0.0;


		// NATE: TODO
		// WHY YOU SO DUMB
		// This logic is the 'inverse' *hint hint* of what you want!

		for (int i = 0; i < 6; i++) {
			if ( toAdd[i] > 0 ) { // exclude poor input
				sum0 += toAdd[i] * weights[0][i];
				sum1 += toAdd[i] * weights[1][i];
				sum2 += toAdd[i] * weights[2][i];
			}
		}

		// set down the smarts
		System.out.println("| " + sum0 + " | " + sum1 + " | " + sum2 + " |");


	}
}