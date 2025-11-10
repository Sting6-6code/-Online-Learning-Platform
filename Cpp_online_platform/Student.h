//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__STUDENT_H
#define DEF__STUDENT_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Student.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Student.h>
#include <User.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>

class User;
class Enrollment;
class Course;
class Submission;
class Grade;
class Assignment;
// line 18 "model.ump"
// line 230 "model.ump"
class Student : public User{
	
	private:
		
		//Student Associations
		// line 20 "model.ump"
		vector<Enrollment*>* studentEnrollments;
		// line 21 "model.ump"
		vector<Submission*>* studentSubmissions;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Student(const string aId, const string aName, const string aEmail);
		Student(Student& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Student& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Student& Right, Student& Left);
		friend bool operator != (Student& Right, Student& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Student& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Student& Right) const{
		  return this != &Right;  
		}
		Student& operator=(Student& other);
		
		void internalCopy(Student& other);
		vector<Enrollment*>* getStudentEnrollments(void);
		Enrollment* getStudentEnrollment(int index);
		unsigned int numberOfStudentEnrollments(void);
		bool hasStudentEnrollment(void);
		int indexOfStudentEnrollment(Enrollment* aStudentEnrollment);
		bool removeStudentEnrollment(Enrollment* aStudentEnrollment);
		bool removeStudentEnrollmentAt(int index);
		bool addStudentEnrollment(Enrollment* aNewStudentEnrollment);
		bool addStudentEnrollmentAt(Enrollment* aNewStudentEnrollment, int index);
		bool addOrMoveStudentEnrollmentAt(Enrollment* aNewStudentEnrollment, int index);
		Enrollment* addStudentEnrollment(const string aId, EnrollmentStatus aStatus, tm* aEnrolledAt, Course* aCourse);
		vector<Submission*>* getStudentSubmissions(void);
		Submission* getStudentSubmission(int index);
		unsigned int numberOfStudentSubmissions(void);
		bool hasStudentSubmission(void);
		int indexOfStudentSubmission(Submission* aStudentSubmission);
		bool removeStudentSubmission(Submission* aStudentSubmission);
		bool removeStudentSubmissionAt(int index);
		bool addStudentSubmission(Submission* aNewStudentSubmission);
		bool addStudentSubmissionAt(Submission* aNewStudentSubmission, int index);
		bool addOrMoveStudentSubmissionAt(Submission* aNewStudentSubmission, int index);
		Submission* addStudentSubmission(const string aId, tm* aSubmittedAt, const int aVersion, bool aCheckPassed, Grade* aSubmissionGrade, Assignment* aAssignment);
		
		// Convenience methods
		int getActiveEnrollmentCount(void);
		
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Student();
		void deleteAssociatedObjects(void);
		string getEmail(void);
		string getName(void);
		string getId(void);
		bool setEmail(const string aNewEmail);
		bool setName(const string aNewName);
		bool setId(const string aNewId);
	
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
    template<> struct hash<Student *> {
        size_t operator()(Student* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif