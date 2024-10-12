cmake_minimum_required(VERSION 3.10)
project(game_of_life)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

include_directories(${CMAKE_SOURCE_DIR}/include)

find_package(SDL2 REQUIRED)
find_package(SDL2_image REQUIRED)

add_executable(game_of_life
    src/main.cpp
)

target_include_directories(game_of_life PRIVATE 
    ${SDL2_INCLUDE_DIRS}
    ${SDL2_IMAGE_INCLUDE_DIRS}
)

target_link_libraries(game_of_life PRIVATE 
    ${SDL2_LIBRARIES}
    SDL2_image::SDL2_image
)

set(CMAKE_CXX_FLAGS_RELEASE "-O3")
