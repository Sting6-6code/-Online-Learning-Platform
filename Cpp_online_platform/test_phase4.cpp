#include <iostream>
#include <ctime>
#include "model_Model.h"
#include "Subscription.h"
#include "Payment.h"
#include "Refund.h"

using namespace std;

int main() {
    cout << "========================================" << endl;
    cout << "  Phase 4 Testing: Payment System" << endl;
    cout << "========================================\n" << endl;
    
    int passed = 0;
    int total = 0;
    
    // Test 1: Subscription validation
    cout << "Test 1: Subscription Validation" << endl;
    
    try {
        Subscription* badSub = new Subscription("SUB001", PlanType::Monthly, nullptr, nullptr);
        cout << "  ✗ Should reject null startAt" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Null startAt rejected" << endl;
        passed++; total++;
    }
    
    tm* startTime = new tm();
    time_t t = time(nullptr);
    localtime_r(&t, startTime);
    
    Subscription* trialSub = new Subscription("SUB002", PlanType::Trial, startTime, nullptr);
    total++;
    if (trialSub->getStatus() == Subscription_ENUM_Status::Trial) {
        cout << "  ✓ Trial subscription starts in Trial status" << endl;
        passed++;
    } else {
        cout << "  ✗ Trial subscription should start in Trial status" << endl;
    }
    
    total++;
    if (trialSub->getNextBillingAt() != nullptr) {
        cout << "  ✓ nextBillingAt auto-calculated" << endl;
        passed++;
    } else {
        cout << "  ✗ nextBillingAt should be auto-calculated" << endl;
    }
    
    tm* startTime2 = new tm();
    localtime_r(&t, startTime2);
    Subscription* monthlySub = new Subscription("SUB003", PlanType::Monthly, startTime2, nullptr);
    total++;
    if (monthlySub->getStatus() == Subscription_ENUM_Status::Active) {
        cout << "  ✓ Monthly subscription starts in Active status" << endl;
        passed++;
    } else {
        cout << "  ✗ Monthly subscription should start in Active status" << endl;
    }
    
    // Test 2: Payment validation
    cout << "\nTest 2: Payment Validation" << endl;
    
    try {
        Payment* badPayment = new Payment("PAY001", 0, PaymentStatus::Pending, nullptr, monthlySub);
        cout << "  ✗ Should reject zero amount" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Zero amount rejected" << endl;
        passed++; total++;
    }
    
    try {
        Payment* badPayment = new Payment("PAY002", 100, PaymentStatus::Pending, nullptr, nullptr);
        cout << "  ✗ Should reject null subscription" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Null subscription rejected" << endl;
        passed++; total++;
    }
    
    Payment* payment = new Payment("PAY003", 50.0, PaymentStatus::Pending, nullptr, monthlySub);
    
    total++;
    if (payment->getStatus() == PaymentStatus::Pending) {
        cout << "  ✓ Payment starts in Pending status" << endl;
        passed++;
    } else {
        cout << "  ✗ Payment should start in Pending status" << endl;
    }
    
    total++;
    if (!payment->isSuccessful()) {
        cout << "  ✓ Pending payment is not successful" << endl;
        passed++;
    } else {
        cout << "  ✗ Pending payment should not be successful" << endl;
    }
    
    // Test 3: Payment state transitions
    cout << "\nTest 3: Payment State Transitions" << endl;
    
    total++;
    if (payment->markSucceeded()) {
        cout << "  ✓ Can mark payment as succeeded" << endl;
        passed++;
    } else {
        cout << "  ✗ Should mark payment as succeeded" << endl;
    }
    
    total++;
    if (payment->isSuccessful()) {
        cout << "  ✓ Payment is now successful" << endl;
        passed++;
    } else {
        cout << "  ✗ Payment should be successful" << endl;
    }
    
    total++;
    if (payment->getPaidAt() != nullptr) {
        cout << "  ✓ paidAt timestamp set" << endl;
        passed++;
    } else {
        cout << "  ✗ paidAt should be set" << endl;
    }
    
    // Test 4: Refund initiation
    cout << "\nTest 4: Refund Operations" << endl;
    
    total++;
    if (payment->initiateRefund(30.0)) {
        cout << "  ✓ Can initiate refund" << endl;
        passed++;
    } else {
        cout << "  ✗ Should initiate refund" << endl;
    }
    
    total++;
    if (payment->getStatus() == PaymentStatus::Refunding) {
        cout << "  ✓ Payment status is Refunding" << endl;
        passed++;
    } else {
        cout << "  ✗ Payment should be in Refunding status" << endl;
    }
    
    total++;
    if (payment->getPaymentRefund() != nullptr) {
        cout << "  ✓ Refund object created" << endl;
        passed++;
    } else {
        cout << "  ✗ Refund object should be created" << endl;
    }
    
    total++;
    if (!payment->initiateRefund(10.0)) {
        cout << "  ✓ Cannot refund twice" << endl;
        passed++;
    } else {
        cout << "  ✗ Should not allow duplicate refund" << endl;
    }
    
    // Test 5: Complete refund
    cout << "\nTest 5: Complete Refund" << endl;
    
    total++;
    if (payment->completeRefund()) {
        cout << "  ✓ Can complete refund" << endl;
        passed++;
    } else {
        cout << "  ✗ Should complete refund" << endl;
    }
    
    total++;
    if (payment->getStatus() == PaymentStatus::Refunded) {
        cout << "  ✓ Payment status is Refunded" << endl;
        passed++;
    } else {
        cout << "  ✗ Payment should be in Refunded status" << endl;
    }
    
    Refund* refund = payment->getPaymentRefund();
    total++;
    if (refund != nullptr && refund->getProcessedAt() != nullptr) {
        cout << "  ✓ Refund processedAt timestamp set" << endl;
        passed++;
    } else {
        cout << "  ✗ Refund processedAt should be set" << endl;
    }
    
    // Test 6: Refund validation
    cout << "\nTest 6: Refund Validation" << endl;
    
    try {
        tm* reqTime = new tm();
        localtime_r(&t, reqTime);
        Refund* badRefund = new Refund("REF001", -10, reqTime, nullptr);
        cout << "  ✗ Should reject negative amount" << endl;
        total++;
    } catch (const invalid_argument& e) {
        cout << "  ✓ Negative refund amount rejected" << endl;
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

