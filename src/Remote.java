import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.AnalogInput;

public class Remote implements Runnable, Debuggable {
	private boolean running;
	private AnalogInput input1, input2;
	private boolean remoteOn;
	private int port1, port2;

	public Remote(){
		input1 = IntelliBrain.getAnalogInput(2);
		input2 = IntelliBrain.getAnalogInput(3);

	}

	public void run(){
		while(true){
			port1 = input1.sample()/5 - 11;
			port2 = input2.sample()/5 - 11;
			if ((port1 < -5 )  || ( port2 < -5))
				remoteOn = false;
			else
				remoteOn = true;
			try { Thread.sleep(500); } catch(Exception e) {};
		}
	}
	public boolean isOn(){
		return remoteOn;
	}
	public int getPort1(){
		return port1;
	}
	public int getPort2(){
		return port2;
	}
	
	public String[] toDebugString(String in[]) {
		in[0] = "Remote Debug";
		String onOff = (isOn())? "On": "Off";
		in[1] = onOff+ " "+getPort1() + " " + getPort2();
		return in;
	}

	public void setRunning(boolean run) {
		running = run;
	}
}
