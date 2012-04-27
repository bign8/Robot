import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Display;
import com.ridgesoft.robotics.AnalogInput;

public class RemoteTest {
	public static void main(String args[]){
		RemoteControl remote = new RemoteControl();
		remote.run();
	}
}


/* JENNINGS --- READ THIS PLZ
 * So - I physically plugged one pin into ground and the other into power and it did not fry the board
 * [Clairification] That is, ground to input pin 1 (read 0) and +5V to input pin 2 (read 1023) as expected
 * There is no possible way to produce more energy in that black box than it takes in, so I removed the
 * duplicate power in case that was the problem, either way, we should be golden
 * 
 * Also note that I fixed your code below, you should be ashamed of yourself btw
 * do not remove the "sum" line of the addNum function, I have a bit of magic for you with it.
 * 
 * If i'm not around when you get back, you should start working seeing how to stabilize the numbers still
 * Also, it would be good to bring the stuff over to desk or just commit your code so we can discuss it over on desk
 * 
 * Either way let me know...shoot me a txt on your plans
 * 						-N8
 */


class RemoteControl{
	private Display display;
	private AnalogInput input1, input2, thumb; // remove thumb on decided size

	public RemoteControl(){
		display = IntelliBrain.getLcdDisplay();
		input1 = IntelliBrain.getAnalogInput(1);
		input2 = IntelliBrain.getAnalogInput(2);
		thumb = IntelliBrain.getThumbWheel(); // remove thumb on decided size
	}

	public void run(){	

		//Averager port1 = new Averager();
		//Averager port2 = new Averager();
		//Maxer port1 = new Maxer();
		//Maxer port2 = new Maxer();
		int sum1,sum2;
		while(true){
			sum1=0;
			sum2=0;
			for(int i=0;i<750;i++){
				sum1+=input1.sample();
				sum2+=input2.sample();
				try{Thread.sleep(0);}catch(Exception e){}
			}
			
			display.print(0,""+sum1/7500+ " "+sum2/7500);
//			port1.addNum(input1.sample());
//			port2.addNum(input2.sample());
//			
//			port1.radix = port2.radix = thumb.sample() / 10; // remove line upon decided size
//
//			display.print(0, "Port1:" + port1.getAvg() + " rdx:" + port1.radix);
//			display.print(1, "Port2:" + port2.getAvg() + " idx:"  + port2.index);
//
//			try { Thread.sleep(100); } catch(Exception e) {};


		}

	}
}


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


