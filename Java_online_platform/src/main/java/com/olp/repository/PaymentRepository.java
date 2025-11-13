package com.olp.repository;

import com.olp.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Task 6.8: PaymentRepository 接口
 * Repository 接口用于 Payment 实体的数据访问
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    
    /**
     * 通过订阅 ID 查找支付记录
     * @param subscriptionId 订阅 ID
     * @return List<Payment>
     */
    @Query("SELECT p FROM Payment p WHERE p.subscription.id = :subscriptionId")
    List<Payment> findBySubscriptionId(@Param("subscriptionId") String subscriptionId);
    
    /**
     * 通过支付状态查找支付记录
     * @param status 支付状态
     * @return List<Payment>
     */
    List<Payment> findByStatus(Payment.PaymentStatus status);
}

