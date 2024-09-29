#include <iostream>
#include <FL/math.h>
#include <fstream>

#include "Vector3D.hpp"
#include "Ray.hpp"
#include "Sphere.hpp"
#include "Camera.hpp"

// Simple RGB struct for pixel color
struct Color {
    int r, g, b;
};

// Function to write the image to a PPM file (simple image format)
void write_image(int width, int height, Color* buffer) {
    std::ofstream image_file("output.ppm");
    image_file << "P3\n" << width << " " << height << "\n255\n";
    for (int i = 0; i < width * height; ++i) {
        image_file << buffer[i].r << " " << buffer[i].g << " " << buffer[i].b << "\n";
    }
    image_file.close();
}

int main() {
    const int image_width = 400;
    const int image_height = 300;

    Color* buffer = new Color[image_width * image_height];
    // Define camera parameters
    Vector3D camera_position(0, 0, 0); // Camera at the origin
    Vector3D camera_direction(0, 0, -1); // Looking down the negative z-axis
    double fov = M_PI / 4; // 45 degrees field of view
    double aspect_ratio = 16.0 / 9.0; // Example aspect ratio
    double near_plane = 1.0; // Distance to near clipping plane

    // Create the camera
    Sphere sphere(Vector3D(0, 0, -5), 1);
    Camera camera(camera_position, camera_direction, fov, aspect_ratio, near_plane);

    for (int y = 0; y < image_height; ++y) {
        for (int x = 0; x < image_width; ++x) {

            double u = (x + 0.5) / image_width; // Center of the pixel
            double v = (y + 0.5) / image_height;

            // Get a ray from the camera through the pixel (x, y)
            Ray ray = camera.get_ray(u, v);
            ray.get_origin().display_vector();

            // Check for intersection with the sphere
            double t;
            if (sphere.intersect(ray, t)) {
                std::cout << "Intersected\n";
                // If it hits the sphere, color it red
                buffer[y * image_width + x] = {255, 0, 0}; // Red
            } else {
                // Otherwise, color it blue for background
                buffer[y * image_width + x] = {0, 0, 255}; // Blue
            }
        }
    }

    // Write the image to a file
    write_image(image_width, image_height, buffer);
    std::cout << "COMPLETED\n";

    delete[] buffer;
    return 0;
}
