import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.Servo;
import com.ridgesoft.io.Display;



/*
 * This class controls the engine and wheel power.
 */

public class Engine implements Runnable{

	private Servo motor;
	
	Display display;
	

	public final int STOP = 40;
	public final int MAX_FORWARD = 127;
	public final int MED_FORWARD = 55;
	public final int MIN_FORWARD = 50;
	public final int MAX_REVERSE = 0;
	public final int MIN_REVERSE = 35;
	
	public Engine(){
		//Get hardware control
		motor = IntelliBrain.getServo(3);

		//Create the Speedometer
		//Thread speed = new Thread(new Speedometer));
		//speed.setPriority(Thread.MAX_PRIORITY-1);
		//speed.start();

	}
	
	public void run(){
		
	}
	
	//Drive forward for specific duration of seconds.
	/*
	public void easeForward(int time){
		try{
			motor.setPosition(MIN_FORWARD);
			Thread.sleep(time*1000);
			motor.setPosition(STOP);
		}catch(Exception e){}

	}
	//*/
	
	public void setPower(int velocity) {
		motor.setPosition(velocity);
	}
	
	public void reverse(int time){
		
	}
	
	public Servo getEngine(){
		return motor;
	}
}