/*
 * This interface allows a constant implementation of runnable thread
 * A better implementation would be to have an abstract interface that all the classes could extend
 * Unfortunantly, its a day away from the due date, so this is how its going to stay
 */

public interface Debuggable {
	public void setRunning( boolean run );
	public String[] toDebugString(String in[]);
}


class RobotThread extends Thread implements Debuggable {

	public void setRunning(boolean run) {
		// TODO Auto-generated method stub
		
	}

	public String[] toDebugString(String[] in) {
		// TODO Auto-generated method stub
		return null;
	}
	
}