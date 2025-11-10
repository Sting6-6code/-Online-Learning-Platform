//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__SUBMISSION_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Submission.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Submission.h>
#include <Grade.h>
#include <Student.h>
#include <Assignment.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <cassert>
#include <string>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Submission::Submission(const string aId, tm* aSubmittedAt, const int aVersion, const bool aCheckPassed, Grade* aSubmissionGrade, Student* aStudent, Assignment* aAssignment):
		submissionGrade(NULL),
		student(NULL),
		assignment(NULL){
  // Validate student is not null
  if (aStudent == nullptr) {
    throw std::invalid_argument("Submission student cannot be null");
  }
  
  // Validate assignment is not null
  if (aAssignment == nullptr) {
    throw std::invalid_argument("Submission assignment cannot be null");
  }
  
  this->id= aId;
  this->submittedAt= aSubmittedAt;
  this->version= aVersion;
  this->checkPassed= aCheckPassed;
  if (!setSubmissionGrade(aSubmissionGrade)){
    throw "Unable to create Submission due to aSubmissionGrade";
  }
  if (!setStudent(aStudent)){
    throw "Unable to create Submission due to aStudent";
  }
  if (!setAssignment(aAssignment)){
    throw "Unable to create Submission due to aAssignment";
  }
  setStatus(Submission_ENUM_Status::Created);  
}

Submission::Submission(Submission& other):
		submissionGrade(NULL),
		student(NULL),
		assignment(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Submission::toOstream(ostream& os) const{
  Submission* thisptr = const_cast<Submission*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "version:" << thisptr->getVersion()<< ", " <<
      "checkPassed:" << thisptr->getCheckPassed() << "]" << endl;
  (thisptr->getSubmittedAt()!= NULL ?  os << "submittedAt:" << thisptr->getSubmittedAt() : os << "submittedAt:" << "NULL")<< endl;
      (thisptr->getSubmissionGrade()!= NULL ?  os << "submissionGrade:" << thisptr->getSubmissionGrade() : os << "submissionGrade:" << "NULL")<< endl;
      (thisptr->getStudent()!= NULL ?  os << "student:" << thisptr->getStudent() : os << "student:" << "NULL")<< endl;
      (thisptr->getAssignment()!= NULL ?  os << "assignment:" << thisptr->getAssignment() : os << "assignment:" << "NULL");  
}

ostream& operator<<(ostream& os, const Submission& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Submission& Right, Submission& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(Right.id.compare(Left.id) != 0){
     return false;
  }
  if(!compare(Right.submittedAt, Left.submittedAt, sizeof Right.submittedAt)){
    return false;
  }
  if(Right.version!= Left.version){
     return false;
  }
  if(Right.checkPassed!= Left.checkPassed){
     return false;
  }
  if(!compare(Right.submissionGrade, Left.submissionGrade, sizeof Right.submissionGrade)){
    return false;
  }
  if(!compare(Right.student, Left.student, sizeof Right.student)){
    return false;
  }
  if(!compare(Right.assignment, Left.assignment, sizeof Right.assignment)){
    return false;
  }  
}

Submission& Submission::operator=(Submission& other){
  internalCopy(other);
  return *this;  
}

void Submission::internalCopy(Submission& other){
  this->id.assign(other.id);
  copyObject(other.submittedAt, this->submittedAt, sizeof other.submittedAt);
  this->version= other.version;
  this->checkPassed= other.checkPassed;
  copyObject(other.submissionGrade, this->submissionGrade, sizeof other.submissionGrade);
  copyObject(other.student, this->student, sizeof other.student);
  copyObject(other.assignment, this->assignment, sizeof other.assignment);  
}

bool Submission::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Submission::setSubmittedAt(tm* aNewSubmittedAt){
  bool wasSet= false;
  this->submittedAt = aNewSubmittedAt;
  wasSet= true;
  return wasSet;  
}

bool Submission::setVersion(const int aNewVersion){
  bool wasSet= false;
  this->version = aNewVersion;
  wasSet= true;
  return wasSet;  
}

bool Submission::setCheckPassed(bool aNewCheckPassed){
  bool wasSet= false;
  this->checkPassed = aNewCheckPassed;
  wasSet= true;
  return wasSet;  
}

bool Submission::setSubmissionGrade(Grade* aNewSubmissionGrade){
  bool wasSet= false;
  if(aNewSubmissionGrade == NULL){
    //Unable to set Grade to NULL, as Grade must always be associated to a Submission
    return wasSet;
  }
  
  Submission* existingSubmission = aNewSubmissionGrade->getSubmission();
  if (existingSubmission != NULL && this!= existingSubmission){
    //Unable to set Grade, the current submissionGrade already has a Grade, which would be orphaned if it were re-assigned
    return wasSet;
  }
  
  Grade* oldSubmissionGrade = this->submissionGrade;
  this->submissionGrade = aNewSubmissionGrade;
  this->submissionGrade->setSubmission(this);
  
  if (oldSubmissionGrade != NULL) {
    oldSubmissionGrade->setSubmission(NULL);
  }
  wasSet = true;
  
  return wasSet;  
}

bool Submission::setStudent(Student* aNewStudent){
  bool wasSet= false;
  if (aNewStudent == NULL){
    return wasSet;
  }
  
  Student* existingStudent = this->student;
  this->student = aNewStudent;
  
  if (existingStudent != NULL && existingStudent != aNewStudent){
      existingStudent->removeStudentSubmission(this);
  }
  this->student->addStudentSubmission(this);
  wasSet = true;
  return wasSet;  
}

bool Submission::setAssignment(Assignment* aNewAssignment){
  bool wasSet= false;
  if (aNewAssignment == NULL){
    return wasSet;
  }
  
  Assignment* existingAssignment = this->assignment;
  this->assignment = aNewAssignment;
  
  if (existingAssignment != NULL && existingAssignment != aNewAssignment){
      existingAssignment->removeAssignmentSubmission(this);
  }
  this->assignment->addAssignmentSubmission(this);
  wasSet = true;
  return wasSet;  
}

string Submission::getId(void){
  return this->id;  
}

tm* Submission::getSubmittedAt(void){
  return this->submittedAt;  
}

int Submission::getVersion(void){
  return this->version;  
}

bool Submission::getCheckPassed(void){
  return this->checkPassed;  
}

bool Submission::isCheckPassed(void){
  return this->checkPassed;  
}

Grade* Submission::getSubmissionGrade(void){
  return this->submissionGrade;  
}

Student* Submission::getStudent(void){
  return this->student;  
}

Assignment* Submission::getAssignment(void){
  return this->assignment;  
}

bool Submission::submit(){
  bool wasEventProcessed= false;
  Submission_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Submission_ENUM_Status::Created:
  		if(isBeforeDeadline()){	
  			setStatus(Submission_ENUM_Status::Submitted);
  			wasEventProcessed = true;
  		}
  	    break;
  	
  	
  	case Submission_ENUM_Status::ResubmissionRequested:
  		if(isBeforeDeadline()){	
  			setStatus(Submission_ENUM_Status::Submitted);
  			wasEventProcessed = true;
  		}
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Submission::startAutoChecks(){
  bool wasEventProcessed= false;
  Submission_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Submission_ENUM_Status::Submitted:
  		setStatus(Submission_ENUM_Status::UnderCheck);
  		wasEventProcessed = true;
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Submission::startGrading(){
  bool wasEventProcessed= false;
  Submission_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Submission_ENUM_Status::Submitted:
  		if(checkPassed){	
  			setStatus(Submission_ENUM_Status::Grading);
  			wasEventProcessed = true;
  		}
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Submission::checksPass(){
  bool wasEventProcessed= false;
  Submission_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Submission_ENUM_Status::UnderCheck:
  		setStatus(Submission_ENUM_Status::Submitted);
  		wasEventProcessed = true;
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Submission::checksFail(){
  bool wasEventProcessed= false;
  Submission_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Submission_ENUM_Status::UnderCheck:
  		setStatus(Submission_ENUM_Status::Returned);
  		wasEventProcessed = true;
  	    break;

      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Submission::grade(){
  bool wasEventProcessed= false;
  Submission_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Submission_ENUM_Status::Grading:
  		setStatus(Submission_ENUM_Status::Graded);
  		wasEventProcessed = true;
  	    break;

      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Submission::requestResubmission(){
  bool wasEventProcessed= false;
  Submission_ENUM_Status aStatus= status;
    
  switch((int)aStatus){	
  	case Submission_ENUM_Status::Graded:
  		if(true){  // Simplified guard - remove canResubmit() for now	
  			setStatus(Submission_ENUM_Status::ResubmissionRequested);
  			wasEventProcessed = true;
  		}
  	    break;
  	
  	
  	case Submission_ENUM_Status::Returned:
  		if(true){  // Simplified guard - remove canResubmit() for now	
  			setStatus(Submission_ENUM_Status::ResubmissionRequested);
  			wasEventProcessed = true;
  		}
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

string Submission::getStatusFullName(void){
  string answer = status;
  return answer;
    
}

Submission_ENUM_Status Submission::getStatus(void){
  return this->status;  
}

void Submission::setStatus(Submission_ENUM_Status aStatus){
  status= aStatus;
    
}

bool Submission::exitStatus(void){
  bool wasEventProcessed = false;
  
    
  switch((int)status){	
  	case Submission_ENUM_Status::Created:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Submission_ENUM_Status::Submitted:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Submission_ENUM_Status::UnderCheck:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Submission_ENUM_Status::Grading:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Submission_ENUM_Status::Graded:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Submission_ENUM_Status::Returned:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Submission_ENUM_Status::ResubmissionRequested:
  		wasEventProcessed= true;
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;
    
}

size_t Submission::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Submission::~Submission(){
  this->deleteAssociatedObjects();
  delete submittedAt;
  delete submissionGrade;
  delete student;
  delete assignment;  
}

void Submission::deleteAssociatedObjects(void){
  if(this->submissionGrade!= NULL){
    this->submissionGrade->setSubmission(NULL);  
    this->submissionGrade= NULL;
  }
  Student* placeholderStudent = this->student;
  this->student = NULL;
  placeholderStudent->removeStudentSubmission(this);
  Assignment* placeholderAssignment = this->assignment;
  this->assignment = NULL;
  placeholderAssignment->removeAssignmentSubmission(this);  
}

//------------------------
// Guard Method for State Machine
//------------------------

bool Submission::isBeforeDeadline(void){
  // Check if current time is before assignment deadline
  time_t now = time(nullptr);
  tm* deadline = assignment->getDeadline();
  if(deadline == nullptr) return false;
  time_t deadlineTime = mktime(deadline);
  return (now <= deadlineTime);
}
;

