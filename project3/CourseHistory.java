
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/** 
 * this arralylist and its methods will allow a student to determine their porgess toward meeting graduation requirememnts. this 
 * class allows a student to check their course history, and summary of their gpa, their distribution area reqs.,
 * their competency req.s, and a sorted list of courses by gpa (highest to lowest). 
 * 
 * this declares the class, and creates an arraylist called courseList with completedcourse objects. 
 */
public class CourseHistory
{private ArrayList<CompletedCourse> courseList;
    /*
     * this is the constructor, giving the object completed course strings that it holds values for.
     */ 
    public CourseHistory()
    { 
        courseList= new ArrayList<CompletedCourse>();
        String department;
        String courseNumber;
        String semesterTaken;
        String credit;
        String grade;
        String competency;
        String distributionArea;
        
        try
        {
            FileReader reader= new FileReader("finishedcourses.txt");
            Scanner in= new Scanner(reader);

            while(in.hasNextLine())
            {
                department= in.nextLine();
                courseNumber=in.nextLine();
                semesterTaken=in.nextLine();
                credit=in.nextLine();
                grade=in.nextLine();
                competency=in.nextLine();
                distributionArea=in.nextLine();
                CompletedCourse theCourse= new CompletedCourse(department, courseNumber, semesterTaken, 
                        credit, grade, competency, distributionArea);
                courseList.add(theCourse);
            }
            in.close();
        }
        catch(IOException exception)

        {
            System.out.println("Error processing file:" + exception);
        }
    }

    /*
     * this method displays all the courses
     */

    public void displayCourseHistory()
    {
        System.out.println("Course History");
            for(int i=0; i<courseList.size(); i++)
            {
                courseList.get(i).displayCourse();
            }
    }

    /*
     * this method calculates  credits earned and the GPA, weighing the type of credit as well as 
     * using total credits taken even if you diddnt pass
     */
    public void displaySummaryReport()
    {
        double full=0.0;
        double quarter=0.0;
        double half=0.0;
        double totalcredits=0.0;
        double attempted=0.0;
        System.out.print("Summary report");
            for(int i=0; i<courseList.size();i++)
            {    
                attempted+= courseList.get(i).getCredit();
                
                    if(courseList.get(i).getCredit()==1.0 && courseList.get(i).getGrade()>0)
            {
                full +=  courseList.get(i).getGrade();

                totalcredits +=courseList.get(i).getCredit();

            }
                    else if(courseList.get(i).getCredit()==.25 && courseList.get(i).getGrade()>0) 
            {
                quarter +=  courseList.get(i).getGrade();
                totalcredits +=courseList.get(i).getCredit();

            }
                    else if(courseList.get(i).getCredit()==.50 && courseList.get(i).getGrade()>0) 
            {
                half += courseList.get(i).getGrade();

                totalcredits +=courseList.get(i).getCredit();

            }
        }
        half=half/2;
        quarter= quarter/4;

        full= (full+ quarter+half)/attempted;
        System.out.println("GPA:" + full +"Credits earned:"+totalcredits);
    }

    /*
     * this method is a like parent  method that we use to set the parameter in the following method
     */ 

    public void allDistributionGroupsReport()
    {
        System.out.println("*********************");
        System.out.println("Distribution Groups");
        System.out.println("*********************");
        distributionReport("SM");
        distributionReport("AH");
        distributionReport("SS");
        distributionReport("LA");
    }

    /*
     * this prints out what distribution the class is and how many credits taken in that section
     */ 
    public void distributionReport(String DistArea)
    { 
        int ct=0;
        for(int i=0; i<courseList.size();i++)
        {
            if(courseList.get(i).getDistArea().equals(DistArea)) 
            {
                if(courseList.get(i).getGrade()>0)
                {  
                    ct++;
                    courseList.get(i).displayCourse();
                }
            }
        }
        System.out.println("*********************");
        System.out.println(DistArea + ": " + ct);
        System.out.println("*********************");

    }

    /*
     * this sets up a parent method that takes in the value of the counter from the following method
     * it then stores that in the competency it counts towards. it also sets up if the competency was completed or incompleted
     */ 

    public void allComptetency()
    {
        System.out.println("*********************");
        System.out.println("Competency Report");
        System.out.println("*********************");
        int ctQ  = competencyReport("Q");
        int ctW = competencyReport("W");
        int ctS = competencyReport("S");

        if(ctQ>=1 && ctW>=1 && ctS>=1)
        {
            System.out.println("All competencies completed");

        }
        else if(ctQ==0 && ctW==0 && ctS==0)
        {
            System.out.println("No competencies completed");
        }
        else 
        {
            System.out.println("Competencies Partially Completed:");
            {if (ctQ>=1)
                {
                    System.out.println("Q is Completed");
                }
                else if( ctQ==0)
                {
                    System.out.println("Q is Incomplete");
                }
                if( ctW>=1)
                {
                    System.out.println("W is Completed");
                }
                else if( ctW==0)
                {
                    System.out.println("W is Incomplete");
                }
                if( ctS>=1)
                {
                    System.out.println("S is Completed");
                }

                else if( ctS==0)
                {
                    System.out.println("S is Incomplete");
                }
            }
        }
    }

    /*
     * this uses the parent method above, and uses the parameter to go through the arraylist and prints out the competencys
     * and if they were completed correctly. 
     */

    public int competencyReport(String competency)
    {    int ct=0;
        for( int i =0; i<courseList.size(); i++)
        { 
            if(courseList.get(i).getCompetency().equals(competency))
                if(courseList.get(i).getGrade()>0)
                {
                    courseList.get(i).getCompetency();
                    ct++;
                }
        }
        return ct;
    }

    /*
     * prints the whole report, all the methods above.
     */ 
    public void fullReport()
    {
        System.out.println("Full report");
        displaySummaryReport();
        allDistributionGroupsReport();
        allComptetency();
    }

    /*
     * sorts the GPAs, puts them from highest to lowest.
     */ 
    public void sortByGPA()
    {
        System.out.println("Sorted List");
        CompletedCourse temp;
        ArrayList<CompletedCourse> arr1= new ArrayList<CompletedCourse>();

        for(int k=0; k<courseList.size(); k++)
        {
            arr1.add(courseList.get(k));
        }
        for(int i=0; i<courseList.size()-1;i++)
        {
            for (int j=0; j<courseList.size()-1; j++) 
            {
                if (arr1.get(j).getGrade()< arr1.get(j+1).getGrade())
                {
                    temp = arr1.get(j);
                    arr1.set(j, arr1.get(j+1));
                    arr1.set(j+1, temp);
                }
            }

        }
        for (int i=0; i<courseList.size(); i++)
        {
            arr1.get(i).displayCourse();
        }
    }
}

