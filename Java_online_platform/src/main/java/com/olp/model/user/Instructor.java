package com.olp.model.user;

import java.util.*;
import javax.persistence.*;
import com.olp.model.course.Course;

// line 24 "model.ump"
// line 235 "model.ump"
@Entity
@Table(name = "instructors")
public class Instructor extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Instructor Associations
  @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Course> taughtCourses;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Protected no-argument constructor for JPA
   */
  protected Instructor()
  {
    super();
    taughtCourses = new ArrayList<Course>();
  }

  public Instructor(String aId, String aName, String aEmail)
  {
    super(aId, aName, aEmail);
    taughtCourses = new ArrayList<Course>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Course getTaughtCourse(int index)
  {
    Course aTaughtCourse = taughtCourses.get(index);
    return aTaughtCourse;
  }

  public List<Course> getTaughtCourses()
  {
    List<Course> newTaughtCourses = Collections.unmodifiableList(taughtCourses);
    return newTaughtCourses;
  }

  public int numberOfTaughtCourses()
  {
    int number = taughtCourses.size();
    return number;
  }

  public boolean hasTaughtCourses()
  {
    boolean has = taughtCourses.size() > 0;
    return has;
  }

  public int indexOfTaughtCourse(Course aTaughtCourse)
  {
    int index = taughtCourses.indexOf(aTaughtCourse);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTaughtCourses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Course addTaughtCourse(String aId, String aTitle, int aCapacity)
  {
    return new Course(aId, aTitle, aCapacity, this);
  }

  public boolean addTaughtCourse(Course aTaughtCourse)
  {
    boolean wasAdded = false;
    if (taughtCourses.contains(aTaughtCourse)) { return false; }
    Instructor existingInstructor = aTaughtCourse.getInstructor();
    boolean isNewInstructor = existingInstructor != null && !this.equals(existingInstructor);
    if (isNewInstructor)
    {
      aTaughtCourse.setInstructor(this);
    }
    else
    {
      taughtCourses.add(aTaughtCourse);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTaughtCourse(Course aTaughtCourse)
  {
    boolean wasRemoved = false;
    //Unable to remove aTaughtCourse, as it must always have a instructor
    if (!this.equals(aTaughtCourse.getInstructor()))
    {
      taughtCourses.remove(aTaughtCourse);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTaughtCourseAt(Course aTaughtCourse, int index)
  {  
    boolean wasAdded = false;
    if(addTaughtCourse(aTaughtCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTaughtCourses()) { index = numberOfTaughtCourses() - 1; }
      taughtCourses.remove(aTaughtCourse);
      taughtCourses.add(index, aTaughtCourse);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTaughtCourseAt(Course aTaughtCourse, int index)
  {
    boolean wasAdded = false;
    if(taughtCourses.contains(aTaughtCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTaughtCourses()) { index = numberOfTaughtCourses() - 1; }
      taughtCourses.remove(aTaughtCourse);
      taughtCourses.add(index, aTaughtCourse);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTaughtCourseAt(aTaughtCourse, index);
    }
    return wasAdded;
  }

  //------------------------
  // Convenience Methods (Task 1.3)
  //------------------------
  
  /**
   * 统计状态不是 Cancelled 和 Completed 的课程数
   * Task 1.3: 添加便捷方法
   */
  public int getActiveCourseCount()
  {
    int count = 0;
    for (Course course : taughtCourses) {
      Course.Status status = course.getStatus();
      if (status != Course.Status.Cancelled && status != Course.Status.Completed) {
        count++;
      }
    }
    return count;
  }

  /**
   * 返回状态为 InProgress 的课程列表
   * Task 1.3: 添加便捷方法
   */
  public List<Course> getCoursesInProgress()
  {
    List<Course> result = new ArrayList<Course>();
    for (Course course : taughtCourses) {
      if (course.getStatus() == Course.Status.InProgress) {
        result.add(course);
      }
    }
    return result;
  }

  public void delete()
  {
    for(int i=taughtCourses.size(); i > 0; i--)
    {
      Course aTaughtCourse = taughtCourses.get(i - 1);
      aTaughtCourse.delete();
    }
    super.delete();
  }

}

