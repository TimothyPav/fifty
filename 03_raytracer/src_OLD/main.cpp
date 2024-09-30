#include <FL/math.h>
#include <fstream>
#include <iostream>

#include "Camera.hpp"
#include "Ray.hpp"
#include "Sphere.hpp"
#include "Vector3D.hpp"

// Simple RGB struct for pixel color
struct Color {
  int r, g, b;
};

// Function to write the image to a PPM file (simple image format)
void write_image(int width, int height, Color *buffer) {
  std::ofstream image_file("output.ppm");
  image_file << "P3\n" << width << " " << height << "\n255\n";
  for (int i = 0; i < width * height; ++i) {
    image_file << buffer[i].r << " " << buffer[i].g << " " << buffer[i].b
               << "\n";
  }
  image_file.close();
}

int main() {
  int image_width = 640;
  int image_height = 360;

  Color *buffer = new Color[image_width * image_height];
  Vector3D camera_position(0, 0, 0);
  Vector3D camera_direction(0, 0, -1);
  double fov = M_PI / 4;
  double aspect_ratio = 16.0 / 9.0;
  double near_plane = 1.0;

  Camera camera(camera_position, camera_direction, fov, aspect_ratio);

  Sphere sphere(Vector3D(0, 0, -5), 1);
  Vector3D light_vector = Vector3D(5, 5, -5);
  Sphere light_source(light_vector, .1);
  Vector3D light_position = light_vector.normalize();

  for (int y = 0; y < image_height; ++y) {
    for (int x = 0; x < image_width; ++x) {

      double u = (x + 0.5) / image_width; // Center of the pixel
      double v = (y + 0.5) / image_height;

      // Get a ray from the camera through the pixel (x, y)
      Ray ray = camera.get_ray(u, v);

      // Check for intersection with the sphere
      double t;
      Vector3D hitnormal;
      if (sphere.intersect(ray, t, hitnormal)) {
            // Calculate the point of intersection
            Vector3D hit_point = ray.get_origin() + (ray.get_direction() * t);
            
            // Calculate light direction
            Vector3D light_direction = (light_position - hit_point).normalize();
            
            // Calculate intensity based on Lambertian reflection (diffuse)
            double intensity = std::max(0.0, hitnormal.dot_product(light_direction));

            // Cast a shadow ray from the hit point to the light
            Vector3D shadow_origin = hit_point + hitnormal * 0.001; // Bias to avoid self-intersection
            Ray shadow_ray(shadow_origin, light_direction);
            bool in_shadow = false;

            for (const auto &object : scene_objects) {
                double shadow_t;
                if (object->intersect(shadow_ray, shadow_t)) {
                    in_shadow = true;
                    break;
                }
            }

            // Set pixel color based on whether the point is in shadow
            if (in_shadow) {
                buffer[y * image_width + x] = {50, 50, 50}; // Shadow color
            } else {
                int red = static_cast<int>(255 * intensity);
                buffer[y * image_width + x] = {red, 0, 0}; // Diffuse shading with red color
            } 
        } else {
            // Otherwise, color it blue for background
            buffer[y * image_width + x] = {0, 0, 255}; // White
      }
    }
  }

  // Write the image to a file
  write_image(image_width, image_height, buffer);
  std::cout << "COMPLETED\n";

  delete[] buffer;
  return 0;
}
