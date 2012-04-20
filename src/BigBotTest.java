import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.AnalogInput;
//import com.ridgesoft.robotics.ContinuousRotationServo;
import com.ridgesoft.robotics.Servo;
//import com.ridgesoft.robotics.Motor;
//import com.ridgesoft.robotics.SonarRangeFinder;
//import com.ridgesoft.robotics.sensors.ParallaxPing;

public class BigBotTest {

	private Servo steeringWheel;
	//private Motor engine;
	private AnalogInput dial;//, remote;
	private Display display;
	//protected static SonarRangeFinder frontSensor;


	public void go() {
		steeringWheel = IntelliBrain.getServo(2);
		//engine = new ContinuousRotationServo( IntelliBrain.getServo(1), true);

		try{
			display = IntelliBrain.getLcdDisplay();
			dial =  IntelliBrain.getThumbWheel();
			//remote = IntelliBrain.getAnalogInput(1);
			//frontSensor = new ParallaxPing(IntelliBrain.getDigitalIO(3));

			//Use thumbwheel to initialize Wheel Position
			//--TODO--//

			//Initialization
			//int dist = 0;
			int count = 1;
			

			while(true){
				
				//engine.setPower(Motor.MAX_FORWARD);
				
				//frontSensor.ping();
				Thread.sleep(100);

				display.print(0, "Pos: " + Integer.toString( dial.sample()/10)+" : "+count);
				//display.print(1, "Dist: " + Integer.toString( dist ));
				//display.print(1, Integer.toString(remote.sample()));
				steeringWheel.setPosition(dial.sample()/10);

				//dist = (int) frontSensor.getDistanceInches();

				//engine.setPower(Motor.MAX_FORWARD);
				//Thread.sleep(1000);
				//engine.setPower(Motor.STOP);
				
				/*
				if (dist > 30 ){
					engine.setPower(Motor.MAX_FORWARD);
				}

				else if (dist < 30 ){
					engine.setPower(Motor.STOP);
				}
				*/

			}//end while
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}