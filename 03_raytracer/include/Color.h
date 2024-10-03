#ifndef COLOR_H
#define COLOR_H


#include <Vec3.h>
#include <iostream>

#include "Interval.h"

using color = Vec3;

inline double linear_to_gamma(double linear_component) {
    if(linear_component > 0)
        return std::sqrt(linear_component);
    return 0;
}

void write_color(std::ostream& out, const color& pixel_color) {
    auto r = pixel_color.x();
    auto g = pixel_color.y();
    auto b = pixel_color.z();

    r = linear_to_gamma(r); 
    g = linear_to_gamma(g);
    b = linear_to_gamma(b);

    // Translate the (0,1) range into (0,255) for a color conversion
    static const Interval intensity(0.000, 0.999);
    int rbyte = int(256 * intensity.clamp(r));
    int gbyte = int(256 * intensity.clamp(g));
    int bbyte = int(256 * intensity.clamp(b));

    // write out pixel color components
    out << rbyte << ' ' << gbyte << ' ' << bbyte << '\n';
}

#endif
