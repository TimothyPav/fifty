#ifndef RULES_H
#define RULES_H

#include <utility>

enum class NeighborhoodShape {
    All8,
    Plus,
    X
};

class Rules{
    private:
        std::pair<int, int> surivial_conditions;
        int birth_condition;
        NeighborhoodShape shape;

    public:
        Rules(std::pair<int, int> survival_conditions, int birth_condition, NeighborhoodShape shape);

        // getters
        std::pair<int, int> get_pair();
        int get_birth_condition();
        NeighborhoodShape get_shape();

        // setters
        void set_pair(std::pair<int, int> survival_conditions);
        void set_birth_condition(int birth_condition);
        void set_shape(NeighborhoodShape shape);

};

#endif
