#ifndef HITTABLE_LIST
#define HITTABLE_LIST

#include "Utils.h"
#include "Hittable.h"

#include <vector>

class Hittable_List : public Hittable {
    public:
        std::vector<shared_ptr<Hittable>> objects; // a list of pointers to different hittable objects
        
        Hittable_List() {}
        Hittable_List(shared_ptr<Hittable> object) { add(object); }

        void clear() { objects.clear(); }

        void add(shared_ptr<Hittable> object) {
            objects.push_back(object);
        }

        bool hit(const Ray& r, Interval ray_t, Hit_record& rec) const override {
            Hit_record temp_rec;
            bool hit_anything = false;

            auto closest_so_far = ray_t.max;

            for (const auto& object : objects) {
                if (object->hit(r, Interval(ray_t.min, closest_so_far), temp_rec)) {
                    hit_anything = true;
                    closest_so_far = temp_rec.t;
                    rec = temp_rec;
                }
            }

            return hit_anything;
        }

};

#endif
