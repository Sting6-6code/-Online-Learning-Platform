/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package com.olp.model.payment;

import java.sql.Date;
import javax.persistence.*;
import com.olp.util.Utils;

// line 206 "model.ump"
// line 290 "model.ump"
@Entity
@Table(name = "payments")
public class Payment
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  // Task 4.7: 添加 Pending 状态
  public enum PaymentStatus { Pending, Succeeded, Failed, Refunding, Refunded }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Payment Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "amount", nullable = false)
  private double amount;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 20, nullable = false)
  private PaymentStatus status;
  
  @Column(name = "paid_at", nullable = false)
  private Date paidAt;

  //Payment Associations
  @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
  private com.olp.model.payment.Refund paymentRefund;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subscription_id", nullable = false)
  private com.olp.model.payment.Subscription subscription;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Task 4.6: 添加构造函数验证
   */
  public Payment(String aId, double aAmount, PaymentStatus aStatus, Date aPaidAt, com.olp.model.payment.Subscription aSubscription)
  {
    // Task 4.6: 验证 amount > 0
    if (aAmount <= 0) {
      throw new IllegalArgumentException("Payment amount must be greater than 0");
    }
    
    // Task 4.6: 验证 subscription 不为 null
    if (aSubscription == null) {
      throw new IllegalArgumentException("Payment subscription cannot be null");
    }
    
    id = aId;
    amount = aAmount;
    status = aStatus; // Task 4.6: 初始状态为构造函数参数值
    paidAt = aPaidAt;
    boolean didAddSubscription = setSubscription(aSubscription);
    if (!didAddSubscription)
    {
      throw new RuntimeException("Unable to create subscriptionPayment due to subscription. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setAmount(double aAmount)
  {
    boolean wasSet = false;
    amount = aAmount;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(PaymentStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setPaidAt(Date aPaidAt)
  {
    boolean wasSet = false;
    paidAt = aPaidAt;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public double getAmount()
  {
    return amount;
  }

  public PaymentStatus getStatus()
  {
    return status;
  }

  public Date getPaidAt()
  {
    return paidAt;
  }
  /* Code from template association_GetOne */
  public com.olp.model.payment.Refund getPaymentRefund()
  {
    return paymentRefund;
  }

  public boolean hasPaymentRefund()
  {
    boolean has = paymentRefund != null;
    return has;
  }
  /* Code from template association_GetOne */
  public com.olp.model.payment.Subscription getSubscription()
  {
    return subscription;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPaymentRefund(com.olp.model.payment.Refund aNewPaymentRefund)
  {
    boolean wasSet = false;
    if (aNewPaymentRefund == null)
    {
      com.olp.model.payment.Refund existingPaymentRefund = paymentRefund;
      paymentRefund = null;
      
      if (existingPaymentRefund != null && existingPaymentRefund.getPayment() != null)
      {
        existingPaymentRefund.setPayment(null);
      }
      wasSet = true;
      return wasSet;
    }

    com.olp.model.payment.Refund currentPaymentRefund = getPaymentRefund();
    if (currentPaymentRefund != null && !currentPaymentRefund.equals(aNewPaymentRefund))
    {
      currentPaymentRefund.setPayment(null);
    }

    paymentRefund = aNewPaymentRefund;
    Payment existingPayment = aNewPaymentRefund.getPayment();

    if (!equals(existingPayment))
    {
      aNewPaymentRefund.setPayment(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSubscription(com.olp.model.payment.Subscription aSubscription)
  {
    boolean wasSet = false;
    if (aSubscription == null)
    {
      return wasSet;
    }

    com.olp.model.payment.Subscription existingSubscription = subscription;
    subscription = aSubscription;
    if (existingSubscription != null && !existingSubscription.equals(aSubscription))
    {
      existingSubscription.removeSubscriptionPayment(this);
    }
    subscription.addSubscriptionPayment(this);
    wasSet = true;
    return wasSet;
  }

  /**
   * Task 4.7: 实现 markSucceeded() 方法
   * 标记支付成功：从 Pending 状态转换到 Succeeded 状态
   */
  public boolean markSucceeded()
  {
    // 守卫条件：当前状态必须是 Pending
    if (status != PaymentStatus.Pending) {
      return false;
    }
    
    // 状态转换
    setStatus(PaymentStatus.Succeeded);
    
    // 设置 paidAt := Utils.getCurrentTime()
    setPaidAt(Utils.getCurrentTime());
    
    return true;
  }

  /**
   * Task 4.7: 实现 markFailed() 方法
   * 标记支付失败：从 Pending 状态转换到 Failed 状态
   */
  public boolean markFailed()
  {
    // 守卫条件：当前状态必须是 Pending
    if (status != PaymentStatus.Pending) {
      return false;
    }
    
    // 状态转换
    setStatus(PaymentStatus.Failed);
    
    return true;
  }

  /**
   * Task 4.8: 实现 initiateRefund() 方法
   * 发起退款：从 Succeeded 状态转换到 Refunding 状态，创建 Refund 对象
   */
  public boolean initiateRefund(double refundAmount)
  {
    // 守卫条件：当前状态必须是 Succeeded
    if (status != PaymentStatus.Succeeded) {
      return false;
    }
    
    // 守卫条件：0 < amount <= this.amount
    if (refundAmount <= 0 || refundAmount > this.amount) {
      return false;
    }
    
    // 先创建 Refund 对象（此时 Payment 状态还是 Succeeded，满足 Refund 构造函数要求）
    String refundId = Utils.generateId("REF");
    Date requestedAt = Utils.getCurrentTime();
    Date processedAt = null; // 初始为 null
    
    // Task 4.9: 使用新的构造函数（包含 payment 参数）
    // 注意：必须在状态转换之前创建 Refund，因为 Refund 构造函数要求 Payment 状态为 Succeeded
    Refund refund = new Refund(refundId, refundAmount, requestedAt, processedAt, this);
    setPaymentRefund(refund);
    
    // 状态转换（在创建 Refund 之后）
    setStatus(PaymentStatus.Refunding);
    
    return true;
  }

  public void delete()
  {
    if (paymentRefund != null)
    {
      paymentRefund.setPayment(null);
    }
    com.olp.model.payment.Subscription placeholderSubscription = subscription;
    this.subscription = null;
    if(placeholderSubscription != null)
    {
      placeholderSubscription.removeSubscriptionPayment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "amount" + ":" + getAmount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "paidAt" + "=" + (getPaidAt() != null ? !getPaidAt().equals(this)  ? getPaidAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "paymentRefund = "+(getPaymentRefund()!=null?Integer.toHexString(System.identityHashCode(getPaymentRefund())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "subscription = "+(getSubscription()!=null?Integer.toHexString(System.identityHashCode(getSubscription())):"null");
  }
}