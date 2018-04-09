import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;


/**
 * SuperSlider class to customize the slider appearance
 * 
 * @author fernandez.laura
 *
 */

public class SuperSlider extends JSlider {

	private static final long serialVersionUID = 1L;
	public boolean knobEnabled;
	public boolean knobPainted;
	int sliderNumber;

	public SuperSlider(int n, int min, int max, int value) {
		super(min,max,value);
		this.sliderNumber=n;
		setUI(new SuperSliderUI(this));
	}

	private class SuperSliderUI extends BasicSliderUI {
		public SuperSliderUI(JSlider b) {
			super(b);
		}

		// For setting the value of the JSlider to the nearest value to where the user clicked on the track
		@Override
		protected void scrollDueToClickInTrack(int direction) {
			// this is the default behavior, let's comment that out
			//scrollByBlock(direction);

			int value = slider.getValue(); 

			if (slider.getOrientation() == JSlider.HORIZONTAL) {
				value = this.valueForXPosition(slider.getMousePosition().x);
			} else if (slider.getOrientation() == JSlider.VERTICAL) {
				value = this.valueForYPosition(slider.getMousePosition().y);
			}
			slider.setValue(value);
		}

		// Paint only if the stimulus has started to be played 
		@Override
		public void paintThumb(Graphics g) {

			if (knobEnabled==true){

				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				Rectangle t = thumbRect;
				g2d.setColor(Color.black);
				int tw2 = t.width / 2;
				g2d.drawLine(t.x, t.y, t.x + t.width - 1, t.y);
				g2d.drawLine(t.x, t.y, t.x + tw2, t.y + t.height);
				g2d.drawLine(t.x + t.width - 1, t.y, t.x + tw2, t.y + t.height);

				knobPainted=true; // need this flag in order to accept the response and go to the next stimulus
				// set to false in init (interface constructor), and in ListSpeech play()

			}else {
				// Do not paint the knob
			}
		} // end paintThumb




	}

	public int getSliderNumber() {
		return sliderNumber;
	}

 
}