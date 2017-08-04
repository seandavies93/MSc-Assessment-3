import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
//class structure included in the given assessment template (methods and gui predefined)
//implementation by Sean Davies
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	//declare the FitnessProgram variable as an instance variable and the constant for the maximum number of classes that can take place in a day
	public FitnessProgram currentprogram;
	private static int MAXCLASSES=7;
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		
		
		initLadiesDay();//obtain the class information contained in the classesin file and store them in the currentprogram variable

		initAttendances();//obtain the attendance information associated with each class and update the current program variable with the information
		
		updateDisplay();//update the display with the information that has been found
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
	    
		String filestring="";
		
		//obtain the class information from the classesin file
		try 
		{
			//initialise the filereader object
			FileReader fin=new FileReader(classesInFile);
			while(true)
			{
				int c=fin.read();
				if(c==-1)
				{
					break;
				}
				
				char cin=(char) c;
				filestring+=cin;
			}
			fin.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//split the string up into an array where each entry refers to a specific class
		String[] classstringarray=filestring.split("[\r\n]+");
		String[] tempclassarray;
		FitnessClass[] program=new FitnessClass[classstringarray.length];
		
		//run through the entries of the class array and extract the information pertaining to each of them
		for(int i=0; i<classstringarray.length; i++)
		{
			tempclassarray=classstringarray[i].split(" ");
			program[i]=new FitnessClass(tempclassarray[0], tempclassarray[1], tempclassarray[2], Integer.parseInt(tempclassarray[3]));
		}
		
		//create a fitnessprogram object using the class information extracted from the classesin file
		currentprogram=new FitnessProgram(program);
		
		
		
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {
	   
		
		String filestring="";
		
		//obtain the information from the attendances file
		try
		{
			//initialise a filereader object for the attendances file
			FileReader fin=new FileReader(attendancesFile);
			while(true)
			{
				int c=fin.read();
				if(c==-1)
				{
					break;
				}
				
				char cin=(char) c;
				filestring+=cin;
			}
			fin.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		//split the information up into an array with each entry referring to a specific class
		String[] attendancestring=filestring.split("[\r\n]+");
		String[] tempattendancesarray;
		
		
		
		for(int i=0; i<attendancestring.length; i++)
		{
			//split the specific attendance log by the space character and use the resulting array to add the attendance information to the relevant fitnessclass
			tempattendancesarray=attendancestring[i].split(" ");
			currentprogram.updateProgramAttendances(tempattendancesarray);
		}
		
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
	    
		//set up the string to display the class information
		String displaystring="";
		
		//this for loop creates the time row
		for(int i=0; i<7; i++)
		{
			displaystring+=(i+9)+"\t\t";
		}
		
		displaystring+="\n";//moves to a new line
		
		//this for loop creates the row showing the class names
		for(int i=0; i<7; i++)
		{
				displaystring+=String.format("%-8s\t",currentprogram.getClassByNum(i).getName());
		}
		
		displaystring+="\n";//moves to a new line
		
		//this loop creates the row showing the instructor name
		for(int i=0; i<7; i++)
		{
				displaystring+=String.format("%-8s\t",currentprogram.getClassByNum(i).getInstructorName());
		}
		
		//add the text to the display
		display.setText(displaystring);
		
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding() {
	    
		//obtain the user input from the textfields
		String IDin=idIn.getText();
		String namein=classIn.getText();
		String tutorin=tutorIn.getText();
		
		//create a new fitness class and add it to the fitness program
		FitnessClass newclass=new FitnessClass(IDin, namein, tutorin, currentprogram.findEarliestTimeSlot());
		currentprogram.addFitnessClass(newclass);
		currentprogram.updateClassesFile();
		
		//update the display
		updateDisplay();
		
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {
		
		//get the class id
		String IDin=idIn.getText();
		
		//find the class with the id just obtained from the user delete it
		for(int i=0; i<MAXCLASSES; i++)
		{
			//here we check that the the id is equal to the id that the user has entered and if it is then that is the record we will update
			if(currentprogram.getClassByNum(i).getID().equals(IDin))
			{
				currentprogram.deleteFitnessClass(i);
			}
		}
		//once the program object has been updated we then update the classesin file and the display
		currentprogram.updateClassesFile();
		updateDisplay();
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {
		//create a reportframe object which will display the report in a separate gui
		ReportFrame report=new ReportFrame(currentprogram);	
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {
		System.exit(0);
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
	    
		//perform the appropriate action for the button pressed
		if(ae.getSource()==addButton)
		{
			processAdding();
		}
		else if(ae.getSource()==deleteButton)
		{
			processDeletion();
		}
		else if(ae.getSource()==attendanceButton)
		{
			displayReport();
		}
		else if(ae.getSource()==closeButton)
		{
			processSaveAndClose();
		}
		
	}
}
