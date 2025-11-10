#include <iostream>
#include <stdexcept>
#include "model_Model.h"
#include "User.h"
#include "Student.h"
#include "Instructor.h"
#include "Administrator.h"
#include "Course.h"
#include "Enrollment.h"

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

void testUserValidation() {
    cout << "\n=== Testing User Validation ===" << endl;
    
    // Test 1: Valid student creation should succeed
    try {
        Student* s1 = new Student("S001", "Alice", "alice@example.com");
        test("Create valid Student", true);
        delete s1;
    } catch (const exception& e) {
        test("Create valid Student", false);
        cout << "  Error: " << e.what() << endl;
    }
    
    // Test 2: Empty id should throw exception
    try {
        Student* s2 = new Student("", "Bob", "bob@example.com");
        test("Empty id throws exception", false);
        delete s2;
    } catch (const invalid_argument& e) {
        test("Empty id throws exception", true);
        cout << "  Expected error: " << e.what() << endl;
    }
    
    // Test 3: Invalid email should throw exception
    try {
        Student* s3 = new Student("S003", "Charlie", "invalid-email");
        test("Invalid email throws exception", false);
        delete s3;
    } catch (const invalid_argument& e) {
        test("Invalid email throws exception", true);
        cout << "  Expected error: " << e.what() << endl;
    }
    
    // Test 4: Empty name should throw exception
    try {
        Student* s4 = new Student("S004", "", "dave@example.com");
        test("Empty name throws exception", false);
        delete s4;
    } catch (const invalid_argument& e) {
        test("Empty name throws exception", true);
        cout << "  Expected error: " << e.what() << endl;
    }
    
    // Test 5: Valid instructor creation
    try {
        Instructor* i1 = new Instructor("I001", "Prof. Smith", "smith@example.com");
        test("Create valid Instructor", true);
        delete i1;
    } catch (const exception& e) {
        test("Create valid Instructor", false);
        cout << "  Error: " << e.what() << endl;
    }
    
    // Test 6: Valid administrator creation
    try {
        Administrator* a1 = new Administrator("A001", "Admin User", "admin@example.com");
        test("Create valid Administrator", true);
        delete a1;
    } catch (const exception& e) {
        test("Create valid Administrator", false);
        cout << "  Error: " << e.what() << endl;
    }
}

void testStudentMethods() {
    cout << "\n=== Testing Student Methods ===" << endl;
    
    try {
        // Create student
        Student* student = new Student("S100", "Test Student", "student@test.com");
        
        // Test initial enrollment count
        int count = student->getActiveEnrollmentCount();
        test("Initial enrollment count is 0", count == 0);
        
        // Create a course and instructor for testing
        Instructor* instructor = new Instructor("I100", "Test Instructor", "instructor@test.com");
        Course* course = new Course("C100", "Test Course", 30, instructor);
        
        // Create enrollment with Active status
        tm* now = new tm();
        time_t t = time(nullptr);
        localtime_r(&t, now);
        
        Enrollment* enrollment1 = new Enrollment("E100", EnrollmentStatus::Active, now, student, course);
        
        // Check active enrollment count
        count = student->getActiveEnrollmentCount();
        test("Active enrollment count is 1", count == 1);
        
        // Create another enrollment with Dropped status
        tm* now2 = new tm();
        localtime_r(&t, now2);
        Enrollment* enrollment2 = new Enrollment("E101", EnrollmentStatus::Dropped, now2, student, course);
        
        // Check active enrollment count (should still be 1)
        count = student->getActiveEnrollmentCount();
        test("Active enrollment count still 1 after Dropped enrollment", count == 1);
        
        // Cleanup
        delete enrollment1;
        delete enrollment2;
        delete course;
        delete instructor;
        delete student;
        
    } catch (const exception& e) {
        test("Student methods test", false);
        cout << "  Error: " << e.what() << endl;
    }
}

void testInstructorMethods() {
    cout << "\n=== Testing Instructor Methods ===" << endl;
    
    try {
        // Create instructor
        Instructor* instructor = new Instructor("I200", "Test Prof", "prof@test.com");
        
        // Test initial course counts
        int activeCount = instructor->getActiveCourseCount();
        test("Initial active course count is 0", activeCount == 0);
        
        vector<Course*>* inProgressCourses = instructor->getCoursesInProgress();
        test("Initial in-progress courses count is 0", inProgressCourses->size() == 0);
        delete inProgressCourses;
        
        // Create courses with different statuses
        Course* course1 = new Course("C200", "Active Course", 30, instructor);
        // Course starts in Draft status
        
        Course* course2 = new Course("C201", "Another Course", 25, instructor);
        
        // Test active course count (Draft courses should be counted as active)
        activeCount = instructor->getActiveCourseCount();
        test("Active course count is 2", activeCount == 2);
        
        // Note: We cannot easily test InProgress status without publishing and starting courses
        // which requires more setup (lessons, assignments, enrollments)
        
        // Cleanup
        delete course1;
        delete course2;
        delete instructor;
        
    } catch (const exception& e) {
        test("Instructor methods test", false);
        cout << "  Error: " << e.what() << endl;
    }
}

int main() {
    cout << "========================================" << endl;
    cout << "  Phase 1 Testing: User Module" << endl;
    cout << "========================================" << endl;
    
    testUserValidation();
    testStudentMethods();
    testInstructorMethods();
    
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

