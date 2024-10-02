#ifndef UTILS_H
#define UTILS_H

#include <cmath>
#include <iostream>
#include <limits>
#include <memory>

// C++ using
using std::make_shared;
using std::shared_ptr;

// Constants
const double infinity = std::numeric_limits<double>::infinity();
const double pi = 3.1415926535897932385;

// Util functions
inline double degrees_to_radians(double degrees) {
    return degrees * pi / 180.0;
}

// Common headers
#include "Color.h"
#include "Ray.h"
#include "Vec3.h"
#include "Interval.h"

#endif

