package com.olp.model.course;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.user.Instructor;

/**
 * Task 2.2: VideoContent 类基础功能测试
 * 验证 VideoContent 类的构造函数验证和 Composition 关系
 */
public class VideoContentTest {

    @Test
    public void testCreateVideoContent() {
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course = new Course("C001", "Java Programming", 50, instructor);
        Lesson lesson = new Lesson("L001", "Introduction", 1, course);
        
        VideoContent video = new VideoContent("V001", "https://example.com/video1.mp4", 600, lesson);
        assertNotNull(video);
        assertEquals("V001", video.getId());
        assertEquals("https://example.com/video1.mp4", video.getUrl());
        assertEquals(600, video.getDurationSec());
        assertEquals(lesson, video.getLesson());
        
        System.out.println("✅ 可以创建 VideoContent 对象");
    }

    @Test
    public void testNullUrlThrowsException() {
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course = new Course("C002", "Python Basics", 40, instructor);
        Lesson lesson = new Lesson("L002", "Lesson 1", 1, course);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new VideoContent("V002", null, 300, lesson),
            "null url 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("url cannot be null or empty"));
        System.out.println("✅ null url 验证通过");
    }

    @Test
    public void testEmptyUrlThrowsException() {
        Instructor instructor = new Instructor("I003", "Dr. Brown", "brown@example.com");
        Course course = new Course("C003", "Web Development", 30, instructor);
        Lesson lesson = new Lesson("L003", "Lesson 1", 1, course);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new VideoContent("V003", "", 300, lesson),
            "空 url 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("url cannot be null or empty"));
        System.out.println("✅ 空 url 验证通过");
    }

    @Test
    public void testZeroDurationThrowsException() {
        Instructor instructor = new Instructor("I004", "Dr. White", "white@example.com");
        Course course = new Course("C004", "Database Design", 25, instructor);
        Lesson lesson = new Lesson("L004", "Lesson 1", 1, course);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new VideoContent("V004", "https://example.com/video.mp4", 0, lesson),
            "durationSec = 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("durationSec must be greater than 0"));
        System.out.println("✅ durationSec = 0 验证通过");
    }

    @Test
    public void testNegativeDurationThrowsException() {
        Instructor instructor = new Instructor("I005", "Dr. Black", "black@example.com");
        Course course = new Course("C005", "Math 101", 100, instructor);
        Lesson lesson = new Lesson("L005", "Lesson 1", 1, course);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new VideoContent("V005", "https://example.com/video.mp4", -100, lesson),
            "durationSec < 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("durationSec must be greater than 0"));
        System.out.println("✅ durationSec < 0 验证通过");
    }

    @Test
    public void testNullLessonThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new VideoContent("V006", "https://example.com/video.mp4", 300, null),
            "null lesson 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("lesson cannot be null"));
        System.out.println("✅ null lesson 验证通过");
    }

    @Test
    public void testCompositionWithLesson() {
        Instructor instructor = new Instructor("I006", "Dr. Green", "green@example.com");
        Course course = new Course("C006", "Physics 101", 80, instructor);
        Lesson lesson = new Lesson("L006", "Introduction to Physics", 1, course);
        
        VideoContent video1 = new VideoContent("V007", "https://example.com/video1.mp4", 600, lesson);
        VideoContent video2 = new VideoContent("V008", "https://example.com/video2.mp4", 900, lesson);
        
        // 验证 Composition 关系：VideoContent 通过 Lesson 管理
        assertTrue(lesson.hasLessonVideos());
        assertEquals(2, lesson.numberOfLessonVideos());
        assertEquals(video1, lesson.getLessonVideo(0));
        assertEquals(video2, lesson.getLessonVideo(1));
        
        // 验证双向关联
        assertEquals(lesson, video1.getLesson());
        assertEquals(lesson, video2.getLesson());
        
        System.out.println("✅ Composition 关系（Lesson-VideoContent）正常工作");
    }

    @Test
    public void testValidDurations() {
        Instructor instructor = new Instructor("I007", "Dr. Blue", "blue@example.com");
        Course course = new Course("C007", "Chemistry 101", 90, instructor);
        Lesson lesson = new Lesson("L007", "Lesson 1", 1, course);
        
        // 测试各种有效的时长
        VideoContent video1 = new VideoContent("V009", "https://example.com/v1.mp4", 1, lesson);
        assertEquals(1, video1.getDurationSec());
        
        VideoContent video2 = new VideoContent("V010", "https://example.com/v2.mp4", 60, lesson);
        assertEquals(60, video2.getDurationSec());
        
        VideoContent video3 = new VideoContent("V011", "https://example.com/v3.mp4", 3600, lesson);
        assertEquals(3600, video3.getDurationSec());
        
        System.out.println("✅ 有效时长验证通过");
    }
}

