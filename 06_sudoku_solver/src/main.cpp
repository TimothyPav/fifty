#include "SudokuBoard.h"
#include <SFML/Graphics.hpp>
#include <iostream>

int main() {
  sf::RenderWindow window(sf::VideoMode(540, 540), "Sudoku");

  try {
    SudokuBoard sudokuBoard(window);

    sudokuBoard.setValue(0, 0, 5);
    sudokuBoard.setValue(0, 1, 3);
    sudokuBoard.setValue(1, 4, 9);

    sudokuBoard.draw();
    window.display();

    while (window.isOpen()) {
      sf::Event event;
      while (window.pollEvent(event)) {
        if (event.type == sf::Event::Closed)
          window.close();
      }
    }


  } catch (const std::exception& e) {
      return 1;
  }

  return 0;
}
