package com.olp.model.payment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

/**
 * Task 4.9: Refund 类构造函数验证测试
 * 验证 Refund 类的基本验证和 OCL 约束
 */
@SpringBootTest
public class RefundTest {

    @Test
    public void testCreateRefund() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB001",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY001",
            100.0,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        
        Date requestedAt = new Date(System.currentTimeMillis());
        Refund refund = new Refund(
            "REF001",
            50.0,
            requestedAt,
            null,
            payment
        );
        
        assertNotNull(refund);
        assertEquals("REF001", refund.getId());
        assertEquals(50.0, refund.getAmount(), 0.001);
        assertEquals(requestedAt, refund.getRequestedAt());
        assertNull(refund.getProcessedAt());
        assertEquals(payment, refund.getPayment());
        
        System.out.println("✅ 可以创建 Refund 对象");
    }

    @Test
    public void testInvalidAmountThrowsException() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB002",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY002",
            100.0,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        Date requestedAt = new Date(System.currentTimeMillis());
        
        // 测试 amount <= 0
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> new Refund("REF002", 0.0, requestedAt, null, payment),
            "amount = 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception1.getMessage().contains("amount must be greater than 0"));
        
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new Refund("REF003", -10.0, requestedAt, null, payment),
            "amount < 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception2.getMessage().contains("amount must be greater than 0"));
        
        System.out.println("✅ amount 验证正常");
    }

    @Test
    public void testNullPaymentThrowsException() {
        Date requestedAt = new Date(System.currentTimeMillis());
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Refund("REF004", 50.0, requestedAt, null, null),
            "null payment 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("payment cannot be null"));
        
        System.out.println("✅ null payment 验证通过");
    }

    @Test
    public void testRefundOnlyForSucceededPayment() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB003",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        Date requestedAt = new Date(System.currentTimeMillis());
        
        // 测试 Pending 状态的 Payment
        Payment payment1 = new Payment(
            "PAY003",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> new Refund("REF005", 50.0, requestedAt, null, payment1),
            "Pending 状态的 Payment 不应该创建 Refund"
        );
        assertTrue(exception1.getMessage().contains("Succeeded"));
        
        // 测试 Failed 状态的 Payment
        Payment payment2 = new Payment(
            "PAY004",
            100.0,
            Payment.PaymentStatus.Failed,
            paidAt,
            subscription
        );
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new Refund("REF006", 50.0, requestedAt, null, payment2),
            "Failed 状态的 Payment 不应该创建 Refund"
        );
        assertTrue(exception2.getMessage().contains("Succeeded"));
        
        // 测试 Refunding 状态的 Payment
        Payment payment3 = new Payment(
            "PAY005",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        payment3.markSucceeded();
        payment3.initiateRefund(50.0);
        assertEquals(Payment.PaymentStatus.Refunding, payment3.getStatus());
        
        // 为 Refunding 状态的 Payment 创建新的 Refund 应该失败
        // 但这里我们测试的是构造函数验证，所以应该检查 payment3 的状态
        // 实际上，Refunding 状态的 Payment 已经有 Refund 了，不应该再创建新的
        // 但为了测试构造函数验证，我们创建一个新的 Payment
        Payment payment4 = new Payment(
            "PAY006",
            100.0,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        // 手动设置为 Refunding（绕过 initiateRefund）
        payment4.setStatus(Payment.PaymentStatus.Refunding);
        
        IllegalArgumentException exception3 = assertThrows(
            IllegalArgumentException.class,
            () -> new Refund("REF007", 50.0, requestedAt, null, payment4),
            "Refunding 状态的 Payment 不应该创建 Refund"
        );
        assertTrue(exception3.getMessage().contains("Succeeded"));
        
        System.out.println("✅ 只能为 Succeeded 状态的 Payment 创建 Refund");
    }

    @Test
    public void testValidRefundCreation() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB004",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY007",
            100.0,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        Date requestedAt = new Date(System.currentTimeMillis());
        
        // 测试有效的 Refund 创建
        Refund refund1 = new Refund(
            "REF008",
            0.01, // 最小有效值
            requestedAt,
            null,
            payment
        );
        assertEquals(0.01, refund1.getAmount(), 0.001);
        
        Refund refund2 = new Refund(
            "REF009",
            100.0, // 等于 Payment amount
            requestedAt,
            null,
            payment
        );
        assertEquals(100.0, refund2.getAmount(), 0.001);
        
        System.out.println("✅ 有效的 Refund 可以创建");
    }

    @Test
    public void testRefundAssociation() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB005",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY008",
            100.0,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        Date requestedAt = new Date(System.currentTimeMillis());
        
        Refund refund = new Refund(
            "REF010",
            50.0,
            requestedAt,
            null,
            payment
        );
        
        // 验证双向关联
        assertEquals(payment, refund.getPayment());
        assertEquals(refund, payment.getPaymentRefund());
        
        System.out.println("✅ Refund 关联验证正常");
    }
}

