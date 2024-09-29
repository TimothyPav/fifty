#include <iostream>

#include "Vector3D.hpp"
#include "Ray.hpp"
#include "Sphere.hpp"

int main() {
    Vector3D origin(0.0,0.0,0.0);
    Vector3D direction(0.0,0.1,1.2);
    Ray ray(origin, direction);

    Vector3D sphere_center(0.0,0.0,5.0);
    Sphere sphere(sphere_center, 1.0);

    double t;
    if (sphere.intersect(ray, t)) {
        std::cout << "Ray intersects sphere at t = " << t << std::endl;
        std::cout << "Actual intersection point at: " << std::endl;
        Vector3D intersection_point = ray.point_at_parameter(t);
        intersection_point.display_vector();
    } else {
        std::cout << "No intersection." << std::endl;
    }

    return 0;
}
