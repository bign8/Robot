/* CS 430: Senior Project 2012
 * 
 * This is the main controller class for the robot. 
 * 
 * It runs each class as a thread 
 */

public class Controller {

	public static void main(String args[]) {
		Speedometer.run(null);
		/*
		//Ignition
		Engine engine = new Engine();
		Thread engineThr = new Thread(engine);
		engineThr.setPriority(Thread.MAX_PRIORITY-2);
		engineThr.start();
		//starter(new Thread( new Tester(engine) ), Thread.MAX_PRIORITY); // uncomment engine tester
		
		// Steering wheel
		SteeringWheel wheel = new SteeringWheel();
		Thread wheelThr = new Thread(wheel);
		wheelThr.setPriority(Thread.MAX_PRIORITY-3);
		wheelThr.start();
		//starter(new Thread( new Tester(wheel) ), Thread.MAX_PRIORITY); // uncomment wheel tester
		
		// Headlights
		Sonar headlight = new Sonar();
		Thread headlightThr = new Thread(headlight);
		headlightThr.setPriority(Thread.MAX_PRIORITY);
		headlightThr.start();
		
		// GPS
		GPS garmin = new GPS();
		Thread garminThr = new Thread(garmin);
		garminThr.setPriority(Thread.MAX_PRIORITY-4);
		garminThr.start();
		
		//Remote
		Remote remote = new Remote();
		Thread remoteThr = new Thread(remote);
		remoteThr.setPriority(Thread.MAX_PRIORITY);
		remoteThr.start();
		
		// Brain
		Intelligence brain = new Intelligence(engine, wheel, headlight, remote);
		Thread brainThr = new Thread(brain);
		brainThr.setPriority(Thread.MAX_PRIORITY-1);
		brainThr.start();
		
		// Debugging - can comment out for production
<<<<<<< HEAD
		Debugger debug = new Debugger(engine, wheel, headlight, brain, garmin, brainThr);
=======
		Debugger debug = new Debugger(engine, wheel, headlight, brain, garmin, remote);
>>>>>>> refs/heads/master
		Thread debugThr = new Thread(debug);
		debugThr.setPriority(Thread.MIN_PRIORITY);
		debugThr.start();
		//*/
	}

	public static void starter(Thread x, int priority) { // do not use for anything but the tester
		x.setPriority(priority);
		x.start();
	}
}
