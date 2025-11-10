cmake_minimum_required(VERSION 3.0)
project(model)

set(CMAKE_CXX_STANDARD 11)
include_directories(${CMAKE_SOURCE_DIR})


set(SOURCE_FILES
model_Model.h
model_Main.cpp
Utils.h
Utils.cpp
User.h
User.cpp
Student.h
Student.cpp
Instructor.h
Instructor.cpp
Administrator.h
Administrator.cpp
Course.h
Course.cpp
Enrollment.h
Enrollment.cpp
Lesson.h
Lesson.cpp
VideoContent.h
VideoContent.cpp
CourseCategory.h
CourseCategory.cpp
Assignment.h
Assignment.cpp
Submission.h
Submission.cpp
Grade.h
Grade.cpp
Subscription.h
Subscription.cpp
Payment.h
Payment.cpp
Refund.h
Refund.cpp)

add_executable(model ${SOURCE_FILES})
if (NOT MSVC)
  target_link_libraries(model pthread)
endif()