#include "SudokuBoard.h"
#include <SFML/Graphics.hpp>
#include <SFML/Graphics/Color.hpp>
#include <iostream>

int main() {
  sf::RenderWindow window(sf::VideoMode(540, 800), "Sudoku");

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

    sudokuBoard.copyGrid();
    sudokuBoard.draw();
    window.display();
    std::vector<std::pair<sf::RectangleShape, sf::Text>> buttons;

    sf::Text text;
    text.setFont(sudokuBoard.font);
    text.setCharacterSize(40);
    text.setString("Time: 2.035s");
    text.setPosition(10.f, 550);
    text.setFillColor(sf::Color::Black);

    for (int i=0; i<5; i++) {
        int yPos{(i+1)*10 + 540 + (73 * i)};
        sf::RectangleShape rec(sf::Vector2f(200.f, 73.f));
        sf::Text text;
        text.setFont(sudokuBoard.font);
        text.setCharacterSize(30);
        rec.setFillColor(sf::Color::Red);
        if (i < 3){
            rec.move(sf::Vector2f(335.f, yPos));
            text.setPosition(345.f, yPos+15);
            std::string algo = "Algorithm " + std::to_string(i+1);
            text.setString(algo);
        }
        else {
            yPos = {(i+1-2)*10 + 540 + (73*(i-2))};
            rec.move(sf::Vector2f(10.f, yPos));
            text.setPosition(20.f, yPos+15);
            std::string option;
            if (i == 3)
                option = "Shuffle";
            else if (i == 4)
                option = "Reset";
            text.setString(option);
        }
        buttons.push_back({rec, text});
    }

    while (window.isOpen()) {
      sf::Event event;

      while (window.pollEvent(event)) {
        if (event.type == sf::Event::Closed)
          window.close();
        if (event.type == sf::Event::KeyPressed) {
          if (sudokuBoard.betterSolveBacktrack()) {
            std::cout << "solved\n";
          } else {
            std::cout << "impossible to solve\n";
          }
        }
      }
      sudokuBoard.draw();
      for (int i=0; i<buttons.size(); i++) {
          window.draw(buttons[i].first);
          window.draw(buttons[i].second);
      }
      window.draw(text);
      window.display();
    }

  } catch (const std::exception &e) {
    return 1;
  }

  return 0;
}
