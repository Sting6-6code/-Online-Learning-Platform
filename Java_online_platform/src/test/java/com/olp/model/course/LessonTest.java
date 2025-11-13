package com.olp.model.course;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.user.Instructor;

/**
 * Task 2.3: Lesson 类基础功能测试
 * 验证 Lesson 类的构造函数验证和便捷方法
 */
public class LessonTest {

    @Test
    public void testCreateLesson() {
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        
        Lesson lesson = new Lesson("L001", "Introduction to Java", 1, course);
        assertNotNull(lesson);
        assertEquals("L001", lesson.getId());
        assertEquals("Introduction to Java", lesson.getTitle());
        assertEquals(1, lesson.getOrderIndex());
        assertEquals(course, lesson.getCourse());
        
        System.out.println("✅ 可以创建 Lesson 对象");
    }

    @Test
    public void testNullTitleThrowsException() {
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course = new Course("C002", "Python Basics", 40, instructor);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Lesson("L002", null, 1, course),
            "null title 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("title cannot be null or empty"));
        System.out.println("✅ null title 验证通过");
    }

    @Test
    public void testEmptyTitleThrowsException() {
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        Course course = new Course("C003", "Web Development", 30, instructor);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Lesson("L003", "", 1, course),
            "空 title 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("title cannot be null or empty"));
        System.out.println("✅ 空 title 验证通过");
    }

    @Test
    public void testNegativeOrderIndexThrowsException() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Lesson("L004", "Lesson 1", -1, course),
            "orderIndex < 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("orderIndex must be >= 0"));
        System.out.println("✅ orderIndex < 0 验证通过");
    }

    @Test
    public void testZeroOrderIndex() {
        Instructor instructor = new Instructor("I005", "Dr. Black", "black@example.com");
        Course course = new Course("C005", "Math 101", 100, instructor);
        
        Lesson lesson = new Lesson("L005", "Lesson 0", 0, course);
        assertEquals(0, lesson.getOrderIndex());
        
        System.out.println("✅ orderIndex = 0 验证通过");
    }

    @Test
    public void testNullCourseThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Lesson("L006", "Lesson 1", 1, null),
            "null course 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("course cannot be null"));
        System.out.println("✅ null course 验证通过");
    }

    @Test
    public void testAddVideoContent() {
        Instructor instructor = new Instructor("I006", "Dr. Green", "green@example.com");
        Course course = new Course("C006", "Physics 101", 80, instructor);
        Lesson lesson = new Lesson("L007", "Introduction to Physics", 1, course);
        
        VideoContent video1 = lesson.addLessonVideo("V001", "https://example.com/v1.mp4", 600);
        VideoContent video2 = lesson.addLessonVideo("V002", "https://example.com/v2.mp4", 900);
        
        assertTrue(lesson.hasLessonVideos());
        assertEquals(2, lesson.numberOfLessonVideos());
        assertEquals(video1, lesson.getLessonVideo(0));
        assertEquals(video2, lesson.getLessonVideo(1));
        
        System.out.println("✅ 可以添加 VideoContent");
    }

    @Test
    public void testGetTotalDuration() {
        Instructor instructor = new Instructor("I007", "Dr. Blue", "blue@example.com");
        Course course = new Course("C007", "Chemistry 101", 90, instructor);
        Lesson lesson = new Lesson("L008", "Introduction to Chemistry", 1, course);
        
        // 初始状态：没有视频
        assertEquals(0, lesson.getTotalDuration(), "没有视频时总时长应该为 0");
        
        // 添加视频
        lesson.addLessonVideo("V003", "https://example.com/v3.mp4", 300);
        assertEquals(300, lesson.getTotalDuration(), "添加一个视频后总时长应该为 300");
        
        lesson.addLessonVideo("V004", "https://example.com/v4.mp4", 600);
        assertEquals(900, lesson.getTotalDuration(), "添加两个视频后总时长应该为 900");
        
        lesson.addLessonVideo("V005", "https://example.com/v5.mp4", 450);
        assertEquals(1350, lesson.getTotalDuration(), "添加三个视频后总时长应该为 1350");
        
        System.out.println("✅ getTotalDuration() 计算正确");
    }

    @Test
    public void testGetTotalDurationWithMultipleVideos() {
        Instructor instructor = new Instructor("I008", "Dr. Red", "red@example.com");
        Course course = new Course("C008", "Biology 101", 85, instructor);
        Lesson lesson = new Lesson("L009", "Cell Biology", 1, course);
        
        // 添加多个视频
        lesson.addLessonVideo("V006", "https://example.com/v6.mp4", 1200);
        lesson.addLessonVideo("V007", "https://example.com/v7.mp4", 1800);
        lesson.addLessonVideo("V008", "https://example.com/v8.mp4", 900);
        lesson.addLessonVideo("V009", "https://example.com/v9.mp4", 600);
        
        int total = lesson.getTotalDuration();
        assertEquals(4500, total, "总时长应该是 4500 秒（75 分钟）");
        
        System.out.println("✅ 多个视频的总时长计算正确: " + total + " 秒");
    }

    @Test
    public void testOrderIndexValidation() {
        Instructor instructor = new Instructor("I009", "Dr. Yellow", "yellow@example.com");
        Course course = new Course("C009", "History 101", 70, instructor);
        
        // 测试有效的 orderIndex 值
        Lesson lesson1 = new Lesson("L010", "Lesson 1", 0, course);
        assertEquals(0, lesson1.getOrderIndex());
        
        Lesson lesson2 = new Lesson("L011", "Lesson 2", 1, course);
        assertEquals(1, lesson2.getOrderIndex());
        
        Lesson lesson3 = new Lesson("L012", "Lesson 3", 100, course);
        assertEquals(100, lesson3.getOrderIndex());
        
        System.out.println("✅ orderIndex 验证正常");
    }
}

