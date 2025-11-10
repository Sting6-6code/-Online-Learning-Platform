/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.sql.Date;
import java.util.*;

/**
 * ===== ????????? =====
 */
// line 176 "model.ump"
// line 285 "model.ump"
public class Subscription
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PaymentStatus { Succeeded, Failed, Refunding, Refunded }
  public enum PlanType { Trial, Monthly, Annual }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Subscription Attributes
  private String id;
  private PlanType plan;
  private Date startAt;
  private Date nextBillingAt;

  //Subscription State Machines
  public enum Status { Trial, Active, PastDue, Suspended, Cancelled }
  private Status status;

  //Subscription Associations
  private List<Payment> subscriptionPayments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Subscription(String aId, PlanType aPlan, Date aStartAt, Date aNextBillingAt)
  {
    id = aId;
    plan = aPlan;
    startAt = aStartAt;
    nextBillingAt = aNextBillingAt;
    subscriptionPayments = new ArrayList<Payment>();
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
  public Payment getSubscriptionPayment(int index)
  {
    Payment aSubscriptionPayment = subscriptionPayments.get(index);
    return aSubscriptionPayment;
  }

  public List<Payment> getSubscriptionPayments()
  {
    List<Payment> newSubscriptionPayments = Collections.unmodifiableList(subscriptionPayments);
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

  public int indexOfSubscriptionPayment(Payment aSubscriptionPayment)
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
  public Payment addSubscriptionPayment(String aId, double aAmount, PaymentStatus aStatus, Date aPaidAt)
  {
    return new Payment(aId, aAmount, aStatus, aPaidAt, this);
  }

  public boolean addSubscriptionPayment(Payment aSubscriptionPayment)
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

  public boolean removeSubscriptionPayment(Payment aSubscriptionPayment)
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
  public boolean addSubscriptionPaymentAt(Payment aSubscriptionPayment, int index)
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

  public boolean addOrMoveSubscriptionPaymentAt(Payment aSubscriptionPayment, int index)
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
      Payment aSubscriptionPayment = subscriptionPayments.get(i - 1);
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