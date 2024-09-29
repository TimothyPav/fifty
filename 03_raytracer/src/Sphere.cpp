#include <cmath>

#include "Sphere.hpp"

Sphere::Sphere(const Vector3D& center, double radius) : center(center), radius(radius) {}

bool Sphere::intersect(const Ray& ray, double& t){
    Vector3D vecL = center - ray.get_origin();
    double tc = vecL.dot_product(ray.get_direction());

    if (tc < 0.0) return false; // sphere is behind array

    double d2 = vecL.dot_product(vecL) - (tc * tc);
    double radius2 = radius * radius;

    if (d2 > radius2) return false; // No intersection

    double t1c = sqrt(radius2 - d2);
    double t1 = tc - t1c; // FIRST INTERSECTION POINT
    double t2 = tc + t1c; // SECOND INTERSECTION POINT

    if (t1 > 0.0) t = t1;
    else if (t2 > 0.0) t = t2;
    else return false;

    return true;
}
