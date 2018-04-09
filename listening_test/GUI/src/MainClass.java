import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * Implements main()
 * Start auditory test by asking for some listeners' input data
 * Initializes Interface (frame) and creates the log file
 * 
 * @author fernandez.laura
 *
 */
public class MainClass {

	public static void main(String args[]) {

		// Input info from the listener performing the test. Will be written as columns in the output CSV file

		// name of listener (can be a pseudonym, or ID code)
		String nameListener = JOptionPane.showInputDialog("Name oder ID der Versuchsperson", "Example: 001");

		String ageListener_str = JOptionPane.showInputDialog("Alter der Versuchsperson", "Example: 25");

		String genderListener = JOptionPane.showInputDialog("Geschlecht der Versuchsperson (m/w)", "Example: w");

		String languageListener = JOptionPane.showInputDialog("Muttersprache der Versuchsperson (m/w)", "Example: Deutsch");


		// Check that no defaults are entered
		
		if (nameListener.equals("Example: 001") || ageListener_str.equals("Example: 25") || genderListener.equals("Example: w")  || languageListener.equals("Example: Deutsch")){

			JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel,"Please, insert right metadata", "Error", JOptionPane.ERROR_MESSAGE);

		}else{
			if (!genderListener.equals("w") && !genderListener.equals("m")){ // no male, no female

				JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel,"Please, insert right metadata", "Error", JOptionPane.ERROR_MESSAGE);

			}else{
				
				int ageListener = Integer.parseInt(ageListener_str);

				// Start Interface GUI
				
				JFrame frame = new Interface(nameListener, ageListener, genderListener, languageListener);

				frame.setVisible(true);



				// Save logs

				PrintStream out = null;
				try {

					File fileOut = new File("files\\output\\Log_GUI_SpeakerCharacteristics_" + nameListener+ ".txt");
					if (!fileOut.exists()) fileOut.getParentFile().mkdirs();
					fileOut.createNewFile(); // if file already exists will do nothing 

					out = new PrintStream(new FileOutputStream(fileOut));

				} catch (IOException e) {
					e.printStackTrace();
				}
				System.setOut(out);



			} 
		} 
	}


}
