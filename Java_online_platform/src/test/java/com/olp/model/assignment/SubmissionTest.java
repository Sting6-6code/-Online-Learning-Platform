package com.olp.model.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.course.Course;
import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import java.sql.Date;

/**
 * Task 3.3-3.4: Submission 类构造函数验证和 submit() 方法测试
 * 验证 Submission 类的基本验证、初始状态和提交功能
 */
@SpringBootTest
public class SubmissionTest {

    @Test
    public void testCreateSubmission() {
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
        Student student = new Student("S001", "John Doe", "john@example.com");
        Grade tempGrade = new Grade("TEMP001", 0.0, "");
        
        Submission submission = new Submission("SUB001", null, 1, false, tempGrade, student, assignment);
        
        assertNotNull(submission);
        assertEquals("SUB001", submission.getId());
        assertEquals(Submission.Status.Created, submission.getStatus(), "初始状态应该为 Created");
        assertEquals(1, submission.getVersion(), "初始 version 应该为构造函数参数值");
        assertFalse(submission.getCheckPassed(), "初始 checkPassed 应该为构造函数参数值");
        assertNull(submission.getSubmittedAt(), "submittedAt 初始可为 null");
        assertEquals(student, submission.getStudent());
        assertEquals(assignment, submission.getAssignment());
        
        System.out.println("✅ 可以创建 Submission 对象");
    }

    @Test
    public void testNullStudentThrowsException() {
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course = new Course("C002", "Python Basics", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A002", "Assignment 1", deadline, 100, course);
        Grade tempGrade = new Grade("TEMP002", 0.0, "");
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Submission("SUB002", null, 1, false, tempGrade, null, assignment),
            "null student 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("student cannot be null"));
        System.out.println("✅ null student 验证通过");
    }

    @Test
    public void testNullAssignmentThrowsException() {
        Student student = new Student("S002", "Jane Doe", "jane@example.com");
        Grade tempGrade = new Grade("TEMP003", 0.0, "");
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Submission("SUB003", null, 1, false, tempGrade, student, null),
            "null assignment 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("assignment cannot be null"));
        System.out.println("✅ null assignment 验证通过");
    }

    @Test
    public void testInitialStatusIsCreated() {
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        Course course = new Course("C003", "Web Development", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A003", "Assignment 1", deadline, 100, course);
        Student student = new Student("S003", "Bob Smith", "bob@example.com");
        Grade tempGrade = new Grade("TEMP004", 0.0, "");
        
        Submission submission = new Submission("SUB004", null, 1, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus(), "新创建的 Submission 初始状态应该为 Created");
        
        System.out.println("✅ 初始状态为 Created");
    }

    @Test
    public void testInitialVersion() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A004", "Assignment 1", deadline, 100, course);
        Student student = new Student("S004", "Alice Brown", "alice@example.com");
        
        // 测试不同的 version 值（每个 submission 需要独立的 Grade）
        Grade tempGrade1 = new Grade("TEMP005", 0.0, "");
        Submission submission1 = new Submission("SUB005", null, 1, false, tempGrade1, student, assignment);
        assertEquals(1, submission1.getVersion());
        
        Grade tempGrade2 = new Grade("TEMP006", 0.0, "");
        Submission submission2 = new Submission("SUB006", null, 2, false, tempGrade2, student, assignment);
        assertEquals(2, submission2.getVersion());
        
        Grade tempGrade3 = new Grade("TEMP007", 0.0, "");
        Submission submission3 = new Submission("SUB007", null, 10, false, tempGrade3, student, assignment);
        assertEquals(10, submission3.getVersion());
        
        System.out.println("✅ 初始 version 为构造函数参数值");
    }

    @Test
    public void testInitialCheckPassed() {
        Instructor instructor = new Instructor("I005", "Dr. Black", "black@example.com");
        Course course = new Course("C005", "Math 101", 100, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A005", "Assignment 1", deadline, 100, course);
        Student student = new Student("S005", "Charlie Green", "charlie@example.com");
        Grade tempGrade1 = new Grade("TEMP006", 0.0, "");
        Grade tempGrade2 = new Grade("TEMP007", 0.0, "");
        
        // 测试 checkPassed = false
        Submission submission1 = new Submission("SUB008", null, 1, false, tempGrade1, student, assignment);
        assertFalse(submission1.getCheckPassed(), "checkPassed = false 应该正确设置");
        
        // 测试 checkPassed = true
        Submission submission2 = new Submission("SUB009", null, 1, true, tempGrade2, student, assignment);
        assertTrue(submission2.getCheckPassed(), "checkPassed = true 应该正确设置");
        
        System.out.println("✅ 初始 checkPassed 为构造函数参数值");
    }

    @Test
    public void testSubmittedAtCanBeNull() {
        Instructor instructor = new Instructor("I006", "Dr. Green", "green@example.com");
        Course course = new Course("C006", "Physics 101", 80, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A006", "Assignment 1", deadline, 100, course);
        Student student = new Student("S006", "David Blue", "david@example.com");
        Grade tempGrade = new Grade("TEMP008", 0.0, "");
        
        // submittedAt 为 null
        Grade tempGrade1 = new Grade("TEMP008", 0.0, "");
        Submission submission1 = new Submission("SUB010", null, 1, false, tempGrade1, student, assignment);
        assertNull(submission1.getSubmittedAt(), "submittedAt 初始可为 null");
        
        // submittedAt 不为 null
        Date submittedAt = new Date(System.currentTimeMillis());
        Grade tempGrade2 = new Grade("TEMP009", 0.0, "");
        Submission submission2 = new Submission("SUB011", submittedAt, 1, false, tempGrade2, student, assignment);
        assertEquals(submittedAt, submission2.getSubmittedAt(), "submittedAt 可以设置值");
        
        System.out.println("✅ submittedAt 初始可为 null");
    }

    @Test
    public void testSubmissionAssociation() {
        Instructor instructor = new Instructor("I007", "Dr. Blue", "blue@example.com");
        Course course = new Course("C007", "Chemistry 101", 90, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A007", "Assignment 1", deadline, 100, course);
        Student student = new Student("S007", "Eve Red", "eve@example.com");
        Grade tempGrade = new Grade("TEMP009", 0.0, "");
        
        Submission submission = new Submission("SUB012", null, 1, false, tempGrade, student, assignment);
        
        // 验证双向关联
        assertTrue(student.hasStudentSubmissions());
        assertEquals(1, student.numberOfStudentSubmissions());
        assertEquals(submission, student.getStudentSubmission(0));
        
        assertTrue(assignment.hasAssignmentSubmissions());
        assertEquals(1, assignment.numberOfAssignmentSubmissions());
        assertEquals(submission, assignment.getAssignmentSubmission(0));
        
        System.out.println("✅ Submission 关联验证正常");
    }

    // Task 3.4: submit() 方法测试
    @Test
    public void testSubmitFromCreated() {
        Instructor instructor = new Instructor("I008", "Dr. Purple", "purple@example.com");
        Course course = new Course("C008", "React Development", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000); // 明天截止
        Assignment assignment = new Assignment("A008", "Assignment 1", deadline, 100, course);
        Student student = new Student("S008", "Frank Yellow", "frank@example.com");
        Grade tempGrade = new Grade("TEMP010", 0.0, "");
        
        Submission submission = new Submission("SUB013", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus());
        assertNull(submission.getSubmittedAt());
        
        // 提交
        boolean submitted = submission.submit();
        assertTrue(submitted, "Created 状态的提交应该可以提交");
        assertEquals(Submission.Status.Submitted, submission.getStatus(), "状态应该变为 Submitted");
        assertNotNull(submission.getSubmittedAt(), "提交时间应该被记录");
        assertEquals(1, submission.getVersion(), "第一个提交 version 应该为 1");
        
        System.out.println("✅ Created 状态的提交可以提交");
    }

    @Test
    public void testSubmitAfterDeadline() {
        Instructor instructor = new Instructor("I009", "Dr. Orange", "orange@example.com");
        Course course = new Course("C009", "Vue.js Basics", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() - 86400000); // 昨天截止（已过期）
        Assignment assignment = new Assignment("A009", "Assignment 1", deadline, 100, course);
        Student student = new Student("S009", "Grace Pink", "grace@example.com");
        Grade tempGrade = new Grade("TEMP011", 0.0, "");
        
        Submission submission = new Submission("SUB014", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 尝试提交（已过期）
        boolean submitted = submission.submit();
        assertFalse(submitted, "超过截止时间的提交应该失败");
        assertEquals(Submission.Status.Created, submission.getStatus(), "状态应该保持为 Created");
        assertNull(submission.getSubmittedAt(), "提交时间不应该被记录");
        
        System.out.println("✅ 超过截止时间的提交失败");
    }

    @Test
    public void testVersionAutoIncrement() {
        Instructor instructor = new Instructor("I010", "Dr. Cyan", "cyan@example.com");
        Course course = new Course("C010", "Angular Framework", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000 * 7); // 一周后截止
        Assignment assignment = new Assignment("A010", "Assignment 1", deadline, 100, course);
        Student student = new Student("S010", "Henry Indigo", "henry@example.com");
        
        // 第一个提交
        Grade tempGrade1 = new Grade("TEMP012", 0.0, "");
        Submission submission1 = new Submission("SUB015", null, 0, false, tempGrade1, student, assignment);
        boolean submitted1 = submission1.submit();
        assertTrue(submitted1);
        assertEquals(1, submission1.getVersion(), "第一个提交 version 应该为 1");
        
        // 第二个提交（同一学生，同一作业）
        Grade tempGrade2 = new Grade("TEMP013", 0.0, "");
        Submission submission2 = new Submission("SUB016", null, 0, false, tempGrade2, student, assignment);
        boolean submitted2 = submission2.submit();
        assertTrue(submitted2);
        assertEquals(2, submission2.getVersion(), "第二个提交 version 应该为 2");
        
        // 第三个提交
        Grade tempGrade3 = new Grade("TEMP014", 0.0, "");
        Submission submission3 = new Submission("SUB017", null, 0, false, tempGrade3, student, assignment);
        boolean submitted3 = submission3.submit();
        assertTrue(submitted3);
        assertEquals(3, submission3.getVersion(), "第三个提交 version 应该为 3");
        
        System.out.println("✅ 版本号自动递增（第一个提交 version = 1，第二个 version = 2）");
    }

    @Test
    public void testSubmitTimeRecorded() {
        Instructor instructor = new Instructor("I011", "Dr. Magenta", "magenta@example.com");
        Course course = new Course("C011", "Node.js Advanced", 25, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A011", "Assignment 1", deadline, 100, course);
        Student student = new Student("S011", "Iris Teal", "iris@example.com");
        Grade tempGrade = new Grade("TEMP015", 0.0, "");
        
        Submission submission = new Submission("SUB018", null, 0, false, tempGrade, student, assignment);
        assertNull(submission.getSubmittedAt(), "提交前 submittedAt 应该为 null");
        
        // 记录提交前的时间
        long beforeSubmit = System.currentTimeMillis();
        
        // 提交
        boolean submitted = submission.submit();
        assertTrue(submitted);
        
        // 记录提交后的时间
        long afterSubmit = System.currentTimeMillis();
        
        // 验证提交时间被记录
        assertNotNull(submission.getSubmittedAt(), "提交时间应该被记录");
        long submittedAtTime = submission.getSubmittedAt().getTime();
        assertTrue(submittedAtTime >= beforeSubmit && submittedAtTime <= afterSubmit, 
                   "提交时间应该在提交操作的时间范围内");
        
        System.out.println("✅ 提交时间被记录");
    }

    @Test
    public void testSubmitFromNonCreatedStatus() {
        Instructor instructor = new Instructor("I012", "Dr. Lavender", "lavender@example.com");
        Course course = new Course("C012", "Docker & Kubernetes", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A012", "Assignment 1", deadline, 100, course);
        Student student = new Student("S012", "Jack Amber", "jack@example.com");
        Grade tempGrade = new Grade("TEMP016", 0.0, "");
        
        Submission submission = new Submission("SUB019", null, 0, false, tempGrade, student, assignment);
        submission.submit(); // 先提交一次
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        
        // 尝试再次提交（从 Submitted 状态）
        boolean submitted = submission.submit();
        assertFalse(submitted, "非 Created 状态的提交应该失败");
        assertEquals(Submission.Status.Submitted, submission.getStatus(), "状态应该保持为 Submitted");
        
        System.out.println("✅ 非 Created 状态的提交失败");
    }

    @Test
    public void testVersionCountsOnlySameStudent() {
        Instructor instructor = new Instructor("I013", "Dr. Coral", "coral@example.com");
        Course course = new Course("C013", "Microservices Architecture", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A013", "Assignment 1", deadline, 100, course);
        
        // 学生1的第一个提交
        Student student1 = new Student("S013", "Kelly Jade", "kelly@example.com");
        Grade tempGrade1 = new Grade("TEMP017", 0.0, "");
        Submission submission1 = new Submission("SUB020", null, 0, false, tempGrade1, student1, assignment);
        submission1.submit();
        assertEquals(1, submission1.getVersion(), "学生1的第一个提交 version = 1");
        
        // 学生2的第一个提交（应该也是 version = 1，因为只统计同一学生的提交）
        Student student2 = new Student("S014", "Liam Opal", "liam@example.com");
        Grade tempGrade2 = new Grade("TEMP018", 0.0, "");
        Submission submission2 = new Submission("SUB021", null, 0, false, tempGrade2, student2, assignment);
        submission2.submit();
        assertEquals(1, submission2.getVersion(), "学生2的第一个提交 version = 1（独立计数）");
        
        // 学生1的第二个提交
        Grade tempGrade3 = new Grade("TEMP019", 0.0, "");
        Submission submission3 = new Submission("SUB022", null, 0, false, tempGrade3, student1, assignment);
        submission3.submit();
        assertEquals(2, submission3.getVersion(), "学生1的第二个提交 version = 2");
        
        System.out.println("✅ 版本号只统计同一学生的提交");
    }

    // Task 3.5: startAutoChecks() 方法测试
    @Test
    public void testStartAutoChecksFromSubmitted() {
        Instructor instructor = new Instructor("I014", "Dr. Mint", "mint@example.com");
        Course course = new Course("C014", "Cloud Computing", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A014", "Assignment 1", deadline, 100, course);
        Student student = new Student("S015", "Mia Quartz", "mia@example.com");
        Grade tempGrade = new Grade("TEMP020", 0.0, "");
        
        Submission submission = new Submission("SUB023", null, 0, false, tempGrade, student, assignment);
        submission.submit(); // 先提交
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        
        // 开始自动检查
        boolean started = submission.startAutoChecks();
        assertTrue(started, "Submitted 状态的提交应该可以开始检查");
        assertEquals(Submission.Status.UnderCheck, submission.getStatus(), "状态应该变为 UnderCheck");
        
        System.out.println("✅ Submitted 状态的提交可以开始检查");
    }

    @Test
    public void testStartAutoChecksFromNonSubmittedStatus() {
        Instructor instructor = new Instructor("I015", "Dr. Sky", "sky@example.com");
        Course course = new Course("C015", "CI/CD Pipeline", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A015", "Assignment 1", deadline, 100, course);
        Student student = new Student("S016", "Noah Rose", "noah@example.com");
        Grade tempGrade = new Grade("TEMP021", 0.0, "");
        
        Submission submission = new Submission("SUB024", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Created 状态尝试开始检查
        boolean started1 = submission.startAutoChecks();
        assertFalse(started1, "Created 状态的提交不应该开始检查");
        assertEquals(Submission.Status.Created, submission.getStatus(), "状态应该保持为 Created");
        
        // 提交后开始检查，然后尝试再次开始检查
        submission.submit();
        submission.startAutoChecks();
        assertEquals(Submission.Status.UnderCheck, submission.getStatus());
        
        boolean started2 = submission.startAutoChecks();
        assertFalse(started2, "UnderCheck 状态的提交不应该再次开始检查");
        assertEquals(Submission.Status.UnderCheck, submission.getStatus(), "状态应该保持为 UnderCheck");
        
        System.out.println("✅ 非 Submitted 状态的提交检查失败");
    }

    // Task 3.6: checksPass() 和 checksFail() 方法测试
    @Test
    public void testChecksPass() {
        Instructor instructor = new Instructor("I016", "Dr. Peach", "peach@example.com");
        Course course = new Course("C016", "DevOps Practices", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A016", "Assignment 1", deadline, 100, course);
        Student student = new Student("S017", "Olivia Pearl", "olivia@example.com");
        Grade tempGrade = new Grade("TEMP022", 0.0, "");
        
        Submission submission = new Submission("SUB025", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        assertEquals(Submission.Status.UnderCheck, submission.getStatus());
        assertFalse(submission.getCheckPassed(), "初始 checkPassed 应该为 false");
        
        // 检查通过
        boolean passed = submission.checksPass();
        assertTrue(passed, "UnderCheck 状态的提交应该可以标记为通过");
        assertEquals(Submission.Status.Submitted, submission.getStatus(), "状态应该变为 Submitted");
        assertTrue(submission.getCheckPassed(), "checkPassed 应该设置为 true");
        
        System.out.println("✅ UnderCheck 状态的提交可以标记为通过");
    }

    @Test
    public void testChecksFail() {
        Instructor instructor = new Instructor("I017", "Dr. Ruby", "ruby@example.com");
        Course course = new Course("C017", "Spring Boot", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A017", "Assignment 1", deadline, 100, course);
        Student student = new Student("S018", "Paul Amethyst", "paul@example.com");
        Grade tempGrade = new Grade("TEMP023", 0.0, "");
        
        Submission submission = new Submission("SUB026", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        assertEquals(Submission.Status.UnderCheck, submission.getStatus());
        assertFalse(submission.getCheckPassed(), "初始 checkPassed 应该为 false");
        
        // 检查失败
        boolean failed = submission.checksFail();
        assertTrue(failed, "UnderCheck 状态的提交应该可以标记为失败");
        assertEquals(Submission.Status.Returned, submission.getStatus(), "状态应该变为 Returned");
        assertFalse(submission.getCheckPassed(), "checkPassed 应该设置为 false");
        
        System.out.println("✅ UnderCheck 状态的提交可以标记为失败");
    }

    @Test
    public void testChecksPassFromNonUnderCheckStatus() {
        Instructor instructor = new Instructor("I018", "Dr. Amber", "amber@example.com");
        Course course = new Course("C018", "Docker & Kubernetes", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A018", "Assignment 1", deadline, 100, course);
        Student student = new Student("S019", "Rachel Diamond", "rachel@example.com");
        Grade tempGrade = new Grade("TEMP024", 0.0, "");
        
        Submission submission = new Submission("SUB027", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Created 状态尝试 checksPass
        boolean passed1 = submission.checksPass();
        assertFalse(passed1, "Created 状态的提交不应该标记为通过");
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Submitted 状态尝试 checksPass
        submission.submit();
        boolean passed2 = submission.checksPass();
        assertFalse(passed2, "Submitted 状态的提交不应该直接标记为通过");
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        
        System.out.println("✅ 非 UnderCheck 状态的提交不能标记为通过");
    }

    @Test
    public void testChecksFailFromNonUnderCheckStatus() {
        Instructor instructor = new Instructor("I019", "Dr. Jade", "jade@example.com");
        Course course = new Course("C019", "Microservices Architecture", 25, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A019", "Assignment 1", deadline, 100, course);
        Student student = new Student("S020", "Sophia Opal", "sophia@example.com");
        Grade tempGrade = new Grade("TEMP025", 0.0, "");
        
        Submission submission = new Submission("SUB028", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Created 状态尝试 checksFail
        boolean failed1 = submission.checksFail();
        assertFalse(failed1, "Created 状态的提交不应该标记为失败");
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Submitted 状态尝试 checksFail
        submission.submit();
        boolean failed2 = submission.checksFail();
        assertFalse(failed2, "Submitted 状态的提交不应该直接标记为失败");
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        
        System.out.println("✅ 非 UnderCheck 状态的提交不能标记为失败");
    }

    @Test
    public void testCheckPassedFlagCorrectlySet() {
        Instructor instructor = new Instructor("I020", "Dr. Emerald", "emerald@example.com");
        Course course = new Course("C020", "Cloud Computing", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A020", "Assignment 1", deadline, 100, course);
        Student student = new Student("S021", "Emma Topaz", "emma@example.com");
        
        // 测试检查通过的情况
        Grade tempGrade1 = new Grade("TEMP026", 0.0, "");
        Submission submission1 = new Submission("SUB029", null, 0, false, tempGrade1, student, assignment);
        submission1.submit();
        submission1.startAutoChecks();
        submission1.checksPass();
        assertTrue(submission1.getCheckPassed(), "检查通过后 checkPassed 应该为 true");
        
        // 测试检查失败的情况
        Grade tempGrade2 = new Grade("TEMP027", 0.0, "");
        Submission submission2 = new Submission("SUB030", null, 0, false, tempGrade2, student, assignment);
        submission2.submit();
        submission2.startAutoChecks();
        submission2.checksFail();
        assertFalse(submission2.getCheckPassed(), "检查失败后 checkPassed 应该为 false");
        
        System.out.println("✅ checkPassed 标志正确设置");
    }

    // Task 3.7: startGrading() 方法测试
    @Test
    public void testStartGradingWithCheckPassed() {
        Instructor instructor = new Instructor("I021", "Dr. Sapphire", "sapphire@example.com");
        Course course = new Course("C021", "Kubernetes Advanced", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A021", "Assignment 1", deadline, 100, course);
        Student student = new Student("S022", "Isabella Quartz", "isabella@example.com");
        Grade tempGrade = new Grade("TEMP028", 0.0, "");
        
        Submission submission = new Submission("SUB031", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        submission.checksPass(); // 检查通过
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        assertTrue(submission.getCheckPassed());
        
        // 开始评分
        boolean started = submission.startGrading();
        assertTrue(started, "检查通过的提交应该可以开始评分");
        assertEquals(Submission.Status.Grading, submission.getStatus(), "状态应该变为 Grading");
        
        System.out.println("✅ 检查通过的提交可以开始评分");
    }

    @Test
    public void testStartGradingWithoutCheckPassed() {
        Instructor instructor = new Instructor("I022", "Dr. Topaz", "topaz@example.com");
        Course course = new Course("C022", "Docker Basics", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A022", "Assignment 1", deadline, 100, course);
        Student student = new Student("S023", "Ava Emerald", "ava@example.com");
        Grade tempGrade = new Grade("TEMP029", 0.0, "");
        
        Submission submission = new Submission("SUB032", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        // 不进行自动检查，直接尝试开始评分
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        assertFalse(submission.getCheckPassed(), "未检查的提交 checkPassed 应该为 false");
        
        // 尝试开始评分（checkPassed = false）
        boolean started = submission.startGrading();
        assertFalse(started, "检查未通过的提交不应该开始评分");
        assertEquals(Submission.Status.Submitted, submission.getStatus(), "状态应该保持为 Submitted");
        
        System.out.println("✅ 检查未通过的提交评分失败");
    }

    @Test
    public void testStartGradingFromNonSubmittedStatus() {
        Instructor instructor = new Instructor("I023", "Dr. Diamond", "diamond@example.com");
        Course course = new Course("C023", "Final Course", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A023", "Assignment 1", deadline, 100, course);
        Student student = new Student("S024", "Charlotte Ruby", "charlotte@example.com");
        Grade tempGrade = new Grade("TEMP030", 0.0, "");
        
        Submission submission = new Submission("SUB033", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Created 状态尝试开始评分
        boolean started1 = submission.startGrading();
        assertFalse(started1, "Created 状态的提交不应该开始评分");
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 提交后开始检查并失败
        submission.submit();
        submission.startAutoChecks();
        submission.checksFail();
        assertEquals(Submission.Status.Returned, submission.getStatus());
        
        // 从 Returned 状态尝试开始评分
        boolean started2 = submission.startGrading();
        assertFalse(started2, "Returned 状态的提交不应该开始评分");
        assertEquals(Submission.Status.Returned, submission.getStatus());
        
        System.out.println("✅ 非 Submitted 状态的提交评分失败");
    }

    // Task 3.8: grade() 方法测试
    @Test
    public void testGradeFromGrading() {
        Instructor instructor = new Instructor("I024", "Dr. Pearl", "pearl@example.com");
        Course course = new Course("C024", "DevOps Practices", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A024", "Assignment 1", deadline, 100, course);
        Student student = new Student("S025", "Luna Amethyst", "luna@example.com");
        Grade tempGrade = new Grade("TEMP031", 0.0, "");
        
        Submission submission = new Submission("SUB034", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        submission.checksPass();
        submission.startGrading();
        assertEquals(Submission.Status.Grading, submission.getStatus());
        
        // 评分
        boolean graded = submission.grade(85.0, "Good work!");
        assertTrue(graded, "Grading 状态的提交应该可以评分");
        assertEquals(Submission.Status.Graded, submission.getStatus(), "状态应该变为 Graded");
        assertNotNull(submission.getSubmissionGrade(), "Grade 对象应该被创建");
        assertEquals(85.0, submission.getSubmissionGrade().getScore(), 0.001);
        assertEquals("Good work!", submission.getSubmissionGrade().getFeedback());
        
        System.out.println("✅ Grading 状态的提交可以评分");
    }

    @Test
    public void testGradeWithInvalidScore() {
        Instructor instructor = new Instructor("I025", "Dr. Quartz", "quartz@example.com");
        Course course = new Course("C025", "Spring Boot", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A025", "Assignment 1", deadline, 100, course);
        Student student = new Student("S026", "Maya Ruby", "maya@example.com");
        Grade tempGrade = new Grade("TEMP032", 0.0, "");
        
        Submission submission = new Submission("SUB035", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        submission.checksPass();
        submission.startGrading();
        assertEquals(Submission.Status.Grading, submission.getStatus());
        
        // 测试负数分数
        boolean graded1 = submission.grade(-10.0, "Feedback");
        assertFalse(graded1, "负数分数应该失败");
        assertEquals(Submission.Status.Grading, submission.getStatus(), "状态应该保持为 Grading");
        
        // 测试超过 maxScore 的分数
        boolean graded2 = submission.grade(150.0, "Feedback");
        assertFalse(graded2, "超过 maxScore 的分数应该失败");
        assertEquals(Submission.Status.Grading, submission.getStatus(), "状态应该保持为 Grading");
        
        System.out.println("✅ 分数范围验证正常（负数或超过 maxScore 失败）");
    }

    @Test
    public void testGradeCreatesOrUpdatesGrade() {
        Instructor instructor = new Instructor("I026", "Dr. Amethyst", "amethyst@example.com");
        Course course = new Course("C026", "Docker & Kubernetes", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A026", "Assignment 1", deadline, 100, course);
        Student student = new Student("S027", "Nora Diamond", "nora@example.com");
        Grade tempGrade = new Grade("TEMP033", 0.0, "");
        
        Submission submission = new Submission("SUB036", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        submission.checksPass();
        submission.startGrading();
        
        // 第一次评分（创建 Grade）
        boolean graded1 = submission.grade(80.0, "Initial feedback");
        assertTrue(graded1);
        assertNotNull(submission.getSubmissionGrade(), "Grade 对象应该被创建");
        assertEquals(80.0, submission.getSubmissionGrade().getScore(), 0.001);
        assertEquals("Initial feedback", submission.getSubmissionGrade().getFeedback());
        
        // 注意：一旦状态变为 Graded，就不能再次调用 grade() 了
        // 但我们可以测试更新已存在的 Grade（通过直接调用 setter）
        submission.getSubmissionGrade().setScore(90.0);
        submission.getSubmissionGrade().setFeedback("Updated feedback");
        assertEquals(90.0, submission.getSubmissionGrade().getScore(), 0.001);
        assertEquals("Updated feedback", submission.getSubmissionGrade().getFeedback());
        
        System.out.println("✅ Grade 对象正确创建或更新");
    }

    @Test
    public void testGradeFromNonGradingStatus() {
        Instructor instructor = new Instructor("I027", "Dr. Rose", "rose@example.com");
        Course course = new Course("C027", "Microservices Architecture", 25, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A027", "Assignment 1", deadline, 100, course);
        Student student = new Student("S028", "Zoe Sapphire", "zoe@example.com");
        Grade tempGrade = new Grade("TEMP034", 0.0, "");
        
        Submission submission = new Submission("SUB037", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Created 状态尝试评分
        boolean graded1 = submission.grade(85.0, "Feedback");
        assertFalse(graded1, "Created 状态的提交不应该评分");
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Submitted 状态尝试评分
        submission.submit();
        boolean graded2 = submission.grade(85.0, "Feedback");
        assertFalse(graded2, "Submitted 状态的提交不应该直接评分");
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        
        System.out.println("✅ 非 Grading 状态的提交评分失败");
    }

    @Test
    public void testGradeWithValidScoreRange() {
        Instructor instructor = new Instructor("I028", "Dr. Amber", "amber@example.com");
        Course course = new Course("C028", "Cloud Computing", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A028", "Assignment 1", deadline, 100, course);
        Student student = new Student("S029", "Aria Topaz", "aria@example.com");
        Grade tempGrade = new Grade("TEMP035", 0.0, "");
        
        Submission submission = new Submission("SUB038", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        submission.checksPass();
        submission.startGrading();
        
        // 测试边界值
        boolean graded1 = submission.grade(0.0, "Minimum score");
        assertTrue(graded1, "score = 0 应该可以评分");
        assertEquals(0.0, submission.getSubmissionGrade().getScore(), 0.001);
        
        // 创建新的 submission 测试最大值
        Grade tempGrade2 = new Grade("TEMP036", 0.0, "");
        Submission submission2 = new Submission("SUB039", null, 0, false, tempGrade2, student, assignment);
        submission2.submit();
        submission2.startAutoChecks();
        submission2.checksPass();
        submission2.startGrading();
        
        boolean graded2 = submission2.grade(100.0, "Maximum score");
        assertTrue(graded2, "score = maxScore 应该可以评分");
        assertEquals(100.0, submission2.getSubmissionGrade().getScore(), 0.001);
        
        System.out.println("✅ 有效分数范围验证通过");
    }

    // Task 3.9: requestResubmission() 方法测试
    @Test
    public void testRequestResubmissionFromGraded() {
        Instructor instructor = new Instructor("I029", "Dr. Jade", "jade@example.com");
        Course course = new Course("C029", "DevOps Practices", 50, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000 * 7); // 一周后截止
        Assignment assignment = new Assignment("A029", "Assignment 1", deadline, 100, course);
        Student student = new Student("S030", "Elena Opal", "elena@example.com");
        Grade tempGrade = new Grade("TEMP037", 0.0, "");
        
        Submission submission = new Submission("SUB040", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        submission.checksPass();
        submission.startGrading();
        submission.grade(75.0, "Good work");
        assertEquals(Submission.Status.Graded, submission.getStatus());
        
        // 要求重交
        boolean requested = submission.requestResubmission();
        assertTrue(requested, "Graded 状态的提交应该可以要求重交");
        assertEquals(Submission.Status.ResubmissionRequested, submission.getStatus(), "状态应该变为 ResubmissionRequested");
        
        System.out.println("✅ Graded 状态的提交可以要求重交");
    }

    @Test
    public void testRequestResubmissionFromReturned() {
        Instructor instructor = new Instructor("I030", "Dr. Emerald", "emerald@example.com");
        Course course = new Course("C030", "Cloud Computing", 40, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000 * 7);
        Assignment assignment = new Assignment("A030", "Assignment 1", deadline, 100, course);
        Student student = new Student("S031", "Grace Topaz", "grace@example.com");
        Grade tempGrade = new Grade("TEMP038", 0.0, "");
        
        Submission submission = new Submission("SUB041", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        submission.checksFail(); // 检查失败
        assertEquals(Submission.Status.Returned, submission.getStatus());
        
        // 要求重交
        boolean requested = submission.requestResubmission();
        assertTrue(requested, "Returned 状态的提交应该可以要求重交");
        assertEquals(Submission.Status.ResubmissionRequested, submission.getStatus(), "状态应该变为 ResubmissionRequested");
        
        System.out.println("✅ Returned 状态的提交可以要求重交");
    }

    @Test
    public void testRequestResubmissionAfterDeadline() {
        Instructor instructor = new Instructor("I031", "Dr. Sapphire", "sapphire@example.com");
        Course course = new Course("C031", "Kubernetes Advanced", 30, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000); // 先设置为未来时间
        Assignment assignment = new Assignment("A031", "Assignment 1", deadline, 100, course);
        Student student = new Student("S032", "Hannah Ruby", "hannah@example.com");
        Grade tempGrade = new Grade("TEMP039", 0.0, "");
        
        Submission submission = new Submission("SUB042", null, 0, false, tempGrade, student, assignment);
        submission.submit();
        submission.startAutoChecks();
        submission.checksPass();
        submission.startGrading();
        submission.grade(80.0, "Good");
        assertEquals(Submission.Status.Graded, submission.getStatus());
        
        // 手动将 deadline 设置为过去的时间（模拟已过期）
        Date pastDeadline = new Date(System.currentTimeMillis() - 86400000);
        assignment.setDeadline(pastDeadline);
        
        // 尝试要求重交（已过期）
        boolean requested = submission.requestResubmission();
        assertFalse(requested, "超过截止时间的提交重交应该失败");
        assertEquals(Submission.Status.Graded, submission.getStatus(), "状态应该保持为 Graded");
        
        System.out.println("✅ 超过截止时间的提交重交失败");
    }

    @Test
    public void testRequestResubmissionFromInvalidStatus() {
        Instructor instructor = new Instructor("I032", "Dr. Diamond", "diamond@example.com");
        Course course = new Course("C032", "Final Course", 25, instructor);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A032", "Assignment 1", deadline, 100, course);
        Student student = new Student("S033", "Ivy Pearl", "ivy@example.com");
        Grade tempGrade = new Grade("TEMP040", 0.0, "");
        
        Submission submission = new Submission("SUB043", null, 0, false, tempGrade, student, assignment);
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Created 状态尝试要求重交
        boolean requested1 = submission.requestResubmission();
        assertFalse(requested1, "Created 状态的提交不应该要求重交");
        assertEquals(Submission.Status.Created, submission.getStatus());
        
        // 从 Submitted 状态尝试要求重交
        submission.submit();
        boolean requested2 = submission.requestResubmission();
        assertFalse(requested2, "Submitted 状态的提交不应该要求重交");
        assertEquals(Submission.Status.Submitted, submission.getStatus());
        
        System.out.println("✅ 非 Graded/Returned 状态的提交不能要求重交");
    }
}

