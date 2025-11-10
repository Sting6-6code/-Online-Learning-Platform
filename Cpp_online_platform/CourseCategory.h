//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__COURSECATEGORY_H
#define DEF__COURSECATEGORY_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "CourseCategory.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <CourseCategory.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>

class Course;
// line 110 "model.ump"
// line 265 "model.ump"
class CourseCategory{
	
	private:
		
		//CourseCategory Attributes
		string id;
		string name;
		//CourseCategory Associations
		// line 45 "model.ump"
		vector<Course*>* courses;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		CourseCategory(const string aId, const string aName);
		CourseCategory(CourseCategory& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const CourseCategory& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (CourseCategory& Right, CourseCategory& Left);
		friend bool operator != (CourseCategory& Right, CourseCategory& Left){
		  return !( Right == Left);  
		}
		bool operator == (const CourseCategory& Right) const{
		  return this == &Right;  
		}
		bool operator != (const CourseCategory& Right) const{
		  return this != &Right;  
		}
		CourseCategory& operator=(CourseCategory& other);
		
		void internalCopy(CourseCategory& other);
		bool setId(const string aNewId);
		bool setName(const string aNewName);
		string getId(void);
		string getName(void);
		vector<Course*>* getCourses(void);
		Course* getCourse(int index);
		unsigned int numberOfCourses(void);
		bool hasCourse(void);
		int indexOfCourse(Course* aCourse);
		bool removeCourse(Course* aCourse);
		bool removeCourseAt(int index);
		bool addCourse(Course* aNewCourse);
		bool addCourseAt(Course* aNewCourse, int index);
		bool addOrMoveCourseAt(Course* aNewCourse, int index);
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~CourseCategory();
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
    template<> struct hash<CourseCategory *> {
        size_t operator()(CourseCategory* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif