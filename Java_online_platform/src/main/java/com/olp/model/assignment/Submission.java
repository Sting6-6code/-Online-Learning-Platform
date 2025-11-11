/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package com.olp.model.assignment;

import java.sql.Date;
import javax.persistence.*;
import com.olp.model.user.Student;

// line 125 "model.ump"
// line 275 "model.ump"
@Entity
@Table(name = "submissions")
public class Submission
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Submission Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "submitted_at")
  private Date submittedAt;
  
  @Column(name = "version", nullable = false)
  private int version;
  
  @Column(name = "check_passed", nullable = false)
  private boolean checkPassed;

  //Submission State Machines
  public enum Status { Created, Submitted, UnderCheck, Grading, Graded, Returned, ResubmissionRequested }
  
  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 30, nullable = false)
  private Status status;

  //Submission Associations
  @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
  private com.olp.model.assignment.Grade submissionGrade;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private com.olp.model.user.Student student;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignment_id", nullable = false)
  private com.olp.model.assignment.Assignment assignment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Submission(String aId, Date aSubmittedAt, int aVersion, boolean aCheckPassed, com.olp.model.assignment.Grade aSubmissionGrade, com.olp.model.user.Student aStudent, com.olp.model.assignment.Assignment aAssignment)
  {
    id = aId;
    submittedAt = aSubmittedAt;
    version = aVersion;
    checkPassed = aCheckPassed;
    boolean didAddSubmissionGrade = setSubmissionGrade(aSubmissionGrade);
    if (!didAddSubmissionGrade)
    {
      throw new RuntimeException("Unable to create submission due to submissionGrade. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create studentSubmission due to student. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAssignment = setAssignment(aAssignment);
    if (!didAddAssignment)
    {
      throw new RuntimeException("Unable to create assignmentSubmission due to assignment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setStatus(Status.Created);
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

  public boolean setSubmittedAt(Date aSubmittedAt)
  {
    boolean wasSet = false;
    submittedAt = aSubmittedAt;
    wasSet = true;
    return wasSet;
  }

  public boolean setVersion(int aVersion)
  {
    boolean wasSet = false;
    version = aVersion;
    wasSet = true;
    return wasSet;
  }

  public boolean setCheckPassed(boolean aCheckPassed)
  {
    boolean wasSet = false;
    checkPassed = aCheckPassed;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public Date getSubmittedAt()
  {
    return submittedAt;
  }

  public int getVersion()
  {
    return version;
  }

  public boolean getCheckPassed()
  {
    return checkPassed;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isCheckPassed()
  {
    return checkPassed;
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

  public boolean submit()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Created:
        if (isBeforeDeadline())
        {
          setStatus(Status.Submitted);
          wasEventProcessed = true;
          break;
        }
        break;
      case ResubmissionRequested:
        if (isBeforeDeadline())
        {
          setStatus(Status.Submitted);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startAutoChecks()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Submitted:
        setStatus(Status.UnderCheck);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startGrading()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Submitted:
        if (getCheckPassed())
        {
          setStatus(Status.Grading);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean checksPass()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case UnderCheck:
        setStatus(Status.Submitted);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean checksFail()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case UnderCheck:
        setStatus(Status.Returned);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean grade()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Grading:
        setStatus(Status.Graded);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean requestResubmission()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Graded:
        if (canResubmit())
        {
          setStatus(Status.ResubmissionRequested);
          wasEventProcessed = true;
          break;
        }
        break;
      case Returned:
        if (canResubmit())
        {
          setStatus(Status.ResubmissionRequested);
          wasEventProcessed = true;
          break;
        }
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
  /* Code from template association_GetOne */
  public com.olp.model.assignment.Grade getSubmissionGrade()
  {
    return submissionGrade;
  }
  /* Code from template association_GetOne */
  public com.olp.model.user.Student getStudent()
  {
    return student;
  }
  /* Code from template association_GetOne */
  public com.olp.model.assignment.Assignment getAssignment()
  {
    return assignment;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setSubmissionGrade(com.olp.model.assignment.Grade aNewSubmissionGrade)
  {
    boolean wasSet = false;
    if (aNewSubmissionGrade == null)
    {
      //Unable to setSubmissionGrade to null, as submission must always be associated to a submissionGrade
      return wasSet;
    }
    
    Submission existingSubmission = aNewSubmissionGrade.getSubmission();
    if (existingSubmission != null && !equals(existingSubmission))
    {
      //Unable to setSubmissionGrade, the current submissionGrade already has a submission, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    com.olp.model.assignment.Grade anOldSubmissionGrade = submissionGrade;
    submissionGrade = aNewSubmissionGrade;
    submissionGrade.setSubmission(this);

    if (anOldSubmissionGrade != null)
    {
      anOldSubmissionGrade.setSubmission(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStudent(com.olp.model.user.Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    com.olp.model.user.Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeStudentSubmission(this);
    }
    student.addStudentSubmission(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssignment(com.olp.model.assignment.Assignment aAssignment)
  {
    boolean wasSet = false;
    if (aAssignment == null)
    {
      return wasSet;
    }

    com.olp.model.assignment.Assignment existingAssignment = assignment;
    assignment = aAssignment;
    if (existingAssignment != null && !existingAssignment.equals(aAssignment))
    {
      existingAssignment.removeAssignmentSubmission(this);
    }
    assignment.addAssignmentSubmission(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    com.olp.model.assignment.Grade existingSubmissionGrade = submissionGrade;
    submissionGrade = null;
    if (existingSubmissionGrade != null)
    {
      existingSubmissionGrade.setSubmission(null);
    }
    com.olp.model.user.Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null)
    {
      placeholderStudent.removeStudentSubmission(this);
    }
    com.olp.model.assignment.Assignment placeholderAssignment = assignment;
    this.assignment = null;
    if(placeholderAssignment != null)
    {
      placeholderAssignment.removeAssignmentSubmission(this);
    }
  }

  // line 161 "model.ump"
  public Boolean isBeforeDeadline(){
    return submittedAt != null;
  }

  // line 165 "model.ump"
  public Boolean canResubmit(){
    return true;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "version" + ":" + getVersion()+ "," +
            "checkPassed" + ":" + getCheckPassed()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "submittedAt" + "=" + (getSubmittedAt() != null ? !getSubmittedAt().equals(this)  ? getSubmittedAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "submissionGrade = "+(getSubmissionGrade()!=null?Integer.toHexString(System.identityHashCode(getSubmissionGrade())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assignment = "+(getAssignment()!=null?Integer.toHexString(System.identityHashCode(getAssignment())):"null");
  }
}