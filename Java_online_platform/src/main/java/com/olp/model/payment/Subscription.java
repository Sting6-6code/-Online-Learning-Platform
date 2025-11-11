/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


package com.olp.model.payment;

import java.sql.Date;
import java.util.*;
import javax.persistence.*;

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

  public Subscription(String aId, PlanType aPlan, Date aStartAt, Date aNextBillingAt)
  {
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

  public boolean chargeSuccess()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Trial:
        setStatus(Status.Active);
        wasEventProcessed = true;
        break;
      case Active:
        setStatus(Status.Active);
        wasEventProcessed = true;
        break;
      case PastDue:
        setStatus(Status.Active);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean chargeFail()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Active:
        setStatus(Status.PastDue);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Active:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case PastDue:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case Suspended:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean graceExpire()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case PastDue:
        setStatus(Status.Suspended);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
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