//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__SUBSCRIPTION_H
#define DEF__SUBSCRIPTION_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Subscription.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Subscription.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>
#include <cassert>
#include <string>

Subscription_ENUM_BEGIN
class Status_{

  public:
    typedef enum{Trial=0, Active=1, PastDue=2, Suspended=3, Cancelled=4} enum_type;
    
    Status_(enum_type val = Trial): _val(val){
        assert(val <= Cancelled);
        }
    
        operator enum_type() const {
            return _val;
        }
        
        operator int() { 
              return static_cast<int>(_val); 
      }
      
      operator string() { 
        switch (_val){
          case Trial: return "Trial";
          case Active: return "Active";
          case PastDue: return "PastDue";
          case Suspended: return "Suspended";
          case Cancelled: return "Cancelled";        
          default:  return "[Unknown ENUM Type]";
      }
    }
    
  private:
      enum_type _val;
};
Subscription_ENUM_END
class Payment;
/*
 * ===== ????????? =====
 */
// line 176 "model.ump"
// line 285 "model.ump"
class Subscription{
	
	private:
		
		//Subscription Attributes
		string id;
		PlanType plan;
		tm* startAt;
		tm* nextBillingAt;
		//Subscription Associations
		// line 182 "model.ump"
		vector<Payment*>* subscriptionPayments;
		//Subscription State Machines
		Subscription_ENUM_Status status;
		
		void setStatus(Subscription_ENUM_Status aStatus);
		bool exitStatus(void);
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Subscription(const string aId, PlanType aPlan, tm* aStartAt, tm* aNextBillingAt);
		Subscription(Subscription& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Subscription& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Subscription& Right, Subscription& Left);
		friend bool operator != (Subscription& Right, Subscription& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Subscription& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Subscription& Right) const{
		  return this != &Right;  
		}
		Subscription& operator=(Subscription& other);
		
		void internalCopy(Subscription& other);
		bool setId(const string aNewId);
		bool setPlan(PlanType aNewPlan);
		bool setStartAt(tm* aNewStartAt);
		bool setNextBillingAt(tm* aNewNextBillingAt);
		string getId(void);
		PlanType getPlan(void);
		tm* getStartAt(void);
		tm* getNextBillingAt(void);
		vector<Payment*>* getSubscriptionPayments(void);
		Payment* getSubscriptionPayment(int index);
		unsigned int numberOfSubscriptionPayments(void);
		bool hasSubscriptionPayment(void);
		int indexOfSubscriptionPayment(Payment* aSubscriptionPayment);
		bool removeSubscriptionPayment(Payment* aSubscriptionPayment);
		bool removeSubscriptionPaymentAt(int index);
		bool addSubscriptionPayment(Payment* aNewSubscriptionPayment);
		bool addSubscriptionPaymentAt(Payment* aNewSubscriptionPayment, int index);
		bool addOrMoveSubscriptionPaymentAt(Payment* aNewSubscriptionPayment, int index);
		Payment* addSubscriptionPayment(const string aId, double aAmount, PaymentStatus aStatus, tm* aPaidAt);
		bool chargeSuccess();
		bool chargeFail();
		bool cancel();
		bool graceExpire();
		string getStatusFullName(void);
		Subscription_ENUM_Status getStatus(void);
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Subscription();
		void deleteAssociatedObjects(void);
	
	protected:
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		virtual void toOstream(ostream& os) const;
};


//------------------------
//HASH FUNCTION USE
//------------------------
#include <unordered_map>
namespace std{
    template<> struct hash<Subscription *> {
        size_t operator()(Subscription* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif