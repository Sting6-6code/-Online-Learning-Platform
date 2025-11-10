/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.sql.Date;

// line 215 "model.ump"
// line 295 "model.ump"
public class Refund
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Refund Attributes
  private String id;
  private double amount;
  private Date requestedAt;
  private Date processedAt;

  //Refund Associations
  private Payment payment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

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
  public Payment getPayment()
  {
    return payment;
  }

  public boolean hasPayment()
  {
    boolean has = payment != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPayment(Payment aNewPayment)
  {
    boolean wasSet = false;
    if (aNewPayment == null)
    {
      Payment existingPayment = payment;
      payment = null;
      
      if (existingPayment != null && existingPayment.getPaymentRefund() != null)
      {
        existingPayment.setPaymentRefund(null);
      }
      wasSet = true;
      return wasSet;
    }

    Payment currentPayment = getPayment();
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