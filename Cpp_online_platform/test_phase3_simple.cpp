#include <iostream>
#include <ctime>
#include "model_Model.h"
#include "User.h"
#include "Student.h"
#include "Instructor.h"
#include "Course.h"
#include "Assignment.h"
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
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Empty title rejected" << endl;
        passed++; total++;
    }
    
    try {
        Assignment* badAssignment = new Assignment("A002", "Test", deadline, 0, course);
        cout << "  ✗ Should reject maxScore <= 0" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Invalid maxScore rejected" << endl;
        passed++; total++;
    }
    
    try {
        Assignment* badAssignment = new Assignment("A003", "Test", nullptr, 100, course);
        cout << "  ✗ Should reject null deadline" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Null deadline rejected" << endl;
        passed++; total++;
    }
    
    Assignment* assignment = new Assignment("A004", "Homework 1", deadline, 100, course);
    
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
    
    total++;
    if (assignment->getMaxScore() == 100) {
        cout << "  ✓ MaxScore is correct" << endl;
        passed++;
    } else {
        cout << "  ✗ MaxScore should be 100" << endl;
    }
    
    // Test 2: Grade validation
    cout << "\nTest 2: Grade Validation" << endl;
    
    try {
        Grade* badGrade = new Grade("G001", -10, "Bad score");
        cout << "  ✗ Should reject negative score" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Negative score rejected" << endl;
        passed++; total++;
    }
    
    Grade* grade = new Grade("G002", 85, "Good work");
    
    total++;
    if (grade->getScore() == 85) {
        cout << "  ✓ Grade score set correctly" << endl;
        passed++;
    } else {
        cout << "  ✗ Grade score should be 85" << endl;
    }
    
    total++;
    if (!grade->setScore(-5)) {
        cout << "  ✓ Negative score in setScore rejected" << endl;
        passed++;
    } else {
        cout << "  ✗ Should reject negative score in setScore" << endl;
    }
    
    total++;
    if (grade->setScore(90)) {
        cout << "  ✓ Can update score to valid value" << endl;
        passed++;
    } else {
        cout << "  ✗ Should accept valid score" << endl;
    }
    
    total++;
    if (grade->getScore() == 90) {
        cout << "  ✓ Score updated correctly" << endl;
        passed++;
    } else {
        cout << "  ✗ Score should be 90" << endl;
    }
    
    // Test 3: Assignment with past deadline
    cout << "\nTest 3: Overdue Assignment" << endl;
    
    tm* pastDeadline = new tm();
    time_t pastTime = time(nullptr) - 86400; // Yesterday
    localtime_r(&pastTime, pastDeadline);
    
    Assignment* overdueAssignment = new Assignment("A005", "Late Homework", pastDeadline, 50, course);
    
    total++;
    if (overdueAssignment->isOverdue()) {
        cout << "  ✓ Overdue assignment detected" << endl;
        passed++;
    } else {
        cout << "  ✗ Assignment should be overdue" << endl;
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

