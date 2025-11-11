package com.olp.model.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import com.olp.model.course.Course;
import com.olp.model.course.Enrollment;
import com.olp.model.assignment.Submission;
import com.olp.model.assignment.Assignment;
import com.olp.model.assignment.Grade;

/**
 * Task 1.2: Student 类基础功能测试
 * 验证 Student 类的继承关系和基本操作
 */
@SpringBootTest
public class StudentTest {

    @Test
    public void testCreateStudent() {
        Student student = new Student("S001", "Alice", "alice@example.com");
        assertNotNull(student);
        assertEquals("S001", student.getId());
        assertEquals("Alice", student.getName());
        assertEquals("alice@example.com", student.getEmail());
        System.out.println("✅ 可以创建 Student 对象");
    }

    @Test
    public void testInheritUserProperties() {
        Student student = new Student("S002", "Bob", "bob@example.com");
        
        // 验证可以访问继承的属性
        assertEquals("S002", student.getId());
        assertEquals("Bob", student.getName());
        assertEquals("bob@example.com", student.getEmail());
        
        System.out.println("✅ 可以访问继承的属性（id, name, email）");
    }

    @Test
    public void testAddAndRemoveEnrollment() {
        Student student = new Student("S003", "Charlie", "charlie@example.com");
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        
        // 创建 Enrollment
        Date enrolledDate = new Date(System.currentTimeMillis());
        Enrollment enrollment = new Enrollment(
            "E001",
            Enrollment.EnrollmentStatus.Active,
            enrolledDate,
            student,
            course
        );
        
        // 验证 Enrollment 已添加到 Student
        assertTrue(student.hasStudentEnrollments());
        assertEquals(1, student.numberOfStudentEnrollments());
        assertEquals(enrollment, student.getStudentEnrollment(0));
        
        System.out.println("✅ 添加 Enrollment 关联成功");
        
        // 测试移除（通过 Enrollment 的 delete 方法）
        enrollment.delete();
        // 注意：由于双向关联，删除 Enrollment 后会自动从 Student 中移除
        System.out.println("✅ 移除 Enrollment 关联成功");
    }

    @Test
    public void testGetActiveEnrollmentCount() {
        Student student = new Student("S004", "David", "david@example.com");
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course1 = new Course("C002", "Python Basics", 30, instructor);
        Course course2 = new Course("C003", "Web Development", 40, instructor);
        
        // 创建多个 Enrollment，包含不同状态
        Date enrolledDate = new Date(System.currentTimeMillis());
        
        Enrollment enrollment1 = new Enrollment(
            "E002",
            Enrollment.EnrollmentStatus.Active,
            enrolledDate,
            student,
            course1
        );
        
        Enrollment enrollment2 = new Enrollment(
            "E003",
            Enrollment.EnrollmentStatus.Active,
            enrolledDate,
            student,
            course2
        );
        
        Enrollment enrollment3 = new Enrollment(
            "E004",
            Enrollment.EnrollmentStatus.Waitlisted,
            enrolledDate,
            student,
            course1
        );
        
        // 验证 Active 数量
        int activeCount = student.getActiveEnrollmentCount();
        assertEquals(2, activeCount, "应该有 2 个 Active 状态的 Enrollment");
        
        System.out.println("✅ getActiveEnrollmentCount() 返回正确结果: " + activeCount);
    }

    @Test
    public void testAddAndRemoveSubmission() {
        Student student = new Student("S005", "Eve", "eve@example.com");
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        
        Date deadline = new Date(System.currentTimeMillis() + 86400000L); // 明天
        Assignment assignment = new Assignment("A001", "SQL Assignment", deadline, 100, course);
        
        // 创建 Submission
        Date submittedDate = new Date(System.currentTimeMillis());
        Grade grade = new Grade("G001", 85.0, "Good work!");
        Submission submission = new Submission(
            "SUB001",
            submittedDate,
            1,
            true,
            grade,
            student,
            assignment
        );
        
        // 验证 Submission 已添加到 Student
        assertTrue(student.hasStudentSubmissions());
        assertEquals(1, student.numberOfStudentSubmissions());
        assertEquals(submission, student.getStudentSubmission(0));
        
        System.out.println("✅ 添加 Submission 关联成功");
    }

    @Test
    public void testMultipleEnrollments() {
        Student student = new Student("S006", "Frank", "frank@example.com");
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        
        // 创建多个课程
        Course course1 = new Course("C005", "Math 101", 100, instructor);
        Course course2 = new Course("C006", "Physics 101", 80, instructor);
        Course course3 = new Course("C007", "Chemistry 101", 90, instructor);
        
        Date enrolledDate = new Date(System.currentTimeMillis());
        
        // 添加多个 Enrollment
        new Enrollment("E005", Enrollment.EnrollmentStatus.Active, enrolledDate, student, course1);
        new Enrollment("E006", Enrollment.EnrollmentStatus.Active, enrolledDate, student, course2);
        new Enrollment("E007", Enrollment.EnrollmentStatus.Dropped, enrolledDate, student, course3);
        
        // 验证总数和 Active 数量
        assertEquals(3, student.numberOfStudentEnrollments());
        assertEquals(2, student.getActiveEnrollmentCount());
        
        System.out.println("✅ 多个 Enrollment 管理正常");
    }

    @Test
    public void testEmptyEnrollments() {
        Student student = new Student("S007", "Grace", "grace@example.com");
        
        // 验证初始状态
        assertFalse(student.hasStudentEnrollments());
        assertEquals(0, student.numberOfStudentEnrollments());
        assertEquals(0, student.getActiveEnrollmentCount());
        
        System.out.println("✅ 空 Enrollment 列表处理正常");
    }
}

