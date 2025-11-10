//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__COURSE_H
#define DEF__COURSE_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Course.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Course.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>
#include <cassert>
#include <string>

Course_ENUM_BEGIN
class Status_{

  public:
    typedef enum{Draft=0, Published=1, EnrollmentOpen=2, Waitlisted=3, InProgress=4, Completed=5, Cancelled=6} enum_type;
    
    Status_(enum_type val = Draft): _val(val){
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
          case Draft: return "Draft";
          case Published: return "Published";
          case EnrollmentOpen: return "EnrollmentOpen";
          case Waitlisted: return "Waitlisted";
          case InProgress: return "InProgress";
          case Completed: return "Completed";
          case Cancelled: return "Cancelled";        
          default:  return "[Unknown ENUM Type]";
      }
    }
    
  private:
      enum_type _val;
};
Course_ENUM_END
class Lesson;
class Enrollment;
class Student;
class Assignment;
class CourseCategory;
class Instructor;
/*
 * ===== ????????? =====
 */
// line 34 "model.ump"
// line 245 "model.ump"
class Course{
	
	private:
		
		//Course Attributes
		string id;
		string title;
		int capacity;
		//Course Associations
		/*
		 * ????????????
		 */
		// line 40 "model.ump"
		vector<Lesson*>* lessons;
		/*
		 * ????????????
		 */
		// line 43 "model.ump"
		vector<Enrollment*>* courseEnrollments;
		// line 44 "model.ump"
		vector<Assignment*>* courseAssignments;
		// line 45 "model.ump"
		vector<CourseCategory*>* categories;
		// line 26 "model.ump"
		Instructor* instructor;
		//Course State Machines
		Status status;
		
		void setStatus(Status aStatus);
		bool exitStatus(void);
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Course(const string aId, const string aTitle, const int aCapacity, Instructor* aInstructor);
		Course(Course& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Course& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Course& Right, Course& Left);
		friend bool operator != (Course& Right, Course& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Course& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Course& Right) const{
		  return this != &Right;  
		}
		Course& operator=(Course& other);
		
		void internalCopy(Course& other);
		bool setId(const string aNewId);
		bool setTitle(const string aNewTitle);
		bool setCapacity(const int aNewCapacity);
		inline unsigned int minimumNumberOfLessons(void){
		  return -1;  
		}
		inline unsigned int minimumNumberOfCourseAssignments(void){
		  return -1;  
		}
		bool setInstructor(Instructor* aNewInstructor);
		inline unsigned int minimumNumberOfInstructors(void){
		  return 1;  
		}
		string getId(void);
		string getTitle(void);
		int getCapacity(void);
		vector<Lesson*>* getLessons(void);
		Lesson* getLesson(int index);
		unsigned int numberOfLessons(void);
		bool hasLesson(void);
		int indexOfLesson(Lesson* aLesson);
		bool removeLesson(Lesson* aLesson);
		bool removeLessonAt(int index);
		bool addLesson(Lesson* aNewLesson);
		bool addLessonAt(Lesson* aNewLesson, int index);
		bool addOrMoveLessonAt(Lesson* aNewLesson, int index);
		Lesson* addLesson(const string aId, const string aTitle, const int aOrderIndex);
		vector<Enrollment*>* getCourseEnrollments(void);
		Enrollment* getCourseEnrollment(int index);
		unsigned int numberOfCourseEnrollments(void);
		bool hasCourseEnrollment(void);
		int indexOfCourseEnrollment(Enrollment* aCourseEnrollment);
		bool removeCourseEnrollment(Enrollment* aCourseEnrollment);
		bool removeCourseEnrollmentAt(int index);
		bool addCourseEnrollment(Enrollment* aNewCourseEnrollment);
		bool addCourseEnrollmentAt(Enrollment* aNewCourseEnrollment, int index);
		bool addOrMoveCourseEnrollmentAt(Enrollment* aNewCourseEnrollment, int index);
		Enrollment* addCourseEnrollment(const string aId, EnrollmentStatus aStatus, tm* aEnrolledAt, Student* aStudent);
		vector<Assignment*>* getCourseAssignments(void);
		Assignment* getCourseAssignment(int index);
		unsigned int numberOfCourseAssignments(void);
		bool hasCourseAssignment(void);
		int indexOfCourseAssignment(Assignment* aCourseAssignment);
		bool removeCourseAssignment(Assignment* aCourseAssignment);
		bool removeCourseAssignmentAt(int index);
		bool addCourseAssignment(Assignment* aNewCourseAssignment);
		bool addCourseAssignmentAt(Assignment* aNewCourseAssignment, int index);
		bool addOrMoveCourseAssignmentAt(Assignment* aNewCourseAssignment, int index);
		Assignment* addCourseAssignment(const string aId, const string aTitle, tm* aDeadline, const int aMaxScore);
		vector<CourseCategory*>* getCategories(void);
		CourseCategory* getCategory(int index);
		unsigned int numberOfCategories(void);
		bool hasCategory(void);
		int indexOfCategory(CourseCategory* aCategory);
		bool removeCategory(CourseCategory* aCategory);
		bool removeCategoryAt(int index);
		bool addCategory(CourseCategory* aNewCategory);
		bool addCategoryAt(CourseCategory* aNewCategory, int index);
		bool addOrMoveCategoryAt(CourseCategory* aNewCategory, int index);
		Instructor* getInstructor(void);
		bool publish();
		bool openEnrollment();
		bool cancel();
		bool startCourse();
		bool complete();
		string getStatusFullName(void);
		Status getStatus(void);
		
		// Guard methods for state machine
		bool hasMinimumContent(void);
		bool hasCapacity(void);
		bool hasActiveEnrollments(void);
		
		// Business methods
		Enrollment* enroll(Student* student);
		
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Course();
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
    template<> struct hash<Course *> {
        size_t operator()(Course* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif