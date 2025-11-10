//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__PAYMENT_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Payment.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Payment.h>
#include <Refund.h>
#include <Subscription.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <ctime>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Payment::Payment(const string aId, const double aAmount, PaymentStatus aStatus, tm* aPaidAt, Subscription* aSubscription):
		paymentRefund(NULL),
		subscription(NULL){
  // Validate amount is positive
  if (aAmount <= 0) {
    throw std::invalid_argument("Payment amount must be greater than 0");
  }
  
  // Validate subscription is not null
  if (aSubscription == nullptr) {
    throw std::invalid_argument("Payment subscription cannot be null");
  }
  
  this->id= aId;
  this->amount= aAmount;
  this->status= aStatus;
  this->paidAt= aPaidAt;
  this->paymentRefund= NULL;
  if (!setSubscription(aSubscription)){
    throw "Unable to create Payment due to aSubscription";
  }  
}

Payment::Payment(Payment& other):
		paymentRefund(NULL),
		subscription(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Payment::toOstream(ostream& os) const{
  Payment* thisptr = const_cast<Payment*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "amount:" << thisptr->getAmount() << "]" << endl;
  os << "status:" << (int)thisptr->getStatus() << endl;
  (thisptr->getPaidAt()!= NULL ?  os << "paidAt:" << thisptr->getPaidAt() : os << "paidAt:" << "NULL")<< endl;
      (thisptr->getPaymentRefund()!= NULL ?  os << "paymentRefund:" << thisptr->getPaymentRefund() : os << "paymentRefund:" << "NULL")<< endl;
      (thisptr->getSubscription()!= NULL ?  os << "subscription:" << thisptr->getSubscription() : os << "subscription:" << "NULL");  
}

ostream& operator<<(ostream& os, const Payment& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Payment& Right, Payment& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(Right.id.compare(Left.id) != 0){
     return false;
  }
  if(Right.amount!= Left.amount){
     return false;
  }
  if(!compare(Right.status, Left.status, sizeof Right.status)){
    return false;
  }
  if(!compare(Right.paidAt, Left.paidAt, sizeof Right.paidAt)){
    return false;
  }
  if(!compare(Right.paymentRefund, Left.paymentRefund, sizeof Right.paymentRefund)){
    return false;
  }
  if(!compare(Right.subscription, Left.subscription, sizeof Right.subscription)){
    return false;
  }  
}

Payment& Payment::operator=(Payment& other){
  internalCopy(other);
  return *this;  
}

void Payment::internalCopy(Payment& other){
  this->id.assign(other.id);
  this->amount= other.amount;
  this->status = other.status;  // Direct assignment for enum class
  copyObject(other.paidAt, this->paidAt, sizeof other.paidAt);
  copyObject(other.paymentRefund, this->paymentRefund, sizeof other.paymentRefund);
  copyObject(other.subscription, this->subscription, sizeof other.subscription);  
}

bool Payment::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Payment::setAmount(double aNewAmount){
  bool wasSet= false;
  this->amount = aNewAmount;
  wasSet= true;
  return wasSet;  
}

bool Payment::setStatus(PaymentStatus aNewStatus){
  bool wasSet= false;
  this->status = aNewStatus;
  wasSet= true;
  return wasSet;  
}

bool Payment::setPaidAt(tm* aNewPaidAt){
  bool wasSet= false;
  this->paidAt = aNewPaidAt;
  wasSet= true;
  return wasSet;  
}

bool Payment::setPaymentRefund(Refund* aNewPaymentRefund){
  bool wasSet= false;
  if (aNewPaymentRefund == NULL){
    Refund* existingPaymentRefund = this->paymentRefund;
    this->paymentRefund = NULL;
    
    if (existingPaymentRefund != NULL && existingPaymentRefund->getPayment() != NULL){
      existingPaymentRefund->setPayment(NULL);
    }
    wasSet = true;
    return wasSet;
  }
  
  Refund* currentPaymentRefund = getPaymentRefund();
  if (currentPaymentRefund != NULL && currentPaymentRefund!= aNewPaymentRefund){
    currentPaymentRefund->setPayment(NULL);
  }
  
  this->paymentRefund = aNewPaymentRefund;
  Payment* existingPayment = aNewPaymentRefund->getPayment();
  
  if (this!=existingPayment){
    aNewPaymentRefund->setPayment(this);
  }
  wasSet= true;
  return wasSet;  
}

bool Payment::setSubscription(Subscription* aNewSubscription){
  bool wasSet= false;
  if (aNewSubscription == NULL){
    return wasSet;
  }
  
  Subscription* existingSubscription = this->subscription;
  this->subscription = aNewSubscription;
  
  if (existingSubscription != NULL && existingSubscription != aNewSubscription){
      existingSubscription->removeSubscriptionPayment(this);
  }
  this->subscription->addSubscriptionPayment(this);
  wasSet = true;
  return wasSet;  
}

string Payment::getId(void){
  return this->id;  
}

double Payment::getAmount(void){
  return this->amount;  
}

PaymentStatus Payment::getStatus(void){
  return this->status;  
}

tm* Payment::getPaidAt(void){
  return this->paidAt;  
}

Refund* Payment::getPaymentRefund(void){
  return this->paymentRefund;  
}

Subscription* Payment::getSubscription(void){
  return this->subscription;  
}

size_t Payment::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Payment::~Payment(){
  this->deleteAssociatedObjects();
  // status is an enum class, not a pointer, no need to delete
  delete paidAt;
  delete paymentRefund;
  delete subscription;  
}

//------------------------
// Convenience and Business Methods
//------------------------

bool Payment::isSuccessful(void){
  return status == PaymentStatus::Succeeded;
}

bool Payment::markSucceeded(void){
  if (status != PaymentStatus::Pending) {
    return false;
  }
  
  // Set paidAt to current time
  paidAt = new tm();
  time_t now = time(nullptr);
  localtime_r(&now, paidAt);
  
  status = PaymentStatus::Succeeded;
  return true;
}

bool Payment::markFailed(void){
  if (status != PaymentStatus::Pending) {
    return false;
  }
  
  status = PaymentStatus::Failed;
  return true;
}

bool Payment::initiateRefund(double amount){
  // Guard: must be in Succeeded state
  if (status != PaymentStatus::Succeeded) {
    return false;
  }
  
  // Guard: amount must not exceed payment amount
  if (amount > this->amount) {
    return false;
  }
  
  // Guard: cannot refund twice
  if (paymentRefund != nullptr) {
    return false;
  }
  
  // Create Refund object
  string refundId = "REF_" + id;
  tm* requestedAt = new tm();
  time_t now = time(nullptr);
  localtime_r(&now, requestedAt);
  
  Refund* refund = new Refund(refundId, amount, requestedAt, nullptr);
  refund->setPayment(this);
  paymentRefund = refund;
  
  status = PaymentStatus::Refunding;
  return true;
}

bool Payment::completeRefund(void){
  // Guard: must be in Refunding state
  if (status != PaymentStatus::Refunding) {
    return false;
  }
  
  // Guard: must have a refund object
  if (paymentRefund == nullptr) {
    return false;
  }
  
  // Set processedAt
  tm* processedAt = new tm();
  time_t now = time(nullptr);
  localtime_r(&now, processedAt);
  paymentRefund->setProcessedAt(processedAt);
  
  status = PaymentStatus::Refunded;
  return true;
}

void Payment::deleteAssociatedObjects(void){
  if(this->paymentRefund!= NULL){
    this->paymentRefund->setPayment(NULL);
  }
  Subscription* placeholderSubscription = this->subscription;
  this->subscription = NULL;
  placeholderSubscription->removeSubscriptionPayment(this);  
}
;

