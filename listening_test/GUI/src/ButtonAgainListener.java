import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * ButtonAgainListener contains method actionPerformed() to react when listener listens again to the same stimulus file
 * 
 * @author fernandez.laura
 *
 */
public class ButtonAgainListener implements ActionListener {

	/**
	 * Interface, controlled by this listener
	 */
	Interface interf;

	/**
	 * Response time (timeClickNext - timeStartPlayingFile)
	 */
	int msNextButton;
	
	/**
	 * Constructor of the class. 
	 * @param: objet of the class Interface, which contains the list of Speech
	 */
	public ButtonAgainListener(Interface interf){
		this.interf = interf;
	}

	/**
	 * actionPerformed starts when the listener clicks on the "Hören" button
	 * just play the file again
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		interf.listSpeech.statStimuli.setTimesHeard(interf.listSpeech.statStimuli.getTimesHeard()+1);  

		// hide or show stuff in the GUI  
		interf.buttonNext.setVisible(false);
		interf.buttonAgain.setVisible(false);
		interf.image.setVisible(true);

		try {

			interf.listSpeech.playAgain();

		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
