#include <cmath>
#include <iostream>

#include "Camera.hpp"

Camera::Camera(const Vector3D& position, const Vector3D& direction, double fov, double aspect_ratio, double near_plane) :
    position(position), direction(direction.normalize()), fov(fov), aspect_ratio(aspect_ratio), near_plane(near_plane) {}

Ray Camera::get_ray(double u, double v) const {
    std::cout << "u, v: " << u << ", " << v << std::endl;
    double viewport_height = 2.0 * near_plane * tan(fov/2.0);
    double viewport_width = viewport_height * aspect_ratio;

    // right and up vectors to orient yourself on the plan/viewport
    Vector3D right = direction.cross_product(Vector3D(0,1,0)).normalize();
    Vector3D up = right.cross_product(direction).normalize();

    // calculate the lower-left corner of the viewport
    Vector3D lower_left = position - (right * (viewport_width / 2)) - (up * (viewport_height/2)) - (direction * near_plane);

    // get ray direction
    Vector3D ray_direction = lower_left + (right * (u * viewport_width)) + (up * (v * viewport_height)) - position;

    return Ray(position, ray_direction.normalize());
}
