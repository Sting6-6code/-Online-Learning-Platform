#ifndef UTILS_H
#define UTILS_H

#include <string>
#include <ctime>

using namespace std;

// Time comparison: returns -1 if t1 < t2, 0 if equal, 1 if t1 > t2
int compareTimes(tm* t1, tm* t2);

// Generate unique ID with prefix
string generateId(const string& prefix);

// Add days to a time
tm* addDays(tm* base, int days);

// Add months to a time
tm* addMonths(tm* base, int months);

// Get current time
tm* getCurrentTime();

#endif // UTILS_H

