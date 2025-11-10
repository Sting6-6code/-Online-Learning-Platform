//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__REFUND_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Refund.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Refund.h>
#include <Payment.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Refund::Refund(const string aId, const double aAmount, tm* aRequestedAt, tm* aProcessedAt):
		payment(NULL){
  // Validate amount is positive
  if (aAmount <= 0) {
    throw std::invalid_argument("Refund amount must be greater than 0");
  }
  
  this->id= aId;
  this->amount= aAmount;
  this->requestedAt= aRequestedAt;
  this->processedAt= aProcessedAt;
  this->payment= NULL;  
}

Refund::Refund(Refund& other):
		payment(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Refund::toOstream(ostream& os) const{
  Refund* thisptr = const_cast<Refund*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "amount:" << thisptr->getAmount() << "]" << endl;
  (thisptr->getRequestedAt()!= NULL ?  os << "requestedAt:" << thisptr->getRequestedAt() : os << "requestedAt:" << "NULL")<< endl;
      (thisptr->getProcessedAt()!= NULL ?  os << "processedAt:" << thisptr->getProcessedAt() : os << "processedAt:" << "NULL")<< endl;
      (thisptr->getPayment()!= NULL ?  os << "payment:" << thisptr->getPayment() : os << "payment:" << "NULL");  
}

ostream& operator<<(ostream& os, const Refund& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Refund& Right, Refund& Left){
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
  if(!compare(Right.requestedAt, Left.requestedAt, sizeof Right.requestedAt)){
    return false;
  }
  if(!compare(Right.processedAt, Left.processedAt, sizeof Right.processedAt)){
    return false;
  }
  if(!compare(Right.payment, Left.payment, sizeof Right.payment)){
    return false;
  }  
}

Refund& Refund::operator=(Refund& other){
  internalCopy(other);
  return *this;  
}

void Refund::internalCopy(Refund& other){
  this->id.assign(other.id);
  this->amount= other.amount;
  copyObject(other.requestedAt, this->requestedAt, sizeof other.requestedAt);
  copyObject(other.processedAt, this->processedAt, sizeof other.processedAt);
  copyObject(other.payment, this->payment, sizeof other.payment);  
}

bool Refund::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Refund::setAmount(double aNewAmount){
  bool wasSet= false;
  this->amount = aNewAmount;
  wasSet= true;
  return wasSet;  
}

bool Refund::setRequestedAt(tm* aNewRequestedAt){
  bool wasSet= false;
  this->requestedAt = aNewRequestedAt;
  wasSet= true;
  return wasSet;  
}

bool Refund::setProcessedAt(tm* aNewProcessedAt){
  bool wasSet= false;
  this->processedAt = aNewProcessedAt;
  wasSet= true;
  return wasSet;  
}

bool Refund::setPayment(Payment* aNewPayment){
  bool wasSet= false;
  if (aNewPayment == NULL){
    Payment* existingPayment = this->payment;
    this->payment = NULL;
    
    if (existingPayment != NULL && existingPayment->getPaymentRefund() != NULL){
      existingPayment->setPaymentRefund(NULL);
    }
    wasSet = true;
    return wasSet;
  }
  
  Payment* currentPayment = getPayment();
  if (currentPayment != NULL && currentPayment!= aNewPayment){
    currentPayment->setPaymentRefund(NULL);
  }
  
  this->payment = aNewPayment;
  Refund* existingPaymentRefund = aNewPayment->getPaymentRefund();
  
  if (this!=existingPaymentRefund){
    aNewPayment->setPaymentRefund(this);
  }
  wasSet= true;
  return wasSet;  
}

string Refund::getId(void){
  return this->id;  
}

double Refund::getAmount(void){
  return this->amount;  
}

tm* Refund::getRequestedAt(void){
  return this->requestedAt;  
}

tm* Refund::getProcessedAt(void){
  return this->processedAt;  
}

Payment* Refund::getPayment(void){
  return this->payment;  
}

size_t Refund::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Refund::~Refund(){
  this->deleteAssociatedObjects();
  delete requestedAt;
  delete processedAt;
  delete payment;  
}

void Refund::deleteAssociatedObjects(void){
  if(this->payment!= NULL){
    this->payment->setPaymentRefund(NULL);
  }  
}
;

