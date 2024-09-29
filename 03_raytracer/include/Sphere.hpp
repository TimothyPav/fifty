#ifndef SPHERE_HPP
#define SPHERE_HPP

#include "Vector3D.hpp"
#include "Ray.hpp"

class Sphere {
    public:
        Vector3D center;
        double radius;

        Sphere(const Vector3D&, double radius);

        bool intersect(const Ray& ray, double& t);
};

#endif
