/* CS 430: Senior Project 2012
 * 
 * This is the main controller class for the robot. This class exists mainly
 * to easily interface with the robot loading software in order to pick and 
 * choose what code the robot is running.  Therefore, at any given time, 
 * most of the classes that this Controller calls may be commented out.
 * 
 * This class forms the link between the data class and many of the operation
 * classes that rely on that data.
 */
/*
public class ShaftEncoderTestDriver {

	public static void main(String args[]) {
		ShaftEncoderTest localizer = new ShaftEncoderTest();
		localizer.run();
		localizer.setMotorPower(7, 7);
		
		try {
			while (true) {
				Pose temp = localizer.getPose();
				System.out.println("x:" + temp.x + " y:" + temp.y + " t:" + temp.t);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("Error in driver");
		}
	}
}
*/