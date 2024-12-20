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
    std::array<std::array<int, GRID_SIZE>, GRID_SIZE> originalGrid;

    void drawGridLines();
    void drawNumbers();

public:
    sf::Font font;
    std::vector<std::pair<sf::RectangleShape, sf::Text>> buttons;
    sf::Text text;

    // Constructor
    SudokuBoard(sf::RenderWindow& window);

    // Public member functions
    void setValue(int row, int col, int value);
    void draw();
    sf::Vector2i getCellFromPosition(int x, int y);
    bool checkPossible(int row, int col, int value);

    bool solveBacktrack(int row, int col);

    bool findEmptyLocation(int& row, int& col);
    bool betterSolveBacktrack();
    
    void printBoard();
    void copyGrid();
    void reset();
    void shuffle();
    bool blankRandomSolve(); 

    void setButtons(std::vector<std::pair<sf::RectangleShape, sf::Text>>& b);
    void setText(sf::Text& text);
};

#endif // SUDOKU_BOARD_H
