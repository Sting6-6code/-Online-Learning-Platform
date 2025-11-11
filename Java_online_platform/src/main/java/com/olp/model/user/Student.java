package com.olp.model.user;

import java.util.*;
import java.sql.Date;
import javax.persistence.*;
import com.olp.model.course.Enrollment;
import com.olp.model.course.Course;
import com.olp.model.assignment.Submission;
import com.olp.model.assignment.Assignment;
import com.olp.model.assignment.Grade;

// line 18 "model.ump"
// line 230 "model.ump"
@Entity
@Table(name = "students")
public class Student extends User
{

  //------------------------
  // ENUMERATIONS
  //------------------------
  // Note: EnrollmentStatus is defined in Enrollment class

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Student Associations
  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Enrollment> studentEnrollments;
  
  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Submission> studentSubmissions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Student(String aId, String aName, String aEmail)
  {
    super(aId, aName, aEmail);
    studentEnrollments = new ArrayList<Enrollment>();
    studentSubmissions = new ArrayList<Submission>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Enrollment getStudentEnrollment(int index)
  {
    Enrollment aStudentEnrollment = studentEnrollments.get(index);
    return aStudentEnrollment;
  }

  public List<Enrollment> getStudentEnrollments()
  {
    List<Enrollment> newStudentEnrollments = Collections.unmodifiableList(studentEnrollments);
    return newStudentEnrollments;
  }

  public int numberOfStudentEnrollments()
  {
    int number = studentEnrollments.size();
    return number;
  }

  public boolean hasStudentEnrollments()
  {
    boolean has = studentEnrollments.size() > 0;
    return has;
  }

  public int indexOfStudentEnrollment(Enrollment aStudentEnrollment)
  {
    int index = studentEnrollments.indexOf(aStudentEnrollment);
    return index;
  }
  /* Code from template association_GetMany */
  public Submission getStudentSubmission(int index)
  {
    Submission aStudentSubmission = studentSubmissions.get(index);
    return aStudentSubmission;
  }

  public List<Submission> getStudentSubmissions()
  {
    List<Submission> newStudentSubmissions = Collections.unmodifiableList(studentSubmissions);
    return newStudentSubmissions;
  }

  public int numberOfStudentSubmissions()
  {
    int number = studentSubmissions.size();
    return number;
  }

  public boolean hasStudentSubmissions()
  {
    boolean has = studentSubmissions.size() > 0;
    return has;
  }

  public int indexOfStudentSubmission(Submission aStudentSubmission)
  {
    int index = studentSubmissions.indexOf(aStudentSubmission);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStudentEnrollments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Enrollment addStudentEnrollment(String aId, Enrollment.EnrollmentStatus aStatus, Date aEnrolledAt, Course aCourse)
  {
    return new Enrollment(aId, aStatus, aEnrolledAt, this, aCourse);
  }

  public boolean addStudentEnrollment(Enrollment aStudentEnrollment)
  {
    boolean wasAdded = false;
    if (studentEnrollments.contains(aStudentEnrollment)) { return false; }
    Student existingStudent = aStudentEnrollment.getStudent();
    boolean isNewStudent = existingStudent != null && !this.equals(existingStudent);
    if (isNewStudent)
    {
      aStudentEnrollment.setStudent(this);
    }
    else
    {
      studentEnrollments.add(aStudentEnrollment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStudentEnrollment(Enrollment aStudentEnrollment)
  {
    boolean wasRemoved = false;
    //Unable to remove aStudentEnrollment, as it must always have a student
    if (!this.equals(aStudentEnrollment.getStudent()))
    {
      studentEnrollments.remove(aStudentEnrollment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addStudentEnrollmentAt(Enrollment aStudentEnrollment, int index)
  {  
    boolean wasAdded = false;
    if(addStudentEnrollment(aStudentEnrollment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudentEnrollments()) { index = numberOfStudentEnrollments() - 1; }
      studentEnrollments.remove(aStudentEnrollment);
      studentEnrollments.add(index, aStudentEnrollment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStudentEnrollmentAt(Enrollment aStudentEnrollment, int index)
  {
    boolean wasAdded = false;
    if(studentEnrollments.contains(aStudentEnrollment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudentEnrollments()) { index = numberOfStudentEnrollments() - 1; }
      studentEnrollments.remove(aStudentEnrollment);
      studentEnrollments.add(index, aStudentEnrollment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStudentEnrollmentAt(aStudentEnrollment, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStudentSubmissions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Submission addStudentSubmission(String aId, Date aSubmittedAt, int aVersion, boolean aCheckPassed, Grade aSubmissionGrade, Assignment aAssignment)
  {
    return new Submission(aId, aSubmittedAt, aVersion, aCheckPassed, aSubmissionGrade, this, aAssignment);
  }

  public boolean addStudentSubmission(Submission aStudentSubmission)
  {
    boolean wasAdded = false;
    if (studentSubmissions.contains(aStudentSubmission)) { return false; }
    Student existingStudent = aStudentSubmission.getStudent();
    boolean isNewStudent = existingStudent != null && !this.equals(existingStudent);
    if (isNewStudent)
    {
      aStudentSubmission.setStudent(this);
    }
    else
    {
      studentSubmissions.add(aStudentSubmission);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStudentSubmission(Submission aStudentSubmission)
  {
    boolean wasRemoved = false;
    //Unable to remove aStudentSubmission, as it must always have a student
    if (!this.equals(aStudentSubmission.getStudent()))
    {
      studentSubmissions.remove(aStudentSubmission);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addStudentSubmissionAt(Submission aStudentSubmission, int index)
  {  
    boolean wasAdded = false;
    if(addStudentSubmission(aStudentSubmission))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudentSubmissions()) { index = numberOfStudentSubmissions() - 1; }
      studentSubmissions.remove(aStudentSubmission);
      studentSubmissions.add(index, aStudentSubmission);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStudentSubmissionAt(Submission aStudentSubmission, int index)
  {
    boolean wasAdded = false;
    if(studentSubmissions.contains(aStudentSubmission))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudentSubmissions()) { index = numberOfStudentSubmissions() - 1; }
      studentSubmissions.remove(aStudentSubmission);
      studentSubmissions.add(index, aStudentSubmission);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStudentSubmissionAt(aStudentSubmission, index);
    }
    return wasAdded;
  }

  //------------------------
  // Convenience Methods (Task 1.2)
  //------------------------
  
  /**
   * 统计 Active 状态的 Enrollment 数量
   * Task 1.2: 添加便捷方法
   */
  public int getActiveEnrollmentCount()
  {
    int count = 0;
    for (Enrollment enrollment : studentEnrollments) {
      if (enrollment.getStatus() == Enrollment.EnrollmentStatus.Active) {
        count++;
      }
    }
    return count;
  }

  public void delete()
  {
    for(int i=studentEnrollments.size(); i > 0; i--)
    {
      Enrollment aStudentEnrollment = studentEnrollments.get(i - 1);
      aStudentEnrollment.delete();
    }
    for(int i=studentSubmissions.size(); i > 0; i--)
    {
      Submission aStudentSubmission = studentSubmissions.get(i - 1);
      aStudentSubmission.delete();
    }
    super.delete();
  }

}

