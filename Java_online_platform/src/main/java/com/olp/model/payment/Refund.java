/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package com.olp.model.payment;

import java.sql.Date;
import javax.persistence.*;

// line 215 "model.ump"
// line 295 "model.ump"
@Entity
@Table(name = "refunds")
public class Refund
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Refund Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "amount", nullable = false)
  private double amount;
  
  @Column(name = "requested_at", nullable = false)
  private Date requestedAt;
  
  @Column(name = "processed_at")
  private Date processedAt;

  //Refund Associations
  @OneToOne
  @JoinColumn(name = "payment_id", nullable = false, unique = true)
  private com.olp.model.payment.Payment payment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Task 4.9: 修改构造函数，添加验证
   */
  public Refund(String aId, double aAmount, Date aRequestedAt, Date aProcessedAt, com.olp.model.payment.Payment aPayment)
  {
    // Task 4.9: 验证 amount > 0
    if (aAmount <= 0) {
      throw new IllegalArgumentException("Refund amount must be greater than 0");
    }
    
    // Task 4.9: 验证 payment 不为 null
    if (aPayment == null) {
      throw new IllegalArgumentException("Refund payment cannot be null");
    }
    
    // Task 4.9: 验证 payment.getStatus() == PaymentStatus.Succeeded（OCL 约束：RefundOnlyForSucceededPayment）
    if (aPayment.getStatus() != Payment.PaymentStatus.Succeeded) {
      throw new IllegalArgumentException("Refund can only be created for Succeeded payment, but payment status is: " + aPayment.getStatus());
    }
    
    id = aId;
    amount = aAmount;
    requestedAt = aRequestedAt;
    processedAt = aProcessedAt;
    boolean didSetPayment = setPayment(aPayment);
    if (!didSetPayment) {
      throw new RuntimeException("Unable to create refund due to payment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  
  // 保留原构造函数以兼容现有代码（不推荐使用，但为了兼容性保留）
  @Deprecated
  public Refund(String aId, double aAmount, Date aRequestedAt, Date aProcessedAt)
  {
    id = aId;
    amount = aAmount;
    requestedAt = aRequestedAt;
    processedAt = aProcessedAt;
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

  public boolean setRequestedAt(Date aRequestedAt)
  {
    boolean wasSet = false;
    requestedAt = aRequestedAt;
    wasSet = true;
    return wasSet;
  }

  public boolean setProcessedAt(Date aProcessedAt)
  {
    boolean wasSet = false;
    processedAt = aProcessedAt;
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

  public Date getRequestedAt()
  {
    return requestedAt;
  }

  public Date getProcessedAt()
  {
    return processedAt;
  }
  /* Code from template association_GetOne */
  public com.olp.model.payment.Payment getPayment()
  {
    return payment;
  }

  public boolean hasPayment()
  {
    boolean has = payment != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPayment(com.olp.model.payment.Payment aNewPayment)
  {
    boolean wasSet = false;
    if (aNewPayment == null)
    {
      com.olp.model.payment.Payment existingPayment = payment;
      payment = null;
      
      if (existingPayment != null && existingPayment.getPaymentRefund() != null)
      {
        existingPayment.setPaymentRefund(null);
      }
      wasSet = true;
      return wasSet;
    }

    com.olp.model.payment.Payment currentPayment = getPayment();
    if (currentPayment != null && !currentPayment.equals(aNewPayment))
    {
      currentPayment.setPaymentRefund(null);
    }

    payment = aNewPayment;
    Refund existingPaymentRefund = aNewPayment.getPaymentRefund();

    if (!equals(existingPaymentRefund))
    {
      aNewPayment.setPaymentRefund(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (payment != null)
    {
      payment.setPaymentRefund(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "amount" + ":" + getAmount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "requestedAt" + "=" + (getRequestedAt() != null ? !getRequestedAt().equals(this)  ? getRequestedAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "processedAt" + "=" + (getProcessedAt() != null ? !getProcessedAt().equals(this)  ? getProcessedAt().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "payment = "+(getPayment()!=null?Integer.toHexString(System.identityHashCode(getPayment())):"null");
  }
}