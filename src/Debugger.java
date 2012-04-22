import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.AnalogInput;

public class Debugger implements Runnable {
	private Engine eng;
	private SteeringWheel wheel;
	private Sonar son;
	private Intelligence intel;
	
	public Debugger(Engine e, SteeringWheel w, Sonar s, Intelligence i) {
		eng = e;
		wheel = w;
		son = s;
		intel = i;
	}

	public void run() {
		String[] data = null, nope = {"No Debug", "Nothing to show"};
		boolean showingNothing = false;
		AnalogInput thumbwheel = IntelliBrain.getThumbWheel();
		Display disp = IntelliBrain.getLcdDisplay();
		
		long time = System.currentTimeMillis();
		while (true) {
			try {
				// main debug logic
				switch ( (int) (thumbwheel.sample() / 102.4) ) {
					case 0:  data = eng  .toDebugString(new String[2]); showingNothing = false; break;
					case 1:  data = wheel.toDebugString(new String[2]); showingNothing = false; break;
					case 2:  data = son  .toDebugString(new String[2]); showingNothing = false; break;
					case 3:  data = intel.toDebugString(new String[2]); showingNothing = false; break;
					default: data = nope;
				}
				if (!showingNothing) { // only update screen when debugging
					disp.print(0, data[0]);
					disp.print(1, data[1]);
				}
				if (data == nope) showingNothing = true;
				
				// Pause thread execution
				time += 1000;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
}