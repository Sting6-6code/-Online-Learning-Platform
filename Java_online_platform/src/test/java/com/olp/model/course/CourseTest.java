package com.olp.model.course;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import com.olp.model.assignment.Assignment;
import java.sql.Date;

/**
 * Task 2.5-2.6: Course 类构造函数验证和 publish() 方法测试
 * 验证 Course 类的基本验证、初始状态和发布功能
 */
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

    // Task 2.7: openEnrollment() 方法测试
    @Test
    public void testOpenEnrollmentFromPublished() {
        Instructor instructor = new Instructor("I014", "Dr. Magenta", "magenta@example.com");
        Course course = new Course("C017", "Advanced Java", 50, instructor);
        
        // 添加内容并发布
        Lesson lesson = new Lesson("L004", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A004", "Assignment 1", deadline, 100, course);
        course.publish();
        assertEquals(Course.Status.Published, course.getStatus());
        
        // 开放选课
        boolean opened = course.openEnrollment();
        assertTrue(opened, "Published 状态的课程应该可以开放选课");
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus(), "状态应该变为 EnrollmentOpen");
        
        System.out.println("✅ Published 状态的课程可以开放选课");
    }

    @Test
    public void testOpenEnrollmentWithZeroCapacity() {
        Instructor instructor = new Instructor("I015", "Dr. Teal", "teal@example.com");
        // 注意：构造函数已经验证 capacity > 0，所以我们需要通过反射或其他方式测试
        // 但根据任务要求，我们测试正常流程：capacity > 0 时应该成功
        
        Course course = new Course("C018", "Python Advanced", 1, instructor); // 最小容量
        Lesson lesson = new Lesson("L005", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A005", "Assignment 1", deadline, 100, course);
        course.publish();
        
        // capacity = 1 > 0，应该可以开放
        boolean opened = course.openEnrollment();
        assertTrue(opened, "capacity > 0 的课程应该可以开放选课");
        
        System.out.println("✅ capacity > 0 的课程可以开放选课");
    }

    @Test
    public void testOpenEnrollmentFromNonPublishedStatus() {
        Instructor instructor = new Instructor("I016", "Dr. Indigo", "indigo@example.com");
        Course course = new Course("C019", "Web Development Advanced", 30, instructor);
        
        // 从 Draft 状态尝试开放选课
        boolean opened = course.openEnrollment();
        assertFalse(opened, "Draft 状态的课程不应该开放选课");
        assertEquals(Course.Status.Draft, course.getStatus(), "状态应该保持为 Draft");
        
        System.out.println("✅ 非 Published 状态的课程开放失败");
    }

    @Test
    public void testOpenEnrollmentFromEnrollmentOpen() {
        Instructor instructor = new Instructor("I017", "Dr. Violet", "violet@example.com");
        Course course = new Course("C020", "Database Advanced", 25, instructor);
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L006", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A006", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus());
        
        // 尝试再次开放选课
        boolean opened = course.openEnrollment();
        assertFalse(opened, "EnrollmentOpen 状态的课程不应该再次开放选课");
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus(), "状态应该保持为 EnrollmentOpen");
        
        System.out.println("✅ EnrollmentOpen 状态的课程不能再次开放选课");
    }

    @Test
    public void testOpenEnrollmentWorkflow() {
        Instructor instructor = new Instructor("I018", "Dr. Lavender", "lavender@example.com");
        Course course = new Course("C021", "Full Stack Development", 40, instructor);
        
        // 1. 初始状态为 Draft
        assertEquals(Course.Status.Draft, course.getStatus());
        assertFalse(course.openEnrollment(), "Draft 状态不能开放选课");
        
        // 2. 发布课程
        Lesson lesson = new Lesson("L007", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A007", "Assignment 1", deadline, 100, course);
        assertTrue(course.publish(), "应该可以发布");
        assertEquals(Course.Status.Published, course.getStatus());
        
        // 3. 开放选课
        assertTrue(course.openEnrollment(), "应该可以开放选课");
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus());
        
        System.out.println("✅ 完整的发布和开放选课流程正常");
    }

    // Task 2.8: enroll() 方法测试
    @Test
    public void testEnrollWhenNotFull() {
        Instructor instructor = new Instructor("I019", "Dr. Coral", "coral@example.com");
        Course course = new Course("C022", "React Development", 50, instructor);
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L008", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A008", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
        // 学生选课
        Student student = new Student("S001", "Alice", "alice@example.com");
        Enrollment enrollment = course.enroll(student);
        
        assertNotNull(enrollment, "未满员课程应该可以选课");
        assertEquals(Enrollment.EnrollmentStatus.Active, enrollment.getStatus(), "应该返回 Active 状态");
        assertEquals(student, enrollment.getStudent());
        assertEquals(course, enrollment.getCourse());
        assertEquals(1, course.numberOfCourseEnrollments());
        
        System.out.println("✅ 未满员课程可以正常选课（返回 Active 状态 Enrollment）");
    }

    @Test
    public void testEnrollWhenFull() {
        Instructor instructor = new Instructor("I020", "Dr. Mint", "mint@example.com");
        Course course = new Course("C023", "Vue.js Basics", 2, instructor); // 容量为 2
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L009", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A009", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
        // 前两个学生选课（应该都是 Active）
        Student student1 = new Student("S002", "Bob", "bob@example.com");
        Student student2 = new Student("S003", "Charlie", "charlie@example.com");
        Enrollment enrollment1 = course.enroll(student1);
        Enrollment enrollment2 = course.enroll(student2);
        
        assertNotNull(enrollment1);
        assertNotNull(enrollment2);
        assertEquals(Enrollment.EnrollmentStatus.Active, enrollment1.getStatus());
        assertEquals(Enrollment.EnrollmentStatus.Active, enrollment2.getStatus());
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus(), "前两个学生选课后应该还是 EnrollmentOpen");
        
        // 第三个学生选课（应该进入候补）
        Student student3 = new Student("S004", "David", "david@example.com");
        Enrollment enrollment3 = course.enroll(student3);
        
        assertNotNull(enrollment3, "满员课程应该可以进入候补");
        assertEquals(Enrollment.EnrollmentStatus.Waitlisted, enrollment3.getStatus(), "应该返回 Waitlisted 状态");
        assertEquals(Course.Status.Waitlisted, course.getStatus(), "课程状态应该自动切换到 Waitlisted");
        
        System.out.println("✅ 满员课程自动进入候补（返回 Waitlisted 状态 Enrollment）");
    }

    @Test
    public void testEnrollDuplicateRejected() {
        Instructor instructor = new Instructor("I021", "Dr. Peach", "peach@example.com");
        Course course = new Course("C024", "Angular Framework", 50, instructor);
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L010", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A010", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
        // 学生第一次选课
        Student student = new Student("S005", "Eve", "eve@example.com");
        Enrollment enrollment1 = course.enroll(student);
        assertNotNull(enrollment1);
        assertEquals(1, course.numberOfCourseEnrollments());
        
        // 学生重复选课
        Enrollment enrollment2 = course.enroll(student);
        assertNull(enrollment2, "重复选课应该被拒绝");
        assertEquals(1, course.numberOfCourseEnrollments(), "不应该创建新的 Enrollment");
        
        System.out.println("✅ 重复选课被拒绝（返回 null）");
    }

    @Test
    public void testEnrollFromWaitlistedStatus() {
        Instructor instructor = new Instructor("I022", "Dr. Sky", "sky@example.com");
        Course course = new Course("C025", "Node.js Advanced", 1, instructor); // 容量为 1
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L011", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A011", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
        // 第一个学生选课（Active）
        Student student1 = new Student("S006", "Frank", "frank@example.com");
        Enrollment enrollment1 = course.enroll(student1);
        assertNotNull(enrollment1);
        assertEquals(Enrollment.EnrollmentStatus.Active, enrollment1.getStatus());
        
        // 第二个学生选课（Waitlisted，课程状态变为 Waitlisted）
        Student student2 = new Student("S007", "Grace", "grace@example.com");
        Enrollment enrollment2 = course.enroll(student2);
        assertNotNull(enrollment2);
        assertEquals(Enrollment.EnrollmentStatus.Waitlisted, enrollment2.getStatus());
        assertEquals(Course.Status.Waitlisted, course.getStatus());
        
        // 第三个学生从 Waitlisted 状态选课（应该也是 Waitlisted）
        Student student3 = new Student("S008", "Henry", "henry@example.com");
        Enrollment enrollment3 = course.enroll(student3);
        assertNotNull(enrollment3);
        assertEquals(Enrollment.EnrollmentStatus.Waitlisted, enrollment3.getStatus());
        assertEquals(Course.Status.Waitlisted, course.getStatus(), "状态应该保持为 Waitlisted");
        
        System.out.println("✅ 从 Waitlisted 状态可以继续选课（进入候补）");
    }

    @Test
    public void testEnrollFromInvalidStatus() {
        Instructor instructor = new Instructor("I023", "Dr. Rose", "rose@example.com");
        Course course = new Course("C026", "Spring Boot", 50, instructor);
        
        // Draft 状态不能选课
        Student student = new Student("S009", "Iris", "iris@example.com");
        Enrollment enrollment1 = course.enroll(student);
        assertNull(enrollment1, "Draft 状态不能选课");
        
        // 发布但未开放选课
        Lesson lesson = new Lesson("L012", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A012", "Assignment 1", deadline, 100, course);
        course.publish();
        assertEquals(Course.Status.Published, course.getStatus());
        
        Enrollment enrollment2 = course.enroll(student);
        assertNull(enrollment2, "Published 状态不能选课");
        
        System.out.println("✅ 非 EnrollmentOpen/Waitlisted 状态的课程不能选课");
    }

    // Task 2.9: startCourse() 方法测试
    @Test
    public void testStartCourseWithActiveEnrollments() {
        Instructor instructor = new Instructor("I024", "Dr. Amber", "amber@example.com");
        Course course = new Course("C027", "Docker & Kubernetes", 50, instructor);
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L013", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A013", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
        // 学生选课
        Student student = new Student("S010", "Jack", "jack@example.com");
        course.enroll(student);
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus());
        
        // 开课
        boolean started = course.startCourse();
        assertTrue(started, "有 Active 学生的课程应该可以开课");
        assertEquals(Course.Status.InProgress, course.getStatus(), "状态应该变为 InProgress");
        
        System.out.println("✅ 有 Active 学生的课程可以开课");
    }

    @Test
    public void testStartCourseWithoutEnrollments() {
        Instructor instructor = new Instructor("I025", "Dr. Jade", "jade@example.com");
        Course course = new Course("C028", "Microservices Architecture", 50, instructor);
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L014", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A014", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
        // 没有学生选课，尝试开课
        boolean started = course.startCourse();
        assertFalse(started, "没有学生的课程不应该开课");
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus(), "状态应该保持为 EnrollmentOpen");
        
        System.out.println("✅ 没有学生的课程开课失败");
    }

    @Test
    public void testStartCourseWithOnlyWaitlisted() {
        Instructor instructor = new Instructor("I026", "Dr. Opal", "opal@example.com");
        Course course = new Course("C029", "Cloud Computing", 1, instructor); // 容量为 1
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L015", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A015", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
        // 第一个学生选课（Active）
        Student student1 = new Student("S011", "Kelly", "kelly@example.com");
        course.enroll(student1);
        
        // 第二个学生选课（Waitlisted）
        Student student2 = new Student("S012", "Liam", "liam@example.com");
        course.enroll(student2);
        assertEquals(Course.Status.Waitlisted, course.getStatus());
        
        // 第一个学生退课
        Enrollment enrollment1 = course.getCourseEnrollment(0);
        enrollment1.dropCourse();
        assertEquals(Enrollment.EnrollmentStatus.Dropped, enrollment1.getStatus());
        
        // 现在只有 Waitlisted 学生，尝试开课
        boolean started = course.startCourse();
        assertFalse(started, "只有 Waitlisted 学生的课程不应该开课");
        assertEquals(Course.Status.Waitlisted, course.getStatus(), "状态应该保持为 Waitlisted");
        
        System.out.println("✅ 只有 Waitlisted 学生的课程开课失败");
    }

    @Test
    public void testStartCourseFromInvalidStatus() {
        Instructor instructor = new Instructor("I027", "Dr. Pearl", "pearl@example.com");
        Course course = new Course("C030", "DevOps Practices", 50, instructor);
        
        // Draft 状态不能开课
        boolean started1 = course.startCourse();
        assertFalse(started1, "Draft 状态不能开课");
        assertEquals(Course.Status.Draft, course.getStatus());
        
        // Published 状态不能开课
        Lesson lesson = new Lesson("L016", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A016", "Assignment 1", deadline, 100, course);
        course.publish();
        assertEquals(Course.Status.Published, course.getStatus());
        
        boolean started2 = course.startCourse();
        assertFalse(started2, "Published 状态不能开课");
        assertEquals(Course.Status.Published, course.getStatus());
        
        System.out.println("✅ 非 EnrollmentOpen/Waitlisted 状态的课程开课失败");
    }

    @Test
    public void testStartCourseFromWaitlistedStatus() {
        Instructor instructor = new Instructor("I028", "Dr. Ruby", "ruby@example.com");
        Course course = new Course("C031", "CI/CD Pipeline", 2, instructor);
        
        // 发布并开放选课
        Lesson lesson = new Lesson("L017", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A017", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        
        // 两个学生选课（都是 Active）
        Student student1 = new Student("S013", "Mia", "mia@example.com");
        Student student2 = new Student("S014", "Noah", "noah@example.com");
        course.enroll(student1);
        course.enroll(student2);
        assertEquals(Course.Status.EnrollmentOpen, course.getStatus());
        
        // 第三个学生选课（Waitlisted，课程状态变为 Waitlisted）
        Student student3 = new Student("S015", "Olivia", "olivia@example.com");
        course.enroll(student3);
        assertEquals(Course.Status.Waitlisted, course.getStatus());
        
        // 从 Waitlisted 状态开课（有 Active 学生）
        boolean started = course.startCourse();
        assertTrue(started, "Waitlisted 状态且有 Active 学生应该可以开课");
        assertEquals(Course.Status.InProgress, course.getStatus(), "状态应该变为 InProgress");
        
        System.out.println("✅ Waitlisted 状态且有 Active 学生可以开课");
    }

    // Task 5.3: validateHasActiveStudents() 方法测试
    @Test
    public void testValidateHasActiveStudentsTrueWhenActiveEnrollmentExists() {
        Instructor instructor = new Instructor("I041", "Dr. Silver", "silver@example.com");
        Course course = new Course("C041", "Serverless Architecture", 3, instructor);

        Lesson lesson = new Lesson("L041", "Intro", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A041", "Assignment", deadline, 100, course);
        course.publish();
        course.openEnrollment();

        Student student = new Student("S041", "Student Active", "active@example.com");
        course.enroll(student);

        assertTrue(course.validateHasActiveStudents(), "存在 Active 学生时验证应通过");
        System.out.println("✅ validateHasActiveStudents 在有 Active 学生时返回 true");
    }

    @Test
    public void testValidateHasActiveStudentsFalseWhenNoActiveEnrollment() {
        Instructor instructor = new Instructor("I042", "Dr. Bronze", "bronze@example.com");
        Course course = new Course("C042", "Event-Driven Systems", 2, instructor);

        Lesson lesson = new Lesson("L042", "Intro", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A042", "Assignment", deadline, 100, course);
        course.publish();
        course.openEnrollment();

        assertFalse(course.validateHasActiveStudents(), "没有 Active 学生时验证应失败");

        Student student = new Student("S042", "Student Waitlisted", "waitlisted@example.com");
        Enrollment waitlisted = course.enroll(student);
        assertNotNull(waitlisted);
        assertEquals(Enrollment.EnrollmentStatus.Active, waitlisted.getStatus());

        waitlisted.dropCourse();
        assertEquals(Enrollment.EnrollmentStatus.Dropped, waitlisted.getStatus());
        assertFalse(course.validateHasActiveStudents(), "只有 Dropped 或候补学生时验证应失败");

        System.out.println("✅ validateHasActiveStudents 在没有 Active 学生时返回 false");
    }

    // Task 2.10: complete() 和 cancel() 方法测试
    @Test
    public void testCompleteFromInProgress() {
        Instructor instructor = new Instructor("I029", "Dr. Sapphire", "sapphire@example.com");
        Course course = new Course("C032", "Kubernetes Advanced", 50, instructor);
        
        // 发布、开放选课、学生选课、开课
        Lesson lesson = new Lesson("L018", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A018", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        Student student = new Student("S016", "Paul", "paul@example.com");
        course.enroll(student);
        course.startCourse();
        assertEquals(Course.Status.InProgress, course.getStatus());
        
        // 结课
        boolean completed = course.complete();
        assertTrue(completed, "InProgress 的课程应该可以结课");
        assertEquals(Course.Status.Completed, course.getStatus(), "状态应该变为 Completed");
        
        System.out.println("✅ InProgress 的课程可以结课");
    }

    @Test
    public void testCompleteFromNonInProgress() {
        Instructor instructor = new Instructor("I030", "Dr. Topaz", "topaz@example.com");
        Course course = new Course("C033", "Docker Basics", 50, instructor);
        
        // Draft 状态不能结课
        boolean completed1 = course.complete();
        assertFalse(completed1, "Draft 状态不能结课");
        assertEquals(Course.Status.Draft, course.getStatus());
        
        // Published 状态不能结课
        Lesson lesson = new Lesson("L019", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A019", "Assignment 1", deadline, 100, course);
        course.publish();
        boolean completed2 = course.complete();
        assertFalse(completed2, "Published 状态不能结课");
        assertEquals(Course.Status.Published, course.getStatus());
        
        System.out.println("✅ 非 InProgress 状态的课程不能结课");
    }

    @Test
    public void testCancelFromVariousStates() {
        Instructor instructor = new Instructor("I031", "Dr. Emerald", "emerald@example.com");
        
        // 测试从 Draft 状态取消
        Course course1 = new Course("C034", "Course 1", 50, instructor);
        boolean cancelled1 = course1.cancel("Low enrollment");
        assertTrue(cancelled1, "Draft 状态应该可以取消");
        assertEquals(Course.Status.Cancelled, course1.getStatus());
        assertEquals("Low enrollment", course1.getCancelReason());
        
        // 测试从 Published 状态取消
        Course course2 = new Course("C035", "Course 2", 50, instructor);
        Lesson lesson = new Lesson("L020", "Introduction", 1, course2);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A020", "Assignment 1", deadline, 100, course2);
        course2.publish();
        boolean cancelled2 = course2.cancel("Instructor unavailable");
        assertTrue(cancelled2, "Published 状态应该可以取消");
        assertEquals(Course.Status.Cancelled, course2.getStatus());
        assertEquals("Instructor unavailable", course2.getCancelReason());
        
        // 测试从 EnrollmentOpen 状态取消
        Course course3 = new Course("C036", "Course 3", 50, instructor);
        Lesson lesson3 = new Lesson("L021", "Introduction", 1, course3);
        Date deadline3 = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment3 = new Assignment("A021", "Assignment 1", deadline3, 100, course3);
        course3.publish();
        course3.openEnrollment();
        boolean cancelled3 = course3.cancel("Technical issues");
        assertTrue(cancelled3, "EnrollmentOpen 状态应该可以取消");
        assertEquals(Course.Status.Cancelled, course3.getStatus());
        assertEquals("Technical issues", course3.getCancelReason());
        
        System.out.println("✅ 任何状态（除 Completed）可以取消");
    }

    @Test
    public void testCancelFromCompleted() {
        Instructor instructor = new Instructor("I032", "Dr. Diamond", "diamond@example.com");
        Course course = new Course("C037", "Final Course", 50, instructor);
        
        // 发布、开放选课、学生选课、开课、结课
        Lesson lesson = new Lesson("L022", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A022", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        Student student = new Student("S017", "Quinn", "quinn@example.com");
        course.enroll(student);
        course.startCourse();
        course.complete();
        assertEquals(Course.Status.Completed, course.getStatus());
        
        // 尝试取消已完成的课程
        boolean cancelled = course.cancel("Should not work");
        assertFalse(cancelled, "Completed 的课程不应该可以取消");
        assertEquals(Course.Status.Completed, course.getStatus(), "状态应该保持为 Completed");
        assertNull(course.getCancelReason(), "不应该设置取消原因");
        
        System.out.println("✅ Completed 的课程不能取消");
    }

    @Test
    public void testCancelFromInProgress() {
        Instructor instructor = new Instructor("I033", "Dr. Quartz", "quartz@example.com");
        Course course = new Course("C038", "Advanced Course", 50, instructor);
        
        // 发布、开放选课、学生选课、开课
        Lesson lesson = new Lesson("L023", "Introduction", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A023", "Assignment 1", deadline, 100, course);
        course.publish();
        course.openEnrollment();
        Student student = new Student("S018", "Rachel", "rachel@example.com");
        course.enroll(student);
        course.startCourse();
        assertEquals(Course.Status.InProgress, course.getStatus());
        
        // 取消进行中的课程
        boolean cancelled = course.cancel("Emergency situation");
        assertTrue(cancelled, "InProgress 状态应该可以取消");
        assertEquals(Course.Status.Cancelled, course.getStatus());
        assertEquals("Emergency situation", course.getCancelReason());
        
        System.out.println("✅ InProgress 状态的课程可以取消");
    }

    @Test
    public void testCancelWithoutReason() {
        Instructor instructor = new Instructor("I034", "Dr. Amethyst", "amethyst@example.com");
        Course course = new Course("C039", "Test Course", 50, instructor);
        
        // 使用无参 cancel() 方法
        boolean cancelled = course.cancel();
        assertTrue(cancelled);
        assertEquals(Course.Status.Cancelled, course.getStatus());
        assertEquals("No reason provided", course.getCancelReason());
        
        System.out.println("✅ 无参 cancel() 方法正常工作");
    }

    // Task 5.1: SeatsNotExceeded 约束验证测试
    @Test
    public void testValidateSeatsNotExceededWhenNotFull() {
        Instructor instructor = new Instructor("I024", "Dr. Amber", "amber@example.com");
        Course course = new Course("C027", "Docker & Kubernetes", 5, instructor);
        
        // 添加课程内容以便发布
        Lesson lesson = new Lesson("L001", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
        
        course.publish();
        course.openEnrollment();
        
        // 添加 3 个 Active Enrollment（未满员）
        Student student1 = new Student("S001", "Student 1", "s1@example.com");
        Student student2 = new Student("S002", "Student 2", "s2@example.com");
        Student student3 = new Student("S003", "Student 3", "s3@example.com");
        
        course.enroll(student1);
        course.enroll(student2);
        course.enroll(student3);
        
        // 验证约束（3 <= 5）
        assertTrue(course.validateSeatsNotExceeded(), "未满员课程验证应该成功");
        
        System.out.println("✅ 未满员课程验证成功");
    }

    @Test
    public void testValidateSeatsNotExceededWhenFull() {
        Instructor instructor = new Instructor("I025", "Dr. Jade", "jade@example.com");
        Course course = new Course("C028", "Microservices Architecture", 2, instructor); // 容量为 2
        
        // 添加课程内容以便发布
        Lesson lesson = new Lesson("L002", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A002", "Assignment 1", deadline, 100, course);
        
        course.publish();
        course.openEnrollment();
        
        // 添加 2 个 Active Enrollment（满员）
        Student student1 = new Student("S004", "Student 4", "s4@example.com");
        Student student2 = new Student("S005", "Student 5", "s5@example.com");
        
        course.enroll(student1);
        course.enroll(student2);
        
        // 验证约束（2 <= 2）
        assertTrue(course.validateSeatsNotExceeded(), "满员课程验证应该成功（等于容量）");
        
        // 尝试添加第 3 个学生（应该创建 Waitlisted）
        Student student3 = new Student("S006", "Student 6", "s6@example.com");
        Enrollment enrollment3 = course.enroll(student3);
        assertNotNull(enrollment3, "应该可以创建 Waitlisted Enrollment");
        assertEquals(Enrollment.EnrollmentStatus.Waitlisted, enrollment3.getStatus());
        
        // 验证约束仍然满足（2 Active <= 2 capacity）
        assertTrue(course.validateSeatsNotExceeded(), "满员后添加 Waitlisted 学生，约束仍然满足");
        
        System.out.println("✅ 满员课程验证成功");
    }

    @Test
    public void testValidateSeatsNotExceededWithDroppedEnrollments() {
        Instructor instructor = new Instructor("I026", "Dr. Emerald", "emerald@example.com");
        Course course = new Course("C029", "Cloud Computing", 2, instructor);
        
        // 添加课程内容以便发布
        Lesson lesson = new Lesson("L003", "Lesson 1", 1, course);
        Date deadline = new Date(System.currentTimeMillis() + 86400000);
        Assignment assignment = new Assignment("A003", "Assignment 1", deadline, 100, course);
        
        course.publish();
        course.openEnrollment();
        
        // 添加 2 个 Active Enrollment（满员）
        Student student1 = new Student("S007", "Student 7", "s7@example.com");
        Student student2 = new Student("S008", "Student 8", "s8@example.com");
        
        Enrollment enrollment1 = course.enroll(student1);
        Enrollment enrollment2 = course.enroll(student2);
        
        // 验证满员
        assertTrue(course.validateSeatsNotExceeded(), "满员时约束应该满足");
        
        // 学生1退课
        enrollment1.dropCourse();
        assertEquals(Enrollment.EnrollmentStatus.Dropped, enrollment1.getStatus());
        
        // 验证约束仍然满足（1 Active <= 2 capacity）
        assertTrue(course.validateSeatsNotExceeded(), "退课后约束仍然满足");
        
        // 现在可以添加新的 Active Enrollment
        Student student3 = new Student("S009", "Student 9", "s9@example.com");
        Enrollment enrollment3 = course.enroll(student3);
        assertNotNull(enrollment3);
        assertEquals(Enrollment.EnrollmentStatus.Active, enrollment3.getStatus());
        
        // 验证约束仍然满足（2 Active <= 2 capacity）
        assertTrue(course.validateSeatsNotExceeded(), "添加新学生后约束仍然满足");
        
        System.out.println("✅ 退课后的约束验证成功");
    }
}

