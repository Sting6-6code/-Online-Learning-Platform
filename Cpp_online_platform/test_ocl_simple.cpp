#include <iostream>
#include <ctime>
#include "model_Model.h"
#include "User.h"
#include "Student.h"
#include "Instructor.h"
#include "Course.h"
#include "Enrollment.h"
#include "Lesson.h"
#include "Assignment.h"
#include "Grade.h"
#include "Payment.h"
#include "Subscription.h"

using namespace std;

int main() {
    cout << "========================================" << endl;
    cout << "  OCL Constraints Validation (Simple)" << endl;
    cout << "========================================\n" << endl;
    
    int passed = 0;
    int total = 0;
    
    // OCL 1: SeatsNotExceeded
    cout << "OCL 1: SeatsNotExceeded" << endl;
    
    Instructor* instructor = new Instructor("I001", "Prof. Smith", "smith@test.com");
    Course* course = new Course("C001", "Small Course", 2, instructor);
    
    Lesson* lesson = new Lesson("L001", "Lesson 1", 1, course);
    tm* deadline = new tm();
    time_t t = time(nullptr) + 86400;
    localtime_r(&t, deadline);
    Assignment* assignment = new Assignment("A001", "HW1", deadline, 100, course);
    
    course->publish();
    course->openEnrollment();
    
    Student* s1 = new Student("S001", "Alice", "alice@test.com");
    Student* s2 = new Student("S002", "Bob", "bob@test.com");
    Student* s3 = new Student("S003", "Charlie", "charlie@test.com");
    
    Enrollment* e1 = course->enroll(s1);
    Enrollment* e2 = course->enroll(s2);
    Enrollment* e3 = course->enroll(s3);
    
    total++;
    if (e1->getStatus() == EnrollmentStatus::Active &&
        e2->getStatus() == EnrollmentStatus::Active &&
        e3->getStatus() == EnrollmentStatus::Waitlisted) {
        cout << "  ✓ Seats constraint enforced" << endl;
        passed++;
    } else {
        cout << "  ✗ Seats constraint failed" << endl;
    }
    
    // OCL 2: EnrollmentOnlyAfterPublish
    cout << "\nOCL 2: EnrollmentOnlyAfterPublish" << endl;
    
    Instructor* instructor2 = new Instructor("I002", "Prof. Jones", "jones@test.com");
    Course* draftCourse = new Course("C002", "Draft Course", 10, instructor2);
    Student* s4 = new Student("S004", "David", "david@test.com");
    
    try {
        tm* now = new tm();
        time_t nowT = time(nullptr);
        localtime_r(&nowT, now);
        Enrollment* badEnroll = new Enrollment("E999", EnrollmentStatus::Active, now, s4, draftCourse);
        cout << "  ✗ Should reject Draft enrollment" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Draft enrollment rejected" << endl;
        passed++; total++;
    }
    
    // OCL 3: CourseStartRequiresStudent
    cout << "\nOCL 3: CourseStartRequiresStudent" << endl;
    
    Course* emptyCourse = new Course("C003", "Empty", 10, instructor2);
    Lesson* lesson2 = new Lesson("L002", "Lesson", 1, emptyCourse);
    tm* deadline2 = new tm();
    localtime_r(&t, deadline2);
    Assignment* assign2 = new Assignment("A002", "HW", deadline2, 100, emptyCourse);
    
    emptyCourse->publish();
    emptyCourse->openEnrollment();
    
    total++;
    if (!emptyCourse->startCourse()) {
        cout << "  ✓ Cannot start without students" << endl;
        passed++;
    } else {
        cout << "  ✗ Should not start" << endl;
    }
    
    // OCL 4: GradeWithinRange
    cout << "\nOCL 4: GradeWithinRange" << endl;
    
    try {
        Grade* badGrade = new Grade("G001", -5, "Negative");
        cout << "  ✗ Should reject negative" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Negative grade rejected" << endl;
        passed++; total++;
    }
    
    // OCL 5: RefundOnlyForSucceededPayment
    cout << "\nOCL 5: RefundOnlyForSucceededPayment" << endl;
    
    tm* startTime = new tm();
    localtime_r(&t, startTime);
    Subscription* sub = new Subscription("SUB001", PlanType::Monthly, startTime, nullptr);
    Payment* payment = new Payment("PAY001", 100.0, PaymentStatus::Pending, nullptr, sub);
    
    total++;
    if (!payment->initiateRefund(50.0)) {
        cout << "  ✓ Pending payment cannot refund" << endl;
        passed++;
    } else {
        cout << "  ✗ Should not refund pending" << endl;
    }
    
    payment->markSucceeded();
    total++;
    if (payment->initiateRefund(50.0)) {
        cout << "  ✓ Succeeded payment can refund" << endl;
        passed++;
    } else {
        cout << "  ✗ Should refund" << endl;
    }
    
    // OCL 6: PublishRequiresContent
    cout << "\nOCL 6: PublishRequiresContent" << endl;
    
    Course* noContent = new Course("C004", "No Content", 10, instructor);
    
    total++;
    if (!noContent->publish()) {
        cout << "  ✓ Cannot publish without content" << endl;
        passed++;
    } else {
        cout << "  ✗ Should not publish" << endl;
    }
    
    // Summary
    cout << "\n========================================" << endl;
    cout << "  Test Results" << endl;
    cout << "========================================" << endl;
    cout << "Passed: " << passed << " / " << total << endl;
    
    if (passed == total) {
        cout << "\n🎉 All OCL constraints validated!" << endl;
        return 0;
    } else {
        cout << "\n❌ Some constraints failed!" << endl;
        return 1;
    }
}

