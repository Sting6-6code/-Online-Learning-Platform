//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__STUDENT_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Student.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Student.h>
#include <User.h>
#include <Enrollment.h>
#include <Course.h>
#include <Submission.h>
#include <Grade.h>
#include <Assignment.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Student::Student(const string aId, const string aName, const string aEmail):User(aId, aName, aEmail){
  this->studentEnrollments= new vector<Enrollment*>();
  this->studentSubmissions= new vector<Submission*>();  
}

Student::Student(Student& other):User(other){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Student::toOstream(ostream& os) const{
  User::toOstream(os);
  Student* thisptr = const_cast<Student*>(this);
  (thisptr->getStudentEnrollments()!= NULL ?  os << "studentEnrollments:" << thisptr->getStudentEnrollments() : os << "studentEnrollments:" << "NULL")<< endl;
      (thisptr->getStudentSubmissions()!= NULL ?  os << "studentSubmissions:" << thisptr->getStudentSubmissions() : os << "studentSubmissions:" << "NULL");  
}

ostream& operator<<(ostream& os, const Student& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Student& Right, Student& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(!std::equal(Right.studentEnrollments->begin(), Right.studentEnrollments->end(), Left.studentEnrollments->begin())){
     return false;
  }
  if(!std::equal(Right.studentSubmissions->begin(), Right.studentSubmissions->end(), Left.studentSubmissions->begin())){
     return false;
  }  
}

Student& Student::operator=(Student& other){
  internalCopy(other);
  return *this;  
}

void Student::internalCopy(Student& other){
  this->studentEnrollments = new vector<Enrollment*>();
  vector<Enrollment*>::iterator studentEnrollmentsBeginIterator(other.studentEnrollments->begin());
  vector<Enrollment*>::iterator studentEnrollmentsEndIterator(other.studentEnrollments->end());
  copyVector(studentEnrollmentsBeginIterator, studentEnrollmentsEndIterator, this->studentEnrollments);
  this->studentSubmissions = new vector<Submission*>();
  vector<Submission*>::iterator studentSubmissionsBeginIterator(other.studentSubmissions->begin());
  vector<Submission*>::iterator studentSubmissionsEndIterator(other.studentSubmissions->end());
  copyVector(studentSubmissionsBeginIterator, studentSubmissionsEndIterator, this->studentSubmissions);  
}

vector<Enrollment*>* Student::getStudentEnrollments(void){
  vector<Enrollment*>* copyOfStudentEnrollments = new vector<Enrollment*>();
  vector<Enrollment*>::iterator studentEnrollmentsBeginIterator(this->studentEnrollments->begin());
  vector<Enrollment*>::iterator studentEnrollmentsEndIterator(this->studentEnrollments->end());
  copyVector(studentEnrollmentsBeginIterator, studentEnrollmentsEndIterator, copyOfStudentEnrollments);
  return copyOfStudentEnrollments;  
}

Enrollment* Student::getStudentEnrollment(int index){
  Enrollment* aStudentEnrollment= (*(this->studentEnrollments))[index];
  return aStudentEnrollment;  
}

unsigned int Student::numberOfStudentEnrollments(void){
  int number=this->studentEnrollments->size();
  return number;  
}

bool Student::hasStudentEnrollment(void){
  return this->numberOfStudentEnrollments()>0;  
}

int Student::indexOfStudentEnrollment(Enrollment* aStudentEnrollment){
  return IndexOf(this->studentEnrollments->begin(), this->studentEnrollments->end(), aStudentEnrollment);  
}

bool Student::removeStudentEnrollment(Enrollment* aStudentEnrollment){
  bool wasRemoved= false;
  int index= IndexOf(this->studentEnrollments->begin(), this->studentEnrollments->end(), aStudentEnrollment);
  if(index<0){ 
    return wasRemoved;
  }
  //Unable to remove aStudentEnrollment, as it must always have a Student
  if (this== aStudentEnrollment->getStudent()){
      return wasRemoved;
  }
  wasRemoved= this->removeStudentEnrollmentAt(index);
  return wasRemoved;  
}

bool Student::removeStudentEnrollmentAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfStudentEnrollments();
  if(index> size){
      return false;
  }
  this->studentEnrollments->erase(this->studentEnrollments->begin()+index);
  wasRemoved= size== this->numberOfStudentEnrollments()+1;
  return wasRemoved;  
}

bool Student::addStudentEnrollment(Enrollment* aNewStudentEnrollment){
  bool wasAdded= false;
  if (IndexOf(this->studentEnrollments->begin(), this->studentEnrollments->end(), aNewStudentEnrollment)> -1) {
    return wasAdded;
  }
  Student* existingStudent = aNewStudentEnrollment->getStudent();
  bool isNewStudent= existingStudent != NULL && this!=existingStudent;
  if(isNewStudent){
    aNewStudentEnrollment->setStudent(this);
  }else{
    this->studentEnrollments->push_back(aNewStudentEnrollment);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Student::addStudentEnrollmentAt(Enrollment* aNewStudentEnrollment, int index){
  bool wasAdded= false;
  if(addStudentEnrollment(aNewStudentEnrollment)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfStudentEnrollments();
      if(size>0){
          this->studentEnrollments->erase(this->studentEnrollments->begin()+IndexOf(this->studentEnrollments->begin(), this->studentEnrollments->end(),aNewStudentEnrollment));    
      }
    
      size= this->numberOfStudentEnrollments();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->studentEnrollments->insert(this->studentEnrollments->begin()+ newIndex, aNewStudentEnrollment);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Student::addOrMoveStudentEnrollmentAt(Enrollment* aNewStudentEnrollment, int index){
  bool wasAdded= false;
  if(IndexOf(this->studentEnrollments->begin(), this->studentEnrollments->end(), aNewStudentEnrollment) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfStudentEnrollments();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeStudentEnrollment(aNewStudentEnrollment)| this->addStudentEnrollmentAt(aNewStudentEnrollment, index);
  }else{
    wasAdded= this->addStudentEnrollmentAt(aNewStudentEnrollment, index);
  }
  
  return wasAdded;  
}

Enrollment* Student::addStudentEnrollment(const string aId, EnrollmentStatus aStatus, tm* aEnrolledAt, Course* aCourse){
  return new Enrollment(aId, aStatus, aEnrolledAt, this, aCourse);  
}

vector<Submission*>* Student::getStudentSubmissions(void){
  vector<Submission*>* copyOfStudentSubmissions = new vector<Submission*>();
  vector<Submission*>::iterator studentSubmissionsBeginIterator(this->studentSubmissions->begin());
  vector<Submission*>::iterator studentSubmissionsEndIterator(this->studentSubmissions->end());
  copyVector(studentSubmissionsBeginIterator, studentSubmissionsEndIterator, copyOfStudentSubmissions);
  return copyOfStudentSubmissions;  
}

Submission* Student::getStudentSubmission(int index){
  Submission* aStudentSubmission= (*(this->studentSubmissions))[index];
  return aStudentSubmission;  
}

unsigned int Student::numberOfStudentSubmissions(void){
  int number=this->studentSubmissions->size();
  return number;  
}

bool Student::hasStudentSubmission(void){
  return this->numberOfStudentSubmissions()>0;  
}

int Student::indexOfStudentSubmission(Submission* aStudentSubmission){
  return IndexOf(this->studentSubmissions->begin(), this->studentSubmissions->end(), aStudentSubmission);  
}

bool Student::removeStudentSubmission(Submission* aStudentSubmission){
  bool wasRemoved= false;
  int index= IndexOf(this->studentSubmissions->begin(), this->studentSubmissions->end(), aStudentSubmission);
  if(index<0){ 
    return wasRemoved;
  }
  //Unable to remove aStudentSubmission, as it must always have a Student
  if (this== aStudentSubmission->getStudent()){
      return wasRemoved;
  }
  wasRemoved= this->removeStudentSubmissionAt(index);
  return wasRemoved;  
}

bool Student::removeStudentSubmissionAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfStudentSubmissions();
  if(index> size){
      return false;
  }
  this->studentSubmissions->erase(this->studentSubmissions->begin()+index);
  wasRemoved= size== this->numberOfStudentSubmissions()+1;
  return wasRemoved;  
}

bool Student::addStudentSubmission(Submission* aNewStudentSubmission){
  bool wasAdded= false;
  if (IndexOf(this->studentSubmissions->begin(), this->studentSubmissions->end(), aNewStudentSubmission)> -1) {
    return wasAdded;
  }
  Student* existingStudent = aNewStudentSubmission->getStudent();
  bool isNewStudent= existingStudent != NULL && this!=existingStudent;
  if(isNewStudent){
    aNewStudentSubmission->setStudent(this);
  }else{
    this->studentSubmissions->push_back(aNewStudentSubmission);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Student::addStudentSubmissionAt(Submission* aNewStudentSubmission, int index){
  bool wasAdded= false;
  if(addStudentSubmission(aNewStudentSubmission)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfStudentSubmissions();
      if(size>0){
          this->studentSubmissions->erase(this->studentSubmissions->begin()+IndexOf(this->studentSubmissions->begin(), this->studentSubmissions->end(),aNewStudentSubmission));    
      }
    
      size= this->numberOfStudentSubmissions();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->studentSubmissions->insert(this->studentSubmissions->begin()+ newIndex, aNewStudentSubmission);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Student::addOrMoveStudentSubmissionAt(Submission* aNewStudentSubmission, int index){
  bool wasAdded= false;
  if(IndexOf(this->studentSubmissions->begin(), this->studentSubmissions->end(), aNewStudentSubmission) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfStudentSubmissions();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeStudentSubmission(aNewStudentSubmission)| this->addStudentSubmissionAt(aNewStudentSubmission, index);
  }else{
    wasAdded= this->addStudentSubmissionAt(aNewStudentSubmission, index);
  }
  
  return wasAdded;  
}

Submission* Student::addStudentSubmission(const string aId, tm* aSubmittedAt, const int aVersion, bool aCheckPassed, Grade* aSubmissionGrade, Assignment* aAssignment){
  return new Submission(aId, aSubmittedAt, aVersion, aCheckPassed, aSubmissionGrade, this, aAssignment);  
}

size_t Student::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Student::~Student(){
  //User::~User();
  this->deleteAssociatedObjects();
  
  std::for_each(studentEnrollments->begin(), studentEnrollments->end(), /*std::default_delete<Enrollment>*/ deleteElement());
  studentEnrollments->clear();
  delete studentEnrollments;
  
  std::for_each(studentSubmissions->begin(), studentSubmissions->end(), /*std::default_delete<Submission>*/ deleteElement());
  studentSubmissions->clear();
  delete studentSubmissions;  
}

void Student::deleteAssociatedObjects(void){
  for(unsigned int index = studentEnrollments->size(); index > 0 ; index--){
    Enrollment* aEnrollment= (*(this->studentEnrollments))[index-1];
    aEnrollment->deleteAssociatedObjects();
  }
  for(unsigned int index = studentSubmissions->size(); index > 0 ; index--){
    Submission* aSubmission= (*(this->studentSubmissions))[index-1];
    aSubmission->deleteAssociatedObjects();
  }  
}

//------------------------
// Convenience Methods
//------------------------

int Student::getActiveEnrollmentCount(void){
  int count = 0;
  for(auto enrollment : *studentEnrollments){
    if(enrollment->getStatus() == EnrollmentStatus::Active){
      count++;
    }
  }
  return count;
}
;

