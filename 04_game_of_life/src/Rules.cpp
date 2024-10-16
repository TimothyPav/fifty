#include "Rules.h"

Rules::Rules(std::pair<int, int> survival_conditions, int birth_condition, NeighborhoodShape shape)
    : surivial_conditions(survival_conditions), birth_condition(birth_condition), shape(shape) {}

std::pair<int, int> Rules::get_pair() {
    return surivial_conditions;
}

int Rules::get_birth_condition() {
    return birth_condition;
}

NeighborhoodShape Rules::get_shape() {
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

