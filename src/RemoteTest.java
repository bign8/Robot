import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.AnalogInput;

public class RemoteTest {
	public static void main(String args[]){
		RemoteControl remote = new RemoteControl(true);
		remote.run();
	}
}

class RemoteControl{
	private Display display;
	private AnalogInput input1, input2, thumb; // remove thumb on decided size
	private boolean debug;
	private boolean remoteOn;

	public RemoteControl(boolean debug){
		display = IntelliBrain.getLcdDisplay();
		input1 = IntelliBrain.getAnalogInput(1);
		input2 = IntelliBrain.getAnalogInput(2);
		thumb = IntelliBrain.getThumbWheel(); // remove thumb on decided size
		this.debug = debug;
	}

	public void run(){
		int r1, r2;
		while(true){
			r1 = input1.sample()/5 - 11;
			r2 = input2.sample()/5 - 11;
			if ((r1 < -5 )  || ( r2 < -5))
				remoteOn = false;
			else
				remoteOn = true;
			if (debug){
				display.print(0, "Port1: "+r1 + " "+isOn());
				display.print(1, "Port2: "+r2);
			}
			try { Thread.sleep(500); } catch(Exception e) {};
		}
	}
	public boolean isOn(){
		return remoteOn;
	}
}


//Nate's Logic (It was good, but not applicable to this..)
//port1.radix = port2.radix = thumb.sample() / 10; // remove line upon decided size
//
//display.print(0, "Port1:" + port1.getAvg() + " rdx:" + port1.radix);
//display.print(1, "Port2:" + port2.getAvg() + " idx:"  + port2.index);
/*
class Averager{
	public int nums[] = new int[50], index = 0, radix = 50, sum = 0; // radix will be converted to nums.length after testing

	public void addNum(int n){
		sum += n - nums[index]; // DO NOT REMOVE - SEE REASONS ABOVE
		nums[index++] = n;
		index %= radix;
	}

	public int getAvg() { // remove for loop once we decide on a size and radix is not dynamic
		int sum=0;
		for(int i=0; i < radix; i++) sum+= nums[i];
		return sum / radix;	
	}
}
*/

