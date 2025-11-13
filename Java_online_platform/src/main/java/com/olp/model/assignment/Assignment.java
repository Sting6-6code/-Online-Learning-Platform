/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package com.olp.model.assignment;

import java.sql.Date;
import java.util.*;
import javax.persistence.*;
import com.olp.model.course.Course;
import com.olp.model.user.Student;
import com.olp.util.Utils;

/**
 * ===== ????????? =====
 * Task 0.7: 添加 JPA 注解
 */
// line 116 "model.ump"
// line 270 "model.ump"
@Entity
@Table(name = "assignments")
public class Assignment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Assignment Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "title", length = 200, nullable = false)
  private String title;
  
  @Column(name = "deadline", nullable = false)
  private Date deadline;
  
  @Column(name = "max_score", nullable = false)
  private int maxScore;

  //Assignment Associations
  @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Submission> assignmentSubmissions;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private com.olp.model.course.Course course;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Protected no-argument constructor for JPA
   */
  protected Assignment()
  {
    assignmentSubmissions = new ArrayList<Submission>();
  }

  public Assignment(String aId, String aTitle, Date aDeadline, int aMaxScore, com.olp.model.course.Course aCourse)
  {
    // Task 3.1: 添加构造函数验证
    if (aTitle == null || aTitle.trim().isEmpty()) {
      throw new IllegalArgumentException("Assignment title cannot be null or empty");
    }
    if (aMaxScore <= 0) {
      throw new IllegalArgumentException("Assignment maxScore must be greater than 0");
    }
    if (aDeadline == null) {
      throw new IllegalArgumentException("Assignment deadline cannot be null");
    }
    if (aCourse == null) {
      throw new IllegalArgumentException("Assignment course cannot be null");
    }
    
    id = aId;
    title = aTitle;
    deadline = aDeadline;
    maxScore = aMaxScore;
    assignmentSubmissions = new ArrayList<Submission>();
    boolean didAddCourse = setCourse(aCourse);
    if (!didAddCourse)
    {
      throw new RuntimeException("Unable to create courseAssignment due to course. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setDeadline(Date aDeadline)
  {
    boolean wasSet = false;
    deadline = aDeadline;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxScore(int aMaxScore)
  {
    boolean wasSet = false;
    maxScore = aMaxScore;
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

  public Date getDeadline()
  {
    return deadline;
  }

  public int getMaxScore()
  {
    return maxScore;
  }
  /* Code from template association_GetMany */
  public Submission getAssignmentSubmission(int index)
  {
    Submission aAssignmentSubmission = assignmentSubmissions.get(index);
    return aAssignmentSubmission;
  }

  public List<Submission> getAssignmentSubmissions()
  {
    List<Submission> newAssignmentSubmissions = Collections.unmodifiableList(assignmentSubmissions);
    return newAssignmentSubmissions;
  }

  public int numberOfAssignmentSubmissions()
  {
    int number = assignmentSubmissions.size();
    return number;
  }

  public boolean hasAssignmentSubmissions()
  {
    boolean has = assignmentSubmissions.size() > 0;
    return has;
  }

  public int indexOfAssignmentSubmission(Submission aAssignmentSubmission)
  {
    int index = assignmentSubmissions.indexOf(aAssignmentSubmission);
    return index;
  }
  /* Code from template association_GetOne */
  public com.olp.model.course.Course getCourse()
  {
    return course;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssignmentSubmissions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Submission addAssignmentSubmission(String aId, Date aSubmittedAt, int aVersion, boolean aCheckPassed, Grade aSubmissionGrade, Student aStudent)
  {
    return new Submission(aId, aSubmittedAt, aVersion, aCheckPassed, aSubmissionGrade, aStudent, this);
  }

  public boolean addAssignmentSubmission(Submission aAssignmentSubmission)
  {
    boolean wasAdded = false;
    if (assignmentSubmissions.contains(aAssignmentSubmission)) { return false; }
    Assignment existingAssignment = aAssignmentSubmission.getAssignment();
    boolean isNewAssignment = existingAssignment != null && !this.equals(existingAssignment);
    if (isNewAssignment)
    {
      aAssignmentSubmission.setAssignment(this);
    }
    else
    {
      assignmentSubmissions.add(aAssignmentSubmission);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAssignmentSubmission(Submission aAssignmentSubmission)
  {
    boolean wasRemoved = false;
    //Unable to remove aAssignmentSubmission, as it must always have a assignment
    if (!this.equals(aAssignmentSubmission.getAssignment()))
    {
      assignmentSubmissions.remove(aAssignmentSubmission);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssignmentSubmissionAt(Submission aAssignmentSubmission, int index)
  {  
    boolean wasAdded = false;
    if(addAssignmentSubmission(aAssignmentSubmission))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignmentSubmissions()) { index = numberOfAssignmentSubmissions() - 1; }
      assignmentSubmissions.remove(aAssignmentSubmission);
      assignmentSubmissions.add(index, aAssignmentSubmission);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssignmentSubmissionAt(Submission aAssignmentSubmission, int index)
  {
    boolean wasAdded = false;
    if(assignmentSubmissions.contains(aAssignmentSubmission))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignmentSubmissions()) { index = numberOfAssignmentSubmissions() - 1; }
      assignmentSubmissions.remove(aAssignmentSubmission);
      assignmentSubmissions.add(index, aAssignmentSubmission);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssignmentSubmissionAt(aAssignmentSubmission, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setCourse(com.olp.model.course.Course aCourse)
  {
    boolean wasSet = false;
    //Must provide course to courseAssignment
    if (aCourse == null)
    {
      return wasSet;
    }

    if (course != null && course.numberOfCourseAssignments() <= com.olp.model.course.Course.minimumNumberOfCourseAssignments())
    {
      return wasSet;
    }

    com.olp.model.course.Course existingCourse = course;
    course = aCourse;
    if (existingCourse != null && !existingCourse.equals(aCourse))
    {
      boolean didRemove = existingCourse.removeCourseAssignment(this);
      if (!didRemove)
      {
        course = existingCourse;
        return wasSet;
      }
    }
    course.addCourseAssignment(this);
    wasSet = true;
    return wasSet;
  }

  //------------------------
  // Convenience Methods (Task 3.1)
  //------------------------
  
  /**
   * 判断作业是否已过期
   * Task 3.1: 添加便捷方法
   * @return true 如果当前时间 > deadline，否则返回 false
   */
  public boolean isOverdue()
  {
    if (deadline == null) {
      return false;
    }
    Date currentTime = Utils.getCurrentTime();
    return Utils.compareDates(currentTime, deadline) > 0;
  }
  
  /**
   * 获取提交数量
   * Task 3.1: 添加便捷方法
   * @return assignmentSubmissions 的大小
   */
  public int getSubmissionCount()
  {
    return assignmentSubmissions.size();
  }

  public void delete()
  {
    for(int i=assignmentSubmissions.size(); i > 0; i--)
    {
      Submission aAssignmentSubmission = assignmentSubmissions.get(i - 1);
      aAssignmentSubmission.delete();
    }
    com.olp.model.course.Course placeholderCourse = course;
    this.course = null;
    if(placeholderCourse != null)
    {
      placeholderCourse.removeCourseAssignment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "title" + ":" + getTitle()+ "," +
            "maxScore" + ":" + getMaxScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "deadline" + "=" + (getDeadline() != null ? !getDeadline().equals(this)  ? getDeadline().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null");
  }
}