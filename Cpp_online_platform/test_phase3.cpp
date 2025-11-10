#include <iostream>
#include <ctime>
#include "model_Model.h"
#include "User.h"
#include "Student.h"
#include "Instructor.h"
#include "Course.h"
#include "Assignment.h"
#include "Submission.h"
#include "Grade.h"

using namespace std;

int main() {
    cout << "========================================" << endl;
    cout << "  Phase 3 Testing: Assignment System" << endl;
    cout << "========================================\n" << endl;
    
    int passed = 0;
    int total = 0;
    
    // Test 1: Assignment validation
    cout << "Test 1: Assignment Validation" << endl;
    
    Instructor* instructor = new Instructor("I001", "Prof. Smith", "smith@test.com");
    Course* course = new Course("C001", "Test Course", 10, instructor);
    
    tm* deadline = new tm();
    time_t t = time(nullptr) + 86400; // Tomorrow
    localtime_r(&t, deadline);
    
    try {
        Assignment* badAssignment = new Assignment("A001", "", deadline, 100, course);
        cout << "  ✗ Should reject empty title" << endl;
        delete badAssignment;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Empty title rejected" << endl;
        passed++; total++;
    }
    
    try {
        Assignment* badAssignment = new Assignment("A002", "Test", deadline, 0, course);
        cout << "  ✗ Should reject maxScore <= 0" << endl;
        delete badAssignment;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Invalid maxScore rejected" << endl;
        passed++; total++;
    }
    
    Assignment* assignment = new Assignment("A003", "Homework 1", deadline, 100, course);
    total++;
    if (!assignment->isOverdue()) {
        cout << "  ✓ Assignment is not overdue" << endl;
        passed++;
    } else {
        cout << "  ✗ Assignment should not be overdue" << endl;
    }
    
    total++;
    if (assignment->getSubmissionCount() == 0) {
        cout << "  ✓ Initial submission count is 0" << endl;
        passed++;
    } else {
        cout << "  ✗ Submission count should be 0" << endl;
    }
    
    // Test 2: Grade validation and percentage
    cout << "\nTest 2: Grade Validation" << endl;
    
    Student* student = new Student("S001", "Alice", "alice@test.com");
    
    try {
        Grade* badGrade = new Grade("G001", -10, "Bad score");
        cout << "  ✗ Should reject negative score" << endl;
        delete badGrade;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Negative score rejected" << endl;
        passed++; total++;
    }
    
    // Test grade with valid score
    Grade* grade = new Grade("G002", 85, "Good work");
    
    total++;
    if (grade->getScore() == 85) {
        cout << "  ✓ Grade score set correctly" << endl;
        passed++;
    } else {
        cout << "  ✗ Grade score should be 85" << endl;
    }
    
    total++;
    if (!grade->setScore(-5)) {  // Negative score
        cout << "  ✓ Negative score in setScore rejected" << endl;
        passed++;
    } else {
        cout << "  ✗ Should reject negative score in setScore" << endl;
    }
    
    delete grade;
    
    // Test 3: Submission lifecycle
    cout << "\nTest 3: Submission Lifecycle" << endl;
    
    // Create a new submission for workflow test
    tm* nowTime = new tm();
    time_t now = time(nullptr);
    localtime_r(&now, nowTime);
    
    Submission* sub2 = new Submission("SUB002", nullptr, 1, false, nullptr, student, assignment);
    
    total++;
    if (sub2->getStatus() == Submission_ENUM_Status::Created) {
        cout << "  ✓ Initial status is Created" << endl;
        passed++;
    } else {
        cout << "  ✗ Initial status should be Created" << endl;
    }
    
    total++;
    if (sub2->submit()) {
        cout << "  ✓ Can submit from Created state" << endl;
        passed++;
    } else {
        cout << "  ✗ Should be able to submit" << endl;
    }
    
    total++;
    if (sub2->getStatus() == Submission_ENUM_Status::Submitted) {
        cout << "  ✓ Status is now Submitted" << endl;
        passed++;
    } else {
        cout << "  ✗ Status should be Submitted" << endl;
    }
    
    total++;
    if (sub2->startAutoChecks()) {
        cout << "  ✓ Can start auto checks" << endl;
        passed++;
    } else {
        cout << "  ✗ Should start auto checks" << endl;
    }
    
    total++;
    if (sub2->getStatus() == Submission_ENUM_Status::UnderCheck) {
        cout << "  ✓ Status is now UnderCheck" << endl;
        passed++;
    } else {
        cout << "  ✗ Status should be UnderCheck" << endl;
    }
    
    total++;
    if (sub2->checksPass()) {
        cout << "  ✓ Checks pass returns to Submitted" << endl;
        passed++;
    } else {
        cout << "  ✗ Should pass checks" << endl;
    }
    
    total++;
    if (sub2->startGrading()) {
        cout << "  ✓ Can start grading after checks pass" << endl;
        passed++;
    } else {
        cout << "  ✗ Should start grading" << endl;
    }
    
    total++;
    if (sub2->getStatus() == Submission_ENUM_Status::Grading) {
        cout << "  ✓ Status is now Grading" << endl;
        passed++;
    } else {
        cout << "  ✗ Status should be Grading" << endl;
    }
    
    // Test 4: Submission validation
    cout << "\nTest 4: Submission Validation" << endl;
    
    try {
        Submission* badSub = new Submission("SUB003", nullptr, 1, false, nullptr, nullptr, assignment);
        cout << "  ✗ Should reject null student" << endl;
        delete badSub;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Null student rejected" << endl;
        passed++; total++;
    }
    
    try {
        Submission* badSub = new Submission("SUB004", nullptr, 1, false, nullptr, student, nullptr);
        cout << "  ✗ Should reject null assignment" << endl;
        delete badSub;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Null assignment rejected" << endl;
        passed++; total++;
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

