#ifndef CAMERA_H
#define CAMERA_h

#include "Hittable.h"
#include "Hittable_List.h"
#include "Sphere.h"
#include "Utils.h"

class Camera {
public:
  // public paramters
  double aspect_ratio = 1.0;
  int image_width = 100;
  int samples_per_pixel = 10; // count of random samples for each pixel
  int max_depth = 10;         // Max number a ray can bounce around

  void render(const Hittable &world) {
    initialize();

    std::cout << "P3\n" << image_width << ' ' << image_height << "\n255\n";

    for (int j = 0; j < image_height; j++) {

      std::clog << "\rScanlines remaining: " << (image_height - j) << ' '
                << std::flush;
      for (int i = 0; i < image_width; i++) {
        color pixel_color(0, 0, 0);
        for (int sample = 0; sample < samples_per_pixel; sample++) {
          Ray r = get_ray(i, j);
          pixel_color += ray_color(r, max_depth, world);
        }
        write_color(std::cout, pixel_samples_scale * pixel_color);
      }
    }
    std::clog << "\rDone.                 \n";
  }

private:
  // private variables
  int image_height;           // Rendered image height
  double pixel_samples_scale; // Color scale factor for a sum of pixel samples
  point3 center;              // Camera center
  point3 pixel00_loc;         // Location of pixel 0, 0
  Vec3 pixel_delta_u;         // Offset to pixel to the right
  Vec3 pixel_delta_v;         // Offset to pixel below

  void initialize() {
    image_height = int(image_width / aspect_ratio);
    image_height = (image_height < 1) ? 1 : image_height;

    pixel_samples_scale = 1.0 / samples_per_pixel;

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
    auto viewport_upper_left =
        center - Vec3(0, 0, focal_length) - viewport_u / 2 - viewport_v / 2;
    pixel00_loc = viewport_upper_left + 0.5 * (pixel_delta_u + pixel_delta_v);
  }

  Ray get_ray(int i, int j) const {
    // Construct a camera ray originating from the origin and directed at
    // randomly sampled point around the pixel location i, j.

    auto offset = sample_square();
    auto pixel_sample = pixel00_loc + ((i + offset.x()) * pixel_delta_u) +
                        ((j + offset.y()) * pixel_delta_v);

    auto ray_origin = center;
    auto ray_direction = pixel_sample - ray_origin;

    return Ray(ray_origin, ray_direction);
  }

  Vec3 sample_square() const {
    // Returns the vector to a random point in the [-.5, -.5] - [+.5, +.5] unit
    // square.
    return Vec3(random_double() - 0.5, random_double() - 0.5, 0);
  }

  color ray_color(const Ray &r,int depth, const Hittable &world) const {
      // Base case we decided to limit bounce rate. Not a true base case
      if (depth <= 0)
          return color(0,0,0);
    Hit_record rec;

    if (world.hit(r, Interval(0.001, infinity), rec)) {
      // Vec3 direction = random_on_hempisphere(rec.normal); UNIFORM DISTRIBUTION
      Vec3 direction = rec.normal + random_unit_vector();
      return 0.7 * ray_color(Ray(rec.p, direction),depth-1, world);
    }

    Vec3 unit_direction = unit_vector(r.direction());
    auto a = 0.5 * (unit_direction.y() + 1.0);
    return (1.0 - a) * color(1.0, 1.0, 1.0) + a * color(0.5, 0.7, 1.0);
  }
};

#endif
