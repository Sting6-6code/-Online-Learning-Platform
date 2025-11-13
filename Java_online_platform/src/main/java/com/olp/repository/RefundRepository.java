package com.olp.repository;

import com.olp.model.payment.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Task 6.8: RefundRepository 接口
 * Repository 接口用于 Refund 实体的数据访问
 */
@Repository
public interface RefundRepository extends JpaRepository<Refund, String> {
    
    /**
     * 通过支付 ID 查找退款记录
     * @param paymentId 支付 ID
     * @return List<Refund>
     */
    @Query("SELECT r FROM Refund r WHERE r.payment.id = :paymentId")
    List<Refund> findByPaymentId(@Param("paymentId") String paymentId);
}

