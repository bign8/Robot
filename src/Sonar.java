import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.SonarRangeFinder;
import com.ridgesoft.robotics.sensors.ParallaxPing;
import com.ridgesoft.intellibrain.IntelliBrainDigitalIO;

public class Sonar implements Runnable {

	private Display display;
	private SonarRangeFinder frontSensor, leftSensor, rightSensor, sideSensor;
	private int distF = 1, distL = 1, distR = 1, distW = 1, distE = 1;
	private IntelliBrainDigitalIO relay;

	private boolean testing = false;
	
	public Sonar(boolean test) {
		testing = test;
		
		if (testing) display = IntelliBrain.getLcdDisplay();
		
		leftSensor = new ParallaxPing(IntelliBrain.getDigitalIO(5));
		frontSensor = new ParallaxPing(IntelliBrain.getDigitalIO(3));
		rightSensor = new ParallaxPing(IntelliBrain.getDigitalIO(4));
		sideSensor = new ParallaxPing(IntelliBrain.getDigitalIO(6));
		
		relay = IntelliBrain.getDigitalIO(7);
		relay.setDirection(true); // configure as output
		relay.clear();
	}

	public void run(){ 
		int counter = 0;
		long time = System.currentTimeMillis();
		
		while(true){
			try{
				
				// Execute the ping
				leftSensor.ping();
				frontSensor.ping();
				rightSensor.ping();
				sideSensor.ping();
				
				// delay execution of thread
				time += 200;
				Thread.sleep(time - System.currentTimeMillis());
				
				// What is the distance
				distL = (int) leftSensor.getDistanceInches();
				distF = (int) frontSensor.getDistanceInches();
				distR = (int) rightSensor.getDistanceInches();
				
				// main logic for relay and side distance sensors
				switch (counter) {
					case 0: distW = (int) sideSensor.getDistanceInches(); relay.set(); break;
					case 1: distE = (int) sideSensor.getDistanceInches(); break;
					case 2: distE = (int) sideSensor.getDistanceInches(); relay.clear(); break;
					case 3: distW = (int) sideSensor.getDistanceInches(); break;
				}
				counter = ( counter + 1 ) % 4 ; // count to four
				
				// display testing output
				if (testing) { 
					display.print(0, "F:" + Integer.toString(distF) + " L:" + Integer.toString(distL) + " R:" + Integer.toString(distR));
					display.print(1, "W:" + Integer.toString(distW) + " E:" + Integer.toString(distE));
				}
				
				
			} catch( Throwable t ) { t.printStackTrace(); }
		}
		
	}
	
	public int getDist(char x) {
		switch(x) {
			case 'c': return distF; // center - front
			case 'l': return distL;
			case 'r': return distR;
			case 'w': return distW;
			case 'e': return distE;
			default: return 0;
		}
	}
}