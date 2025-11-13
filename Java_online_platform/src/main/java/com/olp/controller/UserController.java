package com.olp.controller;

import com.olp.model.user.Administrator;
import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import com.olp.model.user.User;
import com.olp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Task 6.3: UserController REST API
 * 处理用户相关的 HTTP 请求
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取用户信息
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 通过邮箱查找用户
     * GET /api/users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 创建学生
     * POST /api/users/students
     * Body: {"id": "S001", "name": "John", "email": "john@example.com"}
     */
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Map<String, String> request) {
        try {
            String id = request.get("id");
            String name = request.get("name");
            String email = request.get("email");
            
            Student student = userService.createStudent(id, name, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(student);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 创建教师
     * POST /api/users/instructors
     * Body: {"id": "I001", "name": "Dr. Smith", "email": "smith@example.com"}
     */
    @PostMapping("/instructors")
    public ResponseEntity<Instructor> createInstructor(@RequestBody Map<String, String> request) {
        try {
            String id = request.get("id");
            String name = request.get("name");
            String email = request.get("email");
            
            Instructor instructor = userService.createInstructor(id, name, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(instructor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 创建管理员
     * POST /api/users/administrators
     * Body: {"id": "A001", "name": "Admin", "email": "admin@example.com"}
     */
    @PostMapping("/administrators")
    public ResponseEntity<Administrator> createAdministrator(@RequestBody Map<String, String> request) {
        try {
            String id = request.get("id");
            String name = request.get("name");
            String email = request.get("email");
            
            Administrator administrator = userService.createAdministrator(id, name, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(administrator);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 获取所有学生
     * GET /api/users/students
     */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = userService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    /**
     * 获取所有教师
     * GET /api/users/instructors
     */
    @GetMapping("/instructors")
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructors = userService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }
}

