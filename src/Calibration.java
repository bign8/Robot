//import com.ridgesoft.robotics.Servo;
/*
import com.ridgesoft.io.Display;
import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.PushButton;

public class Calibration {
	
	public static Engine engine;
	public static SteeringWheel wheel;
	public static Display display;
	public static PushButton startButton;
	public static PushButton stopButton;
	
	public Calibration(Engine configurationEngine, SteeringWheel configurationWheel){
		engine = configurationEngine;
		wheel = configurationWheel;
		startButton = IntelliBrain.getStartButton();
		stopButton = IntelliBrain.getStopButton();
		display = IntelliBrain.getLcdDisplay();
		
		IntelliBrain.setTerminateOnStop(false);
		boolean calibrate = true;
		while(calibrate){
			display.print(0, "START=Begin");
			display.print(1, "STOP=Calibrate");
			
			if (startButton.isPressed()){
				calibrate = false;
			}
			if (stopButton.isPressed()){
				execute();
				calibrate = false;
			}
		}
		IntelliBrain.setTerminateOnStop(true);
		
	}
	
	public void execute() {
		display.print(0, "PICK UP ROBOT");
		try{
			Thread.sleep(2000); // pick it up
			for(int i=0; i<2; i++){
				
				// setup straight on
				display.print(0, "Straight + Slow Forward");
				wheel.setDirection(wheel.CENTERED);
				engine.setPower(engine.MIN_FORWARD);
				Thread.sleep(1000);
				
				// setup left
				display.print(0, "Left + Slow Reverse");
				wheel.setDirection(wheel.FULL_LEFT);
				engine.setPower(engine.MIN_REVERSE);
				Thread.sleep(1000);
				
				// setup right
				display.print(0, "Right + Max Forward");
				wheel.setDirection(wheel.FULL_RIGHT);
				engine.setPower(engine.MAX_FORWARD);
				Thread.sleep(1000);
				
			}
			
			// complete calibration
			engine.setPower(engine.STOP);
			wheel.setDirection(wheel.CENTERED);
			display.print(0, "Calibration Complete");
			display.print(1, "SET ME DOWN MEOW!");
			Thread.sleep(3000);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
//*/