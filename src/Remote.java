import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.Smoother;

public class Remote implements Runnable, Debuggable {
	private boolean running = true; // important
	private AnalogInput input1, input2;
	private boolean remoteOn;
	private int port1, port2;
	
	private Smoother sampleP1, sampleP2;

	public Remote(){
		input1 = IntelliBrain.getAnalogInput(2);
		input2 = IntelliBrain.getAnalogInput(3);
		
		sampleP1 = new Smoother(0.2f, input1.sample());
		sampleP2 = new Smoother(0.2f, input2.sample());
	}

	public void run(){
		long time = System.currentTimeMillis();
		while(true){
			if (running) {
				// TODO look at getting wider data ranges
				
				port1 = (int) (sampleP1.smooth(input1.sample())/5 - 12);
				port2 = (int) (sampleP2.smooth(input2.sample())/5 - 12);
				
				remoteOn = ( port1 > -5 ) && ( port2 > -5 ) && ( port1 < 5 ) && ( port2 < 5 ); // added cap - absolutely necessary!
			}
			
			try {
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			} catch(Exception e) {};
		}
	}
	
	public String[] toDebugString(String in[]) {
		in[0] = "Remote Debug";
		in[1] = ( remoteOn ? "On" : "Off" ) + " " + port1 + " " + port2;
		return in;
	}

	public boolean isOn(){ return remoteOn; }
	public int getPort1(){ return port1; }
	public int getPort2(){ return port2; }
	public void setRunning(boolean run) { running = run; }
}
