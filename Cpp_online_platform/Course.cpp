//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__COURSE_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Course.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Course.h>
#include <Lesson.h>
#include <Enrollment.h>
#include <Student.h>
#include <Assignment.h>
#include <CourseCategory.h>
#include <Instructor.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>
#include <vector>
#include <cassert>
#include <string>
#include <ctime>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Course::Course(const string aId, const string aTitle, const int aCapacity, Instructor* aInstructor):
		instructor(NULL){
  this->id= aId;
  this->title= aTitle;
  this->capacity= aCapacity;
  this->lessons= new vector<Lesson*>();
  this->courseEnrollments= new vector<Enrollment*>();
  this->courseAssignments= new vector<Assignment*>();
  this->categories= new vector<CourseCategory*>();
  if (!setInstructor(aInstructor)){
    throw "Unable to create Course due to aInstructor";
  }
  setStatus(Status::Draft);  
}

Course::Course(Course& other):
		instructor(NULL){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Course::toOstream(ostream& os) const{
  Course* thisptr = const_cast<Course*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "title:" << thisptr->getTitle()<< ", " <<
      "capacity:" << thisptr->getCapacity() << "]" << endl;
  (thisptr->getLessons()!= NULL ?  os << "lessons:" << thisptr->getLessons() : os << "lessons:" << "NULL")<< endl;
      (thisptr->getCourseEnrollments()!= NULL ?  os << "courseEnrollments:" << thisptr->getCourseEnrollments() : os << "courseEnrollments:" << "NULL")<< endl;
      (thisptr->getCourseAssignments()!= NULL ?  os << "courseAssignments:" << thisptr->getCourseAssignments() : os << "courseAssignments:" << "NULL")<< endl;
      (thisptr->getCategories()!= NULL ?  os << "categories:" << thisptr->getCategories() : os << "categories:" << "NULL")<< endl;
      (thisptr->getInstructor()!= NULL ?  os << "instructor:" << thisptr->getInstructor() : os << "instructor:" << "NULL");  
}

ostream& operator<<(ostream& os, const Course& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Course& Right, Course& Left){
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
  if(Right.capacity!= Left.capacity){
     return false;
  }
  if(!std::equal(Right.lessons->begin(), Right.lessons->end(), Left.lessons->begin())){
     return false;
  }
  if(!std::equal(Right.courseEnrollments->begin(), Right.courseEnrollments->end(), Left.courseEnrollments->begin())){
     return false;
  }
  if(!std::equal(Right.courseAssignments->begin(), Right.courseAssignments->end(), Left.courseAssignments->begin())){
     return false;
  }
  if(!std::equal(Right.categories->begin(), Right.categories->end(), Left.categories->begin())){
     return false;
  }
  if(!compare(Right.instructor, Left.instructor, sizeof Right.instructor)){
    return false;
  }  
}

Course& Course::operator=(Course& other){
  internalCopy(other);
  return *this;  
}

void Course::internalCopy(Course& other){
  this->id.assign(other.id);
  this->title.assign(other.title);
  this->capacity= other.capacity;
  this->lessons = new vector<Lesson*>();
  vector<Lesson*>::iterator lessonsBeginIterator(other.lessons->begin());
  vector<Lesson*>::iterator lessonsEndIterator(other.lessons->end());
  copyVector(lessonsBeginIterator, lessonsEndIterator, this->lessons);
  this->courseEnrollments = new vector<Enrollment*>();
  vector<Enrollment*>::iterator courseEnrollmentsBeginIterator(other.courseEnrollments->begin());
  vector<Enrollment*>::iterator courseEnrollmentsEndIterator(other.courseEnrollments->end());
  copyVector(courseEnrollmentsBeginIterator, courseEnrollmentsEndIterator, this->courseEnrollments);
  this->courseAssignments = new vector<Assignment*>();
  vector<Assignment*>::iterator courseAssignmentsBeginIterator(other.courseAssignments->begin());
  vector<Assignment*>::iterator courseAssignmentsEndIterator(other.courseAssignments->end());
  copyVector(courseAssignmentsBeginIterator, courseAssignmentsEndIterator, this->courseAssignments);
  this->categories = new vector<CourseCategory*>();
  vector<CourseCategory*>::iterator categoriesBeginIterator(other.categories->begin());
  vector<CourseCategory*>::iterator categoriesEndIterator(other.categories->end());
  copyVector(categoriesBeginIterator, categoriesEndIterator, this->categories);
  copyObject(other.instructor, this->instructor, sizeof other.instructor);  
}

bool Course::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool Course::setTitle(const string aNewTitle){
  bool wasSet= false;
  this->title = aNewTitle;
  wasSet= true;
  return wasSet;  
}

bool Course::setCapacity(const int aNewCapacity){
  bool wasSet= false;
  this->capacity = aNewCapacity;
  wasSet= true;
  return wasSet;  
}

bool Course::setInstructor(Instructor* aNewInstructor){
  bool wasSet= false;
  if (aNewInstructor == NULL){
    return wasSet;
  }
  
  Instructor* existingInstructor = this->instructor;
  this->instructor = aNewInstructor;
  
  if (existingInstructor != NULL && existingInstructor != aNewInstructor){
      existingInstructor->removeTaughtCourse(this);
  }
  this->instructor->addTaughtCourse(this);
  wasSet = true;
  return wasSet;  
}

string Course::getId(void){
  return this->id;  
}

string Course::getTitle(void){
  return this->title;  
}

int Course::getCapacity(void){
  return this->capacity;  
}

vector<Lesson*>* Course::getLessons(void){
  vector<Lesson*>* copyOfLessons = new vector<Lesson*>();
  vector<Lesson*>::iterator lessonsBeginIterator(this->lessons->begin());
  vector<Lesson*>::iterator lessonsEndIterator(this->lessons->end());
  copyVector(lessonsBeginIterator, lessonsEndIterator, copyOfLessons);
  return copyOfLessons;  
}

Lesson* Course::getLesson(int index){
  Lesson* aLesson= (*(this->lessons))[index];
  return aLesson;  
}

unsigned int Course::numberOfLessons(void){
  int number=this->lessons->size();
  return number;  
}

bool Course::hasLesson(void){
  return this->numberOfLessons()>0;  
}

int Course::indexOfLesson(Lesson* aLesson){
  return IndexOf(this->lessons->begin(), this->lessons->end(), aLesson);  
}

bool Course::removeLesson(Lesson* aLesson){
  bool wasRemoved= false;
  int index= IndexOf(this->lessons->begin(), this->lessons->end(), aLesson);
  if(index<0){ 
    return wasRemoved;
  }
  //course already at minimum (1)
  if (numberOfLessons() <= minimumNumberOfLessons()){
    return false;
  }
  //Unable to remove aLesson, as it must always have a Course
  if (this== aLesson->getCourse()){
      return wasRemoved;
  }
  wasRemoved= this->removeLessonAt(index);
  return wasRemoved;  
}

bool Course::removeLessonAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfLessons();
  if(index> size){
      return false;
  }
  this->lessons->erase(this->lessons->begin()+index);
  wasRemoved= size== this->numberOfLessons()+1;
  return wasRemoved;  
}

bool Course::addLesson(Lesson* aNewLesson){
  bool wasAdded= false;
  if (IndexOf(this->lessons->begin(), this->lessons->end(), aNewLesson)> -1) {
    return wasAdded;
  }
  Course* existingCourse = aNewLesson->getCourse();
  bool isNewCourse= existingCourse != NULL && this!=existingCourse;
  if(isNewCourse){
    if(existingCourse->numberOfLessons() <= minimumNumberOfLessons()){
      return wasAdded;
    }
    aNewLesson->setCourse(this);
  }else{
    this->lessons->push_back(aNewLesson);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Course::addLessonAt(Lesson* aNewLesson, int index){
  bool wasAdded= false;
  if(addLesson(aNewLesson)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfLessons();
      if(size>0){
          this->lessons->erase(this->lessons->begin()+IndexOf(this->lessons->begin(), this->lessons->end(),aNewLesson));    
      }
    
      size= this->numberOfLessons();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->lessons->insert(this->lessons->begin()+ newIndex, aNewLesson);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Course::addOrMoveLessonAt(Lesson* aNewLesson, int index){
  bool wasAdded= false;
  if(IndexOf(this->lessons->begin(), this->lessons->end(), aNewLesson) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfLessons();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeLesson(aNewLesson)| this->addLessonAt(aNewLesson, index);
  }else{
    wasAdded= this->addLessonAt(aNewLesson, index);
  }
  
  return wasAdded;  
}

Lesson* Course::addLesson(const string aId, const string aTitle, const int aOrderIndex){
  return new Lesson(aId, aTitle, aOrderIndex, this);  
}

vector<Enrollment*>* Course::getCourseEnrollments(void){
  vector<Enrollment*>* copyOfCourseEnrollments = new vector<Enrollment*>();
  vector<Enrollment*>::iterator courseEnrollmentsBeginIterator(this->courseEnrollments->begin());
  vector<Enrollment*>::iterator courseEnrollmentsEndIterator(this->courseEnrollments->end());
  copyVector(courseEnrollmentsBeginIterator, courseEnrollmentsEndIterator, copyOfCourseEnrollments);
  return copyOfCourseEnrollments;  
}

Enrollment* Course::getCourseEnrollment(int index){
  Enrollment* aCourseEnrollment= (*(this->courseEnrollments))[index];
  return aCourseEnrollment;  
}

unsigned int Course::numberOfCourseEnrollments(void){
  int number=this->courseEnrollments->size();
  return number;  
}

bool Course::hasCourseEnrollment(void){
  return this->numberOfCourseEnrollments()>0;  
}

int Course::indexOfCourseEnrollment(Enrollment* aCourseEnrollment){
  return IndexOf(this->courseEnrollments->begin(), this->courseEnrollments->end(), aCourseEnrollment);  
}

bool Course::removeCourseEnrollment(Enrollment* aCourseEnrollment){
  bool wasRemoved= false;
  int index= IndexOf(this->courseEnrollments->begin(), this->courseEnrollments->end(), aCourseEnrollment);
  if(index<0){ 
    return wasRemoved;
  }
  //Unable to remove aCourseEnrollment, as it must always have a Course
  if (this== aCourseEnrollment->getCourse()){
      return wasRemoved;
  }
  wasRemoved= this->removeCourseEnrollmentAt(index);
  return wasRemoved;  
}

bool Course::removeCourseEnrollmentAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfCourseEnrollments();
  if(index> size){
      return false;
  }
  this->courseEnrollments->erase(this->courseEnrollments->begin()+index);
  wasRemoved= size== this->numberOfCourseEnrollments()+1;
  return wasRemoved;  
}

bool Course::addCourseEnrollment(Enrollment* aNewCourseEnrollment){
  bool wasAdded= false;
  if (IndexOf(this->courseEnrollments->begin(), this->courseEnrollments->end(), aNewCourseEnrollment)> -1) {
    return wasAdded;
  }
  Course* existingCourse = aNewCourseEnrollment->getCourse();
  bool isNewCourse= existingCourse != NULL && this!=existingCourse;
  if(isNewCourse){
    aNewCourseEnrollment->setCourse(this);
  }else{
    this->courseEnrollments->push_back(aNewCourseEnrollment);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Course::addCourseEnrollmentAt(Enrollment* aNewCourseEnrollment, int index){
  bool wasAdded= false;
  if(addCourseEnrollment(aNewCourseEnrollment)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfCourseEnrollments();
      if(size>0){
          this->courseEnrollments->erase(this->courseEnrollments->begin()+IndexOf(this->courseEnrollments->begin(), this->courseEnrollments->end(),aNewCourseEnrollment));    
      }
    
      size= this->numberOfCourseEnrollments();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->courseEnrollments->insert(this->courseEnrollments->begin()+ newIndex, aNewCourseEnrollment);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Course::addOrMoveCourseEnrollmentAt(Enrollment* aNewCourseEnrollment, int index){
  bool wasAdded= false;
  if(IndexOf(this->courseEnrollments->begin(), this->courseEnrollments->end(), aNewCourseEnrollment) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfCourseEnrollments();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeCourseEnrollment(aNewCourseEnrollment)| this->addCourseEnrollmentAt(aNewCourseEnrollment, index);
  }else{
    wasAdded= this->addCourseEnrollmentAt(aNewCourseEnrollment, index);
  }
  
  return wasAdded;  
}

Enrollment* Course::addCourseEnrollment(const string aId, EnrollmentStatus aStatus, tm* aEnrolledAt, Student* aStudent){
  return new Enrollment(aId, aStatus, aEnrolledAt, aStudent, this);  
}

vector<Assignment*>* Course::getCourseAssignments(void){
  vector<Assignment*>* copyOfCourseAssignments = new vector<Assignment*>();
  vector<Assignment*>::iterator courseAssignmentsBeginIterator(this->courseAssignments->begin());
  vector<Assignment*>::iterator courseAssignmentsEndIterator(this->courseAssignments->end());
  copyVector(courseAssignmentsBeginIterator, courseAssignmentsEndIterator, copyOfCourseAssignments);
  return copyOfCourseAssignments;  
}

Assignment* Course::getCourseAssignment(int index){
  Assignment* aCourseAssignment= (*(this->courseAssignments))[index];
  return aCourseAssignment;  
}

unsigned int Course::numberOfCourseAssignments(void){
  int number=this->courseAssignments->size();
  return number;  
}

bool Course::hasCourseAssignment(void){
  return this->numberOfCourseAssignments()>0;  
}

int Course::indexOfCourseAssignment(Assignment* aCourseAssignment){
  return IndexOf(this->courseAssignments->begin(), this->courseAssignments->end(), aCourseAssignment);  
}

bool Course::removeCourseAssignment(Assignment* aCourseAssignment){
  bool wasRemoved= false;
  int index= IndexOf(this->courseAssignments->begin(), this->courseAssignments->end(), aCourseAssignment);
  if(index<0){ 
    return wasRemoved;
  }
  //course already at minimum (1)
  if (numberOfCourseAssignments() <= minimumNumberOfCourseAssignments()){
    return false;
  }
  //Unable to remove aCourseAssignment, as it must always have a Course
  if (this== aCourseAssignment->getCourse()){
      return wasRemoved;
  }
  wasRemoved= this->removeCourseAssignmentAt(index);
  return wasRemoved;  
}

bool Course::removeCourseAssignmentAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfCourseAssignments();
  if(index> size){
      return false;
  }
  this->courseAssignments->erase(this->courseAssignments->begin()+index);
  wasRemoved= size== this->numberOfCourseAssignments()+1;
  return wasRemoved;  
}

bool Course::addCourseAssignment(Assignment* aNewCourseAssignment){
  bool wasAdded= false;
  if (IndexOf(this->courseAssignments->begin(), this->courseAssignments->end(), aNewCourseAssignment)> -1) {
    return wasAdded;
  }
  Course* existingCourse = aNewCourseAssignment->getCourse();
  bool isNewCourse= existingCourse != NULL && this!=existingCourse;
  if(isNewCourse){
    if(existingCourse->numberOfCourseAssignments() <= minimumNumberOfCourseAssignments()){
      return wasAdded;
    }
    aNewCourseAssignment->setCourse(this);
  }else{
    this->courseAssignments->push_back(aNewCourseAssignment);
  }
  wasAdded= true;
  return wasAdded;  
}

bool Course::addCourseAssignmentAt(Assignment* aNewCourseAssignment, int index){
  bool wasAdded= false;
  if(addCourseAssignment(aNewCourseAssignment)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfCourseAssignments();
      if(size>0){
          this->courseAssignments->erase(this->courseAssignments->begin()+IndexOf(this->courseAssignments->begin(), this->courseAssignments->end(),aNewCourseAssignment));    
      }
    
      size= this->numberOfCourseAssignments();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->courseAssignments->insert(this->courseAssignments->begin()+ newIndex, aNewCourseAssignment);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Course::addOrMoveCourseAssignmentAt(Assignment* aNewCourseAssignment, int index){
  bool wasAdded= false;
  if(IndexOf(this->courseAssignments->begin(), this->courseAssignments->end(), aNewCourseAssignment) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfCourseAssignments();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeCourseAssignment(aNewCourseAssignment)| this->addCourseAssignmentAt(aNewCourseAssignment, index);
  }else{
    wasAdded= this->addCourseAssignmentAt(aNewCourseAssignment, index);
  }
  
  return wasAdded;  
}

Assignment* Course::addCourseAssignment(const string aId, const string aTitle, tm* aDeadline, const int aMaxScore){
  return new Assignment(aId, aTitle, aDeadline, aMaxScore, this);  
}

vector<CourseCategory*>* Course::getCategories(void){
  vector<CourseCategory*>* copyOfCategories = new vector<CourseCategory*>();
  vector<CourseCategory*>::iterator categoriesBeginIterator(this->categories->begin());
  vector<CourseCategory*>::iterator categoriesEndIterator(this->categories->end());
  copyVector(categoriesBeginIterator, categoriesEndIterator, copyOfCategories);
  return copyOfCategories;  
}

CourseCategory* Course::getCategory(int index){
  CourseCategory* aCategory= (*(this->categories))[index];
  return aCategory;  
}

unsigned int Course::numberOfCategories(void){
  int number=this->categories->size();
  return number;  
}

bool Course::hasCategory(void){
  return this->numberOfCategories()>0;  
}

int Course::indexOfCategory(CourseCategory* aCategory){
  return IndexOf(this->categories->begin(), this->categories->end(), aCategory);  
}

bool Course::removeCategory(CourseCategory* aCategory){
  bool wasRemoved= false;
  int index= IndexOf(this->categories->begin(), this->categories->end(), aCategory);
  if(index<0){ 
    return wasRemoved;
  }
  wasRemoved= this->removeCategoryAt(index);
  if (aCategory->indexOfCourse(this) == -1){
    wasRemoved = true;
  }else{
    wasRemoved = aCategory->removeCourse(this);
    if (!wasRemoved){
      this->categories->insert(this->categories->begin()+ index, aCategory);
    }
  }
  return wasRemoved;  
}

bool Course::removeCategoryAt(int index){
  bool wasRemoved= false;
  if(index<0){
      return wasRemoved;
  }
  
  int size= this->numberOfCategories();
  if(index> size){
      return false;
  }
  this->categories->erase(this->categories->begin()+index);
  wasRemoved= size== this->numberOfCategories()+1;
  return wasRemoved;  
}

bool Course::addCategory(CourseCategory* aNewCategory){
  bool wasAdded= false;
  if (IndexOf(this->categories->begin(), this->categories->end(), aNewCategory)> -1) {
    return wasAdded;
  }
  this->categories->push_back(aNewCategory);
  if (aNewCategory->indexOfCourse(this)!= -1) {
    wasAdded = true;
  }else{
    wasAdded = aNewCategory->addCourse(this);
    if(!wasAdded){
      int currentIndex= this->indexOfCategory(aNewCategory);
      this->categories->erase(this->categories->begin()+currentIndex);
    }
  }
  return wasAdded;  
}

bool Course::addCategoryAt(CourseCategory* aNewCategory, int index){
  bool wasAdded= false;
  if(addCategory(aNewCategory)){
      int newIndex= index;
      if(index < 0 ) { 
          newIndex = 0; 
      }
    
      int size= this->numberOfCategories();
      if(size>0){
          this->categories->erase(this->categories->begin()+IndexOf(this->categories->begin(), this->categories->end(),aNewCategory));    
      }
    
      size= this->numberOfCategories();
      if(newIndex > size) { 
          newIndex = size; 
      }
    
     this->categories->insert(this->categories->begin()+ newIndex, aNewCategory);
      wasAdded = true;
  }
  return wasAdded;  
}

bool Course::addOrMoveCategoryAt(CourseCategory* aNewCategory, int index){
  bool wasAdded= false;
  if(IndexOf(this->categories->begin(), this->categories->end(), aNewCategory) > -1) {
    if (index < 0) {
      index = 0;
    }
  
    int size= this->numberOfCategories();
    if (index > size) {
      index = size - 1;
    }
    
    wasAdded= this->removeCategory(aNewCategory)| this->addCategoryAt(aNewCategory, index);
  }else{
    wasAdded= this->addCategoryAt(aNewCategory, index);
  }
  
  return wasAdded;  
}

Instructor* Course::getInstructor(void){
  return this->instructor;  
}

bool Course::publish(){
  bool wasEventProcessed= false;
  Status aStatus= status;
    
  switch((int)aStatus){	
  	case Status::Draft:
  		if(hasMinimumContent()){	
  			setStatus(Status::Published);
  			wasEventProcessed = true;
  		}
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Course::openEnrollment(){
  bool wasEventProcessed= false;
  Status aStatus= status;
    
  switch((int)aStatus){	
  	case Status::Published:
  		if(hasCapacity()){	
  			setStatus(Status::EnrollmentOpen);
  			wasEventProcessed = true;
  		}
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Course::cancel(){
  bool wasEventProcessed= false;
  Status aStatus= status;
    
  switch((int)aStatus){	
  	case Status::Published:
  		setStatus(Status::Cancelled);
  		wasEventProcessed = true;
  	    break;
  	
  	
  	case Status::EnrollmentOpen:
  		setStatus(Status::Cancelled);
  		wasEventProcessed = true;
  	    break;
  	
  	
  	case Status::Waitlisted:
  		setStatus(Status::Cancelled);
  		wasEventProcessed = true;
  	    break;
  	
  	
  	case Status::InProgress:
  		setStatus(Status::Cancelled);
  		wasEventProcessed = true;
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Course::startCourse(){
  bool wasEventProcessed= false;
  Status aStatus= status;
    
  switch((int)aStatus){	
  	case Status::EnrollmentOpen:
  		if(hasActiveEnrollments()){	
  			setStatus(Status::InProgress);
  			wasEventProcessed = true;
  		}
  	    break;
  	
  	
  	case Status::Waitlisted:
  		if(hasActiveEnrollments()){	
  			setStatus(Status::InProgress);
  			wasEventProcessed = true;
  		}
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

bool Course::complete(){
  bool wasEventProcessed= false;
  Status aStatus= status;
    
  switch((int)aStatus){	
  	case Status::InProgress:
  		setStatus(Status::Completed);
  		wasEventProcessed = true;
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;  
}

string Course::getStatusFullName(void){
  string answer = status;
  return answer;
    
}

Status Course::getStatus(void){
  return this->status;  
}

void Course::setStatus(Status aStatus){
  status= aStatus;
    
}

bool Course::exitStatus(void){
  bool wasEventProcessed = false;
  
    
  switch((int)status){	
  	case Status::Draft:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Status::Published:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Status::EnrollmentOpen:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Status::Waitlisted:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Status::InProgress:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Status::Completed:
  		wasEventProcessed= true;
  	    break;
  	
  	
  	case Status::Cancelled:
  		wasEventProcessed= true;
  	    break;
  
      default:
          // Other states do respond to this event
          break;
  }
  
  
  return wasEventProcessed;
    
}

size_t Course::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Course::~Course(){
  this->deleteAssociatedObjects();
  
  std::for_each(lessons->begin(), lessons->end(), /*std::default_delete<Lesson>*/ deleteElement());
  lessons->clear();
  delete lessons;
  
  std::for_each(courseEnrollments->begin(), courseEnrollments->end(), /*std::default_delete<Enrollment>*/ deleteElement());
  courseEnrollments->clear();
  delete courseEnrollments;
  
  std::for_each(courseAssignments->begin(), courseAssignments->end(), /*std::default_delete<Assignment>*/ deleteElement());
  courseAssignments->clear();
  delete courseAssignments;
  
  std::for_each(categories->begin(), categories->end(), /*std::default_delete<CourseCategory>*/ deleteElement());
  categories->clear();
  delete categories;
  delete instructor;  
}

void Course::deleteAssociatedObjects(void){
  for(unsigned int index = lessons->size(); index > 0 ; index--){
    Lesson* aLesson= (*(this->lessons))[index-1];
    aLesson->deleteAssociatedObjects();
  }
  for(unsigned int index = courseEnrollments->size(); index > 0 ; index--){
    Enrollment* aEnrollment= (*(this->courseEnrollments))[index-1];
    aEnrollment->deleteAssociatedObjects();
  }
  for(unsigned int index = courseAssignments->size(); index > 0 ; index--){
    Assignment* aAssignment= (*(this->courseAssignments))[index-1];
    aAssignment->deleteAssociatedObjects();
  }
  vector<CourseCategory*>* copyOfCategories = new vector<CourseCategory*>();
  vector<CourseCategory*>::iterator categoriesBeginIterator(this->categories->begin());
  vector<CourseCategory*>::iterator categoriesEndIterator(this->categories->end());
  copyVector(categoriesBeginIterator, categoriesEndIterator, copyOfCategories);
  this->categories->clear();
  
  while(this->categories->size()>0){
    CourseCategory* aCourseCategory= (*(copyOfCategories))[0];
    aCourseCategory->removeCourse(this);
  }
  Instructor* placeholderInstructor = this->instructor;
  this->instructor = NULL;
  placeholderInstructor->removeTaughtCourse(this);  
}

//------------------------
// Guard Methods for State Machine
//------------------------

bool Course::hasMinimumContent(void){
  // Check if course has at least 1 lesson and 1 assignment
  return (lessons->size() >= 1 && courseAssignments->size() >= 1);
}

bool Course::hasCapacity(void){
  // Check if capacity is greater than 0
  return (capacity > 0);
}

bool Course::hasActiveEnrollments(void){
  // Check if there is at least one active enrollment
  for(auto enrollment : *courseEnrollments){
    if(enrollment->getStatus() == EnrollmentStatus::Active){
      return true;
    }
  }
  return false;
}

//------------------------
// Business Methods
//------------------------

Enrollment* Course::enroll(Student* student){
  // Only allow enrollment when course is in EnrollmentOpen or Waitlisted state
  if(status != Status::EnrollmentOpen && status != Status::Waitlisted){
    return nullptr;
  }
  
  // Check if student is already enrolled
  for(auto enrollment : *courseEnrollments){
    if(enrollment->getStudent() == student){
      return nullptr; // Already enrolled
    }
  }
  
  // Count current active enrollments
  int activeCount = 0;
  for(auto enrollment : *courseEnrollments){
    if(enrollment->getStatus() == EnrollmentStatus::Active){
      activeCount++;
    }
  }
  
  // Determine enrollment status based on capacity
  EnrollmentStatus enrollmentStatus;
  if(activeCount < capacity){
    enrollmentStatus = EnrollmentStatus::Active;
  } else {
    enrollmentStatus = EnrollmentStatus::Waitlisted;
  }
  
  // Generate enrollment ID
  string studentId = static_cast<User*>(student)->getId();
  string enrollmentId = "ENR_" + id + "_" + studentId;
  
  // Create enrollment with current time
  tm* enrolledAt = new tm();
  time_t now = time(nullptr);
  localtime_r(&now, enrolledAt);
  
  // Create and return the enrollment
  Enrollment* enrollment = new Enrollment(enrollmentId, enrollmentStatus, enrolledAt, student, this);
  
  return enrollment;
}
;

