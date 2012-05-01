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
	private Remote rem;
	
	// locally used for things
	private Display disp;
	private AnalogInput thumbwheel;
	private PushButton startButton;
	private PushButton stopButton;
	public Speaker buzzer;
	
	public Debugger(Engine e, SteeringWheel w, Sonar s, Intelligence i, GPS g, Remote r) {
		eng = e;
		wheel = w;
		son = s;
		intel = i;
		gps = g;
		rem = r;
		
		disp = IntelliBrain.getLcdDisplay();
		thumbwheel = IntelliBrain.getThumbWheel();
		startButton = IntelliBrain.getStartButton();
		stopButton = IntelliBrain.getStopButton();
		buzzer = IntelliBrain.getBuzzer();
	}

	public void run() {
		String[] data = {"",""}, nope = {"No Debug", "Nothing to show"};
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
					case 5:  data = rem  .toDebugString(new String[2]); showingNothing = false; break;
					case 6:  data[0] = "Engine PID"; data[1] = "Settings"; showingNothing = false; break;
					case 7:  data[0] = "Listen to"; data[1] = "user Driving"; showingNothing = false; break;
					case 8:  data[0] = "Remote Started"; data[1] = "Entertainment"; showingNothing = false; break;
					default: data = nope;
				}
				if (!showingNothing) { // only update screen when debugging
					disp.print(0, data[0]);
					disp.print(1, data[1]);
				}
				if (data == nope) showingNothing = true;
				
				// User chooses to debug the class
				if (startButton.isPressed()){
					buzzer.play(500, 50);
					// stop executing threads
					IntelliBrain.setTerminateOnStop(false);
					setAll(false, "Begin Debug", "Waiting Death", chosenOne);
					
					// Chose one of the debugging options
					switch ( chosenOne ) {
						case 0: debugEngine();   break;
						case 1: debugSteering(); break;
						// ... 
						case 4: debugGPS();      break;
						// ...
						case 6: debugPID();      break;
						case 7: listenDriving(); break;
						case 8: roboBomb();      break;
						default:
							//nope
					}
					
					// Starting regular execution
					buzzer.play(500, 50);
					setAll(true, "Debug Complete", "Resuming Operation", chosenOne);
					IntelliBrain.setTerminateOnStop(true);
				}
				
				lastOne = chosenOne;
				
				// Pause thread execution
				time += 1000;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}
	
	private void setAll(boolean run, String msg1, String msg2, int item) throws Throwable {
		disp.print(0, msg1);
		disp.print(1, msg2);
		
		if (item != 0 && item != 6 && item != 7 && item != 8) eng.setRunning(run); else eng.setSpeed(0);
		if (item != 1 && item != 7 && item != 8) wheel.setRunning(run); else wheel.setDirection(wheel.CENTERED);
		if (item != 2 && item != 7 && item != 99) son.setRunning(run);
		if (item != 3 && item != 8) intel.setRunning(run);
		if (item != 5 && item != 6 && item != 7 && item != 8 && item != 99) rem.setRunning(run);
		
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
	
	private void debugPID() {
		boolean debug = true;
		//String[] data;
		int var = 0;
		float value = 0.0f;
		String name = "pGain " + eng.pGain;
		
		long time = System.currentTimeMillis();
		while (debug) {
			try {
				if (startButton.isPressed()) {
					buzzer.play(500, 50);
					switch (var) {
						case 0 :
							eng.pGain += value;
							name = "iGain " + eng.pGain;
							break;
						case 1 :
							eng.iGain += value;
							name = "dGain " + eng.dGain;
							break;
						case 2 :
							eng.dGain += value;
							name = "iCapp " + eng.iCapp;
							break;
						case 3 :
							eng.iCapp += value;
							name = "pGain " + eng.pGain;
							break;
					}
					eng.updatePID();
					var = (var + 1) % 4;
				}
				
				value = (float) ((thumbwheel.sample() * 1.0 / 10000.0) - 0.05);
				
				//data = eng.toDebugString(new String[2]);
				disp.print(0, name);
				disp.print(1, "add: " + value);
				
				if (stopButton.isPressed()) debug = false;
				time += 500;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
	}

	private void listenDriving() throws Throwable {
		boolean debug = true, listening = true, firstTransfer = true;
		String[] learningData = {}, newData;
		int i = 0;
		
		intel.setRemoteStatus(true);
		
		disp.print(0, "Remote Driving");
		disp.print(1, "Learning in progress");
		
		Thread.sleep(1000);
		buzzer.play(500, 50);
		
		long time = System.currentTimeMillis();
		while (debug) {
			try {
			    
				// Building Array of training data!
				if (listening) {
					
					newData = new String[learningData.length + 1];
					for ( i = 0; i < learningData.length; i++ ) newData[i] = learningData[i];
					newData[i] = "{" + son.getDist('w') + ", " + 
							son.getDist('l') + ", " +
							son.getDist('c') + ", " +
							son.getDist('r') + ", " +
							son.getDist('e') + ", " +
							eng.getSpeed() + ", " +
							wheel.getFrontDirection() + ", " +
							wheel.getBackDirection() + "}";
					
					learningData = newData;
					
					if (startButton.isPressed()) {
						buzzer.play(500, 50);
						listening = false;
					}
					
					
				// Trasmitting array of training data!
				} else {
					disp.print(0, "Data Transfer");
					disp.print(1, "Take me home!");
					
					if (firstTransfer) { // shutdown and stop everything
						setAll(false, "Data Transfer", "Take me home!", 4);
						firstTransfer = false;
					}
					
					if (startButton.isPressed()) { //transfer data
						buzzer.play(500, 50);
						
						System.out.println("double[][] trainingData = {");
						for ( i = 0; i < learningData.length; i++ )
							System.out.println("\t" + learningData[i] +
								((i != learningData.length - 1) ? ",": "")
							);
						System.out.println("};");
						
						disp.print(0, "Data Transfer");
						disp.print(1, "Transfer Complete");
					}
				}
				
				if (stopButton.isPressed()) debug = false;
				time += 200;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable e) { e.printStackTrace(); }
		}
		
		intel.setRemoteStatus(false);
	}
	
	private void roboBomb() {
		Entertain mario = new Entertain();
		Thread music = null;
		
		intel.setRemoteStatus(true);
		
		boolean running = true, first = true, debug = true;
		
		disp.print(0, "Ready to Bomb!");
		disp.print(1, "Drive to terrain for me");
		
		long time = System.currentTimeMillis();
		while (debug) {
			try {
				time += 1000;
				Thread.sleep(time - System.currentTimeMillis());
				
				if  (!first && music == null && !rem.isOn()) { // start condition
					setAll(false, "Seranade time!", "Starting Mario", 99);
					
					music = new Thread(mario);
					music.setPriority(Thread.MIN_PRIORITY);
					music.start();
					
					// don't really want to run any other logic while mario is playing!
					running = true;
					while (running) {
						disp.print(0, "I LOVE YOU");
						disp.print(1, "Mario 4 U!");
						if (music != null && rem.isOn()) running = false;
						if (stopButton.isPressed()) {
							debug = false;
							running = false;
						}
						
						time += 1000;
						Thread.sleep(time - System.currentTimeMillis());
					}
					
					mario.stop();
					
					music = null;
					//mario = null;
					setAll(true, "Mario Killed", "Ending Mario", 99);
				} 
				if (first && rem.isOn()) first = false;
				if (stopButton.isPressed()) debug = false;
				
			} catch (Throwable t) { t.printStackTrace(); }
		}
		
		intel.setRemoteStatus(false);
	}
}
