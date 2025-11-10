//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__PAYMENT_H
#define DEF__PAYMENT_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Payment.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Payment.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>

class Refund;
class Subscription;
// line 206 "model.ump"
// line 290 "model.ump"
class Payment{
	
	private:
		
		//Payment Attributes
		string id;
		double amount;
		PaymentStatus status;
		tm* paidAt;
		//Payment Associations
		// line 212 "model.ump"
		Refund* paymentRefund;
		// line 182 "model.ump"
		Subscription* subscription;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Payment(const string aId, const double aAmount, PaymentStatus aStatus, tm* aPaidAt, Subscription* aSubscription);
		Payment(Payment& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Payment& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Payment& Right, Payment& Left);
		friend bool operator != (Payment& Right, Payment& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Payment& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Payment& Right) const{
		  return this != &Right;  
		}
		Payment& operator=(Payment& other);
		
		void internalCopy(Payment& other);
		bool setId(const string aNewId);
		bool setAmount(double aNewAmount);
		bool setStatus(PaymentStatus aNewStatus);
		bool setPaidAt(tm* aNewPaidAt);
		bool setPaymentRefund(Refund* aNewPaymentRefund);
		bool setSubscription(Subscription* aNewSubscription);
		inline unsigned int minimumNumberOfSubscriptions(void){
		  return 1;  
		}
		string getId(void);
		double getAmount(void);
		PaymentStatus getStatus(void);
		tm* getPaidAt(void);
		Refund* getPaymentRefund(void);
		Subscription* getSubscription(void);
		
		// Convenience and business methods
		bool isSuccessful(void);
		bool markSucceeded(void);
		bool markFailed(void);
		bool initiateRefund(double amount);
		bool completeRefund(void);
		
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Payment();
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
    template<> struct hash<Payment *> {
        size_t operator()(Payment* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif