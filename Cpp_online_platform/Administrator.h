//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__ADMINISTRATOR_H
#define DEF__ADMINISTRATOR_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "Administrator.h"
#endif
#endif

//------------------------
//FILE INCLUDES
//------------------------
#include <model_Model.h>
#include <Administrator.h>
#include <User.h>

class User;
// line 29 "model.ump"
// line 240 "model.ump"
class Administrator : public User{
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		Administrator(const string aId, const string aName, const string aEmail);
		Administrator(Administrator& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const Administrator& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (Administrator& Right, Administrator& Left);
		friend bool operator != (Administrator& Right, Administrator& Left){
		  return !( Right == Left);  
		}
		bool operator == (const Administrator& Right) const{
		  return this == &Right;  
		}
		bool operator != (const Administrator& Right) const{
		  return this != &Right;  
		}
		Administrator& operator=(Administrator& other);
		
		void internalCopy(Administrator& other);
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~Administrator();
		void deleteAssociatedObjects(void);
		string getEmail(void);
		string getName(void);
		string getId(void);
		bool setEmail(const string aNewEmail);
		bool setName(const string aNewName);
		bool setId(const string aNewId);
	
	protected:
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		virtual void toOstream(ostream& os) const;
};


//------------------------
//HASH FUNCTION USE
//------------------------
#include <unordered_map>
namespace std{
    template<> struct hash<Administrator *> {
        size_t operator()(Administrator* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif