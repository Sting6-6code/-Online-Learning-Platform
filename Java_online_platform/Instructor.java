/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.util.*;

// line 24 "model.ump"
// line 235 "model.ump"
public class Instructor extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Instructor Associations
  private List<Course> taughtCourses;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Instructor(String aId, String aName, String aEmail)
  {
    super(aId, aName, aEmail);
    taughtCourses = new ArrayList<Course>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Course getTaughtCourse(int index)
  {
    Course aTaughtCourse = taughtCourses.get(index);
    return aTaughtCourse;
  }

  public List<Course> getTaughtCourses()
  {
    List<Course> newTaughtCourses = Collections.unmodifiableList(taughtCourses);
    return newTaughtCourses;
  }

  public int numberOfTaughtCourses()
  {
    int number = taughtCourses.size();
    return number;
  }

  public boolean hasTaughtCourses()
  {
    boolean has = taughtCourses.size() > 0;
    return has;
  }

  public int indexOfTaughtCourse(Course aTaughtCourse)
  {
    int index = taughtCourses.indexOf(aTaughtCourse);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTaughtCourses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Course addTaughtCourse(String aId, String aTitle, int aCapacity)
  {
    return new Course(aId, aTitle, aCapacity, this);
  }

  public boolean addTaughtCourse(Course aTaughtCourse)
  {
    boolean wasAdded = false;
    if (taughtCourses.contains(aTaughtCourse)) { return false; }
    Instructor existingInstructor = aTaughtCourse.getInstructor();
    boolean isNewInstructor = existingInstructor != null && !this.equals(existingInstructor);
    if (isNewInstructor)
    {
      aTaughtCourse.setInstructor(this);
    }
    else
    {
      taughtCourses.add(aTaughtCourse);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTaughtCourse(Course aTaughtCourse)
  {
    boolean wasRemoved = false;
    //Unable to remove aTaughtCourse, as it must always have a instructor
    if (!this.equals(aTaughtCourse.getInstructor()))
    {
      taughtCourses.remove(aTaughtCourse);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTaughtCourseAt(Course aTaughtCourse, int index)
  {  
    boolean wasAdded = false;
    if(addTaughtCourse(aTaughtCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTaughtCourses()) { index = numberOfTaughtCourses() - 1; }
      taughtCourses.remove(aTaughtCourse);
      taughtCourses.add(index, aTaughtCourse);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTaughtCourseAt(Course aTaughtCourse, int index)
  {
    boolean wasAdded = false;
    if(taughtCourses.contains(aTaughtCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTaughtCourses()) { index = numberOfTaughtCourses() - 1; }
      taughtCourses.remove(aTaughtCourse);
      taughtCourses.add(index, aTaughtCourse);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTaughtCourseAt(aTaughtCourse, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=taughtCourses.size(); i > 0; i--)
    {
      Course aTaughtCourse = taughtCourses.get(i - 1);
      aTaughtCourse.delete();
    }
    super.delete();
  }

}