//Note that these imports require the RoboJDE.jar file
//to exist in the build path.
import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.Servo;
import com.ridgesoft.io.Display;



public class Debug {
	public static AnalogInput lineSensor = IntelliBrain.getAnalogInput(4);
	public static AnalogInput dial = IntelliBrain.getThumbWheel();
	public static Display display = IntelliBrain.getLcdDisplay();
	public static Servo steer = IntelliBrain.getServo(1);
	
	public static int s;
	
	public static void main(String Args[]) { // Main executing thread

		try {	//All robot control may throw an exception

			s = lineSensor.sample();

			while (true){ //DIRECTION == "straight") {
				
				display.print(0, "S:" + Integer.toString(s) + " D:" + Integer.toString(dial.sample()/10));
				//steer.setPosition(dial.sample()/10);

				steer.setPosition(50);
				
				
				//turnHardLeft();
				//Thread.sleep(3000);
				
				//turnHardRight();
				//Thread.sleep(3000);
				
				//turnStraight();
				//Thread.sleep(3000);
				
				//turnHardRight();
				//Thread.sleep(3000);
				
				//turnStraight();
				//Thread.sleep(3000);
							}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	
	public static void turnHardLeft(){
		steer.setPosition(0);
	}
	
	public static void turnHardRight(){
		steer.setPosition(100);
	}
	
	public static void turnStraight( /*This should receive some state information */ ){
		s = lineSensor.sample();
		
		display.print(0, "S:" + Integer.toString(s) + " D:" + Integer.toString(dial.sample()/10));
		//steer.setPosition(dial.sample()/10);
		
		steer.setPosition(50);
		try{Thread.sleep(2000);}catch(Exception e){}
		
		int right = 51;
		int left = 49;
		
		while( (s<50) || s>200){

			if (s>200){
				steer.setPosition(right++);
			}

			if (s<50){
				steer.setPosition(left--);
			}

			if (right>75){
				right=60;
			}
			if (left < 25){
				left = 40;
			}
			display.print(1, Integer.toString(left) + " " + Integer.toString(right));
		}
	}
}
