package com.olp.service;

import com.olp.model.course.Course;
import com.olp.model.course.Enrollment;

import java.util.List;
import java.util.Optional;

/**
 * Task 6.4: CourseService 接口
 * 定义课程服务的业务逻辑接口
 */
public interface CourseService {
    
    /**
     * 创建课程
     * @param id 课程 ID
     * @param title 课程标题
     * @param capacity 容量
     * @param instructorId 教师 ID
     * @return Course
     */
    Course createCourse(String id, String title, int capacity, String instructorId);
    
    /**
     * 通过 ID 查找课程
     * @param id 课程 ID
     * @return Optional<Course>
     */
    Optional<Course> findCourseById(String id);
    
    /**
     * 通过教师 ID 查找课程
     * @param instructorId 教师 ID
     * @return List<Course>
     */
    List<Course> findCoursesByInstructor(String instructorId);
    
    /**
     * 通过状态查找课程
     * @param status 课程状态
     * @return List<Course>
     */
    List<Course> findCoursesByStatus(Course.Status status);
    
    /**
     * 获取所有课程
     * @return List<Course>
     */
    List<Course> getAllCourses();
    
    /**
     * 发布课程
     * @param courseId 课程 ID
     * @return Course
     */
    Course publishCourse(String courseId);
    
    /**
     * 开放选课
     * @param courseId 课程 ID
     * @return Course
     */
    Course openEnrollment(String courseId);
    
    /**
     * 学生选课
     * @param courseId 课程 ID
     * @param studentId 学生 ID
     * @return Enrollment
     */
    Enrollment enrollStudent(String courseId, String studentId);
    
    /**
     * 开始课程
     * @param courseId 课程 ID
     * @return Course
     */
    Course startCourse(String courseId);
    
    /**
     * 完成课程
     * @param courseId 课程 ID
     * @return Course
     */
    Course completeCourse(String courseId);
    
    /**
     * 取消课程
     * @param courseId 课程 ID
     * @param reason 取消原因
     * @return Course
     */
    Course cancelCourse(String courseId, String reason);
}

