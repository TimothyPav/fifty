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

class Lambertian : public Material {
    public:
        Lambertian(const color& albedo) : albedo(albedo) {}

        bool scatter(const Ray& r_in, const Hit_record& rec, color& attenuation, Ray& scattered)
        const override {
            auto scatter_direction = rec.normal + random_unit_vector();

            if (scatter_direction.near_zero())
                scatter_direction = rec.normal;

            scattered = Ray(rec.p, scatter_direction);
            attenuation = albedo;
            return true;
        }

    private:
        color albedo;
};

class Metal : public Material {
    public:
        Metal(const color& albedo) : albedo(albedo) {}

        bool scatter(const Ray& r_in, const Hit_record& rec, color& attenuation, Ray& scattered)
        const override {
            Vec3 reflected = reflect(r_in.direction(), rec.normal);
            scattered = Ray(rec.p, reflected);
            attenuation = albedo;
            return true;
        }

    private:
        color albedo;
};

#endif
