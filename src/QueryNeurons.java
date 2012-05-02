/*
 * This class is used to demonstrate the neural network for given inputs
 * http://www.pirobot.org/blog/0007/
 * http://www4.rgu.ac.uk/files/chapter3%20-%20bp.pdf
 */
public class QueryNeurons {
	
	public static void run() {
		
		double[][] trainingData = {
				{100, 100, 100, 100, 100},
				{50, 50, 50, 50, 50},
				{25, 25, 25, 25, 25},
				{10, 10, 10, 10, 10},
				{100, 100, 50, 100, 100},
				{71, 90, 66, 55, 55}
		};
		
		double[][][] weights = {
				{
					{-2.6899604195695592, -6.861307432001552, 3.0889084898418147, -4.898503176326177, 1.2551566115776542, 9.068202059393013},
					{-7.2212457700813735, -8.558025416624963, -0.7293704718999541, 3.512789479224452, -2.8151303213256553, -0.11552900039795944},
					{-3.584359488991256, -1.8468971974678252, 5.861020991086735, -7.365305004733048, -8.811419512038226, 1.3846116847103644},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
					{0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
				}
			};

		
		boolean[][] active = { // last column is bias!
				{ true, true, true, true, true, true }, // first one should always be true
				//{ true, true, true, true, true, true },
				{ true, true, true, false, false, false } // last one should only have three true
			};
		
		double[][] outputs = new double[active.length][active[0].length]; // allows storage of past calcuations
		int i, j, k, epoch;
		
		
		for (i = 0; i < trainingData.length; i++) {
			trainingData[i][0] = capper( ( (trainingData[i][0] < 0) ? 100 : trainingData[i][0]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][1] = capper( ( (trainingData[i][1] < 0) ? 100 : trainingData[i][1]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][2] = capper( ( (trainingData[i][2] < 0) ? 100 : trainingData[i][2]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][3] = capper( ( (trainingData[i][3] < 0) ? 100 : trainingData[i][3]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][4] = capper( ( (trainingData[i][4] < 0) ? 100 : trainingData[i][4]) / 100.0, 1.0, 0.0 ) ;
		}
		
		// -------------------------------------------------------
		// |                     BEGIN EPOCH                     |
		// -------------------------------------------------------
		
		for (epoch = 0; epoch < trainingData.length; epoch++) {
			
			outputs[0][0] = trainingData[epoch % trainingData.length][0];
			outputs[0][1] = trainingData[epoch % trainingData.length][1];
			outputs[0][2] = trainingData[epoch % trainingData.length][2];
			outputs[0][3] = trainingData[epoch % trainingData.length][3];
			outputs[0][4] = trainingData[epoch % trainingData.length][4];

			for (i = 0; i < outputs.length; i++) outputs[i][5] = 1.0; // setup biases
			
			// -------------------------------------------------------
			// |                    QUERY NEURONS                    |
			// -------------------------------------------------------
			
			// looping through the layers
			for ( i = 0; i < weights.length; i++ ) {
				
				// loop through rows of output
				for ( j = 0; j < weights[0].length; j++ ) {
					if ( active[i+1][j] ) {
						for ( k = 0; k < weights[0][0].length; k++ ) if ( active[i][k] ) outputs[i+1][j] += outputs[i][k] * weights[i][j][k]; // sum rows
						
						outputs[i+1][j] = sigmoid( outputs[i+1][j] , 0 ); // perform threshold on sums
					}
				}
			}
			
			// scaling outputs of network - for actual use of neural network
			outputs[i][0] = capper( outputs[i][0] * 6 - 3 , -3.0 , 3.0 ); // [-3,3]
			outputs[i][1] = capper( outputs[i][1] * 100 , 0.0 , 100.0 );  // [0:100]
			outputs[i][2] = capper( outputs[i][2] * 100 , 0.0 , 100.0 );  // [0:100]
			
			System.out.println(outputs[i][0] + " " + outputs[i][1] + " " + outputs[i][2]);
		}
		
	}
	
	public static double sigmoid( double x , double thresh ) { return 1.0 / ( 1.0 + Math.exp(thresh-x) ); }
	public static double capper(double value, double max, double min) {	return value; /*(value > max) ? max : (value < min) ? min : value ;*/ }
	public static void main(String[] vars) { run(); }
}
