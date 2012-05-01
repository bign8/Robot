/**
 * IntelliBrainOdeToJoy - plays the tune Ode to Joy using the IntelliBrain
 * buzzer
 * 
 * Copyright 2003, 2005 by RidgeSoft, LLC., PO Box 482, Pleasanton, CA 94566,
 * U.S.A. www.ridgesoft.com
 * 
 * RidgeSoft grants you the right to use, modify, make derivative works and
 * redistribute this source file provided you do not remove this copyright
 * notice.
 */

import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.Speaker;

public class Entertain implements Runnable {
    public static final int QUARTER_NOTE = 150, HALF_NOTE = QUARTER_NOTE * 2,  TRIPLET = (QUARTER_NOTE * 4) / 3;
	
    public static final int C4 = 262, C4_SHARP = 277,
    		D4 = 294, D4_SHARP = 311,
    		E4 = 330,
    		F4 = 349, F4_SHARP = 370,
    		G4 = 392, G4_SHARP = 415,
    		A4 = 440, A4_SHARP = 466,
    		B4 = 494,
    		C5 = 523, C5_SHARP = 554,
    		D5 = 587, D5_SHARP = 622,
    		E5 = 659,
    		F5 = 698, F5_SHARP = 740,
    		G5 = 784, G5_SHARP = 831,
    		A5 = 880, A5_SHARP = 932,
    		B5 = 988,
    		C6 = 1024,
    		R = 0;

	private Speaker buzzer;
	
	private int [][] intro = {
		{E5, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE}
	};
	private int [][] song = {
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{B4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4_SHARP, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G4, TRIPLET},
		{E5, TRIPLET},
		{G5, TRIPLET},
		
		{A5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{D5, QUARTER_NOTE},
		{B4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//END OF MEASURE 12
		
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{B4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4_SHARP, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G4, TRIPLET},
		{E5, TRIPLET},
		{G5, TRIPLET},
		
		{A5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{D5, QUARTER_NOTE},
		{B4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		{F5_SHARP, QUARTER_NOTE},
		
		{F5, QUARTER_NOTE},
		{D5_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		//END OF MEASURE 24
		
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		{F5_SHARP, QUARTER_NOTE},
		
		{F5, QUARTER_NOTE},
		{D5_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C6, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C6, QUARTER_NOTE},
		
		{C6, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		{F5_SHARP, QUARTER_NOTE},
		
		{F5, QUARTER_NOTE},
		{D5_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{D5_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//END OF MEASURE 36
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		{F5_SHARP, QUARTER_NOTE},
		
		{F5, QUARTER_NOTE},
		{D5_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		{F5_SHARP, QUARTER_NOTE},
		
		{F5, QUARTER_NOTE},
		{D5_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C6, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C6, QUARTER_NOTE},
		
		{C6, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		{F5_SHARP, QUARTER_NOTE},
		
		{F5, QUARTER_NOTE},
		{D5_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		//End of measure 48
				
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{D5_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{C5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{C5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//End of measure 60
				
		{C5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{B4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4_SHARP, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//End of measure 72
				
		{G4, TRIPLET},
		{E5, TRIPLET},
		{G5, TRIPLET},
		
		{A5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{D5, QUARTER_NOTE},
		{B4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{B4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{A4_SHARP, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G4, TRIPLET},
		{E5, TRIPLET},
		{G5, TRIPLET},
		
		{A5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{G5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{D5, QUARTER_NOTE},
		{B4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//End of measure 84
				
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F4_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{B4, TRIPLET},
		{A5, TRIPLET},
		{A5, TRIPLET},
		
		{A5, TRIPLET},
		{G5, TRIPLET},
		{F5, TRIPLET},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//End of measure 96
				
		{B4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{F5, TRIPLET},
		{E5, TRIPLET},
		{D5, TRIPLET},
		
		{C5, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		
		{C4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
	
		{B4, TRIPLET},
		{A5, TRIPLET},
		{A5, TRIPLET},
		
		{A5, TRIPLET},
		{G5, TRIPLET},
		{F5, TRIPLET},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//End of measure 108
				
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{B4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{F5, TRIPLET},
		{E5, TRIPLET},
		{D5, TRIPLET},
		
		{C5, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		
		{C4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{C5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//End of measure 120
				
		{C5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{C5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{D5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{E5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//End of measure 132
				
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{B4, TRIPLET},
		{A5, TRIPLET},
		{A5, TRIPLET},
		
		{A5, TRIPLET},
		{G5, TRIPLET},
		{F5, TRIPLET},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{A4, QUARTER_NOTE},
		
		{G4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{E5, QUARTER_NOTE},
		{C5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4, QUARTER_NOTE},
		
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{G4_SHARP, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{A4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		//End of measure 144
		
		{B4, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{F5, QUARTER_NOTE},
		
		{F5, TRIPLET},
		{E5, TRIPLET},
		{D5, TRIPLET},
		
		{C5, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{E4, QUARTER_NOTE},
		
		{C4, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE},
		{R, QUARTER_NOTE}
	};
	
	private boolean running = true;
	
	public Entertain() {
		buzzer = IntelliBrain.getBuzzer();
	}
	
	public void stop() { 
		running = false;
		//Thread.dumpStack();
		Thread.yield();
	}
	
    public void run() {
    	running = true;
        //try {

        	for(int j = 0; j < intro.length; j++){
				buzzer.play(intro[j][0], intro[j][1]);
				//Thread.yield();
				if (!running) return;
			}
			
			for(int i = 1; i < 3; i++) {
				for(int j = 0; j < song.length; j++){
					buzzer.play(song[j][0], song[j][1]);
					//Thread.yield();
					if (!running) return;
				}
			}

        //} catch (Throwable t) { t.printStackTrace(); }
    }
}
