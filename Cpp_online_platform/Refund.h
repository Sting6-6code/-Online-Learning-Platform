//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__REFUND_H
#define DEF__REFUND_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Refund.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Refund.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>

class Payment;
// line 215 "model.ump"
// line 295 "model.ump"
class Refund{
	
	private:
		
		//Refund Attributes
		string id;
		double amount;
		tm* requestedAt;
		tm* processedAt;
		//Refund Associations
		// line 212 "model.ump"
		Payment* payment;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Refund(const string aId, const double aAmount, tm* aRequestedAt, tm* aProcessedAt);
		Refund(Refund& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Refund& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Refund& Right, Refund& Left);
		friend bool operator != (Refund& Right, Refund& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Refund& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Refund& Right) const{
		  return this != &Right;  
		}
		Refund& operator=(Refund& other);
		
		void internalCopy(Refund& other);
		bool setId(const string aNewId);
		bool setAmount(double aNewAmount);
		bool setRequestedAt(tm* aNewRequestedAt);
		bool setProcessedAt(tm* aNewProcessedAt);
		bool setPayment(Payment* aNewPayment);
		string getId(void);
		double getAmount(void);
		tm* getRequestedAt(void);
		tm* getProcessedAt(void);
		Payment* getPayment(void);
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Refund();
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
    template<> struct hash<Refund *> {
        size_t operator()(Refund* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif