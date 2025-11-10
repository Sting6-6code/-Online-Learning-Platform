//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__INSTRUCTOR_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Instructor.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Instructor.h>
#include <User.h>
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
Instructor::Instructor(const string aId, const string aName, const string aEmail):User(aId, aName, aEmail){
  this->taughtCourses= new vector<Course*>();  
}

Instructor::Instructor(Instructor& other):User(other){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Instructor::toOstream(ostream& os) const{
  User::toOstream(os);
  Instructor* thisptr = const_cast<Instructor*>(this);
  (thisptr->getTaughtCourses()!= NULL ?  os << "taughtCourses:" << thisptr->getTaughtCourses() : os << "taughtCourses:" << "NULL");  
}

ostream& operator<<(ostream& os, const Instructor& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Instructor& Right, Instructor& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(!std::equal(Right.taughtCourses->begin(), Right.taughtCourses->end(), Left.taughtCourses->begin())){
     return false;
  }  
}

Instructor& Instructor::operator=(Instructor& other){
  internalCopy(other);
  return *this;  
}

void Instructor::internalCopy(Instructor& other){
  this->taughtCourses = new vector<Course*>();
  vector<Course*>::iterator taughtCoursesBeginIterator(other.taughtCourses->begin());
  vector<Course*>::iterator taughtCoursesEndIterator(other.taughtCourses->end());
  copyVector(taughtCoursesBeginIterator, taughtCoursesEndIterator, this->taughtCourses);  
}

vector<Course*>* Instructor::getTaughtCourses(void){
  vector<Course*>* copyOfTaughtCourses = new vector<Course*>();
  vector<Course*>::iterator taughtCoursesBeginIterator(this->taughtCourses->begin());
  vector<Course*>::iterator taughtCoursesEndIterator(this->taughtCourses->end());
  copyVector(taughtCoursesBeginIterator, taughtCoursesEndIterator, copyOfTaughtCourses);
  return copyOfTaughtCourses;  
}

Course* Instructor::getTaughtCourse(int index){
  Course* aTaughtCourse= (*(this->taughtCourses))[index];
  return aTaughtCourse;  
}

unsigned int Instructor::numberOfTaughtCourses(void){
  int number=this->taughtCourses->size();
  return number;  
}

bool Instructor::hasTaughtCourse(void){
  return this->numberOfTaughtCourses()>0;  
}

int Instructor::indexOfTaughtCourse(Course* aTaughtCourse){
  return IndexOf(this->taughtCourses->begin(), this->taughtCourses->end(), aTaughtCourse);  
}

bool Instructor::removeTaughtCourse(Course* aTaughtCourse){
  bool wasRemoved= false;
  int index= IndexOf(this->taughtCourses->begin(), this->taughtCourses->end(), aTaughtCourse);
  if(index<0){ 
    return wasRemoved;
  }
  //Unable to remove aTaughtCourse, as it must always have a Instructor
  if (this== aTaughtCourse->getInstructor()){
      return wasRemoved;
  }
  wasRemoved= this->removeTaughtCourseAt(index);
  return wasRemoved;  
}

bool Instructor::removeTaughtCourseAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfTaughtCourses();
  if(index> size){
      return false;
  }
  this->taughtCourses->erase(this->taughtCourses->begin()+index);
  wasRemoved= size== this->numberOfTaughtCourses()+1;
  return wasRemoved;  
}

bool Instructor::addTaughtCourse(Course* aNewTaughtCourse){
  bool wasAdded= false;
  if (IndexOf(this->taughtCourses->begin(), this->taughtCourses->end(), aNewTaughtCourse)> -1) {
    return wasAdded;
  }
  Instructor* existingInstructor = aNewTaughtCourse->getInstructor();
  bool isNewInstructor= existingInstructor != NULL && this!=existingInstructor;
  if(isNewInstructor){
    aNewTaughtCourse->setInstructor(this);
  }else{
    this->taughtCourses->push_back(aNewTaughtCourse);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Instructor::addTaughtCourseAt(Course* aNewTaughtCourse, int index){
  bool wasAdded= false;
  if(addTaughtCourse(aNewTaughtCourse)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfTaughtCourses();
      if(size>0){
          this->taughtCourses->erase(this->taughtCourses->begin()+IndexOf(this->taughtCourses->begin(), this->taughtCourses->end(),aNewTaughtCourse));    
      }
    
      size= this->numberOfTaughtCourses();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->taughtCourses->insert(this->taughtCourses->begin()+ newIndex, aNewTaughtCourse);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Instructor::addOrMoveTaughtCourseAt(Course* aNewTaughtCourse, int index){
  bool wasAdded= false;
  if(IndexOf(this->taughtCourses->begin(), this->taughtCourses->end(), aNewTaughtCourse) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfTaughtCourses();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeTaughtCourse(aNewTaughtCourse)| this->addTaughtCourseAt(aNewTaughtCourse, index);
  }else{
    wasAdded= this->addTaughtCourseAt(aNewTaughtCourse, index);
  }
  
  return wasAdded;  
}

Course* Instructor::addTaughtCourse(const string aId, const string aTitle, const int aCapacity){
  return new Course(aId, aTitle, aCapacity, this);  
}

size_t Instructor::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Instructor::~Instructor(){
  //User::~User();
  this->deleteAssociatedObjects();
  
  std::for_each(taughtCourses->begin(), taughtCourses->end(), /*std::default_delete<Course>*/ deleteElement());
  taughtCourses->clear();
  delete taughtCourses;  
}

void Instructor::deleteAssociatedObjects(void){
  for(unsigned int index = taughtCourses->size(); index > 0 ; index--){
    Course* aCourse= (*(this->taughtCourses))[index-1];
    aCourse->deleteAssociatedObjects();
  }  
}

//------------------------
// Convenience Methods
//------------------------

int Instructor::getActiveCourseCount(void){
  int count = 0;
  for(auto course : *taughtCourses){
    Status status = course->getStatus();
    if(status != Status::Cancelled && status != Status::Completed){
      count++;
    }
  }
  return count;
}

vector<Course*>* Instructor::getCoursesInProgress(void){
  vector<Course*>* result = new vector<Course*>();
  for(auto course : *taughtCourses){
    if(course->getStatus() == Status::InProgress){
      result->push_back(course);
    }
  }
  return result;
}
;

