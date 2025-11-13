package com.olp.model.course;

import javax.persistence.*;

// line 104 "model.ump"
// line 260 "model.ump"
@Entity
@Table(name = "video_contents")
public class VideoContent
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //VideoContent Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "url", length = 500, nullable = false)
  private String url;
  
  @Column(name = "duration_sec", nullable = false)
  private int durationSec;

  //VideoContent Associations
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id", nullable = false)
  private Lesson lesson;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Protected no-argument constructor for JPA
   */
  protected VideoContent()
  {
  }

  public VideoContent(String aId, String aUrl, int aDurationSec, Lesson aLesson)
  {
    // Task 2.2: 添加构造函数验证
    if (aUrl == null || aUrl.trim().isEmpty()) {
      throw new IllegalArgumentException("VideoContent url cannot be null or empty");
    }
    if (aDurationSec <= 0) {
      throw new IllegalArgumentException("VideoContent durationSec must be greater than 0");
    }
    if (aLesson == null) {
      throw new IllegalArgumentException("VideoContent lesson cannot be null");
    }
    
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

