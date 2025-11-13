package com.olp.service;

import com.olp.model.course.Course;
import com.olp.model.course.Enrollment;
import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import com.olp.repository.CourseRepository;
import com.olp.repository.InstructorRepository;
import com.olp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Task 6.4: CourseServiceImpl 实现类
 * 实现课程服务的业务逻辑
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private InstructorRepository instructorRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Override
    public Course createCourse(String id, String title, int capacity, String instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found: " + instructorId));
        
        Course course = new Course(id, title, capacity, instructor);
        return courseRepository.save(course);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseById(String id) {
        return courseRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Course> findCoursesByInstructor(String instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Course> findCoursesByStatus(Course.Status status) {
        return courseRepository.findByStatus(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    @Override
    public Course publishCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
        
        boolean published = course.publish();
        if (!published) {
            throw new IllegalStateException("Failed to publish course. Check course status and content requirements.");
        }
        
        return courseRepository.save(course);
    }
    
    @Override
    public Course openEnrollment(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
        
        boolean opened = course.openEnrollment();
        if (!opened) {
            throw new IllegalStateException("Failed to open enrollment. Course must be published first.");
        }
        
        return courseRepository.save(course);
    }
    
    @Override
    public Enrollment enrollStudent(String courseId, String studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
        
        Enrollment enrollment = course.enroll(student);
        if (enrollment == null) {
            throw new IllegalStateException("Failed to enroll student. Check course status or duplicate enrollment.");
        }
        
        courseRepository.save(course);
        return enrollment;
    }
    
    @Override
    public Course startCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
        
        boolean started = course.startCourse();
        if (!started) {
            throw new IllegalStateException("Failed to start course. Must have active students.");
        }
        
        return courseRepository.save(course);
    }
    
    @Override
    public Course completeCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
        
        boolean completed = course.complete();
        if (!completed) {
            throw new IllegalStateException("Failed to complete course. Course must be in progress.");
        }
        
        return courseRepository.save(course);
    }
    
    @Override
    public Course cancelCourse(String courseId, String reason) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
        
        boolean cancelled = course.cancel(reason);
        if (!cancelled) {
            throw new IllegalStateException("Failed to cancel course. Cannot cancel completed courses.");
        }
        
        return courseRepository.save(course);
    }
}

