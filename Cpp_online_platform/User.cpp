//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__USER_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <User.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <User.h>

//------------------------
//LIBRARY INCLUDES
//------------------------
using namespace std;
#include <ostream>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
User::User(const string aId, const string aName, const string aEmail){
  // Validate id is not empty
  if (aId.empty()) {
    throw std::invalid_argument("User id cannot be empty");
  }
  
  // Validate name is not empty
  if (aName.empty()) {
    throw std::invalid_argument("User name cannot be empty");
  }
  
  // Validate email format (must contain @)
  if (aEmail.find('@') == string::npos) {
    throw std::invalid_argument("User email must contain @");
  }
  
  this->id= aId;
  this->name= aName;
  this->email= aEmail;  
}

User::User(User& other){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void User::toOstream(ostream& os) const{
  User* thisptr = const_cast<User*>(this);
  os << "[" << "id:" << thisptr->getId()<< ", " <<
      "name:" << thisptr->getName()<< ", " <<
      "email:" << thisptr->getEmail() << "]";  
}

ostream& operator<<(ostream& os, const User& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (User& Right, User& Left){
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
  if(Right.email.compare(Left.email) != 0){
     return false;
  }  
}

User& User::operator=(User& other){
  internalCopy(other);
  return *this;  
}

void User::internalCopy(User& other){
  this->id.assign(other.id);
  this->name.assign(other.name);
  this->email.assign(other.email);  
}

bool User::setId(const string aNewId){
  bool wasSet= false;
  this->id = aNewId;
  wasSet= true;
  return wasSet;  
}

bool User::setName(const string aNewName){
  bool wasSet= false;
  this->name = aNewName;
  wasSet= true;
  return wasSet;  
}

bool User::setEmail(const string aNewEmail){
  bool wasSet= false;
  this->email = aNewEmail;
  wasSet= true;
  return wasSet;  
}

string User::getId(void){
  return this->id;  
}

string User::getName(void){
  return this->name;  
}

string User::getEmail(void){
  return this->email;  
}

size_t User::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
User::~User(){
  this->deleteAssociatedObjects();  
}

void User::deleteAssociatedObjects(void){
  //No Implementation in this context  
}
;

