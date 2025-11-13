package com.olp.model.course;

import java.sql.Date;
import javax.persistence.*;
import com.olp.model.user.Student;

// line 90 "model.ump"
// line 250 "model.ump"
@Entity
@Table(name = "enrollments")
public class Enrollment
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum EnrollmentStatus { Active, Waitlisted, Dropped }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Enrollment Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 20, nullable = false)
  private EnrollmentStatus status;
  
  @Column(name = "enrolled_at", nullable = false)
  private Date enrolledAt;

  //Enrollment Associations
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Protected no-argument constructor for JPA
   */
  protected Enrollment()
  {
    status = EnrollmentStatus.Active;
  }

  public Enrollment(String aId, EnrollmentStatus aStatus, Date aEnrolledAt, Student aStudent, Course aCourse)
  {
    // Task 2.4: 添加构造函数验证
    if (aEnrolledAt == null) {
      throw new IllegalArgumentException("Enrollment enrolledAt cannot be null");
    }
    if (aStudent == null) {
      throw new IllegalArgumentException("Enrollment student cannot be null");
    }
    if (aCourse == null) {
      throw new IllegalArgumentException("Enrollment course cannot be null");
    }
    
    // Task 5.2: 实现 EnrollmentOnlyAfterPublish 约束验证
    // OCL 约束：self.course.status ≠ CourseStatus::Draft
    if (aCourse.getStatus() == Course.Status.Draft) {
      throw new IllegalArgumentException("Enrollment can only be created for non-Draft courses, but course status is: " + aCourse.getStatus());
    }
    
    id = aId;
    status = aStatus;
    enrolledAt = aEnrolledAt;
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create studentEnrollment due to student. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCourse = setCourse(aCourse);
    if (!didAddCourse)
    {
      throw new RuntimeException("Unable to create courseEnrollment due to course. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setStatus(EnrollmentStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setEnrolledAt(Date aEnrolledAt)
  {
    boolean wasSet = false;
    enrolledAt = aEnrolledAt;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public EnrollmentStatus getStatus()
  {
    return status;
  }

  public Date getEnrolledAt()
  {
    return enrolledAt;
  }
  /* Code from template association_GetOne */
  public Student getStudent()
  {
    return student;
  }
  /* Code from template association_GetOne */
  public Course getCourse()
  {
    return course;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeStudentEnrollment(this);
    }
    student.addStudentEnrollment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCourse(Course aCourse)
  {
    boolean wasSet = false;
    if (aCourse == null)
    {
      return wasSet;
    }

    Course existingCourse = course;
    course = aCourse;
    if (existingCourse != null && !existingCourse.equals(aCourse))
    {
      existingCourse.removeCourseEnrollment(this);
    }
    course.addCourseEnrollment(this);
    wasSet = true;
    return wasSet;
  }

  //------------------------
  // Business Methods (Task 2.4)
  //------------------------
  
  /**
   * 从 Active 或 Waitlisted 转换为 Dropped
   * Task 2.4: 实现状态转换方法
   * @return true 如果状态转换成功，false 如果当前状态不允许转换
   */
  public boolean dropCourse()
  {
    if (status == EnrollmentStatus.Active || status == EnrollmentStatus.Waitlisted) {
      status = EnrollmentStatus.Dropped;
      return true;
    }
    return false;
  }

  public void delete()
  {
    Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null)
    {
      placeholderStudent.removeStudentEnrollment(this);
    }
    Course placeholderCourse = course;
    this.course = null;
    if(placeholderCourse != null)
    {
      placeholderCourse.removeCourseEnrollment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "enrolledAt" + "=" + (getEnrolledAt() != null ? !getEnrolledAt().equals(this)  ? getEnrolledAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "course = "+(getCourse()!=null?Integer.toHexString(System.identityHashCode(getCourse())):"null");
  }
}

