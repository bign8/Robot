
/*
 * This class is used to demonstrate the neural network for given inputs
 * TODO: Train robot to teach itself
 * http://www4.rgu.ac.uk/files/chapter3%20-%20bp.pdf
 */
public class Nural {
	
	public static void run() {
		
		double[][] trainingData = {
			//   W      L      C      R      E    V     F     B
			{100.0, 100.0, 100.0, 100.0, 100.0, 3.0, 50.0, 50.0},
			{35.0, 90.0, 0.0, 0.0, 0.0, 0.5, 0.5, 0.5},
			{},
			{},
			{},
			{}
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
			}/*,{
				{ 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 1 // Layer 3
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 2
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 3
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 4
				{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }   // Hidden node 5
			},{
				{ 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 1 // Layer 4
				{ 1.0, 1.0, 0.0, 0.0, 0.0, 0.0 },  // Hidden node 2
				{ 1.0, 0.0, 1.0, 0.0, 0.0, 0.0 },  // Hidden node 3
				{ 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 },  // Hidden node 4
				{ 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 }   // Hidden node 5
			}*/
		};
		
		double[][] ofset = {
			{ 0.0, 0.0, 0.0, 0.0, 0.0 }, // sigmoid ofsets 1
			{ 0.0, 0.0, 0.0, 0.0, 0.0 }/*, // sigmoid ofsets 2
			{ 0.0, 0.0, 0.0, 0.0, 0.0 }, // sigmoid ofsets 3
			{ 0.0, 0.0, 0.0, 0.0, 0.0 }  // sigmoid ofsets 4*/
		};
		
		boolean[][] active = {
			{ true, true, false, false, false, false }, // first one should always be true
			{ true, true, false, false, false, false },/*TODO: change bias back to true on this line after devl
			{ true, true, true, true, true, true },
			{ true, true, true, true, true, true },*/
			{ true, false, false, false, false, false } // last one should only have three true
		};
		
		double[][] outputs = new double[active.length][active[0].length]; // allows storage of past calcuations
		
		int i, j, k, l;
		
		// -------------------------------------------------------
		// |                     BEGIN EPOCH                     |
		// -------------------------------------------------------
		
		outputs[0][0] = capper(trainingData[1][0] / 100.0, 1.0, 0.0);
		outputs[0][1] = capper(trainingData[1][1] / 100.0, 1.0, 0.0);
		outputs[0][2] = capper(trainingData[1][2] / 100.0, 1.0, 0.0);
		outputs[0][3] = capper(trainingData[1][3] / 100.0, 1.0, 0.0);
		outputs[0][4] = capper(trainingData[1][4] / 100.0, 1.0, 0.0); // for sensors
		outputs[0][5] = 1.0;
		outputs[1][5] = 1.0;
		outputs[2][5] = 1.0;
		//outputs[3][5] = 1.0;
		//outputs[4][5] = 1.0;
		
		printArr(outputs[0], 50);
		
		// -------------------------------------------------------
		// |                    QUERY NEURONS                    |
		// -------------------------------------------------------
		
		// looping through the layers
		for ( i = 0; i < weights.length; i++ ) {
			
			// loop through rows of output
			for ( j = 0; j < weights[0].length; j++ ) {
				if ( active[i+1][j] ) {
					for ( k = 0; k < weights[0][0].length; k++ ) if ( active[i][k] ) outputs[i+1][j] += outputs[i][k] * weights[i][j][k]; // sum rows
					
					outputs[i+1][j] = sigmoid( outputs[i+1][j] , ofset[i][j]); // perform threshold on sums
				}
			}
			
			printArr(outputs[i+1], i); // debugging
		}
		
		// scaling outputs of network - for actual use of neural network
		//sums[0] = capper( sums[0] * 6 - 3 , -3.0 , 3.0 ); // [-3,3]
		//sums[1] = capper( sums[1] * 100 , 0.0 , 100.0 );  // [0:100]
		//sums[2] = capper( sums[2] * 100 , 0.0 , 100.0 );  // [0:100]
		
		System.out.println("[Backwards NOW]");
		
		// -------------------------------------------------------
		// |                   BACKPROPAGATION                   |
		// -------------------------------------------------------
		
		i = weights.length; // moving backward through layers
		
		// output error!
		double[][] error = new double[weights.length][weights[0].length];
		for ( j = 0; j < weights[0].length; j++ ) {
			if ( active[i][j] )
				error[i-1][j] = outputs[i][j] * ( 1 - outputs[i][j] ) * (trainingData[1][5+j] - outputs[i][j]);
		}
		printArr(error[i-1], 99);
		
		// new weights for output layer
		for ( j = 0; j < weights[0].length; j++ ) {
			if ( active[i][j] ) {
				for ( k = 0; k < weights[0][0].length; k++ ) {
					if (active[i-1][k]) {
						weights[i-1][j][k] += outputs[i-1][k] * error[i-1][j];
					}
				}
				printArr(weights[i-1][j], 98);
			}
		}
		
		// change weights for hidden layers
		for (i--; i > 0 ; i--) {
			
			//calculate errors in hidden layers
			for ( j = 0; j < weights[0].length; j++ ) {
				if ( active[i][j] ) {
					for ( k = 0; k < weights[0][0].length; k++ ) {
						if (active[i+1][k]) {
							error[i-1][j] += error[i][k] * weights[i][k][j];
						}
					}
					error[i-1][j] *= ( 1 - outputs[i][j] ) * outputs[i][j];
				}
			}
			printArr(error[i-1], 97);
			
			System.out.println("\n[Nate is working Here!]"); // TODO remove this line
			
			// calculate new layer weights
			for ( j = 0; j < weights[0].length; j++ ) {
				if ( active[i][j] ) {
					for ( k = 0; k < weights[0][0].length; k++ ) {
						if (active[i][k]) {
							weights[i-1][j][k] += error[i-1][j] * outputs[i-1][k];
						}
					}
				}
			}
		}
		
		// TODO Remove these debugging lines
		System.out.println("\n[Weight Matrix]");
		for (i = 0; i < weights.length; i++) {
			for (j = 0; j < weights[0].length; j++) {
				for (k = 0; k < weights[0][0].length; k++)
					System.out.print(weights[i][j][k] + " ");
				System.out.println();
			}
			System.out.println();
		}
		
		System.out.println("[Errors Matrix]");
		for (i = 0; i < error.length; i++) {
			for (j = 0; j < error[0].length; j++)
				System.out.print(error[i][j] + " ");
			System.out.println();
		}
	}
	
	public static double sigmoid( double x , double thresh ) {
		return 1.0 / ( 1.0 + Math.exp(thresh-x) );
	}
	
	public static void printArr( double [] x, int row ) {
		System.out.print("Row " + row + " | ");
		for (int i = 0; i < x.length; i++) System.out.print(x[i] + " | ");
		System.out.println();
	}
	
	public static double capper(double value, double max, double min) {
		return (value > max) ? max : (value < min) ? min : value ;
	}
	
	public static void main(String[] vars) { run(); }
}