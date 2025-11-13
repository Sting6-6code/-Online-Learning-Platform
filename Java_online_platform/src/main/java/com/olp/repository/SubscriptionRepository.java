package com.olp.repository;

import com.olp.model.payment.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Task 6.8: SubscriptionRepository 接口
 * Repository 接口用于 Subscription 实体的数据访问
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    
    /**
     * 通过订阅状态查找订阅
     * @param status 订阅状态
     * @return List<Subscription>
     */
    List<Subscription> findByStatus(Subscription.Status status);
    
    /**
     * 通过计划类型查找订阅
     * @param plan 计划类型
     * @return List<Subscription>
     */
    List<Subscription> findByPlan(Subscription.PlanType plan);
}

