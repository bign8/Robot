/*
 * This class is used to demonstrate the neural network for given inputs
 * http://www.pirobot.org/blog/0007/
 * http://www4.rgu.ac.uk/files/chapter3%20-%20bp.pdf
 */
public class Nural {
	
	public static void run() {
		/*
		double[][] trainingData = {
			//   W      L      C      R      E    V     F     B
			//{100.0, 100.0, 100.0, 100.0, 100.0, 3.0, 50.0, 50.0},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5}
		};//*/
		
		double[][] trainingData = {
				{79, 49, 42, 37, -1, 0, 50, 50},
				{79, 49, 42, 37, -1, 0, 50, 50},
				{-1, 49, 42, 37, -1, 0, 50, 50},
				{79, 49, 38, 37, -1, 0, 50, 50},
				{79, 49, 42, 37, -1, 0, 50, 50},
				{79, 49, 42, 37, -1, 0, 50, 50},
				{-1, 49, 38, 37, -1, 0, 50, 50},
				{78, 49, 38, 37, -1, 0, 50, 50},
				{78, 49, 42, 37, -1, 0, 50, 50},
				{78, 49, 42, 37, -1, 0, 50, 50},
				{-1, 49, 39, 37, -1, 0, 65, 35},
				{79, 49, 42, 37, -1, 0, 95, 5},
				{79, 49, 39, 37, -1, 0, 95, 5},
				{79, 49, 42, 37, -1, 0, 95, 5},
				{-1, 49, 42, 37, -1, 0, 95, 5},
				{79, 49, 42, 37, -1, 0, 80, 20},
				{79, 49, 42, 37, -1, 0, 35, 65},
				{79, 49, 42, 37, -1, 0, 5, 95},
				{-1, 49, 42, 37, -1, 0, 5, 95},
				{79, 49, 42, 37, -1, 0, 5, 95},
				{79, 49, 42, 37, -1, 0, 5, 95},
				{79, 49, 42, 37, -1, 0, 50, 50},
				{-1, 49, 42, 37, -1, 0, 95, 5},
				{78, 49, 42, 37, -1, 0, 95, 5},
				{78, 49, 42, 37, -1, 0, 95, 5},
				{78, 49, 42, 37, -1, 0, 95, 5},
				{-1, 49, 43, 37, -1, 0, 80, 20},
				{79, 49, 39, 37, -1, 0, 35, 65},
				{79, 49, 38, 37, -1, 0, 5, 95},
				{79, 49, 42, 37, -1, 0, 5, 95},
				{-1, 49, 39, 37, -1, 0, 5, 95},
				{78, 49, 42, 37, -1, 0, 35, 65},
				{78, 49, 42, 37, -1, 0, 80, 20},
				{78, 49, 42, 37, -1, 0, 95, 5},
				{-1, 49, 42, 37, -1, 0, 80, 20},
				{79, 49, 42, 37, -1, 0, 65, 35},
				{79, 49, 42, 37, -1, 0, 65, 35},
				{79, 49, 42, 37, -1, 1, 50, 50},
				{-1, 49, 42, 37, -1, 0, 50, 50},
				{78, 49, 42, 37, -1, 1, 50, 50},
				{78, 49, 42, 37, -1, -2, 50, 50},
				{78, 49, 42, 37, -1, -3, 50, 50},
				{-1, 49, 42, 37, -1, -3, 50, 50},
				{79, 49, 42, 37, -1, -3, 50, 50},
				{79, 49, 42, 37, -1, -1, 50, 50},
				{79, 49, 42, 37, -1, -1, 50, 50},
				{-1, 49, 42, 37, -1, -2, 50, 50},
				{78, 49, 42, 37, -1, -3, 50, 50},
				{78, 49, 42, 37, -1, -3, 50, 50},
				{78, 49, 42, 37, -1, -3, 50, 50},
				{-1, 49, 42, 37, -1, -3, 50, 50},
				{79, 49, 42, 37, -1, -3, 50, 50},
				{79, 49, 42, 37, -1, -3, 50, 50},
				{79, 49, 42, 37, -1, -3, 50, 50},
				{-1, 49, 38, 37, -1, -3, 50, 50},
				{78, 49, 38, 37, -1, -3, 50, 50},
				{78, 49, 38, 37, -1, -3, 50, 50},
				{78, 49, 38, 37, -1, -3, 50, 50},
				{-1, 49, 42, 37, -1, -3, 50, 50},
				{79, 49, 42, 37, -1, -3, 50, 50},
				{79, 49, 42, 37, -1, -1, 50, 50},
				{79, 49, 42, 37, -1, -1, 50, 50},
				{-1, 49, 42, 37, -1, 0, 50, 50},
				{100, 49, 42, 37, -1, 0, 50, 50},
				{100, 49, 38, 37, -1, 0, 50, 50},
				{100, 49, 38, 37, -1, 0, 50, 50}
			};
		//*
		double[][][] weights = {
			{   //  W    L    C    R    E    B	
				{ 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 1 // Layer 1
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
		};//*/
		
		boolean[][] active = { // last column is bias!
			{ true, true, true, true, true, true }, // first one should always be true
			{ true, true, true, true, false, true },//*TODO: change bias back to true on this line after devl
			{ true, true, true, false, false, false } // last one should only have three true
		};
		
		//double[][][] weights = new double[active.length-1][active[0].length][active[0].length-1];
		weights = randomInit(weights, active);
		
		double[][] outputs = new double[active.length][active[0].length]; // allows storage of past calcuations
		double[][] error = new double[weights.length][weights[0].length];
		int i, j, k, epoch, numberOfCycles = 100;
		double learningRate = 0.05d, curError, tempDiff;
		
		
		for (i = 0; i < trainingData.length; i++) {
			trainingData[i][0] = capper( ( (trainingData[i][0] < 0) ? 100 : trainingData[i][0]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][1] = capper( ( (trainingData[i][1] < 0) ? 100 : trainingData[i][1]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][2] = capper( ( (trainingData[i][2] < 0) ? 100 : trainingData[i][2]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][3] = capper( ( (trainingData[i][3] < 0) ? 100 : trainingData[i][3]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][4] = capper( ( (trainingData[i][4] < 0) ? 100 : trainingData[i][4]) / 100.0, 1.0, 0.0 ) ;
			trainingData[i][5] = capper( trainingData[i][5] * 6 - 3 , -3.0 , 3.0 ); // [-3,3]
			trainingData[i][6] = capper( trainingData[i][6] * 100 , 0.0 , 100.0 );  // [0:100]
			trainingData[i][7] = capper( trainingData[i][7] * 100 , 0.0 , 100.0 );  // [0:100]
		}
		
		// -------------------------------------------------------
		// |                     BEGIN EPOCH                     |
		// -------------------------------------------------------
		
		for (epoch = 0; epoch < numberOfCycles; epoch++) {
			
			/*
			outputs[0][0] = (trainingData[epoch % trainingData.length][0] < 0) ? 100 : trainingData[epoch % trainingData.length][0] ;
			outputs[0][1] = (trainingData[epoch % trainingData.length][1] < 0) ? 100 : trainingData[epoch % trainingData.length][1] ;
			outputs[0][2] = (trainingData[epoch % trainingData.length][2] < 0) ? 100 : trainingData[epoch % trainingData.length][2] ;
			outputs[0][3] = (trainingData[epoch % trainingData.length][3] < 0) ? 100 : trainingData[epoch % trainingData.length][3] ;
			outputs[0][4] = (trainingData[epoch % trainingData.length][4] < 0) ? 100 : trainingData[epoch % trainingData.length][4] ;
			
			
			outputs[0][0] = capper(outputs[0][0] / 100.0, 1.0, 0.0);
			outputs[0][1] = capper(outputs[0][1] / 100.0, 1.0, 0.0);
			outputs[0][2] = capper(outputs[0][2] / 100.0, 1.0, 0.0);
			outputs[0][3] = capper(outputs[0][3] / 100.0, 1.0, 0.0);
			outputs[0][4] = capper(outputs[0][4] / 100.0, 1.0, 0.0); // for sensors
			//*/

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
						
						outputs[i+1][j] = sigmoid( outputs[i+1][j] , 0 /*ofset[i][j]*/); // perform threshold on sums
					}
				}
			}
			
			// scaling outputs of network - for actual use of neural network
			//outputs[i][0] = capper( outputs[i][0] * 6 - 3 , -3.0 , 3.0 ); // [-3,3]
			//outputs[i][1] = capper( outputs[i][1] * 100 , 0.0 , 100.0 );  // [0:100]
			//outputs[i][2] = capper( outputs[i][2] * 100 , 0.0 , 100.0 );  // [0:100]
			
			// -------------------------------------------------------
			// |                   BACKPROPAGATION                   |
			// -------------------------------------------------------
			
			i = weights.length; // moving backward through layers
			curError = 0.0;
			
			// output error!
			error = new double[weights.length][weights[0].length]; // clear error to zeros
			for ( j = 0; j < weights[0].length; j++ ) {
				if ( active[i][j] ) {
					tempDiff = (trainingData[epoch % trainingData.length][5+j] - outputs[i][j]);
					error[i-1][j] = outputs[i][j] * ( 1 - outputs[i][j] ) * tempDiff;
					curError += Math.pow(tempDiff, 2);
				}
			}
			
			// Display current calculated error
			System.out.println("Epoch: " + (epoch+1) + "\tOutput Error: " + curError);
			
			// new weights for output layer
			for ( j = 0; j < weights[0].length; j++ ) {
				if ( active[i][j] ) {
					for ( k = 0; k < weights[0][0].length; k++ ) {
						if (active[i-1][k]) {
							weights[i-1][j][k] += outputs[i-1][k] * error[i-1][j] * learningRate;
						}
					}
				}
			}
			
			// change weights for hidden layers
			for (i--; i > 0 ; i--) {
				
				//calculate errors in hidden layers
				for ( j = 0; j < weights[0].length; j++ ) {
					if ( active[i][j] ) {
						for ( k = 0; k < weights[0][0].length; k++ ) { // fix possible error with -1 might want to verify
							if (active[i+1][k]) {
								error[i-1][j] += error[i][k] * weights[i][k][j];
							}
						}
						error[i-1][j] *= ( 1 - outputs[i][j] ) * outputs[i][j];
					}
				}
				
				// calculate new layer weights
				for ( j = 0; j < weights[0].length; j++ ) {
					if ( active[i][j] ) {
						for ( k = 0; k < weights[0][0].length; k++ ) {
							if (active[i][k]) {
								weights[i-1][j][k] += error[i-1][j] * outputs[i-1][k] * learningRate;
							}
						}
					}
				}
			}
		}
		
		printWeights(weights);
	}
	
	public static double sigmoid( double x , double thresh ) { return 1.0 / ( 1.0 + Math.exp(thresh-x) ); }
	public static double capper(double value, double max, double min) {	return (value > max) ? max : (value < min) ? min : value ; }
	public static double[][][] randomInit(double[][][] in, boolean[][] act) {
		for (int i = 0; i < in.length; i++)
			for (int j = 0; j < in[0].length; j++) {
				if (act[i+1][j])
				for (int k = 0; k < in[0][0].length; k++)
					if (act[i][k])
						in[i][j][k] = Math.random();
			}
		return in;
	}
	public static void printWeights(double[][][] weights){
		// Output new weights in a usable format - yes its formatted java
		System.out.println("\n[Weight Matrix]\n\ndouble[][][] weights = {");
		for (int i = 0; i < weights.length; i++) {
			System.out.print("\t{\n");
			for (int j = 0; j < weights[0].length; j++) {
				System.out.print("\t\t{");
				for (int k = 0; k < weights[0][0].length; k++) {
					System.out.print(weights[i][j][k] + "");
					if (k != weights[0][0].length - 1) System.out.print(", ");
				}
				System.out.print("}");
				if (j != weights[0].length - 1) System.out.print(",");
				System.out.println();
			}
			System.out.print("\t}");
			if (i != weights.length - 1) System.out.print(",");
			System.out.println();
		}
		System.out.println("};");
	}
	public static void main(String[] vars) { run(); }
}
