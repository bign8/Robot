/*
 * This class is used to demonstrate the neural network for given inputs
 * http://www.pirobot.org/blog/0007/
 * http://www4.rgu.ac.uk/files/chapter3%20-%20bp.pdf
 */
public class Nural {
	
	public static void run() {
		
		double[][] trainingData = {
			//   W      L      C      R      E    V     F     B
			//{100.0, 100.0, 100.0, 100.0, 100.0, 3.0, 50.0, 50.0},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5}
		};
		
		double[][][] weights = {
			{   //  W    L    C    R    E    B	
				{ 0.1, 0.8, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 1 // Layer 1
				{ 0.4, 0.6, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 2
				{ 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 },  // Hidden node 3
				{ 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 },  // Hidden node 4
				{ 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 }   // Hidden node 5
			},{
				{ 0.3, 0.9, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 1 // Layer 2
				{ 0.3, 0.8, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 2
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 3
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 4
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }   // Hidden node 5
			}
		};
		/*
		double[][] ofset = {
			{ 0.0, 0.0, 0.0, 0.0, 0.0 }, // sigmoid ofsets 1
			{ 0.0, 0.0, 0.0, 0.0, 0.0 }, // sigmoid ofsets 2
			{ 0.0, 0.0, 0.0, 0.0, 0.0 }, // sigmoid ofsets 3
			{ 0.0, 0.0, 0.0, 0.0, 0.0 }  // sigmoid ofsets 4
		};//*/
		
		
		
		boolean[][] active = { // last column is bias!
			{ true, true, false, false, false, false }, // first one should always be true
			{ true, true, false, false, false, false },//*TODO: change bias back to true on this line after devl
			{ true, false, false, false, false, false } // last one should only have three true
		};
		
		//double[][][] weights = new double[active.length-1][active[0].length][active[0].length-1];
		//weights = randomInit(weights);
		
		double[][] outputs = new double[active.length][active[0].length]; // allows storage of past calcuations
		double[][] error = new double[weights.length][weights[0].length];
		int i, j, k, epoch, numberOfCycles = 40;
		double learningRate = 1.0, curError, tempDiff;
		
		// -------------------------------------------------------
		// |                     BEGIN EPOCH                     |
		// -------------------------------------------------------
		
		for (epoch = 0; epoch < numberOfCycles; epoch++) {
		
			outputs[0][0] = capper(trainingData[epoch % trainingData.length][0] / 100.0, 1.0, 0.0);
			outputs[0][1] = capper(trainingData[epoch % trainingData.length][1] / 100.0, 1.0, 0.0);
			outputs[0][2] = capper(trainingData[epoch % trainingData.length][2] / 100.0, 1.0, 0.0);
			outputs[0][3] = capper(trainingData[epoch % trainingData.length][3] / 100.0, 1.0, 0.0);
			outputs[0][4] = capper(trainingData[epoch % trainingData.length][4] / 100.0, 1.0, 0.0); // for sensors

			//for (i = 0; i < active.length; i++) outputs[i][5] = 1.0; // setup biases
			
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
			//sums[0] = capper( sums[0] * 6 - 3 , -3.0 , 3.0 ); // [-3,3]
			//sums[1] = capper( sums[1] * 100 , 0.0 , 100.0 );  // [0:100]
			//sums[2] = capper( sums[2] * 100 , 0.0 , 100.0 );  // [0:100]
			
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
						for ( k = 0; k < weights[0][0].length-1; k++ ) { // fix possible error with -1 might want to verify
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
	public static double[][][] randomInit(double[][][] in) {
		for (int i = 0; i < in.length; i++)
			for (int j = 0; j < in[0].length; j++)
				for (int k = 0; k < in[0][0].length; k++)
					in[i][j][k] = Math.random();
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
