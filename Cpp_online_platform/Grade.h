//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__GRADE_H
#define DEF__GRADE_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Grade.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Grade.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>

class Submission;
// line 169 "model.ump"
// line 280 "model.ump"
class Grade{
	
	private:
		
		//Grade Attributes
		string id;
		double score;
		string feedback;
		//Grade Associations
		// line 131 "model.ump"
		Submission* submission;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Grade(const string aId, const double aScore, const string aFeedback);
		Grade(Grade& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Grade& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Grade& Right, Grade& Left);
		friend bool operator != (Grade& Right, Grade& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Grade& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Grade& Right) const{
		  return this != &Right;  
		}
		Grade& operator=(Grade& other);
		
		void internalCopy(Grade& other);
		bool setId(const string aNewId);
		bool setScore(double aNewScore);
		bool setFeedback(const string aNewFeedback);
		bool setSubmission(Submission* aNewSubmission);
		string getId(void);
		double getScore(void);
		string getFeedback(void);
		Submission* getSubmission(void);
		
		// Convenience methods
		double getPercentage(void);
		
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Grade();
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
    template<> struct hash<Grade *> {
        size_t operator()(Grade* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif