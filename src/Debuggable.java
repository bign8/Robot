/*
 * This interface allows a constant implementation of runnable thread
 * A better implementation would be to have an abstract interface that all the classes could extend
 * Unfortunantly, its a day away from the due date, so this is how its going to stay
 */

public interface Debuggable {
	public void setRunning( boolean run );
	public String[] toDebugString(String in[]);
}

// NOT IMPLEMENTED, but Briliant!
abstract class RobotThread extends Thread implements Debuggable, Runnable { // other classes would extend this!
	
	private boolean running = true;
	private int delay = 100;
	public void setRunning(boolean run) { running = run; }
	public void setDelay(int del) { delay = del; }

	public void run() {
		long time = System.currentTimeMillis();
		while (true) {
			
			if (running) robotRun(); else robotStop();
			
			try {
				time += delay;
				Thread.sleep(time - System.currentTimeMillis());
			} catch (Throwable t) { t.printStackTrace(); }
		}
	}
	
	abstract public void robotRun(); // single execution function when thread is running
	abstract public void robotStop(); // single execution function when thread in stopped
	abstract public String[] toDebugString(String[] in);
}