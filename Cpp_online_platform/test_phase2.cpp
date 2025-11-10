#include <iostream>
#include <stdexcept>
#include "model_Model.h"
#include "User.h"
#include "Student.h"
#include "Instructor.h"
#include "Course.h"
#include "Enrollment.h"
#include "Lesson.h"
#include "Assignment.h"

using namespace std;

// Test counter
int passed = 0;
int failed = 0;

void test(const string& testName, bool condition) {
    if (condition) {
        cout << "✓ PASS: " << testName << endl;
        passed++;
    } else {
        cout << "✗ FAIL: " << testName << endl;
        failed++;
    }
}

void testCoursePublish() {
    cout << "\n=== Testing Course.publish() ===" << endl;
    
    try {
        Instructor* instructor = new Instructor("I001", "Prof. Smith", "smith@test.com");
        Course* course = new Course("C001", "Test Course", 30, instructor);
        
        // Test 1: Cannot publish without content
        test("Course starts in Draft status", course->getStatus() == Status::Draft);
        
        bool published = course->publish();
        test("Cannot publish without content", !published);
        test("Status remains Draft", course->getStatus() == Status::Draft);
        
        // Test 2: Add minimum content and publish
        Lesson* lesson = new Lesson("L001", "Lesson 1", 1, course);
        tm* deadline = new tm();
        time_t t = time(nullptr);
        localtime_r(&t, deadline);
        Assignment* assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
        
        published = course->publish();
        test("Can publish with content", published);
        test("Status changes to Published", course->getStatus() == Status::Published);
        
        // Test 3: Cannot publish again
        published = course->publish();
        test("Cannot publish from Published state", !published);
        
        // Cleanup - Course destructor will handle lessons and assignments
        delete course;
        delete instructor;
        
    } catch (const exception& e) {
        test("Course publish test", false);
        cout << "  Error: " << e.what() << endl;
    }
}

void testCourseOpenEnrollment() {
    cout << "\n=== Testing Course.openEnrollment() ===" << endl;
    
    try {
        Instructor* instructor = new Instructor("I002", "Prof. Jones", "jones@test.com");
        Course* course = new Course("C002", "Test Course 2", 25, instructor); // Normal capacity
        
        // Setup minimum content
        Lesson* lesson = new Lesson("L002", "Lesson 1", 1, course);
        tm* deadline = new tm();
        time_t t = time(nullptr);
        localtime_r(&t, deadline);
        Assignment* assignment = new Assignment("A002", "Assignment 1", deadline, 100, course);
        
        course->publish();
        
        // Test: Open enrollment with capacity
        bool opened = course->openEnrollment();
        test("Can open enrollment with capacity", opened);
        test("Status changes to EnrollmentOpen", course->getStatus() == Status::EnrollmentOpen);
        
        // Cleanup
        delete course;
        delete instructor;
        
    } catch (const exception& e) {
        test("Course openEnrollment test", false);
        cout << "  Error: " << e.what() << endl;
    }
}

void testCourseEnroll() {
    cout << "\n=== Testing Course.enroll() ===" << endl;
    
    try {
        Instructor* instructor = new Instructor("I003", "Prof. Brown", "brown@test.com");
        Course* course = new Course("C003", "Small Course", 2, instructor); // Capacity = 2
        
        // Setup course
        Lesson* lesson = new Lesson("L003", "Lesson 1", 1, course);
        tm* deadline = new tm();
        time_t t = time(nullptr);
        localtime_r(&t, deadline);
        Assignment* assignment = new Assignment("A003", "Assignment 1", deadline, 100, course);
        
        course->publish();
        course->openEnrollment();
        
        // Create students
        Student* student1 = new Student("S001", "Alice", "alice@test.com");
        Student* student2 = new Student("S002", "Bob", "bob@test.com");
        Student* student3 = new Student("S003", "Charlie", "charlie@test.com");
        
        // Test 1: First enrollment should be Active
        Enrollment* enr1 = course->enroll(student1);
        test("First enrollment created", enr1 != nullptr);
        test("First enrollment is Active", enr1->getStatus() == EnrollmentStatus::Active);
        
        // Test 2: Second enrollment should be Active
        Enrollment* enr2 = course->enroll(student2);
        test("Second enrollment created", enr2 != nullptr);
        test("Second enrollment is Active", enr2->getStatus() == EnrollmentStatus::Active);
        
        // Test 3: Third enrollment should be Waitlisted (capacity = 2)
        Enrollment* enr3 = course->enroll(student3);
        test("Third enrollment created", enr3 != nullptr);
        test("Third enrollment is Waitlisted", enr3->getStatus() == EnrollmentStatus::Waitlisted);
        
        // Test 4: Cannot enroll same student twice
        Enrollment* enr4 = course->enroll(student1);
        test("Cannot enroll same student twice", enr4 == nullptr);
        
        // Cleanup - Course and Student destructors will handle enrollments
        delete course;
        delete student1;
        delete student2;
        delete student3;
        delete instructor;
        
    } catch (const exception& e) {
        test("Course enroll test", false);
        cout << "  Error: " << e.what() << endl;
    }
}

void testCourseStartAndComplete() {
    cout << "\n=== Testing Course.startCourse() and complete() ===" << endl;
    
    try {
        Instructor* instructor = new Instructor("I004", "Prof. Davis", "davis@test.com");
        Course* course = new Course("C004", "Test Course 4", 10, instructor);
        
        // Setup course
        Lesson* lesson = new Lesson("L004", "Lesson 1", 1, course);
        tm* deadline = new tm();
        time_t t = time(nullptr);
        localtime_r(&t, deadline);
        Assignment* assignment = new Assignment("A004", "Assignment 1", deadline, 100, course);
        
        course->publish();
        course->openEnrollment();
        
        // Test 1: Cannot start without students
        bool started = course->startCourse();
        test("Cannot start without enrollments", !started);
        
        // Test 2: Enroll a student and start
        Student* student = new Student("S004", "David", "david@test.com");
        Enrollment* enrollment = course->enroll(student);
        
        started = course->startCourse();
        test("Can start with enrollment", started);
        test("Status changes to InProgress", course->getStatus() == Status::InProgress);
        
        // Test 3: Complete course
        bool completed = course->complete();
        test("Can complete course", completed);
        test("Status changes to Completed", course->getStatus() == Status::Completed);
        
        // Cleanup
        delete course;
        delete student;
        delete instructor;
        
    } catch (const exception& e) {
        test("Course start/complete test", false);
        cout << "  Error: " << e.what() << endl;
    }
}

void testCourseCancel() {
    cout << "\n=== Testing Course.cancel() ===" << endl;
    
    try {
        Instructor* instructor = new Instructor("I005", "Prof. Wilson", "wilson@test.com");
        Course* course = new Course("C005", "Test Course 5", 10, instructor);
        
        // Setup course
        Lesson* lesson = new Lesson("L005", "Lesson 1", 1, course);
        tm* deadline = new tm();
        time_t t = time(nullptr);
        localtime_r(&t, deadline);
        Assignment* assignment = new Assignment("A005", "Assignment 1", deadline, 100, course);
        
        course->publish();
        
        // Test: Cancel from Published state
        bool cancelled = course->cancel();
        test("Can cancel published course", cancelled);
        test("Status changes to Cancelled", course->getStatus() == Status::Cancelled);
        
        // Cleanup
        delete course;
        delete instructor;
        
    } catch (const exception& e) {
        test("Course cancel test", false);
        cout << "  Error: " << e.what() << endl;
    }
}

int main() {
    cout << "========================================" << endl;
    cout << "  Phase 2 Testing: Course Module" << endl;
    cout << "========================================" << endl;
    
    testCoursePublish();
    testCourseOpenEnrollment();
    testCourseEnroll();
    testCourseStartAndComplete();
    testCourseCancel();
    
    cout << "\n========================================" << endl;
    cout << "  Test Results" << endl;
    cout << "========================================" << endl;
    cout << "Passed: " << passed << endl;
    cout << "Failed: " << failed << endl;
    cout << "Total:  " << (passed + failed) << endl;
    
    if (failed == 0) {
        cout << "\n🎉 All tests passed!" << endl;
        return 0;
    } else {
        cout << "\n❌ Some tests failed!" << endl;
        return 1;
    }
}

