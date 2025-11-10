#include "Utils.h"
#include <sstream>
#include <iomanip>

// Compare two times: returns -1 if t1 < t2, 0 if equal, 1 if t1 > t2
int compareTimes(tm* t1, tm* t2) {
    if (t1 == nullptr || t2 == nullptr) {
        return (t1 == t2) ? 0 : (t1 == nullptr ? -1 : 1);
    }
    
    time_t time1 = mktime(t1);
    time_t time2 = mktime(t2);
    
    if (time1 < time2) return -1;
    if (time1 > time2) return 1;
    return 0;
}

// Generate unique ID using timestamp and prefix
string generateId(const string& prefix) {
    time_t now = time(nullptr);
    static int counter = 0;
    
    stringstream ss;
    ss << prefix << "_" << now << "_" << (++counter);
    return ss.str();
}

// Add days to a time
tm* addDays(tm* base, int days) {
    if (base == nullptr) return nullptr;
    
    tm* result = new tm(*base);
    time_t baseTime = mktime(result);
    baseTime += days * 24 * 60 * 60;  // Add days in seconds
    
    tm* newTime = localtime(&baseTime);
    *result = *newTime;
    
    return result;
}

// Add months to a time
tm* addMonths(tm* base, int months) {
    if (base == nullptr) return nullptr;
    
    tm* result = new tm(*base);
    result->tm_mon += months;
    
    // Normalize the time structure
    mktime(result);
    
    return result;
}

// Get current time
tm* getCurrentTime() {
    time_t now = time(nullptr);
    tm* currentTime = new tm();
    *currentTime = *localtime(&now);
    return currentTime;
}

