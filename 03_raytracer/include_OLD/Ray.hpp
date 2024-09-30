#ifndef RAY_HPP
#define RAY_HPP

#include "Vector3D.hpp"

class Ray {
    private:
        Vector3D origin;
        Vector3D direction;

    public:
        Ray(const Vector3D& origin, const Vector3D& direction);

        Vector3D get_origin() const;
        Vector3D get_direction() const;
        Vector3D point_at_parameter(double t) const;
};

#endif
