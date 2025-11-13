package com.olp.model.assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.course.Course;
import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import java.sql.Date;

/**
 * Task 3.2: Grade 类基础功能测试
 * 验证 Grade 类的构造函数验证和便捷方法
 */
public class GradeTest {

    @Test
    public void testCreateGrade() {
        // 创建必要的对象
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
        Student student = new Student("S001", "John Doe", "john@example.com");
        
        // 创建临时的 Grade（使用旧构造函数）用于创建 Submission
        Grade tempGrade = new Grade("TEMP001", 0.0, "");
        Submission submission = new Submission("SUB001", null, 1, false, tempGrade, student, assignment);
        
        // 创建真正的 Grade（使用新的构造函数）并替换临时 Grade
        Grade grade = new Grade("G001", 85.0, "Good work!", submission);
        
        assertNotNull(grade);
        assertEquals("G001", grade.getId());
        assertEquals(85.0, grade.getScore(), 0.001);
        assertEquals("Good work!", grade.getFeedback());
        assertEquals(submission, grade.getSubmission());
        
        System.out.println("✅ 可以创建 Grade 对象");
    }

    @Test
    public void testNullSubmissionThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Grade("G002", 80.0, "Feedback", null),
            "null submission 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("submission cannot be null"));
        System.out.println("✅ null submission 验证通过");
    }

    @Test
    public void testNegativeScoreThrowsException() {
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course = new Course("C002", "Python Basics", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A002", "Assignment 1", deadline, 100, course);
        Student student = new Student("S002", "Jane Doe", "jane@example.com");
        Grade tempGrade = new Grade("TEMP002", 0.0, "");
        Submission submission = new Submission("SUB002", null, 1, false, tempGrade, student, assignment);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Grade("G003", -10.0, "Feedback", submission),
            "负数 score 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("score cannot be negative"));
        System.out.println("✅ 负数 score 验证通过");
    }

    @Test
    public void testScoreExceedsMaxScoreThrowsException() {
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        Course course = new Course("C003", "Web Development", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A003", "Assignment 1", deadline, 100, course);
        Student student = new Student("S003", "Bob Smith", "bob@example.com");
        Grade tempGrade = new Grade("TEMP003", 0.0, "");
        Submission submission = new Submission("SUB003", null, 1, false, tempGrade, student, assignment);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Grade("G004", 150.0, "Feedback", submission),
            "超过 maxScore 的 score 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("cannot exceed assignment maxScore"));
        System.out.println("✅ score 超过 maxScore 验证通过");
    }

    @Test
    public void testGetPercentage() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A004", "Assignment 1", deadline, 100, course);
        Student student = new Student("S004", "Alice Brown", "alice@example.com");
        Grade tempGrade = new Grade("TEMP004", 0.0, "");
        Submission submission = new Submission("SUB004", null, 1, false, tempGrade, student, assignment);
        
        // 测试 85/100 = 85%
        Grade grade1 = new Grade("G005", 85.0, "Good", submission);
        assertEquals(85.0, grade1.getPercentage(), 0.001);
        
        // 测试 50/100 = 50%
        Grade grade2 = new Grade("G006", 50.0, "Average", submission);
        assertEquals(50.0, grade2.getPercentage(), 0.001);
        
        // 测试 100/100 = 100%
        Grade grade3 = new Grade("G007", 100.0, "Perfect", submission);
        assertEquals(100.0, grade3.getPercentage(), 0.001);
        
        // 测试 0/100 = 0%
        Grade grade4 = new Grade("G008", 0.0, "Needs improvement", submission);
        assertEquals(0.0, grade4.getPercentage(), 0.001);
        
        System.out.println("✅ getPercentage() 计算正确");
    }

    @Test
    public void testGetPercentageWithDifferentMaxScores() {
        Instructor instructor = new Instructor("I005", "Dr. Black", "black@example.com");
        Course course = new Course("C005", "Math 101", 100, instructor);
        
        // 测试 maxScore = 50
        Date deadline1 = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment1 = new Assignment("A005", "Quiz 1", deadline1, 50, course);
        Student student = new Student("S005", "Charlie Green", "charlie@example.com");
        Grade tempGrade1 = new Grade("TEMP005", 0.0, "");
        Submission submission1 = new Submission("SUB005", null, 1, false, tempGrade1, student, assignment1);
        Grade grade1 = new Grade("G009", 40.0, "Good", submission1);
        assertEquals(80.0, grade1.getPercentage(), 0.001); // 40/50 = 80%
        
        // 测试 maxScore = 200
        Date deadline2 = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment2 = new Assignment("A006", "Final Exam", deadline2, 200, course);
        Grade tempGrade2 = new Grade("TEMP006", 0.0, "");
        Submission submission2 = new Submission("SUB006", null, 1, false, tempGrade2, student, assignment2);
        Grade grade2 = new Grade("G010", 180.0, "Excellent", submission2);
        assertEquals(90.0, grade2.getPercentage(), 0.001); // 180/200 = 90%
        
        System.out.println("✅ 不同 maxScore 的百分比计算正确");
    }

    @Test
    public void testValidScoreRange() {
        Instructor instructor = new Instructor("I006", "Dr. Green", "green@example.com");
        Course course = new Course("C006", "Physics 101", 80, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A007", "Assignment 1", deadline, 100, course);
        Student student = new Student("S006", "David Blue", "david@example.com");
        Grade tempGrade = new Grade("TEMP007", 0.0, "");
        Submission submission = new Submission("SUB007", null, 1, false, tempGrade, student, assignment);
        
        // 测试边界值
        Grade grade1 = new Grade("G011", 0.0, "Minimum", submission);
        assertEquals(0.0, grade1.getScore(), 0.001);
        
        Grade grade2 = new Grade("G012", 100.0, "Maximum", submission);
        assertEquals(100.0, grade2.getScore(), 0.001);
        
        Grade grade3 = new Grade("G013", 50.5, "Decimal", submission);
        assertEquals(50.5, grade3.getScore(), 0.001);
        
        System.out.println("✅ 有效分数范围验证通过");
    }

    @Test
    public void testGradeAssociation() {
        Instructor instructor = new Instructor("I007", "Dr. Blue", "blue@example.com");
        Course course = new Course("C007", "Chemistry 101", 90, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A008", "Assignment 1", deadline, 100, course);
        Student student = new Student("S007", "Eve Red", "eve@example.com");
        Grade tempGrade = new Grade("TEMP008", 0.0, "");
        Submission submission = new Submission("SUB008", null, 1, false, tempGrade, student, assignment);
        
        Grade grade = new Grade("G014", 90.0, "Excellent work", submission);
        
        // 验证双向关联
        assertTrue(grade.hasSubmission());
        assertEquals(submission, grade.getSubmission());
        assertEquals(grade, submission.getSubmissionGrade());
        
        System.out.println("✅ Grade-Submission 关联验证正常");
    }

    // Task 5.6: GradeWithinRange 约束验证
    @Test
    public void testSetScoreWithinRange() {
        Instructor instructor = new Instructor("I008", "Dr. Silver", "silver@example.com");
        Course course = new Course("C008", "Data Structures", 60, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A009", "Assignment 1", deadline, 100, course);
        Student student = new Student("S008", "Laura Gray", "laura@example.com");
        Grade tempGrade = new Grade("TEMP009", 0.0, "");
        Submission submission = new Submission("SUB009", null, 1, false, tempGrade, student, assignment);

        Grade grade = new Grade("G015", 70.0, "Initial", submission);
        assertTrue(grade.setScore(60.0));
        assertEquals(60.0, grade.getScore(), 0.001);

        System.out.println("✅ setScore 可以在合法范围内更新分数");
    }

    @Test
    public void testSetScoreBelowZeroThrowsException() {
        Instructor instructor = new Instructor("I009", "Dr. Bronze", "bronze@example.com");
        Course course = new Course("C009", "Algorithms", 60, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A010", "Assignment 1", deadline, 100, course);
        Student student = new Student("S009", "Kevin Blue", "kevin@example.com");
        Grade tempGrade = new Grade("TEMP010", 0.0, "");
        Submission submission = new Submission("SUB010", null, 1, false, tempGrade, student, assignment);

        Grade grade = new Grade("G016", 50.0, "Initial", submission);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> grade.setScore(-5.0),
            "负分更新应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("cannot be negative"));

        System.out.println("✅ setScore 拒绝小于 0 的分数");
    }

    @Test
    public void testSetScoreAboveMaxThrowsException() {
        Instructor instructor = new Instructor("I010", "Dr. Copper", "copper@example.com");
        Course course = new Course("C010", "Operating Systems", 60, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A011", "Assignment 1", deadline, 100, course);
        Student student = new Student("S010", "Mason Indigo", "mason@example.com");
        Grade tempGrade = new Grade("TEMP011", 0.0, "");
        Submission submission = new Submission("SUB011", null, 1, false, tempGrade, student, assignment);

        Grade grade = new Grade("G017", 80.0, "Initial", submission);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> grade.setScore(150.0),
            "超过 maxScore 的更新应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("cannot exceed assignment maxScore"));

        System.out.println("✅ setScore 拒绝超过 maxScore 的分数");
    }

    @Test
    public void testSetSubmissionRejectsIncompatibleAssignment() {
        Instructor instructor = new Instructor("I011", "Dr. Platinum", "platinum@example.com");
        Course course = new Course("C011", "Computer Networks", 60, instructor);

        Date deadline1 = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment1 = new Assignment("A012", "Assignment 1", deadline1, 100, course);
        Student student = new Student("S011", "Nina Violet", "nina@example.com");
        Grade tempGrade1 = new Grade("TEMP012", 0.0, "");
        Submission submission1 = new Submission("SUB012", null, 1, false, tempGrade1, student, assignment1);

        Grade grade = new Grade("G018", 90.0, "Initial", submission1);

        Date deadline2 = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment2 = new Assignment("A013", "Assignment 2", deadline2, 80, course);
        Grade tempGrade2 = new Grade("TEMP013", 0.0, "");
        Submission submission2 = new Submission("SUB013", null, 1, false, tempGrade2, student, assignment2);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> grade.setSubmission(submission2),
            "分数高于新作业最大值时不应允许重新关联"
        );
        assertTrue(exception.getMessage().contains("cannot exceed assignment maxScore"));

        System.out.println("✅ setSubmission 拒绝与当前分数不兼容的作业");
    }
}

