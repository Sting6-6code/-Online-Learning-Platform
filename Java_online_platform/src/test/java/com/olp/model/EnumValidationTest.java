package com.olp.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.olp.model.course.Course;
import com.olp.model.course.Enrollment;
import com.olp.model.assignment.Submission;
import com.olp.model.payment.Subscription;
import com.olp.model.payment.Payment;

/**
 * Task 0.4: 验证状态机枚举定义
 * 验证所有枚举值与 architecture.md 一致
 */
public class EnumValidationTest {

    @Test
    public void testCourseStatusEnum() {
        // 验证 Course.Status 有 7 个状态
        Course.Status[] statuses = Course.Status.values();
        assertEquals(7, statuses.length, "Course.Status 应该有 7 个状态");
        
        // 验证所有状态值
        assertEquals(Course.Status.Draft, Course.Status.valueOf("Draft"));
        assertEquals(Course.Status.Published, Course.Status.valueOf("Published"));
        assertEquals(Course.Status.EnrollmentOpen, Course.Status.valueOf("EnrollmentOpen"));
        assertEquals(Course.Status.Waitlisted, Course.Status.valueOf("Waitlisted"));
        assertEquals(Course.Status.InProgress, Course.Status.valueOf("InProgress"));
        assertEquals(Course.Status.Completed, Course.Status.valueOf("Completed"));
        assertEquals(Course.Status.Cancelled, Course.Status.valueOf("Cancelled"));
        
        System.out.println("✅ Course.Status 枚举验证通过: " + statuses.length + " 个状态");
    }

    @Test
    public void testSubmissionStatusEnum() {
        // 验证 Submission.Status 有 7 个状态
        Submission.Status[] statuses = Submission.Status.values();
        assertEquals(7, statuses.length, "Submission.Status 应该有 7 个状态");
        
        // 验证所有状态值
        assertEquals(Submission.Status.Created, Submission.Status.valueOf("Created"));
        assertEquals(Submission.Status.Submitted, Submission.Status.valueOf("Submitted"));
        assertEquals(Submission.Status.UnderCheck, Submission.Status.valueOf("UnderCheck"));
        assertEquals(Submission.Status.Grading, Submission.Status.valueOf("Grading"));
        assertEquals(Submission.Status.Graded, Submission.Status.valueOf("Graded"));
        assertEquals(Submission.Status.Returned, Submission.Status.valueOf("Returned"));
        assertEquals(Submission.Status.ResubmissionRequested, Submission.Status.valueOf("ResubmissionRequested"));
        
        System.out.println("✅ Submission.Status 枚举验证通过: " + statuses.length + " 个状态");
    }

    @Test
    public void testSubscriptionStatusEnum() {
        // 验证 Subscription.Status 有 5 个状态
        Subscription.Status[] statuses = Subscription.Status.values();
        assertEquals(5, statuses.length, "Subscription.Status 应该有 5 个状态");
        
        // 验证所有状态值
        assertEquals(Subscription.Status.Trial, Subscription.Status.valueOf("Trial"));
        assertEquals(Subscription.Status.Active, Subscription.Status.valueOf("Active"));
        assertEquals(Subscription.Status.PastDue, Subscription.Status.valueOf("PastDue"));
        assertEquals(Subscription.Status.Suspended, Subscription.Status.valueOf("Suspended"));
        assertEquals(Subscription.Status.Cancelled, Subscription.Status.valueOf("Cancelled"));
        
        System.out.println("✅ Subscription.Status 枚举验证通过: " + statuses.length + " 个状态");
    }

    @Test
    public void testEnrollmentStatusEnum() {
        // 验证 Enrollment.EnrollmentStatus 有 3 个状态
        Enrollment.EnrollmentStatus[] statuses = Enrollment.EnrollmentStatus.values();
        assertEquals(3, statuses.length, "Enrollment.EnrollmentStatus 应该有 3 个状态");
        
        // 验证所有状态值
        assertEquals(Enrollment.EnrollmentStatus.Active, Enrollment.EnrollmentStatus.valueOf("Active"));
        assertEquals(Enrollment.EnrollmentStatus.Waitlisted, Enrollment.EnrollmentStatus.valueOf("Waitlisted"));
        assertEquals(Enrollment.EnrollmentStatus.Dropped, Enrollment.EnrollmentStatus.valueOf("Dropped"));
        
        System.out.println("✅ Enrollment.EnrollmentStatus 枚举验证通过: " + statuses.length + " 个状态");
    }

    @Test
    public void testPaymentStatusEnum() {
        // 验证 Payment.PaymentStatus 有 4 个状态
        Payment.PaymentStatus[] statuses = Payment.PaymentStatus.values();
        assertEquals(4, statuses.length, "Payment.PaymentStatus 应该有 4 个状态");
        
        // 验证所有状态值
        assertEquals(Payment.PaymentStatus.Succeeded, Payment.PaymentStatus.valueOf("Succeeded"));
        assertEquals(Payment.PaymentStatus.Failed, Payment.PaymentStatus.valueOf("Failed"));
        assertEquals(Payment.PaymentStatus.Refunding, Payment.PaymentStatus.valueOf("Refunding"));
        assertEquals(Payment.PaymentStatus.Refunded, Payment.PaymentStatus.valueOf("Refunded"));
        
        System.out.println("✅ Payment.PaymentStatus 枚举验证通过: " + statuses.length + " 个状态");
    }

    @Test
    public void testPlanTypeEnum() {
        // 验证 Subscription.PlanType 有 3 个状态
        Subscription.PlanType[] planTypes = Subscription.PlanType.values();
        assertEquals(3, planTypes.length, "Subscription.PlanType 应该有 3 个状态");
        
        // 验证所有状态值
        assertEquals(Subscription.PlanType.Trial, Subscription.PlanType.valueOf("Trial"));
        assertEquals(Subscription.PlanType.Monthly, Subscription.PlanType.valueOf("Monthly"));
        assertEquals(Subscription.PlanType.Annual, Subscription.PlanType.valueOf("Annual"));
        
        System.out.println("✅ Subscription.PlanType 枚举验证通过: " + planTypes.length + " 个状态");
    }

    @Test
    public void testEnumUsage() {
        // 测试枚举可以正确使用
        Course.Status courseStatus = Course.Status.Draft;
        assertNotNull(courseStatus);
        assertEquals("Draft", courseStatus.name());
        
        Submission.Status submissionStatus = Submission.Status.Created;
        assertNotNull(submissionStatus);
        assertEquals("Created", submissionStatus.name());
        
        Subscription.Status subscriptionStatus = Subscription.Status.Trial;
        assertNotNull(subscriptionStatus);
        assertEquals("Trial", subscriptionStatus.name());
        
        Enrollment.EnrollmentStatus enrollmentStatus = Enrollment.EnrollmentStatus.Active;
        assertNotNull(enrollmentStatus);
        assertEquals("Active", enrollmentStatus.name());
        
        Payment.PaymentStatus paymentStatus = Payment.PaymentStatus.Succeeded;
        assertNotNull(paymentStatus);
        assertEquals("Succeeded", paymentStatus.name());
        
        Subscription.PlanType planType = Subscription.PlanType.Monthly;
        assertNotNull(planType);
        assertEquals("Monthly", planType.name());
        
        System.out.println("✅ 所有枚举可以正确使用");
    }
}

