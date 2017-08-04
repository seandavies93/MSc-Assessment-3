/** Defines an object representing a single fitness class
 */
//class suggested with the template implementation created by Sean Davies
public class FitnessClass implements Comparable<FitnessClass> {


	private String ID;
	private String name;
	private String instructname;
	private int starttime;
	private int[] attendances;
	//default constructor that takes no parameters in the case that we want to initialise the class but don't know its attributes yet
	public FitnessClass()
	{
		this.ID="";
		this.name="Available";
		this.instructname="";
		this.starttime=-1;
		this.attendances=new int[5];
	}
	//full constructor
	public FitnessClass(String IDin, String namein, String instructnamein, int startimein)
	{
		this.ID=IDin;
		this.name=namein;
		this.instructname=instructnamein;
		this.starttime=startimein;
		this.attendances=new int[5];
	}
	
	//takes an extracted attendance log array and uses it to set the attendance information for the current fitness class
	public void setAttendances(String[] attendancelog)
	{
		for(int i=1; i<6; i++)
		{
			this.attendances[i-1]=Integer.parseInt(attendancelog[i]);
		}
	}
	
	//setters for the instance variables
	
	public void setName(String namein)//sets the name
	{
		this.name=namein;
	}
	public void setID(String IDin)//sets the ID
	{
		this.ID=IDin;
	}
	public void setInstructName(String instructnamein)//sets the instructor name
	{
		this.instructname=instructnamein;
	}
	public void setStartTime(int starttimein)//sets the start time
	{
		this.starttime=starttimein;
	}
	
	//getters for the instance variables
	
	public int getStartTime()//returns the start time
	{
		return this.starttime;
	}
	public String getName()//returns the name
	{
		return this.name;
	}
	public String getInstructorName()//returns the instructor name
	{
		return this.instructname;
	}
	public String getID()//returns the ID
	{
		return ID;
	}
	
	public int getAttendanceByIndex(int index)//returns the attendance corresponding to the slot index
	{
		return attendances[index];
	}
    public int compareTo(FitnessClass other) {
	  return 0; // replace with your code
    }
}