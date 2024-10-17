#include "Rules.h"
#include <iostream>

Rules::Rules() {}
Rules::Rules(std::pair<int, int> survival_conditions, int birth_condition, NeighborhoodShape shape)
    : surivial_conditions(survival_conditions), birth_condition(birth_condition), shape(shape) {}

std::pair<int, int> Rules::get_pair() const{
    return surivial_conditions;
}

int Rules::get_birth_condition() const{
    return birth_condition;
}

NeighborhoodShape Rules::get_shape() const{
    return shape;
}

void Rules::set_pair(std::pair<int, int> survival_conditions) {
    this->surivial_conditions = survival_conditions;
}

void Rules::set_birth_condition(int birth_condition) {
    this->birth_condition = birth_condition;
}

void Rules::set_shape(NeighborhoodShape shape) {
    this->shape = shape;
}

std::string neighborhoodShapeToString(NeighborhoodShape shape) {
    switch (shape) {
        case NeighborhoodShape::All8: return "All8";
        case NeighborhoodShape::Plus: return "Plus";
        case NeighborhoodShape::X: return "X";
        default: return "Unknown";
    }
}

//printer
void Rules::print() const{
    std::pair<int, int> survival_conditions = get_pair();
    int birth_condition = get_birth_condition();
    NeighborhoodShape shape = get_shape();

    std::cout << "survival_conditions: " << surivial_conditions.first << ", " << survival_conditions.second << '\n';
    std::cout << "birth_condition: " << birth_condition << '\n';
    std::cout << "shape: " << neighborhoodShapeToString(shape) << '\n';
}

