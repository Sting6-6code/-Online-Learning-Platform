/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package com.olp.model.assignment;

import java.sql.Date;
import javax.persistence.*;
import com.olp.model.user.Student;
import com.olp.util.Utils;

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
    // Task 3.3: 添加构造函数验证
    if (aStudent == null) {
      throw new IllegalArgumentException("Submission student cannot be null");
    }
    if (aAssignment == null) {
      throw new IllegalArgumentException("Submission assignment cannot be null");
    }
    
    id = aId;
    submittedAt = aSubmittedAt; // submittedAt 初始可为 null
    version = aVersion; // 初始 version 为构造函数参数值
    checkPassed = aCheckPassed; // 初始 checkPassed 为构造函数参数值
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
    setStatus(Status.Created); // 初始状态为 Created
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

  /**
   * Task 3.4: 实现 submit() 方法
   * 提交作业：从 Created 状态转换到 Submitted 状态
   */
  public boolean submit()
  {
    // 守卫条件：当前状态必须是 Created
    if (status != Status.Created && status != Status.ResubmissionRequested) {
      return false;
    }
    
    // 守卫条件：当前时间 <= assignment.getDeadline()
    Date currentTime = Utils.getCurrentTime();
    if (assignment == null || assignment.getDeadline() == null) {
      return false;
    }
    if (Utils.compareDates(currentTime, assignment.getDeadline()) > 0) {
      return false; // 超过截止时间
    }
    
    // 设置 submittedAt
    setSubmittedAt(currentTime);
    
    // 计算版本号：统计该学生对该作业的已有提交数 + 1
    int existingCount = 0;
    if (assignment != null && student != null) {
      for (int i = 0; i < assignment.numberOfAssignmentSubmissions(); i++) {
        Submission existingSubmission = assignment.getAssignmentSubmission(i);
        if (existingSubmission.getStudent() != null && 
            existingSubmission.getStudent().equals(student) &&
            !existingSubmission.equals(this)) {
          existingCount++;
        }
      }
    }
    setVersion(existingCount + 1);
    
    // 状态转换
    setStatus(Status.Submitted);
    return true;
  }

  /**
   * Task 3.5: 实现 startAutoChecks() 方法
   * 触发自动检查：从 Submitted 状态转换到 UnderCheck 状态
   * 注意：实际的检查逻辑（查重、编译等）由外部系统触发，此方法仅负责状态转换
   */
  public boolean startAutoChecks()
  {
    // 守卫条件：当前状态必须是 Submitted
    if (status != Status.Submitted) {
      return false;
    }
    
    // 状态转换
    setStatus(Status.UnderCheck);
    return true;
  }

  /**
   * Task 3.7: 实现 startGrading() 方法
   * 开始评分：从 Submitted 状态转换到 Grading 状态
   */
  public boolean startGrading()
  {
    // 守卫条件：当前状态必须是 Submitted
    if (status != Status.Submitted) {
      return false;
    }
    
    // 守卫条件：checkPassed == true
    if (!getCheckPassed()) {
      return false;
    }
    
    // 状态转换
    setStatus(Status.Grading);
    return true;
  }

  /**
   * Task 3.6: 实现 checksPass() 方法
   * 检查通过：从 UnderCheck 状态转换回 Submitted 状态（标记为可评分）
   */
  public boolean checksPass()
  {
    // 守卫条件：当前状态必须是 UnderCheck
    if (status != Status.UnderCheck) {
      return false;
    }
    
    // 设置 checkPassed := true
    setCheckPassed(true);
    
    // 状态转换：回到 Submitted（标记为可评分）
    setStatus(Status.Submitted);
    return true;
  }

  /**
   * Task 3.6: 实现 checksFail() 方法
   * 检查失败：从 UnderCheck 状态转换到 Returned 状态
   */
  public boolean checksFail()
  {
    // 守卫条件：当前状态必须是 UnderCheck
    if (status != Status.UnderCheck) {
      return false;
    }
    
    // 设置 checkPassed := false
    setCheckPassed(false);
    
    // 状态转换：转到 Returned
    setStatus(Status.Returned);
    return true;
  }

  /**
   * Task 3.8: 实现 grade() 方法（带参数）
   * 评分：从 Grading 状态转换到 Graded 状态，创建或更新 Grade 对象
   */
  public boolean grade(double score, String feedback)
  {
    // 守卫条件：当前状态必须是 Grading
    if (status != Status.Grading) {
      return false;
    }
    
    // 守卫条件：0 <= score <= assignment.getMaxScore()
    if (assignment == null) {
      return false;
    }
    if (score < 0 || score > assignment.getMaxScore()) {
      return false;
    }
    
    // 如果 submissionGrade 为 null，创建新的 Grade 对象
    if (submissionGrade == null) {
      String gradeId = com.olp.util.Utils.generateId("GRD");
      Grade newGrade = new Grade(gradeId, score, feedback, this);
      setSubmissionGrade(newGrade);
    } else {
      // 如果 submissionGrade 已存在，更新其 score 和 feedback
      submissionGrade.setScore(score);
      submissionGrade.setFeedback(feedback);
    }
    
    // 状态转换
    setStatus(Status.Graded);
    return true;
  }
  
  // 保留无参方法以兼容现有代码（调用新方法，使用默认值）
  public boolean grade()
  {
    return grade(0.0, "");
  }

  /**
   * Task 3.9: 实现 requestResubmission() 方法
   * 要求重交：从 Graded 或 Returned 状态转换到 ResubmissionRequested 状态
   * 注意：学生可以创建新的 Submission（version 递增）
   */
  public boolean requestResubmission()
  {
    // 守卫条件：当前状态必须是 Graded 或 Returned
    if (status != Status.Graded && status != Status.Returned) {
      return false;
    }
    
    // 守卫条件：Utils.getCurrentTime() <= assignment.getDeadline()（可选）
    if (assignment != null && assignment.getDeadline() != null) {
      Date currentTime = Utils.getCurrentTime();
      if (Utils.compareDates(currentTime, assignment.getDeadline()) > 0) {
        return false; // 超过截止时间
      }
    }
    
    // 状态转换
    setStatus(Status.ResubmissionRequested);
    return true;
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
  /**
   * Task 3.4: 修改 isBeforeDeadline() 方法
   * 检查当前时间是否在截止时间之前
   */
  public Boolean isBeforeDeadline(){
    if (assignment == null || assignment.getDeadline() == null) {
      return false;
    }
    Date currentTime = Utils.getCurrentTime();
    return Utils.compareDates(currentTime, assignment.getDeadline()) <= 0;
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