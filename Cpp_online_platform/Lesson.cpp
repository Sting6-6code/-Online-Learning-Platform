//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__LESSON_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Lesson.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Lesson.h>
#include <VideoContent.h>
#include <Course.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Lesson::Lesson(const string aId, const string aTitle, const int aOrderIndex, Course* aCourse):
		course(NULL){
  this->id= aId;
  this->title= aTitle;
  this->orderIndex= aOrderIndex;
  this->lessonVideos= new vector<VideoContent*>();
  if (!setCourse(aCourse)){
    throw "Unable to create Lesson due to aCourse";
  }  
}

Lesson::Lesson(Lesson& other):
		course(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Lesson::toOstream(ostream& os) const{
  Lesson* thisptr = const_cast<Lesson*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "title:" << thisptr->getTitle()<< ", " <<
      "orderIndex:" << thisptr->getOrderIndex() << "]" << endl;
  (thisptr->getLessonVideos()!= NULL ?  os << "lessonVideos:" << thisptr->getLessonVideos() : os << "lessonVideos:" << "NULL")<< endl;
      (thisptr->getCourse()!= NULL ?  os << "course:" << thisptr->getCourse() : os << "course:" << "NULL");  
}

ostream& operator<<(ostream& os, const Lesson& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Lesson& Right, Lesson& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(Right.id.compare(Left.id) != 0){
     return false;
  }
  if(Right.title.compare(Left.title) != 0){
     return false;
  }
  if(Right.orderIndex!= Left.orderIndex){
     return false;
  }
  if(!std::equal(Right.lessonVideos->begin(), Right.lessonVideos->end(), Left.lessonVideos->begin())){
     return false;
  }
  if(!compare(Right.course, Left.course, sizeof Right.course)){
    return false;
  }  
}

Lesson& Lesson::operator=(Lesson& other){
  internalCopy(other);
  return *this;  
}

void Lesson::internalCopy(Lesson& other){
  this->id.assign(other.id);
  this->title.assign(other.title);
  this->orderIndex= other.orderIndex;
  this->lessonVideos = new vector<VideoContent*>();
  vector<VideoContent*>::iterator lessonVideosBeginIterator(other.lessonVideos->begin());
  vector<VideoContent*>::iterator lessonVideosEndIterator(other.lessonVideos->end());
  copyVector(lessonVideosBeginIterator, lessonVideosEndIterator, this->lessonVideos);
  copyObject(other.course, this->course, sizeof other.course);  
}

bool Lesson::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Lesson::setTitle(const string aNewTitle){
  bool wasSet= false;
  this->title = aNewTitle;
  wasSet= true;
  return wasSet;  
}

bool Lesson::setOrderIndex(const int aNewOrderIndex){
  bool wasSet= false;
  this->orderIndex = aNewOrderIndex;
  wasSet= true;
  return wasSet;  
}

bool Lesson::setCourse(Course* aNewCourse){
  bool wasSet= false;
  if(aNewCourse == NULL){
    //Must provide course to Lesson
    return wasSet;
  }
  
  if(this->course!= NULL&& this->course->numberOfLessons() <= aNewCourse->minimumNumberOfLessons()){
    return wasSet;
  }
  
  Course* existingCourse = this->course;
  this->course = aNewCourse;
  if (existingCourse != NULL && existingCourse!= aNewCourse){
    bool didRemove = existingCourse->removeLesson(this);
    if(!didRemove){
      this->course = existingCourse;
      return wasSet;
    }
  }
  this->course->addLesson(this);
  wasSet = true;
  
  return wasSet;  
}

string Lesson::getId(void){
  return this->id;  
}

string Lesson::getTitle(void){
  return this->title;  
}

int Lesson::getOrderIndex(void){
  return this->orderIndex;  
}

vector<VideoContent*>* Lesson::getLessonVideos(void){
  vector<VideoContent*>* copyOfLessonVideos = new vector<VideoContent*>();
  vector<VideoContent*>::iterator lessonVideosBeginIterator(this->lessonVideos->begin());
  vector<VideoContent*>::iterator lessonVideosEndIterator(this->lessonVideos->end());
  copyVector(lessonVideosBeginIterator, lessonVideosEndIterator, copyOfLessonVideos);
  return copyOfLessonVideos;  
}

VideoContent* Lesson::getLessonVideo(int index){
  VideoContent* aLessonVideo= (*(this->lessonVideos))[index];
  return aLessonVideo;  
}

unsigned int Lesson::numberOfLessonVideos(void){
  int number=this->lessonVideos->size();
  return number;  
}

bool Lesson::hasLessonVideo(void){
  return this->numberOfLessonVideos()>0;  
}

int Lesson::indexOfLessonVideo(VideoContent* aLessonVideo){
  return IndexOf(this->lessonVideos->begin(), this->lessonVideos->end(), aLessonVideo);  
}

bool Lesson::removeLessonVideo(VideoContent* aLessonVideo){
  bool wasRemoved= false;
  int index= IndexOf(this->lessonVideos->begin(), this->lessonVideos->end(), aLessonVideo);
  if(index<0){ 
    return wasRemoved;
  }
  //Unable to remove aLessonVideo, as it must always have a Lesson
  if (this== aLessonVideo->getLesson()){
      return wasRemoved;
  }
  wasRemoved= this->removeLessonVideoAt(index);
  return wasRemoved;  
}

bool Lesson::removeLessonVideoAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfLessonVideos();
  if(index> size){
      return false;
  }
  this->lessonVideos->erase(this->lessonVideos->begin()+index);
  wasRemoved= size== this->numberOfLessonVideos()+1;
  return wasRemoved;  
}

bool Lesson::addLessonVideo(VideoContent* aNewLessonVideo){
  bool wasAdded= false;
  if (IndexOf(this->lessonVideos->begin(), this->lessonVideos->end(), aNewLessonVideo)> -1) {
    return wasAdded;
  }
  Lesson* existingLesson = aNewLessonVideo->getLesson();
  bool isNewLesson= existingLesson != NULL && this!=existingLesson;
  if(isNewLesson){
    aNewLessonVideo->setLesson(this);
  }else{
    this->lessonVideos->push_back(aNewLessonVideo);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Lesson::addLessonVideoAt(VideoContent* aNewLessonVideo, int index){
  bool wasAdded= false;
  if(addLessonVideo(aNewLessonVideo)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfLessonVideos();
      if(size>0){
          this->lessonVideos->erase(this->lessonVideos->begin()+IndexOf(this->lessonVideos->begin(), this->lessonVideos->end(),aNewLessonVideo));    
      }
    
      size= this->numberOfLessonVideos();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->lessonVideos->insert(this->lessonVideos->begin()+ newIndex, aNewLessonVideo);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Lesson::addOrMoveLessonVideoAt(VideoContent* aNewLessonVideo, int index){
  bool wasAdded= false;
  if(IndexOf(this->lessonVideos->begin(), this->lessonVideos->end(), aNewLessonVideo) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfLessonVideos();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeLessonVideo(aNewLessonVideo)| this->addLessonVideoAt(aNewLessonVideo, index);
  }else{
    wasAdded= this->addLessonVideoAt(aNewLessonVideo, index);
  }
  
  return wasAdded;  
}

VideoContent* Lesson::addLessonVideo(const string aId, const string aUrl, const int aDurationSec){
  return new VideoContent(aId, aUrl, aDurationSec, this);  
}

Course* Lesson::getCourse(void){
  return this->course;  
}

size_t Lesson::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Lesson::~Lesson(){
  this->deleteAssociatedObjects();
  
  std::for_each(lessonVideos->begin(), lessonVideos->end(), /*std::default_delete<VideoContent>*/ deleteElement());
  lessonVideos->clear();
  delete lessonVideos;
  delete course;  
}

void Lesson::deleteAssociatedObjects(void){
  for(unsigned int index = lessonVideos->size(); index > 0 ; index--){
    VideoContent* aVideoContent= (*(this->lessonVideos))[index-1];
    aVideoContent->deleteAssociatedObjects();
  }
  Course* placeholderCourse = this->course;
  this->course = NULL;
  placeholderCourse->removeLesson(this);  
}
;

