package com.olp.model.course;

import java.util.*;
import javax.persistence.*;

// line 110 "model.ump"
// line 265 "model.ump"
@Entity
@Table(name = "course_categories")
public class CourseCategory
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CourseCategory Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "name", length = 100, nullable = false, unique = true)
  private String name;

  //CourseCategory Associations
  @ManyToMany(mappedBy = "categories")
  private List<Course> courses;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Protected no-argument constructor for JPA
   */
  protected CourseCategory()
  {
    courses = new ArrayList<Course>();
  }

  public CourseCategory(String aId, String aName)
  {
    // Task 2.1: 添加构造函数验证
    if (aId == null || aId.trim().isEmpty()) {
      throw new IllegalArgumentException("CourseCategory id cannot be null or empty");
    }
    if (aName == null || aName.trim().isEmpty()) {
      throw new IllegalArgumentException("CourseCategory name cannot be null or empty");
    }
    
    id = aId;
    name = aName;
    courses = new ArrayList<Course>();
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

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public Course getCourse(int index)
  {
    Course aCourse = courses.get(index);
    return aCourse;
  }

  public List<Course> getCourses()
  {
    List<Course> newCourses = Collections.unmodifiableList(courses);
    return newCourses;
  }

  public int numberOfCourses()
  {
    int number = courses.size();
    return number;
  }

  public boolean hasCourses()
  {
    boolean has = courses.size() > 0;
    return has;
  }

  public int indexOfCourse(Course aCourse)
  {
    int index = courses.indexOf(aCourse);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCourses()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCourse(Course aCourse)
  {
    boolean wasAdded = false;
    if (courses.contains(aCourse)) { return false; }
    courses.add(aCourse);
    if (aCourse.indexOfCategory(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCourse.addCategory(this);
      if (!wasAdded)
      {
        courses.remove(aCourse);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCourse(Course aCourse)
  {
    boolean wasRemoved = false;
    if (!courses.contains(aCourse))
    {
      return wasRemoved;
    }

    int oldIndex = courses.indexOf(aCourse);
    courses.remove(oldIndex);
    if (aCourse.indexOfCategory(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCourse.removeCategory(this);
      if (!wasRemoved)
      {
        courses.add(oldIndex,aCourse);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCourseAt(Course aCourse, int index)
  {  
    boolean wasAdded = false;
    if(addCourse(aCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourses()) { index = numberOfCourses() - 1; }
      courses.remove(aCourse);
      courses.add(index, aCourse);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCourseAt(Course aCourse, int index)
  {
    boolean wasAdded = false;
    if(courses.contains(aCourse))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCourses()) { index = numberOfCourses() - 1; }
      courses.remove(aCourse);
      courses.add(index, aCourse);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCourseAt(aCourse, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Course> copyOfCourses = new ArrayList<Course>(courses);
    courses.clear();
    for(Course aCourse : copyOfCourses)
    {
      aCourse.removeCategory(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "]";
  }
}

