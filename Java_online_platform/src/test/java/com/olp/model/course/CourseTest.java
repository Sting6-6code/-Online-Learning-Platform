package com.olp.model.course;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.user.Instructor;
import com.olp.model.assignment.Assignment;
import java.sql.Date;

/**
 * Task 2.5-2.6: Course 类构造函数验证和 publish() 方法测试
 * 验证 Course 类的基本验证、初始状态和发布功能
 */
@SpringBootTest
public class CourseTest {

    @Test
    public void testCreateCourse() {
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        
        assertNotNull(course);
        assertEquals("C001", course.getId());
        assertEquals("Java Programming", course.getTitle());
        assertEquals(50, course.getCapacity());
        assertEquals(instructor, course.getInstructor());
        assertEquals(Course.Status.Draft, course.getStatus(), "初始状态应该为 Draft");
        
        System.out.println("✅ 可以创建 Course 对象");
    }

    @Test
    public void testNullTitleThrowsException() {
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Course("C002", null, 50, instructor),
            "null title 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("title cannot be null or empty"));
        System.out.println("✅ null title 验证通过");
    }

    @Test
    public void testEmptyTitleThrowsException() {
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Course("C003", "", 50, instructor),
            "空 title 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("title cannot be null or empty"));
        System.out.println("✅ 空 title 验证通过");
    }

    @Test
    public void testZeroCapacityThrowsException() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Course("C004", "Database Design", 0, instructor),
            "capacity = 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("capacity must be greater than 0"));
        System.out.println("✅ capacity = 0 验证通过");
    }

    @Test
    public void testNegativeCapacityThrowsException() {
        Instructor instructor = new Instructor("I005", "Dr. Black", "black@example.com");
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Course("C005", "Math 101", -10, instructor),
            "capacity < 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("capacity must be greater than 0"));
        System.out.println("✅ capacity < 0 验证通过");
    }

    @Test
    public void testNullInstructorThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Course("C006", "Web Development", 30, null),
            "null instructor 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("instructor cannot be null"));
        System.out.println("✅ null instructor 验证通过");
    }

    @Test
    public void testInitialStatusIsDraft() {
        Instructor instructor = new Instructor("I006", "Dr. Green", "green@example.com");
        Course course = new Course("C007", "Physics 101", 80, instructor);
        
        assertEquals(Course.Status.Draft, course.getStatus(), "新创建的课程初始状态应该为 Draft");
        
        System.out.println("✅ 初始状态为 Draft");
    }

    @Test
    public void testValidCapacity() {
        Instructor instructor = new Instructor("I007", "Dr. Blue", "blue@example.com");
        
        // 测试各种有效的容量值
        Course course1 = new Course("C008", "Course 1", 1, instructor);
        assertEquals(1, course1.getCapacity());
        
        Course course2 = new Course("C009", "Course 2", 50, instructor);
        assertEquals(50, course2.getCapacity());
        
        Course course3 = new Course("C010", "Course 3", 1000, instructor);
        assertEquals(1000, course3.getCapacity());
        
        System.out.println("✅ 有效容量验证通过");
    }

    @Test
    public void testCourseAssociation() {
        Instructor instructor = new Instructor("I008", "Dr. Red", "red@example.com");
        Course course = new Course("C011", "Chemistry 101", 90, instructor);
        
        // 验证双向关联
        assertTrue(instructor.hasTaughtCourses());
        assertEquals(1, instructor.numberOfTaughtCourses());
        assertEquals(course, instructor.getTaughtCourse(0));
        
        System.out.println("✅ Course-Instructor 关联验证正常");
    }

    // Task 2.6: publish() 方法测试
    @Test
    public void testPublishWithMinimumContent() {
        Instructor instructor = new Instructor("I009", "Dr. Yellow", "yellow@example.com");
        Course course = new Course("C012", "Java Advanced", 50, instructor);
        
        // 添加至少一个 Lesson
        Lesson lesson = new Lesson("L001", "Introduction", 1, course);
        
        // 添加至少一个 Assignment
        Date deadline = new Date(System.currentTimeMillis() + 86400000); // 明天
        Assignment assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
        
        // 发布课程
        boolean published = course.publish();
        assertTrue(published, "有内容的 Draft 课程应该可以发布");
        assertEquals(Course.Status.Published, course.getStatus(), "状态应该变为 Published");
        
        System.out.println("✅ Draft 状态的课程可以发布（有内容）");
    }

    @Test
    public void testPublishWithoutLessons() {
        Instructor instructor = new Instructor("I010", "Dr. Purple", "purple@example.com");
        Course course = new Course("C013", "Python Basics", 40, instructor);
        
        // 只添加 Assignment，没有 Lesson
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A002", "Assignment 1", deadline, 100, course);
        
        // 尝试发布课程
        boolean published = course.publish();
        assertFalse(published, "没有 Lesson 的课程不应该发布");
        assertEquals(Course.Status.Draft, course.getStatus(), "状态应该保持为 Draft");
        
        System.out.println("✅ 没有 Lesson 的课程发布失败");
    }

    @Test
    public void testPublishWithoutAssignments() {
        Instructor instructor = new Instructor("I011", "Dr. Orange", "orange@example.com");
        Course course = new Course("C014", "Web Development", 30, instructor);
        
        // 只添加 Lesson，没有 Assignment
        Lesson lesson = new Lesson("L002", "Introduction", 1, course);
        
        // 尝试发布课程
        boolean published = course.publish();
        assertFalse(published, "没有 Assignment 的课程不应该发布");
        assertEquals(Course.Status.Draft, course.getStatus(), "状态应该保持为 Draft");
        
        System.out.println("✅ 没有 Assignment 的课程发布失败");
    }

    @Test
    public void testPublishFromNonDraftStatus() {
        Instructor instructor = new Instructor("I012", "Dr. Pink", "pink@example.com");
        Course course = new Course("C015", "Database Design", 25, instructor);
        
        // 添加内容
        Lesson lesson = new Lesson("L003", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A003", "Assignment 1", deadline, 100, course);
        
        // 先发布
        course.publish();
        assertEquals(Course.Status.Published, course.getStatus());
        
        // 尝试再次发布（从 Published 状态）
        boolean published = course.publish();
        assertFalse(published, "非 Draft 状态的课程不应该再次发布");
        assertEquals(Course.Status.Published, course.getStatus(), "状态应该保持为 Published");
        
        System.out.println("✅ 非 Draft 状态的课程发布失败");
    }

    @Test
    public void testPublishWithEmptyContent() {
        Instructor instructor = new Instructor("I013", "Dr. Cyan", "cyan@example.com");
        Course course = new Course("C016", "Math 101", 100, instructor);
        
        // 不添加任何内容
        boolean published = course.publish();
        assertFalse(published, "没有内容的课程不应该发布");
        assertEquals(Course.Status.Draft, course.getStatus(), "状态应该保持为 Draft");
        
        System.out.println("✅ 没有内容的课程发布失败");
    }
}

