#ifndef CAMERA_H
#define CAMERA_h

#include "Utils.h"
#include "Hittable.h"
#include "Hittable_List.h"
#include "Sphere.h"

class Camera {
public:
  // public paramters
  double aspect_ratio = 1.0;
  int image_width = 100;

  void render(const Hittable &world) {
    initialize();

    std::cout << "P3\n" << image_width << ' ' << image_height << "\n255\n";

    for (int j = 0; j < image_height; j++) {

      std::clog << "\rScanlines remaining: " << (image_height - j) << ' '
                << std::flush;
      for (int i = 0; i < image_width; i++) {
        auto pixel_center =
            pixel00_loc + (i * pixel_delta_u) + (j * pixel_delta_v);
        auto ray_direction = pixel_center - center;
        Ray r(center, ray_direction);

        // A vector because thats how we represent the color
        color pixel_color = ray_color(r, world);

        write_color(std::cout, pixel_color);
      }
    }
    std::clog << "\rDone.                 \n";
  }

private:
  // private variables
    int    image_height;   // Rendered image height
    point3 center;         // Camera center
    point3 pixel00_loc;    // Location of pixel 0, 0
    Vec3   pixel_delta_u;  // Offset to pixel to the right
    Vec3   pixel_delta_v;  // Offset to pixel below

  void initialize() {
    image_height = int(image_width / aspect_ratio);
    image_height = (image_height < 1) ? 1 : image_height;

    // world
    Hittable_List world;
    world.add(make_shared<Sphere>(point3(0, 0, -1), 0.5));
    world.add(make_shared<Sphere>(point3(0, -100.5, -1), 100));

    // camera
    auto focal_length = 1.0;
    auto viewport_height = 2.0;
    auto viewport_width =
        viewport_height * (double(image_width) / image_height);
    center = point3(0, 0, 0);

    // calculate vectors across the horizontal viewport and vertical viewport
    // EDGES
    auto viewport_u = Vec3(viewport_width, 0, 0);
    auto viewport_v = Vec3(0, -viewport_height, 0);

    // calculate horizontal and vertical deltas for each pixel. Does the math on
    // how much each vector must move to move on to the next pixel
    pixel_delta_u = viewport_u / image_width;
    pixel_delta_v = viewport_v / image_height;

    // calculate top left pixel
    auto viewport_upper_left = center - Vec3(0, 0, focal_length) -
                               viewport_u / 2 - viewport_v / 2;
    pixel00_loc =
        viewport_upper_left + 0.5 * (pixel_delta_u + pixel_delta_v);
  }

  color ray_color(const Ray &r, const Hittable &world) const {
    Hit_record rec;

    if (world.hit(r, Interval(0, infinity), rec)) {
      return 0.5 * (rec.normal + color(1, 1, 1));
    }

    Vec3 unit_direction = unit_vector(r.direction());
    auto a = 0.5 * (unit_direction.y() + 1.0);
    return (1.0 - a) * color(1.0, 1.0, 1.0) + a * color(0.5, 0.7, 1.0);
  }
};

#endif
