import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * Class ButtonListener which implements ActionListener to react when the listener clicks on "Next"
 * 
 * @author fernandez.laura
 *
 */

public class ButtonListener implements ActionListener {

	/**
	 * Interface, controlled by this listener
	 */
	Interface interf;

	/**
	 * Duration of pause when changing test parts
	 */
	int secondsPause; 

	/**
	 * Constructor of the class
	 * @param: objet of the class Interface, which contains the list of Speech
	 */
	public ButtonListener(Interface interf){
		this.interf = interf;

		// force to wait 30 seconds during the pause
		secondsPause = 30;
	}


	/**
	 * actionPerformed() starts when the listener clicks on the "Next" button
	 * Registers the listener's answer
	 * Switches between test parts
	 * Checks end of the test
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// Check whether the experiment session has started, else start
		if (interf.listSpeech.playingStimuliPm==true || interf.listSpeech.playingStimuliPf==true){ 

			// React only if the listener has given an answer to all items (there is a value on each slider)
			boolean allSelected = true;

			if (interf.sl.knobPainted==false){
				allSelected=false;
			}


			if (allSelected){

				// Call method to save all given ratings to the current stimulus
				registerAnswer();

				// Switch between test parts if the last stimulus of the first part has been played (Pm to Pf)
				if(interf.listSpeech.playingStimuliPm==true && interf.listSpeech.vectorStimuliPm.isEmpty()){

					interf.listSpeech.playingStimuliPm=false;
					interf.listSpeech.playingStimuliPf=true;

					// Pause change test parts
					interf.listSpeech.includePauseNow(secondsPause);   

					try {
						interf.listSpeech.prepareStimuliPf();
					} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
						e1.printStackTrace();
					}

					// If this was the last file in the Pf vector: finish else next stimulus of Pf
				} else if(interf.listSpeech.playingStimuliPf==true  && interf.listSpeech.vectorStimuliPf.isEmpty()){	
					finish();

					// Else no test section has been finished
				}else{ 

					playNextStimulus();

				}

			} // end checking if all selected (implement else if we want to issue a message for the listener)


			// when no interf.listSpeech.playingStimuli is true -> start test
		}else{ 

			// Change button label from "Start" to "Weiter" (="Next")
			interf.buttonNext.setText("Weiter"); 
			interf.buttonAgain.setVisible(true);

			try {

				// Call method from class ListSpeech
				interf.listSpeech.startStimuli();

			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
				e1.printStackTrace();
			}
		}
	} // End method actionListener


	/**
	 * Called if it is not yet the end of the speech file vectors 
	 * Tries interf.listSpeech.next(); -> Play next speech utterance from listSpeech
	 */
	private void playNextStimulus() {
		try {

			interf.listSpeech.next();

		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}



	/**
	 * Method to register the listener's answer given in the interface
	 * Stores listener' ratings in interf.listSpeech.statStimuli (object from class Statistics)
	 * Writes line to CSV file (lines will be appended as the experiment runs)
	 */
	private void registerAnswer() {

		//Log: write in nameListener.txt
		System.out.println(interf.rating);  

		// Fill in response fields for the statistics in listSpeech
		interf.listSpeech.statStimuli.setRating(interf.rating);

		// Saving ratings to output CSV file
		String delim= ","; // comma
		Statistics stat=interf.listSpeech.statStimuli;
		String str2="\r\n" + stat.getFileName() + delim + interf.nameListener + delim + interf.ageListener + delim + interf.genderListener + delim + interf.languageListener + delim;

		str2 = str2 + stat.getRating();

		try {

			Path fileOutput = Paths.get("files\\output\\output_GUI_SpeakerCharacteristics_"+ interf.nameListener + ".csv");
			Files.write(fileOutput, str2.getBytes(), StandardOpenOption.APPEND);

		}catch (IOException e) {
			e.printStackTrace();
		}

		// increment count of stimuli
		interf.count= interf.count+1;
	}


	/**
	 * Method to be called when all stimulus files have been played
	 * Removes content from the GUI - finishes the experiment
	 */
	private void finish() {

		// Show end message to the listener
		JOptionPane.showMessageDialog(null," Ende des Experiments \n Vielen Dank für Deine Teilnahme!","ENDE",
				JOptionPane.INFORMATION_MESSAGE);


		// End. Closes window 
		interf.setVisible(false);  
		interf.dispose();

	}

} // End class


