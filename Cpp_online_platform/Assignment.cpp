//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__ASSIGNMENT_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Assignment.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Assignment.h>
#include <Submission.h>
#include <Grade.h>
#include <Student.h>
#include <Course.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>
#include <ctime>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Assignment::Assignment(const string aId, const string aTitle, tm* aDeadline, const int aMaxScore, Course* aCourse):
		course(NULL){
  // Validate title is not empty
  if (aTitle.empty()) {
    throw std::invalid_argument("Assignment title cannot be empty");
  }
  
  // Validate maxScore is positive
  if (aMaxScore <= 0) {
    throw std::invalid_argument("Assignment maxScore must be greater than 0");
  }
  
  // Validate deadline is not null
  if (aDeadline == nullptr) {
    throw std::invalid_argument("Assignment deadline cannot be null");
  }
  
  this->id= aId;
  this->title= aTitle;
  this->deadline= aDeadline;
  this->maxScore= aMaxScore;
  this->assignmentSubmissions= new vector<Submission*>();
  if (!setCourse(aCourse)){
    throw "Unable to create Assignment due to aCourse";
  }  
}

Assignment::Assignment(Assignment& other):
		course(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Assignment::toOstream(ostream& os) const{
  Assignment* thisptr = const_cast<Assignment*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "title:" << thisptr->getTitle()<< ", " <<
      "maxScore:" << thisptr->getMaxScore() << "]" << endl;
  (thisptr->getDeadline()!= NULL ?  os << "deadline:" << thisptr->getDeadline() : os << "deadline:" << "NULL")<< endl;
      (thisptr->getAssignmentSubmissions()!= NULL ?  os << "assignmentSubmissions:" << thisptr->getAssignmentSubmissions() : os << "assignmentSubmissions:" << "NULL")<< endl;
      (thisptr->getCourse()!= NULL ?  os << "course:" << thisptr->getCourse() : os << "course:" << "NULL");  
}

ostream& operator<<(ostream& os, const Assignment& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Assignment& Right, Assignment& Left){
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
  if(!compare(Right.deadline, Left.deadline, sizeof Right.deadline)){
    return false;
  }
  if(Right.maxScore!= Left.maxScore){
     return false;
  }
  if(!std::equal(Right.assignmentSubmissions->begin(), Right.assignmentSubmissions->end(), Left.assignmentSubmissions->begin())){
     return false;
  }
  if(!compare(Right.course, Left.course, sizeof Right.course)){
    return false;
  }  
}

Assignment& Assignment::operator=(Assignment& other){
  internalCopy(other);
  return *this;  
}

void Assignment::internalCopy(Assignment& other){
  this->id.assign(other.id);
  this->title.assign(other.title);
  copyObject(other.deadline, this->deadline, sizeof other.deadline);
  this->maxScore= other.maxScore;
  this->assignmentSubmissions = new vector<Submission*>();
  vector<Submission*>::iterator assignmentSubmissionsBeginIterator(other.assignmentSubmissions->begin());
  vector<Submission*>::iterator assignmentSubmissionsEndIterator(other.assignmentSubmissions->end());
  copyVector(assignmentSubmissionsBeginIterator, assignmentSubmissionsEndIterator, this->assignmentSubmissions);
  copyObject(other.course, this->course, sizeof other.course);  
}

bool Assignment::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Assignment::setTitle(const string aNewTitle){
  bool wasSet= false;
  this->title = aNewTitle;
  wasSet= true;
  return wasSet;  
}

bool Assignment::setDeadline(tm* aNewDeadline){
  bool wasSet= false;
  this->deadline = aNewDeadline;
  wasSet= true;
  return wasSet;  
}

bool Assignment::setMaxScore(const int aNewMaxScore){
  bool wasSet= false;
  this->maxScore = aNewMaxScore;
  wasSet= true;
  return wasSet;  
}

bool Assignment::setCourse(Course* aNewCourse){
  bool wasSet= false;
  if(aNewCourse == NULL){
    //Must provide course to Assignment
    return wasSet;
  }
  
  if(this->course!= NULL&& this->course->numberOfCourseAssignments() <= aNewCourse->minimumNumberOfCourseAssignments()){
    return wasSet;
  }
  
  Course* existingCourse = this->course;
  this->course = aNewCourse;
  if (existingCourse != NULL && existingCourse!= aNewCourse){
    bool didRemove = existingCourse->removeCourseAssignment(this);
    if(!didRemove){
      this->course = existingCourse;
      return wasSet;
    }
  }
  this->course->addCourseAssignment(this);
  wasSet = true;
  
  return wasSet;  
}

string Assignment::getId(void){
  return this->id;  
}

string Assignment::getTitle(void){
  return this->title;  
}

tm* Assignment::getDeadline(void){
  return this->deadline;  
}

int Assignment::getMaxScore(void){
  return this->maxScore;  
}

vector<Submission*>* Assignment::getAssignmentSubmissions(void){
  vector<Submission*>* copyOfAssignmentSubmissions = new vector<Submission*>();
  vector<Submission*>::iterator assignmentSubmissionsBeginIterator(this->assignmentSubmissions->begin());
  vector<Submission*>::iterator assignmentSubmissionsEndIterator(this->assignmentSubmissions->end());
  copyVector(assignmentSubmissionsBeginIterator, assignmentSubmissionsEndIterator, copyOfAssignmentSubmissions);
  return copyOfAssignmentSubmissions;  
}

Submission* Assignment::getAssignmentSubmission(int index){
  Submission* aAssignmentSubmission= (*(this->assignmentSubmissions))[index];
  return aAssignmentSubmission;  
}

unsigned int Assignment::numberOfAssignmentSubmissions(void){
  int number=this->assignmentSubmissions->size();
  return number;  
}

bool Assignment::hasAssignmentSubmission(void){
  return this->numberOfAssignmentSubmissions()>0;  
}

int Assignment::indexOfAssignmentSubmission(Submission* aAssignmentSubmission){
  return IndexOf(this->assignmentSubmissions->begin(), this->assignmentSubmissions->end(), aAssignmentSubmission);  
}

bool Assignment::removeAssignmentSubmission(Submission* aAssignmentSubmission){
  bool wasRemoved= false;
  int index= IndexOf(this->assignmentSubmissions->begin(), this->assignmentSubmissions->end(), aAssignmentSubmission);
  if(index<0){ 
    return wasRemoved;
  }
  //Unable to remove aAssignmentSubmission, as it must always have a Assignment
  if (this== aAssignmentSubmission->getAssignment()){
      return wasRemoved;
  }
  wasRemoved= this->removeAssignmentSubmissionAt(index);
  return wasRemoved;  
}

bool Assignment::removeAssignmentSubmissionAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfAssignmentSubmissions();
  if(index> size){
      return false;
  }
  this->assignmentSubmissions->erase(this->assignmentSubmissions->begin()+index);
  wasRemoved= size== this->numberOfAssignmentSubmissions()+1;
  return wasRemoved;  
}

bool Assignment::addAssignmentSubmission(Submission* aNewAssignmentSubmission){
  bool wasAdded= false;
  if (IndexOf(this->assignmentSubmissions->begin(), this->assignmentSubmissions->end(), aNewAssignmentSubmission)> -1) {
    return wasAdded;
  }
  Assignment* existingAssignment = aNewAssignmentSubmission->getAssignment();
  bool isNewAssignment= existingAssignment != NULL && this!=existingAssignment;
  if(isNewAssignment){
    aNewAssignmentSubmission->setAssignment(this);
  }else{
    this->assignmentSubmissions->push_back(aNewAssignmentSubmission);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Assignment::addAssignmentSubmissionAt(Submission* aNewAssignmentSubmission, int index){
  bool wasAdded= false;
  if(addAssignmentSubmission(aNewAssignmentSubmission)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfAssignmentSubmissions();
      if(size>0){
          this->assignmentSubmissions->erase(this->assignmentSubmissions->begin()+IndexOf(this->assignmentSubmissions->begin(), this->assignmentSubmissions->end(),aNewAssignmentSubmission));    
      }
    
      size= this->numberOfAssignmentSubmissions();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->assignmentSubmissions->insert(this->assignmentSubmissions->begin()+ newIndex, aNewAssignmentSubmission);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Assignment::addOrMoveAssignmentSubmissionAt(Submission* aNewAssignmentSubmission, int index){
  bool wasAdded= false;
  if(IndexOf(this->assignmentSubmissions->begin(), this->assignmentSubmissions->end(), aNewAssignmentSubmission) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfAssignmentSubmissions();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeAssignmentSubmission(aNewAssignmentSubmission)| this->addAssignmentSubmissionAt(aNewAssignmentSubmission, index);
  }else{
    wasAdded= this->addAssignmentSubmissionAt(aNewAssignmentSubmission, index);
  }
  
  return wasAdded;  
}

Submission* Assignment::addAssignmentSubmission(const string aId, tm* aSubmittedAt, const int aVersion, bool aCheckPassed, Grade* aSubmissionGrade, Student* aStudent){
  return new Submission(aId, aSubmittedAt, aVersion, aCheckPassed, aSubmissionGrade, aStudent, this);  
}

Course* Assignment::getCourse(void){
  return this->course;  
}

size_t Assignment::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Assignment::~Assignment(){
  this->deleteAssociatedObjects();
  delete deadline;
  
  std::for_each(assignmentSubmissions->begin(), assignmentSubmissions->end(), /*std::default_delete<Submission>*/ deleteElement());
  assignmentSubmissions->clear();
  delete assignmentSubmissions;
  delete course;  
}

//------------------------
// Convenience Methods
//------------------------

bool Assignment::isOverdue(void){
  time_t now = time(nullptr);
  time_t deadlineTime = mktime(deadline);
  return (now > deadlineTime);
}

int Assignment::getSubmissionCount(void){
  return assignmentSubmissions->size();
}

void Assignment::deleteAssociatedObjects(void){
  for(unsigned int index = assignmentSubmissions->size(); index > 0 ; index--){
    Submission* aSubmission= (*(this->assignmentSubmissions))[index-1];
    aSubmission->deleteAssociatedObjects();
  }
  Course* placeholderCourse = this->course;
  this->course = NULL;
  placeholderCourse->removeCourseAssignment(this);  
}
;

