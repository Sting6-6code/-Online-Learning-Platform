//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__VIDEOCONTENT_H
#define DEF__VIDEOCONTENT_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "VideoContent.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <VideoContent.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>

class Lesson;
// line 104 "model.ump"
// line 260 "model.ump"
class VideoContent{
	
	private:
		
		//VideoContent Attributes
		string id;
		string url;
		int durationSec;
		//VideoContent Associations
		// line 101 "model.ump"
		Lesson* lesson;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		VideoContent(const string aId, const string aUrl, const int aDurationSec, Lesson* aLesson);
		VideoContent(VideoContent& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const VideoContent& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (VideoContent& Right, VideoContent& Left);
		friend bool operator != (VideoContent& Right, VideoContent& Left){
		  return !( Right == Left);  
		}
		bool operator == (const VideoContent& Right) const{
		  return this == &Right;  
		}
		bool operator != (const VideoContent& Right) const{
		  return this != &Right;  
		}
		VideoContent& operator=(VideoContent& other);
		
		void internalCopy(VideoContent& other);
		bool setId(const string aNewId);
		bool setUrl(const string aNewUrl);
		bool setDurationSec(const int aNewDurationSec);
		bool setLesson(Lesson* aNewLesson);
		inline unsigned int minimumNumberOfLessons(void){
		  return 1;  
		}
		string getId(void);
		string getUrl(void);
		int getDurationSec(void);
		Lesson* getLesson(void);
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~VideoContent();
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
    template<> struct hash<VideoContent *> {
        size_t operator()(VideoContent* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif