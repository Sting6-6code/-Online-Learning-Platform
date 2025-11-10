/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.sql.Date;

// line 90 "model.ump"
// line 250 "model.ump"
public class Enrollment
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum EnrollmentStatus { Active, Waitlisted, Dropped }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Enrollment Attributes
  private String id;
  private EnrollmentStatus status;
  private Date enrolledAt;

  //Enrollment Associations
  private Student student;
  private Course course;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Enrollment(String aId, EnrollmentStatus aStatus, Date aEnrolledAt, Student aStudent, Course aCourse)
  {
    id = aId;
    status = aStatus;
    enrolledAt = aEnrolledAt;
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create studentEnrollment due to student. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCourse = setCourse(aCourse);
    if (!didAddCourse)
    {
      throw new RuntimeException("Unable to create courseEnrollment due to course. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setStatus(EnrollmentStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setEnrolledAt(Date aEnrolledAt)
  {
    boolean wasSet = false;
    enrolledAt = aEnrolledAt;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public EnrollmentStatus getStatus()
  {
    return status;
  }

  public Date getEnrolledAt()
  {
    return enrolledAt;
  }
  /* Code from template association_GetOne */
  public Student getStudent()
  {
    return student;
  }
  /* Code from template association_GetOne */
  public Course getCourse()
  {
    return course;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeStudentEnrollment(this);
    }
    student.addStudentEnrollment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCourse(Course aCourse)
  {
    boolean wasSet = false;
    if (aCourse == null)
    {
      return wasSet;
    }

    Course existingCourse = course;
    course = aCourse;
    if (existingCourse != null && !existingCourse.equals(aCourse))
    {
      existingCourse.removeCourseEnrollment(this);
    }
    course.addCourseEnrollment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null)
    {
      placeholderStudent.removeStudentEnrollment(this);
    }
    Course placeholderCourse = course;
    this.course = null;
    if(placeholderCourse != null)
    {
      placeholderCourse.removeCourseEnrollment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "enrolledAt" + "=" + (getEnrolledAt() != null ? !getEnrolledAt().equals(this)  ? getEnrolledAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null");
  }
}