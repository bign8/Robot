/* CS 430: Senior Project 2012
 * 
 * This is the main controller class for the robot. 
 * 
 * It runs each class as a thread 
 */

public class Controller {

	public static void main(String args[]) {
		
		//Ignition
		Engine engine = new Engine();
		Thread engineThr = new Thread(engine);
		engineThr.setPriority(Thread.MAX_PRIORITY-3);
		engineThr.start();
		
		// Steering wheel
		SteeringWheel wheel = new SteeringWheel();
		Thread wheelThr = new Thread(wheel);
		wheelThr.setPriority(Thread.MAX_PRIORITY-4);
		wheelThr.start();
		
		// Headlights
		Sonar headlight = new Sonar();
		Thread headlightThr = new Thread(headlight);
		headlightThr.setPriority(Thread.MAX_PRIORITY-1);
		headlightThr.start();
		
		// GPS
		GPS garmin = new GPS();
		//Thread garminThr = new Thread(garmin);
		//garminThr.setPriority(Thread.MAX_PRIORITY-5);
		//garminThr.start();
		
		//Remote
		Remote remote = new Remote();
		Thread remoteThr = new Thread(remote);
		remoteThr.setPriority(Thread.MAX_PRIORITY);
		remoteThr.start();
		
		// Brain
		Intelligence brain = new Intelligence(engine, wheel, headlight, remote);
		Thread brainThr = new Thread(brain);
		brainThr.setPriority(Thread.MAX_PRIORITY-2);
		brainThr.start();
		
		// Debugging
		Debugger debug = new Debugger(engine, wheel, headlight, brain, garmin, remote);
		Thread debugThr = new Thread(debug);
		debugThr.setPriority(Thread.MAX_PRIORITY-5);
		debugThr.start();
	}
}
