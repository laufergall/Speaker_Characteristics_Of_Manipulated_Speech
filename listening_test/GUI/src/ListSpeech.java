import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;



/**
 * ListSpeech contains the list of all speech files
 * Methods to play the files 
 * 
 * @author fernandez.laura
 * 
 */

public class ListSpeech {

	/**
	 * Interface, to update GUI components when playing/stopped
	 */
	Interface interf;

	/**
	 * AudioPlayer, needed to play/stop speech stimuli
	 */
	AudioPlayer audioPlayer;


	/**
	 * Folders with the speech of the different parts of the test
	 */
	File folderPm;
	File folderPf;

	/**
	 *  Array with the speech files of stimuli of each part of the test
	 */
	File stimuliPm[];
	File stimuliPf[];

	/** 
	 * Vector to contain the total of stimuli being played of each part of the test
	 */
	Vector<File> vectorStimuliPm;
	Vector<File> vectorStimuliPf;

	/**
	 * Booleans to indicate the section of the experiments. 
	 */
	boolean playingStimuliPm;
	boolean playingStimuliPf;


	/**
	 * Current stimulus file being played, or waiting from an answer from the listener
	 */
	File currentFile;

	/**
	 * Instance of the class statistics 
	 * Information of the current stimulus file (filename, rating...) 
	 * Needed o save the listener ratings given to the stimulus file
	 */
	Statistics statStimuli;

	/**
	 * Clip with the speech to be played
	 */
	Clip clip;

	/**
	 * time to measure the listener response time
	 */
	long timeStart; // audio clip starts playing
	long timeStopped; // audio clip stops (event)


	/**
	 * Constructor
	 * Fill in content (audio files) in the speech arrays
	 * For the different test parts
	 * 
	 */
	public ListSpeech(Interface interf){
		
		this.interf = interf;

		audioPlayer= new AudioPlayer(this);

		folderPm= new File("files\\speech\\males");
		folderPf= new File("files\\speech\\females");

		// check valid folders and fill in stimuliPm and stimuliPf with the contents (wav files only)
		if(folderPm.isDirectory() && folderPf.isDirectory()){ 
			
			stimuliPm = folderPm.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.toLowerCase().endsWith(".wav"); 
			    }
			});
			
			stimuliPf = folderPf.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.toLowerCase().endsWith(".wav"); 
			    }
			});
			

		}else{
			System.out.println("One or more stimuli folder not valid");
		}

		//Initial status: clip not being played
		audioPlayer.setStatus(audioPlayer.STOPPED);
		timeStart=0;
		timeStopped=0;
	}



	/**
	 * Method to play the next stimuli file, selected randomly
	 * Takes into account the different parts of the test
	 * @return 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public void next() throws LineUnavailableException, UnsupportedAudioFileException, IOException{

		// hide or show stuff in the GUI  
		interf.buttonNext.setVisible(false);
		interf.buttonAgain.setVisible(false);
		interf.image.setVisible(true);
		removeKnob();

		// reset times of a new file
		timeStart=0;
		timeStopped=0;

		// Look for the next stimulus to play, randomly
		int randomNumber=0;
		Random generator=new Random();


		if(playingStimuliPm==true){
			// Generate a random number from  vectorWords.size()
			randomNumber = generator.nextInt(vectorStimuliPm.size());
			// Remove file from vector
			currentFile= (File) vectorStimuliPm.elementAt(randomNumber);
			vectorStimuliPm.removeElementAt(randomNumber);	
			// New statistics of the current file
			statStimuli = new Statistics (currentFile);
		}

		if(playingStimuliPf==true){
			// Generate a random number from  vectorWords.size()
			randomNumber = generator.nextInt(vectorStimuliPf.size());
			// Remove file from vector
			currentFile= (File) vectorStimuliPf.elementAt(randomNumber);
			vectorStimuliPf.removeElementAt(randomNumber);	
			// New statistics of the current file
			statStimuli = new Statistics (currentFile);
		}

		// play the selected stimulus file
		audioPlayer.play(currentFile);

	}



	/**
	 * Method to remove the knobs from all sliders. 
	 * Do not show and do not enable to paint
	 * Needed before playing next and before taking a pause
	 */
	private void removeKnob() {

		int midVal=(interf.maxRating-interf.minRating)/2;

			// Need to move the knob if the listener rated midVal, in order to be able to remove it
			if(interf.rating==midVal){
				interf.sl.setValue(midVal+1); 
			}
			
			// Reset slider value
			interf.sl.setValue(midVal);

			// Remove knob and do not allow to paint it
			interf.sl.knobEnabled=false;
			interf.sl.knobPainted=false;

	}



	/**
	 * Method to force a pause now
	 * Called when switching test parts
	 * It is not possible to click on GUI items during the pause
	 * @param seconds: pause duration in seconds
	 */
	public void includePauseNow(int seconds) {

		// Remove knobs in GUI and do not enable clicks
		removeKnob();

		JOptionPane.showMessageDialog(null," - PAUSE - (OK drücken um Timer zu starten: ~ "+ seconds + " Sek.)","PAUSE",
				JOptionPane.INFORMATION_MESSAGE);
		
		// Counter, force to wait
		long t0, t1;
		t0 =  System.currentTimeMillis();
		do{
			t1 = System.currentTimeMillis();
		}
		while ((t1 - t0) < (seconds * 1000)); // waiting time

		JOptionPane.showMessageDialog(null,"Ende der Pause. Bitte fortfahren","PAUSE",
				JOptionPane.INFORMATION_MESSAGE);

	}


	/**
	 * Start the auditory test. Called from ButtonListener when the listener clicks on the "Start" button 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public void startStimuli() throws LineUnavailableException, UnsupportedAudioFileException, IOException{

		// Logical value to indicate that male stimuli are played
		playingStimuliPm=true;

		// Call method to gather the corresponding stimuli and start playing
		prepareStimuliPm();
	}


	/**
	 * Method to create a vector with the stimuli files for Pm
	 * The files will be removed from the vector after being played.
	 * Called from ButtonListener after the last answer is recorded 
	 * Does not include the speech of the listener (compares the IDs) 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public void prepareStimuliPm() throws LineUnavailableException, UnsupportedAudioFileException, IOException {

		// Log that male speaker stimuli will be played
		System.out.println("- - - - - - Test part : Males - - - - - -"); 

		JOptionPane.showMessageDialog(null,"Männliche Stimmen","Experimenteller Teil",
				JOptionPane.INFORMATION_MESSAGE);

		// Gather male speaker stimuli
		vectorStimuliPm=new Vector<File>();
		for (int i=0; i<stimuliPm.length; i++){

			vectorStimuliPm.addElement(stimuliPm[i]);

		}

		// Start by picking a random stimulus file and playing
		next(); 
	}

	/**
	 * Method to create a vector with the stimuli files for Pf
	 * The files will be removed from the vector after being played.
	 * Called from ButtonListener after the last answer is recorded 
	 * Does not include the speech of the listener (compares the IDs) 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public void prepareStimuliPf() throws LineUnavailableException, UnsupportedAudioFileException, IOException {

		// Log that female speaker stimuli will be played
		System.out.println("- - - - - - Test part : Females - - - - - -"); 

		JOptionPane.showMessageDialog(null,"Weibliche Stimmen","Experimenteller Teil",
				JOptionPane.INFORMATION_MESSAGE);

		// Gather female speaker stimuli
		vectorStimuliPf=new Vector<File>();
		for (int i=0; i<stimuliPf.length; i++){

			vectorStimuliPf.addElement(stimuliPf[i]);

		}

		// Start by picking a random stimulus file and playing
		next(); 
	}


	/**
	 * Gets called when the listener clicks on "Hören" to listen to the same stimulus again, before submitting answers
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void playAgain() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		audioPlayer.play(currentFile);

	}





}
