package com.olp.model.course;

import java.util.*;
import javax.persistence.*;

// line 96 "model.ump"
// line 255 "model.ump"
@Entity
@Table(name = "lessons")
public class Lesson
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Lesson Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "title", length = 200, nullable = false)
  private String title;
  
  @Column(name = "order_index", nullable = false)
  private int orderIndex;

  //Lesson Associations
  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<VideoContent> lessonVideos;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Lesson(String aId, String aTitle, int aOrderIndex, Course aCourse)
  {
    // Task 2.3: 添加构造函数验证
    if (aTitle == null || aTitle.trim().isEmpty()) {
      throw new IllegalArgumentException("Lesson title cannot be null or empty");
    }
    if (aOrderIndex < 0) {
      throw new IllegalArgumentException("Lesson orderIndex must be >= 0");
    }
    if (aCourse == null) {
      throw new IllegalArgumentException("Lesson course cannot be null");
    }
    
    id = aId;
    title = aTitle;
    orderIndex = aOrderIndex;
    lessonVideos = new ArrayList<VideoContent>();
    boolean didAddCourse = setCourse(aCourse);
    if (!didAddCourse)
    {
      throw new RuntimeException("Unable to create lesson due to course. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setOrderIndex(int aOrderIndex)
  {
    boolean wasSet = false;
    orderIndex = aOrderIndex;
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

  public int getOrderIndex()
  {
    return orderIndex;
  }
  /* Code from template association_GetMany */
  public VideoContent getLessonVideo(int index)
  {
    VideoContent aLessonVideo = lessonVideos.get(index);
    return aLessonVideo;
  }

  public List<VideoContent> getLessonVideos()
  {
    List<VideoContent> newLessonVideos = Collections.unmodifiableList(lessonVideos);
    return newLessonVideos;
  }

  public int numberOfLessonVideos()
  {
    int number = lessonVideos.size();
    return number;
  }

  public boolean hasLessonVideos()
  {
    boolean has = lessonVideos.size() > 0;
    return has;
  }

  public int indexOfLessonVideo(VideoContent aLessonVideo)
  {
    int index = lessonVideos.indexOf(aLessonVideo);
    return index;
  }
  /* Code from template association_GetOne */
  public Course getCourse()
  {
    return course;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLessonVideos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public VideoContent addLessonVideo(String aId, String aUrl, int aDurationSec)
  {
    return new VideoContent(aId, aUrl, aDurationSec, this);
  }

  public boolean addLessonVideo(VideoContent aLessonVideo)
  {
    boolean wasAdded = false;
    if (lessonVideos.contains(aLessonVideo)) { return false; }
    Lesson existingLesson = aLessonVideo.getLesson();
    boolean isNewLesson = existingLesson != null && !this.equals(existingLesson);
    if (isNewLesson)
    {
      aLessonVideo.setLesson(this);
    }
    else
    {
      lessonVideos.add(aLessonVideo);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLessonVideo(VideoContent aLessonVideo)
  {
    boolean wasRemoved = false;
    //Unable to remove aLessonVideo, as it must always have a lesson
    if (!this.equals(aLessonVideo.getLesson()))
    {
      lessonVideos.remove(aLessonVideo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLessonVideoAt(VideoContent aLessonVideo, int index)
  {  
    boolean wasAdded = false;
    if(addLessonVideo(aLessonVideo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLessonVideos()) { index = numberOfLessonVideos() - 1; }
      lessonVideos.remove(aLessonVideo);
      lessonVideos.add(index, aLessonVideo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLessonVideoAt(VideoContent aLessonVideo, int index)
  {
    boolean wasAdded = false;
    if(lessonVideos.contains(aLessonVideo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLessonVideos()) { index = numberOfLessonVideos() - 1; }
      lessonVideos.remove(aLessonVideo);
      lessonVideos.add(index, aLessonVideo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLessonVideoAt(aLessonVideo, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setCourse(Course aCourse)
  {
    boolean wasSet = false;
    //Must provide course to lesson
    if (aCourse == null)
    {
      return wasSet;
    }

    if (course != null && course.numberOfLessons() <= Course.minimumNumberOfLessons())
    {
      return wasSet;
    }

    Course existingCourse = course;
    course = aCourse;
    if (existingCourse != null && !existingCourse.equals(aCourse))
    {
      boolean didRemove = existingCourse.removeLesson(this);
      if (!didRemove)
      {
        course = existingCourse;
        return wasSet;
      }
    }
    course.addLesson(this);
    wasSet = true;
    return wasSet;
  }

  //------------------------
  // Convenience Methods (Task 2.3)
  //------------------------
  
  /**
   * 计算所有关联的 VideoContent 的 durationSec 之和
   * Task 2.3: 添加便捷方法
   */
  public int getTotalDuration()
  {
    int total = 0;
    for (VideoContent video : lessonVideos) {
      total += video.getDurationSec();
    }
    return total;
  }

  public void delete()
  {
    while (lessonVideos.size() > 0)
    {
      VideoContent aLessonVideo = lessonVideos.get(lessonVideos.size() - 1);
      aLessonVideo.delete();
      lessonVideos.remove(aLessonVideo);
    }
    
    Course placeholderCourse = course;
    this.course = null;
    if(placeholderCourse != null)
    {
      placeholderCourse.removeLesson(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "title" + ":" + getTitle()+ "," +
            "orderIndex" + ":" + getOrderIndex()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null");
  }
}

