//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__VIDEOCONTENT_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <VideoContent.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <VideoContent.h>
#include <Lesson.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
VideoContent::VideoContent(const string aId, const string aUrl, const int aDurationSec, Lesson* aLesson):
		lesson(NULL){
  this->id= aId;
  this->url= aUrl;
  this->durationSec= aDurationSec;
  if (!setLesson(aLesson)){
    throw "Unable to create VideoContent due to aLesson";
  }  
}

VideoContent::VideoContent(VideoContent& other):
		lesson(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void VideoContent::toOstream(ostream& os) const{
  VideoContent* thisptr = const_cast<VideoContent*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "url:" << thisptr->getUrl()<< ", " <<
      "durationSec:" << thisptr->getDurationSec() << "]" << endl;
  (thisptr->getLesson()!= NULL ?  os << "lesson:" << thisptr->getLesson() : os << "lesson:" << "NULL");  
}

ostream& operator<<(ostream& os, const VideoContent& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (VideoContent& Right, VideoContent& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(Right.id.compare(Left.id) != 0){
     return false;
  }
  if(Right.url.compare(Left.url) != 0){
     return false;
  }
  if(Right.durationSec!= Left.durationSec){
     return false;
  }
  if(!compare(Right.lesson, Left.lesson, sizeof Right.lesson)){
    return false;
  }  
}

VideoContent& VideoContent::operator=(VideoContent& other){
  internalCopy(other);
  return *this;  
}

void VideoContent::internalCopy(VideoContent& other){
  this->id.assign(other.id);
  this->url.assign(other.url);
  this->durationSec= other.durationSec;
  copyObject(other.lesson, this->lesson, sizeof other.lesson);  
}

bool VideoContent::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool VideoContent::setUrl(const string aNewUrl){
  bool wasSet= false;
  this->url = aNewUrl;
  wasSet= true;
  return wasSet;  
}

bool VideoContent::setDurationSec(const int aNewDurationSec){
  bool wasSet= false;
  this->durationSec = aNewDurationSec;
  wasSet= true;
  return wasSet;  
}

bool VideoContent::setLesson(Lesson* aNewLesson){
  bool wasSet= false;
  if (aNewLesson == NULL){
    return wasSet;
  }
  
  Lesson* existingLesson = this->lesson;
  this->lesson = aNewLesson;
  
  if (existingLesson != NULL && existingLesson != aNewLesson){
      existingLesson->removeLessonVideo(this);
  }
  this->lesson->addLessonVideo(this);
  wasSet = true;
  return wasSet;  
}

string VideoContent::getId(void){
  return this->id;  
}

string VideoContent::getUrl(void){
  return this->url;  
}

int VideoContent::getDurationSec(void){
  return this->durationSec;  
}

Lesson* VideoContent::getLesson(void){
  return this->lesson;  
}

size_t VideoContent::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
VideoContent::~VideoContent(){
  this->deleteAssociatedObjects();
  delete lesson;  
}

void VideoContent::deleteAssociatedObjects(void){
  Lesson* placeholderLesson = this->lesson;
  this->lesson = NULL;
  placeholderLesson->removeLessonVideo(this);  
}
;

