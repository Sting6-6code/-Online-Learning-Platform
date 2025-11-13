package com.olp.model.course;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import com.olp.model.assignment.Assignment;
import java.sql.Date;

/**
 * Task 2.4: Enrollment 关联类基础功能测试
 * 验证 Enrollment 类的构造函数验证和状态转换方法
 */
public class EnrollmentTest {

    @Test
    public void testCreateEnrollment() {
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        // Task 5.2: 课程必须不是 Draft 状态才能创建 Enrollment
        Lesson lesson = new Lesson("L001", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
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

    // Task 5.2: EnrollmentOnlyAfterPublish 约束验证测试
    @Test
    public void testEnrollmentCannotBeCreatedForDraftCourse() {
        Instructor instructor = new Instructor("I009", "Dr. Violet", "violet@example.com");
        Course course = new Course("C010", "Draft Course", 50, instructor);
        // Course 初始状态为 Draft
        assertEquals(Course.Status.Draft, course.getStatus());
        
        Student student = new Student("S009", "Grace Purple", "grace@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Enrollment("E011", Enrollment.EnrollmentStatus.Active, enrolledAt, student, course),
            "Draft 状态的课程不应该创建 Enrollment"
        );
        assertTrue(exception.getMessage().contains("Draft"));
        
        System.out.println("✅ Draft 状态的课程无法创建 Enrollment");
    }

    @Test
    public void testEnrollmentCanBeCreatedForPublishedCourse() {
        Instructor instructor = new Instructor("I010", "Dr. Indigo", "indigo@example.com");
        Course course = new Course("C011", "Published Course", 50, instructor);
        // 发布课程
        Lesson lesson = new Lesson("L002", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A002", "Assignment 1", deadline, 100, course);
        course.publish();
        assertEquals(Course.Status.Published, course.getStatus());
        
        Student student = new Student("S010", "Henry Orange", "henry@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        // 应该可以创建 Enrollment（虽然课程状态是 Published，不是 EnrollmentOpen，但约束只要求不是 Draft）
        Enrollment enrollment = new Enrollment("E012", Enrollment.EnrollmentStatus.Active, enrolledAt, student, course);
        assertNotNull(enrollment, "Published 状态的课程可以创建 Enrollment");
        
        System.out.println("✅ Published 状态的课程可以创建 Enrollment");
    }

    @Test
    public void testEnrollmentCanBeCreatedForEnrollmentOpenCourse() {
        Instructor instructor = new Instructor("I011", "Dr. Cyan", "cyan@example.com");
        Course course = new Course("C012", "Enrollment Open Course", 50, instructor);
        // 发布并开放选课
        Lesson lesson = new Lesson("L003", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A003", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus());
        
        Student student = new Student("S011", "Iris Pink", "iris@example.com");
        Date enrolledAt = new Date(System.currentTimeMillis());
        
        Enrollment enrollment = new Enrollment("E013", Enrollment.EnrollmentStatus.Active, enrolledAt, student, course);
        assertNotNull(enrollment, "EnrollmentOpen 状态的课程可以创建 Enrollment");
        
        System.out.println("✅ EnrollmentOpen 状态的课程可以创建 Enrollment");
    }

    @Test
    public void testDropCourseFromActive() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        // 发布课程以便创建 Enrollment
        Lesson lesson = new Lesson("L004", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A004", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
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
        // 发布课程以便创建 Enrollment
        Lesson lesson = new Lesson("L005", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A005", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
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
        // 发布课程以便创建 Enrollment
        Lesson lesson = new Lesson("L006", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A006", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
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
        // 发布课程以便创建 Enrollment
        Lesson lesson = new Lesson("L007", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A007", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
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
        // 发布课程以便创建 Enrollment
        Lesson lesson1 = new Lesson("L008", "Lesson 1", 1, course1);
        Lesson lesson2 = new Lesson("L009", "Lesson 1", 1, course2);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment1 = new Assignment("A008", "Assignment 1", deadline, 100, course1);
        Assignment assignment2 = new Assignment("A009", "Assignment 1", deadline, 100, course2);
        course1.publish();
        course1.openEnrollment();
        course2.publish();
        course2.openEnrollment();
        
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

