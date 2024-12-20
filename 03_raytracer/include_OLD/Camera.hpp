#ifndef CAMERA_HPP
#define CAMERA_HPP

#include "Vector3D.hpp"
#include "Ray.hpp"

class Camera {
    private:
        Vector3D position; // position of camera in 3D space
        Vector3D direction; // where camera is looking
        double fov;
        double aspect_ratio;

    public:
        Camera(const Vector3D& position, const Vector3D& direction, double fov, double aspect_ration);

        Ray get_ray(double u, double v) const; // generate ray for pixel coordinates
};

#endif
