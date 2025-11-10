//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__ENROLLMENT_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Enrollment.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Enrollment.h>
#include <Student.h>
#include <Course.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Enrollment::Enrollment(const string aId, EnrollmentStatus aStatus, tm* aEnrolledAt, Student* aStudent, Course* aCourse):
		student(NULL),
		course(NULL){
  // OCL: EnrollmentOnlyAfterPublish - Course must not be in Draft status
  if (aCourse != nullptr && aCourse->getStatus() == Status::Draft) {
    throw std::invalid_argument("Cannot enroll in a Draft course");
  }
  
  this->id= aId;
  this->status= aStatus;
  this->enrolledAt= aEnrolledAt;
  if (!setStudent(aStudent)){
    throw "Unable to create Enrollment due to aStudent";
  }
  if (!setCourse(aCourse)){
    throw "Unable to create Enrollment due to aCourse";
  }  
}

Enrollment::Enrollment(Enrollment& other):
		student(NULL),
		course(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Enrollment::toOstream(ostream& os) const{
  Enrollment* thisptr = const_cast<Enrollment*>(this);
  os << "[" << "id:" << thisptr->getId() << "]" << endl;
  os << "status:" << (int)thisptr->getStatus() << endl;
  (thisptr->getEnrolledAt()!= NULL ?  os << "enrolledAt:" << thisptr->getEnrolledAt() : os << "enrolledAt:" << "NULL")<< endl;
      (thisptr->getStudent()!= NULL ?  os << "student:" << thisptr->getStudent() : os << "student:" << "NULL")<< endl;
      (thisptr->getCourse()!= NULL ?  os << "course:" << thisptr->getCourse() : os << "course:" << "NULL");  
}

ostream& operator<<(ostream& os, const Enrollment& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Enrollment& Right, Enrollment& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }
  if(Right.id.compare(Left.id) != 0){
     return false;
  }
  if(!compare(Right.status, Left.status, sizeof Right.status)){
    return false;
  }
  if(!compare(Right.enrolledAt, Left.enrolledAt, sizeof Right.enrolledAt)){
    return false;
  }
  if(!compare(Right.student, Left.student, sizeof Right.student)){
    return false;
  }
  if(!compare(Right.course, Left.course, sizeof Right.course)){
    return false;
  }  
}

Enrollment& Enrollment::operator=(Enrollment& other){
  internalCopy(other);
  return *this;  
}

void Enrollment::internalCopy(Enrollment& other){
  this->id.assign(other.id);
  this->status = other.status;  // Direct assignment for enum class
  copyObject(other.enrolledAt, this->enrolledAt, sizeof other.enrolledAt);
  copyObject(other.student, this->student, sizeof other.student);
  copyObject(other.course, this->course, sizeof other.course);  
}

bool Enrollment::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Enrollment::setStatus(EnrollmentStatus aNewStatus){
  bool wasSet= false;
  this->status = aNewStatus;
  wasSet= true;
  return wasSet;  
}

bool Enrollment::setEnrolledAt(tm* aNewEnrolledAt){
  bool wasSet= false;
  this->enrolledAt = aNewEnrolledAt;
  wasSet= true;
  return wasSet;  
}

bool Enrollment::setStudent(Student* aNewStudent){
  bool wasSet= false;
  if (aNewStudent == NULL){
    return wasSet;
  }
  
  Student* existingStudent = this->student;
  this->student = aNewStudent;
  
  if (existingStudent != NULL && existingStudent != aNewStudent){
      existingStudent->removeStudentEnrollment(this);
  }
  this->student->addStudentEnrollment(this);
  wasSet = true;
  return wasSet;  
}

bool Enrollment::setCourse(Course* aNewCourse){
  bool wasSet= false;
  if (aNewCourse == NULL){
    return wasSet;
  }
  
  Course* existingCourse = this->course;
  this->course = aNewCourse;
  
  if (existingCourse != NULL && existingCourse != aNewCourse){
      existingCourse->removeCourseEnrollment(this);
  }
  this->course->addCourseEnrollment(this);
  wasSet = true;
  return wasSet;  
}

string Enrollment::getId(void){
  return this->id;  
}

EnrollmentStatus Enrollment::getStatus(void){
  return this->status;  
}

tm* Enrollment::getEnrolledAt(void){
  return this->enrolledAt;  
}

Student* Enrollment::getStudent(void){
  return this->student;  
}

Course* Enrollment::getCourse(void){
  return this->course;  
}

size_t Enrollment::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Enrollment::~Enrollment(){
  this->deleteAssociatedObjects();
  // status is an enum class, not a pointer, no need to delete
  delete enrolledAt;
  delete student;
  delete course;  
}

void Enrollment::deleteAssociatedObjects(void){
  Student* placeholderStudent = this->student;
  this->student = NULL;
  placeholderStudent->removeStudentEnrollment(this);
  Course* placeholderCourse = this->course;
  this->course = NULL;
  placeholderCourse->removeCourseEnrollment(this);  
}
;

