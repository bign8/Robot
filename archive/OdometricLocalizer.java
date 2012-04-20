import com.ridgesoft.robotics.Localizer;
import com.ridgesoft.robotics.Pose;
import com.ridgesoft.robotics.ShaftEncoder;


public class OdometricLocalizer extends Thread implements Localizer { 
	private static final float PI = 3.14159f; 
	private static final float TWO_PI = PI * 2.0f;
	
	private ShaftEncoder leftEncoder; 
	private ShaftEncoder rightEncoder;
	
	private int previousLeftCounts; 
	private int previousRightCounts;
	
	private float distancePerCount; 
	private float radiansPerCount;
	
	private float x = 0.0f; 
	private float y = 0.0f; 
	private float heading = 0.0f;
	
	private int period;
	
	public OdometricLocalizer( 
				ShaftEncoder leftEncoder, 
				ShaftEncoder rightEncoder, 
				float wheelDiameter, 
				float trackWidth, 
				int countsPerRevolution, 
				int threadPriority, 
				int period){ 
		this.leftEncoder = leftEncoder; 
		this.rightEncoder = rightEncoder; 
		distancePerCount = (PI * wheelDiameter) / (float)countsPerRevolution; 
		radiansPerCount = PI * (wheelDiameter / trackWidth) / countsPerRevolution; 
		this.period = period; 
		previousLeftCounts = leftEncoder.getCounts(); 
		previousRightCounts = rightEncoder.getCounts(); 
		setDaemon(true); 
		setPriority(threadPriority); 
		start(); 
	}
	
	public synchronized Pose getPose() { 
		return new Pose(x, y, heading);
	}
	
	public void run() {
		try { 
			long nextTime = System.currentTimeMillis(); 
			while (true) { 
				int leftCounts = leftEncoder.getCounts(); 
				int rightCounts = rightEncoder.getCounts(); 
				
				int deltaLeft = leftCounts - previousLeftCounts; 
				int deltaRight = rightCounts - previousRightCounts;
				
				float deltaDistance = 0.5f * (float)(deltaLeft + deltaRight) * distancePerCount; 
				float deltaX = deltaDistance * (float)Math.cos(heading); 
				float deltaY = deltaDistance * (float)Math.sin(heading);
				float deltaTheta = (float)(deltaRight - deltaLeft) * radiansPerCount; 
				
				synchronized (this) { 
					x += deltaX; 
					y += deltaY; 
					heading += deltaTheta;
					
					// limit theta to -Pi <= theta < Pi 
					if (heading > PI) 
						heading -= TWO_PI; 
					else if (heading <= -PI) 
						heading += TWO_PI; 
				} 
				
				previousLeftCounts = leftCounts; 
				previousRightCounts = rightCounts;
				
				nextTime += period; 
				Thread.sleep(nextTime - System.currentTimeMillis()); 
			} 
		} catch (Throwable t) { 
			t.printStackTrace(); 
		} 
	}

	@Override
	public void setHeading(float arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPose(Pose arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPose(float arg0, float arg1, float arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(float arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}
}