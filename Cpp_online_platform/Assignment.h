//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__ASSIGNMENT_H
#define DEF__ASSIGNMENT_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Assignment.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Assignment.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>

class Submission;
class Grade;
class Student;
class Course;
/*
 * ===== ????????? =====
 */
// line 116 "model.ump"
// line 270 "model.ump"
class Assignment{
	
	private:
		
		//Assignment Attributes
		string id;
		string title;
		tm* deadline;
		int maxScore;
		//Assignment Associations
		// line 122 "model.ump"
		vector<Submission*>* assignmentSubmissions;
		// line 44 "model.ump"
		Course* course;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Assignment(const string aId, const string aTitle, tm* aDeadline, const int aMaxScore, Course* aCourse);
		Assignment(Assignment& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Assignment& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Assignment& Right, Assignment& Left);
		friend bool operator != (Assignment& Right, Assignment& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Assignment& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Assignment& Right) const{
		  return this != &Right;  
		}
		Assignment& operator=(Assignment& other);
		
		void internalCopy(Assignment& other);
		bool setId(const string aNewId);
		bool setTitle(const string aNewTitle);
		bool setDeadline(tm* aNewDeadline);
		bool setMaxScore(const int aNewMaxScore);
		bool setCourse(Course* aNewCourse);
		inline unsigned int minimumNumberOfCourses(void){
		  return 1;  
		}
		string getId(void);
		string getTitle(void);
		tm* getDeadline(void);
		int getMaxScore(void);
		vector<Submission*>* getAssignmentSubmissions(void);
		Submission* getAssignmentSubmission(int index);
		unsigned int numberOfAssignmentSubmissions(void);
		bool hasAssignmentSubmission(void);
		int indexOfAssignmentSubmission(Submission* aAssignmentSubmission);
		bool removeAssignmentSubmission(Submission* aAssignmentSubmission);
		bool removeAssignmentSubmissionAt(int index);
		bool addAssignmentSubmission(Submission* aNewAssignmentSubmission);
		bool addAssignmentSubmissionAt(Submission* aNewAssignmentSubmission, int index);
		bool addOrMoveAssignmentSubmissionAt(Submission* aNewAssignmentSubmission, int index);
		Submission* addAssignmentSubmission(const string aId, tm* aSubmittedAt, const int aVersion, bool aCheckPassed, Grade* aSubmissionGrade, Student* aStudent);
		Course* getCourse(void);
		
		// Convenience methods
		bool isOverdue(void);
		int getSubmissionCount(void);
		
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Assignment();
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
    template<> struct hash<Assignment *> {
        size_t operator()(Assignment* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif