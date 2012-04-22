import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.Servo;

public class SteeringWheel implements Runnable {

	public final int FULL_LEFT = 0;
	public final int HALF_LEFT = 25;
	public final int FULL_RIGHT = 100;
	public final int HALF_RIGHT = 75;
	public final int CENTERED = 50;
	
	private Servo frontWheels;
	private Servo backWheels;
	private int fDirection = CENTERED, bDirection = CENTERED;
	
	public SteeringWheel() {
		frontWheels = IntelliBrain.getServo(1);
		backWheels = IntelliBrain.getServo(2);
		try {
			
			frontWheels.setPosition(FULL_RIGHT);
			backWheels.setPosition(FULL_RIGHT);
			
			Thread.sleep(500);
			
			frontWheels.setPosition(FULL_LEFT);
			backWheels.setPosition(FULL_LEFT);
			
			Thread.sleep(500);
			
			frontWheels.setPosition(CENTERED);
			backWheels.setPosition(CENTERED);
			
			Thread.sleep(500);
			
		} catch (Throwable t) { t.printStackTrace(); }
	}

	public void run() {
		long time = System.currentTimeMillis();
		while (true) {
			try {
				frontWheels.setPosition(fDirection);
				backWheels.setPosition(bDirection);
				
				// TODO auto correct rear steering straight based on back sensor
			
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
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
		direction = 100 - direction;
		backWheels.setPosition(direction);
		bDirection = direction;
	}
	
	public int getFrontDirection() { return fDirection; }
	public int getBackDirection() { return bDirection; }
	
	public String[] toDebugString(String in[]) {
		in[0] = "Steering Debug";
		in[1] = "Fro:" + Integer.toString(fDirection) + " Bak:" + Integer.toString(bDirection);
		return in;
	}
}