import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.AnalogInput;

public class Remote implements Runnable, Debuggable {
	private boolean running;
	private AnalogInput input1, input2;
	private boolean remoteOn;
	private int port1, port2;
	private int sampleP1, sampleP2;

	public Remote(){
		input1 = IntelliBrain.getAnalogInput(2);
		input2 = IntelliBrain.getAnalogInput(3);
	}

	public void run(){
		long time = System.currentTimeMillis();
		while(true){
			
			sampleP1 = input1.sample();
			sampleP2 = input2.sample();
			
			sampleP1 += input1.sample();
			sampleP2 += input2.sample();
			
			sampleP1 += input1.sample();
			sampleP2 += input2.sample();
			
			port1 = sampleP1/15 - 12; // origionally divided by 5
			port2 = sampleP2/15 - 12;
			
			remoteOn = (port1 > -5 ) && ( port2 > -5);
			
			try {
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			} catch(Exception e) {};
		}
	}
	
	public String[] toDebugString(String in[]) {
		in[0] = "Remote Debug";
		in[1] = ( isOn() ? "On" : "Off" ) + " " + port1 + " " + port2;
		return in;
	}

	public boolean isOn(){ return remoteOn; }
	public int getPort1(){ return port1; }
	public int getPort2(){ return port2; }
	public void setRunning(boolean run) { running = run; }
}
