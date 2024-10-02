#ifndef HITTABLE_H
#define HITTABLE_H

#include "Utils.h"

class Hit_record {
    public:
        point3 p;
        Vec3 normal;
        double t;
        bool front_face;

        void set_face_normal(const Ray& r, const Vec3& outward_normal) {
            // Sets the hit record normal vector.
            // NOTE: the parameter `outward normal` is assumed to be a unit vector

            // if less than zero ray is inside of the sphere
            // if greater than zero ray is outside of the sphere
            front_face = dot(r.direction(), outward_normal) < 0;
            normal = front_face ? outward_normal : -outward_normal;
        }
};

class Hittable {
    public:
        virtual ~Hittable() = default;

        virtual bool hit(const Ray& r, Interval ray_t, Hit_record& rec) const = 0;
};

#endif
