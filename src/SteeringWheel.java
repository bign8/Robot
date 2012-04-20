import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.Servo;

public class SteeringWheel implements Runnable { // combine with engine to create 'driveTrain'

	private Servo frontWheels;
	private Servo backWheels;
	private int fDirection = 0, bDirection = 0;
	
	public final int FULL_LEFT = 0;
	public final int HALF_LEFT = 25;
	public final int FULL_RIGHT = 100;
	public final int HALF_RIGHT = 75;
	public final int CENTERED = 50;
	
	public SteeringWheel() {
		frontWheels = IntelliBrain.getServo(1);
		backWheels = IntelliBrain.getServo(2);
		frontWheels.setPosition(CENTERED);
		backWheels.setPosition(CENTERED);
	}


	public void run() {
		// will be implemented once steering sensors are in place
	}
	
	public void setDirection(int direction){
		frontWheels.setPosition(direction);
		backWheels.setPosition(100-direction);
		fDirection = direction;
		bDirection = direction;
	}
	
	public void setFrontWheels(int direction) {
		frontWheels.setPosition(direction);
		fDirection = direction;
	}
	
	public void setBackWheels(int direction) {
		backWheels.setPosition(direction);
		bDirection = direction;
	}
	
	public int getFrontDirection() { return fDirection; }
	public int getBackDirection() { return bDirection; }
	public Servo getFrontWheels(){ return frontWheels; } // nothing should have controll of servos besides this class
	public Servo getBackWheels(){ return backWheels; }
}