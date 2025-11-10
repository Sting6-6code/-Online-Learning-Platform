/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.util.*;
import java.sql.Date;

/**
 * ===== ????????? =====
 */
// line 34 "model.ump"
// line 245 "model.ump"
public class Course
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum EnrollmentStatus { Active, Waitlisted, Dropped }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Course Attributes
  private String id;
  private String title;
  private int capacity;

  //Course State Machines
  public enum Status { Draft, Published, EnrollmentOpen, Waitlisted, InProgress, Completed, Cancelled }
  private Status status;

  //Course Associations
  private List<Lesson> lessons;
  private List<Enrollment> courseEnrollments;
  private List<Assignment> courseAssignments;
  private List<CourseCategory> categories;
  private Instructor instructor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Course(String aId, String aTitle, int aCapacity, Instructor aInstructor)
  {
    id = aId;
    title = aTitle;
    capacity = aCapacity;
    lessons = new ArrayList<Lesson>();
    courseEnrollments = new ArrayList<Enrollment>();
    courseAssignments = new ArrayList<Assignment>();
    categories = new ArrayList<CourseCategory>();
    boolean didAddInstructor = setInstructor(aInstructor);
    if (!didAddInstructor)
    {
      throw new RuntimeException("Unable to create taughtCourse due to instructor. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setStatus(Status.Draft);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setCapacity(int aCapacity)
  {
    boolean wasSet = false;
    capacity = aCapacity;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public String getTitle()
  {
    return title;
  }

  public int getCapacity()
  {
    return capacity;
  }

  public String getStatusFullName()
  {
    String answer = status.toString();
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  public boolean publish()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Draft:
        if (hasMinimumContent())
        {
          setStatus(Status.Published);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean openEnrollment()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Published:
        if (hasCapacity())
        {
          setStatus(Status.EnrollmentOpen);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Published:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case EnrollmentOpen:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case Waitlisted:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case InProgress:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startCourse()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case EnrollmentOpen:
        if (hasActiveEnrollments())
        {
          setStatus(Status.InProgress);
          wasEventProcessed = true;
          break;
        }
        break;
      case Waitlisted:
        if (hasActiveEnrollments())
        {
          setStatus(Status.InProgress);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean complete()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case InProgress:
        setStatus(Status.Completed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;
  }
  /* Code from template association_GetMany */
  public Lesson getLesson(int index)
  {
    Lesson aLesson = lessons.get(index);
    return aLesson;
  }

  /**
   * ????????????
   */
  public List<Lesson> getLessons()
  {
    List<Lesson> newLessons = Collections.unmodifiableList(lessons);
    return newLessons;
  }

  public int numberOfLessons()
  {
    int number = lessons.size();
    return number;
  }

  public boolean hasLessons()
  {
    boolean has = lessons.size() > 0;
    return has;
  }

  public int indexOfLesson(Lesson aLesson)
  {
    int index = lessons.indexOf(aLesson);
    return index;
  }
  /* Code from template association_GetMany */
  public Enrollment getCourseEnrollment(int index)
  {
    Enrollment aCourseEnrollment = courseEnrollments.get(index);
    return aCourseEnrollment;
  }

  /**
   * ????????????
   */
  public List<Enrollment> getCourseEnrollments()
  {
    List<Enrollment> newCourseEnrollments = Collections.unmodifiableList(courseEnrollments);
    return newCourseEnrollments;
  }

  public int numberOfCourseEnrollments()
  {
    int number = courseEnrollments.size();
    return number;
  }

  public boolean hasCourseEnrollments()
  {
    boolean has = courseEnrollments.size() > 0;
    return has;
  }

  public int indexOfCourseEnrollment(Enrollment aCourseEnrollment)
  {
    int index = courseEnrollments.indexOf(aCourseEnrollment);
    return index;
  }
  /* Code from template association_GetMany */
  public Assignment getCourseAssignment(int index)
  {
    Assignment aCourseAssignment = courseAssignments.get(index);
    return aCourseAssignment;
  }

  public List<Assignment> getCourseAssignments()
  {
    List<Assignment> newCourseAssignments = Collections.unmodifiableList(courseAssignments);
    return newCourseAssignments;
  }

  public int numberOfCourseAssignments()
  {
    int number = courseAssignments.size();
    return number;
  }

  public boolean hasCourseAssignments()
  {
    boolean has = courseAssignments.size() > 0;
    return has;
  }

  public int indexOfCourseAssignment(Assignment aCourseAssignment)
  {
    int index = courseAssignments.indexOf(aCourseAssignment);
    return index;
  }
  /* Code from template association_GetMany */
  public CourseCategory getCategory(int index)
  {
    CourseCategory aCategory = categories.get(index);
    return aCategory;
  }

  public List<CourseCategory> getCategories()
  {
    List<CourseCategory> newCategories = Collections.unmodifiableList(categories);
    return newCategories;
  }

  public int numberOfCategories()
  {
    int number = categories.size();
    return number;
  }

  public boolean hasCategories()
  {
    boolean has = categories.size() > 0;
    return has;
  }

  public int indexOfCategory(CourseCategory aCategory)
  {
    int index = categories.indexOf(aCategory);
    return index;
  }
  /* Code from template association_GetOne */
  public Instructor getInstructor()
  {
    return instructor;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfLessonsValid()
  {
    boolean isValid = numberOfLessons() >= minimumNumberOfLessons();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLessons()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Lesson addLesson(String aId, String aTitle, int aOrderIndex)
  {
    Lesson aNewLesson = new Lesson(aId, aTitle, aOrderIndex, this);
    return aNewLesson;
  }

  public boolean addLesson(Lesson aLesson)
  {
    boolean wasAdded = false;
    if (lessons.contains(aLesson)) { return false; }
    Course existingCourse = aLesson.getCourse();
    boolean isNewCourse = existingCourse != null && !this.equals(existingCourse);

    if (isNewCourse && existingCourse.numberOfLessons() <= minimumNumberOfLessons())
    {
      return wasAdded;
    }
    if (isNewCourse)
    {
      aLesson.setCourse(this);
    }
    else
    {
      lessons.add(aLesson);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLesson(Lesson aLesson)
  {
    boolean wasRemoved = false;
    //Unable to remove aLesson, as it must always have a course
    if (this.equals(aLesson.getCourse()))
    {
      return wasRemoved;
    }

    //course already at minimum (1)
    if (numberOfLessons() <= minimumNumberOfLessons())
    {
      return wasRemoved;
    }

    lessons.remove(aLesson);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLessonAt(Lesson aLesson, int index)
  {  
    boolean wasAdded = false;
    if(addLesson(aLesson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLessons()) { index = numberOfLessons() - 1; }
      lessons.remove(aLesson);
      lessons.add(index, aLesson);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLessonAt(Lesson aLesson, int index)
  {
    boolean wasAdded = false;
    if(lessons.contains(aLesson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLessons()) { index = numberOfLessons() - 1; }
      lessons.remove(aLesson);
      lessons.add(index, aLesson);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLessonAt(aLesson, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCourseEnrollments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Enrollment addCourseEnrollment(String aId, EnrollmentStatus aStatus, Date aEnrolledAt, Student aStudent)
  {
    return new Enrollment(aId, aStatus, aEnrolledAt, aStudent, this);
  }

  public boolean addCourseEnrollment(Enrollment aCourseEnrollment)
  {
    boolean wasAdded = false;
    if (courseEnrollments.contains(aCourseEnrollment)) { return false; }
    Course existingCourse = aCourseEnrollment.getCourse();
    boolean isNewCourse = existingCourse != null && !this.equals(existingCourse);
    if (isNewCourse)
    {
      aCourseEnrollment.setCourse(this);
    }
    else
    {
      courseEnrollments.add(aCourseEnrollment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCourseEnrollment(Enrollment aCourseEnrollment)
  {
    boolean wasRemoved = false;
    //Unable to remove aCourseEnrollment, as it must always have a course
    if (!this.equals(aCourseEnrollment.getCourse()))
    {
      courseEnrollments.remove(aCourseEnrollment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCourseEnrollmentAt(Enrollment aCourseEnrollment, int index)
  {  
    boolean wasAdded = false;
    if(addCourseEnrollment(aCourseEnrollment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourseEnrollments()) { index = numberOfCourseEnrollments() - 1; }
      courseEnrollments.remove(aCourseEnrollment);
      courseEnrollments.add(index, aCourseEnrollment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCourseEnrollmentAt(Enrollment aCourseEnrollment, int index)
  {
    boolean wasAdded = false;
    if(courseEnrollments.contains(aCourseEnrollment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourseEnrollments()) { index = numberOfCourseEnrollments() - 1; }
      courseEnrollments.remove(aCourseEnrollment);
      courseEnrollments.add(index, aCourseEnrollment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCourseEnrollmentAt(aCourseEnrollment, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfCourseAssignmentsValid()
  {
    boolean isValid = numberOfCourseAssignments() >= minimumNumberOfCourseAssignments();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCourseAssignments()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Assignment addCourseAssignment(String aId, String aTitle, Date aDeadline, int aMaxScore)
  {
    Assignment aNewCourseAssignment = new Assignment(aId, aTitle, aDeadline, aMaxScore, this);
    return aNewCourseAssignment;
  }

  public boolean addCourseAssignment(Assignment aCourseAssignment)
  {
    boolean wasAdded = false;
    if (courseAssignments.contains(aCourseAssignment)) { return false; }
    Course existingCourse = aCourseAssignment.getCourse();
    boolean isNewCourse = existingCourse != null && !this.equals(existingCourse);

    if (isNewCourse && existingCourse.numberOfCourseAssignments() <= minimumNumberOfCourseAssignments())
    {
      return wasAdded;
    }
    if (isNewCourse)
    {
      aCourseAssignment.setCourse(this);
    }
    else
    {
      courseAssignments.add(aCourseAssignment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCourseAssignment(Assignment aCourseAssignment)
  {
    boolean wasRemoved = false;
    //Unable to remove aCourseAssignment, as it must always have a course
    if (this.equals(aCourseAssignment.getCourse()))
    {
      return wasRemoved;
    }

    //course already at minimum (1)
    if (numberOfCourseAssignments() <= minimumNumberOfCourseAssignments())
    {
      return wasRemoved;
    }

    courseAssignments.remove(aCourseAssignment);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCourseAssignmentAt(Assignment aCourseAssignment, int index)
  {  
    boolean wasAdded = false;
    if(addCourseAssignment(aCourseAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourseAssignments()) { index = numberOfCourseAssignments() - 1; }
      courseAssignments.remove(aCourseAssignment);
      courseAssignments.add(index, aCourseAssignment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCourseAssignmentAt(Assignment aCourseAssignment, int index)
  {
    boolean wasAdded = false;
    if(courseAssignments.contains(aCourseAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourseAssignments()) { index = numberOfCourseAssignments() - 1; }
      courseAssignments.remove(aCourseAssignment);
      courseAssignments.add(index, aCourseAssignment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCourseAssignmentAt(aCourseAssignment, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCategories()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCategory(CourseCategory aCategory)
  {
    boolean wasAdded = false;
    if (categories.contains(aCategory)) { return false; }
    categories.add(aCategory);
    if (aCategory.indexOfCourse(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCategory.addCourse(this);
      if (!wasAdded)
      {
        categories.remove(aCategory);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCategory(CourseCategory aCategory)
  {
    boolean wasRemoved = false;
    if (!categories.contains(aCategory))
    {
      return wasRemoved;
    }

    int oldIndex = categories.indexOf(aCategory);
    categories.remove(oldIndex);
    if (aCategory.indexOfCourse(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCategory.removeCourse(this);
      if (!wasRemoved)
      {
        categories.add(oldIndex,aCategory);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCategoryAt(CourseCategory aCategory, int index)
  {  
    boolean wasAdded = false;
    if(addCategory(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCategoryAt(CourseCategory aCategory, int index)
  {
    boolean wasAdded = false;
    if(categories.contains(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCategoryAt(aCategory, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setInstructor(Instructor aInstructor)
  {
    boolean wasSet = false;
    if (aInstructor == null)
    {
      return wasSet;
    }

    Instructor existingInstructor = instructor;
    instructor = aInstructor;
    if (existingInstructor != null && !existingInstructor.equals(aInstructor))
    {
      existingInstructor.removeTaughtCourse(this);
    }
    instructor.addTaughtCourse(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (lessons.size() > 0)
    {
      Lesson aLesson = lessons.get(lessons.size() - 1);
      aLesson.delete();
      lessons.remove(aLesson);
    }
    
    for(int i=courseEnrollments.size(); i > 0; i--)
    {
      Enrollment aCourseEnrollment = courseEnrollments.get(i - 1);
      aCourseEnrollment.delete();
    }
    for(int i=courseAssignments.size(); i > 0; i--)
    {
      Assignment aCourseAssignment = courseAssignments.get(i - 1);
      aCourseAssignment.delete();
    }
    ArrayList<CourseCategory> copyOfCategories = new ArrayList<CourseCategory>(categories);
    categories.clear();
    for(CourseCategory aCategory : copyOfCategories)
    {
      aCategory.removeCourse(this);
    }
    Instructor placeholderInstructor = instructor;
    this.instructor = null;
    if(placeholderInstructor != null)
    {
      placeholderInstructor.removeTaughtCourse(this);
    }
  }

  // line 73 "model.ump"
  public Boolean hasMinimumContent(){
    return numberOfLessons() >= 1 && numberOfCourseAssignments() >= 1;
  }

  // line 77 "model.ump"
  public Boolean hasCapacity(){
    return capacity > 0;
  }

  // line 81 "model.ump"
  public Boolean hasActiveEnrollments(){
    for(Enrollment e : courseEnrollments) {
      if(e.getStatus() == EnrollmentStatus.Active) {
        return true;
      }
    }
    return false;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "title" + ":" + getTitle()+ "," +
            "capacity" + ":" + getCapacity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "instructor = "+(getInstructor()!=null?Integer.toHexString(System.identityHashCode(getInstructor())):"null");
  }
}