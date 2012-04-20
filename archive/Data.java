import com.ridgesoft.intellibrain.*;
import com.ridgesoft.io.Display;

/*
 * The data class exists to allow different controlling sections of the 
 * robot code to interface with each other by changing global variables
 * in the data class.
 * 
 * It is long, convoluted, and will only grow more so, but well commented
 * and segmented.
 */

public class Data {
	
	// Global Vars for botty
	public Display display;
	
	/////////////////////// CONSTRUCTOR //////////////////////////////////
	public Data() {
		try {	  //Recall that all hardware controls may throw exceptions
			display = IntelliBrain.getLcdDisplay(); // Get the Display
		} catch (Exception e) {
		}
	}

	////////////////////////////////////////////////////////////////////////
	//////////////////// STATE INFO FOR LINE FOLLOWING /////////////////////
	////////////////////////////////////////////////////////////////////////
	
	public final byte UNK = 0; // unknown
	private final byte S2L = 1; // 2 sensors left of line
	private final byte S1L = 2; // 1 sensor left of line
	private final byte CTR = 3; // centered, both sensors on line
	private final byte S1R = 4; // 1 sensor right of line
	private final byte S2R = 5; // 2 sensors right of line
	public final byte LEFT = 0;
	public final byte RIGHT = 1;
	// State transition table. Specifies the next state given the
	// current state and the Boolean sensor readings:
	// 0 - sensor is not over line
	// 1 - sensor is over the line
	public byte[][] NEXT_STATE = new byte[][] {
			// Index (binary)
					  // 00   01   10	11     Current State
			new byte[] { UNK, S1L, S1R, CTR }, // unknown
			new byte[] { S2L, S1L, S1R, CTR }, // 2 left
			new byte[] { S2L, S1L, S1R, CTR }, // 1 left

			new byte[] { UNK, S1L, S1R, CTR }, // centered
			new byte[] { S2R, S1L, S1R, CTR }, // 1 right
			new byte[] { S2R, S1L, S1R, CTR }, // 2 right
	};
	// Motor power table. Holds the power settings for the motors
	// given particular states.
	protected byte NORMAL = 8;
	protected byte LOW = 4;
	public byte[][] POWER = new byte[][] {
			// Left Right State
			new byte[] { 0, 0 }, // unknown
			new byte[] { NORMAL, 0 }, // 2 left
			new byte[] { NORMAL, LOW }, // 1 left
			new byte[] { NORMAL, NORMAL }, // both on line
			new byte[] { LOW, NORMAL }, // 1 right
			new byte[] { 0, NORMAL }, // 2 right
	};

	public int THRESHOLD = 300;

	public String getState(int state) {
		String stateString = "";
		switch (state) {
		case S2L:
			stateString = "2 Left";
			break;
		case S1L:
			stateString = "1 Left";
			break;
		case CTR:
			stateString = "Centered";
			break;
		case S1R:
			stateString = "1 Right";
			break;
		case S2R:
			stateString = "2 Right";
			break;
		case UNK:
			stateString = "Unknown";
			break;
		default:
			stateString = "Error";
			break;
		}
		return stateString;
	}
	
	// Overwrite parent power speed table
	public void setSpeed(byte normal, byte low) {
		POWER = new byte[][] {
				// Left Right State
				new byte[] { 0, 0 }, // unknown
				new byte[] { normal, 0 }, // 2 left
				new byte[] { normal, low }, // 1 left
				new byte[] { normal, normal }, // both on line
				new byte[] { low, normal }, // 1 right
				new byte[] { 0, normal }, // 2 right
		};
	}
	
	//////////////////// END LINE FOLLOWING LOGIC ////////////////////
}
