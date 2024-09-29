cmake_minimum_required(VERSION 3.10)

# Set the project name
project(Raytracer)

# Set the C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)

# Include directories for headers
include_directories(include)

# Add the executable
add_executable(raytracer 
  src/main.cpp
)

# Optional: enable optimization for release builds
set(CMAKE_CXX_FLAGS_RELEASE "-O3")
