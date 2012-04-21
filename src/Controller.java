/* CS 430: Senior Project 2012
 * 
 * This is the main controller class for the robot. This class exists mainly
 * to easily interface with the robot loading software in order to pick and 
 * choose what code the robot is running.  Therefore, at any given time, 
 * most of the classes that this Controller calls may be commented out.
 * 
 * This class forms the link between the data class and many of the operation
 * classes that rely on that data.
 * 
 * It runs each class as a thread 
 */

public class Controller {
	
	public static void main(String args[]) {
		
		//Ignition
		Engine engine = new Engine(false); // need to run as thread once spedometer is in place
		Thread engineThr = new Thread(engine);
		engineThr.setPriority(Thread.MAX_PRIORITY-2);
		engineThr.start();
		
		//starter(new Thread( new Tester(engine) ), Thread.MAX_PRIORITY); // uncomment engine tester
		
		// Steering wheel
		SteeringWheel wheel = new SteeringWheel();
		
		starter(new Thread( new Tester(wheel) ), Thread.MAX_PRIORITY);
		
		// Start Calibration if needed
		//new Calibration(engine, wheel);
		
		/*
		
		// Headlights
		Sonar headlight = new Sonar(true);
		Thread headlightThr = new Thread(headlight);
		headlightThr.setPriority(Thread.MAX_PRIORITY);
		headlightThr.start();
		
		// Brain
		Intelligence brain = new Intelligence(engine, wheel, headlight);
		Thread brainThr = new Thread(brain);
		brainThr.setPriority(Thread.MAX_PRIORITY-1);
		brainThr.start();
		//*/
	}
	
	public static void starter(Thread x, int priority) {
		x.setPriority(priority);
		x.start();
	}
}