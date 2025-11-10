//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#define DEF__ADMINISTRATOR_BODY

#if defined( PRAGMA ) && ! defined( PRAGMA_IMPLEMENTED )
#pragma implementation <Administrator.h>
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Administrator.h>
#include <User.h>



//------------------------
//CONSTRUCTOR IMPLEMENTATION
//------------------------
Administrator::Administrator(const string aId, const string aName, const string aEmail):User(aId, aName, aEmail){
  //No Implementation in this context  
}

Administrator::Administrator(Administrator& other):User(other){
  internalCopy(other);  
}

//------------------------
//STREAM HELPER GROUP IMPLEMENTATION
//------------------------
void Administrator::toOstream(ostream& os) const{
  User::toOstream(os);  
}

ostream& operator<<(ostream& os, const Administrator& dt){
  dt.toOstream(os);
  return os;  
}

//------------------------
//PREDEFINED OPERATORS IMPLEMENTATION
//------------------------
bool operator == (Administrator& Right, Administrator& Left){
  //if (typeid(Right) != typeid(Left)) {
  //  return false;
  //}
  
  if(Right.hashCode() != Left.hashCode()) {
    return false;
  }  
}

Administrator& Administrator::operator=(Administrator& other){
  internalCopy(other);
  return *this;  
}

void Administrator::internalCopy(Administrator& other){
  //No Implementation in this context  
}

size_t Administrator::hashCode(void){
  return reinterpret_cast<size_t>(this);  
}

//------------------------
//DESTRUCTOR IMPLEMENTATION
//------------------------
Administrator::~Administrator(){
  //User::~User();
  this->deleteAssociatedObjects();  
}

void Administrator::deleteAssociatedObjects(void){
  User::deleteAssociatedObjects();  
}
;

