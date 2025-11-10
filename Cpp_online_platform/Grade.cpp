//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__GRADE_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Grade.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Grade.h>
#include <Submission.h>
#include <Assignment.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Grade::Grade(const string aId, const double aScore, const string aFeedback):
		submission(NULL){
  // Validate score is non-negative
  if (aScore < 0) {
    throw std::invalid_argument("Grade score cannot be negative");
  }
  
  this->id= aId;
  this->score= aScore;
  this->feedback= aFeedback;
  this->submission= NULL;  
}

Grade::Grade(Grade& other):
		submission(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Grade::toOstream(ostream& os) const{
  Grade* thisptr = const_cast<Grade*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "score:" << thisptr->getScore()<< ", " <<
      "feedback:" << thisptr->getFeedback() << "]" << endl;
  (thisptr->getSubmission()!= NULL ?  os << "submission:" << thisptr->getSubmission() : os << "submission:" << "NULL");  
}

ostream& operator<<(ostream& os, const Grade& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Grade& Right, Grade& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(Right.id.compare(Left.id) != 0){
     return false;
  }
  if(Right.score!= Left.score){
     return false;
  }
  if(Right.feedback.compare(Left.feedback) != 0){
     return false;
  }
  if(!compare(Right.submission, Left.submission, sizeof Right.submission)){
    return false;
  }  
}

Grade& Grade::operator=(Grade& other){
  internalCopy(other);
  return *this;  
}

void Grade::internalCopy(Grade& other){
  this->id.assign(other.id);
  this->score= other.score;
  this->feedback.assign(other.feedback);
  copyObject(other.submission, this->submission, sizeof other.submission);  
}

bool Grade::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Grade::setScore(double aNewScore){
  bool wasSet= false;
  
  // Validate score is non-negative
  if (aNewScore < 0) {
    return false;
  }
  
  // Validate score does not exceed maxScore if submission is set
  if (submission != nullptr) {
    Assignment* assignment = submission->getAssignment();
    if (assignment != nullptr) {
      int maxScore = assignment->getMaxScore();
      if (aNewScore > maxScore) {
        return false;
      }
    }
  }
  
  this->score = aNewScore;
  wasSet= true;
  return wasSet;  
}

bool Grade::setFeedback(const string aNewFeedback){
  bool wasSet= false;
  this->feedback = aNewFeedback;
  wasSet= true;
  return wasSet;  
}

bool Grade::setSubmission(Submission* aNewSubmission){
  bool wasSet= false;
  if (this->submission != NULL && this->submission != aNewSubmission && this== this->submission->getSubmissionGrade()){
    //Unable to set Submission, as existing submission would become an orphan
    return wasSet;
  }
    
  this->submission = aNewSubmission;
  Grade* oldSubmissionGrade = aNewSubmission != NULL ? aNewSubmission->getSubmissionGrade() : NULL;
  
  if (this != oldSubmissionGrade){
    if (oldSubmissionGrade != NULL){
        oldSubmissionGrade->submission = NULL;
     }
     
    if (this->submission != NULL){
      this->submission->setSubmissionGrade(this);
    }
  }
  wasSet= true;
  return wasSet;  
}

string Grade::getId(void){
  return this->id;  
}

double Grade::getScore(void){
  return this->score;  
}

string Grade::getFeedback(void){
  return this->feedback;  
}

Submission* Grade::getSubmission(void){
  return this->submission;  
}

size_t Grade::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Grade::~Grade(){
  this->deleteAssociatedObjects();
  delete submission;  
}

//------------------------
// Convenience Methods
//------------------------

double Grade::getPercentage(void){
  if (submission == nullptr) {
    return 0.0;
  }
  
  Assignment* assignment = submission->getAssignment();
  if (assignment == nullptr) {
    return 0.0;
  }
  
  int maxScore = assignment->getMaxScore();
  if (maxScore == 0) {
    return 0.0;
  }
  
  return (score / maxScore) * 100.0;
}

void Grade::deleteAssociatedObjects(void){
  if(this->submission!= NULL){
    Submission* existingSubmission= this->submission;
    this->submission= NULL;
    existingSubmission->deleteAssociatedObjects();
  }
}
;

