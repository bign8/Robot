import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.PushButton;

public class Debugger implements Runnable {
	// links to debuggable classes
	private Engine eng;
	private SteeringWheel wheel;
	private Sonar son;
	private Intelligence intel;
	private GPS gps;
	
	// Threads that need to stop
	private Thread brainThr;
	
	// locally used for things
	private Display disp;
	private AnalogInput thumbwheel;
	private PushButton startButton;
	private PushButton stopButton;
	
	public Debugger(Engine e, SteeringWheel w, Sonar s, Intelligence i, GPS g, Thread bThr) {
		eng = e;
		wheel = w;
		son = s;
		intel = i;
		gps = g;
		
		brainThr = bThr;
		
		disp = IntelliBrain.getLcdDisplay();
		thumbwheel = IntelliBrain.getThumbWheel();
		startButton = IntelliBrain.getStartButton();
		stopButton = IntelliBrain.getStopButton();
	}

	public void run() {
		String[] data = null, nope = {"No Debug", "Nothing to show"};
		boolean showingNothing = false;
		int chosenOne = 0;
		
		long time = System.currentTimeMillis();
		while (true) {
			try {
				// main debug logic
				chosenOne = (int) (thumbwheel.sample() / 102.4);
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
					IntelliBrain.setTerminateOnStop(false);
					
					// stop executing threads
					intel.stop();
					disp.print(0, "Begin Debug");
					disp.print(1, "Waiting Death");
					Thread.sleep(2000);
					
					// Chose one of the debugging options
					switch ( chosenOne ) {
						case 0: debugEngine(); break;
					}
					
					// Starting regular execution
					disp.print(0, "Debug Complete");
					disp.print(1, "Resuming Operation");
					IntelliBrain.setTerminateOnStop(true);
					Thread.sleep(5000);
					brainThr.start();
				}
				
				// Pause thread execution
				time += 1000;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
	
	public void debugEngine() {
		boolean debug = true;
		long time = System.currentTimeMillis();
		while (debug) {
			try {
				
				if (stopButton.isPressed()) debug = false;
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable t) {t.printStackTrace();}
		}
	}
}
