#ifndef MATERIAL_H
#define MATERIAL_H

#include "Hittable.h"

class Material {
    public:
        virtual ~Material() = default;

        virtual bool scatter (
            const Ray& r_in, const Hit_record& rec, color& attenuation, Ray& scattered
        ) const {
            return false;
        }
}; 

#endif
