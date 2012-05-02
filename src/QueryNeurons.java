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
				{71, 90, 66, 55, 55},
				{-1, 44, 33, -1, -1, 2}
		};
		
		double[][][] weights = {
				{
					{-8.781892908766437, -0.011202604470844807, -2.1227850480885166, 1.1951533807070467, -8.110027950172675, -0.08162321166013717},
					{-4.915279608345124, 0.13567333853259125, -6.13711382715632, -4.1934732799044285, -1.9419030645752662, -7.907032862036478},
					{1.815103154113543, 3.6017492079252293, -8.327604095396168, -9.632246787219449, -9.89852467330616, 1.1898154235636549},
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
			trainingData[i][0] = 1./capper( ( (trainingData[i][0] < 0) ? 100 : trainingData[i][0]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][1] = 1./capper( ( (trainingData[i][1] < 0) ? 100 : trainingData[i][1]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][2] = capper( ( (trainingData[i][2] < 0) ? 100 : trainingData[i][2]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][3] = 1./capper( ( (trainingData[i][3] < 0) ? 100 : trainingData[i][3]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][4] = 1./capper( ( (trainingData[i][4] < 0) ? 100 : trainingData[i][4]) / 100.0, 1.0, 0.0 ) ;
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
