import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.io.Speaker;
import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.PushButton;

public class Debugger implements Runnable {
	// links to debuggable classes
	private Engine eng;
	private SteeringWheel wheel;
	private Sonar son;
	private Intelligence intel;
	private GPS gps;
	
	// locally used for things
	private Display disp;
	private AnalogInput thumbwheel;
	private PushButton startButton;
	private PushButton stopButton;
	public Speaker buzzer;
	
	public Debugger(Engine e, SteeringWheel w, Sonar s, Intelligence i, GPS g) {
		eng = e;
		wheel = w;
		son = s;
		intel = i;
		gps = g;
		
		disp = IntelliBrain.getLcdDisplay();
		thumbwheel = IntelliBrain.getThumbWheel();
		startButton = IntelliBrain.getStartButton();
		stopButton = IntelliBrain.getStopButton();
		buzzer = IntelliBrain.getBuzzer();
	}

	public void run() {
		String[] data = null, nope = {"No Debug", "Nothing to show"};
		boolean showingNothing = false;
		int chosenOne = 0, lastOne = 0;
		
		long time = System.currentTimeMillis();
		while (true) {
			try {
				// main debug logic
				chosenOne = (int) (thumbwheel.sample() / 102.4);
				
				if (chosenOne != lastOne) buzzer.play(500, 50);
				
				switch ( chosenOne ) {
					case 0:  data = eng  .toDebugString(new String[2]); showingNothing = false; break;
					case 1:  data = wheel.toDebugString(new String[2]); showingNothing = false; break;
					case 2:  data = son  .toDebugString(new String[2]); showingNothing = false; break;
					case 3:  data = intel.toDebugString(new String[2]); showingNothing = false; break;
					case 4:  data = gps  .toDebugString(new String[2]); showingNothing = false; break;
					default: data = nope;
				}
				if (!showingNothing) { // only update screen when debugging
					disp.print(0, data[0]);
					disp.print(1, data[1]);
				}
				if (data == nope) showingNothing = true;
				
				// User chooses to debug the class
				if (startButton.isPressed()){
					
					// stop executing threads
					IntelliBrain.setTerminateOnStop(false);
					setAll(false, "Begin Debug", "Waiting Death");
					
					// Chose one of the debugging options
					switch ( chosenOne ) {
						case 0: debugEngine();   break;
						case 1: debugSteering(); break;
						// ... 
						case 4: debugGPS();      break;
					}
					
					// Starting regular execution
					setAll(true, "Debug Complete", "Resuming Operation");
					IntelliBrain.setTerminateOnStop(true);
				}
				
				lastOne = chosenOne;
				
				// Pause thread execution
				time += 1000;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
	
	private void setAll(boolean run, String msg1, String msg2) throws Throwable {
		disp.print(0, msg1);
		disp.print(1, msg2);
		
		intel.setRunning(run);
		son.setRunning(run);
		eng.setRunning(run);
		wheel.setRunning(run);
		
		Thread.sleep(2000);
	}
	
	private void debugEngine() {
		boolean debug = true;
		long time = System.currentTimeMillis();
		int power;
		String[] data;
		
		while (debug) {
			try {
				
				power = (thumbwheel.sample() - 512) / 31;
				eng.setSpeed(power);
				
				data = eng.toDebugString(new String[2]);
				data[0] = "Pos:" + power;
				disp.print(0, data[0]);
				disp.print(1, data[1]);
				
				if (stopButton.isPressed()) debug = false;
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable t) { t.printStackTrace(); }
		}
	}
	
	private void debugSteering() {
		boolean debug = true;
		int dir = 0;
		String[] data;
		
		long time = System.currentTimeMillis();
		while (debug) {
			try {
				
				dir = thumbwheel.sample() / 10;
			    wheel.setDirection(dir);
			    
			    data = wheel.toDebugString(new String[2]);
			    data[0] = "Set:" + dir;
				disp.print(0, data[0]);
				disp.print(1, data[1]);
			    
			    if (stopButton.isPressed()) debug = false;
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
	
	private void debugGPS() {
		boolean debug = true;
		String[] data;
		
		long time = System.currentTimeMillis();
		while (debug) {
			try {
			    
			    data = gps.toDebugString(new String[2]);
				disp.print(0, data[0]);
				disp.print(1, data[1]);
			    
			    if (stopButton.isPressed()) debug = false;
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
}
