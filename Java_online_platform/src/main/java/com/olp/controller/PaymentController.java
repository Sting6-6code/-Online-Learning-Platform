package com.olp.controller;

import com.olp.model.payment.Payment;
import com.olp.model.payment.Subscription;
import com.olp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Task 6.9: PaymentController REST API
 * 处理支付和订阅相关的 HTTP 请求
 */
@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    /**
     * 获取所有订阅
     * GET /api/payments/subscriptions
     */
    @GetMapping("/subscriptions")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = paymentService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }
    
    /**
     * 获取订阅详情
     * GET /api/payments/subscriptions/{id}
     */
    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable String id) {
        return paymentService.findSubscriptionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 通过状态获取订阅
     * GET /api/payments/subscriptions/status/{status}
     */
    @GetMapping("/subscriptions/status/{status}")
    public ResponseEntity<List<Subscription>> getSubscriptionsByStatus(@PathVariable String status) {
        try {
            Subscription.Status subscriptionStatus = Subscription.Status.valueOf(status);
            List<Subscription> subscriptions = paymentService.findSubscriptionsByStatus(subscriptionStatus);
            return ResponseEntity.ok(subscriptions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 获取支付详情
     * GET /api/payments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String id) {
        return paymentService.findPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 通过订阅 ID 获取支付
     * GET /api/payments/subscriptions/{subscriptionId}/payments
     */
    @GetMapping("/subscriptions/{subscriptionId}/payments")
    public ResponseEntity<List<Payment>> getPaymentsBySubscription(@PathVariable String subscriptionId) {
        List<Payment> payments = paymentService.findPaymentsBySubscription(subscriptionId);
        return ResponseEntity.ok(payments);
    }
    
    /**
     * 标记支付成功
     * POST /api/payments/{id}/succeed
     */
    @PostMapping("/{id}/succeed")
    public ResponseEntity<Payment> markPaymentSucceeded(@PathVariable String id) {
        try {
            Payment payment = paymentService.markPaymentSucceeded(id);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 标记支付失败
     * POST /api/payments/{id}/fail
     */
    @PostMapping("/{id}/fail")
    public ResponseEntity<Payment> markPaymentFailed(@PathVariable String id) {
        try {
            Payment payment = paymentService.markPaymentFailed(id);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 发起退款
     * POST /api/payments/{id}/refund
     * Body: {"amount": 50.0}
     */
    @PostMapping("/{id}/refund")
    public ResponseEntity<Payment> initiateRefund(@PathVariable String id, @RequestBody Map<String, Object> request) {
        try {
            double amount = ((Number) request.get("amount")).doubleValue();
            Payment payment = paymentService.initiateRefund(id, amount);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 订阅计费成功
     * POST /api/payments/subscriptions/{id}/charge-success
     */
    @PostMapping("/subscriptions/{id}/charge-success")
    public ResponseEntity<Subscription> chargeSuccess(@PathVariable String id) {
        try {
            Subscription subscription = paymentService.chargeSuccess(id);
            return ResponseEntity.ok(subscription);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 订阅计费失败
     * POST /api/payments/subscriptions/{id}/charge-fail
     */
    @PostMapping("/subscriptions/{id}/charge-fail")
    public ResponseEntity<Subscription> chargeFail(@PathVariable String id) {
        try {
            Subscription subscription = paymentService.chargeFail(id);
            return ResponseEntity.ok(subscription);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 取消订阅
     * POST /api/payments/subscriptions/{id}/cancel
     */
    @PostMapping("/subscriptions/{id}/cancel")
    public ResponseEntity<Subscription> cancelSubscription(@PathVariable String id) {
        try {
            Subscription subscription = paymentService.cancelSubscription(id);
            return ResponseEntity.ok(subscription);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 宽限期到期
     * POST /api/payments/subscriptions/{id}/grace-expire
     */
    @PostMapping("/subscriptions/{id}/grace-expire")
    public ResponseEntity<Subscription> graceExpire(@PathVariable String id) {
        try {
            Subscription subscription = paymentService.graceExpire(id);
            return ResponseEntity.ok(subscription);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

