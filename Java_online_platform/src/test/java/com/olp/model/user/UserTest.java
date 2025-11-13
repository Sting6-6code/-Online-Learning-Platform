package com.olp.model.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 1.1: User 基类构造函数验证测试
 * 验证 User 类的属性验证逻辑
 */
public class UserTest {

    @Test
    public void testCreateValidUser() {
        // 通过子类 Student 测试创建有效 User 对象
        Student student = new Student("S001", "Alice", "alice@example.com");
        assertNotNull(student);
        assertEquals("S001", student.getId());
        assertEquals("Alice", student.getName());
        assertEquals("alice@example.com", student.getEmail());
        System.out.println("✅ 创建有效 User 对象成功");
    }

    @Test
    public void testNullIdThrowsException() {
        // 测试 null id 应该抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Student(null, "Alice", "alice@example.com"),
            "null id 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("id cannot be null or empty"));
        System.out.println("✅ null id 验证通过");
    }

    @Test
    public void testEmptyIdThrowsException() {
        // 测试空字符串 id 应该抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Student("", "Alice", "alice@example.com"),
            "空 id 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("id cannot be null or empty"));
        System.out.println("✅ 空 id 验证通过");
    }

    @Test
    public void testWhitespaceIdThrowsException() {
        // 测试只有空格的 id 应该抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Student("   ", "Alice", "alice@example.com"),
            "只有空格的 id 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("id cannot be null or empty"));
        System.out.println("✅ 空格 id 验证通过");
    }

    @Test
    public void testInvalidEmailThrowsException() {
        // 测试无效 email（无 @ 符号）应该抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Student("S001", "Alice", "invalid-email"),
            "无效 email 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("email must contain @ symbol"));
        System.out.println("✅ 无效 email 验证通过");
    }

    @Test
    public void testNullEmailThrowsException() {
        // 测试 null email 应该抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Student("S001", "Alice", null),
            "null email 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("email cannot be null or empty"));
        System.out.println("✅ null email 验证通过");
    }

    @Test
    public void testEmptyEmailThrowsException() {
        // 测试空 email 应该抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Student("S001", "Alice", ""),
            "空 email 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("email cannot be null or empty"));
        System.out.println("✅ 空 email 验证通过");
    }

    @Test
    public void testNullNameThrowsException() {
        // 测试 null name 应该抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Student("S001", null, "alice@example.com"),
            "null name 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("name cannot be null or empty"));
        System.out.println("✅ null name 验证通过");
    }

    @Test
    public void testEmptyNameThrowsException() {
        // 测试空 name 应该抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Student("S001", "", "alice@example.com"),
            "空 name 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("name cannot be null or empty"));
        System.out.println("✅ 空 name 验证通过");
    }

    @Test
    public void testValidEmailFormats() {
        // 测试各种有效的 email 格式
        Student student1 = new Student("S001", "Alice", "alice@example.com");
        assertNotNull(student1);

        Student student2 = new Student("S002", "Bob", "bob.test@example.co.uk");
        assertNotNull(student2);

        Student student3 = new Student("S003", "Charlie", "charlie+tag@example.com");
        assertNotNull(student3);

        System.out.println("✅ 有效 email 格式验证通过");
    }
}

