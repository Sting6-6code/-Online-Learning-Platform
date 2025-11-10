//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__LESSON_H
#define DEF__LESSON_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Lesson.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Lesson.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>

class VideoContent;
class Course;
// line 96 "model.ump"
// line 255 "model.ump"
class Lesson{
	
	private:
		
		//Lesson Attributes
		string id;
		string title;
		int orderIndex;
		//Lesson Associations
		// line 101 "model.ump"
		vector<VideoContent*>* lessonVideos;
		// line 40 "model.ump"
		Course* course;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Lesson(const string aId, const string aTitle, const int aOrderIndex, Course* aCourse);
		Lesson(Lesson& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Lesson& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Lesson& Right, Lesson& Left);
		friend bool operator != (Lesson& Right, Lesson& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Lesson& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Lesson& Right) const{
		  return this != &Right;  
		}
		Lesson& operator=(Lesson& other);
		
		void internalCopy(Lesson& other);
		bool setId(const string aNewId);
		bool setTitle(const string aNewTitle);
		bool setOrderIndex(const int aNewOrderIndex);
		bool setCourse(Course* aNewCourse);
		inline unsigned int minimumNumberOfCourses(void){
		  return 1;  
		}
		string getId(void);
		string getTitle(void);
		int getOrderIndex(void);
		vector<VideoContent*>* getLessonVideos(void);
		VideoContent* getLessonVideo(int index);
		unsigned int numberOfLessonVideos(void);
		bool hasLessonVideo(void);
		int indexOfLessonVideo(VideoContent* aLessonVideo);
		bool removeLessonVideo(VideoContent* aLessonVideo);
		bool removeLessonVideoAt(int index);
		bool addLessonVideo(VideoContent* aNewLessonVideo);
		bool addLessonVideoAt(VideoContent* aNewLessonVideo, int index);
		bool addOrMoveLessonVideoAt(VideoContent* aNewLessonVideo, int index);
		VideoContent* addLessonVideo(const string aId, const string aUrl, const int aDurationSec);
		Course* getCourse(void);
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Lesson();
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
    template<> struct hash<Lesson *> {
        size_t operator()(Lesson* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif