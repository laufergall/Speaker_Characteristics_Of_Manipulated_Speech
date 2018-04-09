import java.io.File;

/**
 * Class to keep track of filename played and the corresponding ratings
 * 
 * @author fernandez.laura
 *   
 */

public class Statistics {

	/**
	 * Number of the listener subject of the experiments 
	 * (all vectors start with this number)
	 * Information input at the beginning of the experiments
	 */
	private String IDListener;

	/**
	 * File being played
	 */
	private File file;

	/**
	 * File duration (ms)
	 * Value not used. Could be included in the CSV output file
	 */
	private int fileDuration;

	/**
	 * Answers given by the listener (ratings in the sliders)
	 */
	private int rating;
 
	/**
	 * Number of times that the listener listens to the current file
	 */
	int timesHeard;

	/**
	 * Set the listener's number
	 * @param n
	 */
	public void setNumListener(String n) {
		this.IDListener=n;
	}

	/**
	 * Set the fileDuration (ms), which is determined with timeStopped-timeStart in listSpeech
	 * Done in ListSpeech, method stopPlaying()
	 * @param d
	 */
	public void setFileDuration(int d){
		this.fileDuration=d;
	}


	/**
	 * Set all ratings (ArrayList) when registering the answer
	 * @param ar
	 */
	public void setRating(int ar){
		this.rating=ar;
	}
 
	/**
	 * Set the number of times that the listener listens to the current file
	 * @param i
	 */
	public void setTimesHeard(int i) {
		this.timesHeard=i;
	}

	/**
	 * 
	 * @return number of the listener participating in the test
	 */
	public String getNumListener(){
		return IDListener;
	}


	/**
	 * 
	 * @return file
	 */
	public File getFile(){
		return file;
	}

	/**
	 * 
	 * @return fileName of the file (string), without path
	 */
	public String getFileName(){
		return file.getName();
	}


	/**
	 * 
	 * @return file duration of this file
	 */
	public int getFileDuration(){
		return fileDuration;
	}


	/**
	 * 
	 * @return allRatings arraylist
	 */
	public int getRating(){
		return rating;
	}


	 

	public int getTimesHeard() {
		return timesHeard;
	}




	/**
	 * Class constructor
	 * @param f file to play
	 */
	public Statistics(File f){

		this.file=f;
		String fileName=f.getName();

		// The file has already been heard once
		this.timesHeard=1; 

		// writing info about the file to be played
		System.out.println("\nPlaying filename: " + fileName);


	}






}
