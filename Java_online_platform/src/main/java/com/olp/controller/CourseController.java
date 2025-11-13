package com.olp.controller;

import com.olp.model.course.Course;
import com.olp.model.course.Enrollment;
import com.olp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Task 6.5: CourseController REST API
 * 处理课程相关的 HTTP 请求
 */
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    /**
     * 获取所有课程
     * GET /api/courses
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
    
    /**
     * 获取课程详情
     * GET /api/courses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        return courseService.findCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 通过教师 ID 获取课程
     * GET /api/courses/instructor/{instructorId}
     */
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable String instructorId) {
        List<Course> courses = courseService.findCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }
    
    /**
     * 通过状态获取课程
     * GET /api/courses/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Course>> getCoursesByStatus(@PathVariable String status) {
        try {
            Course.Status courseStatus = Course.Status.valueOf(status);
            List<Course> courses = courseService.findCoursesByStatus(courseStatus);
            return ResponseEntity.ok(courses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 创建课程
     * POST /api/courses
     * Body: {"id": "C001", "title": "Java Programming", "capacity": 50, "instructorId": "I001"}
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Map<String, Object> request) {
        try {
            String id = (String) request.get("id");
            String title = (String) request.get("title");
            int capacity = (Integer) request.get("capacity");
            String instructorId = (String) request.get("instructorId");
            
            Course course = courseService.createCourse(id, title, capacity, instructorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 发布课程
     * POST /api/courses/{id}/publish
     */
    @PostMapping("/{id}/publish")
    public ResponseEntity<Course> publishCourse(@PathVariable String id) {
        try {
            Course course = courseService.publishCourse(id);
            return ResponseEntity.ok(course);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 开放选课
     * POST /api/courses/{id}/open-enrollment
     */
    @PostMapping("/{id}/open-enrollment")
    public ResponseEntity<Course> openEnrollment(@PathVariable String id) {
        try {
            Course course = courseService.openEnrollment(id);
            return ResponseEntity.ok(course);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 学生选课
     * POST /api/courses/{id}/enroll
     * Body: {"studentId": "S001"}
     */
    @PostMapping("/{id}/enroll")
    public ResponseEntity<Enrollment> enrollStudent(@PathVariable String id, @RequestBody Map<String, String> request) {
        try {
            String studentId = request.get("studentId");
            Enrollment enrollment = courseService.enrollStudent(id, studentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 开始课程
     * POST /api/courses/{id}/start
     */
    @PostMapping("/{id}/start")
    public ResponseEntity<Course> startCourse(@PathVariable String id) {
        try {
            Course course = courseService.startCourse(id);
            return ResponseEntity.ok(course);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 完成课程
     * POST /api/courses/{id}/complete
     */
    @PostMapping("/{id}/complete")
    public ResponseEntity<Course> completeCourse(@PathVariable String id) {
        try {
            Course course = courseService.completeCourse(id);
            return ResponseEntity.ok(course);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 取消课程
     * POST /api/courses/{id}/cancel
     * Body: {"reason": "Insufficient enrollment"}
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Course> cancelCourse(@PathVariable String id, @RequestBody Map<String, String> request) {
        try {
            String reason = request.get("reason");
            Course course = courseService.cancelCourse(id, reason);
            return ResponseEntity.ok(course);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

