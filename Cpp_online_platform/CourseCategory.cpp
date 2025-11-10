//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__COURSECATEGORY_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <CourseCategory.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <CourseCategory.h>
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
CourseCategory::CourseCategory(const string aId, const string aName){
  this->id= aId;
  this->name= aName;
  this->courses= new vector<Course*>();  
}

CourseCategory::CourseCategory(CourseCategory& other){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void CourseCategory::toOstream(ostream& os) const{
  CourseCategory* thisptr = const_cast<CourseCategory*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "name:" << thisptr->getName() << "]" << endl;
  (thisptr->getCourses()!= NULL ?  os << "courses:" << thisptr->getCourses() : os << "courses:" << "NULL");  
}

ostream& operator<<(ostream& os, const CourseCategory& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (CourseCategory& Right, CourseCategory& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(Right.id.compare(Left.id) != 0){
     return false;
  }
  if(Right.name.compare(Left.name) != 0){
     return false;
  }
  if(!std::equal(Right.courses->begin(), Right.courses->end(), Left.courses->begin())){
     return false;
  }  
}

CourseCategory& CourseCategory::operator=(CourseCategory& other){
  internalCopy(other);
  return *this;  
}

void CourseCategory::internalCopy(CourseCategory& other){
  this->id.assign(other.id);
  this->name.assign(other.name);
  this->courses = new vector<Course*>();
  vector<Course*>::iterator coursesBeginIterator(other.courses->begin());
  vector<Course*>::iterator coursesEndIterator(other.courses->end());
  copyVector(coursesBeginIterator, coursesEndIterator, this->courses);  
}

bool CourseCategory::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool CourseCategory::setName(const string aNewName){
  bool wasSet= false;
  this->name = aNewName;
  wasSet= true;
  return wasSet;  
}

string CourseCategory::getId(void){
  return this->id;  
}

string CourseCategory::getName(void){
  return this->name;  
}

vector<Course*>* CourseCategory::getCourses(void){
  vector<Course*>* copyOfCourses = new vector<Course*>();
  vector<Course*>::iterator coursesBeginIterator(this->courses->begin());
  vector<Course*>::iterator coursesEndIterator(this->courses->end());
  copyVector(coursesBeginIterator, coursesEndIterator, copyOfCourses);
  return copyOfCourses;  
}

Course* CourseCategory::getCourse(int index){
  Course* aCourse= (*(this->courses))[index];
  return aCourse;  
}

unsigned int CourseCategory::numberOfCourses(void){
  int number=this->courses->size();
  return number;  
}

bool CourseCategory::hasCourse(void){
  return this->numberOfCourses()>0;  
}

int CourseCategory::indexOfCourse(Course* aCourse){
  return IndexOf(this->courses->begin(), this->courses->end(), aCourse);  
}

bool CourseCategory::removeCourse(Course* aCourse){
  bool wasRemoved= false;
  int index= IndexOf(this->courses->begin(), this->courses->end(), aCourse);
  if(index<0){ 
    return wasRemoved;
  }
  wasRemoved= this->removeCourseAt(index);
  if (aCourse->indexOfCategory(this) == -1){
    wasRemoved = true;
  }else{
    wasRemoved = aCourse->removeCategory(this);
    if (!wasRemoved){
      this->courses->insert(this->courses->begin()+ index, aCourse);
    }
  }
  return wasRemoved;  
}

bool CourseCategory::removeCourseAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfCourses();
  if(index> size){
      return false;
  }
  this->courses->erase(this->courses->begin()+index);
  wasRemoved= size== this->numberOfCourses()+1;
  return wasRemoved;  
}

bool CourseCategory::addCourse(Course* aNewCourse){
  bool wasAdded= false;
  if (IndexOf(this->courses->begin(), this->courses->end(), aNewCourse)> -1) {
    return wasAdded;
  }
  this->courses->push_back(aNewCourse);
  if (aNewCourse->indexOfCategory(this)!= -1) {
    wasAdded = true;
  }else{
    wasAdded = aNewCourse->addCategory(this);
    if(!wasAdded){
      int currentIndex= this->indexOfCourse(aNewCourse);
      this->courses->erase(this->courses->begin()+currentIndex);
    }
  }
  return wasAdded;  
}

bool CourseCategory::addCourseAt(Course* aNewCourse, int index){
  bool wasAdded= false;
  if(addCourse(aNewCourse)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfCourses();
      if(size>0){
          this->courses->erase(this->courses->begin()+IndexOf(this->courses->begin(), this->courses->end(),aNewCourse));    
      }
    
      size= this->numberOfCourses();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->courses->insert(this->courses->begin()+ newIndex, aNewCourse);
      wasAdded = true;
  }
  return wasAdded;  
}

bool CourseCategory::addOrMoveCourseAt(Course* aNewCourse, int index){
  bool wasAdded= false;
  if(IndexOf(this->courses->begin(), this->courses->end(), aNewCourse) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfCourses();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeCourse(aNewCourse)| this->addCourseAt(aNewCourse, index);
  }else{
    wasAdded= this->addCourseAt(aNewCourse, index);
  }
  
  return wasAdded;  
}

size_t CourseCategory::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
CourseCategory::~CourseCategory(){
  this->deleteAssociatedObjects();
  
  std::for_each(courses->begin(), courses->end(), /*std::default_delete<Course>*/ deleteElement());
  courses->clear();
  delete courses;  
}

void CourseCategory::deleteAssociatedObjects(void){
  vector<Course*>* copyOfCourses = new vector<Course*>();
  vector<Course*>::iterator coursesBeginIterator(this->courses->begin());
  vector<Course*>::iterator coursesEndIterator(this->courses->end());
  copyVector(coursesBeginIterator, coursesEndIterator, copyOfCourses);
  this->courses->clear();
  
  while(this->courses->size()>0){
    Course* aCourse= (*(copyOfCourses))[0];
    aCourse->removeCategory(this);
  }  
}
;

