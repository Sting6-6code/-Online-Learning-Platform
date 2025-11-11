package com.olp.model.payment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

/**
 * Task 4.10: 订阅计费工作流集成测试
 * 测试完整的订阅计费流程和退款流程
 */
@SpringBootTest
public class SubscriptionBillingWorkflowTest {

    @Test
    public void testCompleteSubscriptionBillingWorkflow() {
        // 1. 创建 Subscription（状态：Trial）
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB001",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        assertEquals(Subscription.Status.Trial, subscription.getStatus(), "步骤1: 初始状态应为 Trial");
        
        // 2. 创建 Payment（状态：Pending）
        Date paidAt = new Date(System.currentTimeMillis());
        Payment payment1 = new Payment(
            "PAY001",
            99.99,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        assertEquals(Payment.PaymentStatus.Pending, payment1.getStatus(), "步骤2: Payment 初始状态应为 Pending");
        
        // 3. Payment 成功（状态：Succeeded）
        boolean succeeded = payment1.markSucceeded();
        assertTrue(succeeded, "步骤3: Payment 应该可以标记为成功");
        assertEquals(Payment.PaymentStatus.Succeeded, payment1.getStatus(), "步骤3: Payment 状态应为 Succeeded");
        assertNotNull(payment1.getPaidAt(), "步骤3: paidAt 应该被设置");
        
        // 4. Subscription 计费成功（状态：Active，nextBillingAt 更新）
        boolean charged = subscription.chargeSuccess();
        assertTrue(charged, "步骤4: Subscription 应该可以计费成功");
        assertEquals(Subscription.Status.Active, subscription.getStatus(), "步骤4: Subscription 状态应为 Active");
        assertNotNull(subscription.getNextBillingAt(), "步骤4: nextBillingAt 应该被更新");
        
        // 5. 创建新的 Payment（状态：Pending）
        Date paidAt2 = new Date(System.currentTimeMillis() + 1000);
        Payment payment2 = new Payment(
            "PAY002",
            99.99,
            Payment.PaymentStatus.Pending,
            paidAt2,
            subscription
        );
        assertEquals(Payment.PaymentStatus.Pending, payment2.getStatus(), "步骤5: 新 Payment 初始状态应为 Pending");
        
        // 6. Payment 失败（状态：Failed）
        boolean failed = payment2.markFailed();
        assertTrue(failed, "步骤6: Payment 应该可以标记为失败");
        assertEquals(Payment.PaymentStatus.Failed, payment2.getStatus(), "步骤6: Payment 状态应为 Failed");
        
        // 7. Subscription 计费失败（状态：PastDue）
        boolean chargeFailed = subscription.chargeFail();
        assertTrue(chargeFailed, "步骤7: Subscription 应该可以计费失败");
        assertEquals(Subscription.Status.PastDue, subscription.getStatus(), "步骤7: Subscription 状态应为 PastDue");
        
        // 8. 宽限期到期（状态：Suspended）
        // 手动将 nextBillingAt 设置为过去的时间（超过宽限期）
        Date pastBillingAt = new Date(System.currentTimeMillis() - 86400000 * 10); // 10天前
        subscription.setNextBillingAt(pastBillingAt);
        boolean expired = subscription.graceExpire();
        assertTrue(expired, "步骤8: Subscription 宽限期应该可以到期");
        assertEquals(Subscription.Status.Suspended, subscription.getStatus(), "步骤8: Subscription 状态应为 Suspended");
        
        System.out.println("✅ 完整订阅计费工作流测试通过");
    }

    @Test
    public void testRefundWorkflow() {
        // 1. 创建成功的 Payment
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
            "PAY003",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        payment.markSucceeded();
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus(), "步骤1: Payment 状态应为 Succeeded");
        
        // 2. 发起退款（状态：Refunding，创建 Refund）
        boolean refunded = payment.initiateRefund(50.0);
        assertTrue(refunded, "步骤2: Payment 应该可以发起退款");
        assertEquals(Payment.PaymentStatus.Refunding, payment.getStatus(), "步骤2: Payment 状态应为 Refunding");
        assertNotNull(payment.getPaymentRefund(), "步骤2: Refund 对象应该被创建");
        Refund refund = payment.getPaymentRefund();
        assertEquals(50.0, refund.getAmount(), 0.001, "步骤2: Refund 金额应该正确");
        assertNotNull(refund.getRequestedAt(), "步骤2: requestedAt 应该被设置");
        assertNull(refund.getProcessedAt(), "步骤2: processedAt 初始应该为 null");
        
        // 3. 完成退款（Refund.processedAt 设置）
        Date processedAt = new Date(System.currentTimeMillis());
        refund.setProcessedAt(processedAt);
        assertNotNull(refund.getProcessedAt(), "步骤3: processedAt 应该被设置");
        assertEquals(processedAt, refund.getProcessedAt(), "步骤3: processedAt 应该正确设置");
        
        System.out.println("✅ 退款流程测试通过");
    }

    @Test
    public void testSubscriptionStateTransitions() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB003",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        
        // 验证状态转换序列
        assertEquals(Subscription.Status.Trial, subscription.getStatus());
        
        // Trial -> Active
        Date paidAt1 = new Date(System.currentTimeMillis());
        Payment payment1 = new Payment(
            "PAY004",
            99.99,
            Payment.PaymentStatus.Succeeded,
            paidAt1,
            subscription
        );
        subscription.chargeSuccess();
        assertEquals(Subscription.Status.Active, subscription.getStatus());
        
        // Active -> PastDue
        Date paidAt2 = new Date(System.currentTimeMillis() + 1000);
        Payment payment2 = new Payment(
            "PAY005",
            99.99,
            Payment.PaymentStatus.Failed,
            paidAt2,
            subscription
        );
        subscription.chargeFail();
        assertEquals(Subscription.Status.PastDue, subscription.getStatus());
        
        // PastDue -> Suspended (宽限期到期)
        Date pastBillingAt = new Date(System.currentTimeMillis() - 86400000 * 10);
        subscription.setNextBillingAt(pastBillingAt);
        subscription.graceExpire();
        assertEquals(Subscription.Status.Suspended, subscription.getStatus());
        
        // 任何状态 -> Cancelled
        subscription.cancel();
        assertEquals(Subscription.Status.Cancelled, subscription.getStatus());
        
        System.out.println("✅ 状态转换序列验证通过");
    }

    @Test
    public void testPaymentStateTransitions() {
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
            "PAY006",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        
        // 验证状态转换序列
        assertEquals(Payment.PaymentStatus.Pending, payment.getStatus());
        
        // Pending -> Succeeded
        payment.markSucceeded();
        assertEquals(Payment.PaymentStatus.Succeeded, payment.getStatus());
        
        // Succeeded -> Refunding
        payment.initiateRefund(50.0);
        assertEquals(Payment.PaymentStatus.Refunding, payment.getStatus());
        assertNotNull(payment.getPaymentRefund());
        
        System.out.println("✅ Payment 状态转换序列验证通过");
    }

    @Test
    public void testWorkflowGuards() {
        Date startAt = new Date(System.currentTimeMillis());
        Date nextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB005",
            Subscription.PlanType.Monthly,
            startAt,
            nextBillingAt
        );
        Date paidAt = new Date(System.currentTimeMillis());
        
        // 测试守卫条件：不能跳过状态
        Payment payment = new Payment(
            "PAY007",
            100.0,
            Payment.PaymentStatus.Pending,
            paidAt,
            subscription
        );
        
        // 不能从 Pending 直接发起退款
        assertFalse(payment.initiateRefund(50.0), "Pending 状态的 Payment 不应该发起退款");
        
        // 必须先标记为成功
        payment.markSucceeded();
        assertTrue(payment.initiateRefund(50.0), "Succeeded 状态的 Payment 应该可以发起退款");
        
        // 不能再次发起退款
        assertFalse(payment.initiateRefund(30.0), "Refunding 状态的 Payment 不应该再次发起退款");
        
        System.out.println("✅ 守卫条件验证通过");
    }

    @Test
    public void testNextBillingAtUpdate() {
        Date startAt = new Date(System.currentTimeMillis());
        Date originalNextBillingAt = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription = new Subscription(
            "SUB006",
            Subscription.PlanType.Monthly,
            startAt,
            originalNextBillingAt
        );
        
        // 测试 Monthly 计划的 nextBillingAt 更新
        Date paidAt = new Date(System.currentTimeMillis());
        Payment payment = new Payment(
            "PAY008",
            99.99,
            Payment.PaymentStatus.Succeeded,
            paidAt,
            subscription
        );
        subscription.chargeSuccess();
        
        Date expectedNextBillingAt = com.olp.util.Utils.addMonths(originalNextBillingAt, 1);
        assertEquals(expectedNextBillingAt, subscription.getNextBillingAt(), 
                     "Monthly 计划的 nextBillingAt 应该增加 1 个月");
        
        // 测试 Annual 计划的 nextBillingAt 更新
        Date startAt2 = new Date(System.currentTimeMillis());
        Date originalNextBillingAt2 = new Date(System.currentTimeMillis() + 86400000 * 30);
        Subscription subscription2 = new Subscription(
            "SUB007",
            Subscription.PlanType.Annual,
            startAt2,
            originalNextBillingAt2
        );
        
        Date paidAt2 = new Date(System.currentTimeMillis());
        Payment payment2 = new Payment(
            "PAY009",
            999.99,
            Payment.PaymentStatus.Succeeded,
            paidAt2,
            subscription2
        );
        subscription2.chargeSuccess();
        
        Date expectedNextBillingAt2 = com.olp.util.Utils.addMonths(originalNextBillingAt2, 12);
        assertEquals(expectedNextBillingAt2, subscription2.getNextBillingAt(), 
                     "Annual 计划的 nextBillingAt 应该增加 12 个月");
        
        System.out.println("✅ nextBillingAt 更新验证通过");
    }
}

