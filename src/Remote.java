import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.robotics.AnalogInput;
import com.ridgesoft.robotics.Smoother;

/*
 * Note: This class is designed for reading input from an "AR-1 2 channel reciever with reverse" from axial
 * This device usally is directly connected to the servos, and thus produces a square wave
 * The frequency of this square wave is to fast for the Intellibrain 2 processor to 'count' the lenght of a set or cleared bit
 * This problem was fixed using a low pass filter that was turned the square wave into a mostly linear function we could sample
 * 
 * X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X
 * X                                     X                                           X                                           X
 * X    o-----/\/\/\/\------+-------o    X    V |   +---+             +---+          X    V |                                    X
 * X          15 k Ohm      |            X      |   |   |             |   |          X      |                                    X
 * X                      --+--          X      |   |   |     in      |   |          X      |               out                  X
 * X   in                 10 uF    out   X      |   |   |             |   |          X      |                                    X
 * X                      --+--          X      |   |   |             |   |          X      |--------------------------------    X
 * X                        |            X      |---+   +-------------+   +------    X      |                                    X
 * X    o-------------------+-------o    X      0--------------------------------    X      0--------------------------------    X
 * X                                     X                                           X                                           X
 * X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X X
 */

public class Remote implements Runnable, Debuggable {
	
	// Port Reservations
	private static final int AnologueInput1  = 2;
	private static final int AnologueInput2  = 3;
	
	// Algorithm available variables
	private boolean running = true, remoteOn = false;
	private AnalogInput input1, input2;
	private int port1, port2;
	private Smoother sampleP1, sampleP2;

	// Remote Constructor - optain analog input ports and initiate smoother
	public Remote(){
		input1 = IntelliBrain.getAnalogInput(AnologueInput1);
		input2 = IntelliBrain.getAnalogInput(AnologueInput2);
		
		sampleP1 = new Smoother(0.2f, input1.sample());
		sampleP2 = new Smoother(0.2f, input2.sample());
	}

	// Main running thread - recieve data from remote and smooth
	public void run(){
		int temp1, temp2;
		long time = System.currentTimeMillis();
		
		while(true){
			if (running) {
				// TODO look at getting wider data ranges
				
				temp1 = input1.sample();
				temp2 = input2.sample();
				
				remoteOn = ( temp1 > 35 ) && ( temp2 > 35 ) && ( temp1 < 85 ) && ( temp2 < 85 );
				
				port1 = (int) ( sampleP1.smooth(temp1) / 5 - 12 );
				port2 = (int) ( sampleP2.smooth(temp2) / 5 - 12 );
				
			} else {
				remoteOn = false;
			}
			
			try {
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			} catch(Exception e) {};
		}
	}
	
	// Debugging methods and accessors
	public boolean isOn(){ return remoteOn; }
	public int getPort1(){ return port1; }
	public int getPort2(){ return port2; }
	public void setRunning(boolean run) { running = run; }
	public String[] toDebugString(String in[]) {
		in[0] = "Remote Debug";
		in[1] = ( remoteOn ? "On" : "Off" ) + " " + port1 + " " + port2;
		return in;
	}
}
