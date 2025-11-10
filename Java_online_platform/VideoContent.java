/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/



// line 104 "model.ump"
// line 260 "model.ump"
public class VideoContent
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //VideoContent Attributes
  private String id;
  private String url;
  private int durationSec;

  //VideoContent Associations
  private Lesson lesson;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public VideoContent(String aId, String aUrl, int aDurationSec, Lesson aLesson)
  {
    id = aId;
    url = aUrl;
    durationSec = aDurationSec;
    boolean didAddLesson = setLesson(aLesson);
    if (!didAddLesson)
    {
      throw new RuntimeException("Unable to create lessonVideo due to lesson. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setDurationSec(int aDurationSec)
  {
    boolean wasSet = false;
    durationSec = aDurationSec;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public String getUrl()
  {
    return url;
  }

  public int getDurationSec()
  {
    return durationSec;
  }
  /* Code from template association_GetOne */
  public Lesson getLesson()
  {
    return lesson;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLesson(Lesson aLesson)
  {
    boolean wasSet = false;
    if (aLesson == null)
    {
      return wasSet;
    }

    Lesson existingLesson = lesson;
    lesson = aLesson;
    if (existingLesson != null && !existingLesson.equals(aLesson))
    {
      existingLesson.removeLessonVideo(this);
    }
    lesson.addLessonVideo(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Lesson placeholderLesson = lesson;
    this.lesson = null;
    if(placeholderLesson != null)
    {
      placeholderLesson.removeLessonVideo(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "url" + ":" + getUrl()+ "," +
            "durationSec" + ":" + getDurationSec()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "lesson = "+(getLesson()!=null?Integer.toHexString(System.identityHashCode(getLesson())):"null");
  }
}