//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__SUBMISSION_H
#define DEF__SUBMISSION_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Submission.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Submission.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <cassert>
#include <string>

Submission_ENUM_BEGIN
class Status_{

  public:
    typedef enum{Created=0, Submitted=1, UnderCheck=2, Grading=3, Graded=4, Returned=5, ResubmissionRequested=6} enum_type;
    
    Status_(enum_type val = Created): _val(val){
        assert(val <= ResubmissionRequested);
        }
    
        operator enum_type() const {
            return _val;
        }
        
        operator int() { 
              return static_cast<int>(_val); 
      }
      
      operator string() { 
        switch (_val){
          case Created: return "Created";
          case Submitted: return "Submitted";
          case UnderCheck: return "UnderCheck";
          case Grading: return "Grading";
          case Graded: return "Graded";
          case Returned: return "Returned";
          case ResubmissionRequested: return "ResubmissionRequested";        
          default:  return "[Unknown ENUM Type]";
      }
    }
    
  private:
      enum_type _val;
};
Submission_ENUM_END
class Grade;
class Student;
class Assignment;
// line 125 "model.ump"
// line 275 "model.ump"
class Submission{
	
	private:
		
		//Submission Attributes
		string id;
		tm* submittedAt;
		int version;
		bool checkPassed;
		//Submission Associations
		// line 131 "model.ump"
		Grade* submissionGrade;
		// line 21 "model.ump"
		Student* student;
		// line 122 "model.ump"
		Assignment* assignment;
		//Submission State Machines
		Submission_ENUM_Status status;
		
		void setStatus(Submission_ENUM_Status aStatus);
		bool exitStatus(void);
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Submission(const string aId, tm* aSubmittedAt, const int aVersion, const bool aCheckPassed, Grade* aSubmissionGrade, Student* aStudent, Assignment* aAssignment);
		Submission(Submission& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Submission& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Submission& Right, Submission& Left);
		friend bool operator != (Submission& Right, Submission& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Submission& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Submission& Right) const{
		  return this != &Right;  
		}
		Submission& operator=(Submission& other);
		
		void internalCopy(Submission& other);
		bool setId(const string aNewId);
		bool setSubmittedAt(tm* aNewSubmittedAt);
		bool setVersion(const int aNewVersion);
		bool setCheckPassed(bool aNewCheckPassed);
		bool setSubmissionGrade(Grade* aNewSubmissionGrade);
		inline unsigned int minimumNumberOfSubmissionGrades(void){
		  return 1;  
		}
		bool setStudent(Student* aNewStudent);
		inline unsigned int minimumNumberOfStudents(void){
		  return 1;  
		}
		bool setAssignment(Assignment* aNewAssignment);
		inline unsigned int minimumNumberOfAssignments(void){
		  return 1;  
		}
		string getId(void);
		tm* getSubmittedAt(void);
		int getVersion(void);
		bool getCheckPassed(void);
		bool isCheckPassed(void);
		Grade* getSubmissionGrade(void);
		Student* getStudent(void);
		Assignment* getAssignment(void);
		bool submit();
		bool startAutoChecks();
		bool startGrading();
		bool checksPass();
		bool checksFail();
		bool grade();
		bool requestResubmission();
		string getStatusFullName(void);
		Submission_ENUM_Status getStatus(void);
		
		// Guard method for state machine
		bool isBeforeDeadline(void);
		
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Submission();
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
    template<> struct hash<Submission *> {
        size_t operator()(Submission* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif