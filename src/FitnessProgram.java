import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
//class suggested in assessment template code implemented by Sean Davies
public class FitnessProgram {
    
	//variable declarations
	private int MAXCLASSES=7;
	private FitnessClass[] program=new FitnessClass[MAXCLASSES];

	//takes an array of fitness classes and slots them into the appropriate time slot of the fitness program
	public FitnessProgram(FitnessClass[] programin)
	{
		//this for loop assigns all the classes to their relevant slot using their time to calculate the correct index
		for(int i=0; i<programin.length; i++)
		{
			this.program[programin[i].getStartTime()-9]=programin[i];
		}
		
		//sets the null objects of the available slots to an empty object
		for(int i=0; i<MAXCLASSES; i++)
		{
			if(program[i]==null)
			{
				program[i]=new FitnessClass();
			}
				
		}
		
	}
	
	//takes an attendance log extracted from the attendance file and adds it to the appropriate class in the FitnessProgram 
	public void updateProgramAttendances(String[] attendancelog)
	{			
		
		for(int i=0; i<MAXCLASSES; i++)
		{
				if((this.program[i].getID().equals(attendancelog[0])))
				{
					
					this.program[i].setAttendances(attendancelog);
				}
		}
	}
	//returns the class corresponding to a given index in the program array
	public FitnessClass getClassByNum(int classnum)
	{
		return program[classnum];
	}
	//searches through the program for the earliest available slot and returns the integer specifying the start time
	public int findEarliestTimeSlot()
	{
		int time=-1;
		for(int i=0; i<MAXCLASSES; i++)
		{
			if(program[i].getStartTime()==-1)
			{
				time=i+9;
				break;
			}	
		}
		return time;
	}
	//adds a fitness class to the program
	public void addFitnessClass(FitnessClass fitin)
	{
		this.program[fitin.getStartTime()-9].setID(fitin.getID());
		this.program[fitin.getStartTime()-9].setName(fitin.getName());
		this.program[fitin.getStartTime()-9].setInstructName(fitin.getInstructorName());
		this.program[fitin.getStartTime()-9].setStartTime(fitin.getStartTime());
		
	}
	//deletes a fitness program at the specified index
	public void deleteFitnessClass(int slotnum)
	{
		this.program[slotnum].setID(" ");
		this.program[slotnum].setName("Available");
		this.program[slotnum].setInstructName(" ");
		this.program[slotnum].setStartTime(-1);
	}
	//updates the classesin file according to changes made to the program data stored within this class
	public void updateClassesFile()
	{
		try
		{
			//set up a filewriter for the classesin file and the string that will be written to the file
			FileWriter write=new FileWriter("classesIn.txt");
			String classline="";
			for(int i=0; i<MAXCLASSES; i++)
			{
				if(this.program[i].getStartTime()!=-1)
				{
					classline+=this.program[i].getID()+" "+this.program[i].getName()+" "+this.program[i].getInstructorName()+" "+this.program[i].getStartTime()+"\r\n";
				}
			}
			
			//once we have set up the data in the appropriate format we then write it to the file and close the writer
			write.write(classline);
			write.close();
		}
		catch (IOException e)
		{
		
			e.printStackTrace();
		}
	}
}
