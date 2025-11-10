//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__SUBSCRIPTION_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Subscription.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Subscription.h>
#include <Payment.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>
#include <cassert>
#include <string>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Subscription::Subscription(const string aId, PlanType aPlan, tm* aStartAt, tm* aNextBillingAt){
  // Validate startAt is not null
  if (aStartAt == nullptr) {
    throw std::invalid_argument("Subscription startAt cannot be null");
  }
  
  this->id= aId;
  this->plan= aPlan;
  this->startAt= aStartAt;
  
  // Auto-calculate nextBillingAt based on plan if not provided
  if (aNextBillingAt == nullptr) {
    tm* calculatedNext = new tm(*aStartAt);
    time_t startTime = mktime(calculatedNext);
    
    switch(aPlan) {
      case PlanType::Trial:
        startTime += 7 * 24 * 60 * 60; // 7 days
        break;
      case PlanType::Monthly:
        startTime += 30 * 24 * 60 * 60; // 30 days
        break;
      case PlanType::Annual:
        startTime += 365 * 24 * 60 * 60; // 365 days
        break;
    }
    
    localtime_r(&startTime, calculatedNext);
    this->nextBillingAt = calculatedNext;
  } else {
    this->nextBillingAt = aNextBillingAt;
  }
  
  this->subscriptionPayments= new vector<Payment*>();
  
  // Set initial status based on plan
  if (aPlan == PlanType::Trial) {
    setStatus(Subscription_ENUM_Status::Trial);
  } else {
    setStatus(Subscription_ENUM_Status::Active);
  }
}

Subscription::Subscription(Subscription& other){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Subscription::toOstream(ostream& os) const{
  Subscription* thisptr = const_cast<Subscription*>(this);
  os << "[" << "id:" << thisptr->getId() << "]" << endl;
  os << "plan:" << (int)thisptr->getPlan() << endl;
  (thisptr->getStartAt()!= NULL ?  os << "startAt:" << thisptr->getStartAt() : os << "startAt:" << "NULL")<< endl;
      (thisptr->getNextBillingAt()!= NULL ?  os << "nextBillingAt:" << thisptr->getNextBillingAt() : os << "nextBillingAt:" << "NULL")<< endl;
      (thisptr->getSubscriptionPayments()!= NULL ?  os << "subscriptionPayments:" << thisptr->getSubscriptionPayments() : os << "subscriptionPayments:" << "NULL");  
}

ostream& operator<<(ostream& os, const Subscription& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Subscription& Right, Subscription& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(Right.id.compare(Left.id) != 0){
     return false;
  }
  if(!compare(Right.plan, Left.plan, sizeof Right.plan)){
    return false;
  }
  if(!compare(Right.startAt, Left.startAt, sizeof Right.startAt)){
    return false;
  }
  if(!compare(Right.nextBillingAt, Left.nextBillingAt, sizeof Right.nextBillingAt)){
    return false;
  }
  if(!std::equal(Right.subscriptionPayments->begin(), Right.subscriptionPayments->end(), Left.subscriptionPayments->begin())){
     return false;
  }  
}

Subscription& Subscription::operator=(Subscription& other){
  internalCopy(other);
  return *this;  
}

void Subscription::internalCopy(Subscription& other){
  this->id.assign(other.id);
  this->plan = other.plan;  // Direct assignment for enum class
  copyObject(other.startAt, this->startAt, sizeof other.startAt);
  copyObject(other.nextBillingAt, this->nextBillingAt, sizeof other.nextBillingAt);
  this->subscriptionPayments = new vector<Payment*>();
  vector<Payment*>::iterator subscriptionPaymentsBeginIterator(other.subscriptionPayments->begin());
  vector<Payment*>::iterator subscriptionPaymentsEndIterator(other.subscriptionPayments->end());
  copyVector(subscriptionPaymentsBeginIterator, subscriptionPaymentsEndIterator, this->subscriptionPayments);  
}

bool Subscription::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Subscription::setPlan(PlanType aNewPlan){
  bool wasSet= false;
  this->plan = aNewPlan;
  wasSet= true;
  return wasSet;  
}

bool Subscription::setStartAt(tm* aNewStartAt){
  bool wasSet= false;
  this->startAt = aNewStartAt;
  wasSet= true;
  return wasSet;  
}

bool Subscription::setNextBillingAt(tm* aNewNextBillingAt){
  bool wasSet= false;
  this->nextBillingAt = aNewNextBillingAt;
  wasSet= true;
  return wasSet;  
}

string Subscription::getId(void){
  return this->id;  
}

PlanType Subscription::getPlan(void){
  return this->plan;  
}

tm* Subscription::getStartAt(void){
  return this->startAt;  
}

tm* Subscription::getNextBillingAt(void){
  return this->nextBillingAt;  
}

vector<Payment*>* Subscription::getSubscriptionPayments(void){
  vector<Payment*>* copyOfSubscriptionPayments = new vector<Payment*>();
  vector<Payment*>::iterator subscriptionPaymentsBeginIterator(this->subscriptionPayments->begin());
  vector<Payment*>::iterator subscriptionPaymentsEndIterator(this->subscriptionPayments->end());
  copyVector(subscriptionPaymentsBeginIterator, subscriptionPaymentsEndIterator, copyOfSubscriptionPayments);
  return copyOfSubscriptionPayments;  
}

Payment* Subscription::getSubscriptionPayment(int index){
  Payment* aSubscriptionPayment= (*(this->subscriptionPayments))[index];
  return aSubscriptionPayment;  
}

unsigned int Subscription::numberOfSubscriptionPayments(void){
  int number=this->subscriptionPayments->size();
  return number;  
}

bool Subscription::hasSubscriptionPayment(void){
  return this->numberOfSubscriptionPayments()>0;  
}

int Subscription::indexOfSubscriptionPayment(Payment* aSubscriptionPayment){
  return IndexOf(this->subscriptionPayments->begin(), this->subscriptionPayments->end(), aSubscriptionPayment);  
}

bool Subscription::removeSubscriptionPayment(Payment* aSubscriptionPayment){
  bool wasRemoved= false;
  int index= IndexOf(this->subscriptionPayments->begin(), this->subscriptionPayments->end(), aSubscriptionPayment);
  if(index<0){ 
    return wasRemoved;
  }
  //Unable to remove aSubscriptionPayment, as it must always have a Subscription
  if (this== aSubscriptionPayment->getSubscription()){
      return wasRemoved;
  }
  wasRemoved= this->removeSubscriptionPaymentAt(index);
  return wasRemoved;  
}

bool Subscription::removeSubscriptionPaymentAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfSubscriptionPayments();
  if(index> size){
      return false;
  }
  this->subscriptionPayments->erase(this->subscriptionPayments->begin()+index);
  wasRemoved= size== this->numberOfSubscriptionPayments()+1;
  return wasRemoved;  
}

bool Subscription::addSubscriptionPayment(Payment* aNewSubscriptionPayment){
  bool wasAdded= false;
  if (IndexOf(this->subscriptionPayments->begin(), this->subscriptionPayments->end(), aNewSubscriptionPayment)> -1) {
    return wasAdded;
  }
  Subscription* existingSubscription = aNewSubscriptionPayment->getSubscription();
  bool isNewSubscription= existingSubscription != NULL && this!=existingSubscription;
  if(isNewSubscription){
    aNewSubscriptionPayment->setSubscription(this);
  }else{
    this->subscriptionPayments->push_back(aNewSubscriptionPayment);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Subscription::addSubscriptionPaymentAt(Payment* aNewSubscriptionPayment, int index){
  bool wasAdded= false;
  if(addSubscriptionPayment(aNewSubscriptionPayment)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfSubscriptionPayments();
      if(size>0){
          this->subscriptionPayments->erase(this->subscriptionPayments->begin()+IndexOf(this->subscriptionPayments->begin(), this->subscriptionPayments->end(),aNewSubscriptionPayment));    
      }
    
      size= this->numberOfSubscriptionPayments();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->subscriptionPayments->insert(this->subscriptionPayments->begin()+ newIndex, aNewSubscriptionPayment);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Subscription::addOrMoveSubscriptionPaymentAt(Payment* aNewSubscriptionPayment, int index){
  bool wasAdded= false;
  if(IndexOf(this->subscriptionPayments->begin(), this->subscriptionPayments->end(), aNewSubscriptionPayment) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfSubscriptionPayments();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeSubscriptionPayment(aNewSubscriptionPayment)| this->addSubscriptionPaymentAt(aNewSubscriptionPayment, index);
  }else{
    wasAdded= this->addSubscriptionPaymentAt(aNewSubscriptionPayment, index);
  }
  
  return wasAdded;  
}

Payment* Subscription::addSubscriptionPayment(const string aId, double aAmount, PaymentStatus aStatus, tm* aPaidAt){
  return new Payment(aId, aAmount, aStatus, aPaidAt, this);  
}

bool Subscription::chargeSuccess(){
  bool wasEventProcessed= false;
  Subscription_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Subscription_ENUM_Status::Trial:
  		setStatus(Subscription_ENUM_Status::Active);
  		wasEventProcessed = true;
  	    break;
  	
  	
  	case Subscription_ENUM_Status::Active:
  		setStatus(Subscription_ENUM_Status::Active);
  		wasEventProcessed = true;
  	    break;
  	
  	
  	case Subscription_ENUM_Status::PastDue:
  		setStatus(Subscription_ENUM_Status::Active);
  		wasEventProcessed = true;
  	    break;

      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Subscription::chargeFail(){
  bool wasEventProcessed= false;
  Subscription_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Subscription_ENUM_Status::Active:
  		setStatus(Subscription_ENUM_Status::PastDue);
  		wasEventProcessed = true;
  	    break;

      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Subscription::cancel(){
  bool wasEventProcessed= false;
  Subscription_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Subscription_ENUM_Status::Active:
  		setStatus(Subscription_ENUM_Status::Cancelled);
  		wasEventProcessed = true;
  	    break;
  	
  	
  	case Subscription_ENUM_Status::PastDue:
  		setStatus(Subscription_ENUM_Status::Cancelled);
  		wasEventProcessed = true;
  	    break;
  	
  	
  	case Subscription_ENUM_Status::Suspended:
  		setStatus(Subscription_ENUM_Status::Cancelled);
  		wasEventProcessed = true;
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Subscription::graceExpire(){
  bool wasEventProcessed= false;
  Subscription_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Subscription_ENUM_Status::PastDue:
  		setStatus(Subscription_ENUM_Status::Suspended);
  		wasEventProcessed = true;
  	    break;

      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

string Subscription::getStatusFullName(void){
  string answer = status;
  return answer;
    
}

Subscription_ENUM_Status Subscription::getStatus(void){
  return this->status;  
}

void Subscription::setStatus(Subscription_ENUM_Status aStatus){
  status= aStatus;
    
}

bool Subscription::exitStatus(void){
  bool wasEventProcessed = false;
  
    
  switch((int)status){	
  	case Subscription_ENUM_Status::Trial:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Subscription_ENUM_Status::Active:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Subscription_ENUM_Status::PastDue:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Subscription_ENUM_Status::Suspended:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Subscription_ENUM_Status::Cancelled:
  		wasEventProcessed= true;
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;
    
}

size_t Subscription::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Subscription::~Subscription(){
  this->deleteAssociatedObjects();
  // plan is an enum class, not a pointer, no need to delete
  delete startAt;
  delete nextBillingAt;
  
  std::for_each(subscriptionPayments->begin(), subscriptionPayments->end(), /*std::default_delete<Payment>*/ deleteElement());
  subscriptionPayments->clear();
  delete subscriptionPayments;  
}

void Subscription::deleteAssociatedObjects(void){
  for(unsigned int index = subscriptionPayments->size(); index > 0 ; index--){
    Payment* aPayment= (*(this->subscriptionPayments))[index-1];
    aPayment->deleteAssociatedObjects();
  }  
}
;

