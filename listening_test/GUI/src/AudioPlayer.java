import java.io.File;
import java.io.IOException;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * The AudioPlayer class contains methods to enable playing the stimulus files
 * 
 * @author fernandez.laura
 *
 */

public class AudioPlayer {


	/**
	 * Instance of class ListSpeech, for updating GUI in interface when stimulus stops playing
	 */
	ListSpeech listSpeech;
	
	/**
	 * Clip with the speech to be played
	 */
	Clip clip;

	/**
	 * Status of the clip: played or stop
	 */
	private String status;
	final String PLAYING="playing";
	final String STOPPED="stopped";

	/**
	 * time to measure the listener response time
	 */
	long timeStart; // audio clip starts playing
	long timeStopped; // audio clip stops (event)


	/**
	 * Constructor
	 * @param listSpeech 
	 */
	public AudioPlayer(ListSpeech listSpeech){
		this.listSpeech = listSpeech;

		//Initial status: clip not being played
		status=STOPPED;
	}


	/**
	 * Plays audio file once (.wav format)
	 * @param file to play
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public void play(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException{

		if (status != PLAYING) {

			clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			clip.open(ais);
			clip.addLineListener(this.lineListener);
			status = PLAYING;
			// Set timeStart only the first time the stimulus is played
			if (timeStart==0){
				this.timeStart=System.currentTimeMillis();
			}
			// Start playing
			clip.start();
		}
	}


	/**
	 * Method to stop playing the file from ButtonListener 
	 * Also stops when the file ends (lineListener)
	 */
	public void stopPlaying(){
		clip.stop();
		status=STOPPED;
		// Set timeStopped only the first time the stimulus is played
				if (timeStopped==0){
					this.timeStopped=System.currentTimeMillis();
					int d=(int) (timeStopped-timeStart); // file duration in ms
					listSpeech.statStimuli.setFileDuration(d);
					System.out.println("File duration (ms): " + (timeStopped-timeStart));
				}
				listSpeech.interf.buttonNext.setVisible(true);
				listSpeech.interf.buttonAgain.setVisible(true);
				listSpeech.interf.image.setVisible(false);
	}


	/**
	 * Line listener to listen to events occurring in the clip (i.e. stop).
	 */
	protected LineListener lineListener = new LineListener()
	{
		public void update(LineEvent e){
			onLineEvent(e);
		}
	};



	/**
	 * Thread to call processLineEvent(e)
	 * @param e
	 */
	protected void onLineEvent(final LineEvent e)
	{
		// This event comes from the Java Sound Dispatch Thread. Synchronize access to this class by processing the
		// event on the AWT Event Thread.
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				processLineEvent(e);
			}
		});
	}


	/**
	 * To detect when the clips ends playing
	 * @param e
	 */
	protected void processLineEvent(LineEvent e)
	{
		if (e.getType() == LineEvent.Type.STOP){
			stopPlaying();
		}
	}

	/**
	 * Getter for status("playing" or "stopped")
	 * @return status
	 */
	public String getStatus(){
		return this.status;
	}


	/**
	 * Setter for status("playing" or "stopped")
	 * @param s
	 */
	public void setStatus(String s){
		this.status=s;
	}
}
