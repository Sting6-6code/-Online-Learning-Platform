package com.olp.service;

import com.olp.model.payment.Payment;
import com.olp.model.payment.Subscription;
import com.olp.model.payment.Refund;

import java.util.List;
import java.util.Optional;

/**
 * Task 6.8: PaymentService 接口
 * 定义支付和订阅服务的业务逻辑接口
 */
public interface PaymentService {
    
    /**
     * 通过 ID 查找订阅
     * @param id 订阅 ID
     * @return Optional<Subscription>
     */
    Optional<Subscription> findSubscriptionById(String id);
    
    /**
     * 获取所有订阅
     * @return List<Subscription>
     */
    List<Subscription> getAllSubscriptions();
    
    /**
     * 通过状态查找订阅
     * @param status 订阅状态
     * @return List<Subscription>
     */
    List<Subscription> findSubscriptionsByStatus(Subscription.Status status);
    
    /**
     * 通过 ID 查找支付
     * @param id 支付 ID
     * @return Optional<Payment>
     */
    Optional<Payment> findPaymentById(String id);
    
    /**
     * 通过订阅 ID 查找支付
     * @param subscriptionId 订阅 ID
     * @return List<Payment>
     */
    List<Payment> findPaymentsBySubscription(String subscriptionId);
    
    /**
     * 标记支付成功
     * @param paymentId 支付 ID
     * @return Payment
     */
    Payment markPaymentSucceeded(String paymentId);
    
    /**
     * 标记支付失败
     * @param paymentId 支付 ID
     * @return Payment
     */
    Payment markPaymentFailed(String paymentId);
    
    /**
     * 发起退款
     * @param paymentId 支付 ID
     * @param amount 退款金额
     * @return Payment
     */
    Payment initiateRefund(String paymentId, double amount);
    
    /**
     * 订阅计费成功
     * @param subscriptionId 订阅 ID
     * @return Subscription
     */
    Subscription chargeSuccess(String subscriptionId);
    
    /**
     * 订阅计费失败
     * @param subscriptionId 订阅 ID
     * @return Subscription
     */
    Subscription chargeFail(String subscriptionId);
    
    /**
     * 取消订阅
     * @param subscriptionId 订阅 ID
     * @return Subscription
     */
    Subscription cancelSubscription(String subscriptionId);
    
    /**
     * 宽限期到期
     * @param subscriptionId 订阅 ID
     * @return Subscription
     */
    Subscription graceExpire(String subscriptionId);
}

