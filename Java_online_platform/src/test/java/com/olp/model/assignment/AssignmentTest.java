package com.olp.model.assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.course.Course;
import com.olp.model.user.Instructor;
import java.sql.Date;

/**
 * Task 3.1: Assignment 类基础功能测试
 * 验证 Assignment 类的构造函数验证和便捷方法
 */
public class AssignmentTest {

    @Test
    public void testCreateAssignment() {
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000); // 明天
        
        Assignment assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
        assertNotNull(assignment);
        assertEquals("A001", assignment.getId());
        assertEquals("Assignment 1", assignment.getTitle());
        assertEquals(100, assignment.getMaxScore());
        assertEquals(deadline, assignment.getDeadline());
        assertEquals(course, assignment.getCourse());
        
        System.out.println("✅ 可以创建 Assignment 对象");
    }

    @Test
    public void testNullTitleThrowsException() {
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course = new Course("C002", "Python Basics", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Assignment("A002", null, deadline, 100, course),
            "null title 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("title cannot be null or empty"));
        System.out.println("✅ null title 验证通过");
    }

    @Test
    public void testEmptyTitleThrowsException() {
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        Course course = new Course("C003", "Web Development", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Assignment("A003", "", deadline, 100, course),
            "空 title 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("title cannot be null or empty"));
        System.out.println("✅ 空 title 验证通过");
    }

    @Test
    public void testZeroMaxScoreThrowsException() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Assignment("A004", "Assignment 1", deadline, 0, course),
            "maxScore = 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("maxScore must be greater than 0"));
        System.out.println("✅ maxScore = 0 验证通过");
    }

    @Test
    public void testNegativeMaxScoreThrowsException() {
        Instructor instructor = new Instructor("I005", "Dr. Black", "black@example.com");
        Course course = new Course("C005", "Math 101", 100, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Assignment("A005", "Assignment 1", deadline, -10, course),
            "maxScore < 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("maxScore must be greater than 0"));
        System.out.println("✅ maxScore < 0 验证通过");
    }

    @Test
    public void testNullDeadlineThrowsException() {
        Instructor instructor = new Instructor("I006", "Dr. Green", "green@example.com");
        Course course = new Course("C006", "Physics 101", 80, instructor);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Assignment("A006", "Assignment 1", null, 100, course),
            "null deadline 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("deadline cannot be null"));
        System.out.println("✅ null deadline 验证通过");
    }

    @Test
    public void testNullCourseThrowsException() {
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Assignment("A007", "Assignment 1", deadline, 100, null),
            "null course 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("course cannot be null"));
        System.out.println("✅ null course 验证通过");
    }

    @Test
    public void testIsOverdue() {
        Instructor instructor = new Instructor("I007", "Dr. Blue", "blue@example.com");
        Course course = new Course("C007", "Chemistry 101", 90, instructor);
        
        // 测试未过期的作业（明天截止）
        Date futureDeadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment1 = new Assignment("A008", "Future Assignment", futureDeadline, 100, course);
        assertFalse(assignment1.isOverdue(), "未来的截止日期不应该过期");
        
        // 测试已过期的作业（昨天截止）
        Date pastDeadline = new Date(System.currentTimeMillis() - 86400000);
        Assignment assignment2 = new Assignment("A009", "Past Assignment", pastDeadline, 100, course);
        assertTrue(assignment2.isOverdue(), "过去的截止日期应该过期");
        
        System.out.println("✅ isOverdue() 判断正确");
    }

    @Test
    public void testGetSubmissionCount() {
        Instructor instructor = new Instructor("I008", "Dr. Red", "red@example.com");
        Course course = new Course("C008", "Biology 101", 85, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        
        Assignment assignment = new Assignment("A010", "Assignment 1", deadline, 100, course);
        
        // 初始状态：没有提交
        assertEquals(0, assignment.getSubmissionCount(), "初始状态应该没有提交");
        
        // 注意：这里我们只测试方法本身，不实际创建 Submission 对象
        // 因为创建 Submission 需要更多依赖，这会在后续任务中实现
        System.out.println("✅ getSubmissionCount() 返回正确数量（初始为 0）");
    }

    @Test
    public void testValidMaxScore() {
        Instructor instructor = new Instructor("I009", "Dr. Yellow", "yellow@example.com");
        Course course = new Course("C009", "History 101", 70, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        
        // 测试各种有效的 maxScore 值
        Assignment assignment1 = new Assignment("A011", "Assignment 1", deadline, 1, course);
        assertEquals(1, assignment1.getMaxScore());
        
        Assignment assignment2 = new Assignment("A012", "Assignment 2", deadline, 100, course);
        assertEquals(100, assignment2.getMaxScore());
        
        Assignment assignment3 = new Assignment("A013", "Assignment 3", deadline, 1000, course);
        assertEquals(1000, assignment3.getMaxScore());
        
        System.out.println("✅ 有效 maxScore 验证通过");
    }

    @Test
    public void testAssignmentAssociation() {
        Instructor instructor = new Instructor("I010", "Dr. Purple", "purple@example.com");
        Course course = new Course("C010", "Literature 101", 60, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        
        Assignment assignment = new Assignment("A014", "Essay Assignment", deadline, 100, course);
        
        // 验证双向关联
        assertTrue(course.hasCourseAssignments());
        assertEquals(1, course.numberOfCourseAssignments());
        assertEquals(assignment, course.getCourseAssignment(0));
        
        System.out.println("✅ Assignment-Course 关联验证正常");
    }
}

