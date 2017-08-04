import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
//class and gui layout included in assessment template
//implementation by Sean Davies
public class ReportFrame extends JFrame {
	
        private JTextArea reportframe;
        
        public ReportFrame(FitnessProgram program)
        {
        	//initialise the report frame and the textarea 
        	setSize(200, 400);
        	
        	reportframe=new JTextArea();
        	JPanel pan=new JPanel();
        	pan.add(reportframe);
        	add(pan);
        	
        	//initialise the report string
        	String report="";
        	
        	//extract all the information pertaining to the class program and then add it to the report string
        	for(int i=0; i<7; i++)
        	{
        		if(program.getClassByNum(i).getStartTime()!=-1)
        		{
        			report+=String.format("%-10s\t%-10s\t%-10s\t%d\t", program.getClassByNum(i).getID(), program.getClassByNum(i).getName(), program.getClassByNum(i).getInstructorName(), program.getClassByNum(i).getStartTime());
        			report+=String.format("%-4d\t%-4d\t%-4d\t%-4d\t%-4d\t", program.getClassByNum(i).getAttendanceByIndex(0), 
        				program.getClassByNum(i).getAttendanceByIndex(1), 
        				program.getClassByNum(i).getAttendanceByIndex(2), 
        				program.getClassByNum(i).getAttendanceByIndex(3), 
        				program.getClassByNum(i).getAttendanceByIndex(4));
        		
        			double average=0.0;
        		
        			for(int j=0; j<5; j++)
        			{
        				average+=program.getClassByNum(i).getAttendanceByIndex(j);
        				System.out.println(program.getClassByNum(i).getAttendanceByIndex(j));
        			}
        		
        			average/=5;
        			report+=String.format("%.2f", average);
        			report+="\n";
        		}
        	}
        	
        	//set the text of the text area to the report string
        	reportframe.setText(report);
        	setVisible(true);
        	
        }
        
}

