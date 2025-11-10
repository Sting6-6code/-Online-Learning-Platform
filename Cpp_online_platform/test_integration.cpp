#include <iostream>
#include <ctime>
#include "model_Model.h"
#include "User.h"
#include "Student.h"
#include "Instructor.h"
#include "Administrator.h"
#include "Course.h"
#include "Enrollment.h"
#include "Lesson.h"
#include "Assignment.h"
#include "Subscription.h"
#include "Payment.h"

using namespace std;

int main() {
    cout << "========================================" << endl;
    cout << "  Integration Test: Full System Flow" << endl;
    cout << "========================================\n" << endl;
    
    int passed = 0;
    int total = 0;
    
    // Step 1: Create users
    cout << "Step 1: Create Users" << endl;
    Instructor* instructor = new Instructor("INST001", "Dr. Smith", "smith@university.edu");
    Student* alice = new Student("STU001", "Alice Johnson", "alice@student.edu");
    Student* bob = new Student("STU002", "Bob Williams", "bob@student.edu");
    Administrator* admin = new Administrator("ADM001", "System Admin", "admin@system.edu");
    
    total++;
    if (instructor && alice && bob && admin) {
        cout << "  ✓ All users created successfully" << endl;
        passed++;
    }
    
    // Step 2: Create and setup course
    cout << "\nStep 2: Create and Setup Course" << endl;
    Course* course = new Course("CS101", "Introduction to Programming", 2, instructor);
    
    Lesson* lesson1 = new Lesson("LES001", "Variables and Data Types", 1, course);
    Lesson* lesson2 = new Lesson("LES002", "Control Structures", 2, course);
    
    tm* deadline = new tm();
    time_t t = time(nullptr) + 7 * 24 * 60 * 60; // 1 week from now
    localtime_r(&t, deadline);
    Assignment* assignment = new Assignment("ASS001", "Hello World Program", deadline, 100, course);
    
    total++;
    if (course->hasMinimumContent()) {
        cout << "  ✓ Course has minimum content" << endl;
        passed++;
    }
    
    // Step 3: Publish and open enrollment
    cout << "\nStep 3: Publish and Open Enrollment" << endl;
    total++;
    if (course->publish() && course->openEnrollment()) {
        cout << "  ✓ Course published and enrollment opened" << endl;
        passed++;
    }
    
    // Step 4: Student enrollment
    cout << "\nStep 4: Student Enrollment" << endl;
    Enrollment* enroll1 = course->enroll(alice);
    Enrollment* enroll2 = course->enroll(bob);
    
    total++;
    if (enroll1 && enroll2 && 
        enroll1->getStatus() == EnrollmentStatus::Active &&
        enroll2->getStatus() == EnrollmentStatus::Active) {
        cout << "  ✓ Students enrolled successfully" << endl;
        passed++;
    }
    
    total++;
    if (alice->getActiveEnrollmentCount() == 1) {
        cout << "  ✓ Student enrollment count correct" << endl;
        passed++;
    }
    
    // Step 5: Start course
    cout << "\nStep 5: Start Course" << endl;
    total++;
    if (course->startCourse()) {
        cout << "  ✓ Course started successfully" << endl;
        passed++;
    }
    
    total++;
    if (course->getStatus() == Status::InProgress) {
        cout << "  ✓ Course status is InProgress" << endl;
        passed++;
    }
    
    // Step 6: Subscription and Payment
    cout << "\nStep 6: Subscription and Payment" << endl;
    tm* startTime = new tm();
    localtime_r(&t, startTime);
    Subscription* subscription = new Subscription("SUB001", PlanType::Monthly, startTime, nullptr);
    
    total++;
    if (subscription->getStatus() == Subscription_ENUM_Status::Active) {
        cout << "  ✓ Subscription created and active" << endl;
        passed++;
    }
    
    Payment* payment = new Payment("PAY001", 49.99, PaymentStatus::Pending, nullptr, subscription);
    payment->markSucceeded();
    
    total++;
    if (payment->isSuccessful()) {
        cout << "  ✓ Payment processed successfully" << endl;
        passed++;
    }
    
    // Step 7: Refund processing
    cout << "\nStep 7: Refund Processing" << endl;
    total++;
    if (payment->initiateRefund(25.0)) {
        cout << "  ✓ Refund initiated" << endl;
        passed++;
    }
    
    total++;
    if (payment->completeRefund()) {
        cout << "  ✓ Refund completed" << endl;
        passed++;
    }
    
    // Step 8: Complete course
    cout << "\nStep 8: Complete Course" << endl;
    total++;
    if (course->complete()) {
        cout << "  ✓ Course completed successfully" << endl;
        passed++;
    }
    
    total++;
    if (course->getStatus() == Status::Completed) {
        cout << "  ✓ Course status is Completed" << endl;
        passed++;
    }
    
    // Summary
    cout << "\n========================================" << endl;
    cout << "  Integration Test Results" << endl;
    cout << "========================================" << endl;
    cout << "Passed: " << passed << " / " << total << endl;
    
    if (passed == total) {
        cout << "\n🎉 Full system integration successful!" << endl;
        cout << "✅ Online Learning Platform MVP is ready!" << endl;
        return 0;
    } else {
        cout << "\n❌ Integration test failed!" << endl;
        return 1;
    }
}

