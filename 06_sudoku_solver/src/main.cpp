#include "SudokuBoard.h"
#include <SFML/Graphics.hpp>
#include <iostream>

int main() {
  sf::RenderWindow window(sf::VideoMode(540, 540), "Sudoku");

  try {
    SudokuBoard sudokuBoard(window);

    sudokuBoard.setValue(0, 3, 2);
    sudokuBoard.setValue(0, 4, 6);
    sudokuBoard.setValue(0, 6, 7);
    sudokuBoard.setValue(0, 8, 1);

    sudokuBoard.setValue(1, 0, 6);
    sudokuBoard.setValue(1, 1, 8);
    sudokuBoard.setValue(1, 4, 7);
    sudokuBoard.setValue(1, 7, 9);

    sudokuBoard.setValue(2, 0, 1);
    sudokuBoard.setValue(2, 1, 9);
    sudokuBoard.setValue(2, 5, 4);
    sudokuBoard.setValue(2, 6, 5);

    sudokuBoard.setValue(3, 0, 8);
    sudokuBoard.setValue(3, 1, 2);
    sudokuBoard.setValue(3, 3, 1);
    sudokuBoard.setValue(3, 7, 4);

    sudokuBoard.setValue(4, 2, 4);
    sudokuBoard.setValue(4, 3, 6);
    sudokuBoard.setValue(4, 5, 2);
    sudokuBoard.setValue(4, 6, 9);

    sudokuBoard.setValue(5, 1, 5);
    sudokuBoard.setValue(5, 5, 3);
    sudokuBoard.setValue(5, 7, 2);
    sudokuBoard.setValue(5, 8, 8);

    sudokuBoard.setValue(6, 2, 9);
    sudokuBoard.setValue(6, 3, 3);
    sudokuBoard.setValue(6, 7, 7);
    sudokuBoard.setValue(6, 8, 4);

    sudokuBoard.setValue(7, 1, 4);
    sudokuBoard.setValue(7, 4, 5);
    sudokuBoard.setValue(7, 7, 3);
    sudokuBoard.setValue(7, 8, 6);

    sudokuBoard.setValue(8, 0, 7);
    sudokuBoard.setValue(8, 2, 3);
    sudokuBoard.setValue(8, 4, 1);
    sudokuBoard.setValue(8, 5, 8);

    sudokuBoard.draw();
    window.display();

    while (window.isOpen()) {
      sf::Event event;
      while (window.pollEvent(event)) {
        if (event.type == sf::Event::Closed)
          window.close();
        if (event.type == sf::Event::KeyPressed) {
            if (sudokuBoard.solveBacktrack(0, 0))
            {
                std::cout << "solved\n";
            } else {
                std::cout << "impossible to solve\n";
            }
            
        }
      }
      sudokuBoard.draw();
      window.display();
    }

  } catch (const std::exception &e) {
    return 1;
  }

  return 0;
}
