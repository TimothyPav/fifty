cmake_minimum_required(VERSION 3.10)
project(sudoku-solver)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

find_package(SFML 2.5 COMPONENTS graphics window system REQUIRED)

include_directories(include/)

add_executable(solve src/main.cpp)

target_link_libraries(solve PRIVATE sfml-graphics sfml-window sfml-system)
