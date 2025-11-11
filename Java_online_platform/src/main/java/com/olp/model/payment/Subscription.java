/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package com.olp.model.payment;

import java.sql.Date;
import java.util.*;
import javax.persistence.*;
import com.olp.util.Utils;

/**
 * ===== ????????? =====
 * Task 0.7: 添加 JPA 注解
 */
// line 176 "model.ump"
// line 285 "model.ump"
@Entity
@Table(name = "subscriptions")
public class Subscription
{

  //------------------------
  // ENUMERATIONS
  //------------------------
  // Note: PaymentStatus is defined in Payment class
  public enum PlanType { Trial, Monthly, Annual }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Subscription Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "plan", length = 20, nullable = false)
  private PlanType plan;
  
  @Column(name = "start_at", nullable = false)
  private Date startAt;
  
  @Column(name = "next_billing_at", nullable = false)
  private Date nextBillingAt;

  //Subscription State Machines
  public enum Status { Trial, Active, PastDue, Suspended, Cancelled }
  
  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 20, nullable = false)
  private Status status;

  //Subscription Associations
  @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<com.olp.model.payment.Payment> subscriptionPayments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Task 4.1: 添加构造函数验证
   */
  public Subscription(String aId, PlanType aPlan, Date aStartAt, Date aNextBillingAt)
  {
    // Task 4.1: 验证 plan 不为 null
    if (aPlan == null) {
      throw new IllegalArgumentException("Subscription plan cannot be null");
    }
    
    // Task 4.1: 验证 startAt 不为 null
    if (aStartAt == null) {
      throw new IllegalArgumentException("Subscription startAt cannot be null");
    }
    
    // Task 4.1: nextBillingAt 可以为 null（初始创建时）
    
    id = aId;
    plan = aPlan;
    startAt = aStartAt;
    nextBillingAt = aNextBillingAt;
    subscriptionPayments = new ArrayList<com.olp.model.payment.Payment>();
    setStatus(Status.Trial);
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

  public boolean setPlan(PlanType aPlan)
  {
    boolean wasSet = false;
    plan = aPlan;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartAt(Date aStartAt)
  {
    boolean wasSet = false;
    startAt = aStartAt;
    wasSet = true;
    return wasSet;
  }

  public boolean setNextBillingAt(Date aNextBillingAt)
  {
    boolean wasSet = false;
    nextBillingAt = aNextBillingAt;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public PlanType getPlan()
  {
    return plan;
  }

  public Date getStartAt()
  {
    return startAt;
  }

  public Date getNextBillingAt()
  {
    return nextBillingAt;
  }

  public String getStatusFullName()
  {
    String answer = status.toString();
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  /**
   * Task 4.2: 实现 chargeSuccess() 方法
   * 计费成功：从 Trial/Active/PastDue 状态转换到 Active 状态
   */
  public boolean chargeSuccess()
  {
    // 守卫条件：当前状态必须是 Trial、Active 或 PastDue
    if (status != Status.Trial && status != Status.Active && status != Status.PastDue) {
      return false;
    }
    
    // 守卫条件：最近一笔 Payment 状态为 Succeeded
    Payment latestPayment = getLatestPayment();
    if (latestPayment == null || latestPayment.getStatus() != Payment.PaymentStatus.Succeeded) {
      return false;
    }
    
    // 状态转换
    setStatus(Status.Active);
    
    // 更新 nextBillingAt
    if (nextBillingAt != null) {
      if (plan == PlanType.Monthly) {
        nextBillingAt = Utils.addMonths(nextBillingAt, 1);
      } else if (plan == PlanType.Annual) {
        nextBillingAt = Utils.addMonths(nextBillingAt, 12);
      }
      // Trial 计划不更新 nextBillingAt
    }
    
    return true;
  }
  
  /**
   * Task 4.2: 获取最近一笔 Payment
   */
  private Payment getLatestPayment() {
    if (subscriptionPayments == null || subscriptionPayments.isEmpty()) {
      return null;
    }
    // 找到最近的一笔 Payment（按 paidAt 排序，取最新的）
    Payment latest = null;
    Date latestDate = null;
    for (Payment payment : subscriptionPayments) {
      if (payment.getPaidAt() != null) {
        if (latestDate == null || Utils.compareDates(payment.getPaidAt(), latestDate) > 0) {
          latest = payment;
          latestDate = payment.getPaidAt();
        }
      }
    }
    return latest;
  }

  /**
   * Task 4.3: 实现 chargeFail() 方法
   * 计费失败：从 Active 状态转换到 PastDue 状态
   */
  public boolean chargeFail()
  {
    // 守卫条件：当前状态必须是 Active
    if (status != Status.Active) {
      return false;
    }
    
    // 守卫条件：最近一笔 Payment 状态为 Failed
    Payment latestPayment = getLatestPayment();
    if (latestPayment == null || latestPayment.getStatus() != Payment.PaymentStatus.Failed) {
      return false;
    }
    
    // 状态转换
    setStatus(Status.PastDue);
    return true;
  }

  /**
   * Task 4.4: 实现 cancel() 方法
   * 取消订阅：从任意状态转换到 Cancelled 状态
   */
  public boolean cancel()
  {
    // 可以从任意状态取消（无守卫条件）
    // 状态转换
    setStatus(Status.Cancelled);
    return true;
  }

  /**
   * Task 4.5: 实现 graceExpire() 方法
   * 宽限期到期：从 PastDue 状态转换到 Suspended 状态
   */
  public boolean graceExpire()
  {
    // 守卫条件：当前状态必须是 PastDue
    if (status != Status.PastDue) {
      return false;
    }
    
    // 守卫条件：当前日期 > 宽限到期（宽限期为 7 天）
    if (nextBillingAt == null) {
      return false;
    }
    Date graceExpiryDate = Utils.addDays(nextBillingAt, 7);
    Date currentTime = Utils.getCurrentTime();
    if (Utils.compareDates(currentTime, graceExpiryDate) <= 0) {
      return false; // 宽限期未到期
    }
    
    // 状态转换
    setStatus(Status.Suspended);
    return true;
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;
  }
  /* Code from template association_GetMany */
  public com.olp.model.payment.Payment getSubscriptionPayment(int index)
  {
    com.olp.model.payment.Payment aSubscriptionPayment = subscriptionPayments.get(index);
    return aSubscriptionPayment;
  }

  public List<com.olp.model.payment.Payment> getSubscriptionPayments()
  {
    List<com.olp.model.payment.Payment> newSubscriptionPayments = Collections.unmodifiableList(subscriptionPayments);
    return newSubscriptionPayments;
  }

  public int numberOfSubscriptionPayments()
  {
    int number = subscriptionPayments.size();
    return number;
  }

  public boolean hasSubscriptionPayments()
  {
    boolean has = subscriptionPayments.size() > 0;
    return has;
  }

  public int indexOfSubscriptionPayment(com.olp.model.payment.Payment aSubscriptionPayment)
  {
    int index = subscriptionPayments.indexOf(aSubscriptionPayment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSubscriptionPayments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public com.olp.model.payment.Payment addSubscriptionPayment(String aId, double aAmount, Payment.PaymentStatus aStatus, Date aPaidAt)
  {
    return new com.olp.model.payment.Payment(aId, aAmount, aStatus, aPaidAt, this);
  }

  public boolean addSubscriptionPayment(com.olp.model.payment.Payment aSubscriptionPayment)
  {
    boolean wasAdded = false;
    if (subscriptionPayments.contains(aSubscriptionPayment)) { return false; }
    Subscription existingSubscription = aSubscriptionPayment.getSubscription();
    boolean isNewSubscription = existingSubscription != null && !this.equals(existingSubscription);
    if (isNewSubscription)
    {
      aSubscriptionPayment.setSubscription(this);
    }
    else
    {
      subscriptionPayments.add(aSubscriptionPayment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSubscriptionPayment(com.olp.model.payment.Payment aSubscriptionPayment)
  {
    boolean wasRemoved = false;
    //Unable to remove aSubscriptionPayment, as it must always have a subscription
    if (!this.equals(aSubscriptionPayment.getSubscription()))
    {
      subscriptionPayments.remove(aSubscriptionPayment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSubscriptionPaymentAt(com.olp.model.payment.Payment aSubscriptionPayment, int index)
  {  
    boolean wasAdded = false;
    if(addSubscriptionPayment(aSubscriptionPayment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSubscriptionPayments()) { index = numberOfSubscriptionPayments() - 1; }
      subscriptionPayments.remove(aSubscriptionPayment);
      subscriptionPayments.add(index, aSubscriptionPayment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSubscriptionPaymentAt(com.olp.model.payment.Payment aSubscriptionPayment, int index)
  {
    boolean wasAdded = false;
    if(subscriptionPayments.contains(aSubscriptionPayment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSubscriptionPayments()) { index = numberOfSubscriptionPayments() - 1; }
      subscriptionPayments.remove(aSubscriptionPayment);
      subscriptionPayments.add(index, aSubscriptionPayment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSubscriptionPaymentAt(aSubscriptionPayment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=subscriptionPayments.size(); i > 0; i--)
    {
      com.olp.model.payment.Payment aSubscriptionPayment = subscriptionPayments.get(i - 1);
      aSubscriptionPayment.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "plan" + "=" + (getPlan() != null ? !getPlan().equals(this)  ? getPlan().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startAt" + "=" + (getStartAt() != null ? !getStartAt().equals(this)  ? getStartAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "nextBillingAt" + "=" + (getNextBillingAt() != null ? !getNextBillingAt().equals(this)  ? getNextBillingAt().toString().replaceAll("  ","    ") : "this" : "null");
  }
}