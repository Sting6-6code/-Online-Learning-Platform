//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__USER_H
#define DEF__USER_H

#ifdef PRAGMA
#pragma once
#ifndef _MSC_VER
#pragma interface "User.h"
#endif
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


/*
 * ===== ????????? =====
 */
// line 11 "model.ump"
// line 225 "model.ump"
class User{
	
	private:
		
		//User Attributes
		string id;
		string name;
		string email;
	
	public:
		
		//------------------------
		//CONSTRUCTOR
		//------------------------
		User(const string aId, const string aName, const string aEmail);
		User(User& other);
		
		//------------------------
		//STREAM HELPER GROUPDECLARATION
		//------------------------
		friend ostream& operator<<(ostream& os, const User& dt);
		
		//------------------------
		//PREDEFINED OPERATORS
		//------------------------
		friend bool operator == (User& Right, User& Left);
		friend bool operator != (User& Right, User& Left){
		  return !( Right == Left);  
		}
		bool operator == (const User& Right) const{
		  return this == &Right;  
		}
		bool operator != (const User& Right) const{
		  return this != &Right;  
		}
		User& operator=(User& other);
		
		void internalCopy(User& other);
		bool setId(const string aNewId);
		bool setName(const string aNewName);
		bool setEmail(const string aNewEmail);
		string getId(void);
		string getName(void);
		string getEmail(void);
		virtual size_t hashCode(void);
		
		//------------------------
		//DESTRUCTOR
		//------------------------
		virtual ~User();
		void deleteAssociatedObjects(void);
	
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
    template<> struct hash<User *> {
        size_t operator()(User* ptr ) const {
            return ptr->hashCode();
        }
    };
}
#endif