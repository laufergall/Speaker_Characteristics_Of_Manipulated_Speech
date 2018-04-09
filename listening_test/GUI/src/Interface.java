import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 * Class Interface, with the application frame
 * extends JFrame
 * Add graphical components and logical stuff
 * 
 * @author fernandez.laura
 *
 */

@SuppressWarnings("serial")
public class Interface extends JFrame {

	/**
	 * Count stimuli played, to keep track of the order
	 */
	int count;

	/**
	 *  JPanels in which to place different components
	 */
	//JPanel panelImage;
	JPanel panelTask;
	JPanel panelSprecher;
	JPanel panelLow;  

	/**
	 * Name of the listener subject of the experiments 
	 * inserted through JOptionPane.dialog
	 */
	String nameListener; 

	/**
	 * Number, gender, and age of the listener, to be identified in the statistics analysis
	 * inserted through JOptionPane.dialog
	 */

	String genderListener;
	String languageListener;
	int ageListener;

	/**
	 * Label in which to show the title, task
	 */
	JLabel task; 

	/**
	 * Label in which to show the 10 personality questions of the BFI-10
	 */
	JLabel derSprecher;

	/**
	 * Label with the loudspeaker icon
	 */
	JLabel image;

	/**
	 * Slider to manipulate by the listeners
	 */
	SuperSlider sl;
	
	/**
	 * Rating values
	 */
	final int minRating = 0;
	final int maxRating = 100;
	int rating = 50;
	
	/**
	 * Font for the labels
	 */
	Font myFont20;
	Font myFont30;
	
	/**
	 * Button to start the test and to listen to the next voice
	 */
	JButton buttonNext;

	/**
	 * Button to listen again to the same stimulus
	 */
	JButton buttonAgain;

	/**
	 * ActionListener for the Next button (pick the slider value)
	 */
	ButtonListener buttonActionListener;

	/**
	 * ActionListener for the button to listen again
	 */
	ButtonAgainListener buttonAgainActionListener;

	/**
	 * Instance of the class AudioPlayer, necessary to play the corresponding file
	 */
	AudioPlayer audioPlayer;

	/**
	 *   ListSpeech contains the list of speech that the listener will hear: words, sentences, text
	 */
	ListSpeech listSpeech;


	/**
	 * Class constructor
	 * Initialized variables and add listeners to buttons and add table and other graphical components
	 * @param name - name of the listener, subject of the experiments
	 * @param nl - number of the listener (order)
	 * @param al - age of the listener
	 * @param gl - gender of the listener
	 */
	public Interface(String name, int al, String gl, String ms){

		/**
		// Main window, instead of full screen		
		//setSize(1450,850); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,screenSize.width, screenSize.height);
		setVisible(true);
		*/


		// For full screen:
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);

		//info about the listener
		nameListener=name;
		ageListener=al;
		genderListener=gl;
		languageListener=ms;

		// Count of stimuli played, init to 1
		count=1;

		// Font for the labels
		myFont20=new Font("Arial",Font.BOLD,20);
		myFont30=new Font("Arial",Font.BOLD,30);
		
		// Label (title/task) above the questionnaire items
		task = new JLabel("Inwieweit treffen die folgenden Attribute auf den Sprecher zu?");
		task.setFont(myFont30);

		// Icon
		image = new JLabel();
		ImageIcon icon = new ImageIcon("files\\images\\Loudspeaker.png");
		image.setIcon(icon);
		image.setVisible(false); // only display while playing stimuli

		// Define labels and arrange the sliders (as many as formLength)
		createSliders();

		// Button Next and its Actionlistener
		buttonNext = new JButton();
		buttonNext.setText("Start");
		buttonNext.setFont(myFont20);
		buttonActionListener= new ButtonListener(this);
		buttonNext.addActionListener(buttonActionListener);

		// Button Again and its Actionlistener
		buttonAgain = new JButton();
		buttonAgain.setText("Hören");
		buttonAgain.setFont(myFont20);
		buttonAgainActionListener= new ButtonAgainListener(this);
		buttonAgain.addActionListener(buttonAgainActionListener);
		buttonAgain.setVisible(false);


		// Define the panels and add GUI components
		createGUI();

		//Run the experiments, playing speech files listed in listSpeech
		listSpeech= new ListSpeech(this);

		// Start writing the output file 
		writeCSVfisrtLine();

	}

	/**
	 * Assign labels to sliders and adds change listeners
	 */
	private void createSliders() {

		// Init sliders and set labels and set changeListener
		// *** Main modification with respect to GUI_SpeakerCharacteristics
		// *** In this case, we are only interested in this scale:
		// gefuehlskalt - herzlich (in English: cold - hearty)
		// *** hende, only one slider is needed in the interface
		sl = new SuperSlider(0, minRating, maxRating, (maxRating-minRating)/2);

		JLabel le = new JLabel("gefühlskalt");
		JLabel ri = new JLabel("herzlich");
			
		le.setFont(myFont20);
		ri.setFont(myFont20);
		
		//le.setPreferredSize(new Dimension(100, 10));
		//ri.setPreferredSize(new Dimension(100, 10));

		Hashtable<Integer, JLabel> lt = new Hashtable<Integer, JLabel>();
		lt.put( new Integer(minRating), le);
		lt.put( new Integer(maxRating), ri);

		sl.setLabelTable(lt);
		sl.setPaintLabels(true);
		sl.knobEnabled=false;    							 
		sl.knobPainted=false; 
		sl.setPreferredSize(new Dimension(800, 80));

		ChangeListener cl = new ChangeListener()    
		{
			public void stateChanged(ChangeEvent event)
			{
				// enable to paint the knob!
				if (sl.knobEnabled==false){
					sl.knobEnabled=true;     					 
				}

				// update text field when the slider value changes
				SuperSlider source = (SuperSlider) event.getSource();

				// Log all slider value changes
				System.out.println("SliderNumber:"+source.getSliderNumber()+",  Value:"+source.getValue());  

				// Store current rating for this slider
				rating = source.getValue();
			}
		};
		sl.addChangeListener(cl);
	}


	/**
	 * Create panels, place all GUI components, add panels to container
	 */
	private void createGUI() {
		// Panel with the title/task label	
		panelTask = new JPanel(); //new GridLayout(2,1));
		panelTask.add(task, BorderLayout.CENTER);


		//Panels in which to place the sliders
		panelSprecher = new JPanel();
		panelSprecher.add(new JLabel());
		panelSprecher.add(new JLabel());
		panelSprecher.add(new JLabel());
		panelSprecher.add(new JLabel());

		JPanel ps=new JPanel();
		ps.add(sl);

		//Panel in which to place the Next button
		panelLow = new JPanel(new FlowLayout());  
		panelLow.add(buttonAgain, BorderLayout.WEST);
		panelLow.add(image, BorderLayout.CENTER);
		panelLow.add(buttonNext, BorderLayout.EAST);


		//add panels to frame
		Container container= getContentPane();
		
		container.setLayout(new GridLayout(4,1));

		container.add(new JPanel());
		container.add(panelTask); 
		container.add(ps); 
		container.add(panelLow); 

	}


	/**
	 * Writes first row of CSV file (first line: column names)
	 */
	private void writeCSVfisrtLine() {
		String delim= ","; // comma

		String str= "filename" + delim + "nameListener" + delim + "ageListener" + delim + "genderListener" + delim + "languageListener" + delim;

		// For the questionnaire items, just write the right adjective
		str = str + "herzlich";

		try {

			Path fileOutput = Paths.get("files\\output\\output_GUI_SpeakerCharacteristics_"+ nameListener + ".csv");
			Files.write(fileOutput, str.getBytes(), StandardOpenOption.CREATE_NEW);

		}catch (IOException e) {
			e.printStackTrace();
		}

	}


}
