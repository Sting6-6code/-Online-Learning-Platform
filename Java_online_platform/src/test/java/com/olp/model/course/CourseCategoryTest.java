package com.olp.model.course;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.user.Instructor;

/**
 * Task 2.1: CourseCategory 类基础功能测试
 * 验证 CourseCategory 类的构造函数验证和多对多关联
 */
public class CourseCategoryTest {

    @Test
    public void testCreateCourseCategory() {
        CourseCategory category = new CourseCategory("CAT001", "Programming");
        assertNotNull(category);
        assertEquals("CAT001", category.getId());
        assertEquals("Programming", category.getName());
        System.out.println("✅ 可以创建 CourseCategory 对象");
    }

    @Test
    public void testNullIdThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new CourseCategory(null, "Programming"),
            "null id 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("id cannot be null or empty"));
        System.out.println("✅ null id 验证通过");
    }

    @Test
    public void testEmptyIdThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new CourseCategory("", "Programming"),
            "空 id 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("id cannot be null or empty"));
        System.out.println("✅ 空 id 验证通过");
    }

    @Test
    public void testNullNameThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new CourseCategory("CAT001", null),
            "null name 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("name cannot be null or empty"));
        System.out.println("✅ null name 验证通过");
    }

    @Test
    public void testEmptyNameThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new CourseCategory("CAT001", ""),
            "空 name 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("name cannot be null or empty"));
        System.out.println("✅ 空 name 验证通过");
    }

    @Test
    public void testMultipleCategories() {
        CourseCategory cat1 = new CourseCategory("CAT001", "Programming");
        CourseCategory cat2 = new CourseCategory("CAT002", "Mathematics");
        CourseCategory cat3 = new CourseCategory("CAT003", "Science");
        
        assertNotNull(cat1);
        assertNotNull(cat2);
        assertNotNull(cat3);
        
        assertEquals("Programming", cat1.getName());
        assertEquals("Mathematics", cat2.getName());
        assertEquals("Science", cat3.getName());
        
        System.out.println("✅ 可以创建多个 CourseCategory 对象");
    }

    @Test
    public void testManyToManyWithCourse() {
        Instructor instructor = new Instructor("I001", "Dr. Smith", "smith@example.com");
        Course course1 = new Course("C001", "Java Programming", 50, instructor);
        Course course2 = new Course("C002", "Python Basics", 40, instructor);
        
        CourseCategory category = new CourseCategory("CAT001", "Programming");
        
        // 测试添加课程到分类
        boolean added1 = category.addCourse(course1);
        assertTrue(added1, "应该可以添加课程到分类");
        assertEquals(1, category.numberOfCourses());
        assertTrue(category.hasCourses());
        
        boolean added2 = category.addCourse(course2);
        assertTrue(added2, "应该可以添加第二个课程");
        assertEquals(2, category.numberOfCourses());
        
        // 验证双向关联
        assertTrue(course1.hasCategories());
        assertTrue(course2.hasCategories());
        assertEquals(1, course1.numberOfCategories());
        assertEquals(1, course2.numberOfCategories());
        
        System.out.println("✅ 多对多关联（Course-CourseCategory）正常工作");
    }

    @Test
    public void testRemoveCourseFromCategory() {
        Instructor instructor = new Instructor("I002", "Dr. Johnson", "johnson@example.com");
        Course course = new Course("C003", "Web Development", 30, instructor);
        CourseCategory category = new CourseCategory("CAT002", "Web Development");
        
        // 添加课程
        category.addCourse(course);
        assertEquals(1, category.numberOfCourses());
        
        // 移除课程
        boolean removed = category.removeCourse(course);
        assertTrue(removed, "应该可以移除课程");
        assertEquals(0, category.numberOfCourses());
        assertFalse(category.hasCourses());
        
        // 验证双向关联已移除
        assertFalse(course.hasCategories());
        
        System.out.println("✅ 移除课程关联正常工作");
    }

    @Test
    public void testEmptyCategory() {
        CourseCategory category = new CourseCategory("CAT003", "Empty Category");
        
        assertFalse(category.hasCourses());
        assertEquals(0, category.numberOfCourses());
        
        System.out.println("✅ 空分类处理正常");
    }
}

