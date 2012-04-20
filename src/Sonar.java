import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.SonarRangeFinder;
import com.ridgesoft.robotics.sensors.ParallaxPing;
import com.ridgesoft.intellibrain.IntelliBrainDigitalIO;

public class Sonar implements Runnable {

	private Display display;
	protected static SonarRangeFinder frontSensor, leftSensor, rightSensor, westSensor, eastSensor;
	private int distF = 1, distL = 1, distR = 1, distW = 1, distE = 1;

	private boolean testing = false;
	
	public Sonar() {
		new Sonar(false);
	}
	
	public Sonar(boolean test) {
		testing = test;
		
		if (testing) display = IntelliBrain.getLcdDisplay();
		
		leftSensor = new ParallaxPing(IntelliBrain.getDigitalIO(5));
		frontSensor = new ParallaxPing(IntelliBrain.getDigitalIO(3));
		rightSensor = new ParallaxPing(IntelliBrain.getDigitalIO(4));
		//westSensor = new ParallaxPing(IntelliBrain.getDigitalIO(7)); // what port should this go on?
		eastSensor = new ParallaxPing(IntelliBrain.getDigitalIO(6));
		
		IntelliBrainDigitalIO data = IntelliBrain.getDigitalIO(10);
		data.setDirection(true); // configure as output
		//data.set();
		//data.clear();
		//data.toggle();
		//data.isSet();
		
		// see second to last paragraph on page 78 - http://www.ridgesoft.com/articles/education/ExploringRoboticsEdition2.pdf
		
		// we can only have 4 sonar sensors on the robot at the time -- 2 brains?
		
		 // have to connect to pins 6 and 7 - hopefully
		// looks like the metal washer on the east west mount is shorting out the sensor - need to re-mount with insiluator
	}

	public void run(){ 
		try{

			while(true){
				// Execute the ping
				leftSensor.ping();
				frontSensor.ping();
				rightSensor.ping();
				
				Thread.sleep(100);
				
				// What is the distance
				distL = (int) leftSensor.getDistanceInches();
				distF = (int) frontSensor.getDistanceInches();
				distR = (int) rightSensor.getDistanceInches();
				//distW = (int) westSensor.getDistanceInches();
				//distE = (int) eastSensor.getDistanceInches();
				
				if (testing) { 
					display.print(0, "F:" + Integer.toString(distF) + " L:" + Integer.toString(distL) + " R:" + Integer.toString(distR));
					display.print(1, "W:" + Integer.toString(distW) + " E:" + Integer.toString(distE));
				}
				
			}//end while
		} catch( Throwable t ) {
			t.printStackTrace();
		}
	}
	
	public int getDist(char x) {
		switch(x) {
			case 'c': return distF;
			case 'l': return distL;
			case 'r': return distR;
			case 'w': return distW;
			case 'e': return distE;
			default: return 0;
		}
	}
}