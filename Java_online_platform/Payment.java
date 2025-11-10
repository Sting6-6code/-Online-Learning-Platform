/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.sql.Date;

// line 206 "model.ump"
// line 290 "model.ump"
public class Payment
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PaymentStatus { Succeeded, Failed, Refunding, Refunded }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Payment Attributes
  private String id;
  private double amount;
  private PaymentStatus status;
  private Date paidAt;

  //Payment Associations
  private Refund paymentRefund;
  private Subscription subscription;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Payment(String aId, double aAmount, PaymentStatus aStatus, Date aPaidAt, Subscription aSubscription)
  {
    id = aId;
    amount = aAmount;
    status = aStatus;
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
  public Refund getPaymentRefund()
  {
    return paymentRefund;
  }

  public boolean hasPaymentRefund()
  {
    boolean has = paymentRefund != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Subscription getSubscription()
  {
    return subscription;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPaymentRefund(Refund aNewPaymentRefund)
  {
    boolean wasSet = false;
    if (aNewPaymentRefund == null)
    {
      Refund existingPaymentRefund = paymentRefund;
      paymentRefund = null;
      
      if (existingPaymentRefund != null && existingPaymentRefund.getPayment() != null)
      {
        existingPaymentRefund.setPayment(null);
      }
      wasSet = true;
      return wasSet;
    }

    Refund currentPaymentRefund = getPaymentRefund();
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
  public boolean setSubscription(Subscription aSubscription)
  {
    boolean wasSet = false;
    if (aSubscription == null)
    {
      return wasSet;
    }

    Subscription existingSubscription = subscription;
    subscription = aSubscription;
    if (existingSubscription != null && !existingSubscription.equals(aSubscription))
    {
      existingSubscription.removeSubscriptionPayment(this);
    }
    subscription.addSubscriptionPayment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (paymentRefund != null)
    {
      paymentRefund.setPayment(null);
    }
    Subscription placeholderSubscription = subscription;
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