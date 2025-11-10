//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__INSTRUCTOR_H
#define DEF__INSTRUCTOR_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Instructor.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Instructor.h>
#include <User.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>

class User;
class Course;
// line 24 "model.ump"
// line 235 "model.ump"
class Instructor : public User{
	
	private:
		
		//Instructor Associations
		// line 26 "model.ump"
		vector<Course*>* taughtCourses;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Instructor(const string aId, const string aName, const string aEmail);
		Instructor(Instructor& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Instructor& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Instructor& Right, Instructor& Left);
		friend bool operator != (Instructor& Right, Instructor& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Instructor& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Instructor& Right) const{
		  return this != &Right;  
		}
		Instructor& operator=(Instructor& other);
		
		void internalCopy(Instructor& other);
		vector<Course*>* getTaughtCourses(void);
		Course* getTaughtCourse(int index);
		unsigned int numberOfTaughtCourses(void);
		bool hasTaughtCourse(void);
		int indexOfTaughtCourse(Course* aTaughtCourse);
		bool removeTaughtCourse(Course* aTaughtCourse);
		bool removeTaughtCourseAt(int index);
		bool addTaughtCourse(Course* aNewTaughtCourse);
		bool addTaughtCourseAt(Course* aNewTaughtCourse, int index);
		bool addOrMoveTaughtCourseAt(Course* aNewTaughtCourse, int index);
		Course* addTaughtCourse(const string aId, const string aTitle, const int aCapacity);
		
		// Convenience methods
		int getActiveCourseCount(void);
		vector<Course*>* getCoursesInProgress(void);
		
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Instructor();
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
    template<> struct hash<Instructor *> {
        size_t operator()(Instructor* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif