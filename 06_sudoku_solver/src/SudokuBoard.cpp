#include "SudokuBoard.h"
#include <algorithm>
#include <chrono>
#include <cmath>
#include <iostream>
#include <numeric>
#include <random>
#include <thread>
#include <vector>

void SudokuBoard::copyGrid() {
  for (int i = 0; i < GRID_SIZE; i++) {
    for (int j = 0; j < GRID_SIZE; j++) {
      originalGrid[i][j] = grid[i][j];
    }
  }
}

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

void SudokuBoard::shuffle() {
  for (int i = 0; i < GRID_SIZE; i++) {
    for (int j = 0; j < GRID_SIZE; j++) {
      grid[i][j] = 0;
      originalGrid[i][j] = -1;
    }
  }
  blankRandomSolve();
  for (int i = 0; i < GRID_SIZE; i++) {
    for (int j = 0; j < GRID_SIZE; j++) {
      if (int random_number = rand() % 2  == 0) {
          originalGrid[i][j] = 0;
          grid[i][j] = 0;
      } 
    }
  }
  draw();
}

void SudokuBoard::reset() {
  for (int i = 0; i < GRID_SIZE; i++) {
    for (int j = 0; j < GRID_SIZE; j++) {
        if (originalGrid[i][j] == 0) // if the unsolved part of the board was blank, set the main board to blank
            grid[i][j] = 0;
    }
  }
}

bool SudokuBoard::blankRandomSolve() {
  int row, col;

  if (!findEmptyLocation(row, col))
    return true;

  std::vector<int> numbers(9);
  std::iota(numbers.begin(), numbers.end(), 1); // Fill with 1-9

  // Shuffle the vector to randomize
  std::random_device rd;
  std::mt19937 g(rd());
  std::shuffle(numbers.begin(), numbers.end(), g);

  for (int& num : numbers) {
    if (checkPossible(row, col, num)) {
      grid[row][col] = num;

      if (blankRandomSolve())
        return true;

      // backtrack step
      grid[row][col] = 0;
    }
  }
  return false; // will never get here
}

void SudokuBoard::drawNumbers() {
  for (int row = 0; row < GRID_SIZE; row++) {
    for (int col = 0; col < GRID_SIZE; col++) {
      if (grid[row][col] != 0) {
        sf::Text text;
        text.setFont(font);
        text.setString(std::to_string(grid[row][col]));
        text.setCharacterSize(30);

        // If part of original grid, fill color BLACK else BLUE
        if (originalGrid[row][col] == 0)
          text.setFillColor(sf::Color::Blue);
        else
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

SudokuBoard::SudokuBoard(sf::RenderWindow &window) : window(window) {
  // fill grid with zeroes
  for (auto &row : grid) {
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

  // test
  for (int i = 0; i < buttons.size(); i++) {
    window.draw(buttons[i].first);
    window.draw(buttons[i].second);
  }
  window.draw(text);
}

sf::Vector2i SudokuBoard::getCellFromPosition(int x, int y) {
  return sf::Vector2i(x / CELL_SIZE, y / CELL_SIZE);
}

bool SudokuBoard::checkPossible(int row, int col, int value) {
  // check row and col where program wants to place value
  for (int i = 0; i < GRID_SIZE; i++) {
    // std::cout << "grid[row][i]: " << grid[row][i] << "\n";
    // std::cout << "grid[i][col] " << grid[i][col] << "\n";
    if (grid[row][i] == value || grid[i][col] == value) {
      return false;
    }
  }
  // get 3x3 box where the value sits
  int subRow = floor(row / (double)3) * 3;
  int subCol = floor(col / (double)3) * 3;
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
      // find if value exists in the 3x3 box
      if (grid[i + subRow][j + subCol] == value) {
        return false;
      }
    }
  }

  return true;
}

bool SudokuBoard::solveBacktrack(int row, int col) {
  // std::cout << "solving... current row and col: " << row << ", " << col <<
  // "\n";
  draw();
  window.display();
  std::this_thread::sleep_for(std::chrono::milliseconds(50));

  if (row == GRID_SIZE - 1 && col == GRID_SIZE)
    return true;

  if (col == GRID_SIZE) {
    row++;
    col = 0;
  }

  if (grid[row][col] > 0)
    return solveBacktrack(row, col + 1);

  for (int i = 1; i <= 9; i++) {
    if (checkPossible(row, col, i)) {
      setValue(row, col, i);
      if (solveBacktrack(row, col + 1))
        return true;

      // backtrack step
      setValue(row, col, 0);
    }
  }
  return false;
}

bool SudokuBoard::findEmptyLocation(int &row, int &col) {
  for (row = 0; row < GRID_SIZE; row++) {
    for (col = 0; col < GRID_SIZE; col++) {
      if (grid[row][col] == 0)
        return true;
    }
  }
  return false;
}

bool SudokuBoard::betterSolveBacktrack() {
  draw();
  window.display();
  std::this_thread::sleep_for(std::chrono::milliseconds(50));

  int row, col;

  if (!findEmptyLocation(row, col))
    return true;

  for (int num = 1; num <= 9; num++) {
    if (checkPossible(row, col, num)) {
      grid[row][col] = num;

      if (betterSolveBacktrack())
        return true;

      // backtrack step
      grid[row][col] = 0;
    }
  }

  return false;
}

void SudokuBoard::printBoard() {
  for (int i = 0; i < GRID_SIZE; i++) {
    for (int j = 0; j < GRID_SIZE; j++) {
      std::cout << grid[i][j] << " ";
    }
    std::cout << '\n';
  }
}

void SudokuBoard::setButtons(
    std::vector<std::pair<sf::RectangleShape, sf::Text>> &b) {
  this->buttons = b;
}

void SudokuBoard::setText(sf::Text &text) { this->text = text; }
