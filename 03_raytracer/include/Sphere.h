#ifndef SPHERE_H
#define SPHERE_H

#include "Utils.h"

#include "Vec3.h"
#include "Hittable.h"

class Sphere : public Hittable {
    public:
        Sphere(const point3& center, double radius, shared_ptr<Material> mat)
            : center(center), radius(std::fmax(0, radius)), mat(mat) {}

        bool hit(const Ray& r, Interval ray_t, Hit_record& rec) const override {
            Vec3 oc = center - r.origin();
            auto a = r.direction().length_squared();
            auto h = dot(r.direction(), oc);
            auto c = oc.length_squared() - radius*radius;

            auto discriminant = h*h - a*c;
            if (discriminant < 0) return false;

            auto sqrtd = sqrt(discriminant);

            // Find the nearest root in the range of tmin and tmax
            auto root = (h - sqrtd) / a;
            if (!ray_t.surrounds(root)){
                root = (h + sqrtd) / a;
                if (!ray_t.surrounds(root)){
                    return false; // both points failed intersection test
                }
            }

            rec.t = root;
            rec.p = r.at(rec.t);
            Vec3 outward_normal = (rec.p - center) / radius;
            rec.set_face_normal(r, outward_normal);
            rec.mat = mat;

            return true;
        }


    
    private:
        point3 center;
        double radius;
        shared_ptr<Material> mat;

};

#endif
