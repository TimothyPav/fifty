#include "SudokuBoard.h"
#include <iostream>
#include <cmath>
#include <chrono>
#include <thread>

void SudokuBoard::drawGridLines() {
    // HORIZONTAL LINES
    for (int i = 0; i <= GRID_SIZE; i++) {
        sf::RectangleShape line(
            sf::Vector2f(GRID_SIZE * CELL_SIZE,
                        (i % 3 == 0) ? THICK_LINK_THICKNESS : LINE_THICKNESS));
        line.setPosition(0, i * CELL_SIZE);
        line.setFillColor(sf::Color::Black);
        window.draw(line);
    }
    // VERTICAL LINES
    for (int i = 0; i <= GRID_SIZE; i++) {
        sf::RectangleShape line(
            sf::Vector2f((i % 3 == 0) ? THICK_LINK_THICKNESS : LINE_THICKNESS,
                        GRID_SIZE * CELL_SIZE));
        line.setPosition(i * CELL_SIZE, 0);
        line.setFillColor(sf::Color::Black);
        window.draw(line);
    }
}

void SudokuBoard::drawNumbers() {
    for (int row = 0; row < GRID_SIZE; row++) {
        for (int col = 0; col < GRID_SIZE; col++) {
            if (grid[row][col] != 0) {
                sf::Text text;
                text.setFont(font);
                text.setString(std::to_string(grid[row][col]));
                text.setCharacterSize(30);
                text.setFillColor(sf::Color::Black);
                // Center numbers in the cell
                sf::FloatRect textRect = text.getLocalBounds();
                text.setPosition(col * CELL_SIZE + (CELL_SIZE - textRect.width) / 2,
                               row * CELL_SIZE + (CELL_SIZE - textRect.height) / 2);
                window.draw(text);
            }
        }
    }
}

SudokuBoard::SudokuBoard(sf::RenderWindow& window) : window(window) {
    // fill grid with zeroes
    for (auto& row : grid) {
        row.fill(0);
    }
    // Try to load the system font
    const std::string UBUNTU_FONT_PATH =
        "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf";
    if (!font.loadFromFile(UBUNTU_FONT_PATH)) {
        std::cerr << "Error: Could not load font from " << UBUNTU_FONT_PATH
                  << std::endl;
        std::cerr << "Please verify the font path or install dejavu-sans using:"
                  << std::endl;
        std::cerr << "sudo apt-get install fonts-dejavu" << std::endl;
        throw std::runtime_error("Font loading failed");
    }
}

void SudokuBoard::setValue(int row, int col, int value) {
    if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE &&
        value >= 0 && value <= 9) {
        grid[row][col] = value;
    }
}

void SudokuBoard::draw() {
    window.clear(sf::Color::White);
    drawGridLines();
    drawNumbers();
}

sf::Vector2i SudokuBoard::getCellFromPosition(int x, int y) {
    return sf::Vector2i(x / CELL_SIZE, y / CELL_SIZE);
}

bool SudokuBoard::checkPossible(int row, int col, int value){
    // check row and col where program wants to place value
    for (int i = 0; i < GRID_SIZE; i++){
        // std::cout << "grid[row][i]: " << grid[row][i] << "\n";
        // std::cout << "grid[i][col] " << grid[i][col] << "\n";
        if (grid[row][i] == value || grid[i][col] == value)
        {
            return false;
        }
    }
    // get 3x3 box where the value sits
    int subRow = floor(row/(double)3) * 3;
    int subCol = floor(col/(double)3) * 3;
    for (int i = 0; i < 3; i++){
        for (int j = 0; j < 3; j++){
            // find if value exists in the 3x3 box
            if (grid[i+subRow][j+subCol] == value)
            {
                return false;
            }
        }
    }

    return true;
}

bool SudokuBoard::solveBacktrack(int row, int col)
{
    // std::cout << "solving... current row and col: " << row << ", " << col << "\n";
    draw();
    window.display();
    std::this_thread::sleep_for(std::chrono::milliseconds(50));
    
    if (row == GRID_SIZE-1 && col == GRID_SIZE)
        return true;

    if (col == GRID_SIZE)
    {
        row++;
        col = 0;
    }

    if (grid[row][col] > 0)
        return solveBacktrack(row, col+1);

    for (int i = 1; i <= 9; i++)
    {
        if (checkPossible(row, col, i))
        {
            setValue(row, col, i);
            if (solveBacktrack(row, col+1))
                return true;

            // backtrack step
            setValue(row, col, 0);
        }
    }
    std::cout << "returning false...\n";
    return false;
}

void SudokuBoard::printBoard()
{
    std::cout << '\n';
    for (int i = 0; i < GRID_SIZE; i++){
        for (int j = 0; j < GRID_SIZE; j++){
            std::cout << grid[i][j] << " ";
        }
        std::cout << '\n';
    }
}


