#ifndef SUDOKU_BOARD_H
#define SUDOKU_BOARD_H

#include <SFML/Graphics.hpp>
#include <SFML/Graphics/RenderWindow.hpp>
#include <array>
#include <string>

class SudokuBoard {
private:
    static const int GRID_SIZE = 9;
    static const int CELL_SIZE = 60;
    static const int LINE_THICKNESS = 2;
    static const int THICK_LINK_THICKNESS = 4;

    sf::RenderWindow& window;
    std::array<std::array<int, GRID_SIZE>, GRID_SIZE> grid;
    sf::Font font;

    void drawGridLines();
    void drawNumbers();

public:
    // Constructor
    SudokuBoard(sf::RenderWindow& window);

    // Public member functions
    void setValue(int row, int col, int value);
    void draw();
    sf::Vector2i getCellFromPosition(int x, int y);
};

#endif // SUDOKU_BOARD_H
