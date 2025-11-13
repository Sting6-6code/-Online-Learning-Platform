/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package com.olp.model.assignment;

import java.sql.Date;
import javax.persistence.*;

// line 169 "model.ump"
// line 280 "model.ump"
@Entity
@Table(name = "grades")
public class Grade
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Grade Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "score", nullable = false)
  private double score;
  
  @Column(name = "feedback", length = 1000)
  private String feedback;

  //Grade Associations
  @OneToOne
  @JoinColumn(name = "submission_id", nullable = false, unique = true)
  private com.olp.model.assignment.Submission submission;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Protected no-argument constructor for JPA
   */
  protected Grade()
  {
  }

  public Grade(String aId, double aScore, String aFeedback, com.olp.model.assignment.Submission aSubmission)
  {
    // Task 3.2: 添加构造函数验证
    if (aSubmission == null) {
      throw new IllegalArgumentException("Grade submission cannot be null");
    }
    if (aSubmission.getAssignment() == null) {
      throw new IllegalArgumentException("Grade submission must have an assignment");
    }
    validateScoreWithinSubmission(aScore, aSubmission);
    
    id = aId;
    score = aScore;
    feedback = aFeedback;
    setSubmission(aSubmission);
  }
  
  // 保留原构造函数以兼容现有代码（不推荐使用）
  public Grade(String aId, double aScore, String aFeedback)
  {
    id = aId;
    score = aScore;
    feedback = aFeedback;
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

  public boolean setScore(double aScore)
  {
    boolean wasSet = false;
    validateScoreWithinSubmission(aScore, submission);
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setFeedback(String aFeedback)
  {
    boolean wasSet = false;
    feedback = aFeedback;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public double getScore()
  {
    return score;
  }

  public String getFeedback()
  {
    return feedback;
  }
  /* Code from template association_GetOne */
  public com.olp.model.assignment.Submission getSubmission()
  {
    return submission;
  }

  public boolean hasSubmission()
  {
    boolean has = submission != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setSubmission(com.olp.model.assignment.Submission aNewSubmission)
  {
    boolean wasSet = false;
    if (aNewSubmission != null)
    {
      validateScoreWithinSubmission(score, aNewSubmission);
    }
    if (submission != null && !submission.equals(aNewSubmission) && equals(submission.getSubmissionGrade()))
    {
      //Unable to setSubmission, as existing submission would become an orphan
      return wasSet;
    }

    submission = aNewSubmission;
    Grade anOldSubmissionGrade = aNewSubmission != null ? aNewSubmission.getSubmissionGrade() : null;

    if (!this.equals(anOldSubmissionGrade))
    {
      if (anOldSubmissionGrade != null)
      {
        anOldSubmissionGrade.submission = null;
      }
      if (submission != null)
      {
        submission.setSubmissionGrade(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  
  /**
   * Task 5.6: 验证成绩范围
   * 确保 0 <= score <= assignment.maxScore
   */
  private void validateScoreWithinSubmission(double candidateScore, com.olp.model.assignment.Submission targetSubmission)
  {
    if (candidateScore < 0) {
      throw new IllegalArgumentException("Grade score cannot be negative");
    }
    if (targetSubmission == null) {
      throw new IllegalArgumentException("Grade submission cannot be null when validating score");
    }
    if (targetSubmission.getAssignment() == null) {
      // 提交尚未关联作业，暂时跳过最大值验证
      return;
    }
    int maxScore = targetSubmission.getAssignment().getMaxScore();
    if (candidateScore > maxScore) {
      throw new IllegalArgumentException("Grade score (" + candidateScore + ") cannot exceed assignment maxScore (" + maxScore + ")");
    }
  }

  //------------------------
  // Convenience Methods (Task 3.2)
  //------------------------
  
  /**
   * 计算成绩百分比
   * Task 3.2: 添加便捷方法
   * @return (score / assignment.maxScore) * 100.0，如果 maxScore 为 0 则返回 0.0
   */
  public double getPercentage()
  {
    if (submission == null || submission.getAssignment() == null) {
      return 0.0;
    }
    int maxScore = submission.getAssignment().getMaxScore();
    if (maxScore == 0) {
      return 0.0;
    }
    return (score / maxScore) * 100.0;
  }

  public void delete()
  {
    com.olp.model.assignment.Submission existingSubmission = submission;
    submission = null;
    if (existingSubmission != null)
    {
      existingSubmission.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "score" + ":" + getScore()+ "," +
            "feedback" + ":" + getFeedback()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "submission = "+(getSubmission()!=null?Integer.toHexString(System.identityHashCode(getSubmission())):"null");
  }
}