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
					{1.171117137379588, -6.631413701315866, -7.181738759195897, 4.528397365404116, -4.649555425554947, -2.714545938744788},
					{6.240154998907648, -1.4720848111196252, -7.116031975866748, -9.996657415034873, -8.369734997708843, -4.382111083158606},
					{7.505160799750831, 2.5767036968642194, 7.723840985325547, 4.302574784484895, -4.901269564164316, -6.0293301716118926},
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
