package com.olp.model.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.course.Course;
import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import java.sql.Date;

/**
 * Task 3.10: 提交工作流集成测试
 * 测试完整的提交工作流程和失败流程
 */
@SpringBootTest
public class SubmissionWorkflowTest {

    @Test
    public void testCompleteSubmissionWorkflow() {
        // 准备数据
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000 * 7); // 一周后截止
        Assignment assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
        Student student = new Student("S001", "John Doe", "john@example.com");
        Grade tempGrade = new Grade("TEMP001", 0.0, "");
        
        // 1. 创建 Submission（状态：Created）
        Submission submission = new Submission("SUB001", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus(), "步骤1: 初始状态应为 Created");
        assertNull(submission.getSubmittedAt());
        assertEquals(0, submission.getVersion());
        
        // 2. 提交（状态：Submitted）
        boolean submitted = submission.submit();
        assertTrue(submitted, "步骤2: 应该可以提交");
        assertEquals(Submission.Status.Submitted, submission.getStatus(), "步骤2: 状态应为 Submitted");
        assertNotNull(submission.getSubmittedAt(), "步骤2: 提交时间应该被记录");
        assertEquals(1, submission.getVersion(), "步骤2: 版本号应该为 1");
        
        // 3. 开始自动检查（状态：UnderCheck）
        boolean checksStarted = submission.startAutoChecks();
        assertTrue(checksStarted, "步骤3: 应该可以开始检查");
        assertEquals(Submission.Status.UnderCheck, submission.getStatus(), "步骤3: 状态应为 UnderCheck");
        
        // 4. 检查通过（状态：Submitted，checkPassed = true）
        boolean checksPassed = submission.checksPass();
        assertTrue(checksPassed, "步骤4: 应该可以标记为通过");
        assertEquals(Submission.Status.Submitted, submission.getStatus(), "步骤4: 状态应回到 Submitted");
        assertTrue(submission.getCheckPassed(), "步骤4: checkPassed 应为 true");
        
        // 5. 开始评分（状态：Grading）
        boolean gradingStarted = submission.startGrading();
        assertTrue(gradingStarted, "步骤5: 应该可以开始评分");
        assertEquals(Submission.Status.Grading, submission.getStatus(), "步骤5: 状态应为 Grading");
        
        // 6. 评分（状态：Graded）
        boolean graded = submission.grade(85.0, "Excellent work!");
        assertTrue(graded, "步骤6: 应该可以评分");
        assertEquals(Submission.Status.Graded, submission.getStatus(), "步骤6: 状态应为 Graded");
        assertNotNull(submission.getSubmissionGrade(), "步骤6: Grade 对象应该被创建");
        assertEquals(85.0, submission.getSubmissionGrade().getScore(), 0.001);
        assertEquals("Excellent work!", submission.getSubmissionGrade().getFeedback());
        
        System.out.println("✅ 完整提交工作流测试通过");
    }

    @Test
    public void testFailedSubmissionWorkflow() {
        // 准备数据
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course = new Course("C002", "Python Basics", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000 * 7);
        Assignment assignment = new Assignment("A002", "Assignment 1", deadline, 100, course);
        Student student = new Student("S002", "Jane Doe", "jane@example.com");
        Grade tempGrade = new Grade("TEMP002", 0.0, "");
        
        // 1. 创建 Submission
        Submission submission = new Submission("SUB002", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus(), "步骤1: 初始状态应为 Created");
        
        // 2. 提交
        boolean submitted = submission.submit();
        assertTrue(submitted, "步骤2: 应该可以提交");
        assertEquals(Submission.Status.Submitted, submission.getStatus(), "步骤2: 状态应为 Submitted");
        
        // 3. 开始自动检查
        boolean checksStarted = submission.startAutoChecks();
        assertTrue(checksStarted, "步骤3: 应该可以开始检查");
        assertEquals(Submission.Status.UnderCheck, submission.getStatus(), "步骤3: 状态应为 UnderCheck");
        
        // 4. 检查失败（状态：Returned）
        boolean checksFailed = submission.checksFail();
        assertTrue(checksFailed, "步骤4: 应该可以标记为失败");
        assertEquals(Submission.Status.Returned, submission.getStatus(), "步骤4: 状态应为 Returned");
        assertFalse(submission.getCheckPassed(), "步骤4: checkPassed 应为 false");
        
        // 5. 要求重交（状态：ResubmissionRequested）
        boolean resubmissionRequested = submission.requestResubmission();
        assertTrue(resubmissionRequested, "步骤5: 应该可以要求重交");
        assertEquals(Submission.Status.ResubmissionRequested, submission.getStatus(), "步骤5: 状态应为 ResubmissionRequested");
        
        System.out.println("✅ 失败流程测试通过");
    }

    @Test
    public void testResubmissionWorkflow() {
        // 准备数据
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        Course course = new Course("C003", "Web Development", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000 * 7);
        Assignment assignment = new Assignment("A003", "Assignment 1", deadline, 100, course);
        Student student = new Student("S003", "Bob Smith", "bob@example.com");
        
        // 第一次提交
        Grade tempGrade1 = new Grade("TEMP003", 0.0, "");
        Submission submission1 = new Submission("SUB003", null, 0, false, tempGrade1, student, assignment);
        submission1.submit();
        submission1.startAutoChecks();
        submission1.checksPass();
        submission1.startGrading();
        submission1.grade(60.0, "Needs improvement");
        submission1.requestResubmission();
        assertEquals(Submission.Status.ResubmissionRequested, submission1.getStatus());
        
        // 第二次提交（重交）
        Grade tempGrade2 = new Grade("TEMP004", 0.0, "");
        Submission submission2 = new Submission("SUB004", null, 0, false, tempGrade2, student, assignment);
        boolean submitted2 = submission2.submit();
        assertTrue(submitted2, "应该可以重交");
        assertEquals(Submission.Status.Submitted, submission2.getStatus());
        assertEquals(2, submission2.getVersion(), "重交的版本号应该为 2");
        
        // 完成第二次提交的流程
        submission2.startAutoChecks();
        submission2.checksPass();
        submission2.startGrading();
        submission2.grade(90.0, "Much better!");
        assertEquals(Submission.Status.Graded, submission2.getStatus());
        
        System.out.println("✅ 重交工作流测试通过");
    }

    @Test
    public void testWorkflowStateTransitions() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000 * 7);
        Assignment assignment = new Assignment("A004", "Assignment 1", deadline, 100, course);
        Student student = new Student("S004", "Alice Brown", "alice@example.com");
        Grade tempGrade = new Grade("TEMP005", 0.0, "");
        
        Submission submission = new Submission("SUB005", null, 0, false, tempGrade, student, assignment);
        
        // 验证状态转换序列
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        submission.submit();
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        
        submission.startAutoChecks();
        assertEquals(Submission.Status.UnderCheck, submission.getStatus());
        
        submission.checksPass();
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        assertTrue(submission.getCheckPassed());
        
        submission.startGrading();
        assertEquals(Submission.Status.Grading, submission.getStatus());
        
        submission.grade(95.0, "Perfect!");
        assertEquals(Submission.Status.Graded, submission.getStatus());
        
        System.out.println("✅ 状态转换序列验证通过");
    }

    @Test
    public void testWorkflowGuards() {
        Instructor instructor = new Instructor("I005", "Dr. Black", "black@example.com");
        Course course = new Course("C005", "Math 101", 100, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000 * 7);
        Assignment assignment = new Assignment("A005", "Assignment 1", deadline, 100, course);
        Student student = new Student("S005", "Charlie Green", "charlie@example.com");
        Grade tempGrade = new Grade("TEMP006", 0.0, "");
        
        Submission submission = new Submission("SUB006", null, 0, false, tempGrade, student, assignment);
        
        // 测试守卫条件：不能跳过状态
        assertFalse(submission.startAutoChecks(), "Created 状态不能直接开始检查");
        assertFalse(submission.startGrading(), "Created 状态不能直接开始评分");
        assertFalse(submission.grade(85.0, "Feedback"), "Created 状态不能直接评分");
        
        submission.submit();
        assertFalse(submission.startGrading(), "Submitted 状态且 checkPassed=false 不能开始评分");
        assertFalse(submission.grade(85.0, "Feedback"), "Submitted 状态不能直接评分");
        
        submission.startAutoChecks();
        assertFalse(submission.startGrading(), "UnderCheck 状态不能开始评分");
        assertFalse(submission.grade(85.0, "Feedback"), "UnderCheck 状态不能评分");
        
        System.out.println("✅ 守卫条件验证通过");
    }
}

