package com.olp.model.course;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import java.sql.Date;

/**
 * Task 2.4: Enrollment 关联类基础功能测试
 * 验证 Enrollment 类的构造函数验证和状态转换方法
 */
@SpringBootTest
public class EnrollmentTest {

    @Test
    public void testCreateEnrollment() {
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        Student student = new Student("S001", "John Doe", "john@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        Enrollment enrollment = new Enrollment("E001", Enrollment.EnrollmentStatus.Active, enrolledAt, student, course);
        assertNotNull(enrollment);
        assertEquals("E001", enrollment.getId());
        assertEquals(Enrollment.EnrollmentStatus.Active, enrollment.getStatus());
        assertEquals(enrolledAt, enrollment.getEnrolledAt());
        assertEquals(student, enrollment.getStudent());
        assertEquals(course, enrollment.getCourse());
        
        System.out.println("✅ 可以创建 Enrollment 对象");
    }

    @Test
    public void testNullEnrolledAtThrowsException() {
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course = new Course("C002", "Python Basics", 40, instructor);
        Student student = new Student("S002", "Jane Doe", "jane@example.com");
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Enrollment("E002", Enrollment.EnrollmentStatus.Active, null, student, course),
            "null enrolledAt 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("enrolledAt cannot be null"));
        System.out.println("✅ null enrolledAt 验证通过");
    }

    @Test
    public void testNullStudentThrowsException() {
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        Course course = new Course("C003", "Web Development", 30, instructor);
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Enrollment("E003", Enrollment.EnrollmentStatus.Active, enrolledAt, null, course),
            "null student 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("student cannot be null"));
        System.out.println("✅ null student 验证通过");
    }

    @Test
    public void testNullCourseThrowsException() {
        Student student = new Student("S003", "Bob Smith", "bob@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Enrollment("E004", Enrollment.EnrollmentStatus.Active, enrolledAt, student, null),
            "null course 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("course cannot be null"));
        System.out.println("✅ null course 验证通过");
    }

    @Test
    public void testDropCourseFromActive() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        Student student = new Student("S004", "Alice Brown", "alice@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        Enrollment enrollment = new Enrollment("E005", Enrollment.EnrollmentStatus.Active, enrolledAt, student, course);
        assertEquals(Enrollment.EnrollmentStatus.Active, enrollment.getStatus());
        
        boolean dropped = enrollment.dropCourse();
        assertTrue(dropped, "从 Active 状态应该可以退课");
        assertEquals(Enrollment.EnrollmentStatus.Dropped, enrollment.getStatus());
        
        System.out.println("✅ 从 Active 状态退课成功");
    }

    @Test
    public void testDropCourseFromWaitlisted() {
        Instructor instructor = new Instructor("I005", "Dr. Black", "black@example.com");
        Course course = new Course("C005", "Math 101", 100, instructor);
        Student student = new Student("S005", "Charlie Green", "charlie@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        Enrollment enrollment = new Enrollment("E006", Enrollment.EnrollmentStatus.Waitlisted, enrolledAt, student, course);
        assertEquals(Enrollment.EnrollmentStatus.Waitlisted, enrollment.getStatus());
        
        boolean dropped = enrollment.dropCourse();
        assertTrue(dropped, "从 Waitlisted 状态应该可以退课");
        assertEquals(Enrollment.EnrollmentStatus.Dropped, enrollment.getStatus());
        
        System.out.println("✅ 从 Waitlisted 状态退课成功");
    }

    @Test
    public void testDropCourseFromDropped() {
        Instructor instructor = new Instructor("I006", "Dr. Green", "green@example.com");
        Course course = new Course("C006", "Physics 101", 80, instructor);
        Student student = new Student("S006", "David Blue", "david@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        Enrollment enrollment = new Enrollment("E007", Enrollment.EnrollmentStatus.Dropped, enrolledAt, student, course);
        assertEquals(Enrollment.EnrollmentStatus.Dropped, enrollment.getStatus());
        
        boolean dropped = enrollment.dropCourse();
        assertFalse(dropped, "从 Dropped 状态不应该再次退课");
        assertEquals(Enrollment.EnrollmentStatus.Dropped, enrollment.getStatus(), "状态应该保持为 Dropped");
        
        System.out.println("✅ 从 Dropped 状态不能再次退课");
    }

    @Test
    public void testEnrollmentAssociation() {
        Instructor instructor = new Instructor("I007", "Dr. Blue", "blue@example.com");
        Course course = new Course("C007", "Chemistry 101", 90, instructor);
        Student student = new Student("S007", "Eve Red", "eve@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        Enrollment enrollment = new Enrollment("E008", Enrollment.EnrollmentStatus.Active, enrolledAt, student, course);
        
        // 验证双向关联
        assertTrue(student.hasStudentEnrollments());
        assertTrue(course.hasCourseEnrollments());
        assertEquals(1, student.numberOfStudentEnrollments());
        assertEquals(1, course.numberOfCourseEnrollments());
        
        System.out.println("✅ Enrollment 关联验证正常");
    }

    @Test
    public void testMultipleEnrollments() {
        Instructor instructor = new Instructor("I008", "Dr. Red", "red@example.com");
        Course course1 = new Course("C008", "Biology 101", 85, instructor);
        Course course2 = new Course("C009", "History 101", 70, instructor);
        Student student = new Student("S008", "Frank Yellow", "frank@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        Enrollment enrollment1 = new Enrollment("E009", Enrollment.EnrollmentStatus.Active, enrolledAt, student, course1);
        Enrollment enrollment2 = new Enrollment("E010", Enrollment.EnrollmentStatus.Waitlisted, enrolledAt, student, course2);
        
        assertEquals(2, student.numberOfStudentEnrollments());
        assertEquals(1, course1.numberOfCourseEnrollments());
        assertEquals(1, course2.numberOfCourseEnrollments());
        
        System.out.println("✅ 多个 Enrollment 关联正常");
    }
}

