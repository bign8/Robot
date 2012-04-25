import com.ridgesoft.intellibrain.IntelliBrain;
import java.io.InputStream;
import javax.comm.SerialPort;

// special thanks to
// http://tech.groups.yahoo.com/group/intellibrain/message/25

public class GPS implements Runnable {
	
	private class LatLon {
		public int curLat = 0, curLon = 0, curHdg = 0, curSpd = 0;
		public boolean valid;
	}
	
	private LatLon latLon;
	private InputStream com1;
	
	public GPS() {
		latLon = new LatLon();
		
		try {
			SerialPort coms = IntelliBrain.getCom2();
			
			coms.setSerialPortParams(4800, // baud
					SerialPort.DATABITS_8, 
					SerialPort.STOPBITS_1, 
					SerialPort.PARITY_NONE);
			
			com1 = coms.getInputStream();
			
		} catch (Throwable e) { }
		
	}
	
	public void run() {
		
		byte[] inputBuffer = new byte[128];
		byte[] parseBuffer = new byte[128]; // should be smaller
		
		int jdx = 0; // pointing to end of parseBuffer
		boolean stillReading = false;
		long time = System.currentTimeMillis();
		
		while(true) {
			try {
				
				//display.print(1, new String(buffer));
				
				int inputCount = com1.read(inputBuffer, 0, inputBuffer.length);
				if (com1.available() > 1024) {
					//com1.skip(com1.available()-128); // nobody wants old data - have not seen executed
					System.out.println("Data Skipped!");
				}
				
				// find start of desired sentance
				for (int idx = 0; idx < inputCount - 6; idx++) {
					if (!stillReading) {
						jdx = 0;
						if ((char) inputBuffer[idx    ] != 'G') continue;
						if ((char) inputBuffer[idx + 1] != 'P') continue;
						if ((char) inputBuffer[idx + 2] != 'R') continue;
						if ((char) inputBuffer[idx + 3] != 'M') continue;
						if ((char) inputBuffer[idx + 4] != 'C') continue;
						idx += 6; // clean identifiers?
					}
					stillReading = true;
					
					while (idx < inputCount && jdx < parseBuffer.length) { // copy into read buffer
						parseBuffer[jdx] = inputBuffer[idx];
						
						if ((char)parseBuffer[jdx] == '\r' || (char)parseBuffer[jdx] == '$') { // finished reading the junk
							stillReading = false;
							break;
						}
						
						idx++; jdx++;
					}

					// Begin Parsing
					if (!stillReading) { // parse buffer for da magic
						//System.out.println(new String(parseBuffer));
						//String[] data = toDebugString( new String[2] );
						//System.out.println(data[0] + "\n" + data[1]);
						
						try {
							synchronized (latLon) {
								latLon.valid  = (firstChar(parseBuffer, 2) == 'A');
								//if (latLon.valid) {
									latLon.curLat = cvtFld(parseBuffer, 4, true);
									latLon.curLon = cvtFld(parseBuffer, 2, true);
									latLon.curHdg = cvtFld(parseBuffer, 7, false);
									latLon.curSpd = cvtFld(parseBuffer, 6, false);
								//}
							}
						} catch (Error e) {/* silent kill on the errors I produce */} // data has no dot and thus errors out stuff
					}
					
					break; // no more looping this round
				}
				
				// pause thread execution
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			} catch( Throwable t ) { System.out.println("Error"); t.printStackTrace(); }
		} // end while true
			
		
	}
	
	private static char firstChar(byte[] buf, int fieldNumber) {
		int cdx = 0;
		for (cdx = 0; cdx < buf.length-1; cdx++) {
			if (buf[cdx] == ',') fieldNumber--;
			if (fieldNumber == 0) break;
		}
		return (char) buf[++cdx];
		
	}
	
	// converts degree - minutes (dddmm.mmmm) to integer minutes
	private static int cvtFld(byte[] buf, int fieldNum, boolean degFlg) throws Error {
		int cdx = 0;
		for (cdx = 0; cdx < buf.length-1; cdx++) {
			if (buf[cdx] == ',')
				fieldNum--;
			if (fieldNum == 0)
				break;
		}
		
		int rtnVal = 0;
		
		for (cdx++; buf[cdx] != '.'; cdx++) {
			if (cdx >= buf.length-1) throw new Error("No Dot!");
			rtnVal *= 10;
			rtnVal += buf[cdx] - '0';
		}
		
		if (degFlg) /* not exactly gps, but it works as far as getting coorninates */
			rtnVal = ((rtnVal - rtnVal % 100) * 60) / 100 + (rtnVal % 100);
		for (cdx++; buf[cdx] != ','; cdx++) {
			if (cdx >= buf.length-1) throw new Error("No Last Comma");
			rtnVal *= 10;
			rtnVal += buf[cdx] - '0';
		}
		
		return rtnVal;
	}
	
	public String[] toDebugString(String in[]) {
		synchronized (latLon) {
			in[0] = "V:" + (latLon.valid ? "V " : "I ") + 
					"Lat:" + latLon.curLat + " Lon:" + latLon.curLon;
			in[1] = "Hdg:" + latLon.curHdg + " Spd:" + latLon.curSpd;
			return in;
		}
	}
}
