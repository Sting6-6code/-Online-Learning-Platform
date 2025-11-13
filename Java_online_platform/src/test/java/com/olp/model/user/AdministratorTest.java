package com.olp.model.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 1.4: Administrator 类基础功能测试
 * 验证 Administrator 类的继承关系和基本操作
 */
public class AdministratorTest {

    @Test
    public void testCreateAdministrator() {
        Administrator admin = new Administrator("A001", "Admin User", "admin@example.com");
        assertNotNull(admin);
        assertEquals("A001", admin.getId());
        assertEquals("Admin User", admin.getName());
        assertEquals("admin@example.com", admin.getEmail());
        System.out.println("✅ 可以创建 Administrator 对象");
    }

    @Test
    public void testInheritUserProperties() {
        Administrator admin = new Administrator("A002", "System Admin", "sysadmin@example.com");
        
        // 验证可以访问继承的属性
        assertEquals("A002", admin.getId());
        assertEquals("System Admin", admin.getName());
        assertEquals("sysadmin@example.com", admin.getEmail());
        
        System.out.println("✅ 可以访问继承的属性（id, name, email）");
    }

    @Test
    public void testInheritanceRelationship() {
        Administrator admin = new Administrator("A003", "Super Admin", "superadmin@example.com");
        
        // 验证继承关系
        assertTrue(admin instanceof User, "Administrator 应该继承 User");
        assertTrue(admin instanceof Administrator, "应该是 Administrator 实例");
        
        // 验证可以调用 User 的方法
        String id = admin.getId();
        String name = admin.getName();
        String email = admin.getEmail();
        
        assertNotNull(id);
        assertNotNull(name);
        assertNotNull(email);
        
        System.out.println("✅ 继承关系正确");
    }

    @Test
    public void testMultipleAdministrators() {
        Administrator admin1 = new Administrator("A004", "Admin 1", "admin1@example.com");
        Administrator admin2 = new Administrator("A005", "Admin 2", "admin2@example.com");
        Administrator admin3 = new Administrator("A006", "Admin 3", "admin3@example.com");
        
        // 验证每个管理员都是独立的实例
        assertNotEquals(admin1.getId(), admin2.getId());
        assertNotEquals(admin2.getId(), admin3.getId());
        assertNotEquals(admin1.getEmail(), admin2.getEmail());
        
        System.out.println("✅ 可以创建多个 Administrator 对象");
    }

    @Test
    public void testAdministratorValidation() {
        // 验证 Administrator 也遵循 User 的验证规则
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> new Administrator(null, "Admin", "admin@example.com"),
            "null id 应该抛出异常"
        );
        
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new Administrator("A007", "Admin", "invalid-email"),
            "无效 email 应该抛出异常"
        );
        
        System.out.println("✅ Administrator 遵循 User 的验证规则");
    }
}

