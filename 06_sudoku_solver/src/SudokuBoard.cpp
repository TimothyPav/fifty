#include "SudokuBoard.h"
#include <iostream>

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
