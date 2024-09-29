cmake_minimum_required(VERSION 3.10)

# Set the project name
project(Raytracer)

# Set the C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

# Include directories for headers
include_directories(${CMAKE_SOURCE_DIR}/include)

# Add the executable
add_executable(raytracer 
  src/main.cpp
  src/Vector3D.cpp
  src/Ray.cpp
  src/Sphere.cpp
  src/Camera.cpp
)

# Optional: enable optimization for release builds
set(CMAKE_CXX_FLAGS_RELEASE "-O3")
