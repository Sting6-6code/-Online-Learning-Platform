package com.olp.service;

import com.olp.model.payment.Payment;
import com.olp.model.payment.Subscription;
import com.olp.repository.PaymentRepository;
import com.olp.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Task 6.8: PaymentServiceImpl 实现类
 * 实现支付和订阅服务的业务逻辑
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Subscription> findSubscriptionById(String id) {
        return subscriptionRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Subscription> findSubscriptionsByStatus(Subscription.Status status) {
        return subscriptionRepository.findByStatus(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findPaymentById(String id) {
        return paymentRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Payment> findPaymentsBySubscription(String subscriptionId) {
        return paymentRepository.findBySubscriptionId(subscriptionId);
    }
    
    @Override
    public Payment markPaymentSucceeded(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));
        
        boolean succeeded = payment.markSucceeded();
        if (!succeeded) {
            throw new IllegalStateException("Failed to mark payment as succeeded. Payment must be pending.");
        }
        
        return paymentRepository.save(payment);
    }
    
    @Override
    public Payment markPaymentFailed(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));
        
        boolean failed = payment.markFailed();
        if (!failed) {
            throw new IllegalStateException("Failed to mark payment as failed. Payment must be pending.");
        }
        
        return paymentRepository.save(payment);
    }
    
    @Override
    public Payment initiateRefund(String paymentId, double amount) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));
        
        boolean initiated = payment.initiateRefund(amount);
        if (!initiated) {
            throw new IllegalStateException("Failed to initiate refund. Check payment status and amount.");
        }
        
        return paymentRepository.save(payment);
    }
    
    @Override
    public Subscription chargeSuccess(String subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));
        
        boolean charged = subscription.chargeSuccess();
        if (!charged) {
            throw new IllegalStateException("Failed to charge subscription. Check subscription status and payment.");
        }
        
        return subscriptionRepository.save(subscription);
    }
    
    @Override
    public Subscription chargeFail(String subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));
        
        boolean failed = subscription.chargeFail();
        if (!failed) {
            throw new IllegalStateException("Failed to mark charge as failed. Subscription must be active.");
        }
        
        return subscriptionRepository.save(subscription);
    }
    
    @Override
    public Subscription cancelSubscription(String subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));
        
        boolean cancelled = subscription.cancel();
        if (!cancelled) {
            throw new IllegalStateException("Failed to cancel subscription.");
        }
        
        return subscriptionRepository.save(subscription);
    }
    
    @Override
    public Subscription graceExpire(String subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));
        
        boolean expired = subscription.graceExpire();
        if (!expired) {
            throw new IllegalStateException("Failed to expire grace period. Subscription must be past due.");
        }
        
        return subscriptionRepository.save(subscription);
    }
}

