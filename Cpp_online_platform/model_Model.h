//PLEASE DO NOT EDIT THIS CODE
//This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language
#ifndef DEF__
#define DEF__

#include <condition_variable>

#if defined(WIN32) || defined(_WIN32) || defined(__WIN32__) || defined(__NT__) || defined(_WIN64)
#define WINDOWS_OS
// NO PREPROCESSOR DEFINITION FOR PRAGMA
#if _MSC_VER
#define PRAGMA
#pragma warning( disable : 4290 )
#endif
#elif defined(hpux) || defined(__hpux) || defined(__hpux__)
#define HPUX_OS
#elif defined(__APPLE__) || defined(macintosh)
#define MAC_OS
#elif defined(bsdi) || defined(__bsdi__)
#define BSD_OS
#endif

#ifdef PRAGMA
#pragma once
#ifdef _MSC_VER
#pragma include_alias("..\\model_Model.h", "model_Model.h")
#pragma include_alias(".\\User.h", "/User.h")
#pragma include_alias(".\\Student.h", "/Student.h")
#pragma include_alias(".\\Instructor.h", "/Instructor.h")
#pragma include_alias(".\\Administrator.h", "/Administrator.h")
#pragma include_alias(".\\Course.h", "/Course.h")
#pragma include_alias(".\\Enrollment.h", "/Enrollment.h")
#pragma include_alias(".\\Lesson.h", "/Lesson.h")
#pragma include_alias(".\\VideoContent.h", "/VideoContent.h")
#pragma include_alias(".\\CourseCategory.h", "/CourseCategory.h")
#pragma include_alias(".\\Assignment.h", "/Assignment.h")
#pragma include_alias(".\\Submission.h", "/Submission.h")
#pragma include_alias(".\\Grade.h", "/Grade.h")
#pragma include_alias(".\\Subscription.h", "/Subscription.h")
#pragma include_alias(".\\Payment.h", "/Payment.h")
#pragma include_alias(".\\Refund.h", "/Refund.h")
#else
#pragma interface "model_Model.h"
#endif
#endif

//------------------------
// PACKAGE FILES DECLARATION
//------------------------
#include <sstream>
#include <cmath>
  
#ifdef WINDOWS_OS
#include <windows.h>
#include <process.h>
#else
#include <errno.h>
#include <pthread.h>
#include <unistd.h>
#include <cstring>
#include <signal.h>
#endif

#ifdef HPUX_OS
#include <sys/pstat.h>
#elif defined MAC_OS
#undef DEBUG
#include <CoreServices/CoreServices.h>
#elif defined BSD_OS
#include <mach/mach_types.h>
#include <sys/systm.h>
#include <sys/types.h>
#include <sys/sysctl.h>
#endif
//------------------------
//USED LIBRARIES
//------------------------
using namespace std;

//------------------------
//USED LIBRARIES
//------------------------
#include <vector>
#include <algorithm>
#include <iostream>
#include <cstring>
#include <iostream>
#include <queue>
#include <iostream>
#include <map>
#include <exception>
#include <stdexcept>
#include <cassert>
#include "stdio.h"
#include <time.h>
#include <ctype.h>
#include <time.h>
#include <ctype.h>
#include <time.h>
#include <ctype.h>
#include <time.h>
#include <ctype.h>
#include <time.h>
#include <ctype.h>
#include <time.h>
#include <ctype.h>
#include <time.h>
#include <ctype.h>
#include <time.h>
#include <ctype.h>
#define Course_ENUM_BEGIN   namespace Course_ENUM{
#define Course_ENUM_END   }
#define Status   ::Course_ENUM::Status_
#define Submission_ENUM_BEGIN   namespace Submission_ENUM{
#define Submission_ENUM_END   }
#define Submission_ENUM_Status   ::Submission_ENUM::Status_
#define Subscription_ENUM_BEGIN   namespace Subscription_ENUM{
#define Subscription_ENUM_END   }
#define Subscription_ENUM_Status   ::Subscription_ENUM::Status_

//------------------------------
//MISSING ENUM DEFINITIONS
//------------------------------
// Enrollment Status Enum (using enum class to avoid name conflicts)
enum class EnrollmentStatus {
    Active,
    Waitlisted,
    Dropped,
    Completed
};

// Payment Status Enum (using enum class to avoid name conflicts)
enum class PaymentStatus {
    Pending,
    Succeeded,
    Failed,
    Refunding,
    Refunded
};

// Plan Type Enum (using enum class to avoid name conflicts)
enum class PlanType {
    Trial,
    Monthly,
    Annual
};

//------------------------------
//NAMESPACES AND PREDEFINITIONS
//------------------------------
#ifdef __cplusplus
Course_ENUM_BEGIN
Course_ENUM_END
Submission_ENUM_BEGIN
Submission_ENUM_END
Subscription_ENUM_BEGIN
Subscription_ENUM_END
#endif 


//is_pointer
template <typename T> struct remove_const_type { typedef T type; };
template <typename T> struct remove_const_type<const T> { typedef T type; };
template <typename T> struct remove_volatile_type { typedef T type; };
template <typename T> struct remove_volatile_type<volatile T> { typedef T type; };
template <typename T> struct removeType : remove_const_type<typename remove_volatile_type<T>::type> {};
template <typename T> struct is_ptr_type { enum { value = false }; };
template <typename T> struct is_ptr_type<T*> { enum { value = true }; };
template <typename T> struct is_ptr : is_ptr_type<typename removeType<T>::type> {};


#define PLACE_HOLDER      int
#define USECS_PER_MSEC      1000
#define MUSECS_PER_SEC      1000
#define USECS_PER_SEC      1000000

#define INSTANCEOF(object, clazz)  !dynamic_cast<clazz*>(object)
#define ARGUMENT_UPPER_LIMIT      10
#define EMPTY()  
#define COMMA() ,
#define SEMICOLON() ;
#define TYPENAME_ARGS(i, value) typename ArgumentType##i
#define TYPENAME_VALUE_ARGS(i, value) typename ArgumentType##i=value
#define INIT_VALUE_ARG(i, name) this->_##name##i=name##i
#define SER_ARG(i, name) _##name##i=transport[i - 1]
#define DES_ARG(i, name) transport[i - 1]=_##name##i
#define NAMED_ARG(i, name) name##i 
#define MEMBER_ARG(i, name) ArgumentType##i name##i
#define INIT_MEMBER_ARG(i, name) _##name##i(name##i)
#define VOID_ARG(i, value) void
#define CAT(a, ...) a ## __VA_ARGS__
#define REPEAT_DEC(count ,macro, split, ...) CAT(REPEAT_DEC_,count)(macro, split, __VA_ARGS__)
#define REPEAT_DEC_1(macro, split, ...) 
#define REPEAT_DEC_2(macro, split, ...)  macro(1, __VA_ARGS__)
#define REPEAT_DEC_3(macro, split, ...)  macro(2, __VA_ARGS__) split() REPEAT_DEC_2(macro, split, __VA_ARGS__)
#define REPEAT_DEC_4(macro, split, ...)  macro(3, __VA_ARGS__) split() REPEAT_DEC_3(macro, split, __VA_ARGS__)
#define REPEAT_DEC_5(macro, split, ...)  macro(4, __VA_ARGS__) split() REPEAT_DEC_4(macro, split, __VA_ARGS__)
#define REPEAT_DEC_6(macro, split, ...)  macro(5, __VA_ARGS__) split() REPEAT_DEC_5(macro, split, __VA_ARGS__)
#define REPEAT_DEC_7(macro, split, ...)  macro(6, __VA_ARGS__) split() REPEAT_DEC_6(macro, split, __VA_ARGS__)
#define REPEAT_DEC_8(macro, split, ...)  macro(7, __VA_ARGS__) split() REPEAT_DEC_7(macro, split, __VA_ARGS__)
#define REPEAT_DEC_9(macro, split, ...)  macro(8, __VA_ARGS__) split() REPEAT_DEC_8(macro, split, __VA_ARGS__)
#define REPEAT_DEC_10(macro, split, ...) macro(9, __VA_ARGS__) split() REPEAT_DEC_9(macro, split, __VA_ARGS__)
#define REPEAT_DEC_11(macro, split, ...) macro(10, __VA_ARGS__) split() REPEAT_DEC_10(macro, split, __VA_ARGS__)
#define REPEAT_DEC_12(macro, split, ...) macro(11, __VA_ARGS__) split() REPEAT_DEC_11(macro, split, __VA_ARGS__)
#define REPEAT_DEC_13(macro, split, ...) macro(12, __VA_ARGS__) split() REPEAT_DEC_12(macro, split, __VA_ARGS__)
#define REPEAT_DEC_14(macro, split, ...) macro(13, __VA_ARGS__) split() REPEAT_DEC_13(macro, split, __VA_ARGS__)
#define REPEAT_DEC_15(macro, split, ...) macro(14, __VA_ARGS__) split() REPEAT_DEC_14(macro, split, __VA_ARGS__)
#define REPEAT_DEC_16(macro, split, ...) macro(15, __VA_ARGS__) split() REPEAT_DEC_15(macro, split, __VA_ARGS__)

#define REPEAT_INC(count, macro, split, ...) CAT(REPEAT_INC_,count)(macro, split, __VA_ARGS__)
#define REPEAT_INC_1(macro, split, ...)  macro(1, __VA_ARGS__)
#define REPEAT_INC_2(macro, split, ...)  REPEAT_INC_1(macro, split, __VA_ARGS__) split() macro(2, __VA_ARGS__)
#define REPEAT_INC_3(macro, split, ...)  REPEAT_INC_2(macro, split, __VA_ARGS__) split() macro(3, __VA_ARGS__)
#define REPEAT_INC_4(macro, split, ...)  REPEAT_INC_3(macro, split, __VA_ARGS__) split() macro(4, __VA_ARGS__)
#define REPEAT_INC_5(macro, split, ...)  REPEAT_INC_4(macro, split, __VA_ARGS__) split() macro(5, __VA_ARGS__)
#define REPEAT_INC_6(macro, split, ...)  REPEAT_INC_5(macro, split, __VA_ARGS__) split() macro(6, __VA_ARGS__)
#define REPEAT_INC_7(macro, split, ...)  REPEAT_INC_6(macro, split, __VA_ARGS__) split() macro(7, __VA_ARGS__)
#define REPEAT_INC_8(macro, split, ...)  REPEAT_INC_7(macro, split, __VA_ARGS__) split() macro(8, __VA_ARGS__)
#define REPEAT_INC_9(macro, split, ...)  REPEAT_INC_8(macro, split, __VA_ARGS__) split() macro(9, __VA_ARGS__)
#define REPEAT_INC_10(macro, split, ...)  REPEAT_INC_9(macro, split, __VA_ARGS__) split() macro(10, __VA_ARGS__)
#define REPEAT_INC_11(macro, split, ...) REPEAT_INC_10(macro, split, __VA_ARGS__) split() macro(11, __VA_ARGS__)
#define REPEAT_INC_12(macro, split, ...) REPEAT_INC_11(macro, split, __VA_ARGS__) split() macro(12, __VA_ARGS__)
#define REPEAT_INC_13(macro, split, ...) REPEAT_INC_12(macro, split, __VA_ARGS__) split() macro(13, __VA_ARGS__)
#define REPEAT_INC_14(macro, split, ...) REPEAT_INC_13(macro, split, __VA_ARGS__) split() macro(14, __VA_ARGS__)
#define REPEAT_INC_15(macro, split, ...) REPEAT_INC_14(macro, split, __VA_ARGS__) split() macro(15, __VA_ARGS__)
#define REPEAT_INC_16(macro, split, ...) REPEAT_INC_15(macro, split, __VA_ARGS__) split() macro(16, __VA_ARGS__)

#define VAR_TYPES(N) REPEAT_INC(N, TYPENAME_ARGS, COMMA)
#define VAR_TYPES_DEFAULT(N,VALUE) REPEAT_INC(N, TYPENAME_VALUE_ARGS, COMMA, VALUE)
#define VAR_ARGS(N) REPEAT_INC(N, NAMED_ARG, COMMA, ArgumentType)
#define VAR_NAMED_ARGS(N, name) REPEAT_INC(N, NAMED_ARG, COMMA, name)
#define VOID_ARGS(N) REPEAT_INC(N, VOID_ARG, COMMA)

#define VAR_ARGS_MEMBERS(N, name, delim) REPEAT_INC(N, MEMBER_ARG, delim, name)
#define INIT_VAR_ARGS_MEMBERS(N, name) REPEAT_INC(N, INIT_MEMBER_ARG, COMMA, name)
#define INIT_VALUE_ARGS(N, name) REPEAT_INC(N, INIT_VALUE_ARG, SEMICOLON, name)

#define SERIALIZE_ARGS(N, name) REPEAT_INC(N, SER_ARG, SEMICOLON, name)
#define DESERIALIZE_ARGS(N, name) REPEAT_INC(N, DES_ARG, SEMICOLON, name)

#define GENERATE_METHOD_CALLBACK_SIGNATURES_ARGUMENTS(N, value)      \
template<typename Caller, typename ReturnType, VAR_TYPES(N)>         \
struct MethodCallbackSignature<Caller,ReturnType, VAR_ARGS(N)> {  \
typedef ReturnType(Caller::*Method)(VAR_ARGS(N)); };        \
template<typename Caller, VAR_TYPES(N)>                  \
struct MethodCallbackSignature<Caller, void, VAR_ARGS(N)> {     \
typedef void (Caller::*Method)(VAR_ARGS(N)); };

#define GENERATE_METHOD_CALLBACK_INVOKE_ARGUMENTS(N, value)                                      \
template <class BASE, class Caller, class FutureResultType, class ReturnType, VAR_TYPES(N)>                    \
class MethodCallbackInvoke<BASE,Caller,FutureResultType,ReturnType, VAR_ARGS(N)>                        \
: public BaseMethodCallbackInvoke<BASE, Caller, FutureResultType, ReturnType> {public:                  \
typedef typename MethodCallbackSignature<Caller, ReturnType, VAR_ARGS(N)>::Method Callback;                  \
MethodCallbackInvoke(Caller* caller, Callback method, VAR_ARGS_MEMBERS(N, arg, COMMA), const FutureResultType& result) :  \
BaseMethodCallbackInvoke<BASE, Caller, FutureResultType, ReturnType>(caller, result), _method(method), INIT_VAR_ARGS_MEMBERS(N, arg) {}                \
protected: VAR_ARGS_MEMBERS(N, _arg, SEMICOLON); Callback _method; };

#define GENERATE_DELEGATE_INVOKE_ARGUMENTS(N, value)                                  \
template <class BASE, class Caller, class FutureType, class ReturnType, VAR_TYPES(N)>                \
class DelegateInvoke<BASE, Caller, FutureType, ReturnType, VAR_ARGS(N)> :                    \
public MethodCallbackInvoke<BASE, Caller, FutureType, ReturnType, VAR_ARGS(N)> {public:                \
DelegateInvoke(Caller* caller, typename DelegateInvoke::Callback method, VAR_ARGS_MEMBERS(N, arg, COMMA), const FutureType& result)  \
: MethodCallbackInvoke<BASE, Caller, FutureType, ReturnType, VAR_ARGS(N)>(caller, method, VAR_NAMED_ARGS(N, arg), result) {}                  \
void invokeMethod() { this -> _result.resolveData(new ReturnType((this -> _context->*this -> _method)(VAR_NAMED_ARGS(N, this->_arg))));}};  \
template <class BASE, class Caller, class FutureType, VAR_TYPES(N)>                          \
class DelegateInvoke<BASE, Caller, FutureType, void, VAR_ARGS(N)> :                        \
public MethodCallbackInvoke<BASE, Caller, FutureType, void, VAR_ARGS(N)>{public:                  \
DelegateInvoke(Caller* caller, typename DelegateInvoke::Callback method, VAR_ARGS_MEMBERS(N, arg, COMMA), const FutureType& result)  \
: MethodCallbackInvoke<BASE, Caller, FutureType, void, VAR_ARGS(N)>(caller, method, VAR_NAMED_ARGS(N, arg), result) {}                  \
void invokeMethod() { (this -> _context->*this -> _method)(VAR_NAMED_ARGS(N, this->_arg));}};

#define GENERATE_DELEGATE_ARGUMENTS(N, value)                                              \
template <class Caller, class ReturnType, VAR_TYPES(N)> class Delegate<Caller, ReturnType, VAR_ARGS(N)> :            \
public DelegateInvoke < DelegateBase, Caller, FutureResult<ReturnType>, ReturnType, VAR_ARGS(N) > {              \
public:  Delegate(Caller* caller, typename Delegate::Callback method, VAR_ARGS_MEMBERS(N, arg, COMMA), const FutureResult<ReturnType>& result)  \
: DelegateInvoke< DelegateBase, Caller, FutureResult<ReturnType>, ReturnType, VAR_ARGS(N) >(caller, method, VAR_NAMED_ARGS(N, arg), result) {} };                      \
template <class Caller, VAR_TYPES(N)> class Delegate<Caller, void, VAR_ARGS(N)> :                        \
public DelegateInvoke < DelegateBase, Caller, FutureResult<void>, void, VAR_ARGS(N) >{                    \
public:  Delegate(Caller* caller, typename Delegate::Callback method, VAR_ARGS_MEMBERS(N, arg, COMMA), const FutureResult<void>& result)      \
: DelegateInvoke< DelegateBase, Caller, FutureResult<void>, void, VAR_ARGS(N) >(caller, method, VAR_NAMED_ARGS(N, arg), result) {}};

#define GENERATE_MULTICAST_ARGUMENTS(N, value)                                                            \
template<class ReturnType, VAR_TYPES(N)>                                                          \
class IDelegatePublisher<ReturnType, VAR_ARGS(N)> {public:                                                  \
virtual FutureResult<ReturnType> publish(VAR_ARGS_MEMBERS(N, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) = 0;            \
};                                                                              \
template <class ReturnType, VAR_TYPES(N)>                                                          \
class MulticastDelegate<ReturnType, VAR_ARGS(N)> : public IDelegatePublisher<ReturnType, VAR_ARGS(N)>{private:                        \
typedef std::vector< IDelegatePublisher<ReturnType, VAR_ARGS(N)>* > SubscribersList;                                  \
SubscribersList subscribers;                                                              \
public:                                                                            \
MulticastDelegate() {}                                                                  \
MulticastDelegate& operator += (IDelegatePublisher<ReturnType, VAR_ARGS(N)>* method) {                                  \
subscribers.push_back(method);                                                            \
return *this;}                                                                    \
void notify(VAR_ARGS_MEMBERS(N, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) {        \
typename SubscribersList::iterator it = subscribers.begin();                                            \
for (; it != subscribers.end(); it++) { (*it)->publish(VAR_NAMED_ARGS(N, arg), priority, delay, timeout); }  }    \
FutureResult<ReturnType> operator () (VAR_ARGS_MEMBERS(N, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) {              \
FutureResult<ReturnType> result = publish(VAR_NAMED_ARGS(N, arg), priority, delay, timeout);                            \
notify(VAR_NAMED_ARGS(N, arg), priority, delay, timeout);                      \
return result;}};

#define GENERATE_ACTIVE_ARGUMENTS(N, value)                                                                              \
template <class Caller, class ReturnType, VAR_TYPES(N)> class Active<Caller, ReturnType, VAR_ARGS(N)> : public ActiveConstraintUID, public MulticastDelegate<ReturnType, VAR_ARGS(N)> { public:  \
typedef Delegate<Caller, ReturnType, VAR_ARGS(N)> DelegateType;                                                                \
typedef typename MethodCallbackSignature<Caller, ReturnType, VAR_ARGS(N)>::Method Callback;                                                  \
Active(Caller* caller, Scheduler<Caller>* sch, Callback method) :_context(caller), _sch(sch), _method(method) {}                                      \
FutureResult<ReturnType> operator () (VAR_ARGS_MEMBERS(N, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) {              \
FutureResult<ReturnType> result = publish(VAR_NAMED_ARGS(N, arg), priority, delay, timeout); return result;};                            \
FutureResult<ReturnType> publish(VAR_ARGS_MEMBERS(N, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) {                                    \
FutureResult<ReturnType> result(new FutureObject<ReturnType>());                                                            \
DelegateBase::Ptr pDelegate(new DelegateType(_context, _method, VAR_NAMED_ARGS(N, arg), result));                                            \
_sch->schedule(pDelegate,priority,delay, timeout, _guardId, _conditionId);                                                        \
this->notify(VAR_NAMED_ARGS(N, arg), priority, delay, timeout);                \
return result;} private: Caller* _context; Scheduler<Caller>* _sch; Callback _method; };


#define GENERATE_METHOD_CALLBACK_SIGNATURES(N)  REPEAT_DEC(N, GENERATE_METHOD_CALLBACK_SIGNATURES_ARGUMENTS, EMPTY)
#define GENERATE_METHOD_CALLBACK_INVOKE(N)  REPEAT_DEC(N, GENERATE_METHOD_CALLBACK_INVOKE_ARGUMENTS, EMPTY)
#define GENERATE_DELEGATE_INVOKE(N)  REPEAT_DEC(N, GENERATE_DELEGATE_INVOKE_ARGUMENTS, EMPTY)
#define GENERATE_DELEGATE(N)  REPEAT_DEC(N, GENERATE_DELEGATE_ARGUMENTS, EMPTY)
#define GENERATE_MULTICAST_METHOD(N)  REPEAT_DEC(N, GENERATE_MULTICAST_ARGUMENTS, EMPTY)
#define GENERATE_ACTIVE_METHOD(N)  REPEAT_DEC(N, GENERATE_ACTIVE_ARGUMENTS, EMPTY)


#ifdef WINDOWS_OS
#define isnan(x) _isnan(x)
#define isinf(x) (!_finite(x))

#define SOCKET_TYPE            SOCKET
#define CLOSE_SOCKET(arg) \
  closesocket(arg)

#define EVENT_TYPE            HANDLE
#define CONDITION_TYPE          PLACE_HOLDER
#define THREAD_TYPE             HANDLE
#define THREAD_RETURN_TYPE        unsigned WINAPI
#define THREAD_ERROR_INSTANCE(returnValue)  ((returnValue) == NULL)
#define THREAD_ERROR_CODE(value)      GetLastError()

#define MUTEX_CRITICAL_SECTION      CRITICAL_SECTION
#define START_MUTEX_FUNCTION(arg) \
  InitializeCriticalSection((arg))

#define TERMINATE_MUTEX_FUNCTION(arg) \
  DeleteCriticalSection((arg))

#define LOCK_MUTEX_FUNCTION(arg) \
  EnterCriticalSection((arg))

#define UNLOCK_MUTEX_FUNCTION(arg) \
  LeaveCriticalSection((arg))

#define START_EVENT_TYPE_FUNCTION(mutex, cond, reset) \
  mutex = CreateEvent(NULL, reset, FALSE, NULL);    \
  if (!mutex)                      \
    throw ThreadException("mutex signal failed")

#define TERMINATE_EVENT_FUNCTION(mutex, cond) \
  CloseHandle(mutex)

#define WAIT_EVENT_FUNCTION(mutex, cond, wakeup)    \
  switch(WaitForSingleObject(mutex, INFINITE)) {    \
  case WAIT_OBJECT_0:                  \
    return;                      \
  default:                      \
    throw ThreadException("wait event failed");    \
  }

#define WAIT_TIME_EVENT_FUNCTION(mutex, cond, time, wakeup, reset, status)      \
  switch (WaitForSingleObject(mutex, time + 1))                  \
  {                                        \
  case WAIT_OBJECT_0:                                \
    status = true;                                \
    break;                                        \
  case WAIT_TIMEOUT:                                \
    status = false;                                \
    break;                                        \
  default:                                    \
    throw ThreadException("wait failed");                      \
  }

#define WAKEUP_EVENT_FUNCTION(mutex, cond, wakeup) \
  SetEvent(mutex)

#define THREAD_JOIN_FUNCTION(hdl)        WaitForSingleObject(hdl, INFINITE)
#define THREAD_SLEEP_FUNCTION(ms)        Sleep((ms))

#define THREAD_CREATE_FUNCTION(id, funPtr, callPtr)  id =(HANDLE)CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)funPtr,callPtr,0L,NULL)

#define THREAD_TERMINATE_FUNCTION(hdl, status)  TerminateThread(hdl, 0)

#define THREAD_CANCEL_FUNCTION(hdl)  TerminateThread(hdl, 0)

#define IS_THREAD_ALIVE_FUNCTION(hdl, isRunning)  \
  DWORD exitCode = 0;                \
  if(GetExitCodeThread(hdl, &exitCode))       \
    isRunning = (exitCode == STILL_ACTIVE)

#define BROADCAST_FUNCTION(arg) 0

#define SET_EVENT_FUNCTION(arg) \
  SetEvent((arg))

#define RESET_EVENT_FUNCTION(arg) \
  ResetEvent((arg))

#define LOCK_MUTEX_EVENT_FUNCTION(arg) 0
#define UNLOCK_MUTEX_EVENT_FUNCTION(arg) 0
  
#else
typedef int                 BOOL;

#ifndef FALSE
#define FALSE               0
#endif

#ifndef TRUE
#define TRUE                1
#endif
#define SOCKET_TYPE            int
#define CLOSE_SOCKET(arg) \
  close(arg)

#define EVENT_TYPE              pthread_mutex_t
#define CONDITION_TYPE            pthread_cond_t

#define THREAD_TYPE              pthread_t
#define THREAD_RETURN_TYPE          void *

#define THREAD_ERROR_INSTANCE(returnValue)  ((returnValue) == NULL)
#define THREAD_ERROR_CODE(value)      errno

#define MUTEX_CRITICAL_SECTION          pthread_mutex_t
#define START_MUTEX_FUNCTION(arg)  \
  pthread_mutex_init ((arg), NULL)

#define TERMINATE_MUTEX_FUNCTION(arg) \
  pthread_mutex_destroy((arg))

#define LOCK_MUTEX_FUNCTION(arg)  \
  pthread_mutex_lock((arg))

#define UNLOCK_MUTEX_FUNCTION(arg) \
  pthread_mutex_unlock((arg))

#define START_EVENT_TYPE_FUNCTION(mutex, cond, reset) \
   if (pthread_mutex_init(&mutex, NULL))        \
    throw ThreadException("mutex signal failed");  \
   pthread_cond_init(&cond, NULL)

#define TERMINATE_EVENT_FUNCTION(mutex, cond) \
  pthread_cond_destroy(&cond); \
  pthread_mutex_destroy(&mutex)

#define WAIT_EVENT_FUNCTION(mutex, cond, wakeup)    \
  pthread_mutex_lock(&mutex);              \
  int err = 0;                    \
  while (!wakeup) {                        \
    err = pthread_cond_wait(&cond, &mutex);      \
    if (err) {                    \
      pthread_mutex_unlock(&mutex);          \
      throw ThreadException("wait event failed"); \
    }                        \
  }                          \
  wakeup = FALSE;                    \
  pthread_mutex_unlock(&mutex)


#define WAIT_TIME_EVENT_FUNCTION(mutex, cond, ms, wakeup, reset, status)      \
struct timeval tv                                \
struct timespec tdif                              \
gettimeofday(&tv, NULL)                              \
tdif.tv_sec  = tv.tv_sec + ms / MUSECS_PER_SEC                  \
tdif.tv_nsec = tv.tv_usec*MUSECS_PER_SEC + (ms % MUSECS_PER_SEC)*USECS_PER_SEC  \
if (tdif.tv_nsec >= NSECS_PER_SEC) {                      \
tdif.tv_nsec -= NSECS_PER_SEC                        \
tdif.tv_sec++                                \
}                                    \
pthread_mutex_lock(&mutex)                            \
while (!wakeup)                              \
{                              \
status = pthread_cond_timedwait(&cond, &mutex, &tdif)            \
if(status) {                                \
if (status == ETIMEDOUT) break;                      \
pthread_mutex_unlock(&mutex)                      \
throw ThreadException(get_error(status))                \
}                              \
}                              \
wakeup =  status == 0 && reset ? false : wakeup                  \
pthread_mutex_unlock(&mutex)

#define WAKEUP_EVENT_FUNCTION(mutex, cond, wakeup)  \
  pthread_mutex_lock(&mutex);            \
  wakeup = TRUE;                  \
  pthread_cond_signal(&cond);            \
  pthread_mutex_unlock(&mutex)

#define THREAD_JOIN_FUNCTION(id)  pthread_join(id, NULL)
#define THREAD_SLEEP_FUNCTION(ms) \
  struct timeval tv; \
    tv.tv_usec = (ms % MUSECS_PER_SEC) * USECS_PER_MSEC; \
    tv.tv_sec = ms / MUSECS_PER_SEC; \
    select(0, NULL, NULL, NULL, &tv)

#define THREAD_CREATE_FUNCTION(id, funPtr, callPtr)        \
  pthread_attr_t attr;                    \
  pthread_attr_init(&attr);                  \
  pthread_attr_setdetachstate(&attr,PTHREAD_CREATE_DETACHED);  \
  pthread_attr_setinheritsched(&attr, PTHREAD_INHERIT_SCHED);  \
  pthread_create(&id, &attr, funPtr, callPtr);        \
  pthread_attr_destroy(&attr)

#define THREAD_TERMINATE_FUNCTION(hdl, status)  pthread_kill(hdl, status)
#define THREAD_CANCEL_FUNCTION(Id)          \
  if (pthread_cancel(Id) == 0)          \
    pthread_detach(Id);

#define IS_THREAD_ALIVE_FUNCTION(hdl, isRunning)  \
  isRunning = (pthread_kill(hdl, 0) == 0)

#define BROADCAST_FUNCTION(arg) \
  pthread_cond_broadcast((arg))

#define SET_EVENT_FUNCTION(arg) 1

#define RESET_EVENT_FUNCTION(arg) 1

#define LOCK_MUTEX_EVENT_FUNCTION(arg)  \
  pthread_mutex_lock((arg))

#define UNLOCK_MUTEX_EVENT_FUNCTION(arg) \
  pthread_mutex_unlock((arg))

#endif

#define CREATE_THREAD(id, funPtr, callPtr)                \
  THREAD_CREATE_FUNCTION(id, funPtr, callPtr);            \
  if(THREAD_ERROR_INSTANCE(id))                    \
    throw ThreadException(ErrorMessage(THREAD_ERROR_CODE(id)))

static std::string ErrorMessage(int errorCode){
  string str = "";
  if (errorCode == 0) {
    return str;
  }

  char const* what = "Error Numer";
  int whlen = strlen(what);
  int ncode = errorCode, dlen = 1;
  while (ncode !=0) {dlen++; ncode/=10;}
  char *buffer = (char *) malloc(sizeof(char) * (whlen + dlen + 1));
    sprintf(buffer, "%s = %d", what, errorCode);
  str = buffer;
  return str;
}

class Exception : public std::exception {
  friend ostream& operator<<(ostream& output, const Exception& ex) {
    output << "Exception: " << ex.error;
    return output;
  }
public:
  Exception() throw() : error(std::string("Exception")) {}
  Exception(std::string err) throw() : error(err) {}
  Exception(const Exception& source) throw() : std::exception(source) { error = source.error; }
  virtual ~Exception() throw () {}
  Exception& operator=(const Exception& source) throw() {
    if (&source != this) {
      error = source.error;
    }
    std::exception::operator= (source);
    return *this;
  }
  void setError(std::string exce) { error = exce; }
  virtual const char* what() const throw() { return error.c_str(); }
protected:
  std::string error;
};


struct ThreadException : public Exception{
public:
  ThreadException() : Exception() {}
  ThreadException(char* errorMessage) : Exception(errorMessage) {}
  ThreadException(std::string errorMessage) : Exception(errorMessage) {}
};

template <typename T>
void* ConvertToFunctionPointer(T x) {
  return *reinterpret_cast<void**>(&x);
}

struct Runnable {
  virtual void run() = 0;
};

class AtomicMutex{
  public:
  AtomicMutex() { START_MUTEX_FUNCTION(&section); }
  ~AtomicMutex() { TERMINATE_MUTEX_FUNCTION(&section); }
    void lock() { LOCK_MUTEX_FUNCTION(&section); }
    void unlock() { UNLOCK_MUTEX_FUNCTION(&section); }

  private:
  MUTEX_CRITICAL_SECTION section;
};

static AtomicMutex atomicMutex;

class MutexLock{
public:

  MutexLock() : m_value(0){
    wakeup = FALSE;
    START_EVENT_TYPE_FUNCTION(mutex, cond, FALSE);
  }

  MutexLock(const MutexLock& m) {
    this->m_value = m.m_value;
    this->wakeup = m.wakeup;
    this->mutex = m.mutex;
    this->cond = m.cond;
  }

  ~MutexLock(){
    TERMINATE_EVENT_FUNCTION(mutex, cond);
  }

  void wait() {
    WAIT_EVENT_FUNCTION(mutex, cond, wakeup);
  }

  void wakeUp() {
    WAKEUP_EVENT_FUNCTION(mutex, cond, wakeup);
  }

  void lock() {
    bool canBeAccessed = this->verifyLock();
    if (canBeAccessed){
      return;
    }

    atomicMutex.lock();
    canBeAccessed = m_value == 0;
    m_value += 1;
    atomicMutex.unlock();

    if (!canBeAccessed){
      wait();
      atomicMutex.lock();
      m_value -= 1;
      atomicMutex.unlock();
    }

  }

  bool isLocked() {
    atomicMutex.lock();
    bool canBeAccessed = m_value == 0;
    atomicMutex.unlock();
    return !canBeAccessed;
  }

  bool verifyLock(int set = 0) {
    atomicMutex.lock();
    bool canBeAccessed = m_value == 0;
    if (m_value == set) {
      m_value = !set;
      canBeAccessed = true;
    }
    atomicMutex.unlock();
    return canBeAccessed;
  }

  void unlock() {
    if (!verifyLock(1)){
      wakeUp();
    }
  }

private:
  volatile int m_value;
  volatile bool wakeup;

  EVENT_TYPE mutex;
  CONDITION_TYPE  cond;
};

#define synchronized(L)                                               \
        for(L.lock();L.isLocked();                    \
      L.unlock() )

struct ThreadParameters
{
    void* runCall;
    void* context;
    ThreadParameters(void* ctx, void* callPtr) : context(ctx), runCall(callPtr) {}
};


class Thread: public Runnable {

public:
    Thread(string threadName = "") : runnableObject(NULL), name(threadName), thrParams(this,ConvertToFunctionPointer(&Thread::run))
            , _threadHdl(0),_isRunning(false),_isTerminated(false) {}
    Thread(Runnable *target, string threadName = "") :  name(threadName),thrParams(this,ConvertToFunctionPointer(&Thread::run)) , runnableObject(target) {}
    Thread(void* funcPtr, void* ctx = 0 ,string threadName = "") : runnableObject(NULL), name(threadName), thrParams(ctx,funcPtr) {}
    Thread(void (*funcPtr)(void*), void* ctx = 0, string threadName = "") : runnableObject(NULL),name(threadName), thrParams(ctx,ConvertToFunctionPointer(funcPtr)) {}
    Thread(void (*funcPtr)(), string threadName = "") : runnableObject(NULL),name(threadName), thrParams(this,ConvertToFunctionPointer(funcPtr)) {}
    template<class T>
    Thread(void (T::*RunnableCall)(), string threadName = "") : runnableObject(NULL), name(threadName), thrParams(this,ConvertToFunctionPointer(RunnableCall)) {}
    template<class T>
    Thread(void (T::*RunnableCall)(void*), void* ctx = 0, string threadName = "") : runnableObject(NULL), name(threadName), thrParams(ctx,ConvertToFunctionPointer(RunnableCall)) {}
    virtual ~Thread() {
        if(isRunning()) THREAD_TERMINATE_FUNCTION(_threadHdl, 0);
    }

    static void sleep(long ms) throw(ThreadException) {
        THREAD_SLEEP_FUNCTION(ms);
    }

    THREAD_TYPE getId() const {
        return this->_threadHdl;
    }

    static THREAD_RETURN_TYPE threadFunctionPointer(void* ptr) {
        ThreadParameters* threadParameters = (ThreadParameters*)ptr;
        if(threadParameters->context != NULL) {
            Runnable* run = (Thread*) threadParameters->context;
            run->run();
            //((void (*)(void*)) threadParameters->runCall)(threadParameters->context);
        } else {
            ((void (*)(void)) threadParameters->runCall)();
        }

        return 0;
    }

    bool isFinished() {
        return !this->isRunning();
    }

    bool isTerminated() {
        synchronized(lock) {
            return this->_isTerminated;
        }
        return 0;
    }

    bool isRunning() {
        synchronized(lock) {
            if(this->_isRunning) {
                IS_THREAD_ALIVE_FUNCTION(_threadHdl,_isRunning);
            }
            return this->_isRunning;
        }

        return 0;
    }

    virtual void run() {
        if(this->runnableObject != NULL) {
            runnableObject->run();
        }
    }

    virtual void stop() {
        synchronized(lock) {
            THREAD_CANCEL_FUNCTION(_threadHdl);
            _isTerminated = true;
            _isRunning = false;
        }
    }

    string getName() const {
        return name;
    }

    void setName(string name) {
        this->name = name;
    }

    virtual void start(Runnable *target) throw(ThreadException) {
        runnableObject = target;
        start();
    }

    virtual void start() throw(ThreadException) {
        synchronized(lock) {
            reset();
            CREATE_THREAD(_threadHdl,Thread::threadFunctionPointer, &thrParams);
            _isRunning = true;
        }
    }

    void join() throw(ThreadException) {
        THREAD_JOIN_FUNCTION(_threadHdl);
    }

    void wait() throw(ThreadException) {
        lock.wait();
    }

    void wakeUp() throw(ThreadException) {
        lock.wakeUp();
    }

private:
    mutable MutexLock lock;
    THREAD_TYPE _threadHdl;
    string name;

    ThreadParameters thrParams;
    Runnable* runnableObject;

    bool _isRunning;
    bool _isTerminated;

    void reset() {
        _threadHdl = 0;
        _isRunning = false;
        _isTerminated = false;
    }
};

template <class PT>
class AutoPtr {
public:
  AutoPtr(PT* p = NULL, bool shared = false) : _ptr(NULL) { _ptr=p; if (shared && _ptr) _ptr->reference(); }
  AutoPtr(const AutoPtr& ptr) : _ptr(NULL) { _ptr=ptr._ptr; if (_ptr) _ptr->reference(); }
  ~AutoPtr() { if (_ptr) _ptr->release(); }
  AutoPtr& operator = (const AutoPtr& ptr) {
    if (&ptr != this) {
      if (_ptr) _ptr->release();
      _ptr = ptr._ptr;
      if (_ptr) _ptr->reference();
    }
    return *this;
  }
  PT* operator -> () {
    if (_ptr)
      return _ptr;
    else
      throw std::runtime_error("Null Pointer Exception");
  }
  PT* reference() { if (_ptr) _ptr->reference(); return _ptr; }
  PT* value() { return _ptr; }
  operator const PT* () const { return _ptr; }
  bool operator == (const AutoPtr& ptr) const { return _ptr == ptr._ptr; }
  bool operator != (const AutoPtr& ptr) const { return _ptr != ptr._ptr; }
private:
  PT* _ptr;
};

class ReferenceObject {
private:
  mutable MutexLock mutex;
  mutable volatile int referenceNumber;
public:
  ReferenceObject() {
    synchronized(mutex) {
      referenceNumber = 1;
    }
  }
  void reference() const {
    synchronized(mutex) {
      ++referenceNumber;
    }
  }
  void release() const {
    synchronized(mutex) {
      --referenceNumber;
    }
    if (referenceNumber == 0) {
      delete this;
    }
  }
  size_t size() const {
    int val = 0;
    synchronized(mutex) {
      val = referenceNumber;
    }
  }
protected:
  virtual ~ReferenceObject() {}
};
template <typename Object> struct RefPointer : public ReferenceObject { typedef AutoPtr<Object> Ptr; };

class AutoLock {
public:
  explicit AutoLock(MutexLock& mutex) : _mutex(mutex) { _mutex.lock(); }
  ~AutoLock() { try{ _mutex.unlock(); } catch (...) {} }
private:
  MutexLock& _mutex;
};

class Signal {
    std::mutex _mtx;
    bool _event_flag;
    std::condition_variable  _cond;
public:
    Signal(bool manualReset = true) :_event_flag{!manualReset}{}

    void notify() {
        _mtx.lock();
        _event_flag = true;
        _mtx.unlock();
        _cond.notify_all();
    }

    void wait() {
        std::unique_lock<std::mutex> lock = std::unique_lock<std::mutex>(_mtx);
        _cond.wait(lock, [&]{return _event_flag;});
    }

    bool wait(long ms, bool timeout = false) {
        int status = false;
        std::unique_lock<std::mutex> lock;
        if (timeout && !(lock = std::unique_lock<std::mutex>(_mtx),_cond.wait_for(lock, std::chrono::milliseconds(ms),[&]{return _event_flag;})) )
            throw ThreadException("Timeout Exception");
        return status;
    }
};


class UID {
public:
  unsigned int _uid;
  UID() { _uid = ++uid; }
  UID(const UID& uid) { _uid = uid._uid; }
  UID& operator=(const UID& uid)  { _uid = uid._uid; return(*this); }
  operator int() { return _uid; }
  bool operator == (const UID& uid) const { return _uid == uid._uid; }
  bool operator != (const UID& uid) const { return _uid != uid._uid; }
  bool operator == (const unsigned int& uid) const { return _uid == uid; }
  bool operator != (const unsigned int& uid) const { return _uid != uid; }
protected:
  static unsigned int uid;
};

template <class DataType>
class DataResolver {
public:
  DataResolver() : _data(0) { }
  DataType& data() {
    return *_data;
  }
  void resolveData(DataType* data) {
    delete _data;
    _data = data;
  }
private:
  DataType* _data;
};

class ErrorResolver {
public:
  ErrorResolver() :_error(0) {}
  std::string getErrorMessage() const {
    return (_error) ? _error->what() : std::string();
  }
  std::exception* getError() const {
    return _error;
  }
  void resolveError(const std::string& msg) {
    delete _error;
    _error = new std::runtime_error(msg.c_str());
  }
  bool hasError() const {
    return _error != 0;
  }
private:
  std::exception*  _error;
};

class SharedObject : public ReferenceObject {
public:
  SharedObject() : _signal(false) {}
  void wait() {
    _signal.wait();
  }
  bool wait(long ms, bool timeout = false) {
    return _signal.wait(ms, timeout);
  }
  void notify() {
    _signal.notify();
  }
private:
  Signal       _signal;
};

template <typename T>
struct SharedObjectProxy
{
  typedef T* DataTypePtr;
public:
  SharedObjectProxy(DataTypePtr data) : _data(data) {}
  SharedObjectProxy(const SharedObjectProxy& proxy) {
    _data = proxy._data;
    _data->reference();
  }
  ~SharedObjectProxy() {
    _data->release();
  }
  void snooze() {
    _data->wait(0); //for (; data->wait(10); );
  }
  void wait() {
    _data->wait();
  }
  bool wait(long ms, bool timeout = false) {
    return _data->wait(ms, timeout);
  }
  bool ready() const {
    return _data->wait(0);
  }
  void notify() {
    _data->notify();
  }
protected:
  SharedObjectProxy();
  DataTypePtr  _data;
};

template <typename BASE, typename T = typename BASE::DataTypePtr>
struct ErrorProxy : public BASE {
public:
  ErrorProxy(T data) : BASE(data) {}
  ErrorProxy(const ErrorProxy& proxy) : BASE(proxy) {}
  std::string getErrorMessage() const {
    return this -> _data->getErrorMessage();
  }
  std::exception* getError() const {
    return this -> _data->getError();
  }
  void resolveError(const std::string& msg) {
      this -> _data->resolveError(msg);
  }
  bool hasError() const {
    return this -> _data->hasError();
  }
};

template <typename BASE, class Type, typename T = typename BASE::DataTypePtr>
struct DataProxy : public BASE {
public:
  DataProxy(T data) : BASE(data) {}
  DataProxy(const DataProxy& proxy) : BASE(proxy) {}
  Type& data() const {
    return this -> _data->data();
  }
  void resolveData(Type* data) {
      this -> _data->resolveData(data);
  }
};

template<typename Caller, typename ReturnType = void, VAR_TYPES_DEFAULT(ARGUMENT_UPPER_LIMIT, void)> struct MethodCallbackSignature;
template<typename Caller, typename ReturnType, VAR_TYPES(ARGUMENT_UPPER_LIMIT)> struct MethodCallbackSignature { typedef ReturnType(Caller::*Method)(VAR_ARGS(ARGUMENT_UPPER_LIMIT)); };
template<typename Caller, typename ReturnType> struct MethodCallbackSignature<Caller, ReturnType, VOID_ARGS(ARGUMENT_UPPER_LIMIT)> { typedef ReturnType(Caller::*Method)(); };
template<typename Caller, VAR_TYPES(ARGUMENT_UPPER_LIMIT)> struct MethodCallbackSignature<Caller, void, VAR_ARGS(ARGUMENT_UPPER_LIMIT)> { typedef void (Caller::*Method)(VAR_ARGS(ARGUMENT_UPPER_LIMIT)); };
template<typename Caller> struct MethodCallbackSignature<Caller, void, VOID_ARGS(ARGUMENT_UPPER_LIMIT)> { typedef void (Caller::*Method)(); };

GENERATE_METHOD_CALLBACK_SIGNATURES(ARGUMENT_UPPER_LIMIT)


template <class BASE, class Caller, class FutureType, class ReturnType>
class BaseMethodCallbackInvoke : public BASE {
public:
    BaseMethodCallbackInvoke(Caller* caller, const FutureType& result) :
            _result(result), _context(caller) {}
    ~BaseMethodCallbackInvoke() { this->release(); }
    void run(){
        try {
            invokeMethod();
        }
        catch (std::exception& e) {
            _result.resolveError(e.what());
        }
        catch (...) {
            _result.resolveError("Invoke Error");
        }
        _result.notify();
    }
    void resolveError(const std::string& msg) {
        _result.resolveError(msg);
    }
protected:
    virtual void invokeMethod() = 0;
    Caller* _context;
    FutureType _result;
};

template<class BASE, class Caller, class FutureType, class ReturnType, VAR_TYPES_DEFAULT(ARGUMENT_UPPER_LIMIT, void)> class MethodCallbackInvoke;

template <class BASE, class Caller, class FutureType, class ReturnType = void, VAR_TYPES(ARGUMENT_UPPER_LIMIT)>
class MethodCallbackInvoke : public BaseMethodCallbackInvoke<BASE, Caller, FutureType, ReturnType> {
public:
    typedef typename MethodCallbackSignature<Caller, ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)>::Method Callback;
    MethodCallbackInvoke(Caller* caller, Callback method, VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), const FutureType& result) :
            BaseMethodCallbackInvoke<BASE, Caller, FutureType, ReturnType>(caller, result), _method(method), INIT_VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg) {}
protected:
    VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, _arg, SEMICOLON);
    Callback    _method;
};

GENERATE_METHOD_CALLBACK_INVOKE(ARGUMENT_UPPER_LIMIT)

template <class BASE, class Caller, class FutureType, class ReturnType>
class MethodCallbackInvoke<BASE, Caller, FutureType, ReturnType> : public BaseMethodCallbackInvoke<BASE, Caller, FutureType, ReturnType>{
public:
    typedef typename MethodCallbackSignature<Caller, ReturnType>::Method Callback;
    MethodCallbackInvoke(Caller* caller, Callback method, const FutureType& result) :
            BaseMethodCallbackInvoke<BASE, Caller, FutureType, ReturnType>(caller, result), _method(method) {}
protected:
    Callback    _method;
};

template<class BASE, class Caller, class FutureType, class ReturnType = void, VAR_TYPES_DEFAULT(ARGUMENT_UPPER_LIMIT, void)> class DelegateInvoke;

template <class BASE, class Caller, class FutureType, class ReturnType, VAR_TYPES(ARGUMENT_UPPER_LIMIT)>
class DelegateInvoke : public MethodCallbackInvoke<BASE, Caller, FutureType, ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)> {
public:
    DelegateInvoke(Caller* caller, typename DelegateInvoke::Callback method, VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), const FutureType& result)
            : MethodCallbackInvoke<BASE, Caller, FutureType, ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)>(caller, method, VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), result) {}
    void invokeMethod() {
        this -> _result.resolveData(new ReturnType((this -> _context->*this -> _method)(NAMED_ARG(ARGUMENT_UPPER_LIMIT, this->arg))));
    }
};

template <class BASE, class Caller, class FutureType, VAR_TYPES(ARGUMENT_UPPER_LIMIT)>
class DelegateInvoke<BASE, Caller, FutureType, void, VAR_ARGS(ARGUMENT_UPPER_LIMIT)> : public MethodCallbackInvoke<BASE, Caller, FutureType, void, VAR_ARGS(ARGUMENT_UPPER_LIMIT)>{
public:
    DelegateInvoke(Caller* caller, typename DelegateInvoke::Callback method, VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), const FutureType& result)
            : MethodCallbackInvoke<BASE, Caller, FutureType, void, VAR_ARGS(ARGUMENT_UPPER_LIMIT)>(caller, method, VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), result) {}
    void invokeMethod() {
        (this -> _context->*this -> _method)(NAMED_ARG(ARGUMENT_UPPER_LIMIT, this->arg));
    }
};

GENERATE_DELEGATE_INVOKE(ARGUMENT_UPPER_LIMIT)

template <class BASE, class Caller, class FutureType, class ReturnType>
class DelegateInvoke<BASE, Caller, FutureType, ReturnType> : public MethodCallbackInvoke<BASE, Caller, FutureType, ReturnType, void>{
public:
    DelegateInvoke(Caller* caller, typename DelegateInvoke::Callback method, const FutureType& result) : MethodCallbackInvoke<BASE, Caller, FutureType, ReturnType, void>(caller, method, result) {}
    void invokeMethod() {
        this -> _result.resolveData(new ReturnType((this -> _context->*this -> _method)()));
    }
};

template <class BASE, class Caller, class FutureType>
class DelegateInvoke<BASE, Caller, FutureType> : public MethodCallbackInvoke<BASE, Caller, FutureType, void, void>{
public:
    DelegateInvoke(Caller* caller, typename DelegateInvoke::Callback method, const FutureType& result) : MethodCallbackInvoke<BASE, Caller, FutureType, void, void>(caller, method, result) {}
    void invokeMethod() {
        (this -> _context->*this -> _method)();
    }
};

class DelegateBase : public Runnable, public RefPointer<DelegateBase> {
public:
    virtual void resolveError(const std::string& msg) = 0;
};

template <class FutureType> class FutureObject : public SharedObject, public DataResolver<FutureType>, public ErrorResolver {};
template <> class FutureObject<void> : public SharedObject, public ErrorResolver{};
template <class FutureType> class FutureResult : public DataProxy< ErrorProxy< SharedObjectProxy<FutureObject<FutureType> > >, FutureType>{
public: FutureResult(typename SharedObjectProxy<FutureObject<FutureType> >::DataTypePtr ptr) :DataProxy< ErrorProxy< SharedObjectProxy<FutureObject<FutureType> > >, FutureType>(ptr){}
};
template <> class FutureResult<void> : public ErrorProxy< SharedObjectProxy<FutureObject<void> > >{
public:  FutureResult(SharedObjectProxy::DataTypePtr ptr) :ErrorProxy(ptr){}
};

template<class Caller, class ReturnType = void, VAR_TYPES_DEFAULT(ARGUMENT_UPPER_LIMIT, void)> class Delegate;
template <class Caller, class ReturnType, VAR_TYPES(ARGUMENT_UPPER_LIMIT)> class Delegate : public DelegateInvoke < DelegateBase, Caller, FutureResult<ReturnType>, ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT) > {
public:  Delegate(Caller* caller, typename Delegate::Callback method, VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), const FutureResult<ReturnType>& result) : DelegateInvoke< DelegateBase, Caller, FutureResult<ReturnType>, ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT) >(caller, method, VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), result) {}
};
template <class Caller, VAR_TYPES(ARGUMENT_UPPER_LIMIT)> class Delegate<Caller, void, VAR_ARGS(ARGUMENT_UPPER_LIMIT)> : public DelegateInvoke < DelegateBase, Caller, FutureResult<void>, void, VAR_ARGS(ARGUMENT_UPPER_LIMIT) >{
public:  Delegate(Caller* caller, typename Delegate::Callback method, VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), const FutureResult<void>& result) : DelegateInvoke< DelegateBase, Caller, FutureResult<void>, void, VAR_ARGS(ARGUMENT_UPPER_LIMIT) >(caller, method, VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), result) {}
};
template <class Caller, class ReturnType> class Delegate<Caller, ReturnType, void> : public DelegateInvoke< DelegateBase, Caller, FutureResult<ReturnType>, ReturnType, void >{
public:  Delegate(Caller* caller, typename Delegate::Callback method, const FutureResult<ReturnType>& result) : DelegateInvoke< DelegateBase, Caller, FutureResult<ReturnType>, ReturnType, void >(caller, method, result) {}
};
template <class Caller> class Delegate<Caller, void, void> : public DelegateInvoke< DelegateBase, Caller, FutureResult<void>, void, void >{
public:  Delegate(Caller* caller, typename Delegate::Callback method, const FutureResult<void>& result) : DelegateInvoke< DelegateBase, Caller, FutureResult<void>, void, void >(caller, method, result) {}
};

GENERATE_DELEGATE(ARGUMENT_UPPER_LIMIT)


template <class Caller>
struct Request : public RefPointer<Request<Caller>> {
public:
  typedef std::deque<typename Request::Ptr> RequestsQueue;
  typedef typename MethodCallbackSignature<Caller, bool, int>::Method RequestGuard;
  Request(int priority = 0, long delay = 0, long timeout = 0, Caller* caller = 0, RequestGuard guard = 0, int guardId = 0, int conditionId = 0) : _priority(priority), _delay(delay), _timeout(timeout), _context(caller), _guard(guard), _guardId(guardId), _conditionId(conditionId) {}
  int getPriority() const { return _priority; }
  int getDelay() const { return _delay; }
  int getTimeout() const { return _timeout; }
  bool filtered() {
    if (_guard){
      return !((_context->*_guard)(_guardId));
    }
    return false;
  }
  bool deferred() {
    if (_guard){
      return !((_context->*_guard)(_conditionId));
    }
    return false;
  }
private:
  Caller*    _context;
  RequestGuard _guard;
  int _guardId;
  int _conditionId;
  int _priority;
  long _delay;
  long _timeout;
};

template <class Caller>
struct MethodAccessRequest {
    typename Request<Caller>::Ptr request;
    Signal ready;
    typedef std::deque<MethodAccessRequest<Caller>*> MethodAccessRequestQueue;
};

template <class Caller> struct MethodInvokeRequest : public Request<Caller> {
public:
    MethodInvokeRequest(DelegateBase::Ptr requestRunnable, int priority = 0, long delay = 0, long timeout = 0, Caller* caller = 0, typename MethodInvokeRequest::RequestGuard guard = 0, int guardId = 0, int conditionId = 0)
            : _requestRunnable(requestRunnable), Request<Caller>(priority, delay, timeout, caller, guard, guardId, conditionId){}
    DelegateBase::Ptr getRequest() const { return _requestRunnable; }
private:
    DelegateBase::Ptr _requestRunnable;
};


template <class Caller>
class ScheduleQueue : protected Runnable {
public:
    ScheduleQueue() : deferringRunning(false)  {}
    ~ScheduleQueue() { try { clear(); } catch (...) {} }
    void request(typename Request<Caller>::Ptr request, bool urgent = false) {
        synchronized(_lock) {
            deferringRunning = false;
            if (request->deferred()) {
                _deferred.push_back(request);
            }
            else {
                addRequest(request, urgent);
            }
        }
    }

    Request<Caller>* processPendingRequests(){
        //::Sleep(1);    // DEBUG
        typename Request<Caller>::Ptr request = NULL;
        MethodAccessRequest<Caller>* mq = NULL;
        processDeferred();
        synchronized(_lock){
            // ::Sleep(1);  // DEBUG
            request = getNextRequest();

        }
        if (request.value() != NULL) {
            return request.reference();
        }
        else if (_pending.empty() && !_deferred.empty() && !deferringRunning) {
            _thread.start(this);
            deferringRunning = true;
        }
        mq = new MethodAccessRequest<Caller>();
        _pending.push_back(mq);
        mq->ready.wait();
        request = mq->request;
        delete mq;
        return request.reference();
    }
    void done() {
        synchronized(_lock) {
            for (typename MethodAccessRequest<Caller>::MethodAccessRequestQueue::iterator it = _pending.begin(); it != _pending.end(); ++it) {
                (*it)->ready.notify();
            }
            _pending.clear();
        }
    }
    bool empty() const {
        synchronized(_lock) {
            return _requests.empty() && _deferred.empty();
        }
    }
    int size() const {
        synchronized(_lock) {
            return static_cast<int>(_requests.size() + _deferred.size());
        }
    }
    void clear() {
        synchronized(_lock) {
            _requests.clear();
            _deferred.clear();
        }
        _thread.join();
    }
protected:
    void run() {
        while (deferringRunning) { try { processDeferred(); } catch (...) {} }
    }

private:
    void addRequest(typename Request<Caller>::Ptr request, bool urgent = false) {
        if (_pending.empty()) {
            if (urgent) {
                _requests.push_front(request);
            }
            else if (request.value() &&  request->getPriority() <= 0) {
                _requests.push_back(request);
            }
            else {
                typename Request<Caller>::RequestsQueue::iterator it;
                for (it = _requests.begin(); it != _requests.end(); ++it) {
                    if (request->getPriority() > (*it)->getPriority())
                        break;
                }
                _requests.insert(it, request);
            }
        }
        else {
            MethodAccessRequest<Caller>* mq = _pending.front();
            _pending.pop_front();
            mq->request = request;
            mq->ready.notify();
        }
    }
    void processDeferred() {
        if (!_deferred.empty()) {
            typename Request<Caller>::Ptr deferred = _deferred.front();
            if (deferred.value() && !deferred->deferred()) {
                _deferred.pop_front();
                addRequest(deferred);
            }
        }
        else {
            deferringRunning = false;
            return;
        }
    }
    typename Request<Caller>::Ptr getNextRequest() {
        typename Request<Caller>::Ptr request;
        if (!_requests.empty()) {
            request = _requests.front();
            _requests.pop_front();
        }
        return request;
    }
private:
    mutable MutexLock  _lock;
    Thread        _thread;
    bool        deferringRunning;
    typename Request<Caller>::RequestsQueue  _requests;
    typename Request<Caller>::RequestsQueue  _deferred;
    typename MethodAccessRequest<Caller>::MethodAccessRequestQueue  _pending;
};

class TimedEvent : protected Runnable {
public:
    TimedEvent() { }
    virtual ~TimedEvent() { }
    void timeout(DelegateBase::Ptr  method, long ms) {
        _method = method;
        _thread.start(this);
        try{
            _timeoutEvent.wait(ms, true);
        }
        catch (...) {
            _thread.stop();
            _method->resolveError("Timeout Exception");
            _method = NULL;
            _timeoutEvent.notify();
        }
    }
protected:
    void run() {
        _method->reference();
        _method->run();
        _timeoutEvent.notify();
    }
private:
    Thread        _thread;
    DelegateBase::Ptr  _method;
    Signal        _timeoutEvent;
};

template <class Caller>
class Scheduler : protected Runnable {
public:
    typedef typename Request<Caller>::RequestGuard GuardList;
    Scheduler(Caller* caller = 0, GuardList guardList = 0) : _context(caller), _guardList(guardList) {
        _run = true;
        _thread.start(this);
    }
    virtual ~Scheduler() { try { stop(); } catch (...) {} }
    void schedule(DelegateBase::Ptr pDelegate, int priority, long delay, long timeout, UID guard, UID condition) {
        _queue.request(new MethodInvokeRequest<Caller>(pDelegate, priority, delay, timeout, _context, _guardList, guard, condition));
    }
    void cancel() {
        _queue.clear();
    }
protected:
    void run() {
        AutoPtr<Request<Caller > > pendingRequest = _queue.processPendingRequests();
        while (pendingRequest) {
            MethodInvokeRequest<Caller>* mth = dynamic_cast<MethodInvokeRequest<Caller>*>(pendingRequest.value());
            if (mth != NULL && !mth->filtered()) {
                long delay = mth->getDelay();
                if (delay > 0) {
                    Thread::sleep(delay);
                }
                if (mth->getTimeout() > 0) {
                    _timeEvent.timeout(mth->getRequest(), mth->getTimeout());
                }
                else {
                    DelegateBase::Ptr pDelegate = mth->getRequest();
                    if (pDelegate.value()) {
                        pDelegate->reference();
                        pDelegate->run();
                        pDelegate = NULL;
                    }
                }
            }
            pendingRequest = NULL;
            if (_run)
                pendingRequest = _queue.processPendingRequests();
        }
    }
    void stop() {
        _queue.clear();
        _queue.done();
        _run = false;
        _thread.join();
    }
private:
    Caller*          _context;
    Thread          _thread;
    ScheduleQueue<Caller>  _queue;
    GuardList        _guardList;
    bool          _run;
    TimedEvent        _timeEvent;
};


struct ActiveConstraintUID {
public:
    UID getGuardUID() { return _guardId; }
    UID getConditionUID() { return _conditionId; }
protected:
    UID _guardId;
    UID _conditionId;
};

template<class ReturnType, VAR_TYPES_DEFAULT(ARGUMENT_UPPER_LIMIT, void)> class IDelegatePublisher;
template<class ReturnType, VAR_TYPES(ARGUMENT_UPPER_LIMIT)>
class IDelegatePublisher {
public:
    virtual FutureResult<ReturnType> publish(VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) = 0;
};
template<class ReturnType>
class IDelegatePublisher<ReturnType> {
public:
    virtual FutureResult<ReturnType> publish(int priority = 0, long delay = 0, long timeout = 0) = 0;
};

template<class ReturnType, VAR_TYPES_DEFAULT(ARGUMENT_UPPER_LIMIT, void)> class MulticastDelegate;
template <class ReturnType, VAR_TYPES(ARGUMENT_UPPER_LIMIT)>
class MulticastDelegate : public IDelegatePublisher<ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)> {
private:
    typedef std::vector< IDelegatePublisher<ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)> * > SubscribersList;
    SubscribersList subscribers;
public:
    MulticastDelegate() {}
    MulticastDelegate& operator += (const IDelegatePublisher<ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)>* method) {
        subscribers.push_back(method);
        return *this;
    }
    void notify(VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) {
        typename SubscribersList::iterator it = subscribers.begin();
        for (; it != subscribers.end(); it++) { it->publish(VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), priority, delay, timeout); }
    }
    FutureResult<ReturnType> operator () (VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) {
        FutureResult<ReturnType> result = publish(VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), priority, delay, timeout);
        notify(VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), priority, delay, timeout);
        return result;
    }
};
template <class ReturnType>
class MulticastDelegate<ReturnType> : public IDelegatePublisher<ReturnType>{
private:
    typedef std::vector< IDelegatePublisher<ReturnType> * > SubscribersList;
    SubscribersList subscribers;
public:
    MulticastDelegate() {}
    MulticastDelegate& operator += (const IDelegatePublisher<ReturnType>* method) {
        subscribers.push_back(method);
        return *this;
    }
    void notify(int priority = 0, long delay = 0, long timeout = 0) {
        typename SubscribersList::iterator it = subscribers.begin();
        for (; it != subscribers.end(); it++) { (*it)->publish(priority, delay, timeout); }
    }
    FutureResult<ReturnType> operator () (int priority = 0, long delay = 0, long timeout = 0) {
        FutureResult<ReturnType> result = this -> publish(priority, delay, timeout);
        notify(priority, delay, timeout);
        return result;
    }
};


GENERATE_MULTICAST_METHOD(ARGUMENT_UPPER_LIMIT)

template<class Caller, class ReturnType, VAR_TYPES_DEFAULT(ARGUMENT_UPPER_LIMIT, void)> class Active;
template <class Caller, class ReturnType, VAR_TYPES(ARGUMENT_UPPER_LIMIT)>
class Active : public ActiveConstraintUID, public MulticastDelegate<ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)>{
public:
    typedef Delegate<Caller, ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)> DelegateType;
    typedef typename MethodCallbackSignature<Caller, ReturnType, VAR_ARGS(ARGUMENT_UPPER_LIMIT)>::Method Callback;
    Active(Caller* caller, Scheduler<Caller>* sch, Callback method) :_context(caller), _sch(sch), _method(method) {}
    FutureResult<ReturnType> operator () (VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) {
        FutureResult<ReturnType> result = publish(priority, delay, timeout);
        return result;
    }
    FutureResult<ReturnType> publish(VAR_ARGS_MEMBERS(ARGUMENT_UPPER_LIMIT, arg, COMMA), int priority = 0, long delay = 0, long timeout = 0) {
        FutureResult<ReturnType> result(new FutureObject<ReturnType>());
        DelegateBase::Ptr pDelegate(new DelegateType(_context, _method, VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), result));
        _sch->schedule(pDelegate, priority, delay, timeout, _guardId, _conditionId);
        this->notify(VAR_NAMED_ARGS(ARGUMENT_UPPER_LIMIT, arg), priority, delay, timeout);
        return result;
    }
private:
    Caller* _context;
    Scheduler<Caller>* _sch;
    Callback   _method;
};

template <class Caller, class ReturnType>
class Active <Caller, ReturnType, void> : public ActiveConstraintUID, public MulticastDelegate<ReturnType> {
public:
    typedef Delegate<Caller, ReturnType, void> DelegateType;
    typedef typename MethodCallbackSignature<Caller, ReturnType>::Method Callback;
    Active(Caller* caller, Scheduler<Caller>* sch, Callback method) :_context(caller), _sch(sch), _method(method) {}
    FutureResult<ReturnType> publish(int priority = 0, long delay = 0, long timeout = 0) {
        FutureResult<ReturnType>  result(new FutureObject<ReturnType>());
        DelegateBase::Ptr pDelegate(new DelegateType(_context, _method, result));
        _sch->schedule(pDelegate, priority, delay, timeout, _guardId, _conditionId);
        this->notify(priority, delay, timeout);
        return result;
    }
private:
    Caller* _context;
    Scheduler<Caller>* _sch;
    Callback   _method;
};
GENERATE_ACTIVE_METHOD(ARGUMENT_UPPER_LIMIT)

template <class Caller>
class AsyncMethod : protected Runnable, public ActiveConstraintUID {
public:
    typedef Delegate<Caller, void, void> DelegateType;
    typedef Active<Caller, void> ActiveType;
    typedef typename MethodCallbackSignature<Caller>::Method Callback;
    AsyncMethod(Caller* caller, Callback method, long interval = 0, long delay = 0, long timeout = 0) :_context(caller), _delegate(new DelegateType(_context, method, new FutureObject<void>())), _delay(delay), _timeout(timeout), _interval(interval), _active(false){}
    AsyncMethod(Caller* caller, Scheduler<Caller>* sch, Callback method, long interval = 0, long delay = 0, long timeout = 0) :_context(caller), _delegate(new DelegateType(_context, method, new FutureObject<void>())), _delay(delay), _timeout(timeout), _interval(interval), _sch(sch), _active(true) {}
    virtual ~AsyncMethod() { try { stop();  _thread.join(); } catch (...) {} }
    void start() {
        _run = true;
        _thread.start(this);
    }
    void stop() {
        _run = false;
    }
protected:
    void run() {
        while (_run) {
            if (!_active && _delay > 0) {
                Thread::sleep(_delay);
            }
            if (_interval > 0) {
                if (_active) {
                    _sch->schedule(_delegate, -1, _delay, _timeout, _guardId, _conditionId);
                }
                else {
                    _delegate->run();
                    if (_timeout > 0)
                        _timeoutEvent.wait(_timeout, true);
                }
                Thread::sleep(_interval);
            }
        }
    }
private:
    bool        _run;
    long        _interval;
    long        _delay;
    long        _timeout;
    bool        _active;
    Thread        _thread;
    Caller*        _context;
    DelegateBase::Ptr  _delegate;
    Scheduler<Caller>*  _sch;
    Signal        _timeoutEvent;
};

//#define Active 0
//#define Cancelled 1
//#define Completed 2
//#define Created 3
//#define Draft 4
//#define EnrollmentOpen 5
//#define Graded 6
//#define Grading 7
//#define InProgress 8
//#define PastDue 9
//#define Published 10
//#define ResubmissionRequested 11
//#define Returned 12
//#define Submitted 13
//#define Suspended 14
//#define Trial 15
//#define UnderCheck 16
//#define Waitlisted 17


//A template function to get the index of an item for a given vector. It returns -1 if the
//item was not found
template <typename InputIterator, typename EqualityComparable>
  int IndexOf(const InputIterator& begin,
  const InputIterator& end, const EqualityComparable& item) {
    if(begin == end )
      return -1;

    InputIterator fnd = std::find(begin, end, item);
    unsigned int index = std::distance(begin, fnd);
    return fnd != end ? index : -1;
};

//Used as a function pointer to safely destroy elements or collections of elements
//Example: 
//  std::for_each( arr->begin(), arr->end(), delete_pointer_element());
//  delete arr;
struct deleteElement{
template< typename T >
void operator()( T element ) const{
  delete element;
  }
};

template<typename T>
unsigned int compare(T c1, T c2, unsigned int sz ) {
  if(!is_ptr<T>::value) {
    return std::memcmp(&c1,&c2, sz);
  }
  return std::memcmp((void*)c1, (void*)c2, sz);
};

template<typename T>
void copyObject(T* object1, T* object2, unsigned int sz ) {
  if(!is_ptr<T>::value) {
    std::memcpy(&object2,&object1, sz);
  }
  std::memcpy((void*)object2, (void*)object1, sz);
};


//A template function to copy vector
template<typename InputIterator, typename T>
void copyVector(InputIterator& it, InputIterator& end ,T* vectorObj) {  
  for (; it != end; ++it) {
    vectorObj->push_back(*it);
  }
};


#ifndef TIME_AND_DATE_DEFINITION
#define TIME_AND_DATE_DEFINITION
namespace { 
// Ported strptime from BSD
#ifdef WINDOWS_OS

const char* Days[] = {
        "Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"
};

const char* DaySymbols[] = {"Sun", "Mon", "Tue", "Wed","Thu", "Fri", "Sat"};

const char* Monthes[] = {"January", "February", "March", "April", "May", "June",
          "July", "August", "September", "October", "November", "December" };

const char* MonthSymbols[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                   "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

const char* parseNumber(const char* s, int low, int high, int* value) {
        const char* p = s;
        for (*value = 0; *p != NULL && isdigit(*p); ++p) {
                *value = (*value) * 10 + static_cast<int>(*p) - static_cast<int>('0');
        }
        if (p == s || *value < low || *value > high) return NULL;
        return p;
}

char* strptime(const char *s, const char *format, struct tm *tm) {
        while (*format != NULL && *s != NULL) {
                if (*format != '%') {
                        if (*s != *format) return NULL;
                        ++format;
                        ++s;
                        continue;
                }
                ++format;
                int len = 0;
                switch (*format) {
          case 'a':
          case 'A':
                  tm->tm_wday = -1;
                  for (int i = 0; i < 7; ++i) {
                          len = static_cast<int>(strlen(DaySymbols[i]));
                          if (_strnicmp(DaySymbols[i], s, len) == 0) {
                                  tm->tm_wday = i;
                                  break;
                          }
                          len = static_cast<int>(strlen(Days[i]));
                          if (_strnicmp(Days[i], s, len) == 0) {
                                  tm->tm_wday = i;
                                  break;
                          }
                  }
                  if (tm->tm_wday == -1) return NULL;
                  s += len;
                  break;
          case 'b':
          case 'B':
          case 'h':
                  tm->tm_mon = -1;
                  for (int i = 0; i < 12; ++i) {
                          len = static_cast<int>(strlen(MonthSymbols[i]));
                          if (_strnicmp(MonthSymbols[i], s, len) == 0) {
                                  tm->tm_mon = i;
                                  break;
                          }
                          len = static_cast<int>(strlen(Monthes[i]));
                          if (_strnicmp(Monthes[i], s, len) == 0) {
                                  tm->tm_mon = i;
                                  break;
                          }
                  }
                  if (tm->tm_mon == -1) return NULL;
                  s += len;
                  break;
          case 'm':
                  s = parseNumber(s, 1, 12, &tm->tm_mon);
                  if (s == NULL) return NULL;
                  --tm->tm_mon;
                  break;
          case 'd':
          case 'e':
                  s = parseNumber(s, 1, 31, &tm->tm_mday);
                  if (s == NULL) return NULL;
                  break;
          case 'H':
                  s = parseNumber(s, 0, 23, &tm->tm_hour);
                  if (s == NULL) return NULL;
                  break;
          case 'M':
                  s = parseNumber(s, 0, 59, &tm->tm_min);
                  if (s == NULL) return NULL;
                  break;
          case 'S':
                  s = parseNumber(s, 0, 60, &tm->tm_sec);
                  if (s == NULL) return NULL;
                  break;
          case 'Y':
                  s = parseNumber(s, 1900, 9999, &tm->tm_year);
                  if (s == NULL) return NULL;
                  tm->tm_year -= 1900;
                  break;
          case 'y':
                  s = parseNumber(s, 0, 99, &tm->tm_year);
                  if (s == NULL) return NULL;
                  if (tm->tm_year <= 68) {
                          tm->tm_year += 100;
                  }
                  break;
          case 't':
          case 'n':
                  while (isspace(*s)) ++s;
                  break;
          case '%':
                  if (*s != '%') return NULL;
                  ++s;
                  break;
          default:
                  return NULL;
                }
                ++format;
        }

        if (*format != NULL) {return NULL;} 
    else {return const_cast<char*>(s);}
}
#endif

tm* ParseDate(string buf) {
  time_t t(time(NULL));   
  tm* pDate = new tm(*localtime(&t));
  char* ret = strptime(buf.c_str(), "%d-%m-%Y", pDate);
  assert(ret != NULL);
  return pDate;
};

time_t* ParseTime(string buf) {
  tm tm;
  time_t* pTime;
  char* ret = strptime(buf.c_str(), "%H:%M:%S", &tm);
  assert(ret != NULL);  
  pTime = new time_t(mktime(&tm));
  return pTime;
};
}
#endif


#endif
