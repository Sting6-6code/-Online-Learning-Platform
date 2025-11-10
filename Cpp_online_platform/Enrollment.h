//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__ENROLLMENT_H
#define DEF__ENROLLMENT_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Enrollment.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Enrollment.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>

class Student;
class Course;
// line 90 "model.ump"
// line 250 "model.ump"
class Enrollment{
	
	private:
		
		//Enrollment Attributes
		string id;
		EnrollmentStatus status;
		tm* enrolledAt;
		//Enrollment Associations
		// line 20 "model.ump"
		Student* student;
		// line 43 "model.ump"
		Course* course;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Enrollment(const string aId, EnrollmentStatus aStatus, tm* aEnrolledAt, Student* aStudent, Course* aCourse);
		Enrollment(Enrollment& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Enrollment& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Enrollment& Right, Enrollment& Left);
		friend bool operator != (Enrollment& Right, Enrollment& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Enrollment& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Enrollment& Right) const{
		  return this != &Right;  
		}
		Enrollment& operator=(Enrollment& other);
		
		void internalCopy(Enrollment& other);
		bool setId(const string aNewId);
		bool setStatus(EnrollmentStatus aNewStatus);
		bool setEnrolledAt(tm* aNewEnrolledAt);
		bool setStudent(Student* aNewStudent);
		inline unsigned int minimumNumberOfStudents(void){
		  return 1;  
		}
		bool setCourse(Course* aNewCourse);
		inline unsigned int minimumNumberOfCourses(void){
		  return 1;  
		}
		string getId(void);
		EnrollmentStatus getStatus(void);
		tm* getEnrolledAt(void);
		Student* getStudent(void);
		Course* getCourse(void);
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Enrollment();
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
    template<> struct hash<Enrollment *> {
        size_t operator()(Enrollment* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif