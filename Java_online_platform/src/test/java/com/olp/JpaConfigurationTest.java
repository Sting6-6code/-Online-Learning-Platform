package com.olp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 0.7: 验证 JPA 配置
 * 测试所有实体类是否正确配置，Hibernate 可以识别并创建表
 */
@SpringBootTest
@ActiveProfiles("dev")
public class JpaConfigurationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testEntityManagerIsAvailable() {
        assertNotNull(entityManager, "EntityManager 应该可用");
        System.out.println("✅ EntityManager 可用");
    }

    @Test
    public void testAllEntitiesAreRegistered() {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        
        // 验证所有实体类都已注册
        assertTrue(entities.size() > 0, "应该有实体类注册");
        
        // 验证关键实体类存在
        boolean hasStudent = entities.stream()
            .anyMatch(e -> e.getName().equals("Student"));
        boolean hasCourse = entities.stream()
            .anyMatch(e -> e.getName().equals("Course"));
        boolean hasEnrollment = entities.stream()
            .anyMatch(e -> e.getName().equals("Enrollment"));
        boolean hasAssignment = entities.stream()
            .anyMatch(e -> e.getName().equals("Assignment"));
        boolean hasSubmission = entities.stream()
            .anyMatch(e -> e.getName().equals("Submission"));
        boolean hasPayment = entities.stream()
            .anyMatch(e -> e.getName().equals("Payment"));
        boolean hasSubscription = entities.stream()
            .anyMatch(e -> e.getName().equals("Subscription"));
        
        assertTrue(hasStudent, "Student 实体应该注册");
        assertTrue(hasCourse, "Course 实体应该注册");
        assertTrue(hasEnrollment, "Enrollment 实体应该注册");
        assertTrue(hasAssignment, "Assignment 实体应该注册");
        assertTrue(hasSubmission, "Submission 实体应该注册");
        assertTrue(hasPayment, "Payment 实体应该注册");
        assertTrue(hasSubscription, "Subscription 实体应该注册");
        
        System.out.println("✅ 所有实体类已注册: " + entities.size() + " 个实体");
        entities.forEach(e -> System.out.println("   - " + e.getName()));
    }

    @Test
    public void testJpaConfigurationIsValid() {
        // 如果 JPA 配置有错误，应用启动时会抛出异常
        // 这个测试通过意味着配置基本正确
        assertNotNull(entityManager.getMetamodel(), "JPA Metamodel 应该可用");
        System.out.println("✅ JPA 配置有效");
    }
}

