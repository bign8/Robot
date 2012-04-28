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
    public static final int QUARTER_NOTE = 150;
    public static final int HALF_NOTE = QUARTER_NOTE * 2;
	public static final int TRIPLET = (QUARTER_NOTE * 4) / 3;
	
    public static final int C4 = 262;
    public static final int C4_SHARP = 277;
    public static final int D4 = 294;
    public static final int D4_SHARP = 311;
    public static final int E4 = 330;
    public static final int F4 = 349;
    public static final int F4_SHARP = 370;
    public static final int G4 = 392;
    public static final int G4_SHARP = 415;
    public static final int A4 = 440;
    public static final int A4_SHARP = 466;
    public static final int B4 = 494;
	public static final int C5 = 523;
	public static final int C5_SHARP = 554;
	public static final int D5 = 587;
	public static final int D5_SHARP = 622;
	public static final int E5 = 659;
	public static final int F5 = 698;
	public static final int F5_SHARP = 740;
	public static final int G5 = 784;
	public static final int G5_SHARP = 831;
	public static final int A5 = 880;
	public static final int A5_SHARP = 932;
	public static final int B5 = 988;
	public static final int C6 = 1024;
	public static final int R = 0;

	public Speaker buzzer;
	
	public Entertain() {
		buzzer = IntelliBrain.getBuzzer();
	}
	
    public void run() {
        try {
            
			//Mario Theme  Broken into measures
			buzzer.play(E5, QUARTER_NOTE);
			buzzer.play(E5, QUARTER_NOTE);
			buzzer.play(R, QUARTER_NOTE);
			buzzer.play(E5, QUARTER_NOTE);
			
			buzzer.play(R, QUARTER_NOTE);
			buzzer.play(C5, QUARTER_NOTE);
			buzzer.play(E5, QUARTER_NOTE);
			buzzer.play(R, QUARTER_NOTE);
			
			buzzer.play(G5, QUARTER_NOTE);
			buzzer.play(R, QUARTER_NOTE);
			buzzer.play(R, QUARTER_NOTE);
			buzzer.play(R, QUARTER_NOTE);
			
			buzzer.play(G4, QUARTER_NOTE);
			buzzer.play(R, QUARTER_NOTE);
			buzzer.play(R, QUARTER_NOTE);
			buzzer.play(R, QUARTER_NOTE);
			
			for(int i = 1; i < 3; i++) {
			
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(B4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4_SHARP, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(G4, TRIPLET);
				buzzer.play(E5, TRIPLET);
				buzzer.play(G5, TRIPLET);
				
				buzzer.play(A5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(B4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//END OF MEASURE 12
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(B4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4_SHARP, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(G4, TRIPLET);
				buzzer.play(E5, TRIPLET);
				buzzer.play(G5, TRIPLET);
				
				buzzer.play(A5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(B4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				buzzer.play(F5_SHARP, QUARTER_NOTE);
				
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(D5_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				//END OF MEASURE 24
				
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				buzzer.play(F5_SHARP, QUARTER_NOTE);
				
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(D5_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C6, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C6, QUARTER_NOTE);
				
				buzzer.play(C6, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				buzzer.play(F5_SHARP, QUARTER_NOTE);
				
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(D5_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(D5_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//END OF MEASURE 36
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				buzzer.play(F5_SHARP, QUARTER_NOTE);
				
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(D5_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				buzzer.play(F5_SHARP, QUARTER_NOTE);
				
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(D5_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C6, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C6, QUARTER_NOTE);
				
				buzzer.play(C6, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				buzzer.play(F5_SHARP, QUARTER_NOTE);
				
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(D5_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				//End of measure 48
						
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(D5_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//End of measure 60
						
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(G5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(B4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4_SHARP, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//End of measure 72
						
				buzzer.play(G4, TRIPLET);
				buzzer.play(E5, TRIPLET);
				buzzer.play(G5, TRIPLET);
				
				buzzer.play(A5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(B4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(B4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4_SHARP, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(G4, TRIPLET);
				buzzer.play(E5, TRIPLET);
				buzzer.play(G5, TRIPLET);
				
				buzzer.play(A5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(G5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(B4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//End of measure 84
						
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F4_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(B4, TRIPLET);
				buzzer.play(A5, TRIPLET);
				buzzer.play(A5, TRIPLET);
				
				buzzer.play(A5, TRIPLET);
				buzzer.play(G5, TRIPLET);
				buzzer.play(F5, TRIPLET);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//End of measure 96
						
				buzzer.play(B4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(F5, TRIPLET);
				buzzer.play(E5, TRIPLET);
				buzzer.play(D5, TRIPLET);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				
				buzzer.play(C4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
			
				buzzer.play(B4, TRIPLET);
				buzzer.play(A5, TRIPLET);
				buzzer.play(A5, TRIPLET);
				
				buzzer.play(A5, TRIPLET);
				buzzer.play(G5, TRIPLET);
				buzzer.play(F5, TRIPLET);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//End of measure 108
						
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(B4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(F5, TRIPLET);
				buzzer.play(E5, TRIPLET);
				buzzer.play(D5, TRIPLET);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				
				buzzer.play(C4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//End of measure 120
						
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(D5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(G5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//End of measure 132
						
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(B4, TRIPLET);
				buzzer.play(A5, TRIPLET);
				buzzer.play(A5, TRIPLET);
				
				buzzer.play(A5, TRIPLET);
				buzzer.play(G5, TRIPLET);
				buzzer.play(F5, TRIPLET);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(A4, QUARTER_NOTE);
				
				buzzer.play(G4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(E5, QUARTER_NOTE);
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4, QUARTER_NOTE);
				
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(G4_SHARP, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(A4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				//End of measure 144
				
				buzzer.play(B4, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(F5, QUARTER_NOTE);
				
				buzzer.play(F5, TRIPLET);
				buzzer.play(E5, TRIPLET);
				buzzer.play(D5, TRIPLET);
				
				buzzer.play(C5, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(E4, QUARTER_NOTE);
				
				buzzer.play(C4, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
				buzzer.play(R, QUARTER_NOTE);
			
			}

        } catch (Throwable t) { t.printStackTrace(); }
    }
}
