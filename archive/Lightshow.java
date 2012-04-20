import com.ridgesoft.intellibrain.IntelliBrain;
import com.ridgesoft.io.LED;

public class Lightshow {

	// private int value;

	public Lightshow(int n) {
		// value = n;
	}

	public void go() {
		try {
			LED[] leds;
			if (IntelliBrain.getModel() == IntelliBrain.MODEL_IntelliBrain)
				leds = new LED[8]; // IntelliBrain, requires expansion board
			else
				leds = new LED[4]; // IntelliBrain2
			for (int i = 0; i < leds.length; ++i)
				leds[i] = IntelliBrain.getUserLed(i + 1);

			while (true) {
				int i = 0;
				while (i < leds.length) {
					leds[i].on();
					Thread.sleep(100);
					++i;
				}

				i = 0;
				while (i < leds.length) {
					leds[i].off();
					Thread.sleep(100);
					++i;
				}

				i = 0;
				while (i < leds.length) {
					leds[i].on();
					Thread.sleep(100);
					i += 2;
				}

				i = 0;
				while (i < leds.length) {
					leds[i].toggle();
					Thread.sleep(100);
					++i;
				}

				i = 0;
				while (i < leds.length) {
					leds[i].off();
					Thread.sleep(100);
					++i;
				}

				i = 0;
				while (i < leds.length) {
					leds[i].on();
					++i;
				}

				for (int j = 0; j < 10; ++j) {
					Thread.sleep(100);
					Thread.sleep(100);
					Thread.sleep(100);
					i = 0;
					while (i < leds.length) {
						leds[i].toggle();
						++i;
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
