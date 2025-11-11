package com.olp.model.payment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

/**
 * Task 4.6: Payment 类构造函数验证测试
 * 验证 Payment 类的基本验证和初始状态
 */
@SpringBootTest
public class PaymentTest {

    @Test
    public void testCreatePayment() {
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
            99.99,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        
        assertNotNull(payment);
        assertEquals("PAY001", payment.getId());
        assertEquals(99.99, payment.getAmount(), 0.001);
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus(), "初始状态应该为构造函数参数值");
        assertEquals(paidAt, payment.getPaidAt());
        assertEquals(subscription, payment.getSubscription());
        
        System.out.println("✅ 可以创建 Payment 对象");
    }

    @Test
    public void testInitialStatusFromConstructor() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB002",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        // 测试不同的初始状态
        Payment payment1 = new Payment(
            "PAY002",
            99.99,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        assertEquals(Payment.PaymentStatus.Succeeded, payment1.getStatus(), "初始状态应该为 Succeeded");
        
        Payment payment2 = new Payment(
            "PAY003",
            99.99,
            Payment.PaymentStatus.Failed,
            paidAt,
            subscription
        );
        assertEquals(Payment.PaymentStatus.Failed, payment2.getStatus(), "初始状态应该为 Failed");
        
        Payment payment3 = new Payment(
            "PAY004",
            99.99,
            Payment.PaymentStatus.Refunding,
            paidAt,
            subscription
        );
        assertEquals(Payment.PaymentStatus.Refunding, payment3.getStatus(), "初始状态应该为 Refunding");
        
        System.out.println("✅ 初始状态为构造函数参数值");
    }

    @Test
    public void testInvalidAmountThrowsException() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB003",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        // 测试 amount <= 0
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> new Payment("PAY005", 0.0, Payment.PaymentStatus.Succeeded, paidAt, subscription),
            "amount = 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception1.getMessage().contains("amount must be greater than 0"));
        
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new Payment("PAY006", -10.0, Payment.PaymentStatus.Succeeded, paidAt, subscription),
            "amount < 0 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception2.getMessage().contains("amount must be greater than 0"));
        
        System.out.println("✅ amount 验证正常（<= 0 抛出异常）");
    }

    @Test
    public void testNullSubscriptionThrowsException() {
        Date paidAt = new Date(System.currentTimeMillis());
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Payment("PAY007", 99.99, Payment.PaymentStatus.Succeeded, paidAt, null),
            "null subscription 应该抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("subscription cannot be null"));
        
        System.out.println("✅ null subscription 验证通过");
    }

    @Test
    public void testValidAmount() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB004",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        // 测试有效的 amount 值
        Payment payment1 = new Payment(
            "PAY008",
            0.01, // 最小有效值
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        assertEquals(0.01, payment1.getAmount(), 0.001);
        
        Payment payment2 = new Payment(
            "PAY009",
            9999.99, // 大值
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        assertEquals(9999.99, payment2.getAmount(), 0.001);
        
        System.out.println("✅ 有效的 amount 值可以创建 Payment");
    }

    @Test
    public void testPaymentAssociation() {
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
            "PAY010",
            99.99,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        
        // 验证双向关联
        assertTrue(subscription.hasSubscriptionPayments());
        assertEquals(1, subscription.numberOfSubscriptionPayments());
        assertEquals(payment, subscription.getSubscriptionPayment(0));
        assertEquals(subscription, payment.getSubscription());
        
        System.out.println("✅ Payment 关联验证正常");
    }

    // Task 4.7: Payment 状态转换方法测试
    @Test
    public void testMarkSucceededFromPending() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB006",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY011",
            99.99,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        assertEquals(Payment.PaymentStatus.Pending, payment.getStatus());
        
        // 标记为成功
        boolean succeeded = payment.markSucceeded();
        assertTrue(succeeded, "Pending 状态的支付应该可以标记为成功");
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus(), "状态应该变为 Succeeded");
        assertNotNull(payment.getPaidAt(), "paidAt 应该被设置");
        
        System.out.println("✅ Pending 状态的支付可以标记为成功");
    }

    @Test
    public void testMarkFailedFromPending() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB007",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY012",
            99.99,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        assertEquals(Payment.PaymentStatus.Pending, payment.getStatus());
        
        // 标记为失败
        boolean failed = payment.markFailed();
        assertTrue(failed, "Pending 状态的支付应该可以标记为失败");
        assertEquals(Payment.PaymentStatus.Failed, payment.getStatus(), "状态应该变为 Failed");
        
        System.out.println("✅ Pending 状态的支付可以标记为失败");
    }

    @Test
    public void testMarkSucceededFromNonPendingStatus() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB008",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        // 测试从 Succeeded 状态
        Payment payment1 = new Payment(
            "PAY013",
            99.99,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        boolean succeeded1 = payment1.markSucceeded();
        assertFalse(succeeded1, "Succeeded 状态的支付不应该再次标记为成功");
        assertEquals(Payment.PaymentStatus.Succeeded, payment1.getStatus());
        
        // 测试从 Failed 状态
        Payment payment2 = new Payment(
            "PAY014",
            99.99,
            Payment.PaymentStatus.Failed,
            paidAt,
            subscription
        );
        boolean succeeded2 = payment2.markSucceeded();
        assertFalse(succeeded2, "Failed 状态的支付不应该标记为成功");
        assertEquals(Payment.PaymentStatus.Failed, payment2.getStatus());
        
        System.out.println("✅ 非 Pending 状态的支付操作失败");
    }

    @Test
    public void testMarkFailedFromNonPendingStatus() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB009",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        // 测试从 Succeeded 状态
        Payment payment1 = new Payment(
            "PAY015",
            99.99,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        boolean failed1 = payment1.markFailed();
        assertFalse(failed1, "Succeeded 状态的支付不应该标记为失败");
        assertEquals(Payment.PaymentStatus.Succeeded, payment1.getStatus());
        
        // 测试从 Failed 状态
        Payment payment2 = new Payment(
            "PAY016",
            99.99,
            Payment.PaymentStatus.Failed,
            paidAt,
            subscription
        );
        boolean failed2 = payment2.markFailed();
        assertFalse(failed2, "Failed 状态的支付不应该再次标记为失败");
        assertEquals(Payment.PaymentStatus.Failed, payment2.getStatus());
        
        System.out.println("✅ 非 Pending 状态的支付操作失败");
    }

    @Test
    public void testMarkSucceededSetsPaidAt() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB010",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date initialPaidAt = new Date(System.currentTimeMillis() - 86400000); // 昨天
        
        Payment payment = new Payment(
            "PAY017",
            99.99,
            Payment.PaymentStatus.Pending,
            initialPaidAt,
            subscription
        );
        assertEquals(initialPaidAt, payment.getPaidAt());
        
        // 记录标记成功前的时间
        long beforeMark = System.currentTimeMillis();
        
        // 标记为成功
        payment.markSucceeded();
        
        // 记录标记成功后的时间
        long afterMark = System.currentTimeMillis();
        
        // 验证 paidAt 被更新为当前时间
        assertNotNull(payment.getPaidAt());
        long paidAtTime = payment.getPaidAt().getTime();
        assertTrue(paidAtTime >= beforeMark && paidAtTime <= afterMark, 
                   "paidAt 应该在标记成功操作的时间范围内");
        
        System.out.println("✅ markSucceeded() 正确设置 paidAt");
    }

    // Task 4.8: initiateRefund() 方法测试
    @Test
    public void testInitiateRefundFromSucceeded() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB011",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY018",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        payment.markSucceeded();
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus());
        
        // 发起退款
        boolean refunded = payment.initiateRefund(50.0);
        assertTrue(refunded, "Succeeded 状态的支付应该可以发起退款");
        assertEquals(Payment.PaymentStatus.Refunding, payment.getStatus(), "状态应该变为 Refunding");
        assertNotNull(payment.getPaymentRefund(), "Refund 对象应该被创建");
        assertEquals(50.0, payment.getPaymentRefund().getAmount(), 0.001);
        assertNotNull(payment.getPaymentRefund().getRequestedAt(), "requestedAt 应该被设置");
        assertNull(payment.getPaymentRefund().getProcessedAt(), "processedAt 初始应该为 null");
        
        System.out.println("✅ Succeeded 状态的支付可以发起退款");
    }

    @Test
    public void testInitiateRefundWithInvalidAmount() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB012",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY019",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        payment.markSucceeded();
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus());
        
        // 测试负数退款金额
        boolean refunded1 = payment.initiateRefund(-10.0);
        assertFalse(refunded1, "负数退款金额应该失败");
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus(), "状态应该保持为 Succeeded");
        
        // 测试超过原金额的退款
        boolean refunded2 = payment.initiateRefund(150.0);
        assertFalse(refunded2, "超过原金额的退款应该失败");
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus(), "状态应该保持为 Succeeded");
        
        // 测试退款金额为 0
        boolean refunded3 = payment.initiateRefund(0.0);
        assertFalse(refunded3, "退款金额为 0 应该失败");
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus(), "状态应该保持为 Succeeded");
        
        System.out.println("✅ 退款金额验证正常（负数或超过原金额失败）");
    }

    @Test
    public void testInitiateRefundCreatesRefund() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB013",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        Payment payment = new Payment(
            "PAY020",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        payment.markSucceeded();
        
        // 发起退款
        payment.initiateRefund(75.0);
        
        // 验证 Refund 对象被创建
        assertNotNull(payment.getPaymentRefund(), "Refund 对象应该被创建");
        Refund refund = payment.getPaymentRefund();
        assertNotNull(refund.getId(), "Refund ID 应该被生成");
        assertTrue(refund.getId().startsWith("REF"), "Refund ID 应该以 'REF' 开头");
        assertEquals(75.0, refund.getAmount(), 0.001);
        assertNotNull(refund.getRequestedAt(), "requestedAt 应该被设置");
        assertNull(refund.getProcessedAt(), "processedAt 初始应该为 null");
        assertEquals(payment, refund.getPayment(), "Refund 应该关联到 Payment");
        
        System.out.println("✅ Refund 对象正确创建");
    }

    @Test
    public void testInitiateRefundFromNonSucceededStatus() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB014",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        // 测试从 Pending 状态
        Payment payment1 = new Payment(
            "PAY021",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        boolean refunded1 = payment1.initiateRefund(50.0);
        assertFalse(refunded1, "Pending 状态的支付不应该发起退款");
        assertEquals(Payment.PaymentStatus.Pending, payment1.getStatus());
        
        // 测试从 Failed 状态
        Payment payment2 = new Payment(
            "PAY022",
            100.0,
            Payment.PaymentStatus.Failed,
            paidAt,
            subscription
        );
        boolean refunded2 = payment2.initiateRefund(50.0);
        assertFalse(refunded2, "Failed 状态的支付不应该发起退款");
        assertEquals(Payment.PaymentStatus.Failed, payment2.getStatus());
        
        // 测试从 Refunding 状态
        Payment payment3 = new Payment(
            "PAY023",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        payment3.markSucceeded();
        payment3.initiateRefund(50.0);
        assertEquals(Payment.PaymentStatus.Refunding, payment3.getStatus());
        boolean refunded3 = payment3.initiateRefund(30.0);
        assertFalse(refunded3, "Refunding 状态的支付不应该再次发起退款");
        assertEquals(Payment.PaymentStatus.Refunding, payment3.getStatus());
        
        System.out.println("✅ 非 Succeeded 状态的支付发起退款失败");
    }

    @Test
    public void testInitiateRefundWithValidAmountRange() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB015",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        // 测试边界值：最小退款金额
        Payment payment1 = new Payment(
            "PAY024",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        payment1.markSucceeded();
        boolean refunded1 = payment1.initiateRefund(0.01); // 最小有效值
        assertTrue(refunded1, "最小退款金额应该可以退款");
        assertEquals(0.01, payment1.getPaymentRefund().getAmount(), 0.001);
        
        // 测试边界值：最大退款金额（等于原金额）
        Payment payment2 = new Payment(
            "PAY025",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        payment2.markSucceeded();
        boolean refunded2 = payment2.initiateRefund(100.0); // 等于原金额
        assertTrue(refunded2, "等于原金额的退款应该可以退款");
        assertEquals(100.0, payment2.getPaymentRefund().getAmount(), 0.001);
        
        System.out.println("✅ 有效退款金额范围验证通过");
    }
}

