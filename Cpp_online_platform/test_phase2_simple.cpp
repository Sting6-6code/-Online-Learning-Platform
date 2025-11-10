#include <iostream>
#include "model_Model.h"
#include "User.h"
#include "Student.h"
#include "Instructor.h"
#include "Course.h"
#include "Enrollment.h"
#include "Lesson.h"
#include "Assignment.h"

using namespace std;

int main() {
    cout << "========================================" << endl;
    cout << "  Phase 2 Testing: Course Module" << endl;
    cout << "========================================\n" << endl;
    
    int passed = 0;
    int total = 0;
    
    // Test 1: Course Lifecycle
    cout << "Test 1: Course Lifecycle" << endl;
    Instructor* instructor = new Instructor("I001", "Prof. Smith", "smith@test.com");
    Course* course = new Course("C001", "Test Course", 5, instructor);
    
    total++;
    if (course->getStatus() == Status::Draft) {
        cout << "  ✓ Course starts in Draft" << endl;
        passed++;
    } else {
        cout << "  ✗ Course should start in Draft" << endl;
    }
    
    total++;
    if (!course->publish()) {
        cout << "  ✓ Cannot publish without content" << endl;
        passed++;
    } else {
        cout << "  ✗ Should not publish without content" << endl;
    }
    
    // Add content
    Lesson* lesson = new Lesson("L001", "Lesson 1", 1, course);
    tm* deadline = new tm();
    time_t t = time(nullptr);
    localtime_r(&t, deadline);
    Assignment* assignment = new Assignment("A001", "Assignment 1", deadline, 100, course);
    
    total++;
    if (course->publish()) {
        cout << "  ✓ Can publish with content" << endl;
        passed++;
    } else {
        cout << "  ✗ Should publish with content" << endl;
    }
    
    total++;
    if (course->getStatus() == Status::Published) {
        cout << "  ✓ Status is Published" << endl;
        passed++;
    } else {
        cout << "  ✗ Status should be Published" << endl;
    }
    
    total++;
    if (course->openEnrollment()) {
        cout << "  ✓ Can open enrollment" << endl;
        passed++;
    } else {
        cout << "  ✗ Should open enrollment" << endl;
    }
    
    total++;
    if (course->getStatus() == Status::EnrollmentOpen) {
        cout << "  ✓ Status is EnrollmentOpen" << endl;
        passed++;
    } else {
        cout << "  ✗ Status should be EnrollmentOpen" << endl;
    }
    
    // Test 2: Enrollment
    cout << "\nTest 2: Enrollment Logic" << endl;
    Student* s1 = new Student("S001", "Alice", "alice@test.com");
    Student* s2 = new Student("S002", "Bob", "bob@test.com");
    Student* s3 = new Student("S003", "Charlie", "charlie@test.com");
    Student* s4 = new Student("S004", "David", "david@test.com");
    Student* s5 = new Student("S005", "Eve", "eve@test.com");
    Student* s6 = new Student("S006", "Frank", "frank@test.com");
    
    Enrollment* e1 = course->enroll(s1);
    total++;
    if (e1 != nullptr && e1->getStatus() == EnrollmentStatus::Active) {
        cout << "  ✓ First enrollment is Active" << endl;
        passed++;
    } else {
        cout << "  ✗ First enrollment should be Active" << endl;
    }
    
    Enrollment* e2 = course->enroll(s2);
    Enrollment* e3 = course->enroll(s3);
    Enrollment* e4 = course->enroll(s4);
    Enrollment* e5 = course->enroll(s5);
    
    total++;
    if (e5 != nullptr && e5->getStatus() == EnrollmentStatus::Active) {
        cout << "  ✓ Fifth enrollment is Active (within capacity)" << endl;
        passed++;
    } else {
        cout << "  ✗ Fifth enrollment should be Active" << endl;
    }
    
    Enrollment* e6 = course->enroll(s6);
    total++;
    if (e6 != nullptr && e6->getStatus() == EnrollmentStatus::Waitlisted) {
        cout << "  ✓ Sixth enrollment is Waitlisted (exceeds capacity)" << endl;
        passed++;
    } else {
        cout << "  ✗ Sixth enrollment should be Waitlisted" << endl;
    }
    
    Enrollment* e_dup = course->enroll(s1);
    total++;
    if (e_dup == nullptr) {
        cout << "  ✓ Cannot enroll same student twice" << endl;
        passed++;
    } else {
        cout << "  ✗ Should not enroll same student twice" << endl;
    }
    
    // Test 3: Start and Complete
    cout << "\nTest 3: Start and Complete Course" << endl;
    total++;
    if (course->startCourse()) {
        cout << "  ✓ Can start course with enrollments" << endl;
        passed++;
    } else {
        cout << "  ✗ Should start course with enrollments" << endl;
    }
    
    total++;
    if (course->getStatus() == Status::InProgress) {
        cout << "  ✓ Status is InProgress" << endl;
        passed++;
    } else {
        cout << "  ✗ Status should be InProgress" << endl;
    }
    
    total++;
    if (course->complete()) {
        cout << "  ✓ Can complete course" << endl;
        passed++;
    } else {
        cout << "  ✗ Should complete course" << endl;
    }
    
    total++;
    if (course->getStatus() == Status::Completed) {
        cout << "  ✓ Status is Completed" << endl;
        passed++;
    } else {
        cout << "  ✗ Status should be Completed" << endl;
    }
    
    // Summary
    cout << "\n========================================" << endl;
    cout << "  Test Results" << endl;
    cout << "========================================" << endl;
    cout << "Passed: " << passed << " / " << total << endl;
    
    if (passed == total) {
        cout << "\n🎉 All tests passed!" << endl;
        return 0;
    } else {
        cout << "\n❌ Some tests failed!" << endl;
        return 1;
    }
}

