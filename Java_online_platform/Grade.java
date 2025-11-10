/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.sql.Date;

// line 169 "model.ump"
// line 280 "model.ump"
public class Grade
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Grade Attributes
  private String id;
  private double score;
  private String feedback;

  //Grade Associations
  private Submission submission;

  //------------------------
  // CONSTRUCTOR
  //------------------------

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
  public Submission getSubmission()
  {
    return submission;
  }

  public boolean hasSubmission()
  {
    boolean has = submission != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setSubmission(Submission aNewSubmission)
  {
    boolean wasSet = false;
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

  public void delete()
  {
    Submission existingSubmission = submission;
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